package org.knowm.xchange.bitkonan.service.polling;

import java.io.IOException;

import org.knowm.xchange.Exchange;
import org.knowm.xchange.bitkonan.dto.marketdata.BitKonanOrderBook;
import org.knowm.xchange.bitkonan.dto.marketdata.BitKonanTicker;

public class BitKonanMarketDataServiceRaw extends BitKonanBasePollingService {

  /**
   * Constructor
   *
   * @param exchange
   */
  protected BitKonanMarketDataServiceRaw(Exchange exchange) {

    super(exchange);
  }

  public BitKonanTicker getBitKonanTicker(String base) throws IOException {
    if ("btc".equals(base))
      return bitKonan.getBitKonanTickerBTC();
    else
      return bitKonan.getBitKonanTicker(base);
  }

  public BitKonanOrderBook getBitKonanOrderBook(String base) throws IOException {

    return bitKonan.getBitKonanOrderBook(base);
  }

}
