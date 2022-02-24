package org.knowm.xchange.kraken.service;

import java.io.IOException;
import java.util.Collection;
import java.util.Map;
import org.knowm.xchange.Exchange;
import org.knowm.xchange.currency.CurrencyPair;
import org.knowm.xchange.dto.Order;
import org.knowm.xchange.dto.account.OpenPositions;
import org.knowm.xchange.dto.trade.LimitOrder;
import org.knowm.xchange.dto.trade.MarketOrder;
import org.knowm.xchange.dto.trade.OpenOrders;
import org.knowm.xchange.dto.trade.UserTrades;
import org.knowm.xchange.exceptions.ExchangeException;
import org.knowm.xchange.kraken.KrakenAdapters;
import org.knowm.xchange.kraken.KrakenUtils;
import org.knowm.xchange.kraken.dto.trade.KrakenOrder;
import org.knowm.xchange.kraken.dto.trade.KrakenTrade;
import org.knowm.xchange.service.trade.TradeService;
import org.knowm.xchange.service.trade.params.CancelOrderByIdParams;
import org.knowm.xchange.service.trade.params.CancelOrderByUserReferenceParams;
import org.knowm.xchange.service.trade.params.CancelOrderParams;
import org.knowm.xchange.service.trade.params.DefaultTradeHistoryParamsTimeSpan;
import org.knowm.xchange.service.trade.params.TradeHistoryParamCurrencyPair;
import org.knowm.xchange.service.trade.params.TradeHistoryParamOffset;
import org.knowm.xchange.service.trade.params.TradeHistoryParams;
import org.knowm.xchange.service.trade.params.TradeHistoryParamsIdSpan;
import org.knowm.xchange.service.trade.params.TradeHistoryParamsTimeSpan;
import org.knowm.xchange.service.trade.params.orders.DefaultOpenOrdersParamCurrencyPair;
import org.knowm.xchange.service.trade.params.orders.OpenOrdersParamCurrencyPair;
import org.knowm.xchange.service.trade.params.orders.OpenOrdersParams;
import org.knowm.xchange.service.trade.params.orders.OrderQueryParams;
import org.knowm.xchange.utils.DateUtils;

public class KrakenTradeService extends KrakenTradeServiceRaw implements TradeService {

  /**
   * Constructor
   *
   * @param exchange
   */
  public KrakenTradeService(Exchange exchange) {

    super(exchange);
  }

  @Override
  public OpenOrders getOpenOrders() throws IOException {
    return getOpenOrders(null);
  }

  @Override
  public OpenOrders getOpenOrders(OpenOrdersParams params) throws IOException {
    Map<String, KrakenOrder> krakenOrders = super.getKrakenOpenOrders();
    if (params != null && params instanceof OpenOrdersParamCurrencyPair) {
      OpenOrdersParamCurrencyPair openOrdersParamCurrencyPair =
          (OpenOrdersParamCurrencyPair) params;
      Map<String, KrakenOrder> filteredKrakenOrders =
          KrakenUtils.filterOpenOrdersByCurrencyPair(
              krakenOrders, openOrdersParamCurrencyPair.getCurrencyPair());
      return KrakenAdapters.adaptOpenOrders(filteredKrakenOrders);
    }
    return KrakenAdapters.adaptOpenOrders(krakenOrders);
  }

  @Override
  public String placeMarketOrder(MarketOrder marketOrder) throws IOException {

    return KrakenAdapters.adaptOrderId(super.placeKrakenMarketOrder(marketOrder));
  }

  @Override
  public OpenPositions getOpenPositions() throws IOException {
    return KrakenAdapters.adaptOpenPositions(super.getKrakenOpenPositions());
  }

  @Override
  public String placeLimitOrder(LimitOrder limitOrder) throws IOException {

    return KrakenAdapters.adaptOrderId(super.placeKrakenLimitOrder(limitOrder));
  }

