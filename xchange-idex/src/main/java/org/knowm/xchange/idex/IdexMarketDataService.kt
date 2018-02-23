package org.knowm.xchange.idex

import org.knowm.xchange.currency.*
import org.knowm.xchange.dto.*
import org.knowm.xchange.dto.marketdata.*
import org.knowm.xchange.dto.trade.*
import org.knowm.xchange.idex.dto.*
import org.knowm.xchange.idex.service.*
import org.knowm.xchange.service.marketdata.*
import org.knowm.xchange.utils.*
import java.math.BigDecimal.*
import java.util.*

class IdexMarketDataService(private val idexExchange: IdexExchange) : MarketDataService, MarketApi() {
    override fun getTicker(currencyPair: CurrencyPair, vararg args: Any?): Ticker {
        return ticker(currencyPair.market)[currencyPair]
    }

    override fun getOrderBook(currencyPair: CurrencyPair, vararg args: Any?) =
            orderBook(currencyPair.orderbook)
                    .run {
                        OrderBook(Date(),
                                  asks.map {
                                      LimitOrder(Order.OrderType.ASK, it.amount.toBigDecimal()?:ZERO, currencyPair,
                                                 it.orderHash,
                                                 Date(it.params.expires.toLong()), it.price.toBigDecimal()?:ZERO)
                                  },
                                  bids.map {
                                      LimitOrder(Order.OrderType.BID, it.amount.toBigDecimalOrNull()?:ZERO, currencyPair,
                                                 it.orderHash,
                                                 Date(it.params.expires.toLong()), it.price.toBigDecimal()?:ZERO)
                                  })
                    }

    override fun getTrades(currencyPair: CurrencyPair, vararg args: Any?) = Trades(
            tradeHistory(currencyPair.tradeReq).map { it[currencyPair] })

}
val CurrencyPair.idexMkt get() =    "${base.symbol}_${counter.symbol}"
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
        .type(Order.OrderType.valueOf(type.toUpperCase()))
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