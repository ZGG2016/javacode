
## hadoop.sequencefile.SequenceFileTest类

（1）需求：

先从 `/in/sf` 读取两个小文件，以 SequenceFile 的形式写入到 `/out/sfile` 中。

再从 `/out/sfile` 读取出数据

（2）集群中执行：

    hadoop jar sf.jar hadoop.sequencefile.SequenceFileTest

（3）写入完后，打开输出目录，得到：

```sh
[root@zgg jar]# hadoop fs -text /out/sfile
2020-12-27 22:07:53,199 INFO sasl.SaslDataTransferClient: SASL encryption trust check: localHostTrusted = false, remoteHostTrusted = false
2020-12-27 22:07:53,282 INFO zlib.ZlibFactory: Successfully loaded & initialized native-zlib library
2020-12-27 22:07:53,282 INFO compress.CodecPool: Got brand-new decompressor [.deflate]
hdfs://zgg:9000/in/sf/secondsort.txt    3       3
3       2
3       1
2       2
2       1
1       1
1       1
2       1
2       2
3       1
3       2
3       3

hdfs://zgg:9000/in/sf/stjoin.txt        child parent
Tom Jack
Jack Alice
Jack Jesse
```

（4）输出为：

```sh
[root@zgg jar]# hadoop jar sf.jar hadoop.sequencefile.SequenceFileTest
2020-12-27 22:03:55,556 INFO zlib.ZlibFactory: Successfully loaded & initialized native-zlib library
2020-12-27 22:03:55,557 INFO compress.CodecPool: Got brand-new compressor [.deflate]
the file name is hdfs://zgg:9000/in/sf/secondsort.txt
2020-12-27 22:03:55,612 INFO sasl.SaslDataTransferClient: SASL encryption trust check: localHostTrusted = false, remoteHostTrusted = false
the file name is hdfs://zgg:9000/in/sf/stjoin.txt
2020-12-27 22:03:55,676 INFO sasl.SaslDataTransferClient: SASL encryption trust check: localHostTrusted = false, remoteHostTrusted = false
2020-12-27 22:03:55,707 INFO sasl.SaslDataTransferClient: SASL encryption trust check: localHostTrusted = false, remoteHostTrusted = false
2020-12-27 22:03:56,225 INFO sasl.SaslDataTransferClient: SASL encryption trust check: localHostTrusted = false, remoteHostTrusted = false
2020-12-27 22:03:56,236 INFO compress.CodecPool: Got brand-new decompressor [.deflate]
key:hdfs://zgg:9000/in/sf/secondsort.txt
value:3 3
3       2
3       1
2       2
2       1
1       1
1       1
2       1
2       2
3       1
3       2
3       3

-------------------------------
key:hdfs://zgg:9000/in/sf/stjoin.txt
value:child parent
Tom Jack
Jack Alice
Jack Jesse

-------------------------------
```
