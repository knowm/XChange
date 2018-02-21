package org.knowm.xchange.idex

import com.squareup.okhttp.*
import com.squareup.okhttp.internal.http.*
import org.knowm.xchange.*
import org.knowm.xchange.currency.*
import org.knowm.xchange.currency.Currency
import org.knowm.xchange.dto.*
import org.knowm.xchange.dto.Order.OrderType.*
import org.knowm.xchange.dto.account.*
import org.knowm.xchange.dto.marketdata.*
import org.knowm.xchange.dto.meta.*
import org.knowm.xchange.dto.trade.*
import org.knowm.xchange.idex.api.*
import org.knowm.xchange.idex.model.*
import org.knowm.xchange.idex.util.*
import org.knowm.xchange.service.account.*
import org.knowm.xchange.service.marketdata.*
import org.knowm.xchange.service.trade.*
import org.knowm.xchange.service.trade.params.*
import org.knowm.xchange.service.trade.params.orders.*
import org.knowm.xchange.utils.nonce.*
import java.math.*
import java.util.*

class IdexExchange : Exchange, BaseExchange() {

    override fun getExchangeSpecification(): ExchangeSpecification = this.exchangeSpecification
    val defaultApi = object : DefaultApi() {
        init {
            apiClient = object : ApiClient() {
                override fun buildRequest(path: String?, method: String?, queryParams: MutableList<Pair>?,
                                          collectionQueryParams: MutableList<org.knowm.xchange.idex.util.Pair>?,
                                          body: Any?,
                                          headerParams: MutableMap<String, String>?,
                                          formParams: MutableMap<String, Any>?,
                                          authNames: Array<out String>?,
                                          progressRequestListener: ProgressRequestBody.ProgressRequestListener?): Request {

                    val url = buildUrl(path, queryParams, collectionQueryParams)
                    val reqBuilder = Request.Builder().url(url)

                    var contentType = headerParams!!["Content-Type"] ?: "application/json"

                    val permitsRequestBody = HttpMethod.permitsRequestBody(method)
                    var json = ""
                    val reqBody = when {
                        permitsRequestBody -> {
                            val mediaType = MediaType.parse(contentType)
                            json = JSON().gson.toJson(formParams)
                            RequestBody.create(MediaType.parse(contentType), json)
                        }
                        else -> null
                    }

                    reqBuilder.headers(Headers.of(headerParams))

                    var request: Request? = null
                    updateParamsForAuth(authNames, queryParams, headerParams)

                    if (progressRequestListener != null && reqBody != null) {
                        val progressRequestBody = ProgressRequestBody(reqBody, progressRequestListener)
                        request = reqBuilder.method(method, progressRequestBody).build()
                    } else {
                        request = reqBuilder.method(method, reqBody).build()
                    }

                    return request
                }
            }
        }
    }
    override fun getNonceFactory() = AtomicLongIncrementalTime2014NonceFactory()

    override fun initServices() {
    }

