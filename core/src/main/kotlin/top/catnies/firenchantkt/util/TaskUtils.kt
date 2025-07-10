package top.catnies.firenchantkt.util

import cn.chengzhiya.mhdfscheduler.scheduler.MHDFScheduler
import top.catnies.firenchantkt.FirEnchantPlugin
import java.util.concurrent.atomic.AtomicInteger

object TaskUtils {

    val plugin = FirEnchantPlugin.instance

    // 运行异步任务并同步执行回调
    fun runAsyncTaskLaterWithSyncCallback(async:() -> Unit, callback: () -> Unit, delay: Long = 0) {
        MHDFScheduler.getAsyncScheduler().runTaskLater( plugin, {
            async()
            MHDFScheduler.getGlobalRegionScheduler().runTask(plugin) {
                callback()
            }
        }, delay)
    }

    // 运行异步并行任务
    fun runAsyncTasksLater(delay: Long = 0, vararg tasks: () -> Unit) {
        tasks.forEach { MHDFScheduler.getAsyncScheduler().runTask(plugin, it) }
    }

    // 运行多个异步任务并在全部完成后执行同步回调
    fun runAsyncTasksWithSyncCallback(delay: Long = 0, vararg tasks: () -> Unit, callback: () -> Unit) {
        if (tasks.isEmpty()) {
            // 没任时直接回调
            MHDFScheduler.getGlobalRegionScheduler().runTask(plugin, callback)
            return
        }

        val counter = AtomicInteger(tasks.size)
        tasks.forEach { task ->
            MHDFScheduler.getAsyncScheduler().runTaskLater(plugin, {
                try {
                    task()
                }
                finally {
                    // 计数器减一并检查是否所有任务完成
                    if (counter.decrementAndGet() == 0) {
                        MHDFScheduler.getGlobalRegionScheduler().runTask(plugin, callback)
                    }
                }
            }, delay)
        }
    }
}