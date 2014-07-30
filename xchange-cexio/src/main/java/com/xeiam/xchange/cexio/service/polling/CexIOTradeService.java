package com.xeiam.xchange.cexio.service.polling;

import java.io.IOException;
import java.util.List;

import com.xeiam.xchange.ExchangeSpecification;
import com.xeiam.xchange.NotAvailableFromExchangeException;
import com.xeiam.xchange.cexio.CexIOAdapters;
import com.xeiam.xchange.cexio.dto.trade.CexIOOrder;
import com.xeiam.xchange.dto.marketdata.Trades;
import com.xeiam.xchange.dto.trade.LimitOrder;
import com.xeiam.xchange.dto.trade.MarketOrder;
import com.xeiam.xchange.dto.trade.OpenOrders;
import com.xeiam.xchange.service.polling.PollingTradeService;

/**
 * Author: brox
 * Since: 2/6/14
 */

public class CexIOTradeService extends CexIOTradeServiceRaw implements PollingTradeService {

  /**
   * Initialize common properties from the exchange specification
   * 
   * @param exchangeSpecification The {@link com.xeiam.xchange.ExchangeSpecification}
   */
  public CexIOTradeService(ExchangeSpecification exchangeSpecification) {

    super(exchangeSpecification);
  }

  @Override
  public OpenOrders getOpenOrders() throws IOException {

    List<CexIOOrder> cexIOOrderList = getCexIOOpenOrders();

    return CexIOAdapters.adaptOpenOrders(cexIOOrderList);
  }

  @Override
  public String placeMarketOrder(MarketOrder marketOrder) throws IOException {

    throw new NotAvailableFromExchangeException();
  }

  @Override
  public String placeLimitOrder(LimitOrder limitOrder) throws IOException {

    CexIOOrder order = placeCexIOLimitOrder(limitOrder);

    return Integer.toString(order.getId());
  }

  @Override
  public boolean cancelOrder(String orderId) throws IOException {

    return cancelCexIOOrder(orderId);
  }

  @Override
  public Trades getTradeHistory(Object... args) throws IOException {

    throw new NotAvailableFromExchangeException();
  }

}
