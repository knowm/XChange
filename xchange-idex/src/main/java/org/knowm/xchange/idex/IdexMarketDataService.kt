package org.knowm.xchange.idex

import com.squareup.okhttp.*
import okio.*
import org.knowm.xchange.currency.*
import org.knowm.xchange.dto.Order.*
import org.knowm.xchange.dto.Order.OrderType.*
import org.knowm.xchange.dto.marketdata.*
import org.knowm.xchange.dto.trade.*
import org.knowm.xchange.idex.dto.*
import org.knowm.xchange.idex.service.*
import org.knowm.xchange.idex.util.*
import org.knowm.xchange.service.marketdata.*
import org.knowm.xchange.utils.*
import java.math.BigDecimal.*
import java.util.*
import java.util.logging.*


class IdexMarketDataService(private val idexExchange: IdexExchange) : MarketDataService, MarketApi() {
    init {

        if (IdexExchange.debugMe) {
            apiClient = ApiClient()
            IdexExchange.setupDebug(apiClient)
        }
    }

    override fun getTicker(currencyPair: CurrencyPair, vararg args: Any?): Ticker =
            ticker(currencyPair.market)[currencyPair]

    override fun getOrderBook(currencyPair: CurrencyPair, vararg args: Any?): OrderBook =
            orderBook(currencyPair.orderbook)
                    .run {
                        OrderBook(Date(),
                                  asks.map {
                                      LimitOrder(ASK, it.amount.toBigDecimal() ?: ZERO, currencyPair,
                                                 it.orderHash,
                                                 Date(it.params.expires.toLong()), it.price.toBigDecimal() ?: ZERO)
                                  },
                                  bids.map {
                                      LimitOrder(BID, it.amount.toBigDecimalOrNull() ?: ZERO,
                                                 currencyPair,
                                                 it.orderHash,
                                                 Date(it.params.expires.toLong()), it.price.toBigDecimal() ?: ZERO)
                                  })
                    }

    override fun getTrades(currencyPair: CurrencyPair, vararg args: Any?) = Trades(MarketApi().tradeHistory(
            TradeHistoryReq().market(currencyPair.idexMkt)).map { tradeHistoryItem ->


        Trade(ASK[tradeHistoryItem.type], tradeHistoryItem.amount.toBigDecimalOrNull() ?: ZERO, currencyPair,
              tradeHistoryItem.price.toBigDecimalOrNull() ?: ZERO, DateUtils.fromISO8601DateString(
                tradeHistoryItem.date.replace(" ", "T") + "Z")
                      .also {
                          if (debugMe && 0 == (debugDateCounter++)) System.err.println(
                                  tradeHistoryItem.date + " = " + it)
                      }, tradeHistoryItem.transactionHash)
    })

    fun getIdexCurrencyInfo(): Any {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    fun getAllIdexTickers(): Any {
//        val volume24 = MarketApi().volume24(Volume24Req().market("ETH_REP"))
        TODO()
    }
}

private fun testVol24() {
//this method is dead on arrival
//    jim@mp$ curl -XPOST https://api.idex.market/return24Volume -d"{}"
//    {"error":"Cannot read property '0' of null"}
    val marketApi = MarketApi()
    marketApi.apiClient.httpClient.interceptors().add(Interceptor { chain ->
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
    })

}

operator fun OrderType.get(type: String): OrderType {
    return when (type) {
        "buy" -> BID
        "sell" -> ASK
        else -> TODO("need to parse for ordertype" + type)
    }
}

val CurrencyPair.idexMkt get() = "${base.symbol}_${counter.symbol}"
val CurrencyPair.tradeReq inline get() = TradeHistoryReq().market(idexMkt)
val CurrencyPair.market inline get() = Market().market(idexMkt)
val CurrencyPair.orderbook inline get() = OrderBookReq().market(idexMkt)

/**
 * returns XChange Trade
 *
 */
operator fun TradeHistoryItem.get(currencyPair: CurrencyPair) = Trade.Builder()
        .currencyPair(currencyPair)
        .id(orderHash)
        .originalAmount(amount.toBigDecimalOrNull() ?: ZERO)
        .price(price.toBigDecimalOrNull() ?: ZERO)
        .timestamp(DateUtils.fromISO8601DateString(date))
        .type(ASK[type])
        .build()

/**
 * returns XChange Ticker
 */
operator fun ReturnTickerResponse.get(currencyPair: CurrencyPair) = Ticker.Builder()
        .currencyPair(currencyPair)
        .timestamp(Date())
        .open(last.toBigDecimalOrNull())
        .ask(lowestAsk.toBigDecimalOrNull() ?: ZERO)
        .bid(highestBid.toBigDecimalOrNull() ?: ZERO)
        .last(last.toBigDecimalOrNull() ?: ZERO)
        .high(high.toBigDecimalOrNull() ?: ZERO)
        .low(low.toBigDecimalOrNull() ?: ZERO)
        .volume(baseVolume.toBigDecimalOrNull() ?: ZERO)
        .quoteVolume(quoteVolume.toBigDecimalOrNull() ?: ZERO)
        .build()

var debugMe = "true" == System.getProperty("XChangeDebug", "false")
var debugDateCounter = 0;