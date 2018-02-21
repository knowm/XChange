package org.knowm.xchange.idex

import org.knowm.xchange.dto.*
import org.knowm.xchange.dto.trade.*
import org.knowm.xchange.service.trade.*
import org.knowm.xchange.service.trade.params.*
import org.knowm.xchange.service.trade.params.orders.*

class IdexTradeService : TradeService {
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