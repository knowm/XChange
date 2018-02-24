package org.knowm.xchange.idex

import org.knowm.xchange.currency.*
import org.knowm.xchange.dto.*
import org.knowm.xchange.dto.marketdata.*
import org.knowm.xchange.dto.trade.*
import org.knowm.xchange.idex.dto.*
import org.knowm.xchange.idex.service.*
import org.knowm.xchange.idex.util.*
import org.knowm.xchange.service.trade.*
import org.knowm.xchange.service.trade.params.*
import org.knowm.xchange.service.trade.params.orders.*
import org.knowm.xchange.utils.*
import org.web3j.protocol.*
import org.web3j.protocol.core.methods.response.*
import java.math.BigDecimal.*
import java.util.*

class IdexTradeService(val idexExchange: IdexExchange) : TradeService, TradeApi() {
    override fun placeLimitOrder(limitOrder: LimitOrder): String {
        TODO()
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

    override fun verifyOrder(limitOrder: LimitOrder?) {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    override fun verifyOrder(marketOrder: MarketOrder?) {
//        Web3jService()
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

private val TradeHistoryParams.orderTrades: OrderTradesReq
    get() {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }
