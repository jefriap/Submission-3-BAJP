package com.submission.filmcatalogue.utils

import android.os.Handler
import android.os.Looper
import androidx.annotation.VisibleForTesting
import java.util.concurrent.Executor
import java.util.concurrent.Executors

open class AppExecutors @VisibleForTesting constructor(
    private val diskIO: Executor,
    private val mainThread: Executor
) {
    constructor() : this(DiskIOThreadExecutor(), MainThreadExecutor())

    fun diskIO() : Executor {
        return  diskIO
    }

    fun mainThread(): Executor {
        return mainThread
    }

    private class DiskIOThreadExecutor : Executor {

        private val diskIO: Executor
        init {
            diskIO = Executors.newSingleThreadExecutor()
        }
        override fun execute(p0: Runnable) {
            diskIO.execute(p0)
        }
    }

    private class MainThreadExecutor: Executor {
        private val threadHandler = Handler(Looper.getMainLooper())

        override fun execute(p0: Runnable) {
            threadHandler.post(p0)
        }
    }
}