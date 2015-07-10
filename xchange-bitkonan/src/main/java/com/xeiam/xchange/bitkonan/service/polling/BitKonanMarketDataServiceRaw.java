package com.xeiam.xchange.bitkonan.service.polling;

import java.io.IOException;

import com.xeiam.xchange.Exchange;
import com.xeiam.xchange.bitkonan.dto.marketdata.BitKonanOrderBook;
import com.xeiam.xchange.bitkonan.dto.marketdata.BitKonanTicker;

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
