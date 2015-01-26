package com.xeiam.xchange.bitkonan.service.polling;

import java.io.IOException;

import si.mazi.rescu.SynchronizedValueFactory;

import com.xeiam.xchange.Exchange;
import com.xeiam.xchange.bitkonan.BitKonan;
import com.xeiam.xchange.bitkonan.dto.marketdata.BitKonanOrderBook;
import com.xeiam.xchange.bitkonan.dto.marketdata.BitKonanTicker;

/**
 * @author Piotr Ładyżyński
 */
public abstract class BitKonanMarketDataServiceRaw extends BitKonanBasePollingService<BitKonan> {

  /**
   * Constructor
   *
   * @param exchange
   * @param nonceFactory
   */
  protected BitKonanMarketDataServiceRaw(Exchange exchange, SynchronizedValueFactory<Long> nonceFactory) {

    super(BitKonan.class, exchange, nonceFactory);
  }

  public BitKonanTicker getBitKonanTickerBTC() throws IOException {

    return bitKonan.getBitKonanTickerBTC();
  }

  public BitKonanOrderBook getBitKonanOrderBookBTC() throws IOException {

    return bitKonan.getBitKonanOrderBookBTC();
  }

}