  @Override
  public boolean cancelOrder(String orderId) throws IOException {

    return super.cancelKrakenOrder(orderId).getCount() > 0;
  }

  @Override
  public boolean cancelOrder(CancelOrderParams orderParams) throws IOException {
    if (orderParams instanceof CancelOrderByIdParams) {
      return cancelOrder(((CancelOrderByIdParams) orderParams).getOrderId());
    }
    if (orderParams instanceof CancelOrderByUserReferenceParams) {
      return cancelOrder(((CancelOrderByUserReferenceParams) orderParams).getUserReference());
    }
    return false;
  }

  @Override
  public Class[] getRequiredCancelOrderParamClasses() {
    return new Class[] {CancelOrderByIdParams.class, CancelOrderByUserReferenceParams.class};
  }

  /**
   * @param params Can optionally implement {@link TradeHistoryParamOffset} and {@link
   *     TradeHistoryParamsTimeSpan} and {@link TradeHistoryParamsIdSpan} All other
   *     TradeHistoryParams types will be ignored.
   */
  @Override
  public UserTrades getTradeHistory(TradeHistoryParams params)
      throws ExchangeException, IOException {

    String start = null;
    String end = null;

    Long offset = null;

    CurrencyPair currencyPair = null;

    if (params instanceof TradeHistoryParamOffset) {
      offset = ((TradeHistoryParamOffset) params).getOffset();
    }

    if (params instanceof TradeHistoryParamsIdSpan) {
      TradeHistoryParamsIdSpan idSpan = (TradeHistoryParamsIdSpan) params;
      start = idSpan.getStartId();
      end = idSpan.getEndId();
    }

    if (params instanceof TradeHistoryParamsTimeSpan) {
      TradeHistoryParamsTimeSpan timeSpan = (TradeHistoryParamsTimeSpan) params;
      start =
          DateUtils.toUnixTimeOptional(timeSpan.getStartTime()).map(Object::toString).orElse(start);

      end = DateUtils.toUnixTimeOptional(timeSpan.getEndTime()).map(Object::toString).orElse(end);
    }

    Map<String, KrakenTrade> krakenTradeHistory =
        getKrakenTradeHistory(null, false, start, end, offset).getTrades();

    if (params instanceof TradeHistoryParamCurrencyPair
        && ((TradeHistoryParamCurrencyPair) params).getCurrencyPair() != null) {
      krakenTradeHistory =
          KrakenUtils.filterTradeHistoryByCurrencyPair(
              krakenTradeHistory, ((TradeHistoryParamCurrencyPair) params).getCurrencyPair());
    }

    return KrakenAdapters.adaptTradesHistory(krakenTradeHistory);
  }

  @Override
  public TradeHistoryParams createTradeHistoryParams() {

    return new org.knowm.xchange.kraken.service.KrakenTradeHistoryParams();
  }

  @Override
  public OpenOrdersParams createOpenOrdersParams() {
    return new DefaultOpenOrdersParamCurrencyPair();
  }

  @Override
  public Collection<Order> getOrder(OrderQueryParams... orderQueryParams) throws IOException {
    return KrakenAdapters.adaptOrders(super.getOrders(TradeService.toOrderIds(orderQueryParams)));
  }

  @Deprecated
  // Use org.knowm.xchange.kraken.service.KrakenTradeHistoryParams.java
  public static class KrakenTradeHistoryParams extends DefaultTradeHistoryParamsTimeSpan
      implements TradeHistoryParamOffset, TradeHistoryParamsIdSpan {

    private Long offset;
    private String startId;
    private String endId;

    @Override
    public Long getOffset() {
      return offset;
    }

    @Override
    public void setOffset(Long offset) {
      this.offset = offset;
    }

    @Override
    public String getStartId() {
      return startId;
    }

    @Override
    public String getEndId() {
      return endId;
    }

    @Override
    public void setStartId(String startId) {
      this.startId = startId;
    }

    @Override
    public void setEndId(String endId) {
      this.endId = endId;
    }
  }
}
