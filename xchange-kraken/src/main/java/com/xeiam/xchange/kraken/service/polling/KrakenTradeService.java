package com.xeiam.xchange.kraken.service.polling;

import java.io.IOException;

import com.xeiam.xchange.Exchange;
import com.xeiam.xchange.dto.trade.LimitOrder;
import com.xeiam.xchange.dto.trade.MarketOrder;
import com.xeiam.xchange.dto.trade.OpenOrders;
import com.xeiam.xchange.dto.trade.UserTrades;
import com.xeiam.xchange.exceptions.ExchangeException;
import com.xeiam.xchange.kraken.KrakenAdapters;
import com.xeiam.xchange.service.polling.trade.PollingTradeService;
import com.xeiam.xchange.service.polling.trade.params.DefaultTradeHistoryParamsTimeSpan;
import com.xeiam.xchange.service.polling.trade.params.TradeHistoryParamOffset;
import com.xeiam.xchange.service.polling.trade.params.TradeHistoryParams;
import com.xeiam.xchange.service.polling.trade.params.TradeHistoryParamsTimeSpan;
import com.xeiam.xchange.utils.DateUtils;

public class KrakenTradeService extends KrakenTradeServiceRaw implements PollingTradeService {

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

    return KrakenAdapters.adaptOpenOrders(super.getKrakenOpenOrders());
  }

  @Override
  public String placeMarketOrder(MarketOrder marketOrder) throws IOException {

    return KrakenAdapters.adaptOrderId(super.placeKrakenMarketOrder(marketOrder));
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
  public UserTrades getTradeHistory(Object... args) throws IOException {

    return KrakenAdapters.adaptTradesHistory(getKrakenTradeHistory());
  }

  /**
   * @param params Can optionally implement {@link TradeHistoryParamOffset} and {@link TradeHistoryParamsTimeSpan}. All other TradeHistoryParams types
   *        will be ignored.
   */
  @Override
  public UserTrades getTradeHistory(TradeHistoryParams params) throws ExchangeException, IOException {

    final Long startTime;
    final Long endTime;
    if (params instanceof TradeHistoryParamsTimeSpan) {
      TradeHistoryParamsTimeSpan timeSpan = (TradeHistoryParamsTimeSpan) params;
      startTime = DateUtils.toUnixTimeNullSafe(timeSpan.getStartTime());
      endTime = DateUtils.toUnixTimeNullSafe(timeSpan.getEndTime());
    } else {
      startTime = null;
      endTime = null;
    }

    final Long offset;
    if (params instanceof TradeHistoryParamOffset) {
      offset = ((TradeHistoryParamOffset) params).getOffset();
    } else {
      offset = null;
    }

    return KrakenAdapters.adaptTradesHistory(getKrakenTradeHistory(null, false, startTime, endTime, offset));
  }

  @Override
  public com.xeiam.xchange.service.polling.trade.params.TradeHistoryParams createTradeHistoryParams() {

    return new KrakenTradeHistoryParams();
  }

  public static class KrakenTradeHistoryParams extends DefaultTradeHistoryParamsTimeSpan implements TradeHistoryParamOffset {

    private Long offset;

    @Override
    public void setOffset(Long offset) {
      this.offset = offset;
    }

    @Override
    public Long getOffset() {
      return offset;
    }
  }

}
