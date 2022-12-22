package doglong.mythicspawnview.core.config

import doglong.mythicspawnview.libs.IActive
import org.bukkit.configuration.file.YamlConfiguration
import org.bukkit.plugin.Plugin
import java.io.File

object SpawnsConfig: IActive {

    var defSetting = SpawnSetting(0.0, 0.0, 0.0)
    var spawns = mutableMapOf<String, SpawnSetting>()

    override fun init(plugin: Plugin) {
    }

    override fun starting(plugin: Plugin) {
        val file = File(plugin.dataFolder, "spawns.yml").apply {
            if (!this.exists()) {
                plugin.saveResource("spawns.yml", true)
            }
        }
        this.spawns = mutableMapOf()
        val yaml = YamlConfiguration.loadConfiguration(file)
        this.defSetting = SpawnSetting(yaml.getDouble("default.offsetX"), yaml.getDouble("default.offsetY"), yaml.getDouble("default.offsetZ"))
        for (key in yaml.getKeys(false)) {
            if (key.equals("default")) {
                continue
            }
            val section = yaml.getConfigurationSection(key)
            section?.getStringList("spawns")?.forEach {
                this.spawns[it] = SpawnSetting(
                    section.getDouble("offsetX"),
                    section.getDouble("offsetY"),
                    section.getDouble("offsetZ")
                )
            }
        }
    }

    override fun stopping(plugin: Plugin) {
    }


    class SpawnSetting(var x: Double, var y: Double, var z: Double)
}