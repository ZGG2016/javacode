package mutualfriends;

import org.apache.hadoop.conf.Configuration;
import org.apache.hadoop.fs.FileSystem;
import org.apache.hadoop.fs.Path;
import org.apache.hadoop.io.Text;
import org.apache.hadoop.mapreduce.Job;
import org.apache.hadoop.mapreduce.Mapper;
import org.apache.hadoop.mapreduce.Reducer;
import org.apache.hadoop.mapreduce.lib.input.FileInputFormat;
import org.apache.hadoop.mapreduce.lib.output.FileOutputFormat;
import org.apache.log4j.BasicConfigurator;

import java.io.IOException;
import java.net.URI;
import java.util.Set;
import java.util.StringTokenizer;
import java.util.TreeSet;

/**
 *方法1：
 *   对于A的朋友是BCDEF，那么BC的共同朋友就是A。
 *   所以将BC作为key，将A作为value，在map端输出即可！其他的朋友循环处理。
 */

public class MutualFriends01 {

    private static final String INPUT_PATH = "hdfs://zgg:9000/in/mf.txt";
    private static final String OUTPUT_PATH = "hdfs://zgg:9000/out/mf";

    public static void main(String[] args) throws Exception {
        BasicConfigurator.configure();
        Configuration conf = new Configuration();

        FileSystem fs = FileSystem.get(new URI(INPUT_PATH), conf);
        Path outPath = new Path(OUTPUT_PATH);

        if(fs.exists(outPath)){
            fs.delete(outPath,true);
        }

        Job job = Job.getInstance(conf);
        job.setJarByClass(MutualFriends01.class);

        job.setMapperClass(MFMapper.class);
        job.setMapOutputKeyClass(Text.class);
        job.setMapOutputValueClass(Text.class);

        job.setReducerClass(MFReducer.class);
        job.setOutputKeyClass(Text.class);
        job.setOutputValueClass(Text.class);

        FileInputFormat.setInputPaths(job,new Path(INPUT_PATH));
        FileOutputFormat.setOutputPath(job,new Path(OUTPUT_PATH));
        System.exit(job.waitForCompletion(true)? 0: 1);

    }

    private static class MFMapper extends Mapper<Object, Text, Text, Text> {
        Text k = new Text();
        Text v = new Text();

        @Override
        public void map(Object key, Text value, Context context) throws
                IOException, InterruptedException {
            StringTokenizer itr = new StringTokenizer(value.toString());

            v.set(itr.nextToken());

            Set<String> set = new TreeSet<String>();
            while (itr.hasMoreTokens()) {
                set.add(itr.nextToken());
            }

            String[] friends = new String[set.size()];
            friends = set.toArray(friends);

            for(int i=0;i<friends.length;i++){
                for(int j=i+1;j<friends.length;j++){
                    String outputkey = friends[i]+friends[j];
                    k.set(outputkey);
                    context.write(k,v);
                }
            }
        }
    }

    public static class MFReducer extends Reducer<Text,Text,Text,Text> {

        Text v = new Text();

        public void reduce(Text key, Iterable<Text> values,
                Context context) throws IOException, InterruptedException {

            String  mutualfriends ="";

            for (Text val : values) {
                if("".equals(mutualfriends)){
                    mutualfriends = val.toString();
                }else{
                    mutualfriends = mutualfriends+":"+val.toString();
                }
            }
            v.set(mutualfriends);
            context.write(key, v);
        }
    }

}
