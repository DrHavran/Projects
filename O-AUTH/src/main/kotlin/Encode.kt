import javax.crypto.Mac
import javax.crypto.spec.SecretKeySpec
import java.util.*

class Encode {
    fun hash(data: String, secret: String): String {
        val mac = Mac.getInstance("HmacSHA256")
        val secretKey = SecretKeySpec(secret.toByteArray(Charsets.UTF_8), "HmacSHA256")
        mac.init(secretKey)
        val hashBytes = mac.doFinal(data.toByteArray(Charsets.UTF_8))
        return Base64.getUrlEncoder().withoutPadding().encodeToString(hashBytes)
    }

    fun encodeBase64(input: String): String {
        return Base64.getUrlEncoder().withoutPadding().encodeToString(input.toByteArray(Charsets.UTF_8))
    }

    fun decodeBase64(input: String): String {
        return String(Base64.getUrlDecoder().decode(input), Charsets.UTF_8)
    }
}