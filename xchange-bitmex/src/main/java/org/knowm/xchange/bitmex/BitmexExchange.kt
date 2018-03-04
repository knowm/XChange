package org.knowm.xchange.bitmex

import com.squareup.okhttp.*
import okio.*
import org.apache.commons.codec.digest.*
import org.knowm.xchange.*
import org.knowm.xchange.bitmex.service.*
import org.knowm.xchange.bitmex.util.*
import org.knowm.xchange.currency.*
import org.knowm.xchange.currency.Currency
import org.knowm.xchange.dto.*
import org.knowm.xchange.dto.account.*
import org.knowm.xchange.dto.marketdata.*
import org.knowm.xchange.dto.trade.*
import org.knowm.xchange.service.account.*
import org.knowm.xchange.service.marketdata.*
import org.knowm.xchange.service.trade.params.*
import org.knowm.xchange.utils.nonce.*
import java.math.*
import java.math.BigDecimal.*
import java.util.*
import java.util.logging.*

class BitmexExchange : Exchange, BaseExchange() {

    val xwfc = "application/x-www-form-urlencoded"


    val bitmexClient = object : ApiClient() {
        var debugMe = System.getProperty("XChangeDebug", "false") != "false"
        private val CONTENT_TYPE = MediaType.parse(xwfc)

        init {
            httpClient.interceptors().add(


                    Interceptor {
                        val request = it.request()
                        val hdr = request.headers().toMultimap()
                        val requestBuffer = Buffer()
                        request.body()?.writeTo(requestBuffer)
                        val body: String = requestBuffer.readUtf8()
                        val verb = request.method()
                        val encodedPath = request.httpUrl().encodedPath()

                        val apiSecret = exchangeSpecification.secretKey

                        hdr["api-key"] = listOf(exchangeSpecification.apiKey)

                        val expires = "" + (System.currentTimeMillis() + 5_000) / 1000
                        hdr["api-expires"] = listOf(expires)
                        val path1 = pathRegex.replace(request.url().toExternalForm(), "$1")
                        hdr["api-signature"] = listOf(HmacUtils.hmacSha256Hex(apiSecret, verb + path1 + expires + body))

                        val doppleganger = request.newBuilder().headers(
                                Headers.of(hdr.entries.associate { it.key to it.value.first() }))
                        val build = doppleganger.build()
                        val response = it.proceed(build)
                        if (debugMe) Logger.getAnonymousLogger().info(
                                String.format("<-- Received response " + response.code() +
                                                      " for " +
                                                      response.request().url() + "--\n" +
                                                      response.headers())
                        )

                        val contentType = response.body().contentType()
                        val content = response.body().string()
                        if (debugMe) Logger.getAnonymousLogger().info(content)

                        val wrappedBody = ResponseBody.create(contentType, content)
                        response.newBuilder().body(wrappedBody).build()

                    })
        }


        val pathRegex = Regex(pattern = ".*://[^/]*(/[^?#]+.*)")
    }

    override fun getMarketDataService(): MarketDataService =
            object : MarketDataService {
                val orderBookApi = OrderBookApi(bitmexClient)
                val tradeApi = TradeApi(bitmexClient)
                val instrumentApi = InstrumentApi(bitmexClient)
                override fun getTicker(currencyPair: CurrencyPair?, vararg args: Any?): Ticker {
                    TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
                }

                override fun getOrderBook(currencyPair: CurrencyPair, vararg args: Any?): OrderBook {

                    val orderBookGetL2 = orderBookApi.orderBookGetL2("${currencyPair.base}_${currencyPair.base}",
                                                                     args.run {
                                                                         when (args.isNotEmpty()) {
                                                                             true -> args[0].toString().toBigDecimal()
                                                                             else -> ZERO
                                                                         }
                                                                     }
                    );

                    val ob = orderBookGetL2.map {
                        LimitOrder(Order.OrderType.BID[it.side], it.size, CurrencyPair(it.symbol),
                                   it.id.toPlainString(), null, it.price.toBigDecimal())
                    }
                    return OrderBook(
                            null,
                            ob.filter { it.type == Order.OrderType.ASK },
                            ob.filter { it.type == Order.OrderType.BID }
                    )
                }

                override fun getTrades(currencyPair: CurrencyPair?, vararg args: Any?): Trades {
                    TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
                }


            }


    override fun getAccountService(): AccountService {

        return object : AccountService, UserApi(bitmexClient) {

            override fun withdrawFunds(currency: Currency?, amount: BigDecimal?, address: String?): String {
                TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
            }

            override fun withdrawFunds(params: WithdrawFundsParams?): String {
                TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
            }

            override fun requestDepositAddress(currency: Currency?, vararg args: String?): String {
                TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
            }

            override fun createFundingHistoryParams(): TradeHistoryParams {
                TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
            }

            override fun getFundingHistory(params: TradeHistoryParams?): MutableList<FundingRecord> {
                TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
            }

            override fun getAccountInfo(): AccountInfo {
                val xBt = userGetWallet(null);
                val wallet = org.knowm.xchange.dto.account.Wallet(
                        "" + xBt.getAccount(),
                        Arrays.asList(Balance(org.knowm.xchange.currency.Currency.BTC, xBt.getAmount().divide(
                                BigDecimal.valueOf(100_000_000L))))
                );
                return AccountInfo(wallet);
            }
        }

    }


    override fun getNonceFactory() = AtomicLongIncrementalTime2014NonceFactory()


    override fun initServices() {

    }

    override fun getDefaultExchangeSpecification() = ExchangeSpecification(
            BitmexExchange::class.java).apply {
        exchangeDescription = "Bitmex exchange Kotlin based API client "
        exchangeName = "bitmex"

    }

}

operator fun Order.OrderType.get(side: String): Order.OrderType {
    val simplest: Order.OrderType? = Order.OrderType.valueOf(side.toUpperCase())
    return simplest ?: when (side.toLowerCase()) {
        "buy" -> Order.OrderType.BID
        "sell" -> Order.OrderType.ASK;
        else -> TODO("update Ordertype parser ")

    }
}

/**
 * Delivery dates for future date currencies
 */
enum class BitmexPrompt {
    perpetual, weekly, monthly, quarterly, biquarterly
}

fun main(args: Array<String>) {
    val apiKey = "LAqUlngMIQkIUjXMUreyu3qn"
    val apiSecret = "chNOOS4KvNXR_Xq4k4c9qsfoKWvnDecLATCRlcBwyKDYnWgO"

    var verb = "GET"
//    # Note url-encoding on querystring - this is '/api/v1/instrument?filter={"symbol": "XBTM15"}'
    var path = "/api/v1/instrument"
    var expires = 1518064236 //# 2018-02-08T04:30:36Z
    var data = ""
    val hmacSha256Hex = HmacUtils.hmacSha256Hex(apiSecret, verb + path + expires + data)
    System.err.println(hmacSha256Hex)


}