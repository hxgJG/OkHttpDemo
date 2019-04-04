package com.hexg.okhttpdemo.http.manager;

import android.util.Log;

import com.hexg.okhttpdemo.http.bean.HttpTask;

import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.DelayQueue;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.RejectedExecutionHandler;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

public class ThreadPoolManager {

    private static ThreadPoolManager instance;
    // create queue ---> first in, first out
    private LinkedBlockingQueue<Runnable> queue = new LinkedBlockingQueue<>();

    // create thread pool
    private ThreadPoolExecutor executor;

    private ThreadPoolManager() {
        executor = new ThreadPoolExecutor(3, 10, 15,
                TimeUnit.SECONDS, new ArrayBlockingQueue<Runnable>(4), new RejectedExecutionHandler() {
            @Override
            public void rejectedExecution(Runnable r, ThreadPoolExecutor executor) {
                addTask(r);
            }
        });

        // create Runnable
        Runnable coreThread = new Runnable() {
            Runnable runnable = null;

            @Override
            public void run() {
                while (true) {
                    try {
                        runnable = queue.take();
                        executor.execute(runnable);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
            }
        };
        executor.execute(coreThread);

        Runnable delayThread = new Runnable() {
            HttpTask task = null;

            @Override
            public void run() {
                while (true) {
                    try {
                        task = delayQueue.take();
                        int retryCount = task.getRetryCount();
                        if (retryCount < 3) {
                            executor.execute(task);
                            task.setRetryCount(retryCount + 1);
                            Log.w("hxg", "retry count = " + task.getRetryCount());
                        } else {
                            Log.w("hxg", "retry failed, not retry again");
                        }
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
            }
        };
        executor.execute(delayThread);
    }

    public static ThreadPoolManager getInstance() {
        if (instance == null) {
            synchronized (ThreadPoolManager.class) {
                if (instance == null) {
                    instance = new ThreadPoolManager();
                }
            }
        }
        return instance;
    }

    // add async task to queue
    public void addTask(Runnable runnable) {
        if (runnable != null) {
            try {
                queue.put(runnable);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }

    //  创建延迟队列 (重试机制要延迟一定的时间)
    private DelayQueue<HttpTask> delayQueue = new DelayQueue<>();

    // add failed task to delayQueue
    public void addDelayTask(HttpTask ht){
        if (ht != null) {
            ht.setDelayTime(3000);
            delayQueue.offer(ht);
        }
    }
}
