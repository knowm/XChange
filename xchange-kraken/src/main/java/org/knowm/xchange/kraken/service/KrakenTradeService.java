package org.knowm.xchange.kraken.service;

import java.io.IOException;
import java.util.Collection;
import org.knowm.xchange.Exchange;
import org.knowm.xchange.dto.Order;
import org.knowm.xchange.dto.account.OpenPositions;
import org.knowm.xchange.dto.trade.*;
import org.knowm.xchange.exceptions.ExchangeException;
import org.knowm.xchange.kraken.KrakenAdapters;
import org.knowm.xchange.service.trade.TradeService;
import org.knowm.xchange.service.trade.params.*;
import org.knowm.xchange.service.trade.params.orders.OpenOrdersParams;
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
    return getOpenOrders(createOpenOrdersParams());
  }

  @Override
  public OpenOrders getOpenOrders(OpenOrdersParams params) throws IOException {
    return KrakenAdapters.adaptOpenOrders(super.getKrakenOpenOrders());
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

    return KrakenAdapters.adaptTradesHistory(
        getKrakenTradeHistory(null, false, start, end, offset).getTrades());
  }

  @Override
  public TradeHistoryParams createTradeHistoryParams() {

    return new KrakenTradeHistoryParams();
  }

  @Override
  public OpenOrdersParams createOpenOrdersParams() {
    return null;
  }

  @Override
  public Collection<Order> getOrder(String... orderIds) throws IOException {

    return KrakenAdapters.adaptOrders(super.getOrders(orderIds));
  }

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
