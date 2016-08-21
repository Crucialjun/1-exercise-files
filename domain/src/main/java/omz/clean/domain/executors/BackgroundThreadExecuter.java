/*
 *
 * Copyright (C) 2016. OMRI EREZ
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package omz.clean.domain.executors;

import java.util.concurrent.Executor;
import java.util.concurrent.PriorityBlockingQueue;
import java.util.concurrent.RejectedExecutionHandler;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

import omz.clean.domain.utils.LogUtils;

/**
 * Created by omrierez on 07/08/16.
 */

public class BackgroundThreadExecuter implements Executor {

    private static final String TAG = "BackgroundThreadExecute";
    private static final short KEEP_ALIVE = 1000;
    private static final short MIN_NUMBER_OF_THREADS = 2;
    private static final short MAX_NUMBER_OF_THREADS = 10;

    private RejectedExecutionHandler mRejectedHandler = new RejectedExecutionHandelerImpl();
    private PriorityBlockingQueue mPriorityQueue = new PriorityBlockingQueue();
    private ThreadPoolExecutor mExecutor = new ThreadPoolExecutor(MIN_NUMBER_OF_THREADS, MAX_NUMBER_OF_THREADS,
            KEEP_ALIVE, TimeUnit.MILLISECONDS, mPriorityQueue, mRejectedHandler);

    @Override
    public void execute(Runnable job) {
        mExecutor.execute(job);
    }


    private static class RejectedExecutionHandelerImpl implements RejectedExecutionHandler {
        @Override
        public void rejectedExecution(Runnable runnable,
                                      ThreadPoolExecutor executor) {
            LogUtils.logInfo(TAG, "Runnable was rejected");
        }
    }

}

