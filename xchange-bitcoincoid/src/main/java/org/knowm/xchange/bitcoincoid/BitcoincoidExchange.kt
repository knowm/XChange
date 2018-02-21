package org.knowm.xchange.bitcoincoid

import com.squareup.okhttp.*
import okio.*
import org.apache.commons.codec.digest.*
import org.knowm.xchange.*
import org.knowm.xchange.bitcoincoid.api.*
import org.knowm.xchange.bitcoincoid.util.*
import org.knowm.xchange.currency.*
import org.knowm.xchange.dto.account.*
import org.knowm.xchange.service.account.*
import org.knowm.xchange.service.trade.params.*
import org.knowm.xchange.utils.nonce.*
import java.io.*
import java.math.*
import java.util.logging.*


class BitcoincoidExchange : Exchange, BaseExchange() {
    override fun getNonceFactory() = AtomicLongIncrementalTime2014NonceFactory()


    override fun initServices() {

    }

    override fun getDefaultExchangeSpecification() = ExchangeSpecification(
            BitcoincoidExchange::class.java).apply {
        exchangeDescription = "Bitcoin.co.id exchange Kotlin based API client "
        exchangeName = "bitcoincoid"

    }

    private val xwfc = "application/x-www-form-urlencoded"

    val viewApi: ViewApi = object : ViewApi() {

        private val bcoidApiClient = object : ApiClient() {
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
                val frmFlds = linkedMapOf<String, Any>()
                formParams?.let(frmFlds::putAll)
                frmFlds["nonce"] = nonceFactory.createValue().toString()
                frmFlds["method"] = path!!
                val hdr = linkedMapOf<String, String>()
                headerParams?.let(hdr::putAll)

                val payload = frmFlds.map {
                    listOf(it.key, it.value.toString()).joinToString("=")
                }.joinToString("&")

                val reqbody = RequestBody.create(CONTENT_TYPE, payload)

                hdr["Key"] = exchangeSpecification.apiKey
                hdr["Sign"] = HmacUtils.hmacSha512Hex(exchangeSpecification.secretKey, payload)

                val request = Request.Builder()
                        .url(buildUrl("", queryParams, collectionQueryParams))
                        .headers(Headers.of(hdr))
                        .post(reqbody)
                        .build()
                return request
            }
        }

        init {
            apiClient = bcoidApiClient
        }
    }

    override fun getAccountService(): AccountService {
        return object : AccountService {
            override fun getAccountInfo(): AccountInfo {
                val infoPost = viewApi.infoPost
                val apiInfoResponseReturn = infoPost.`return`
                val balance = apiInfoResponseReturn.balance
                val map = balance.map { entry ->
                    val key = entry.key
                    val value1 = entry.value
                    Balance(Currency(key), value1)
                }

                return AccountInfo(Wallet(map))
            }

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

        }
    }


}