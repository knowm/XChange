package com.xeiam.xchange.vircurex.service.polling;

import java.io.IOException;

import com.xeiam.xchange.Exchange;
import com.xeiam.xchange.currency.CurrencyPair;
import com.xeiam.xchange.vircurex.dto.marketdata.VircurexDepth;
import com.xeiam.xchange.vircurex.dto.marketdata.VircurexLastTrade;

public class VircurexMarketDataServiceRaw extends VircurexBasePollingService {

  /**
   * Constructor
   *
   * @param exchange
   */
  public VircurexMarketDataServiceRaw(Exchange exchange) {

    super(exchange);

  }

  // TODO: implement Ticker by combining get_last_trade, get_volume, get_highest_bid and get_lowest_ask APIs: https://vircurex.com/welcome/api?locale=en
  public VircurexLastTrade getVircurexTicker(CurrencyPair currencyPair) throws IOException {

    VircurexLastTrade vircurexLastTrade = vircurexAuthenticated.getLastTrade(currencyPair.base.getCurrencyCode().toLowerCase(),
        currencyPair.counter.getCurrencyCode().toLowerCase());

    return vircurexLastTrade;
  }

  // TODO: implement Trades using trades API: https://vircurex.com/welcome/api?locale=en

  public VircurexDepth getVircurexOrderBook(CurrencyPair currencyPair) throws IOException {

    VircurexDepth vircurexDepth = vircurexAuthenticated.getFullDepth(currencyPair.base.getCurrencyCode().toLowerCase(), currencyPair.counter.getCurrencyCode().toLowerCase());

    return vircurexDepth;
  }

}
