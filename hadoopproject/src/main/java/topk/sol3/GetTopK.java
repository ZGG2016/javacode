package topk.sol3;

import org.apache.hadoop.conf.Configuration;
import org.apache.hadoop.fs.FileSystem;
import org.apache.hadoop.fs.Path;
import org.apache.hadoop.io.NullWritable;
import org.apache.hadoop.io.Text;
import org.apache.hadoop.mapreduce.Job;
import org.apache.hadoop.mapreduce.Mapper;
import org.apache.hadoop.mapreduce.Reducer;
import org.apache.hadoop.mapreduce.lib.input.FileInputFormat;
import org.apache.hadoop.mapreduce.lib.output.FileOutputFormat;
import org.apache.log4j.BasicConfigurator;

import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.TreeMap;

public class GetTopK {

    public static final int K = 3;
    private static final String IN = "hdfs://zgg:9000/out/topk/cnum";
    private static final String OUT = "hdfs://zgg:9000/out/topk/res3";

    public static void main(String[] args) throws URISyntaxException, IOException, ClassNotFoundException, InterruptedException {

        BasicConfigurator.configure();

        FileSystem fs = FileSystem.get(new URI(IN),new Configuration());
        Path out = new Path(OUT);
        if(fs.exists(out)){
            fs.delete(out,true);
        }

        Job job = Job.getInstance();
        job.setJarByClass(GetTopK.class);

        job.setMapperClass(InputMapper.class);
        job.setMapOutputKeyClass(NullWritable.class);
        job.setMapOutputValueClass(Text.class);

//        job.setReducerClass(GetKReducer.class);
//        job.setOutputKeyClass(NullWritable.class);
//        job.setOutputValueClass(Text.class);

        FileInputFormat.addInputPath(job,new Path(IN));
        FileOutputFormat.setOutputPath(job,new Path(OUT));

        System.exit(job.waitForCompletion(true)?0:1);
    }

    private static class InputMapper extends Mapper<Object, Text, NullWritable, Text> {

        private static TreeMap<Integer, String> tm = new TreeMap<Integer, String>();

        @Override
        protected void map(Object key, Text value, Context context) throws IOException, InterruptedException {
            String[] ss = value.toString().split("\t");

            tm.put(Integer.parseInt(ss[1]), value.toString());
            if (tm.size() > K)
                tm.remove(tm.firstKey());
        }

        @Override
        protected void cleanup(Context context) throws IOException, InterruptedException {

//            System.out.println("=================================");
//            Set<Integer> set = tm.keySet();
//            for(Integer s:set){
//                System.out.println(String.valueOf(s)+":"+tm.get(s).toString());
//            }
//            System.out.println("=================================");

            for(String val: tm.values()){
                context.write(NullWritable.get(), new Text(val));
            }
        }

    }

    public static class GetKReducer extends Reducer<NullWritable, Text, NullWritable, Text> {
        private static TreeMap<Integer, String> tm = new TreeMap<Integer, String>();

        @Override
        protected void reduce(NullWritable key, Iterable<Text> values, Context context) throws IOException, InterruptedException {
            for (Text val : values) {

                String[] ss = val.toString().split("\t");

                tm.put(Integer.parseInt(ss[1]), val.toString());

                if (tm.size() > K)
                    tm.remove(tm.firstKey());
            }
            for (String val: tm.values())
                context.write(NullWritable.get(), new Text(val));
        }

    }
//    url1    18
//    url6    12
//    url5    11
}
