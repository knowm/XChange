package org.knowm.xchange.kucoin.service;

import java.io.IOException;
import java.util.Collection;
import java.util.Date;
import org.knowm.xchange.Exchange;
import org.knowm.xchange.currency.CurrencyPair;
import org.knowm.xchange.dto.Order;
import org.knowm.xchange.dto.trade.LimitOrder;
import org.knowm.xchange.dto.trade.MarketOrder;
import org.knowm.xchange.dto.trade.OpenOrders;
import org.knowm.xchange.dto.trade.UserTrades;
import org.knowm.xchange.exceptions.ExchangeException;
import org.knowm.xchange.exceptions.NotAvailableFromExchangeException;
import org.knowm.xchange.kucoin.dto.KucoinAdapters;
import org.knowm.xchange.kucoin.dto.KucoinResponse;
import org.knowm.xchange.kucoin.dto.trading.KucoinDealtOrdersInfo;
import org.knowm.xchange.service.trade.TradeService;
import org.knowm.xchange.service.trade.params.CancelOrderByCurrencyPair;
import org.knowm.xchange.service.trade.params.CancelOrderByIdParams;
import org.knowm.xchange.service.trade.params.CancelOrderByOrderTypeParams;
import org.knowm.xchange.service.trade.params.CancelOrderParams;
import org.knowm.xchange.service.trade.params.TradeHistoryParamCurrencyPair;
import org.knowm.xchange.service.trade.params.TradeHistoryParamPaging;
import org.knowm.xchange.service.trade.params.TradeHistoryParams;
import org.knowm.xchange.service.trade.params.TradeHistoryParamsTimeSpan;
import org.knowm.xchange.service.trade.params.orders.DefaultOpenOrdersParamCurrencyPair;
import org.knowm.xchange.service.trade.params.orders.OpenOrdersParamCurrencyPair;
import org.knowm.xchange.service.trade.params.orders.OpenOrdersParams;

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
    return KucoinAdapters.adaptActiveOrders(
        currencyPair,
        getKucoinOpenOrders(currencyPair, null)
            .getData()); // order type null returns both bid and ask
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
            ((CancelOrderByOrderTypeParams) orderParams).getOrderType())
        .isSuccess();
  }

  @Override
  public UserTrades getTradeHistory(TradeHistoryParams params) throws IOException {

    if (!(params instanceof TradeHistoryParamPaging)) {
      throw new ExchangeException(
          "You need to provide paging information to get the trade history.");
    }

    TradeHistoryParamPaging pagingParams = (TradeHistoryParamPaging) params;
    CurrencyPair pair = null;
    Date startTime = null;
    Date endTime = null;

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

    if (params instanceof TradeHistoryParamsTimeSpan) {
      startTime = ((TradeHistoryParamsTimeSpan) params).getStartTime();
      endTime = ((TradeHistoryParamsTimeSpan) params).getEndTime();
    }

    // Kucoin has 1-based paging
    KucoinResponse<KucoinDealtOrdersInfo> response =
        getKucoinTradeHistory(
            pair,
            null,
            pagingParams.getPageLength(),
            pagingParams.getPageNumber() + 1,
            startTime,
            endTime);
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
