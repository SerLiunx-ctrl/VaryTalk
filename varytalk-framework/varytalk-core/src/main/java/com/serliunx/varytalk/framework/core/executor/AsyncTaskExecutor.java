package com.serliunx.varytalk.framework.core.executor;

import org.springframework.stereotype.Component;

import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

@Component
public class AsyncTaskExecutor {

    private final ThreadPoolExecutor threadPoolExecutor;

    public AsyncTaskExecutor() {
        this.threadPoolExecutor = new ThreadPoolExecutor(
                10,
                100,
                1000,
                TimeUnit.MILLISECONDS,
                new ArrayBlockingQueue<>(32),
                new TaskThreadFactory(),
                new ThreadPoolExecutor.CallerRunsPolicy()
        );
    }

    public void submit(Runnable runnable){
        this.threadPoolExecutor.execute(runnable);
    }
}
