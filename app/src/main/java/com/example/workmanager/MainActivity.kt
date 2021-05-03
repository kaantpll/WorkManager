package com.example.workmanager

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.constraintlayout.solver.state.State
import androidx.constraintlayout.widget.ConstraintSet
import androidx.work.*
import java.util.concurrent.TimeUnit

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)


       // simpleWork()

        myWorkManager()
    }

    private fun simpleWork(){
        val mRequest : WorkRequest = OneTimeWorkRequestBuilder<MyWorker>()
                .build()

        WorkManager.getInstance(this)
                .enqueue(mRequest)

    }

    private fun myWorkManager(){
        val constraints = Constraints.Builder()
                .setRequiresCharging(false)
                .setRequiredNetworkType(NetworkType.NOT_REQUIRED)
                .setRequiresBatteryNotLow(true)
                .build()

        val myRequest = PeriodicWorkRequest.Builder(
                MyWorker::class.java,
                15,
                TimeUnit.MINUTES
        ).setConstraints(constraints)
                .build()
        WorkManager.getInstance(this)
                .enqueueUniquePeriodicWork(
                        "my_id",
                        ExistingPeriodicWorkPolicy.KEEP,
                        myRequest
                )

    }
}