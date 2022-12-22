package doglong.mythicspawnview.libs.command

import doglong.mythicspawnview.libs.PluginProxy
import doglong.mythicspawnview.libs.utils.MessageUtils.message
import doglong.mythicspawnview.libs.utils.StringUtils.isNotEmpty
import org.bukkit.command.CommandSender
import org.bukkit.command.TabExecutor
import org.bukkit.entity.Player
import org.bukkit.util.StringUtil
import java.util.*

abstract class Command<T : PluginProxy> : TabExecutor {

    constructor(plugin: T) {
        this.plugin = plugin
    }

    constructor(parent: Command<T>) {
        this.plugin = parent.plugin
    }

    protected var plugin: T? = null

    // 指令名称
    abstract val name: String
    // 指令子名称
    open val aliases: Array<String>? = null
    // 指令权限
    open val permissionNode: String? = null
    // 是否允许控制台执行
    open val consoleFriendly: Boolean = true
    // name <-> instance
    private val subCommands: MutableMap<String, Command<T>> = mutableMapOf()
    // sub name <-> instance
    private val subCommandAliases: MutableMap<String, Command<T>> = mutableMapOf()

    abstract fun onCommand(sender: CommandSender, args: Array<String>): Boolean

    abstract fun onTabComplete(sender: CommandSender, args: Array<String>): List<String>?

    @SafeVarargs
    fun addCommands(vararg commands: Command<T>) {
        for (command in commands) {
            this.subCommands[command.name.lowercase()] = command
            for (alias in command.aliases!!) {
                this.subCommandAliases[alias.lowercase()] = command
            }
        }
    }

    override fun onCommand(
        sender: CommandSender,
        cmd: org.bukkit.command.Command?,
        label: String?,
        args: Array<String>
    ): Boolean {
        return if (this.permissionNode.isNotEmpty() && !sender.hasPermission(this.permissionNode)) {
            sender.message("&cYou don't have permission to do this!")
            true
        } else if (!this.consoleFriendly && sender !is Player) {
            sender.message("&cOnly players can do this!")
            true
        } else {
            val sub: Command<T>
            if (args.isNotEmpty() && subCommands[args[0].lowercase(Locale.getDefault())] != null) {
                sub = subCommands[args[0].lowercase(Locale.getDefault())]!!
                sub.onCommand(sender, cmd, label, args.copyOfRange(1, args.size))
            } else if (args.isNotEmpty() && subCommandAliases[args[0].lowercase(Locale.getDefault())] != null) {
                sub = subCommandAliases[args[0].lowercase(Locale.getDefault())]!!
                sub.onCommand(sender, cmd, label, args.copyOfRange(1, args.size))
            } else {
                onCommand(sender, args)
            }
        }
    }

    override fun onTabComplete(
        sender: CommandSender,
        cmd: org.bukkit.command.Command?,
        label: String?,
        args: Array<String>
    ): List<String?>? {
        return if (this.permissionNode.isNotEmpty() && !sender.hasPermission(this.permissionNode)) {
            null
        } else {
            val sub: Command<T>
            if (args.size > 1 && subCommands[args[0].lowercase(Locale.getDefault())] != null) {
                sub = subCommands[args[0].lowercase(Locale.getDefault())]!!
                sub.onTabComplete(sender, cmd, label, args.copyOfRange(1, args.size))
            } else if (args.size > 1 && subCommandAliases[args[0].lowercase(Locale.getDefault())] != null) {
                sub = subCommandAliases[args[0].lowercase(Locale.getDefault())]!!
                sub.onTabComplete(sender, cmd, label, args.copyOfRange(1, args.size))
            } else {
                var result: List<String?>? = this.onTabComplete(sender, args)
                if (result == null && args.size == 1) {
                    result = ArrayList()
                    StringUtil.copyPartialMatches(args[0], subCommands.keys, result)
                }
                result ?: ArrayList()
            }
        }
    }


}