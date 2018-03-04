package org.knowm.xchange.idex

import org.knowm.xchange.currency.*
import org.knowm.xchange.currency.Currency
import org.knowm.xchange.dto.Order.*
import org.knowm.xchange.dto.Order.OrderType.*
import org.knowm.xchange.dto.marketdata.*
import org.knowm.xchange.dto.trade.*
import org.knowm.xchange.idex.dto.*
import org.knowm.xchange.idex.service.*
import org.knowm.xchange.idex.util.*
import org.knowm.xchange.service.marketdata.*
import org.knowm.xchange.utils.*
import java.io.*
import java.math.BigDecimal.*
import java.net.*
import java.util.*
import java.util.zip.*
import javax.net.ssl.*


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



    companion object {
        val allTickers by lazy { IdexMarketDataService.allTickersStatic() };
        val allCounter by lazy {
            allTickers.keys.map { it.split('_')[0] }.distinct().sorted().map(::Currency)
        }
        val allBase by lazy {
            allTickers.keys.map { it.split('_')[1] }.distinct().sorted().map(::Currency)
        }
        val allCurrencies by lazy{ allCurrenciesStatic()}
        /**same as  curl -XPOST https://api.idex.market/returnTicker
         */
        fun allTickersStatic(): ReturnTickerRequestedWithNull {
            val c: javax.net.ssl.HttpsURLConnection = URL(
                    "https://api.idex.market/returnTicker").openConnection() as HttpsURLConnection
            c.requestMethod = "POST"
            c.setRequestProperty("Accept-Encoding", "gzip");
            c.setRequestProperty("User-Agent", "irrelevant")
            val inputStream = c.inputStream
            return IdexExchange.gson.fromJson(InputStreamReader(GZIPInputStream(inputStream)),
                                              ReturnTickerRequestedWithNull::class.java).also { inputStream.close() }

        }

        fun allCurrenciesStatic(): ReturnCurrenciesResponse  {
            val c: javax.net.ssl.HttpsURLConnection = URL(
                    "https://api.idex.market/returnCurrencies").openConnection() as HttpsURLConnection
            c.requestMethod = "POST"
            c.setRequestProperty("Accept-Encoding", "gzip");
            c.setRequestProperty("User-Agent", "irrelevant")
            val inputStream = c.inputStream
            return IdexExchange.gson.fromJson(InputStreamReader(GZIPInputStream(inputStream)),
                                              ReturnCurrenciesResponse::class.java).also { inputStream.close() }
        }

        val CurrencyPair.idexMkt get() = "${counter.symbol}_${base.symbol}"
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
    }
}
