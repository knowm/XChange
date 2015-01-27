package com.xeiam.xchange.bitkonan.service.polling;

import java.io.IOException;

import com.xeiam.xchange.Exchange;
import com.xeiam.xchange.bitkonan.BitKonan;
import com.xeiam.xchange.bitkonan.dto.marketdata.BitKonanOrderBook;
import com.xeiam.xchange.bitkonan.dto.marketdata.BitKonanTicker;

/**
 * @author Piotr Ładyżyński
 */
public class BitKonanMarketDataServiceRaw extends BitKonanBasePollingService<BitKonan> {

  /**
   * Constructor
   *
   * @param exchange
   */
  protected BitKonanMarketDataServiceRaw(Exchange exchange) {

    // TODO look at this
    super(BitKonan.class, exchange);
  }

  public BitKonanTicker getBitKonanTickerBTC() throws IOException {

    return bitKonan.getBitKonanTickerBTC();
  }

  public BitKonanOrderBook getBitKonanOrderBookBTC() throws IOException {

    return bitKonan.getBitKonanOrderBookBTC();
  }

}
