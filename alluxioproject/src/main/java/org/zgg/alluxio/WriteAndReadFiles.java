package org.zgg.alluxio;

import alluxio.AlluxioURI;
import alluxio.client.file.FileInStream;
import alluxio.client.file.FileOutStream;
import alluxio.client.file.FileSystem;

// 如果执行出现权限、用户的问题，那么就修改文件目录权限，或环境变量中添加alluxio环境变量
public class WriteAndReadFiles {
    private static final FileSystem FS = FileSystem.Factory.get();

    public static void main(String[] args) throws Exception {

        String spath = "/in/test.txt";
        WriteFile(spath);
    }

    private static void WriteFile(String spath) throws Exception {

        AlluxioURI path = new AlluxioURI(spath);
        FileOutStream out = FS.createFile(path);
        out.write("this is test ".getBytes());

        out.close();

    }

    private static void ReadFile(String spath) throws Exception {

        AlluxioURI path = new AlluxioURI(spath);
        FileInStream in = FS.openFile(path);
        byte[] buffer = new byte[1024];
        for (int len = 0; (len = in.read(buffer)) != -1; ) {
            String data = new String(buffer, 0, len);
            System.out.println(data);
        }

        in.close();
    }
}


