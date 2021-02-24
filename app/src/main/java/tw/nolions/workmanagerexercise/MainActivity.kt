package tw.nolions.workmanagerexercise

import android.os.Bundle
import android.util.Log
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.work.*
import kotlinx.android.synthetic.main.activity_main.*
import java.util.concurrent.TimeUnit

class MainActivity : AppCompatActivity(), View.OnClickListener {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val workRequest = periodicWorker()
            .setConstraints(setConstraints())
            .setBackoffCriteria(BackoffPolicy.LINEAR, 30, TimeUnit.SECONDS)
            .addTag(FirstWork.tag())
            .build()

        registeredWorkManager(workRequest)

        cancelWorkerBtn.setOnClickListener(this)
        getWorkerBtn.setOnClickListener(this)
    }

    override fun onClick(view: View?) {
        when (view!!.id) {
            R.id.cancelWorkerBtn -> cancelWorker(FirstWork.tag())
            R.id.getWorkerBtn -> getWorker(FirstWork.tag())
        }
    }

    private fun periodicWorker() = PeriodicWorkRequestBuilder<FirstWork>(15, TimeUnit.MINUTES)

    private fun oneTimeWorker() = OneTimeWorkRequestBuilder<FirstWork>()

    /**
     * 系統觸發條件
     *
     * =========================
     * NetworkType 網路狀態
     * BatteryNotLow 電量
     * RequiresCharging 充電
     * DeviceIdle 閒置狀態
     * StorageNotLow 儲存空間
     */
    private fun setConstraints(): Constraints {
        return Constraints.Builder()
            .setRequiredNetworkType(NetworkType.UNMETERED) // 網路狀態
            .setRequiresCharging(true) // 充電
            .build()
    }

    /**
     * 向WorkManager註冊worker
     *
     * @param workRequest
     */
    private fun registeredWorkManager(workRequest: WorkRequest) {
        when (workRequest) {
            is PeriodicWorkRequest -> {
                WorkManager.getInstance(this).enqueueUniquePeriodicWork(
                    FirstWork.tag(),
                    ExistingPeriodicWorkPolicy.REPLACE,
                    workRequest as PeriodicWorkRequest
                )
            }
            else -> {
                WorkManager.getInstance(this).enqueue(workRequest)
            }
        }
    }

    /**
     * 取消Worker
     *
     */
    private fun cancelWorker(tag: String) {
        Log.d("FirstWork", "cancelWorker")
        WorkManager.getInstance(this).cancelAllWorkByTag(tag)
    }

    /**
     * 取得Worker
     *
     */
    private fun getWorker(tag: String) {
        Log.d("FirstWork", "getWorker")
        val info = WorkManager.getInstance(this).getWorkInfosByTag(tag)
        Log.d("FirstWork", "Worker Info: $info")
    }
}