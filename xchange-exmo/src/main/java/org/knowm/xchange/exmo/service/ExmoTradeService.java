package org.knowm.xchange.exmo.service;

import org.knowm.xchange.currency.CurrencyPair;
import org.knowm.xchange.dto.Order;
import org.knowm.xchange.dto.marketdata.Trades;
import org.knowm.xchange.dto.trade.LimitOrder;
import org.knowm.xchange.dto.trade.MarketOrder;
import org.knowm.xchange.dto.trade.OpenOrders;
import org.knowm.xchange.dto.trade.StopOrder;
import org.knowm.xchange.dto.trade.UserTrade;
import org.knowm.xchange.dto.trade.UserTrades;
import org.knowm.xchange.exceptions.ExchangeException;
import org.knowm.xchange.exceptions.NotYetImplementedForExchangeException;
import org.knowm.xchange.exmo.ExmoExchange;
import org.knowm.xchange.service.trade.TradeService;
import org.knowm.xchange.service.trade.params.CancelOrderByIdParams;
import org.knowm.xchange.service.trade.params.CancelOrderParams;
import org.knowm.xchange.service.trade.params.TradeHistoryParamCurrencyPair;
import org.knowm.xchange.service.trade.params.TradeHistoryParamLimit;
import org.knowm.xchange.service.trade.params.TradeHistoryParamOffset;
import org.knowm.xchange.service.trade.params.TradeHistoryParams;
import org.knowm.xchange.service.trade.params.orders.OpenOrdersParams;

import java.io.IOException;
import java.util.Collection;
import java.util.List;
import java.util.Map;

public class ExmoTradeService extends ExmoTradeServiceRaw implements TradeService {
    public ExmoTradeService(ExmoExchange exmoExchange) {
        super(exmoExchange);
    }

    @Override
    public OpenOrders getOpenOrders() throws IOException {
        return getOpenOrders(null);
    }

    @Override
    public OpenOrders getOpenOrders(OpenOrdersParams params) throws IOException {
        return new OpenOrders(openOrders());
    }

    @Override
    public String placeLimitOrder(LimitOrder limitOrder) throws IOException {
        String type = limitOrder.getType().equals(Order.OrderType.BID) ? "buy" : "sell";

        Map map = exmo.orderCreate(
                signatureCreator, apiKey, exchange.getNonceFactory(),
                format(limitOrder.getCurrencyPair()),
                limitOrder.getOriginalAmount(),
                limitOrder.getLimitPrice(),
                type
        );

        Boolean result = (Boolean) map.get("result");
        if (!result)
            throw new ExchangeException("Failed to place order: " + map.get("error"));

        return map.get("order_id").toString();
    }

    @Override
    public boolean cancelOrder(String orderId) throws IOException {
        return cancelOrder((CancelOrderByIdParams) () -> orderId);
    }

    @Override
    public boolean cancelOrder(CancelOrderParams orderParams) throws IOException {
        if (orderParams instanceof CancelOrderByIdParams) {
            CancelOrderByIdParams params = (CancelOrderByIdParams) orderParams;
            String orderId = params.getOrderId();
            Map map = exmo.orderCancel(signatureCreator, apiKey, exchange.getNonceFactory(), orderId);
            return (boolean) map.get("result");
        }

        return false;
    }

    @Override
    public UserTrades getTradeHistory(TradeHistoryParams params) throws IOException {
        int limit = 10000;
        long offset = 0;
        CurrencyPair currencyPair = null;

        if (params instanceof TradeHistoryParamCurrencyPair) {
            TradeHistoryParamCurrencyPair tradeHistoryParamCurrencyPair = (TradeHistoryParamCurrencyPair) params;
            currencyPair = tradeHistoryParamCurrencyPair.getCurrencyPair();
        }

        if (params instanceof TradeHistoryParamLimit) {
            limit = ((TradeHistoryParamLimit) params).getLimit();
        }

        if (params instanceof TradeHistoryParamOffset) {
            offset = ((TradeHistoryParamOffset) params).getOffset();
        }

        List<UserTrade> trades = trades(limit, offset, currencyPair);

        return new UserTrades(trades, Trades.TradeSortType.SortByTimestamp);
    }
}
