package org.knowm.xchange.lakebtc.service;

import org.knowm.xchange.Exchange;
import org.knowm.xchange.currency.CurrencyPair;
import org.knowm.xchange.lakebtc.LakeBTC;
import org.knowm.xchange.lakebtc.dto.marketdata.LakeBTCOrderBook;
import org.knowm.xchange.lakebtc.dto.marketdata.LakeBTCTickers;

import java.io.IOException;

/**
 * @author kpysniak
 */
public class LakeBTCMarketDataServiceRaw extends LakeBTCBaseService {

  /**
   * @param exchange
   */
  protected LakeBTCMarketDataServiceRaw(Exchange exchange) {

    super(exchange);

  }

  public LakeBTCTickers getLakeBTCTickers() throws IOException {

    return lakeBTC.getLakeBTCTickers();
  }

  public LakeBTCOrderBook getLakeBTCOrderBookUSD(CurrencyPair pair) throws IOException {

    return lakeBTC.getLakeBTCOrderBookUSD(new LakeBTC.Pair(pair));
  }

  public LakeBTCOrderBook getLakeBTCOrderBookCNY() throws IOException {

    return lakeBTC.getLakeBTCOrderBookCNY();
  }
}
