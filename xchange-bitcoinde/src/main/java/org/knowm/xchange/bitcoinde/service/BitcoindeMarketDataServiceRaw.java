package org.knowm.xchange.bitcoinde.service;

import java.io.IOException;

import org.knowm.xchange.Exchange;
import org.knowm.xchange.bitcoinde.dto.marketdata.BitcoindeOrderbookWrapper;
import org.knowm.xchange.bitcoinde.dto.marketdata.BitcoindeTradesWrapper;

import si.mazi.rescu.SynchronizedValueFactory;

/**
 * @author matthewdowney
 */
public class BitcoindeMarketDataServiceRaw extends BitcoindeBaseService {

  private final SynchronizedValueFactory<Long> nonceFactory;

  /**
   * Constructor
   *
   * @param exchange
   */
  public BitcoindeMarketDataServiceRaw(Exchange exchange) {

    super(exchange);
    this.nonceFactory = exchange.getNonceFactory();

  }

  public BitcoindeOrderbookWrapper getBitcoindeOrderBook() throws IOException {

    return bitcoinde.getOrderBook(apiKey, nonceFactory, signatureCreator);
  }

  public BitcoindeTradesWrapper getBitcoindeTrades(Integer since) throws IOException {

    return bitcoinde.getTrades(since, apiKey, nonceFactory, signatureCreator);
  }

}
