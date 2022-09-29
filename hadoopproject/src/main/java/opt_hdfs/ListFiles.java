package opt_hdfs;

import org.apache.hadoop.conf.Configuration;
import org.apache.hadoop.fs.FileSystem;
import org.apache.hadoop.fs.LocatedFileStatus;
import org.apache.hadoop.fs.Path;
import org.apache.hadoop.fs.RemoteIterator;
import org.apache.log4j.BasicConfigurator;

import java.io.IOException;
import java.net.URI;

/*
 * 查看目录下的内容、上传文件、下载文件、删除文件、
	移动（复制）文件

 * listFiles:列出给定路径下的文件的状态和块的位置
 * */
public class ListFiles {

	public static void main(String[] args) throws IOException{
		BasicConfigurator.configure(); 
		
		Configuration conf = new Configuration();
		
		String uri="hdfs://zgg:9000/";
        FileSystem fs = FileSystem.get(URI.create(uri), conf);
        /*
         * 如果path是一个目录，那么
	     *    如果true,递归返回该目录下的文件和文件夹
	     *    如果false,返回该目录下的文件和文件夹
         * 如果path是一个文件，那么就列出给定路径下的文件的状态和块的位置.true or false得到的结果是一样的。
         * */
        
        //迭代器，集合的元素需要远程获取
        //path是一个目录
        RemoteIterator<LocatedFileStatus> myFiles1=fs.listFiles(new Path(uri+"user"), true);
        
        while(myFiles1.hasNext()) {
        	LocatedFileStatus myFileStatus=myFiles1.next();
        	System.out.println(myFileStatus);
        }
        
        System.out.println("--------------------------------------------------");
        
        //path是一个文件
        RemoteIterator<LocatedFileStatus> myFiles2=fs.listFiles(new Path(uri+"test/1.txt"), true);
        
        while(myFiles2.hasNext()) {
        	LocatedFileStatus myFileStatus=myFiles2.next();
        	System.out.println(myFileStatus);
        }
        
        fs.close();
	}
	
}
