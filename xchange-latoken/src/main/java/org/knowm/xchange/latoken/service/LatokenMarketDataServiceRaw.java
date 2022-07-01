package org.knowm.xchange.latoken.service;

import java.io.IOException;
import java.util.List;
import org.knowm.xchange.Exchange;
import org.knowm.xchange.currency.CurrencyPair;
import org.knowm.xchange.latoken.LatokenAdapters;
import org.knowm.xchange.latoken.dto.marketdata.LatokenOrderbook;
import org.knowm.xchange.latoken.dto.marketdata.LatokenTicker;
import org.knowm.xchange.latoken.dto.marketdata.LatokenTrades;

public class LatokenMarketDataServiceRaw extends LatokenBaseService {

  protected LatokenMarketDataServiceRaw(Exchange exchange) {
    super(exchange);
  }

  public LatokenTicker getLatokenTicker(CurrencyPair pair) throws IOException {

    LatokenTicker ticker = latoken.getTicker(LatokenAdapters.toSymbol(pair));
    return ticker;
  }

  public List<LatokenTicker> getLatokenTickers() throws IOException {

    List<LatokenTicker> latokenTickerList = latoken.getAllTickers();
    return latokenTickerList;
  }

  public LatokenOrderbook getLatokenOrderbook(CurrencyPair pair, Integer limit) throws IOException {

    return latoken.getOrderbook(LatokenAdapters.toSymbol(pair), limit);
  }

  public LatokenTrades getLatokenTrades(CurrencyPair pair, Integer limit) throws IOException {

    return latoken.getTrades(LatokenAdapters.toSymbol(pair), limit);
  }
}