    override fun getAccountService(): AccountService {
        return object : AccountService {
            /*override fun getAccountInfo(): AccountInfo {

                val apiKey = exchangeSpecification.apiKey
                val s = apiKey.slice(0.rangeTo(6)) + "…"
                val returnBalancesPost = DefaultApi().returnCompleteBalances(apiKey)
                val entries = returnBalancesPost.entries
                val toList = entries.toList()
                val list = toList.map {
                    val key = it.key!!
                    val value = it.value!!
                    val currency = Currency(key)
                    Balance(currency, null, value.available, value.onOrders)
                }
                val accountInfo = AccountInfo(Wallet(s, list))
                return accountInfo
            }
            */
            override fun getAccountInfo(): AccountInfo {

                val apiKey = exchangeSpecification.apiKey
                val s = apiKey.slice(0.rangeTo(6)) + "…"
                val returnBalancesPost = defaultApi.returnCompleteBalances(apiKey)
                val entries = returnBalancesPost.entries
                val toList = entries.toList()
                val list = toList.map {
                    val key = it.key!!
                    val value = it.value!!
                    val currency = Currency(key)
                    Balance(currency, null, value.available, value.onOrders)
                }
                val accountInfo = AccountInfo(Wallet(s, list))
                return accountInfo
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


            private fun returnTickerRequestedWithNull() = let {
                val returnTickerPost = DefaultApi().returnTickerPost(null)
                val gson = JSON().gson
                val toJson = gson.toJson(returnTickerPost)
                gson.fromJson(toJson, ReturnTickerRequestedWithNull::class.java)
            }


        }
    }

    override fun getDefaultExchangeSpecification() = ExchangeSpecification(IdexExchange::class.java).apply {
        exchangeDescription = "Idex.market exchange Kotlin based API client "
        exchangeName = "idex"

    }

    override fun getExchangeMetaData(): ExchangeMetaData = TODO(
            "not implemented") //To change body of created functions use File | Settings | File Templates.


    override fun remoteInit() {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }
/*
        override fun getExchangeSymbols(): MutableList<CurrencyPair> = returnTickerRequestedWithNull().map {
            it.key.split("_")
        }.map {
                    CurrencyPair(it[0], it[1])
                }.toMutableList()*/

    override fun getTradeService(): TradeService {
        return object : TradeService {
            override fun placeLimitOrder(limitOrder: LimitOrder?): String {
                TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
            }

            override fun createOpenOrdersParams(): OpenOrdersParams {
                TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
            }

            override fun createTradeHistoryParams(): TradeHistoryParams {
                TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
            }

            override fun placeStopOrder(stopOrder: StopOrder?): String {
                TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
            }

            override fun cancelOrder(orderId: String?): Boolean {
                TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
            }

            override fun cancelOrder(orderParams: CancelOrderParams?): Boolean {
                TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
            }

            override fun placeMarketOrder(marketOrder: MarketOrder?): String {
                TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
            }

            override fun getTradeHistory(params: TradeHistoryParams?): UserTrades {
                TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
            }

            override fun verifyOrder(limitOrder: LimitOrder?) {
                TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
            }

            override fun verifyOrder(marketOrder: MarketOrder?) {
                TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
            }

            override fun getOpenOrders(): OpenOrders {
                TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
            }

            override fun getOpenOrders(params: OpenOrdersParams?): OpenOrders {
                TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
            }

            override fun getOrder(vararg orderIds: String?): MutableCollection<Order> {
                TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
            }
        }
    }

    val gson = JSON().gson

    override fun getMarketDataService(): MarketDataService {
        return object : MarketDataService {
            override fun getTicker(currencyPair: CurrencyPair?, vararg args: Any?): Ticker {

                val returnTickerPost = DefaultApi().returnTickerPost(
                        "${currencyPair!!.base.symbol}_${currencyPair.counter.symbol}")
                val x: ReturnTickerResponse = gson.fromJson<ReturnTickerResponse>(gson.toJson(returnTickerPost),
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

                        val x = gson.fromJson<ReturnOrderBookResponse>(gson.toJson(returnOrderBookPost),
                                                                       ReturnOrderBookResponse::class.java)

                        return OrderBook(Date(),
                                         x.asks.map
                                         {
                                             LimitOrder(ASK, it.amount, currencyPair, it.orderHash,
                                                        Date(it.params.expires.toLong()), it.price)
                                         },
                                         x.bids.map {
                                             LimitOrder(ASK, it.amount, currencyPair, it.orderHash,
                                                        Date(it.params.expires.toLong()), it.price)
                                         })
                    }


                }

            }

            override fun getTrades(currencyPair: CurrencyPair?, vararg args: Any?): Trades {
                TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
            }
        }
    }

    override fun applySpecification(exchangeSpecification: ExchangeSpecification?) {
        this.exchangeSpecification = exchangeSpecification
    }


}

