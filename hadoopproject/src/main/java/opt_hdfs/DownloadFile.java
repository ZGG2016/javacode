package opt_hdfs;

import org.apache.hadoop.conf.Configuration;
import org.apache.hadoop.fs.FileSystem;
import org.apache.hadoop.fs.Path;
import org.apache.log4j.BasicConfigurator;

import java.io.IOException;
import java.net.URI;

/*
 * 从hdfs上复制文件到本地
 * 
 * */
public class DownloadFile {

	public static void main(String[] args) throws IOException {
		BasicConfigurator.configure(); 
		
        Configuration conf = new Configuration();
        String uri="hdfs://zgg:9000/";
        String hdfs = uri+"test/1.txt";
        String local = "/data";
        FileSystem fs = FileSystem.get(URI.create(uri), conf);
        fs.copyToLocalFile(new Path(hdfs),new Path(local));
        System.out.println("下载成功");
        fs.close();
        }
}
