package org.zgg.leetcode.Graph;

import java.util.LinkedList;
import java.util.List;
import java.util.Queue;

/*
* 841. Keys and Rooms
* */
public class test1 {
    public boolean canVisitAllRooms(List<List<Integer>> rooms){

        //bfs
        boolean[] marked = new boolean[rooms.size()];
        Queue<Integer> q = new LinkedList<>();
        q.offer(0);
        marked[0]=true;
        int size = 0;
        while(!q.isEmpty()){
            Integer tmp = q.poll();
            size++;

            List<Integer> keys = rooms.get(tmp);
            for(int k:keys){
                if(marked[k]) continue;
                marked[k]=true;
                q.offer(k);
            }
        }
        return size == rooms.size();
    }
}
