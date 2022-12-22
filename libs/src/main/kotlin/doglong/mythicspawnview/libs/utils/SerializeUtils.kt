package doglong.mythicspawnview.libs.utils

import java.io.*

object SerializeUtils {

    fun <T : Serializable?> T.serialize(): ByteArray? {
        val bos = ByteArrayOutputStream()
        var oos: ObjectOutputStream? = null
        try {
            oos = ObjectOutputStream(bos)
            oos.writeObject(this)
            oos.flush()
            return bos.toByteArray()
        } catch (e: IOException) {
            e.printStackTrace()
        } finally {
            try {
                oos?.close()
                bos.close()
            } catch (e: IOException) {
                e.printStackTrace()
            }
        }
        return null
    }

    @Throws(IOException::class, ClassNotFoundException::class)
    fun <E> Class<E>.deserialize(bytes: ByteArray?): E? {
        val bas = ByteArrayInputStream(bytes)
        val ois = ObjectInputStream(bas)
        val `object` = ois.readObject()
        return if (this.isAssignableFrom(`object`.javaClass)) {
            this.cast(`object`)
        } else null
    }

}