package com.example.a531app.architecture;

import android.os.Handler;
import android.os.Looper;
import java.util.concurrent.Executor;
import java.util.concurrent.Executors;

public class AppExecutors {

    private final Executor mDiskIO;

    private final Executor mNetworkIO;

    private final Executor mMainThread;

    private static AppExecutors executorInstance;

    private static final Object LOCK = new Object();

    private AppExecutors(Executor diskIO, Executor networkIO, Executor mainThread) {
        this.mDiskIO = diskIO;
        this.mNetworkIO = networkIO;
        this.mMainThread = mainThread;
    }

    public static AppExecutors getInstance() {
        if(executorInstance==null){
            synchronized (LOCK){
                executorInstance = new AppExecutors(Executors.newSingleThreadExecutor(), Executors.newFixedThreadPool(3),
                        new MainThreadExecutor());
            }
        }

        return executorInstance;
    }

    public Executor diskIO() {
        return mDiskIO;
    }

    public Executor networkIO() {
        return mNetworkIO;
    }

    public Executor mainThread() {
        return mMainThread;
    }

    private static class MainThreadExecutor implements Executor {
        private Handler mainThreadHandler = new Handler(Looper.getMainLooper());

        @Override
        public void execute(Runnable command) {
            mainThreadHandler.post(command);
        }


    }
}
