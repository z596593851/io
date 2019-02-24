package com.hxm.netty;

import java.util.Queue;
import java.util.concurrent.ConcurrentLinkedQueue;

public class test {
    public static void main(String [] args){
        Queue<Runnable> taskQueue = new ConcurrentLinkedQueue<Runnable>();
        for(;;){
            Runnable task = taskQueue.poll();
            if(task==null)
                break;
            task.run();
        }

    }
}
