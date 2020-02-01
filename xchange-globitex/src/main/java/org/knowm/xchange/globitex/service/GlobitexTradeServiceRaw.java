package org.knowm.xchange.globitex.service;

import java.io.IOException;
import org.knowm.xchange.Exchange;
import org.knowm.xchange.dto.trade.LimitOrder;
import org.knowm.xchange.dto.trade.MarketOrder;
import org.knowm.xchange.exceptions.ExchangeException;
import org.knowm.xchange.globitex.GlobitexAdapters;
import org.knowm.xchange.globitex.dto.trade.GlobitexActiveOrders;
import org.knowm.xchange.globitex.dto.trade.GlobitexExecutionReport;
import org.knowm.xchange.globitex.dto.trade.GlobitexUserTrades;
import org.knowm.xchange.service.trade.params.TradeHistoryParamsAll;
import org.knowm.xchange.service.trade.params.orders.OpenOrdersParamCurrencyPair;
import si.mazi.rescu.HttpStatusIOException;

public class GlobitexTradeServiceRaw extends GlobitexBaseService {

  public GlobitexTradeServiceRaw(Exchange exchange) {
    super(exchange);
  }

  public GlobitexUserTrades getGlobitexUserTrades(TradeHistoryParamsAll params) throws IOException {
    try {

      return globitex.getTradeHistory(
          exchange.getExchangeSpecification().getApiKey(),
          exchange.getNonceFactory(),
          signatureCreator,
          "ts",
          0,
          params.getLimit(),
          GlobitexAdapters.adaptCurrencyPairToGlobitexSymbol(params.getCurrencyPair()),
          exchange.getExchangeSpecification().getUserName());

    } catch (HttpStatusIOException e) {
      throw new ExchangeException(e.getHttpBody());
    }
  }

  public GlobitexActiveOrders getGlobitexActiveOrders() throws IOException {
    try {
      return globitex.getActiveOrders(
          exchange.getExchangeSpecification().getApiKey(),
          exchange.getNonceFactory(),
          signatureCreator,
          null,
          null,
          exchange.getExchangeSpecification().getUserName());
    } catch (HttpStatusIOException e) {
      throw new ExchangeException(e.getHttpBody());
    }
  }

  public GlobitexActiveOrders getGlobitexActiveOrders(OpenOrdersParamCurrencyPair params)
      throws IOException {
    try {

      return globitex.getActiveOrders(
          exchange.getExchangeSpecification().getApiKey(),
          exchange.getNonceFactory(),
          signatureCreator,
          GlobitexAdapters.adaptCurrencyPairToGlobitexSymbol(params.getCurrencyPair()),
          null,
          exchange.getExchangeSpecification().getUserName());
    } catch (HttpStatusIOException e) {
      throw new ExchangeException(e.getHttpBody());
    }
  }

  public GlobitexExecutionReport placeGlobitexLimitOrder(LimitOrder limitOrder) throws IOException {
    try {
      return globitex.placeNewOrder(
          exchange.getExchangeSpecification().getApiKey(),
          exchange.getNonceFactory(),
          signatureCreator,
          exchange.getExchangeSpecification().getUserName(),
          GlobitexAdapters.adaptCurrencyPairToGlobitexSymbol(limitOrder.getCurrencyPair()),
          GlobitexAdapters.adaptOrderType(limitOrder.getType()),
          limitOrder.getLimitPrice().toString(),
          limitOrder.getOriginalAmount().toString());
    } catch (HttpStatusIOException e) {
      throw new ExchangeException(e.getHttpBody());
    }
  }

  public GlobitexExecutionReport placeGlobitexMarketOrder(MarketOrder marketOrder)
      throws IOException {
    try {
      return globitex.placeNewOrder(
          exchange.getExchangeSpecification().getApiKey(),
          exchange.getNonceFactory(),
          signatureCreator,
          exchange.getExchangeSpecification().getUserName(),
          GlobitexAdapters.adaptCurrencyPairToGlobitexSymbol(marketOrder.getCurrencyPair()),
          GlobitexAdapters.adaptOrderType(marketOrder.getType()),
          null,
          marketOrder.getOriginalAmount().toString());
    } catch (HttpStatusIOException e) {
      throw new ExchangeException(e.getHttpBody());
    }
  }

  public GlobitexExecutionReport globitexCancelOrder(String clientOrderId) throws IOException {
    try {
      return globitex.cancelOrder(
          exchange.getExchangeSpecification().getApiKey(),
          exchange.getNonceFactory(),
          signatureCreator,
          clientOrderId);
    } catch (HttpStatusIOException e) {
      throw new ExchangeException(e.getHttpBody());
    }
  }
}
