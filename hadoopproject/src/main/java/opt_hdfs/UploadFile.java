package opt_hdfs;

import org.apache.hadoop.conf.Configuration;
import org.apache.hadoop.fs.FileSystem;
import org.apache.hadoop.fs.Path;
import org.apache.log4j.BasicConfigurator;

import java.io.IOException;
import java.net.URI;

public class UploadFile {

	public static void main(String[] args) throws IOException {
		
		BasicConfigurator.configure(); 
		Configuration conf = new Configuration();
		
        String uri="hdfs://zgg:9000/";  // core-site.xml文件中配置fs.defaultFS
        String target = uri+"test";  //指定目的地的目录
        String local = "/data/1.txt";   //指定源文件的位置
        //创建一个FileSystem对象，这个对象能访问HDFS(被授权)
        FileSystem fs = FileSystem.get(URI.create(uri), conf);
        fs.copyFromLocalFile(new Path(local), new Path(target));
        System.out.println("上传成功");
        fs.close();
	}
}
