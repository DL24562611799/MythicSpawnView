package doglong.mythicspawnview.libs.utils

import doglong.mythicspawnview.libs.PluginProxy
import doglong.mythicspawnview.libs.command.Command
import org.bukkit.Bukkit
import org.bukkit.util.StringUtil
import java.util.ArrayList
import java.util.Objects

object CommandUtils {

    fun String.getOnlinePlayerTabComplete(): List<String> {
        val list: MutableList<String> = ArrayList()
        for (player in Bukkit.getOnlinePlayers()) {
            list.add(player.name)
        }
        return StringUtil.copyPartialMatches(this, list, ArrayList())
    }

    fun <T : PluginProxy> Command<T>.register() {
        val pluginCommand = Objects.requireNonNull(Bukkit.getPluginCommand(this.name), "")
        pluginCommand.tabCompleter = this;
        pluginCommand.executor = this;
    }
}