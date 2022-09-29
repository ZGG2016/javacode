package compress;

import org.apache.hadoop.conf.Configuration;
import org.apache.hadoop.io.IOUtils;
import org.apache.hadoop.io.compress.CodecPool;
import org.apache.hadoop.io.compress.CompressionCodec;
import org.apache.hadoop.io.compress.CompressionOutputStream;
import org.apache.hadoop.io.compress.Compressor;
import org.apache.hadoop.util.ReflectionUtils;
import org.apache.log4j.BasicConfigurator;
/*
* 使用压缩池对读取自标准输入的数据进行压缩,然后将其写到标准输出
*
* */

public class PooledStreamCompressor {
    public static void main(String[] args) throws Exception {
        BasicConfigurator.configure();

//        String codecClassname = args[0];
        String codecClassname = "org.apache.hadoop.io.compress.BZip2Codec";

        Class<?> codecClass = Class.forName(codecClassname);

        Configuration conf = new Configuration();
        CompressionCodec codec = (CompressionCodec)
                ReflectionUtils.newInstance(codecClass, conf);

        Compressor compressor = null;
        try {
            //对于指定的CompressionCodec，从池中获取一个压缩器
            compressor = CodecPool.getCompressor(codec);
            CompressionOutputStream out =
                    codec.createOutputStream(System.out, compressor);
            IOUtils.copyBytes(System.in, out, 4096, false);
            out.finish();
            //通过finally数据块，可以在不同数据流间来回复制数据，即使出现IOException，也能保证compressor返回到池中
            } finally {
            CodecPool.returnCompressor(compressor);
        }
    }
}
