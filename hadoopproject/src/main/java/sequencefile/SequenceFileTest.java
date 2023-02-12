package sequencefile;

import org.apache.hadoop.conf.Configuration;
import org.apache.hadoop.fs.FSDataInputStream;
import org.apache.hadoop.fs.FileStatus;
import org.apache.hadoop.fs.FileSystem;
import org.apache.hadoop.fs.Path;
import org.apache.hadoop.io.SequenceFile;
import org.apache.hadoop.io.Text;
import org.apache.hadoop.io.Writable;
import org.apache.hadoop.util.ReflectionUtils;

import java.io.IOException;
import java.util.Arrays;

public class SequenceFileTest {

    private static final String INSTR = "hdfs://bigdata101:9000/in/sf";
    private static final String OUTSTR = "hdfs://bigdata101:9000/out/sfile";

    public static void main(String[] args) throws IOException{
        write(INSTR,OUTSTR);
//        read(OUTSTR);
    }

    public static void write(String inStr,String outStr) throws IOException {

        System.setProperty("HADOOP_USER_NAME","root");

        Configuration conf = new Configuration();
        conf.set("mapred.jop.tracker", "hdfs://bigdata101:9001");
        conf.set("fs.default.name", "hdfs://bigdata101:9000");
        FileSystem fs = FileSystem.get(conf);

        Text key = new Text();
        Text value = new Text();

        SequenceFile.Writer.Option fileOption = SequenceFile.Writer.file(new Path(outStr));
        SequenceFile.Writer.Option keyOption = SequenceFile.Writer.keyClass(key.getClass());
        SequenceFile.Writer.Option valueOption = SequenceFile.Writer.valueClass(value.getClass());
        SequenceFile.Writer.Option compressionOption= SequenceFile.Writer.compression(SequenceFile.CompressionType.BLOCK);

        SequenceFile.Writer writer = SequenceFile.createWriter(
                conf,fileOption,keyOption,valueOption,compressionOption
        );

        //获取输入目录下的所有文件
        FileStatus[] fileStatusArr = fs.listStatus(new Path(inStr));
        for (FileStatus fileStatus : fileStatusArr) {

            System.out.println("the file name is " + fileStatus.getPath());
            FSDataInputStream fsDataIn = fs.open(fileStatus.getPath());
            byte[] buffer = new byte[((int) fileStatus.getLen())];
            int num = fsDataIn.read(buffer);

            key.set(fileStatus.getPath().toString());
            System.out.println("key ---> " + fileStatus.getPath().toString());
            value.set(buffer);
            System.out.println("value ---> " + buffer);
            writer.append(key, value);
        }

        writer.close();

    }

    public static void read(String inStr) throws IOException{

        Configuration conf = new Configuration();

        SequenceFile.Reader.Option fileOption = SequenceFile.Reader.file(new Path(inStr));

        SequenceFile.Reader reader = new SequenceFile.Reader(conf,fileOption);

        Writable key = (Writable) ReflectionUtils.newInstance(reader.getKeyClass(),conf);
        Writable value = (Writable)ReflectionUtils.newInstance(reader.getValueClass(),conf);

        while (reader.next(key, value)){
            System.out.println("**********"+"key:"+key+"**********");
            System.out.println(value);
            System.out.println("************************************");
        }

        reader.close();
    }
}
