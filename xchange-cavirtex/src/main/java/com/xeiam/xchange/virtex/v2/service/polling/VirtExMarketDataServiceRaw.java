package com.xeiam.xchange.virtex.v2.service.polling;

import java.io.IOException;
import java.util.List;

import com.xeiam.xchange.Exchange;
import com.xeiam.xchange.currency.CurrencyPair;
import com.xeiam.xchange.virtex.v2.VirtEx;
import com.xeiam.xchange.virtex.v2.dto.marketdata.VirtExDepth;
import com.xeiam.xchange.virtex.v2.dto.marketdata.VirtExTicker;
import com.xeiam.xchange.virtex.v2.dto.marketdata.VirtExTrade;

import si.mazi.rescu.RestProxyFactory;

public class VirtExMarketDataServiceRaw extends VirtexBasePollingService {

  private final VirtEx virtEx;

  /**
   * Constructor
   *
   * @param exchange
   */
  public VirtExMarketDataServiceRaw(Exchange exchange) {

    super(exchange);
    this.virtEx = RestProxyFactory.createProxy(VirtEx.class, exchange.getExchangeSpecification().getSslUri());
  }

  public VirtExTicker getVirtExTicker(CurrencyPair currencyPair) throws IOException {

    return virtEx.getTicker(currencyPair.base.getCurrencyCode(), currencyPair.counter.getCurrencyCode()).getTicker();
  }

  public VirtExDepth getVirtExOrderBook(CurrencyPair currencyPair) throws IOException {

    return virtEx.getFullDepth(currencyPair.base.getCurrencyCode(), currencyPair.counter.getCurrencyCode()).getDepth();
  }

  public List<VirtExTrade> getVirtExTrades(CurrencyPair currencyPair) throws IOException {

    return virtEx.getTrades(currencyPair.base.getCurrencyCode(), currencyPair.counter.getCurrencyCode()).getTrades();
  }

}
