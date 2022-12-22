package doglong.mythicspawnview.core.listener

import doglong.mythicspawnview.MythicSpawnView
import doglong.mythicspawnview.core.task.MythicSpawnerUpdateTask
import doglong.mythicspawnview.libs.utils.runAsync
import io.lumine.xikage.mythicmobs.api.bukkit.events.MythicReloadedEvent
import org.bukkit.event.EventHandler
import org.bukkit.event.Listener

object MythicMobsListener : Listener {

    @EventHandler
    fun on(e: MythicReloadedEvent) {
        runAsync{
            MythicSpawnView.starting()
            MythicSpawnerUpdateTask.clear()
        }
    }

}