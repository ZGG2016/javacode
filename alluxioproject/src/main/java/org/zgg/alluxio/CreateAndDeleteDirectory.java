package org.zgg.alluxio;

import alluxio.AlluxioURI;
import alluxio.client.file.FileSystem;

// 如果执行出现权限、用户的问题，那么就修改文件或目录权限，或环境变量中添加alluxio环境变量
public class CreateAndDeleteDirectory {

    private static final FileSystem FS = FileSystem.Factory.get();

    public static void main(String[] args) throws Exception {

        String spath = "/test/test1/";
        createDirectory(spath);
    }

    private static void createDirectory(String spath) throws Exception{

        AlluxioURI path = new AlluxioURI(spath);
        FS.createDirectory(path);
        System.out.println("Done!!!");
    }

    private static void deleteDirectory(String spath) throws Exception{

        AlluxioURI path = new AlluxioURI(spath);
        FS.delete(path);
        System.out.println("Done!!!");
    }
}
