package com.xeiam.xchange.kraken.service.polling;

import java.io.IOException;

import si.mazi.rescu.SynchronizedValueFactory;

import com.xeiam.xchange.ExchangeSpecification;
import com.xeiam.xchange.dto.trade.LimitOrder;
import com.xeiam.xchange.dto.trade.MarketOrder;
import com.xeiam.xchange.dto.trade.OpenOrders;
import com.xeiam.xchange.dto.trade.UserTrade;
import com.xeiam.xchange.dto.trade.UserTrades;
import com.xeiam.xchange.kraken.KrakenAdapters;
import com.xeiam.xchange.service.polling.PollingTradeService;
import com.xeiam.xchange.service.polling.opt.SinceTradeHistoryProvider;

public class KrakenTradeService extends KrakenTradeServiceRaw implements PollingTradeService, SinceTradeHistoryProvider {

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

    return KrakenAdapters.adaptTradesHistory(super.getKrakenTradeHistory());
  }

  /**
   * Get user trade history since the timestamp of passed UserTrade
   */
  @Override
  public UserTrades getTradeHistory(UserTrade last) throws IOException {
    return KrakenAdapters.adaptTradesHistory(super.getKrakenTradeHistory(null, false, last.getTimestamp().getTime(), null, null));
  }
}
