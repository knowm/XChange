package org.knowm.xchange.liqui.service;

import java.util.Map;
import org.knowm.xchange.Exchange;
import org.knowm.xchange.currency.CurrencyPair;
import org.knowm.xchange.dto.trade.LimitOrder;
import org.knowm.xchange.liqui.Liqui;
import org.knowm.xchange.liqui.dto.LiquiTradeType;
import org.knowm.xchange.liqui.dto.trade.LiquiCancelOrder;
import org.knowm.xchange.liqui.dto.trade.LiquiOrderInfo;
import org.knowm.xchange.liqui.dto.trade.LiquiTrade;
import org.knowm.xchange.liqui.dto.trade.LiquiUserTrade;
import org.knowm.xchange.liqui.dto.trade.result.LiquiTradeResult;

public class LiquiTradeServiceRaw extends LiquiBaseService {
  public LiquiTradeServiceRaw(final Exchange exchange) {
    super(exchange);
  }

  public LiquiTrade placeLiquiLimitOrder(final LimitOrder order) {
    final LiquiTradeType orderType = LiquiTradeType.fromOrderType(order.getType());

    final LiquiTradeResult trade =
        liquiAuthenticated.trade(
            exchange.getExchangeSpecification().getApiKey(),
            signatureCreator,
            exchange.getNonceFactory(),
            "trade",
            new Liqui.Pairs(order.getCurrencyPair()),
            orderType.toString(),
            order.getLimitPrice().toPlainString(),
            order.getRemainingAmount().toPlainString());

    return checkResult(trade);
  }

  public Map<Long, LiquiOrderInfo> getActiveOrders() {
    return checkResult(
        liquiAuthenticated.activeOrders(
            exchange.getExchangeSpecification().getApiKey(),
            signatureCreator,
            exchange.getNonceFactory(),
            "activeOrders",
            null));
  }

  public Map<Long, LiquiOrderInfo> getActiveOrders(final CurrencyPair pair) {
    return checkResult(
        liquiAuthenticated.activeOrders(
            exchange.getExchangeSpecification().getApiKey(),
            signatureCreator,
            exchange.getNonceFactory(),
            "activeOrders",
            new Liqui.Pairs(pair)));
  }

  public LiquiOrderInfo getOrderInfo(final long orderId) {
    return checkResult(
            liquiAuthenticated.orderInfo(
                exchange.getExchangeSpecification().getApiKey(),
                signatureCreator,
                exchange.getNonceFactory(),
                "orderInfo",
                orderId))
        .get(orderId);
  }

  public LiquiCancelOrder cancelOrder(final long orderId) {
    return checkResult(
        liquiAuthenticated.cancelOrder(
            exchange.getExchangeSpecification().getApiKey(),
            signatureCreator,
            exchange.getNonceFactory(),
            "cancelOrder",
            orderId));
  }

  public Map<Long, LiquiUserTrade> getTradeHistory(
      final CurrencyPair pair,
      final Long fromTrade,
      final Long toTrade,
      final Integer amountOftrades,
      final Long startTime,
      final Long endTime) {
    return checkResult(
            liquiAuthenticated.tradeHistory(
                exchange.getExchangeSpecification().getApiKey(),
                signatureCreator,
                exchange.getNonceFactory(),
                "tradeHistory",
                fromTrade,
                amountOftrades,
                fromTrade,
                toTrade,
                null,
                startTime,
                endTime,
                pair != null ? new Liqui.Pairs(pair) : null))
        .getHistory();
  }
}
