package doglong.mythicspawnview.core.task

import com.gmail.filoghost.holographicdisplays.api.Hologram
import com.gmail.filoghost.holographicdisplays.api.HologramsAPI
import com.gmail.filoghost.holographicdisplays.api.line.TextLine
import doglong.mythicspawnview.MythicSpawnView
import doglong.mythicspawnview.core.config.RootConfig
import doglong.mythicspawnview.core.config.SpawnsConfig
import doglong.mythicspawnview.libs.IActive
import doglong.mythicspawnview.libs.utils.StringUtils.toColor
import doglong.mythicspawnview.libs.utils.StringUtils.toFormat
import doglong.mythicspawnview.libs.utils.runAsync
import doglong.mythicspawnview.libs.utils.runSyncTimer
import io.lumine.xikage.mythicmobs.MythicMobs
import io.lumine.xikage.mythicmobs.adapters.bukkit.BukkitAdapter
import org.bukkit.plugin.Plugin

object MythicSpawnerUpdateTask: IActive {

    private var lock = false
    private var running = false
    private var holograms = mutableMapOf<String, Hologram>()

    fun update() {

        if (!lock && !running) {
            running = true
            SpawnsConfig.spawns.entries.forEach {
                val spawner = MythicMobs.inst().spawnerManager.getSpawnerByName(it.key)
                val setting = it.value
                if (spawner != null) {
                    val hologram = holograms.computeIfAbsent(spawner.internalName) {
                        val location = spawner.location
                        location.add(setting.x, setting.y, setting.z)
                        HologramsAPI.createHologram(
                            MythicSpawnView.plugin,
                            BukkitAdapter.adapt(location)
                        )
                    }
                    RootConfig.descriptionList.forEachIndexed { i: Int, s: String ->
                        val text = s.toFormat(
                            spawner.internalName,
                            spawner.typeName,
                            if (spawner.isOnWarmup) spawner.remainingWarmupSeconds else RootConfig.placeholderNoWarmup
                        ).toColor()
                        val textLine: TextLine
                        try {
                            textLine = hologram.getLine(i) as TextLine
                            textLine.text = text
                        }catch (_: Exception) {
                            try {
                                hologram.insertTextLine(i, text)
                            }catch (_: Exception) {
                            }
                        }
                    }
                }
            }
            running = false
        }
    }

    fun clear() {
        runAsync {
            val time = System.currentTimeMillis()
            MythicSpawnView.log("&6开始重新清除缓存...")
            lock = true
            while (running) {
            }
            holograms.entries.forEach { it.value.delete() }
            holograms.clear()
            lock = false
            MythicSpawnView.log("&6插件缓存已清除，耗时: {0}".toFormat(System.currentTimeMillis() - time))
        }
    }

    override fun init(plugin: Plugin) {
        runSyncTimer({ update() }, 20, 20)
    }

    override fun starting(plugin: Plugin) {
    }

    override fun stopping(plugin: Plugin) {
        clear()
    }
}