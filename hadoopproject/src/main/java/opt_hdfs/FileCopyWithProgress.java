package opt_hdfs;

import org.apache.hadoop.conf.Configuration;
import org.apache.hadoop.fs.FSDataOutputStream;
import org.apache.hadoop.fs.FileSystem;
import org.apache.hadoop.fs.Path;
import org.apache.hadoop.io.IOUtils;
import org.apache.hadoop.util.Progressable;

import java.io.BufferedInputStream;
import java.io.FileInputStream;
import java.io.InputStream;
import java.net.URI;

/*
 * 把1.txt中的内容添加到12.txt
 * 
 * */
public class FileCopyWithProgress {

	public static void main(String[] args) throws Exception{
		
		String localSrc =  "/data/1.txt";
		String dst = "hdfs://zgg:9000/test/12.txt";
		//创建BufferedInputStream对象时，会创建一个内部缓存数组，读到的内容会存到这个数组中
		InputStream in = new BufferedInputStream(new FileInputStream(localSrc));
		
		Configuration conf = new Configuration();
		FileSystem fs = FileSystem.get(URI.create(dst), conf);
		
		FSDataOutputStream out = fs.create(new Path(dst), new Progressable() {
			@Override
			public void progress() {
				System.out.println("写入成功");  //显示数据写入数据节点的进度
			}
		});
		
		IOUtils.copyBytes(in,out,4096,true);  //会覆盖原来的内容
	}
}
