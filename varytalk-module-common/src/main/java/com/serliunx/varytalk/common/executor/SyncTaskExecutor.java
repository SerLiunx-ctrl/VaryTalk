package com.serliunx.varytalk.common.executor;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

@Component
public class SyncTaskExecutor {

    private final ThreadPoolExecutor threadPoolExecutor;
    private final Logger logger = LoggerFactory.getLogger(SyncTaskExecutor.class);

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
        logger.info("异步任务执行器初始化完成...");
    }

    public void submit(Runnable runnable){
        this.threadPoolExecutor.execute(runnable);
    }
}
