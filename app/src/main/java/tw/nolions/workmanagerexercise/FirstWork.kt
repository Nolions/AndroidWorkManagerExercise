package tw.nolions.workmanagerexercise

import android.content.Context
import android.util.Log
import androidx.work.Worker
import androidx.work.WorkerParameters

class FirstWork(context: Context, workerParams: WorkerParameters) : Worker(context, workerParams) {

    companion object {
        fun tag() = "first"
    }

    override fun doWork(): Result {
        Log.d("FirstWork", "doWork")
        Number.i++
        if (Number.i == 1 ) {
            Log.d("FirstWork", "doWork, retry")
            return Result.retry()
        }

        Log.d("FirstWork", "Number is ${Number.i}")
        Log.d("FirstWork", "doWork, success")
        return Result.success()
    }
}