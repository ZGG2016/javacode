package distributedcache;

import org.apache.hadoop.conf.Configuration;
import org.apache.hadoop.fs.Path;
import org.apache.hadoop.io.LongWritable;
import org.apache.hadoop.io.NullWritable;
import org.apache.hadoop.io.Text;
import org.apache.hadoop.mapreduce.Job;
import org.apache.hadoop.mapreduce.Mapper;
import org.apache.hadoop.mapreduce.Reducer;
import org.apache.hadoop.mapreduce.filecache.DistributedCache;
import org.apache.hadoop.mapreduce.lib.input.FileInputFormat;
import org.apache.hadoop.mapreduce.lib.output.FileOutputFormat;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.HashMap;
import java.util.Map;

/**
 * 分布式缓存：https://blog.csdn.net/lgdlxc/article/details/49836957
 * [未跑成功]
 */
public class DistributedCacheTest {

    private static final String INPUT_PATH = "hdfs://node1:9000/in/movies.dat";
    private static final String OUTPUT_PATH = "hdfs://node1:9000/out/movies";

    public static void main(String[] args) throws InterruptedException, ClassNotFoundException, IOException, URISyntaxException {
        Configuration conf = new Configuration();

        DistributedCache.createSymlink(conf);
        DistributedCache.addCacheFile(new URI("hdfs://node1:9000/in/movies.dat#moviesss.dat"), conf);

        Job job = new Job(conf);
        job.setJobName("Join on Map Side");
        job.setJarByClass(DistributedCacheTest.class);

        job.setMapperClass(MapJoiner.class);
        job.setMapOutputKeyClass(Text.class);
        job.setMapOutputValueClass(Text.class);

        job.setReducerClass(DirectReducer.class);
        job.setOutputKeyClass(NullWritable.class);
        job.setOutputValueClass(Text.class);

        FileInputFormat.addInputPath(job, new Path(INPUT_PATH));
        FileOutputFormat.setOutputPath(job, new Path(OUTPUT_PATH));
        System.exit(job.waitForCompletion(true) ? 0 : 1);
    }

    public static class MapJoiner extends Mapper<LongWritable, Text, Text, Text> {

        static Map<String, String> movies = new HashMap<String, String>();

        public void setup(Context context) {
            try {
                FileReader reader = new FileReader("moviesss.dat");
                BufferedReader br = new BufferedReader(reader);
                String s1 = null;

                while ((s1 = br.readLine()) != null) {
                    System.out.println(s1);
                    String[] splits = s1.split("::");
                    String movieId = splits[0];
                    String movieName = splits[1];
                    movies.put(movieId, movieName);
                }
                br.close();
                reader.close();

            } catch (Exception e) {
                e.printStackTrace();
            }
        }

        private final Text outKey = new Text();
        private final Text outVal = new Text();

        public void map(LongWritable key, Text value, Context context) throws IOException, InterruptedException {

            if (value != null || value.toString() != null) {
                String[] splits = value.toString().split("::");
                String movieId = splits[1];
                String movieName = movies.get(movieId);
                outKey.set(movieId);
                outVal.set(movieName + "::" + value.toString());
                context.write(outKey, outVal);
            }
        }
    }

    public static class DirectReducer extends Reducer<Text, Text, NullWritable, Text> {
        NullWritable outKey = NullWritable.get();

        public void reduce(Text key, Iterable<Text> values, Context context) throws IOException, InterruptedException {
            for (Text value : values) {
                context.write(outKey, value);
            }
        }
    }
}
