package doglong.mythicspawnview.libs.utils

import doglong.mythicspawnview.libs.Main
import org.bukkit.Bukkit
import java.util.concurrent.CompletableFuture

/**
 * 在主线程运行任务(使用BukkitRunnable)
 */
fun runSync(task: Runnable) = Bukkit.getScheduler().runTask(Main.getPlugin(), task)

fun runSyncTimer(task: Runnable, delay: Long, tick: Long) =  Bukkit.getScheduler().runTaskTimer(Main.getPlugin(), task, delay, tick)

fun runAsync(task: Runnable) {
    CompletableFuture.runAsync(task)
}

fun runAsyncLater(task: Runnable, delay: Long) =  Bukkit.getScheduler().runTaskLaterAsynchronously(Main.getPlugin(), task, delay)

fun runAsyncTimer(task: Runnable, delay: Long, tick: Long) =  Bukkit.getScheduler().runTaskTimerAsynchronously(Main.getPlugin(), task, delay, tick)