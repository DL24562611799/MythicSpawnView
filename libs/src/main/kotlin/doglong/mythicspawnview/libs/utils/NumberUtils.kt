package doglong.mythicspawnview.libs.utils

import java.math.BigDecimal
import java.math.RoundingMode
import java.util.*
import java.util.regex.Pattern

object NumberUtils {

    private val NUMBER_PATTERN = Pattern.compile("^[+|-]?[0-9]+(\\.[0-9]+)?$")

    fun String.isNumber(): Boolean {
        return NUMBER_PATTERN.matcher(this).matches();
    }

    fun String.toDecimal(): BigDecimal {
        return if (this.isNumber()) {
            BigDecimal(this)
        }else {
            val str = this.replace(" ", "").replace("§+[a-z0-9]", "").replace("[^-0-9.]", "")
            if (str.isNotEmpty() && str.replace("[^.]", "").length <= 1) {
                BigDecimal(str)
            }else {
                BigDecimal.ZERO
            }
        }
    }

    fun getRandomNumber(): Double {
        return BigDecimal((Math.random() * 100.0).toString()).setScale(3, RoundingMode.HALF_UP).toDouble()
    }

    fun DoubleArray?.random(): BigDecimal {
        val min: Double = this.min()
        val max: Double = this.max()
        if (min >= 0.0) {
            val minDec = BigDecimal(min.toString())
            val maxDec = BigDecimal(max.toString())
            val difference = maxDec.subtract(minDec)
            if (difference.toDouble() > 0.0) {
                val value = BigDecimal(Math.random().toString()).multiply(difference)
                return value.add(minDec)
            }
        }
        return BigDecimal(max.coerceAtLeast(min))
    }

    @JvmOverloads
    fun Any?.toInt(def: Int = 0): Int {
        return if (Objects.isNull(this)) {
            def
        } else {
            val string = this.toString()
            try {
                string.toInt()
            } catch (e: NumberFormatException) {
                BigDecimal(this.toString()).toInt()
            }
        }
    }

    @JvmOverloads
    fun Any?.toLong(def: Long = 0): Long {
        return if (Objects.isNull(this)) {
            def
        } else {
            val string = this.toString()
            try {
                string.toLong()
            } catch (e: NumberFormatException) {
                BigDecimal(this.toString()).toLong()
            }
        }
    }

    @JvmOverloads
    fun Any?.toDouble(def: Double = 0.0): Double {
        return if (Objects.isNull(this)) {
            def
        } else {
            val string = this.toString()
            try {
                string.toDouble()
            } catch (e: NumberFormatException) {
                BigDecimal(this.toString()).toDouble()
            }
        }
    }

    @JvmOverloads
    fun Any?.toFloat(def: Float = 0f): Float {
        return if (Objects.isNull(this)) {
            def
        } else {
            val string = this.toString()
            try {
                string.toFloat()
            } catch (e: NumberFormatException) {
                BigDecimal(this.toString()).toFloat()
            }
        }
    }

    @JvmOverloads
    fun Any?.toByte(def: Byte = 0): Byte {
        return if (Objects.isNull(this)) {
            def
        } else {
            val string = this.toString()
            try {
                string.toByte()
            } catch (e: NumberFormatException) {
                BigDecimal(this.toString()).toByte()
            }
        }
    }

    @JvmOverloads
    fun Any?.toShort(def: Short = 0): Short {
        return if (Objects.isNull(this)) {
            def
        } else {
            val string = this.toString()
            try {
                string.toShort()
            } catch (e: NumberFormatException) {
                BigDecimal(this.toString()).toShort()
            }
        }
    }

    fun LongArray?.max(): Long {
        return if (this == null) {
            throw IllegalArgumentException("The Array must not be null")
        } else if (this.isEmpty()) {
            throw IllegalArgumentException("Array cannot be empty.")
        } else {
            var max = this[0]
            for (j in 1 until this.size) {
                if (this[j] > max) {
                    max = this[j]
                }
            }
            max
        }
    }

    fun IntArray?.max(): Int {
        return if (this == null) {
            throw IllegalArgumentException("The Array must not be null")
        } else if (this.isEmpty()) {
            throw IllegalArgumentException("Array cannot be empty.")
        } else {
            var max = this[0]
            for (j in 1 until this.size) {
                if (this[j] > max) {
                    max = this[j]
                }
            }
            max
        }
    }

