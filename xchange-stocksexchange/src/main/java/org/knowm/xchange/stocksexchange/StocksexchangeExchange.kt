package org.knowm.xchange.stocksexchange

import com.squareup.okhttp.*
import okio.*
import org.apache.commons.codec.digest.*
import org.knowm.xchange.*
import org.knowm.xchange.currency.*
import org.knowm.xchange.dto.account.*
import org.knowm.xchange.service.account.*
import org.knowm.xchange.service.trade.params.*
import org.knowm.xchange.stocksexchange.api.*
import org.knowm.xchange.stocksexchange.util.*
import org.knowm.xchange.utils.nonce.*
import java.io.*
import java.math.*
import java.util.logging.*


class StocksexchangeExchange : Exchange, BaseExchange() {
    override fun getNonceFactory() = AtomicLongIncrementalTime2014NonceFactory()
    override fun initServices() {

    }

    override fun getDefaultExchangeSpecification() = ExchangeSpecification(
            StocksexchangeExchange::class.java).apply {
        exchangeDescription = "Bitcoin.co.id exchange Kotlin based API client "
        exchangeName = "bitcoincoid"

    }


//    private val viewApi = ApiClient().createService<ViewApi>(ViewApi::class.java)

    private val xwfc = "application/x-www-form-urlencoded"

    private val sexchangeClient = object : ApiClient() {
        var debugMe = System.getProperty("XChangeDebug", "false") != "false"

        private val CONTENT_TYPE = MediaType.parse(xwfc)
        override fun buildRequest(path: String?, method: String?, queryParams: MutableList<Pair>?,
                                  collectionQueryParams: MutableList<Pair>?, body: Any?,
                                  headerParams: MutableMap<String, String>?,
                                  formParams: MutableMap<String, Any>?,
                                  authNames: Array<out String>?,
                                  progressRequestListener: ProgressRequestBody.ProgressRequestListener?): Request {
            if (debugMe)
                httpClient.interceptors().add(


                        object : Interceptor {
                            @Throws(IOException::class)
                            override fun intercept(chain: Interceptor.Chain): Response {
                                val request = chain.request()

                                val t1 = System.nanoTime()
                                Logger.getAnonymousLogger().info(
                                        String.format("--> Sending request %s on %s%n%s",
                                                      request.url(),
                                                      chain.connection(), request.headers()))

                                val requestBuffer = Buffer()
                                request.body().writeTo(requestBuffer)
                                Logger.getAnonymousLogger().info(requestBuffer.readUtf8())

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
                                return response.newBuilder().body(wrappedBody).build()
                            }
                        })
            debugMe = false;
            val f = linkedMapOf<String, Any>()
            formParams?.let(f::putAll)
            f["nonce"] = nonceFactory.createValue().toString()
            f["method"] = when {
                        path!!.startsWith('/') -> path.drop(1)
                        else -> path
                    };


        val h = linkedMapOf<String, String>()
        headerParams?.let(h::putAll)

        val s = f.map {
            listOf(it.key, it.value.toString()).joinToString("=")
        }.joinToString("&")

        val reqbody = RequestBody.create(CONTENT_TYPE, s)

        h["Key"] = exchangeSpecification.apiKey
        h["Sign"] = HmacUtils(HmacAlgorithms.HMAC_SHA_512,
        exchangeSpecification.secretKey).hmacHex(s)

        val request = Request.Builder()
                .url(buildUrl("", queryParams, collectionQueryParams))
                .headers(Headers.of(h))
                .post(reqbody)
                .build()
        return request
    }
}

val defaultApi = object : DefaultApi() {
    init {

        apiClient = sexchangeClient
    }

}

override fun getAccountService(): AccountService {

    return object : AccountService {
        override fun withdrawFunds(currency: Currency?, amount: BigDecimal?, address: String?): String {
            TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
        }

        override fun withdrawFunds(params: WithdrawFundsParams?): String {
            TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
        }

        override fun requestDepositAddress(currency: Currency?, vararg args: String?): String {
            TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
        }

        override fun getFundingHistory(params: TradeHistoryParams?): MutableList<FundingRecord> {
            TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
        }

        override fun createFundingHistoryParams(): TradeHistoryParams {
            TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
        }

        override fun getAccountInfo(): AccountInfo? {
            val infoPost = defaultApi.infoPost
            return infoPost.run {
                val funds = data.funds
                val holdFunds = data.holdFunds
                AccountInfo(
                        Wallet(exchangeSpecification.userName ?: "default",
                               funds.map { f ->
                                   when {
                                       (f.key in holdFunds) -> Balance(Currency(f.key), null, f.value,
                                                                       holdFunds[f.key])
                                       else -> Balance(Currency(f.key), null, f.value)
                                   }
                               }))
            }
        }
    }
}
}