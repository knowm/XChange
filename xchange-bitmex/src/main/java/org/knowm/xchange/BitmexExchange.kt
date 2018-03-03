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

    private val xwfc = "application/x-www-form-urlencoded"

    private val bitmexClient = object : ApiClient() {
        var debugMe = System.getProperty("XChangeDebug", "false") != "false"

        private val CONTENT_TYPE = MediaType.parse(xwfc)
        override fun buildRequest(path: String?, method: String?, queryParams: MutableList<Pair>?,
                                  collectionQueryParams: MutableList<Pair>?, body: Any?,
                                  headerParams: MutableMap<String, String>?, formParams: MutableMap<String, Any>?,
                                  authNames: Array<out String>?,
                                  progressRequestListener: ProgressRequestBody.ProgressRequestListener?): Request? {
            if (debugMe)
                httpClient.interceptors().add(Interceptor { chain ->
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
                    response.newBuilder().body(wrappedBody).build()
                })
            debugMe = false;
            val frmFlds = linkedMapOf<String, Any>()
            formParams?.let(frmFlds::putAll)
//            frmFlds["method"] = path!!
            val hdr = linkedMapOf<String, String>()
            headerParams?.let(hdr::putAll)

//            authentications["apiKey"] = ApiKeyAuth("header", "api-key")
//            authentications["apiNonce"] = ApiKeyAuth("header", "api-nonce")
//            authentications["apiSignature"] = ApiKeyAuth("header", "api-signature")


            val payload = frmFlds.map {
                listOf(it.key, it.value.toString()).joinToString("=")
            }.joinToString("&")

            val reqbody = RequestBody.create(CONTENT_TYPE, payload)
            val apiSecret = exchangeSpecification.secretKey

            hdr["apiKey"] = exchangeSpecification.apiKey
            hdr["apiSignature"] = HmacUtils.hmacSha256Hex(apiSecret, payload)
/*           Our reference market maker bot features a working implementation of our API key authentication.


           Note: Previous versions of this document descripted an api-nonce value, which is a value that should increase between the bounds of 0 and 253. This scheme, while still supported, has significant problems with multithreaded clients and should not be used. Do not use it for new applications.
*/
            val expires = "" + System.currentTimeMillis() + 30_000
            hdr["apiExpires"] = expires
            val verb = "POST"
            HmacUtils.hmacSha256Hex(apiSecret, verb + path + expires + payload)
            val request = Request.Builder()
                    .url(buildUrl("", queryParams, collectionQueryParams))
                    .headers(Headers.of(hdr))
                    .post(reqbody)
                    .build()
            return request
        }
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
