package custompartition;

import org.apache.hadoop.io.Text;
import org.apache.hadoop.mapreduce.lib.partition.HashPartitioner;

public class KpiPartitioner extends HashPartitioner<Text, KpiWritable> {
    @Override
    public int getPartition(Text key, KpiWritable value, int numReduceTasks) {
        return (key.toString().length() == 11) ? 0 : 1;
    }
}