    fun ShortArray?.max(): Short {
        return if (this == null) {
            throw IllegalArgumentException("The Array must not be null")
        } else if (this.isEmpty()) {
            throw IllegalArgumentException("Array cannot be empty.")
        } else {
            var max = this[0]
            for (i in 1 until this.size) {
                if (this[i] > max) {
                    max = this[i]
                }
            }
            max
        }
    }

    fun ByteArray?.max(): Byte {
        return if (this == null) {
            throw IllegalArgumentException("The Array must not be null")
        } else if (this.isEmpty()) {
            throw IllegalArgumentException("Array cannot be empty.")
        } else {
            var max = this[0]
            for (i in 1 until this.size) {
                if (this[i] > max) {
                    max = this[i]
                }
            }
            max
        }
    }

    fun DoubleArray?.max(): Double {
        return if (this == null) {
            throw IllegalArgumentException("The Array must not be null")
        } else if (this.isEmpty()) {
            throw IllegalArgumentException("Array cannot be empty.")
        } else {
            var max = this[0]
            for (j in 1 until this.size) {
                if (java.lang.Double.isNaN(this[j])) {
                    return Double.NaN
                }
                if (this[j] > max) {
                    max = this[j]
                }
            }
            max
        }
    }

    fun FloatArray?.max(): Float {
        return if (this == null) {
            throw IllegalArgumentException("The Array must not be null")
        } else if (this.isEmpty()) {
            throw IllegalArgumentException("Array cannot be empty.")
        } else {
            var max = this[0]
            for (j in 1 until this.size) {
                if (java.lang.Float.isNaN(this[j])) {
                    return Float.NaN
                }
                if (this[j] > max) {
                    max = this[j]
                }
            }
            max
        }
    }

    fun LongArray?.min(): Long {
        return if (this == null) {
            throw IllegalArgumentException("The Array must not be null")
        } else if (this.isEmpty()) {
            throw IllegalArgumentException("Array cannot be empty.")
        } else {
            var min = this[0]
            for (i in 1 until this.size) {
                if (this[i] < min) {
                    min = this[i]
                }
            }
            min
        }
    }

    fun IntArray?.min(): Int {
        return if (this == null) {
            throw IllegalArgumentException("The Array must not be null")
        } else if (this.isEmpty()) {
            throw IllegalArgumentException("Array cannot be empty.")
        } else {
            var min = this[0]
            for (j in 1 until this.size) {
                if (this[j] < min) {
                    min = this[j]
                }
            }
            min
        }
    }

    fun ShortArray?.min(): Short {
        return if (this == null) {
            throw IllegalArgumentException("The Array must not be null")
        } else if (this.isEmpty()) {
            throw IllegalArgumentException("Array cannot be empty.")
        } else {
            var min = this[0]
            for (i in 1 until this.size) {
                if (this[i] < min) {
                    min = this[i]
                }
            }
            min
        }
    }

    fun ByteArray?.min(): Byte {
        return if (this == null) {
            throw IllegalArgumentException("The Array must not be null")
        } else if (this.isEmpty()) {
            throw IllegalArgumentException("Array cannot be empty.")
        } else {
            var min = this[0]
            for (i in 1 until this.size) {
                if (this[i] < min) {
                    min = this[i]
                }
            }
            min
        }
    }

    fun DoubleArray?.min(): Double {
        return if (this == null) {
            throw IllegalArgumentException("The Array must not be null")
        } else if (this.isEmpty()) {
            throw IllegalArgumentException("Array cannot be empty.")
        } else {
            var min = this[0]
            for (i in 1 until this.size) {
                if (java.lang.Double.isNaN(this[i])) {
                    return Double.NaN
                }
                if (this[i] < min) {
                    min = this[i]
                }
            }
            min
        }
    }

    fun FloatArray?.min(): Float {
        return if (this == null) {
            throw IllegalArgumentException("The Array must not be null")
        } else if (this.isEmpty()) {
            throw IllegalArgumentException("Array cannot be empty.")
        } else {
            var min = this[0]
            for (i in 1 until this.size) {
                if (java.lang.Float.isNaN(this[i])) {
                    return Float.NaN
                }
                if (this[i] < min) {
                    min = this[i]
                }
            }
            min
        }
    }
}