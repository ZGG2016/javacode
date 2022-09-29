### TopK问题

#### 1、数据格式

topk 文件夹下有 10 个子文件夹 file*，各个子文件夹下各有一个文件 file*.txt

注意：存在两个url出现的次数相同的情况。

```
[root@zgg data]# hadoop fs -ls -R /in/topk
drwxr-xr-x   - root supergroup          0 2020-11-22 17:29 /in/topk/file0
-rw-r--r--   1 root supergroup         58 2020-11-22 17:29 /in/topk/file0/file0.txt
drwxr-xr-x   - root supergroup          0 2020-11-22 17:29 /in/topk/file1
-rw-r--r--   1 root supergroup         58 2020-11-22 17:29 /in/topk/file1/file1.txt
drwxr-xr-x   - root supergroup          0 2020-11-22 17:29 /in/topk/file2
-rw-r--r--   1 root supergroup         58 2020-11-22 17:29 /in/topk/file2/file2.txt
drwxr-xr-x   - root supergroup          0 2020-11-22 17:29 /in/topk/file3
-rw-r--r--   1 root supergroup         58 2020-11-22 17:29 /in/topk/file3/file3.txt
drwxr-xr-x   - root supergroup          0 2020-11-22 17:29 /in/topk/file4
-rw-r--r--   1 root supergroup         58 2020-11-22 17:29 /in/topk/file4/file4.txt
drwxr-xr-x   - root supergroup          0 2020-11-22 17:29 /in/topk/file5
-rw-r--r--   1 root supergroup         58 2020-11-22 17:29 /in/topk/file5/file5.txt
drwxr-xr-x   - root supergroup          0 2020-11-22 17:29 /in/topk/file6
-rw-r--r--   1 root supergroup         58 2020-11-22 17:29 /in/topk/file6/file6.txt
drwxr-xr-x   - root supergroup          0 2020-11-22 17:29 /in/topk/file7
-rw-r--r--   1 root supergroup         58 2020-11-22 17:29 /in/topk/file7/file7.txt
drwxr-xr-x   - root supergroup          0 2020-11-22 17:29 /in/topk/file8
-rw-r--r--   1 root supergroup         58 2020-11-22 17:29 /in/topk/file8/file8.txt
drwxr-xr-x   - root supergroup          0 2020-11-22 17:29 /in/topk/file9
-rw-r--r--   1 root supergroup         58 2020-11-22 17:29 /in/topk/file9/file9.txt

[root@zgg data]# hadoop fs -cat /in/topk/file0/file0.txt
url0
url1
url2
url3
url4
url5
url6
url7
url8
url9
```

#### 2、需求

统计出现次数前 5 名 url。

#### 3、方法

先统计各url出现的次数

- solution1：根据url和对应的次数定义一个新key，利用二次排序，取出topk。

- solution2：根据url的次数，定义一个新key，将元素降序排列，然后将key[url]和value[次数]交换，根据新key数据类型和旧的value[次数]，作为key，利用排序特性，取出topk

【以上：取前k或者取后k，可以通过改变新key中的compareTo方法来实现】

- solution3：利用TreeMap，先取每个分片(mapper任务)中的topk，再取全局的topk。由于将次数作为键，而TreeMap的键是不能重复的，所以对于存在两个url出现的次数相同的情况，无法处理。

【取前k或者取后k，可通过自定义比较器实现】【注意：setup、cleanup、map方法和mapper任务(分片)的关系】

- solution4：建hive表A，挂分区channel，每个文件夹是一个分区. 

```sql
select x.url,x.c from
(select url,count(1) as c 
	from A where channel ='' group by url) x 
order by x.c desc limit 1000000; 
```

参考：[https://blog.csdn.net/liushahe2012/article/details/68189837](https://blog.csdn.net/liushahe2012/article/details/68189837)