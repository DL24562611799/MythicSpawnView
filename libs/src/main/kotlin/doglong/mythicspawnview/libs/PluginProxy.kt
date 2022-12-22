package doglong.mythicspawnview.libs

import doglong.mythicspawnview.libs.utils.StringUtils.toColor
import org.bukkit.Bukkit
import org.bukkit.plugin.java.JavaPlugin

abstract class PluginProxy {

    val plugin: JavaPlugin = Main.getPlugin();
    private val actives = mutableSetOf<IActive>()

    open fun initializing() {}

    open fun loading() {}

    open fun enabling() {}

    open fun disabling() {}

    fun init() {
        this.actives.forEach { iActive -> iActive.init(this.plugin) }
    }

    fun starting() {
        this.actives.forEach { iActive -> iActive.starting(this.plugin) }
    }

    fun stopping() {
        this.actives.forEach { iActive -> iActive.stopping(this.plugin) }
    }

    fun IActive.addActive() {
        actives.add(this)
    }

    fun log(message: String) {
        Bukkit.getConsoleSender().sendMessage("&a[&6${plugin.name}&a] $message".toColor())
    }
}