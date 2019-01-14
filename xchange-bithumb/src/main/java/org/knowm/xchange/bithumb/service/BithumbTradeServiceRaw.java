package org.knowm.xchange.bithumb.service;

import java.io.IOException;
import java.util.List;
import org.knowm.xchange.Exchange;
import org.knowm.xchange.bithumb.BithumbUtils;
import org.knowm.xchange.bithumb.dto.BithumbResponse;
import org.knowm.xchange.bithumb.dto.account.BithumbOrder;
import org.knowm.xchange.bithumb.dto.account.BithumbTransaction;
import org.knowm.xchange.bithumb.dto.trade.BithumbOpenOrdersParam;
import org.knowm.xchange.bithumb.dto.trade.BithumbTradeResponse;
import org.knowm.xchange.currency.CurrencyPair;
import org.knowm.xchange.dto.trade.LimitOrder;
import org.knowm.xchange.dto.trade.MarketOrder;
import org.knowm.xchange.exceptions.ExchangeException;
import org.knowm.xchange.exceptions.NotAvailableFromExchangeException;

public class BithumbTradeServiceRaw extends BithumbBaseService {

  protected BithumbTradeServiceRaw(Exchange exchange) {
    super(exchange);
  }

  public List<BithumbOrder> getBithumbOrders(BithumbOpenOrdersParam params) throws IOException {

    final BithumbResponse<List<BithumbOrder>> orders =
        bithumbAuthenticated.getOrders(
            apiKey,
            signatureCreator,
            exchange.getNonceFactory(),
            endpointGenerator,
            null,
            null,
            null,
            null,
            BithumbUtils.getBaseCurrency(params.getCurrencyPair()));
    return checkResult(orders);
  }

  public List<BithumbOrder> getBithumbOrdersByOrderId(Long orderId) throws IOException {

    final BithumbResponse<List<BithumbOrder>> orders =
        bithumbAuthenticated.getOrders(
            apiKey,
            signatureCreator,
            exchange.getNonceFactory(),
            endpointGenerator,
            orderId,
            null,
            null,
            null,
            null);
    return checkResult(orders);
  }

  public BithumbTradeResponse placeBithumbMarketOrder(MarketOrder marketOrder) throws IOException {
    switch (marketOrder.getType()) {
      case BID:
        return bithumbAuthenticated.marketBuy(
            apiKey,
            signatureCreator,
            exchange.getNonceFactory(),
            endpointGenerator,
            marketOrder.getOriginalAmount(),
            BithumbUtils.getBaseCurrency(marketOrder.getCurrencyPair()));
      case ASK:
        return bithumbAuthenticated.marketSell(
            apiKey,
            signatureCreator,
            exchange.getNonceFactory(),
            endpointGenerator,
            marketOrder.getOriginalAmount(),
            BithumbUtils.getBaseCurrency(marketOrder.getCurrencyPair()));
      default:
        throw new NotAvailableFromExchangeException();
    }
  }

  public BithumbTradeResponse placeBithumbLimitOrder(LimitOrder limitOrder) throws IOException {
    final CurrencyPair currencyPair = limitOrder.getCurrencyPair();
    return bithumbAuthenticated.placeOrder(
        apiKey,
        signatureCreator,
        exchange.getNonceFactory(),
        endpointGenerator,
        BithumbUtils.getBaseCurrency(currencyPair),
        BithumbUtils.getCounterCurrency(),
        limitOrder.getOriginalAmount(),
        limitOrder.getLimitPrice(),
        BithumbUtils.fromOrderType(limitOrder.getType()));
  }

  public boolean cancelBithumbOrder(Long orderId) throws IOException {
    final BithumbOrder bithumbOrder =
        getBithumbOrdersByOrderId(orderId)
            .stream()
            .findFirst()
            .orElseThrow(() -> new ExchangeException("Order is not exists."));
    final BithumbResponse cancelOrder =
        bithumbAuthenticated.cancelOrder(
            apiKey,
            signatureCreator,
            exchange.getNonceFactory(),
            endpointGenerator,
            bithumbOrder.getType().name(),
            bithumbOrder.getOrderId(),
            bithumbOrder.getOrderCurrency());
    return cancelOrder.isSuccess();
  }

  public List<BithumbTransaction> bithumbTransactions(BithumbTradeHistoryParams params)
      throws IOException {
    final BithumbResponse<List<BithumbTransaction>> transactions =
        bithumbAuthenticated.getTransactions(
            apiKey,
            signatureCreator,
            exchange.getNonceFactory(),
            endpointGenerator,
            null,
            null,
            BithumbUtils.getBaseCurrency(params.getCurrencyPair()));

    return checkResult(transactions);
  }
}
