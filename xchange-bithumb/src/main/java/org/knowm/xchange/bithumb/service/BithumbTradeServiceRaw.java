package org.knowm.xchange.bithumb.service;

import java.io.IOException;
import java.util.List;
import javax.annotation.Nullable;
import org.knowm.xchange.Exchange;
import org.knowm.xchange.bithumb.BithumbException;
import org.knowm.xchange.bithumb.BithumbUtils;
import org.knowm.xchange.bithumb.dto.BithumbResponse;
import org.knowm.xchange.bithumb.dto.account.BithumbOrder;
import org.knowm.xchange.bithumb.dto.account.BithumbTransaction;
import org.knowm.xchange.bithumb.dto.trade.BithumbTradeResponse;
import org.knowm.xchange.currency.CurrencyPair;
import org.knowm.xchange.dto.trade.LimitOrder;
import org.knowm.xchange.dto.trade.MarketOrder;
import org.knowm.xchange.exceptions.NotAvailableFromExchangeException;

public class BithumbTradeServiceRaw extends BithumbBaseService {

  protected BithumbTradeServiceRaw(Exchange exchange) {
    super(exchange);
  }

  public List<BithumbOrder> getBithumbOrders(CurrencyPair currencyPair) throws IOException {

    final BithumbResponse<List<BithumbOrder>> orders =
        bithumbAuthenticated.getOrders(
            apiKey,
            signatureCreator,
            exchange.getNonceFactory(),
            "2",
            endpointGenerator,
            null,
            null,
            null,
            null,
            BithumbUtils.getBaseCurrency(currencyPair));
    return orders.getData();
  }

  @Nullable
  public BithumbOrder getBithumbOrdersByOrderId(Long orderId, String type) throws IOException {

    final BithumbResponse<List<BithumbOrder>> orders =
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
            null);
    return orders.getData().stream().findFirst().orElse(null);
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
            BithumbUtils.getBaseCurrency(marketOrder.getCurrencyPair()));
      case ASK:
        return bithumbAuthenticated.marketSell(
            apiKey,
            signatureCreator,
            exchange.getNonceFactory(),
            "2",
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
        "2",
        endpointGenerator,
        BithumbUtils.getBaseCurrency(currencyPair),
        BithumbUtils.getCounterCurrency(),
        limitOrder.getOriginalAmount(),
        limitOrder.getLimitPrice(),
        BithumbUtils.fromOrderType(limitOrder.getType()));
  }

  public boolean cancelBithumbOrder(Long orderId, CurrencyPair currencyPair) throws IOException {

    return getBithumbOrders(currencyPair).stream()
        .filter(bo -> bo.getOrderId() == orderId)
        .findFirst()
        .map(
            bo -> {
              try {
                return cancelBithumbOrder(
                    orderId, bo.getType().name(), BithumbUtils.getBaseCurrency(currencyPair));
              } catch (IOException | BithumbException ignored) {
              }
              return null;
            })
        .isPresent();
  }

  private BithumbResponse cancelBithumbOrder(Long orderId, String type, String baseCurrency)
      throws IOException {
    return bithumbAuthenticated.cancelOrder(
        apiKey,
        signatureCreator,
        exchange.getNonceFactory(),
        "2",
        endpointGenerator,
        type,
        orderId,
        baseCurrency);
  }

  public List<BithumbTransaction> bithumbTransactions(CurrencyPair currencyPair)
      throws IOException {
    final BithumbResponse<List<BithumbTransaction>> transactions =
        bithumbAuthenticated.getTransactions(
            apiKey,
            signatureCreator,
            exchange.getNonceFactory(),
            "2",
            endpointGenerator,
            null,
            null,
            BithumbUtils.getBaseCurrency(currencyPair));

    return transactions.getData();
  }
}
