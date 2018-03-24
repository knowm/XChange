package org.knowm.xchange.huobipro

import com.squareup.okhttp.*
import okio.Buffer
import org.apache.commons.codec.digest.HmacUtils
import org.knowm.xchange.BaseExchange
import org.knowm.xchange.Exchange
import org.knowm.xchange.ExchangeSpecification
import org.knowm.xchange.huobipro.util.*

import org.knowm.xchange.service.account.AccountService
import org.knowm.xchange.utils.nonce.AtomicLongIncrementalTime2014NonceFactory
import java.io.InputStream
import java.io.UnsupportedEncodingException
import java.net.URLEncoder
import java.time.Instant
import java.time.ZoneId
import java.time.format.DateTimeFormatter
import java.util.logging.Logger

class HuobiproExchange : Exchange, BaseExchange() {
    override fun loadExchangeMetaData(`is`: InputStream?) {
    }

    override fun <T : Any?> loadMetaData(`is`: InputStream?, type: Class<T>?): T {
        return super.loadMetaData(`is`, type)
    }

    val authClient = object : ApiClient() {
        private val CONTENT_TYPE = MediaType.parse("application/json")

        init {
            httpClient.interceptors().add(
                    Interceptor {
                        val request = it.request()
                        val hdr = request.headers().toMultimap()
                        val requestBuffer = Buffer()
                        request.body()?.writeTo(requestBuffer)
                        val body: String = requestBuffer.readUtf8()
                        val verb = request.method()
                        val httpUrl = request.httpUrl()
                        val encodedPath = httpUrl.encodedPath()

                        val apiSecret = exchangeSpecification.secretKey

                        val matchResult = pathRegex.find(request.url().toExternalForm())!!
                        val host = matchResult.groups[1]!!.value
                        val uri = matchResult.groups[2]!!.value
                        val appKey = exchangeSpecification.apiKey
                        val newBuilder = httpUrl.newBuilder()
                        var params :  Map<String, String> = when (verb) {
                            "GET" -> {
                                val encodedQuery = httpUrl.encodedQuery()

                                if (null != encodedQuery) {
                                    (encodedQuery / '&').associate { it % '=' }.toMutableMap()
                                } else
                                    mutableMapOf()
                            }
                            "POST" -> gson.fromJson<Map<String, String>>(body, Map::class.java).toMutableMap()
                            else -> {
                                 mapOf()
                            }
                        }
                        params.forEach { t, u -> newBuilder.removeAllQueryParameters(t).addQueryParameter(t, u) }

                        params=sig .createSignature(appKey = exchangeSpecification.apiKey, appSecretKey = exchangeSpecification.secretKey, method = verb, host = host, uri = uri, orig = params)
//                       createSignature(/*exchangeSpecification.apiKey, exchangeSpecification.secretKey, */verb, host, uri, mutableMap).toMutableMap()


                        params.forEach { t, u -> newBuilder.removeAllQueryParameters(t).addQueryParameter(t, u) }
                        val doppleganger = request.newBuilder().headers(
                                Headers.of(hdr.entries.associate { it.key to it.value.first() })
                        ).url(newBuilder.build())
                        val build = doppleganger.build()
                        val response = it.proceed(build)
                        if ( debugMe) {
                            val url = response.request().url()
                            val code = response.code()
                            val headers = response.headers()
                            System.err.println(
                                    "<-- Received response $code for $url\n\n--\n$headers"
                            )
                        }

                        val body1 = response.body()
                        val contentType = body1.contentType()
                        val content = response.body().string()
                        if ( debugMe) Logger.getAnonymousLogger().info(content)

                        val wrappedBody = ResponseBody.create(contentType, content)
                        response.newBuilder().body(wrappedBody).build()

                    })
        }


        val pathRegex = Regex(pattern = ".*://([^/]*)(/[^?#]+).*")
    }

    override fun getAccountService(): AccountService {
        return HuobiproAccountService(this)
    }

    override fun getDefaultExchangeSpecification(): ExchangeSpecification {
        return object : ExchangeSpecification(HuobiproExchange::class.java) {
            override fun getExchangeName(): String {
                return "Huobipro"
            }

            override fun getExchangeDescription(): String {
                return "HuobiPro Swagger Generated Exchange"
            }

            override fun getHost() = "api.huobi.pro"
        }
    }

    val xwfc = "application/x-www-form-urlencoded"
    override fun getNonceFactory() = AtomicLongIncrementalTime2014NonceFactory()
    override fun initServices() {}

