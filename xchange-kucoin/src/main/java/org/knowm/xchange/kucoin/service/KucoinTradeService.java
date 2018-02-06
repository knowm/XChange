package org.knowm.xchange.kucoin.service;

import org.knowm.xchange.Exchange;
import org.knowm.xchange.currency.CurrencyPair;
import org.knowm.xchange.dto.Order;
import org.knowm.xchange.dto.trade.*;
import org.knowm.xchange.exceptions.ExchangeException;
import org.knowm.xchange.exceptions.NotAvailableFromExchangeException;
import org.knowm.xchange.exceptions.NotYetImplementedForExchangeException;
import org.knowm.xchange.kucoin.dto.KucoinAdapters;
import org.knowm.xchange.kucoin.dto.KucoinResponse;
import org.knowm.xchange.kucoin.dto.trading.KucoinDealtOrdersInfo;
import org.knowm.xchange.service.trade.TradeService;
import org.knowm.xchange.service.trade.params.*;
import org.knowm.xchange.service.trade.params.orders.DefaultOpenOrdersParamCurrencyPair;
import org.knowm.xchange.service.trade.params.orders.OpenOrdersParamCurrencyPair;
import org.knowm.xchange.service.trade.params.orders.OpenOrdersParams;

import java.io.IOException;
import java.util.Collection;

public class KucoinTradeService extends KucoinTradeServiceRaw implements TradeService {

  public KucoinTradeService(Exchange exchange) {

    super(exchange);
  }

  @Override
  public OpenOrders getOpenOrders() throws IOException {

    throw new ExchangeException("You need to provide the currency pair to get open orders.");
  }

  @Override
  public OpenOrders getOpenOrders(OpenOrdersParams params) throws IOException {
    if (!(params instanceof OpenOrdersParamCurrencyPair)) {
      throw new ExchangeException("You need to provide the currency pair to get open orders.");
    }
    CurrencyPair currencyPair = ((OpenOrdersParamCurrencyPair) params).getCurrencyPair();
    return KucoinAdapters.adaptActiveOrders(currencyPair, getKucoinOpenOrders(currencyPair, null).getData()); // order type null returns both bid and ask
  }

  @Override
  public String placeMarketOrder(MarketOrder marketOrder) throws IOException {
    
    throw new NotAvailableFromExchangeException();
  }

  @Override
  public String placeLimitOrder(LimitOrder limitOrder) throws IOException {
    
    return placeKucoinLimitOrder(limitOrder).getData().getOrderOid();
  }

  @Override
  public String placeStopOrder(StopOrder stopOrder) throws IOException {
    throw new NotYetImplementedForExchangeException();
  }

  @Override
  public boolean cancelOrder(String orderId) throws IOException {

    throw new ExchangeException(
        "You need to provide the currency pair, the order id and the order type to cancel an order.");
  }

  @Override
  public boolean cancelOrder(CancelOrderParams orderParams) throws IOException {

    if (!(orderParams instanceof CancelOrderByCurrencyPair)
        && !(orderParams instanceof CancelOrderByIdParams)
        && !(orderParams instanceof CancelOrderByOrderTypeParams)) {
      throw new ExchangeException(
          "You need to provide the currency pair, the order id and the order type to cancel an order.");
    }
    return cancelKucoinOrder(
        ((CancelOrderByCurrencyPair) orderParams).getCurrencyPair(),
        ((CancelOrderByIdParams) orderParams).getOrderId(),
        ((CancelOrderByOrderTypeParams) orderParams).getOrderType()).isSuccess();
  }

  @Override
  public UserTrades getTradeHistory(TradeHistoryParams params) throws IOException {

    if (!(params instanceof TradeHistoryParamPaging)) {
      throw new ExchangeException("You need to provide paging information to get the trade history.");
    }
    
    TradeHistoryParamPaging pagingParams = (TradeHistoryParamPaging) params;
    CurrencyPair pair = null;

    if (params instanceof TradeHistoryParamCurrencyPair) {
      if (pagingParams.getPageLength() > 20) {
        throw new ExchangeException("Page length > 20 not allowed with a currency pair.");
      }
      pair = ((TradeHistoryParamCurrencyPair) params).getCurrencyPair();
    } else {
      if (pagingParams.getPageLength() > 100) {
        throw new ExchangeException("Page length > 100 not allowed with a currency pair.");
      }
    }
    // Kucoin has 1-based paging
    KucoinResponse<KucoinDealtOrdersInfo> response = getKucoinTradeHistory(pair, null,
        pagingParams.getPageLength(), pagingParams.getPageNumber() + 1, null, null);
    return KucoinAdapters.adaptUserTrades(response.getData().getDealtOrders());
  }

  @Override
  public TradeHistoryParams createTradeHistoryParams() {

    return new KucoinTradeHistoryParams();
  }

  @Override
  public OpenOrdersParams createOpenOrdersParams() {

    return new DefaultOpenOrdersParamCurrencyPair();
  }


  @Override
  public Collection<Order> getOrder(String... orderIds) throws IOException {

    throw new NotAvailableFromExchangeException();
  }

}
