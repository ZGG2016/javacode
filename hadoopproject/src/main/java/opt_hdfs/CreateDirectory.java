package opt_hdfs;

import org.apache.hadoop.conf.Configuration;
import org.apache.hadoop.fs.FileSystem;
import org.apache.hadoop.fs.Path;
import org.apache.log4j.BasicConfigurator;

import java.io.IOException;
import java.net.URI;

/*
 * mkdirs:创建目录
 * */
public class CreateDirectory {

	public static void main(String[] args) throws IOException {
		BasicConfigurator.configure(); 
		
        Configuration conf = new Configuration();
        String uri="hdfs://zgg:9000/";
        FileSystem fs = FileSystem.get(URI.create(uri), conf);
        fs.mkdirs(new Path(uri+"test"));
        System.out.println("创建成功");
        
        fs.close();
	}
}
