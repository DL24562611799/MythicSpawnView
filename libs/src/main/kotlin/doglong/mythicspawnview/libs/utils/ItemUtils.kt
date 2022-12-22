package doglong.mythicspawnview.libs.utils

import org.bukkit.Material
import org.bukkit.inventory.ItemStack
import org.bukkit.inventory.meta.ItemMeta

object ItemUtils {
    /**
     * 修改ItemMeta
     *       air.meta {
     *          this.displayName = ""
     *       }
     */
    inline fun <T : ItemStack> T.meta(block: ItemMeta.() -> Unit): T {
        val itemMeta = itemMeta ?: return this
        block(itemMeta)
        this.itemMeta = itemMeta
        return this
    }

    /**
     * 检查材质是否是空气
     */
    fun Material.checkAir(): Boolean = when (this.name) {
        "AIR",
        "VOID_AIR",
        "CAVE_AIR",
        "LEGACY_AIR" -> true

        else -> false
    }

    /**
     * 检查物品是否是空气.null也为空气
     */
    fun ItemStack?.checkAir(): Boolean = if (this == null) true else type.checkAir()
}