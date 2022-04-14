package fr.twah2em.login.encryption

/**
 * Not very useful since there is only one encryption method, but maybe it can be in the future.
 */
interface Encryptor {
    fun encrypt(password: String): String
    fun toHex(bytes: ByteArray): String
    fun fromHex(hex: String): ByteArray
    fun validate(password: String, encrypted: String): Boolean
}