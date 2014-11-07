package com.xeiam.xchange.kraken.service.polling;

import java.io.IOException;
import java.util.Date;

import com.xeiam.xchange.service.polling.opt.TradeHistoryTimeSpan;
import si.mazi.rescu.SynchronizedValueFactory;

import com.xeiam.xchange.ExchangeSpecification;
import com.xeiam.xchange.dto.trade.LimitOrder;
import com.xeiam.xchange.dto.trade.MarketOrder;
import com.xeiam.xchange.dto.trade.OpenOrders;
import com.xeiam.xchange.dto.trade.UserTrades;
import com.xeiam.xchange.kraken.KrakenAdapters;
import com.xeiam.xchange.service.polling.PollingTradeService;

import javax.annotation.Nullable;

public class KrakenTradeService extends KrakenTradeServiceRaw implements PollingTradeService {

  public KrakenTradeService(ExchangeSpecification exchangeSpecification, SynchronizedValueFactory<Long> nonceFactory) {

    super(exchangeSpecification, nonceFactory);
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

    Long start = null;
    Long end = null;

    if (args.length == 1) {
      Object arg0 = args[0];
      if (arg0 instanceof TradeHistoryParam) {
        TradeHistoryParam param = (TradeHistoryParam) arg0;
        if (param.from != null)
          start = param.from.getTime();
        if (param.to != null)
          end = param.to.getTime();
      }
    }

    return KrakenAdapters.adaptTradesHistory(getKrakenTradeHistory(null, false, start, end, null));
  }

  @Override
  public Object createTradeHistoryParams() {
    return new TradeHistoryParam();
  }

  public static class TradeHistoryParam implements TradeHistoryTimeSpan{
    @Nullable
    private Date from;
    @Nullable
    private Date to;

    public TradeHistoryParam() {
    }

    public TradeHistoryParam(Date from) {
      this.from = from;
    }

    public TradeHistoryParam(Date from, Date to) {
      this.from = from;
      this.to = to;
    }

    @Override
    public void setToTime(Date to) {
      this.to = to;
    }

    @Override
    public void setFromTime(Date from) {
      this.from = from;
    }
  }
}
