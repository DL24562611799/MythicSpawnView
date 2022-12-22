package doglong.mythicspawnview.libs

import org.bukkit.plugin.Plugin

interface IActive {

    fun init(plugin: Plugin)

    fun starting(plugin: Plugin)

    fun stopping(plugin: Plugin)

}