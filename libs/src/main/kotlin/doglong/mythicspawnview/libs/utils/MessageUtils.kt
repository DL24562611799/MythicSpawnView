package doglong.mythicspawnview.libs.utils

import doglong.mythicspawnview.libs.utils.StringUtils.toColor
import org.bukkit.command.CommandSender

object MessageUtils {

    fun CommandSender.message(message: String) {
        this.sendMessage(message.toColor())
    }


}