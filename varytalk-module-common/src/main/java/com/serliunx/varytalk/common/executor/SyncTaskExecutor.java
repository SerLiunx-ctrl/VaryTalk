package com.serliunx.varytalk.common.executor;

import org.springframework.stereotype.Component;

import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

@Component
public class SyncTaskExecutor {

    private final ThreadPoolExecutor threadPoolExecutor;

    public SyncTaskExecutor() {
        this.threadPoolExecutor = new ThreadPoolExecutor(
                10,
                100,
                1000,
                TimeUnit.MILLISECONDS,
                new ArrayBlockingQueue<>(32),
                new CustomThreadFactory(),
                new ThreadPoolExecutor.CallerRunsPolicy()
        );
    }

    public void submit(Runnable runnable){
        this.threadPoolExecutor.execute(runnable);
    }
}
