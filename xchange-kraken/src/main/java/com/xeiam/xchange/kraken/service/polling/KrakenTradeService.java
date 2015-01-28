package com.xeiam.xchange.kraken.service.polling;

import java.io.IOException;
import java.util.Date;

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
   * Required parameters {@link TradeHistoryParamsTimeSpan} {@link TradeHistoryParamOffset}
   */
  @Override
  public UserTrades getTradeHistory(TradeHistoryParams params) throws ExchangeException, IOException {

    TradeHistoryParamsTimeSpan timeSpan = (TradeHistoryParamsTimeSpan) params;
    TradeHistoryParamOffset offset = (TradeHistoryParamOffset) params;

    return KrakenAdapters.adaptTradesHistory(getKrakenTradeHistory(null, false, getTime(timeSpan.getStartTime()), getTime(timeSpan.getEndTime()),
        offset.getOffset()));
  }

  @Override
  public com.xeiam.xchange.service.polling.trade.params.TradeHistoryParams createTradeHistoryParams() {

    return new KrakenTradeHistoryParams();
  }

  private static Long getTime(Date date) {

    return date == null ? null : date.getTime();
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
