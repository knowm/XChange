package org.knowm.xchange.huobipro

import javax.crypto.Mac
import javax.crypto.spec.SecretKeySpec
import java.nio.charset.StandardCharsets
import java.security.InvalidKeyException
import java.security.NoSuchAlgorithmException
import java.util.Base64

import java.lang.System.err
import java.util.Arrays.asList

class sig {

    companion object {
        /**this code courtesy of ... the nice guy who wrote it and put on github...
         *
         * 创建一个有效的签名。该方法为客户端调用，将在传入的params中添加AccessKeyId、Timestamp、SignatureVersion、SignatureMethod、Signature参数。
         *
         * @param appKey       AppKeyId.
         * @param appSecretKey AppKeySecret.
         * @param method       请求方法，"GET"或"POST"
         * @param host         请求域名，例如"be.huobi.com"
         * @param uri          请求路径，注意不含?以及后的参数，例如"/v1/api/info"
         * @param params       原始请求参数，以Key-Value存储，注意Value不要编码
         */
        fun createSignature(appKey: String, appSecretKey: String, method: String, host: String,
                            uri: String, orig: Map<String, String>): Map<String, String> {
            var sb = (method.toUpperCase()) + ('\n') /* GET*/ + (host.toLowerCase()) + ('\n') /* Host*/ + (uri) + ('\n') // /path
            var params = orig.filterNot { it.key == "Signature" } +
//            params.remove("Signature")
                    ("AccessKeyId" to appKey) +
                    ("SignatureVersion" to "2") +
                    ("SignatureMethod" to "HmacSHA256") +
                    ("Timestamp" to gmtNow())
            // build signature:

            //            val map = TreeMap(params)
            //            map.forEach { (key, value) -> sb+(key)+('=')+(urlEncode(value))+('&') }
            //            // remove last '&':
            //            sb.deleteCharAt(sb.length - 1)
            sb += params.entries.sortedBy { it.key }.map { it.run { asList(key, urlEncode( value)) }.joinToString("=") }.joinToString("&")

            // sign:
            var hmacSha256: Mac? = null
            try {
                hmacSha256 = Mac.getInstance("HmacSHA256")
                val secKey = SecretKeySpec(appSecretKey.toByteArray(StandardCharsets.UTF_8), "HmacSHA256")
                hmacSha256!!.init(secKey)
            } catch (e: NoSuchAlgorithmException) {
                throw RuntimeException("No such algorithm: " + e.message)
            } catch (e: InvalidKeyException) {
                throw RuntimeException("Invalid key: " + e.message)
            }

            val payload = sb.toString()
            val hash = hmacSha256.doFinal(payload.toByteArray(StandardCharsets.UTF_8))
            val actualSign = Base64.getEncoder().encodeToString(hash)
            params += ("Signature" to actualSign)
            if (debugMe) {
                err.println("Dump parameters:")
                params.forEach { key, value ->
                    err.println("  key: $key, value: $value")
                }
            }
            return params
        }

        internal var debugMe = System.getProperty("XChangeDebug", "false") != "false"
    }
}
