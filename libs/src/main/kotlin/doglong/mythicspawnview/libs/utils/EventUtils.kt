package doglong.mythicspawnview.libs.utils

import doglong.mythicspawnview.libs.Main
import org.bukkit.Bukkit
import org.bukkit.event.Event
import org.bukkit.event.EventPriority
import org.bukkit.event.HandlerList
import org.bukkit.event.Listener
import org.bukkit.plugin.EventExecutor

object EventUtils {

    fun Listener.register() {
        Bukkit.getPluginManager().registerEvents(this, Main.getPlugin())
    }

    fun Listener.unregister() {
        HandlerList.unregisterAll(this)
    }

    /**
     * 虚拟的Listener
     */
    fun interface FakeEventListener : EventExecutor, Listener

    /**
     * 通过方法快速注册一个事件监听器
     */
    inline fun <reified T : Event> listen(
        priority: EventPriority = EventPriority.NORMAL,
        ignoreCancelled: Boolean = true,
        noinline action: T.() -> Unit
    ): FakeEventListener {
        return createListener(T::class.java, priority, ignoreCancelled, action)
    }

    /**
     * 根据class创建事件监听器
     */
    @Suppress("UNCHECKED_CAST")
    fun <E : Event> createListener(
        clazz: Class<E>,
        priority: EventPriority = EventPriority.NORMAL,
        ignoreCancelled: Boolean = true,
        action: E.() -> Unit
    ): FakeEventListener {
        val fakeEventListener = FakeEventListener { _, event ->
            runCatching {
                action.invoke(event as E)
            }.getOrElse { it.printStackTrace() }
        }
        Bukkit.getPluginManager()
            .registerEvent(
                clazz, fakeEventListener, priority,
                fakeEventListener, Main.getPlugin(), ignoreCancelled
            )
        return fakeEventListener
    }
}