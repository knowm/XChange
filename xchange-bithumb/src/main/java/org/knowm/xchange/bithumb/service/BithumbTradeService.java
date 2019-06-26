package org.knowm.xchange.bithumb.service;

import java.io.IOException;
import java.util.Optional;
import org.knowm.xchange.Exchange;
import org.knowm.xchange.bithumb.BithumbAdapters;
import org.knowm.xchange.bithumb.BithumbErrorAdapter;
import org.knowm.xchange.bithumb.BithumbException;
import org.knowm.xchange.bithumb.dto.trade.BithumbOpenOrdersParam;
import org.knowm.xchange.currency.CurrencyPair;
import org.knowm.xchange.dto.trade.LimitOrder;
import org.knowm.xchange.dto.trade.MarketOrder;
import org.knowm.xchange.dto.trade.OpenOrders;
import org.knowm.xchange.dto.trade.UserTrades;
import org.knowm.xchange.exceptions.NotAvailableFromExchangeException;
import org.knowm.xchange.exceptions.NotYetImplementedForExchangeException;
import org.knowm.xchange.service.trade.TradeService;
import org.knowm.xchange.service.trade.params.*;
import org.knowm.xchange.service.trade.params.orders.OpenOrdersParamCurrencyPair;
import org.knowm.xchange.service.trade.params.orders.OpenOrdersParams;

public class BithumbTradeService extends BithumbTradeServiceRaw implements TradeService {

  public BithumbTradeService(Exchange exchange) {
    super(exchange);
  }

  @Override
  public OpenOrders getOpenOrders() throws IOException {
    throw new NotAvailableFromExchangeException();
  }

  @Override
  public OpenOrders getOpenOrders(OpenOrdersParams params) throws IOException {

    final CurrencyPair currencyPair =
        Optional.ofNullable(params)
            .filter(p -> p instanceof OpenOrdersParamCurrencyPair)
            .map(p -> ((OpenOrdersParamCurrencyPair) p).getCurrencyPair())
            .orElse(null);

    try {
      return BithumbAdapters.adaptOrders(getBithumbOrders(currencyPair));
    } catch (BithumbException e) {
      throw BithumbErrorAdapter.adapt(e);
    }
  }

  @Override
  public String placeMarketOrder(MarketOrder marketOrder) throws IOException {
    try {
      return placeBithumbMarketOrder(marketOrder).getOrderId();
    } catch (BithumbException e) {
      throw BithumbErrorAdapter.adapt(e);
    }
  }

  @Override
  public String placeLimitOrder(LimitOrder limitOrder) throws IOException {
    try {
      return placeBithumbLimitOrder(limitOrder).getOrderId();
    } catch (BithumbException e) {
      throw BithumbErrorAdapter.adapt(e);
    }
  }

  @Override
  public boolean cancelOrder(CancelOrderParams orderParams) throws IOException {

    if (orderParams instanceof CancelOrderByPairAndIdParams) {
      try {
        final CancelOrderByPairAndIdParams params = (CancelOrderByPairAndIdParams) orderParams;
        return cancelBithumbOrder(Long.valueOf(params.getOrderId()), params.getCurrencyPair());
      } catch (BithumbException e) {
        throw BithumbErrorAdapter.adapt(e);
      }
    } else {
      throw new NotYetImplementedForExchangeException(
          "Only CancelOrderByPairAndIdParams supported");
    }
  }

  @Override
  public UserTrades getTradeHistory(TradeHistoryParams params) throws IOException {

    final CurrencyPair currencyPair =
        Optional.ofNullable(params)
            .filter(p -> p instanceof TradeHistoryParamCurrencyPair)
            .map(p -> ((TradeHistoryParamCurrencyPair) p).getCurrencyPair())
            .orElse(null);
    try {
      return BithumbAdapters.adaptUserTrades(bithumbTransactions(currencyPair), currencyPair);
    } catch (BithumbException e) {
      throw BithumbErrorAdapter.adapt(e);
    }
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
