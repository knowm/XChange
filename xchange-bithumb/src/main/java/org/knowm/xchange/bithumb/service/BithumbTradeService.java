package org.knowm.xchange.bithumb.service;

import org.knowm.xchange.bithumb.BithumbAdapters;
import org.knowm.xchange.bithumb.dto.account.BithumbOrder;
import org.knowm.xchange.bithumb.dto.trade.BithumbOpenOrdersParam;
import org.knowm.xchange.dto.trade.LimitOrder;
import org.knowm.xchange.dto.trade.MarketOrder;
import org.knowm.xchange.dto.trade.OpenOrders;
import org.knowm.xchange.dto.trade.UserTrades;
import org.knowm.xchange.service.trade.TradeService;
import org.knowm.xchange.service.trade.params.CancelOrderByIdParams;
import org.knowm.xchange.service.trade.params.CancelOrderParams;
import org.knowm.xchange.service.trade.params.TradeHistoryParams;
import org.knowm.xchange.service.trade.params.orders.OpenOrdersParams;

import java.io.IOException;
import java.util.List;

public class BithumbTradeService extends BithumbTradeServiceRaw implements TradeService {

    @Override
    public OpenOrders getOpenOrders() throws IOException {
        return getOpenOrders(null);
    }

    @Override
    public OpenOrders getOpenOrders(OpenOrdersParams params) throws IOException {
        final List<BithumbOrder> bithumbOrders = getBithumbOrders((BithumbOpenOrdersParam) params);
        return BithumbAdapters.adaptOrders(bithumbOrders);
    }

    @Override
    public String placeMarketOrder(MarketOrder marketOrder) throws IOException {
        return placeBithumbMarketOrder(marketOrder).getOrderId();
    }

    @Override
    public String placeLimitOrder(LimitOrder limitOrder) throws IOException {
        return placeBithumbLimitOrder(limitOrder).getOrderId();
    }

    @Override
    public boolean cancelOrder(String orderId) throws IOException {
        return cancelBithumbOrder(orderId);
    }

    @Override
    public boolean cancelOrder(CancelOrderParams orderParams) throws IOException {

        if (orderParams instanceof CancelOrderByIdParams) {
            return cancelOrder(((CancelOrderByIdParams) orderParams).getOrderId());
        } else {
            return false;
        }
    }

    @Override
    public UserTrades getTradeHistory(TradeHistoryParams params) throws IOException {
        return BithumbAdapters.adaptUserTrades(
                bithumbTransactions((BithumbTradeHistoryParams) params),
                ((BithumbTradeHistoryParams) params).getCurrencyPair());
    }

    @Override
    public TradeHistoryParams createTradeHistoryParams() {
        return new BithumbTradeHistoryParams();
    }

    @Override
    public OpenOrdersParams createOpenOrdersParams() {
        return new BithumbOpenOrdersParam();
    }
}
