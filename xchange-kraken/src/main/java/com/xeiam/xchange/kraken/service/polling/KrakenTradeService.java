package com.xeiam.xchange.kraken.service.polling;

import com.xeiam.xchange.ExchangeException;
import com.xeiam.xchange.ExchangeSpecification;
import com.xeiam.xchange.dto.trade.LimitOrder;
import com.xeiam.xchange.dto.trade.MarketOrder;
import com.xeiam.xchange.dto.trade.OpenOrders;
import com.xeiam.xchange.dto.trade.UserTrades;
import com.xeiam.xchange.kraken.KrakenAdapters;
import com.xeiam.xchange.service.polling.PollingTradeService;
import com.xeiam.xchange.service.polling.trade.TradeHistoryParams;
import com.xeiam.xchange.service.polling.trade.TradeHistoryParamsTimeSpan;
import com.xeiam.xchange.service.polling.trade.TradeHistoryParamsTimeSpanImpl;
import si.mazi.rescu.SynchronizedValueFactory;

import java.io.IOException;

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

    return KrakenAdapters.adaptTradesHistory(getKrakenTradeHistory());
  }

  @Override
  public UserTrades getTradeHistory(TradeHistoryParams params) throws ExchangeException, IOException {

    TradeHistoryParamsTimeSpan p = (TradeHistoryParamsTimeSpan) params;

    Long start = null;
    Long end = null;

    if (p.getStartTime() != null)
      start = p.getStartTime().getTime();
    if (p.getEndTime() != null)
      end = p.getEndTime().getTime();

    return KrakenAdapters.adaptTradesHistory(getKrakenTradeHistory(null, false, start, end, null));
  }

  @Override
  public com.xeiam.xchange.service.polling.trade.TradeHistoryParams createTradeHistoryParams() {
    return new TradeHistoryParamsTimeSpanImpl();
  }

}
