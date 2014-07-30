package com.xeiam.xchange.justcoin.service.polling;

import java.io.IOException;

import com.xeiam.xchange.ExchangeSpecification;
import com.xeiam.xchange.dto.marketdata.Trades;
import com.xeiam.xchange.dto.trade.LimitOrder;
import com.xeiam.xchange.dto.trade.MarketOrder;
import com.xeiam.xchange.dto.trade.OpenOrders;
import com.xeiam.xchange.justcoin.JustcoinAdapters;
import com.xeiam.xchange.service.polling.PollingTradeService;

/**
 * @author jamespedwards42
 */
public class JustcoinTradeService extends JustcoinTradeServiceRaw implements PollingTradeService {

  public JustcoinTradeService(final ExchangeSpecification exchangeSpecification) {

    super(exchangeSpecification);
  }

  @Override
  public OpenOrders getOpenOrders() throws IOException {

    return JustcoinAdapters.adaptOpenOrders(super.getOrders());
  }

  @Override
  public String placeMarketOrder(MarketOrder marketOrder) throws IOException {

    return super.placeMarketOrder(marketOrder);
  }

  @Override
  public String placeLimitOrder(LimitOrder limitOrder) throws IOException {

    return super.placeLimitOrder(limitOrder);
  }

  @Override
  public boolean cancelOrder(String orderId) throws IOException {

    return super.cancelOrder(orderId);
  }

  @Override
  public Trades getTradeHistory(Object... args) throws IOException {

    return JustcoinAdapters.adaptTrades(super.getOrderHistory());
  }
}
