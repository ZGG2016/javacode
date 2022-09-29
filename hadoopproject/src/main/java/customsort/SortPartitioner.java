package customsort;

import org.apache.hadoop.io.IntWritable;
import org.apache.hadoop.mapreduce.Partitioner;

public class SortPartitioner extends Partitioner<TextInt, IntWritable> {
    @Override
    public int getPartition(TextInt textInt, IntWritable intWritable, int numPartitions) {
        return textInt.getStr().hashCode() & Integer.MAX_VALUE % numPartitions;

    }
}
