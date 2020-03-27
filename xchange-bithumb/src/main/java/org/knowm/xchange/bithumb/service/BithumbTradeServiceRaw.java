package org.knowm.xchange.bithumb.service;

import java.io.IOException;
import java.util.List;
import javax.annotation.Nullable;
import org.knowm.xchange.Exchange;
import org.knowm.xchange.bithumb.BithumbException;
import org.knowm.xchange.bithumb.BithumbUtils;
import org.knowm.xchange.bithumb.dto.BithumbResponse;
import org.knowm.xchange.bithumb.dto.account.BithumbOrder;
import org.knowm.xchange.bithumb.dto.account.BithumbOrderDetail;
import org.knowm.xchange.bithumb.dto.trade.BithumbTradeResponse;
import org.knowm.xchange.bithumb.dto.trade.BithumbUserTransaction;
import org.knowm.xchange.currency.CurrencyPair;
import org.knowm.xchange.dto.trade.LimitOrder;
import org.knowm.xchange.dto.trade.MarketOrder;
import org.knowm.xchange.exceptions.NotAvailableFromExchangeException;

public class BithumbTradeServiceRaw extends BithumbBaseService {

  protected BithumbTradeServiceRaw(Exchange exchange) {
    super(exchange);
  }

  public BithumbResponse<List<BithumbOrder>> getBithumbOrders(CurrencyPair currencyPair)
      throws IOException {
    return bithumbAuthenticated.getOrders(
        apiKey,
        signatureCreator,
        exchange.getNonceFactory(),
        "2",
        endpointGenerator,
        null,
        null,
        1000,
        null,
        BithumbUtils.getBaseCurrency(currencyPair),
        BithumbUtils.getCounterCurrency());
  }

  @Nullable
  public BithumbOrder getBithumbOrdersByOrderId(
      String orderId, String type, CurrencyPair currencyPair) throws IOException {

    final BithumbResponse<List<BithumbOrder>> response =
        bithumbAuthenticated.getOrders(
            apiKey,
            signatureCreator,
            exchange.getNonceFactory(),
            "2",
            endpointGenerator,
            orderId,
            type,
            null,
            null,
            BithumbUtils.getBaseCurrency(currencyPair),
            BithumbUtils.getCounterCurrency());
    return response.getData().stream().findFirst().orElse(null);
  }

  public BithumbResponse<BithumbOrderDetail> getBithumbOrderDetail(
      String orderId, CurrencyPair currencyPair) throws IOException {
    return bithumbAuthenticated.getOrderDetail(
        apiKey,
        signatureCreator,
        exchange.getNonceFactory(),
        "2",
        endpointGenerator,
        orderId,
        BithumbUtils.getBaseCurrency(currencyPair),
        BithumbUtils.getCounterCurrency());
  }

  public BithumbTradeResponse placeBithumbMarketOrder(MarketOrder marketOrder) throws IOException {
    switch (marketOrder.getType()) {
      case BID:
        return bithumbAuthenticated.marketBuy(
            apiKey,
            signatureCreator,
            exchange.getNonceFactory(),
            "2",
            endpointGenerator,
            marketOrder.getOriginalAmount(),
            BithumbUtils.getBaseCurrency(marketOrder.getCurrencyPair()),
            BithumbUtils.getCounterCurrency());
      case ASK:
        return bithumbAuthenticated.marketSell(
            apiKey,
            signatureCreator,
            exchange.getNonceFactory(),
            "2",
            endpointGenerator,
            marketOrder.getOriginalAmount(),
            BithumbUtils.getBaseCurrency(marketOrder.getCurrencyPair()),
            BithumbUtils.getCounterCurrency());
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
        "2",
        endpointGenerator,
        BithumbUtils.getBaseCurrency(currencyPair),
        BithumbUtils.getCounterCurrency(),
        limitOrder.getOriginalAmount(),
        limitOrder.getLimitPrice(),
        BithumbUtils.fromOrderType(limitOrder.getType()));
  }

  public boolean cancelBithumbOrder(String orderId, CurrencyPair currencyPair) throws IOException {

    return getBithumbOrders(currencyPair).getData().stream()
        .filter(bo -> bo.getOrderId().equals(orderId))
        .findFirst()
        .map(
            bo -> {
              try {
                return cancelBithumbOrder(orderId, bo.getType().name(), currencyPair);
              } catch (IOException | BithumbException ignored) {
              }
              return null;
            })
        .isPresent();
  }

  private BithumbResponse cancelBithumbOrder(String orderId, String type, CurrencyPair currencyPair)
      throws IOException {
    return bithumbAuthenticated.cancelOrder(
        apiKey,
        signatureCreator,
        exchange.getNonceFactory(),
        "2",
        endpointGenerator,
        type,
        orderId,
        BithumbUtils.getBaseCurrency(currencyPair),
        BithumbUtils.getCounterCurrency());
  }

  public BithumbResponse<List<BithumbUserTransaction>> getBithumbUserTransactions(
      CurrencyPair currencyPair) throws IOException {
    return bithumbAuthenticated.getUserTransactions(
        apiKey,
        signatureCreator,
        exchange.getNonceFactory(),
        "2",
        endpointGenerator,
        null,
        50,
        null,
        BithumbUtils.getBaseCurrency(currencyPair),
        BithumbUtils.getCounterCurrency());
  }
}
