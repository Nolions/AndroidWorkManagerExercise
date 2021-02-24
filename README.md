# WorkManagerExercise

## Describe

Android WorkManager 練習

## API

## WorkRequest

| 物件| 說明 |
| ----------- | -----------|
| OneTimeWorkRequest | 只執行一次工作 |
| PeriodicWorkRequest |定期工作|

***Create One Time Work Request***

```
val oneWorkRequest = OneTimeWorkRequestBuilder<MyWork>().build()
```

***Create Periodic Work Request***

```
val periodicWorkRequest = PeriodicWorkRequestBuilder<MyWork>(15, TimeUnit.MINUTES)
    // Additional configuration
    .build()
```


## Constraints Worker 觸發條件

setConstraints()

| 參數| 說明 |
| ----------- | -----------|
| NetworkType | 允許執行網路狀態 |
| BatteryNotLow | 當BatteryNotLow設為true時，則電量不足時不執行 |
| RequiresCharging | 當RequiresCharging設為true時，則充電狀態下才執行 |
| DeviceIdle | 當DeviceIdle設為true時，狀態在閒置狀態下下執行 |
| StorageNotLow | 當StorageNotLow設為true時，裝置儲存空間不足時不執行 |

```
// 透過WIFI連結網路，並且連結充電器狀態下才觸發
Constraints.Builder()
    .setRequiredNetworkType(NetworkType.UNMETERED) // 網路狀態
    .setRequiresCharging(true) // 充電
    .build()
```

### NetworkType

| 參數| 說明 |
| ----------- | -----------|
| CONNECTED | 沒有要求 |
| METERED | 網路啟用 |
| NOT_REQUIRED | 沒有網路 |
| NOT_ROAMING | 使用行動數據網路|
| UNMETERED | 使用Wi-Fi網路 |


##  重新執行策略

setBackoffCriteria(BackoffPolicy, long, TimeUnit)

| 參數| 說明 |
| ----------- | -----------|
| backoffPolicyaddTag | 重新執行策略 |
| backoffDelay | 延遲時間 |
| timeUnit | 時間單位 |

## REFERENCE

1. [使用 WorkManager 调度任务](https://developer.android.com/topic/libraries/architecture/workmanager)
2. [WorkManager: 週期性任務](https://www.mdeditor.tw/pl/pFYr/zh-tw)
