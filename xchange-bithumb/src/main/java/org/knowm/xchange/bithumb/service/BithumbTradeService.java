package org.knowm.xchange.bithumb.service;

import java.io.IOException;
import java.util.Arrays;
import java.util.Collection;
import java.util.Objects;
import java.util.Optional;
import java.util.stream.Collectors;

import org.knowm.xchange.Exchange;
import org.knowm.xchange.bithumb.BithumbAdapters;
import org.knowm.xchange.bithumb.BithumbErrorAdapter;
import org.knowm.xchange.bithumb.BithumbException;
import org.knowm.xchange.bithumb.dto.BithumbResponse;
import org.knowm.xchange.bithumb.dto.account.BithumbOrderDetail;
import org.knowm.xchange.bithumb.dto.trade.BithumbOpenOrdersParam;
import org.knowm.xchange.bithumb.dto.trade.BithumbTradeResponse;
import org.knowm.xchange.currency.CurrencyPair;
import org.knowm.xchange.dto.Order;
import org.knowm.xchange.dto.trade.LimitOrder;
import org.knowm.xchange.dto.trade.MarketOrder;
import org.knowm.xchange.dto.trade.OpenOrders;
import org.knowm.xchange.dto.trade.UserTrades;
import org.knowm.xchange.exceptions.NotAvailableFromExchangeException;
import org.knowm.xchange.exceptions.NotYetImplementedForExchangeException;
import org.knowm.xchange.service.trade.TradeService;
import org.knowm.xchange.service.trade.params.CancelOrderByPairAndIdParams;
import org.knowm.xchange.service.trade.params.CancelOrderParams;
import org.knowm.xchange.service.trade.params.TradeHistoryParamInstrument;
import org.knowm.xchange.service.trade.params.TradeHistoryParams;
import org.knowm.xchange.service.trade.params.orders.OpenOrdersParamInstrument;
import org.knowm.xchange.service.trade.params.orders.OpenOrdersParams;
import org.knowm.xchange.service.trade.params.orders.OrderQueryParamInstrument;
import org.knowm.xchange.service.trade.params.orders.OrderQueryParams;

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
            .filter(p -> p instanceof OpenOrdersParamInstrument)
            .map(p -> ((OpenOrdersParamInstrument) p).getCurrencyPair())
            .orElse(null);

    try {
      return BithumbAdapters.adaptOrders(getBithumbOrders(currencyPair).getData());
    } catch (BithumbException e) {
      throw BithumbErrorAdapter.adapt(e);
    }
  }

  @Override
  public String placeMarketOrder(MarketOrder marketOrder) throws IOException {
    try {
      BithumbTradeResponse bithumbTradeResponse = placeBithumbMarketOrder(marketOrder);
      if (!"0000".equals(bithumbTradeResponse.getStatus()))
        throw new BithumbException(
            bithumbTradeResponse.getStatus(), bithumbTradeResponse.getMessage());
      return bithumbTradeResponse.getOrderId();
    } catch (BithumbException e) {
      throw BithumbErrorAdapter.adapt(e);
    }
  }

  @Override
  public String placeLimitOrder(LimitOrder limitOrder) throws IOException {
    try {
      BithumbTradeResponse bithumbTradeResponse = placeBithumbLimitOrder(limitOrder);
      if (!"0000".equals(bithumbTradeResponse.getStatus()))
        throw new BithumbException(
            bithumbTradeResponse.getStatus(), bithumbTradeResponse.getMessage());
      return bithumbTradeResponse.getOrderId();
    } catch (BithumbException e) {
      throw BithumbErrorAdapter.adapt(e);
    }
  }

  @Override
  public boolean cancelOrder(CancelOrderParams orderParams) throws IOException {

    if (orderParams instanceof CancelOrderByPairAndIdParams) {
      try {
        final CancelOrderByPairAndIdParams params = (CancelOrderByPairAndIdParams) orderParams;
        return cancelBithumbOrder(params.getOrderId(), params.getCurrencyPair());
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
            .filter(p -> p instanceof TradeHistoryParamInstrument)
            .map(p -> ((TradeHistoryParamInstrument) p).getCurrencyPair())
            .orElse(null);
    try {
      return BithumbAdapters.adaptUserTrades(
          getBithumbUserTransactions(currencyPair).getData(), currencyPair);
    } catch (BithumbException e) {
      throw BithumbErrorAdapter.adapt(e);
    }
  }

  @Override
  public Collection<Order> getOrder(String... orderIds) throws IOException {
    throw new NotAvailableFromExchangeException();
  }

  @Override
  public Collection<Order> getOrder(OrderQueryParams... orderQueryParams) throws IOException {
    /* This only works for executed orders */
    return Arrays.stream(orderQueryParams)
        .filter(oq -> oq instanceof OrderQueryParamInstrument)
        .map(oq -> (OrderQueryParamInstrument) oq)
        .map(
            oq -> {
              try {
                BithumbResponse<BithumbOrderDetail> r =
                    getBithumbOrderDetail(oq.getOrderId(), oq.getCurrencyPair());
                return BithumbAdapters.adaptOrderDetail(r.getData(), oq.getOrderId());

              } catch (IOException e) {
                throw new RuntimeException(e);
              } catch (BithumbException e) {
                throw BithumbErrorAdapter.adapt(e);
              }
            })
        .filter(Objects::nonNull)
        .collect(Collectors.toList());
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
