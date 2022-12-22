package doglong.mythicspawnview.libs.utils

import org.bukkit.ChatColor
import java.util.*
import kotlin.math.abs

object StringUtils {

    private val alphabet = "ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz0123456789-_=".toCharArray()

    fun String?.isEmpty(): Boolean {
        return this == null || "" == this.trim { it <= ' ' }
    }

    fun String?.isNotEmpty(): Boolean {
        return !this.isEmpty()
    }

    fun String.stripColor(): String {
        return ChatColor.stripColor(this);
    }

    fun String.toColor(): String {
        return ChatColor.translateAlternateColorCodes('&', this)
    }

    /**
     * 快速格式化字符串，比起replace的速度快4倍
     * @param params 参数数组
     */
    fun String.toFormat(vararg params: Any?): String {

        if (params.isEmpty() || this.isEmpty()) {
            return this
        }
        val arr: CharArray = this.toCharArray()
        val stringBuilder: StringBuilder = StringBuilder();
        var i = 0
        while (i < arr.size) {
            val mark = i
            if (arr[i] == '{') {
                var num = 0
                while (i + 1 < arr.size && Character.isDigit(arr[i + 1])) {
                    i++
                    num *= 10
                    num += arr[i].code - '0'.code
                }
                if (i != mark && i + 1 < arr.size && arr[i + 1] == '}') {
                    i++
                    if (params.size > num) stringBuilder.append(params[num])
                } else {
                    i = mark
                }
            }
            if (mark == i) {
                stringBuilder.append(arr[i])
            }
            i++
        }
        return stringBuilder.toString();
    }

    /**
     * 将随机UUID转换成指定位数的字符串
     * @param bits 指定位数
     * @return uuid
     */
    fun randomUUID(bits: Int): String {
        val uuid = UUID.randomUUID()
        val msb = uuid.mostSignificantBits
        val lsb = uuid.leastSignificantBits
        val out = CharArray(24)
        var tmp: Int
        var idx = 0
        // 循环写法
        var bit = 0
        var bt1 = 8
        var bt2 = 8
        var mask: Int
        var offsetm: Int
        var offsetl: Int
        while (bit < 16) {
            offsetm = 64 - (bit + 3) * 8
            tmp = 0
            if (bt1 > 3) {
                mask = 1 shl 8 * 3 - 1
            } else if (bt1 >= 0) {
                mask = 1 shl 8 * bt1 - 1
                bt2 -= 3 - bt1
            } else {
                mask = 1 shl 8 * bt2.coerceAtMost(3) - 1
                bt2 -= 3
            }
            if (bt1 > 0) {
                bt1 -= 3
                tmp = (if (offsetm < 0) msb else msb ushr offsetm and mask.toLong()).toInt()
                if (bt1 < 0) {
                    tmp = tmp shl abs(offsetm)
                    mask = 1 shl 8 * abs(bt1) - 1
                }
            }
            if (offsetm < 0) {
                offsetl = 64 + offsetm
                tmp = (tmp.toLong() or ((if (offsetl < 0) lsb else lsb ushr offsetl) and mask.toLong())).toInt()
            }
            if (bit == 15) {
                out[idx + 3] = alphabet[64]
                out[idx + 2] = alphabet[64]
                tmp = tmp shl 4
            } else {
                out[idx + 3] = alphabet[tmp and 0x3f]
                tmp = tmp shr 6
                out[idx + 2] = alphabet[tmp and 0x3f]
                tmp = tmp shr 6
            }
            out[idx + 1] = alphabet[tmp and 0x3f]
            tmp = tmp shr 6
            out[idx] = alphabet[tmp and 0x3f]
            bit += 3
            idx += 4
        }
        return String(out, 0, bits)
    }
}