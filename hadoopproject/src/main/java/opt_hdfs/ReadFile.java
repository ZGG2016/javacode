package opt_hdfs;

import org.apache.hadoop.conf.Configuration;
import org.apache.hadoop.fs.FSDataInputStream;
import org.apache.hadoop.fs.FileSystem;
import org.apache.hadoop.fs.Path;
import org.apache.hadoop.io.IOUtils;

import java.io.IOException;
import java.net.URI;

/*
 * 读hdfs中的文件
 * 
 * */
public class ReadFile {
	
	public static void main(String[] args) throws IOException{
		 	Configuration conf = new Configuration();
		 	String uri = "hdfs://zgg:9000/test/1.txt";
	        FileSystem fs = FileSystem.get(URI.create(uri),conf);	 
	        FSDataInputStream in = null;
	        try {
	            in = fs.open(new Path(uri));
	            IOUtils.copyBytes(in, System.out, 4096, false); //复制到标准输出流
	            
//	 ##########################################################################	 	            
//	            System.out.println("******************************");	            
//	            System.out.println(in.getPos()); //得到输入流的当前位置
//    ##########################################################################	            
//	            System.out.println("******************************");
//	            in.seek(1);   //从指定位置读
//	            IOUtils.copyBytes(in, System.out, 4096, false);
//	  ##########################################################################	 	            	       
//	            byte[] myBuffer  = new byte[20];	
//	            in.read(5,myBuffer,0,10);
//	            System.out.println(in.read(5,myBuffer,0,10));
//	            System.out.println(new String(myBuffer));
	            
	        } finally {
	            IOUtils.closeStream(in);
	        }
	}

}
