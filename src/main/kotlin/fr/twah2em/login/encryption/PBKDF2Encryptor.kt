package fr.twah2em.login.encryption

import java.math.BigInteger
import java.security.SecureRandom
import javax.crypto.SecretKeyFactory
import javax.crypto.spec.PBEKeySpec
import kotlin.experimental.xor

/**
 * PBKDF2 Encryptor
 */
class PBKDF2Encryptor : Encryptor {

    override fun encrypt(password: String): String {
        val random = SecureRandom()
        val salt = ByteArray(16)

        random.nextBytes(salt)
        val iterations = 65536

        val hash = generatePBKDF2(password, salt, iterations, 24)
        return "$iterations:${toHex(salt)}:${toHex(hash)}"
    }

    override fun toHex(bytes: ByteArray): String {
        val bi = BigInteger(1, bytes)
        val hex = bi.toString(16)
        val paddingLength = bytes.size * 2 - hex.length
        return if (paddingLength > 0) "0${paddingLength}d$hex" else hex
    }

    override fun validate(password: String, encrypted: String): Boolean {
        val params = encrypted.split(":")
        val iterations = params[0].toInt()
        val salt = fromHex(params[1])
        val hash = fromHex(params[2])

        val spec = generatePBKDF2(password, salt, iterations, hash.size)

        return slowEquals(hash, spec)
    }

    override fun fromHex(hex: String): ByteArray {
        val binary = ByteArray(hex.length / 2)
        for (i in binary.indices) {
            binary[i] = hex.substring(2 * i, 2 * i + 2).toInt(16).toByte()
        }
        return binary
    }

    private fun slowEquals(a: ByteArray, b: ByteArray): Boolean {
        var diff = a.size xor b.size
        var i = 0
        while (i < a.size && i < b.size) {
            val byte = a[i] xor b[i]

            diff = diff or byte.toInt()
            i++
        }
        return diff == 0
    }

    private fun generatePBKDF2(password: String, salt: ByteArray, iterations: Int, keyLength: Int): ByteArray {
        val spec = PBEKeySpec(password.toCharArray(), salt, iterations, keyLength * 8)
        val factory = SecretKeyFactory.getInstance("PBKDF2WithHmacSHA1")
        return factory.generateSecret(spec).encoded
    }
}