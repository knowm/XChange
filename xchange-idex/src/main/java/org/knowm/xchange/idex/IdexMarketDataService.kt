package org.knowm.xchange.idex

import org.knowm.xchange.currency.*
import org.knowm.xchange.dto.*
import org.knowm.xchange.dto.marketdata.*
import org.knowm.xchange.dto.trade.*
import org.knowm.xchange.idex.dto.*
import org.knowm.xchange.idex.service.*
import org.knowm.xchange.service.marketdata.*
import java.util.*

class IdexMarketDataService(private val idexExchange: IdexExchange) :
        MarketDataService {
    override fun getTicker(currencyPair: CurrencyPair  , vararg args: Any?): Ticker {

        val returnTickerPost = DefaultApi().returnTickerPost(
                "${currencyPair .base.symbol}_${currencyPair.counter.symbol}")
        val x: ReturnTickerResponse = idexExchange.gson.fromJson<ReturnTickerResponse>(
                idexExchange.gson.toJson(returnTickerPost),
                ReturnTickerResponse::class.java)
        return Ticker.Builder()
                .currencyPair(currencyPair)
                .ask(x.lowestAsk)
                .bid(x.highestBid)
                .last(x.last)
                .high(x.high)
                .low(x.low)
                .volume(x.baseVolume)
                .quoteVolume(x.quoteVolume)
                .build()
        /* else {
            val x = DefaultApi().returnTickerPost(null)
            val str = idexExchange.gson.toJson(x)
            System.err.println(str)
            val fromJson = idexExchange.gson.fromJson<ReturnTickerRequestedWithNull>(str,
                                                                                     ReturnTickerRequestedWithNull::class.java)
            TODO( "find the right call for "+fromJson )
        }*/

    }

    override fun getOrderBook(currencyPair: CurrencyPair?, vararg args: Any?): OrderBook {
        val returnOrderBookPost = DefaultApi().returnOrderBookPost(
                if (currencyPair != null)
                    currencyPair.let { currencyPair.base.symbol + "_" + currencyPair.counter.symbol }
                else null)
        when {
            null == currencyPair -> {
                TODO("this method only supports paraterized calls")
            }
            else -> {

                val x = idexExchange.gson.fromJson<ReturnOrderBookResponse>(
                        idexExchange.gson.toJson(returnOrderBookPost),
                        ReturnOrderBookResponse::class.java)

                return OrderBook(Date(),
                                 x.asks.map
                                 {
                                     LimitOrder(
                                             Order.OrderType.ASK,
                                             it.amount, currencyPair, it.orderHash,
                                             Date(
                                                     it.params.expires.toLong()),
                                             it.price)
                                 },
                                 x.bids.map {
                                     LimitOrder(
                                             Order.OrderType.BID,
                                             it.amount, currencyPair, it.orderHash,
                                             Date(
                                                     it.params.expires.toLong()),
                                             it.price)
                                 })
            }
        }
    }

    override fun getTrades(currencyPair: CurrencyPair?, vararg args: Any?): Trades {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }
}