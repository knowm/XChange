package org.knowm.xchange.idex

import com.squareup.okhttp.*
import com.squareup.okhttp.Interceptor
import okio.*
import org.knowm.xchange.*
import org.knowm.xchange.dto.meta.*
import org.knowm.xchange.idex.dto.*
import org.knowm.xchange.idex.util.*
import org.knowm.xchange.utils.nonce.*
import si.mazi.rescu.*
import java.util.logging.*

class IdexExchange : Exchange, BaseExchange() {
    private val idexAccountService by lazy { -> IdexAccountService(this) }
    private val idexTradeService by lazy { IdexTradeService(this) }
    private val idexMarketDataService by lazy { IdexMarketDataService(this) }

    override fun getAccountService(): IdexAccountService = idexAccountService
    override fun getTradeService(): IdexTradeService = idexTradeService
    override fun getMarketDataService(): IdexMarketDataService = idexMarketDataService

    override fun getExchangeSpecification(): ExchangeSpecification = this.exchangeSpecification

    /*was AtomicLongIncrementalTime2014NonceFactory()*/
    override fun getNonceFactory() =  SynchronizedValueFactory<Long>{idexTradeService.nextNonce(
            NextNonceReq()).nonce.toLong()}

    override fun initServices() = Unit
    override fun getExchangeMetaData(): ExchangeMetaData = ExchangeMetaData(
            emptyMap(),
            emptyMap(),
            emptyArray(),
            emptyArray(),
            true)

    override fun applySpecification(exchangeSpecification: ExchangeSpecification?) {
        this.exchangeSpecification = exchangeSpecification
    }

    override fun getDefaultExchangeSpecification() = ExchangeSpecification(IdexExchange::class.java).apply {
        exchangeDescription = "Idex.market exchange Kotlin based API client "
        exchangeName = "idex"
    }

    override fun remoteInit() {
        // tbd
    }

    companion object {
        val gson by lazy { JSON().gson }

        /**
         * if you need to debug the REST api calls use -DXChangeDebug=true
         */

        val debugMe: Boolean by lazy { "true" == System.getProperty("XChangeDebug", "false") }

        fun setupDebug(apiClient: ApiClient) {
            val element = debugInterceptor
            apiClient!!.httpClient.interceptors().add(debugInterceptor)
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
}


