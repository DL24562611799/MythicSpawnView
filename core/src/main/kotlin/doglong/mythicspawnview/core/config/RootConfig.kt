package doglong.mythicspawnview.core.config

import doglong.mythicspawnview.libs.IActive
import doglong.mythicspawnview.libs.utils.StringUtils.toColor
import org.bukkit.plugin.Plugin

object RootConfig: IActive {

    var descriptionList = mutableListOf<String>()
    var placeholderNoWarmup = ""

    override fun init(plugin: Plugin) {
    }

    override fun starting(plugin: Plugin) {
        plugin.saveDefaultConfig()
        plugin.reloadConfig()
        val c = plugin.config
        descriptionList = c.getStringList("description")
        descriptionList.replaceAll { it.toColor() }
        placeholderNoWarmup = c.getString("placeholder.no-warmup", "").toColor()
    }

    override fun stopping(plugin: Plugin) {
    }

}