package org.knowm.xchange.coinbasepro.service;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import org.knowm.xchange.client.ResilienceRegistries;
import org.knowm.xchange.coinbasepro.CoinbaseProAdapters;
import org.knowm.xchange.coinbasepro.CoinbaseProExchange;
import org.knowm.xchange.coinbasepro.dto.CoinbaseProException;
import org.knowm.xchange.coinbasepro.dto.trade.CoinbaseProTradeHistoryParams;
import org.knowm.xchange.currency.CurrencyPair;
import org.knowm.xchange.dto.Order;
import org.knowm.xchange.dto.trade.LimitOrder;
import org.knowm.xchange.dto.trade.MarketOrder;
import org.knowm.xchange.dto.trade.OpenOrders;
import org.knowm.xchange.dto.trade.StopOrder;
import org.knowm.xchange.dto.trade.UserTrades;
import org.knowm.xchange.exceptions.FundsExceededException;
import org.knowm.xchange.instrument.Instrument;
import org.knowm.xchange.service.trade.TradeService;
import org.knowm.xchange.service.trade.params.CancelOrderByIdParams;
import org.knowm.xchange.service.trade.params.CancelOrderParams;
import org.knowm.xchange.service.trade.params.TradeHistoryParamCurrencyPair;
import org.knowm.xchange.service.trade.params.TradeHistoryParamInstrument;
import org.knowm.xchange.service.trade.params.TradeHistoryParamLimit;
import org.knowm.xchange.service.trade.params.TradeHistoryParamTransactionId;
import org.knowm.xchange.service.trade.params.TradeHistoryParams;
import org.knowm.xchange.service.trade.params.TradeHistoryParamsTimeSpan;
import org.knowm.xchange.service.trade.params.orders.DefaultOpenOrdersParamCurrencyPair;
import org.knowm.xchange.service.trade.params.orders.OpenOrdersParamCurrencyPair;
import org.knowm.xchange.service.trade.params.orders.OpenOrdersParams;
import org.knowm.xchange.service.trade.params.orders.OrderQueryParams;
import org.knowm.xchange.utils.DateUtils;

public class CoinbaseProTradeService extends CoinbaseProTradeServiceRaw implements TradeService {

  public CoinbaseProTradeService(
      CoinbaseProExchange exchange, ResilienceRegistries resilienceRegistries) {
    super(exchange, resilienceRegistries);
  }

  @Override
  public OpenOrders getOpenOrders() throws IOException {
    return CoinbaseProAdapters.adaptOpenOrders(getCoinbaseProOpenOrders());
  }

  @Override
  public OpenOrdersParams createOpenOrdersParams() {
    return new DefaultOpenOrdersParamCurrencyPair();
  }

  @Override
  public OpenOrders getOpenOrders(OpenOrdersParams params) throws IOException {
    if (params instanceof OpenOrdersParamCurrencyPair) {
      OpenOrdersParamCurrencyPair pairParams = (OpenOrdersParamCurrencyPair) params;
      String productId = CoinbaseProAdapters.adaptProductID(pairParams.getCurrencyPair());
      return CoinbaseProAdapters.adaptOpenOrders(getCoinbaseProOpenOrders(productId));
    }
    return CoinbaseProAdapters.adaptOpenOrders(getCoinbaseProOpenOrders());
  }

  @Override
  public String placeMarketOrder(MarketOrder marketOrder) throws IOException {
    return placeCoinbaseProOrder(CoinbaseProAdapters.adaptCoinbaseProPlaceMarketOrder(marketOrder))
        .getId();
  }

  @Override
  public String placeLimitOrder(LimitOrder limitOrder) throws IOException, FundsExceededException {
    return placeCoinbaseProOrder(CoinbaseProAdapters.adaptCoinbaseProPlaceLimitOrder(limitOrder))
        .getId();
  }

  @Override
  public String placeStopOrder(StopOrder stopOrder) throws IOException, FundsExceededException {
    return placeCoinbaseProOrder(CoinbaseProAdapters.adaptCoinbaseProStopOrder(stopOrder)).getId();
  }

  @Override
  public boolean cancelOrder(String orderId) throws IOException {
    return cancelCoinbaseProOrder(orderId);
  }

  @Override
  public boolean cancelOrder(CancelOrderParams orderParams) throws IOException {
    if (orderParams instanceof CancelOrderByIdParams) {
      return cancelOrder(((CancelOrderByIdParams) orderParams).getOrderId());
    } else {
      return false;
    }
  }

  @Override
  public UserTrades getTradeHistory(TradeHistoryParams params) throws IOException {

    String orderId = null;
    String productId = null;
    Integer afterTradeId = null;
    Integer beforeTradeId = null;
    Integer limit = null;
    String startDate = null;
    String endDate = null;

    if (params instanceof CoinbaseProTradeHistoryParams) {
      CoinbaseProTradeHistoryParams historyParams =
          (CoinbaseProTradeHistoryParams) params;
      afterTradeId = historyParams.getAfterTradeId();
      beforeTradeId = historyParams.getBeforeTradeId();
    }

    if (params instanceof TradeHistoryParamTransactionId) {
      TradeHistoryParamTransactionId tnxIdParams =
          (TradeHistoryParamTransactionId) params;
      orderId = tnxIdParams.getTransactionId();
    }

    if (params instanceof TradeHistoryParamCurrencyPair) {
      TradeHistoryParamCurrencyPair ccyPairParams =
          (TradeHistoryParamCurrencyPair) params;
      CurrencyPair currencyPair = ccyPairParams.getCurrencyPair();
      if (currencyPair != null) {
        productId = CoinbaseProAdapters.adaptProductID(currencyPair);
      }
    }

    if (params instanceof TradeHistoryParamInstrument) {
      TradeHistoryParamInstrument ccyPairParams =
          (TradeHistoryParamInstrument) params;
      Instrument instrument = ccyPairParams.getInstrument();
      if (instrument != null) {
        productId = CoinbaseProAdapters.adaptProductID(instrument);
      }
    }

    if (params instanceof TradeHistoryParamLimit) {
      TradeHistoryParamLimit limitParams = (TradeHistoryParamLimit) params;
      limit = limitParams.getLimit();
    }

    if(params instanceof TradeHistoryParamsTimeSpan){
      TradeHistoryParamsTimeSpan timeSpanParams = (TradeHistoryParamsTimeSpan) params;
      startDate = (timeSpanParams.getStartTime() == null) ? null : DateUtils.toISODateString(timeSpanParams.getStartTime());
      endDate = (timeSpanParams.getEndTime() == null) ? null : DateUtils.toISODateString(timeSpanParams.getEndTime());
    }

    if(orderId == null && productId == null){
      throw new CoinbaseProException("Either orderId or productId must be provided");
    }

    return CoinbaseProAdapters.adaptTradeHistory(getCoinbaseProFills(orderId, productId, limit, beforeTradeId, afterTradeId, null, startDate, endDate));
  }

  @Override
  public TradeHistoryParams createTradeHistoryParams() {
    return new CoinbaseProTradeHistoryParams();
  }

  @Override
  public Collection<Order> getOrder(OrderQueryParams... orderQueryParams) throws IOException {
    final String[] orderIds = Arrays.stream(orderQueryParams).map(OrderQueryParams::getOrderId).toArray(String[]::new);

    Collection<Order> orders = new ArrayList<>(orderIds.length);

    for (String orderId : orderIds) {
      orders.add(CoinbaseProAdapters.adaptOrder(super.getOrder(orderId)));
    }

    return orders;
  }
}
