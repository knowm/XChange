package org.knowm.xchange.idex

import org.knowm.xchange.currency.*
import org.knowm.xchange.dto.*
import org.knowm.xchange.dto.Order.OrderType.*
import org.knowm.xchange.dto.marketdata.*
import org.knowm.xchange.dto.trade.*
import org.knowm.xchange.idex.dto.*
import org.knowm.xchange.idex.service.*
import org.knowm.xchange.idex.util.*
import org.knowm.xchange.service.trade.*
import org.knowm.xchange.service.trade.params.*
import org.knowm.xchange.service.trade.params.orders.*
import org.knowm.xchange.utils.*
import java.math.*
import java.math.BigDecimal.*
import java.util.*

class IdexTradeService(val idexExchange: IdexExchange) : TradeService, TradeApi() {
    init {

        if (IdexExchange.debugMe) {
            apiClient = ApiClient()
            IdexExchange.setupDebug(apiClient)
        }
    }

    override fun cancelOrder(orderParams: CancelOrderParams?): Boolean {
        TODO("not an Idex option")
    }

    val apiKey get() = idexExchange.exchangeSpecification.apiKey
    private val idexServerNonce get() = nextNonce(NextNonceReq().address(apiKey)).nonce
    override fun placeLimitOrder(limitOrder1: LimitOrder): String {
        when (limitOrder1) {
            is IdexLimitOrder -> {
                val limitOrder: IdexLimitOrder = limitOrder1
                val apiKey = idexExchange.exchangeSpecification.apiKey
                order(OrderReq()
                              .address(apiKey)
                              .nonce(nextNonce(NextNonceReq().address(apiKey)).nonce)
                              .amountBuy(limitOrder.amountBuy)
                              .amountSell(limitOrder.amountSell)
                              .tokenBuy(limitOrder.tokenBuy)
                              .tokenSell(limitOrder.tokenSell)
                              .r(limitOrder.r)
                              .s(limitOrder.s)
                              .v(limitOrder.v)
                ).orderNumber
            }
        }
        TODO("This method requires " + IdexLimitOrder::class.java.canonicalName);
    }


    override fun createOpenOrdersParams() = OpenOrdersParams { it is IdexLimitOrder }


    override fun createTradeHistoryParams(): TradeHistoryParams = IdexTradeHistoryReq()

    override fun placeStopOrder(stopOrder: StopOrder?): String =
            throw org.knowm.xchange.exceptions.NotAvailableFromExchangeException()


    override fun cancelOrder(orderId: String) =
            cancel(CancelReq().run { orderHash(orderId).address(apiKey).nonce(idexServerNonce) }).success == 1


    override fun placeMarketOrder(marketOrder: MarketOrder): String =
            when (marketOrder) {
                is IdexMarketOrder -> {
                    val trade = trade(TradeReq().apply {
                        val m: IdexMarketOrder = marketOrder;
                        orderHash(m.id)
                                .amount(m.originalAmount.toPlainString())
                                .address(apiKey)
                                .nonce(idexServerNonce)
                                .r(m.r)
                                .s(m.s)
                                .v(m.v)
                    }
                    )
                    if (trade.isEmpty()) throw ApiException(
                            "Idex orders are done as all-or-nothing bulk arrays.  this trade " +
                                    "did not return any confirmations, all failed.")
                    trade.first().orderHash

                }
                else -> throw ApiException(
                        "Idex Market Orders require " + IdexMarketOrder::class.java.canonicalName)
            }

    class IdexTradeHistoryReq(val sort: Trades.TradeSortType? = Trades.TradeSortType.SortByTimestamp,
                              val lastId: Long? = null) : TradeHistoryParams, TradeHistoryReq()

    override fun getTradeHistory(params: TradeHistoryParams): UserTrades {
        if (params !is IdexTradeHistoryReq) {
            throw  ApiException("tradehistory requires " + IdexTradeHistoryReq::class.java.canonicalName)
        }

        val m = params.market.split("_")
        val currencyPair = CurrencyPair(m[0], m[1])
        val tradeHistory = MarketApi().tradeHistory(params)
        val map = tradeHistory.map {
            UserTrade(enumValueOf(it.type),
                      it.amount.toBigDecimalOrNull() ?: ZERO,
                      currencyPair,
                      it.price.toBigDecimalOrNull() ?: ZERO,
                      DateUtils.fromISO8601DateString(it.date),
                      it.uuid, it.orderHash, ZERO, currencyPair.base)
        }
        var ret: UserTrades? = null
        if (params.lastId != null)
            params.lastId.let { ret = UserTrades(map, it, params.sort) }
        return ret ?: UserTrades(map, params.sort)
    }

    override fun verifyOrder(
            limitOrder: LimitOrder?) = throw org.knowm.xchange.exceptions.NotAvailableFromExchangeException()


    override fun verifyOrder(
            marketOrder: MarketOrder?) = throw org.knowm.xchange.exceptions.NotAvailableFromExchangeException()

    override fun getOpenOrders(): OpenOrders = OpenOrders(openOrders(OpenOrdersReq().address(apiKey)).map {

        val c2 = it.params.run { listOf(buySymbol, sellSymbol) }
        LimitOrder(BID[it.type], it.amount.toBigDecimalOrNull() ?: ZERO,
                   CurrencyPair(c2[0], c2[1]), it.orderHash.toString(), Date(),
                   it.price.toBigDecimalOrNull() ?: ZERO)
    })

    override fun getOpenOrders(params: OpenOrdersParams): OpenOrders = OpenOrders(
            getOpenOrders().openOrders.filter(params::accept))

    override fun getOrder(vararg orderIds: String?): MutableCollection<Order> =
            getOpenOrders { it.id in orderIds }.openOrders.toMutableList()

}

class IdexMarketOrder(val r: String, val s: String, val v: String, type: OrderType?, originalAmount: BigDecimal?,
                      currencyPair: CurrencyPair?, id: String?, timestamp: Date?, averagePrice: BigDecimal?,
                      cumulativeAmount: BigDecimal?, fee: BigDecimal?, status: OrderStatus?) :
        MarketOrder(type, originalAmount, currencyPair, id, timestamp, averagePrice, cumulativeAmount, fee, status)

/**
v - ...
r - ...
s - (v r and s are the values produced by your private key signature, see above for details)
 */
public class IdexLimitOrder(type: OrderType?, originalAmount: BigDecimal?,
                            currencyPair: CurrencyPair?, id: String?,
                            timestamp: Date?, limitPrice: BigDecimal?,
                            val r: String,
                            val s: String,
                            val v: String,
                            val orderHash: String,
                            /**amountBuy (uint256) - The amount of the token you will receive when the order is fully filled
                             */
                            val amountBuy: String,
                            /**amountSell (uint256) - The amount of the token you will give up when the order is fully filled
                             */
                            val amountSell: String,
                            /**tokenBuy (address string) - The address of the token you will receive as a result of the trade
                             */
                            val tokenBuy: String,
                            /**tokenSell (address string) - The address of the token you will lose as a result of the trade
                             */
                            val tokenSell: String,
                            /**expires (uint256) - DEPRECATED this property has no effect on your limit order but is still REQUIRED to submit a limit order as it is one of the parameters that is hashed. It must be a numeric type
                             */
                            val expires: Long = Random().nextLong()

) :
        LimitOrder(type, originalAmount, currencyPair, id, timestamp, limitPrice)
