package doglong.mythicspawnview.core.listener

import doglong.mythicspawnview.core.task.MythicSpawnerUpdateTask
import io.lumine.xikage.mythicmobs.api.bukkit.events.MythicReloadedEvent
import org.bukkit.event.EventHandler
import org.bukkit.event.Listener

object MythicMobsListener : Listener {

    @EventHandler
    fun on(e: MythicReloadedEvent) {
        MythicSpawnerUpdateTask.clear()
    }

}