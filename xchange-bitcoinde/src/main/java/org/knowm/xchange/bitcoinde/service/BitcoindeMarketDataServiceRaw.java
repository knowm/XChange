package org.knowm.xchange.bitcoinde.service;

import java.io.IOException;

import org.knowm.xchange.Exchange;
import org.knowm.xchange.bitcoinde.BitcoindeUtils;
import org.knowm.xchange.bitcoinde.dto.marketdata.BitcoindeOrderbookWrapper;
import org.knowm.xchange.bitcoinde.dto.marketdata.BitcoindeTradesWrapper;
import org.knowm.xchange.currency.CurrencyPair;

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

  public BitcoindeOrderbookWrapper getBitcoindeOrderBook(CurrencyPair currencyPair) throws IOException {

    return bitcoinde.getOrderBook(BitcoindeUtils.createBitcoindePair(currencyPair), apiKey, nonceFactory, signatureCreator);
  }

  public BitcoindeTradesWrapper getBitcoindeTrades(CurrencyPair currencyPair, Integer since) throws IOException {

    return bitcoinde.getTrades(BitcoindeUtils.createBitcoindePair(currencyPair), since, apiKey, nonceFactory, signatureCreator);
  }

}
