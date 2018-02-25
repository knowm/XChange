package org.knowm.xchange.idex

import com.squareup.okhttp.*
import okio.*
import org.knowm.xchange.*
import org.knowm.xchange.dto.meta.*
import org.knowm.xchange.idex.util.*
import org.knowm.xchange.service.account.*
import org.knowm.xchange.service.marketdata.*
import org.knowm.xchange.service.trade.*
import org.knowm.xchange.utils.nonce.*
import java.util.logging.*

class IdexExchange : Exchange, BaseExchange() {
    override fun getExchangeSpecification(): ExchangeSpecification = this.exchangeSpecification
    override fun getNonceFactory() = AtomicLongIncrementalTime2014NonceFactory()
    override fun initServices() = Unit
    override fun getExchangeMetaData(): ExchangeMetaData = TODO("not implemented")
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

    override fun getAccountService(): AccountService = IdexAccountService(this)
    override fun getTradeService(): TradeService = IdexTradeService(this)
    override fun getMarketDataService(): MarketDataService = IdexMarketDataService(this)

    companion object {
        val gson = JSON().gson

        val debugMe: Boolean by lazy { "true" == System.getProperty("XChangeDebug", "false") }

        fun setupDebug(apiClient: ApiClient) {
            val element = debugInterceptor
            apiClient!!.httpClient.interceptors().add(debugInterceptor)
        }
    }
}


/**
 * also one exists for gzip variant on SO as well
 */
private val debugInterceptor = Interceptor { chain ->
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