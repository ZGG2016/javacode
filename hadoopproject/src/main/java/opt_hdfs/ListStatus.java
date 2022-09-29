package opt_hdfs;

import org.apache.hadoop.conf.Configuration;
import org.apache.hadoop.fs.FileStatus;
import org.apache.hadoop.fs.FileSystem;
import org.apache.hadoop.fs.FileUtil;
import org.apache.hadoop.fs.Path;
import org.apache.log4j.BasicConfigurator;

import java.net.URI;

/*
 * listStatus:列出给定路径下的文件的状态
 *            也可以只列出给定路径下的文件
 *            
 *  不同于listFiles,不能递归列出
 * 
 * */
public class ListStatus {
	
	public static void main(String[] args) throws Exception {
		BasicConfigurator.configure(); 
		
        Configuration conf = new Configuration();
        String uri="hdfs://zgg:9000/";
        FileSystem fs = FileSystem.get(URI.create(uri), conf);
        
        Path[] paths = new Path[args.length];
        
        for(int i=0;i<paths.length;i++) {
        	paths[i] = new Path(args[i]);  //往path数组添加元素
        }
        
        FileStatus[] status = fs.listStatus(paths);
        //列出给定路径下的文件的状态
        Path[] listedPaths = FileUtil.stat2Paths(status);
        for(Path p : listedPaths) {
        	System.out.println(p);
        }
        //列出给定路径下的文件
//        for(FileStatus f:status) {
//        	System.out.println(f);
//        }
        
        fs.close();
	}
}
