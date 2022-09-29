package opt_hdfs;

import org.apache.hadoop.conf.Configuration;
import org.apache.hadoop.fs.FSDataOutputStream;
import org.apache.hadoop.fs.FileSystem;
import org.apache.hadoop.fs.Path;

import java.io.IOException;
import java.net.URI;

/*
 * 
 * 使用write方法写字符串到12.txt
 * 
 * */
public class WriteFile {

	public static void main(String[] args) throws IOException {
		
		String str = "123";
		String dst = "hdfs://zgg:9000/test/12.txt";
		Configuration conf = new Configuration();
		FileSystem fs = FileSystem.get(URI.create(dst), conf);
		
		FSDataOutputStream out = fs.create(new Path(dst));
		
		out.writeBytes(str);
		
 		System.out.println("写入成功");
		
		out.close();

	}

}
