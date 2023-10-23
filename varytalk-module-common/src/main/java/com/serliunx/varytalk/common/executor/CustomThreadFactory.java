package com.serliunx.varytalk.common.executor;

import java.util.concurrent.ThreadFactory;
import java.util.concurrent.atomic.AtomicInteger;

public class CustomThreadFactory implements ThreadFactory {

    private final AtomicInteger count = new AtomicInteger();

    @Override
    @SuppressWarnings("all")
    public Thread newThread(Runnable runnable) {
        String name = "task-executor-{count}".replace("{count}", String.valueOf(count.getAndIncrement()));
        return new Thread(runnable, name);
    }

    public int getCount(){
        return count.get();
    }
}
