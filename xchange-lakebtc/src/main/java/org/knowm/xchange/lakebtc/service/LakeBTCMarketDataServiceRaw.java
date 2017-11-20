package org.knowm.xchange.lakebtc.service;

import java.io.IOException;

import org.knowm.xchange.Exchange;
import org.knowm.xchange.currency.CurrencyPair;
import org.knowm.xchange.lakebtc.LakeBTC;
import org.knowm.xchange.lakebtc.dto.marketdata.LakeBTCOrderBook;
import org.knowm.xchange.lakebtc.dto.marketdata.LakeBTCTickers;

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

  public LakeBTCOrderBook getLakeOrderBook(CurrencyPair pair) throws IOException {

    return lakeBTC.getLakeBTCOrderBookUSD(new LakeBTC.Pair(pair));
  }

}
