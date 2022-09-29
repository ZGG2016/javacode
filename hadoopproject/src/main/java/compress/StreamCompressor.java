package compress;

import org.apache.hadoop.conf.Configuration;
import org.apache.hadoop.io.IOUtils;
import org.apache.hadoop.io.compress.CompressionCodec;
import org.apache.hadoop.io.compress.CompressionOutputStream;
import org.apache.hadoop.util.ReflectionUtils;
import org.apache.log4j.BasicConfigurator;

import java.io.IOException;

/*
* 压缩从标准输入读取的数据,并写到标准输出
* 没跑通？
* */
public class StreamCompressor {
    public static void main(String[] args) throws ClassNotFoundException, IOException {
        BasicConfigurator.configure();

        String codecClassname = args[0];
//        String codecClassname = "org.apache.hadoop.io.compress.BZip2Codec";
        Class<?> codecClass = Class.forName(codecClassname);
        Configuration conf = new Configuration();
        CompressionCodec codec = (CompressionCodec) ReflectionUtils.newInstance(codecClass, conf);

        CompressionOutputStream out = codec.createOutputStream(System.out);
        // out 从 System.in 取到数据，压缩后，输出到  System.out
        IOUtils.copyBytes(System.in, out, 4096, false);
        out.finish();
    }
}
