package org.knowm.xchange.bitkonan.service;

import java.io.IOException;
import java.util.List;

import org.knowm.xchange.Exchange;
import org.knowm.xchange.bitkonan.dto.marketdata.BitKonanOrderBook;
import org.knowm.xchange.bitkonan.dto.marketdata.BitKonanTicker;
import org.knowm.xchange.bitkonan.dto.marketdata.BitKonanTrade;
import org.knowm.xchange.exceptions.NotYetImplementedForExchangeException;

public class BitKonanMarketDataServiceRaw extends BitKonanBaseService {

  /**
   * Constructor
   *
   * @param exchange
   */
  protected BitKonanMarketDataServiceRaw(Exchange exchange) {

    super(exchange);
  }

  public BitKonanTicker getBitKonanTicker(String base) throws IOException {
    if ("BTC".equals(base.toUpperCase())) {
      return bitKonan.getBitKonanTickerBTC();
    } else {
      return bitKonan.getBitKonanTicker(base);
    }
  }

  public BitKonanOrderBook getBitKonanOrderBook(String base) throws IOException {

    return bitKonan.getBitKonanOrderBook(base);
  }

  public List<BitKonanTrade> getBitKonanTrades(String base) throws IOException {
    if ("BTC".equals(base.toUpperCase())) {
      return bitKonan.getBitKonanTradesBTC();
    } else {
      throw new NotYetImplementedForExchangeException();
    }
  }

}