    companion object {
        val gson by lazy { JSON().gson }

        /**
         * if you need to debug the REST api calls use -DXChangeDebug=true
         */

        val debugMe: Boolean by lazy { "true" == System.getProperty("XChangeDebug", "false") }

        fun setupDebug(apiClient: ApiClient) {
            apiClient.httpClient.interceptors().add(debugInterceptor)
        }

        val debugInterceptor by lazy {
            /**
             * jn: I would prefer to generate the rescu api for REST calls but
             * this gets the job done for now...
             *
             * also an interceptor exists for gzip request variant
             * https://github.com/square/okhttp/wiki/Interceptors
             *
             */

            Interceptor { chain ->
                val request = chain.request()
                val t1 = System.nanoTime()
                Logger.getAnonymousLogger().info(
                        String.format("--> Sending request %s on %s%n%s",
                                request.url(),
                                chain.connection(), request.headers()))

                val requestBuffer = Buffer()
                if (request.method() == "POST") {
                    request.body().writeTo(requestBuffer)
                    Logger.getAnonymousLogger().info(requestBuffer.readUtf8())
                }
                val response = chain.proceed(request)

                val t2 = System.nanoTime()
                Logger.getAnonymousLogger().info(
                        String.format("<-- Received response for %s in %.1fms%n%s",
                                response.request().url(), (t2 - t1) / 1e6,
                                response.headers()))

                val contentType = response.body().contentType()
                val content = response.body().string()
                Logger.getAnonymousLogger().info(content)

                val wrappedBody = ResponseBody.create(contentType, content)
                response.newBuilder().body(wrappedBody).build()
            }
        }
    }

    /**
     * Create a valid signature. This method is called by the client and will add the AccessKeyId, Timestamp,
     * SignatureVersion, SignatureMethod, Signature parameters in the incoming params.
     *
     * @param appKey       AppKeyId.
     * @param appSecretKey AppKeySecret.
     * @param method       Request method, "GET" or "POST"
     * @param host         Request domain name, eg "be.huobi.com"
     * @param uri          request path, note Does not include ? and after parameters, such as "/v1/api/info"
     * @param params       Original request parameters, stored as Key-Value, Note Value is not encoded
     */
    fun createSignature(method: String, host: String, uri: String,
                        params1: Map<String, String>): Map<String, String> {

        val params = params1.filterNot { it.key == "Signature" }.entries.
                associate { entry -> entry.key to urlEncode(  entry.value )} +
                ("SignatureVersion" to "2") +
                ("Timestamp" to urlEncode(  gmtNow())  )+
                ("SignatureMethod" to "HmacSHA256") +
                ("AccessKeyId" to  urlEncode(exchangeSpecification.apiKey  ))//scrambled order intentionally


        val joinToString = params.entries.sortedBy { it.key }.map { val listOf = listOf(it.key, urlEncode(it.value))
            listOf.joinToString("=") }.joinToString (  "&" )
        val payload = "${method.toUpperCase()}\n" +
                "${host.toLowerCase()}\n" +
                "$uri\n" +
             "$joinToString"

        val actualSign = java.util.Base64 .getEncoder().encodeToString( HmacUtils.hmacSha256 (exchangeSpecification.secretKey, payload) )


        val pair = "Signature" to urlEncode(actualSign)
        val map = params + pair
        return  map
                .also {
                    if (Companion.debugMe) System.err.println("Dump parameters:" + map)
                }
    }
}

operator fun String.rem(c: Char) = this.split(c).toPair()
operator fun String.div(c: Char) = this.split(c)

/** wish this was part of the stdlib */
inline  fun <reified T> Iterable<T>.toPair() = iterator().run { Pair(next(), next()) }

/**
 *       * Use standard URL Encode encoding. Note that unlike the default JDK, spaces are coded as %20 instead of +.
 *       *
 *       * @param s String
 *       * @return URL encoded string
 *        */
fun urlEncode(s: String): String {
    try {
        return URLEncoder.encode(s, "UTF-8").replace("\\+".toRegex(), "%20")
    } catch (e: UnsupportedEncodingException) {
        throw IllegalArgumentException("UTF-8 encoding not supported!")
    }

}

/**
 * Return epoch seconds
 */
fun epochNow(): Long {
    return Instant.now().epochSecond
}

fun gmtNow(): String {
    return Instant.ofEpochSecond(epochNow()).atZone(ZONE_GMT).format(DT_FORMAT)
}

val DT_FORMAT = DateTimeFormatter.ofPattern("uuuu-MM-dd'T'HH:mm:ss")
val ZONE_GMT = ZoneId.of("Z")
