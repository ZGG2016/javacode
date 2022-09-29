package opt_hdfs;

import org.apache.hadoop.conf.Configuration;
import org.apache.hadoop.fs.FileSystem;
import org.apache.hadoop.fs.Path;
import org.apache.log4j.BasicConfigurator;

/*
 * 删除文件
 * 
 * */
public class DeleteFile {

    public static void main(String[] args) throws Exception {
    	BasicConfigurator.configure(); 
    	
        Configuration conf = new Configuration();
        Path path = new Path("hdfs://zgg/test/1.txt");
        FileSystem fs =path.getFileSystem(conf);
        if(fs.exists(path)){
            fs.delete(path,true);
        }
        System.out.println("删除成功");
    }
}
