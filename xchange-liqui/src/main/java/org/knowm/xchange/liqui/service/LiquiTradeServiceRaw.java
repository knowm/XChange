package org.knowm.xchange.liqui.service;

import org.knowm.xchange.Exchange;
import org.knowm.xchange.currency.CurrencyPair;
import org.knowm.xchange.dto.trade.LimitOrder;
import org.knowm.xchange.liqui.Liqui;
import org.knowm.xchange.liqui.dto.LiquiTradeType;
import org.knowm.xchange.liqui.dto.trade.result.LiquiActiveOrdersResult;
import org.knowm.xchange.liqui.dto.trade.result.LiquiCancelOrderResult;
import org.knowm.xchange.liqui.dto.trade.result.LiquiOrderInfoResult;
import org.knowm.xchange.liqui.dto.trade.result.LiquiTradeHistoryResult;
import org.knowm.xchange.liqui.dto.trade.result.LiquiTradeResult;

public class LiquiTradeServiceRaw extends LiquiBaseService {
    public LiquiTradeServiceRaw(final Exchange exchange) {
        super(exchange);
    }

    public LiquiTradeResult placeLiquiLimitOrder(final LimitOrder order) {
        final LiquiTradeType orderType = LiquiTradeType.fromOrderType(order.getType());

        final LiquiTradeResult trade = liquiAuthenticated.trade(exchange.getExchangeSpecification().getApiKey(), signatureCreator,
                exchange.getNonceFactory(), "trade", new Liqui.Pairs(order.getCurrencyPair()), orderType.toString(),
                order.getLimitPrice().toPlainString(), order.getRemainingAmount().toPlainString());

        return trade;
    }

    public LiquiActiveOrdersResult getActiveOrders() {
        return liquiAuthenticated.activeOrders(exchange.getExchangeSpecification().getApiKey(), signatureCreator,
                exchange.getNonceFactory(), "activeOrders", null);
    }

    public LiquiActiveOrdersResult getActiveOrders(final CurrencyPair pair) {
        return liquiAuthenticated.activeOrders(exchange.getExchangeSpecification().getApiKey(), signatureCreator,
                exchange.getNonceFactory(), "activeOrders", new Liqui.Pairs(pair));
    }

    public LiquiOrderInfoResult getOrderInfo(final long orderId) {
        return liquiAuthenticated.orderInfo(exchange.getExchangeSpecification().getApiKey(), signatureCreator,
                exchange.getNonceFactory(), "orderInfo", orderId);
    }

    public LiquiCancelOrderResult cancelOrder(final long orderId) {
        return liquiAuthenticated.cancelOrder(exchange.getExchangeSpecification().getApiKey(), signatureCreator,
                exchange.getNonceFactory(), "cancelOrder", orderId);
    }

    public LiquiTradeHistoryResult getTradeHistory() {
        return liquiAuthenticated.tradeHistory(exchange.getExchangeSpecification().getApiKey(), signatureCreator,
                exchange.getNonceFactory(), "tradeHistory", null, null, null,
                null, null, null, null);
    }

    public LiquiTradeHistoryResult getTradeHistory(final CurrencyPair pair) {
        return liquiAuthenticated.tradeHistory(exchange.getExchangeSpecification().getApiKey(), signatureCreator,
                exchange.getNonceFactory(), "tradeHistory", null, null, null,
                null, null, null, new Liqui.Pairs(pair));
    }

    public LiquiTradeHistoryResult getTradeHistory(final CurrencyPair pair, final int amountOftrades) {
        return liquiAuthenticated.tradeHistory(exchange.getExchangeSpecification().getApiKey(), signatureCreator,
                exchange.getNonceFactory(), "tradeHistory", null, amountOftrades, null,
                null, null, null, new Liqui.Pairs(pair));
    }

    public LiquiTradeHistoryResult getTradeHistory(final CurrencyPair pair, final long fromTrade, final long toTrade, final int amountOftrades,
                                                   final long startTime, final long endTime) {
        return liquiAuthenticated.tradeHistory(exchange.getExchangeSpecification().getApiKey(), signatureCreator,
                exchange.getNonceFactory(), "tradeHistory", fromTrade, amountOftrades,
                toTrade, null, startTime, endTime, new Liqui.Pairs(pair));
    }
}
