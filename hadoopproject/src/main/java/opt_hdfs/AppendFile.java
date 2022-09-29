package opt_hdfs;

import org.apache.hadoop.conf.Configuration;
import org.apache.hadoop.fs.FileSystem;
import org.apache.hadoop.fs.Path;
import org.apache.hadoop.io.IOUtils;

import java.io.*;
import java.net.URI;

/**
 * 往hdfs追加写数据
 */
public class AppendFile {
    public static void main(String[] args) {
        String hdfs_path = "hdfs://zgg:9000/in/wc.txt";//文件路径
        Configuration conf = new Configuration();
        conf.setBoolean("dfs.support.append", true);

        String inpath = "src/main/resources/bos.txt";
        FileSystem fs = null;
        try {
            fs = FileSystem.get(URI.create(hdfs_path), conf);
            //要追加的文件流，inpath为文件
            InputStream in = new
                    BufferedInputStream(new FileInputStream(inpath));
            OutputStream out = fs.append(new Path(hdfs_path));
            IOUtils.copyBytes(in, out, 4096, true);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
