package stjoin;

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
import java.util.ArrayList;

/*
 * 单表关联：https://www.pianshen.com/article/14641771190/
 * */
public class STjoin {

    static final String input_path = "hdfs://zgg:9000/in/stjoin.txt";
    static final String output_path = "hdfs://zgg:9000/out/stjoin";

    public static void main(String[] args) throws Exception {

        BasicConfigurator.configure();
        Configuration conf = new Configuration();

        FileSystem fileSystem = FileSystem.get(new URI(input_path), conf);
        Path outPath = new Path(output_path);
        if(fileSystem.exists(outPath)){
            fileSystem.delete(outPath, true);
        }

        Job job=Job.getInstance(conf);

        job.setJarByClass(STjoin.class);

        job.setMapperClass(STjoinMapper.class);
        job.setMapOutputKeyClass(Text.class);
        job.setMapOutputValueClass(Text.class);

        job.setReducerClass(STjoinReducer.class);
        job.setOutputKeyClass(Text.class);
        job.setOutputValueClass(Text.class);

        FileInputFormat.setInputPaths(job,new Path(input_path));
        FileOutputFormat.setOutputPath(job, outPath);

        System.exit(job.waitForCompletion(true)?0:1);

    }

    public static class STjoinMapper extends Mapper<Object, Text, Text, Text>{

        @Override
        protected void map(Object key, Text value, Mapper<Object, Text, Text, Text>.Context context)
                throws IOException, InterruptedException {
            String[] token = value.toString().split(" ");
            String child = token[0];
            String parent = token[1];

            context.write(new Text(parent), new Text("+"+child));
            context.write(new Text(child), new Text("-"+parent));

        }
    }

    public static class STjoinReducer extends Reducer<Text, Text,Text, Text>{
        private final Text grandchild = new Text();
        private final Text grandparent = new Text();
        public static int time=0;

        @Override
        protected void reduce(Text key, Iterable<Text> values, Reducer<Text, Text, Text, Text>.Context context)
                throws IOException, InterruptedException {

            //输出表头
            if(time==0) {
                context.write(new Text("grandchild"),new Text("grandparent"));
                time++;
            }

            ArrayList<Text> grandparent = new ArrayList<Text>();
            ArrayList<Text> grandchild = new ArrayList<Text>();

            for(Text val : values) {
                String s= val.toString();
                if(s.startsWith("-")){
                    grandparent.add(new Text(s.substring(1)));
                }
                else{
                    grandchild.add(new Text(s.substring(1)));
                }
            }

            for(int i=0;i<grandchild.size();i++){
                //System.out.println("孙"+grandchild.get(i));
                for(int j=0;j<grandparent.size();j++){
                    context.write(grandchild.get(i), grandparent.get(j));
                    // System.out.println("组"+grandparent.get(j));
                }
            }
        }

    }
}

