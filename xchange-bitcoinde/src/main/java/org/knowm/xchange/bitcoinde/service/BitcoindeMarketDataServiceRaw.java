package org.knowm.xchange.bitcoinde.service;

import java.io.IOException;

import org.knowm.xchange.Exchange;
import org.knowm.xchange.bitcoinde.Bitcoinde;
import org.knowm.xchange.bitcoinde.dto.marketdata.BitcoindeOrderBook;
import org.knowm.xchange.bitcoinde.dto.marketdata.BitcoindeRate;
import org.knowm.xchange.bitcoinde.dto.marketdata.BitcoindeTrade;

import si.mazi.rescu.RestProxyFactory;

/**
 * @author matthewdowney
 */
public class BitcoindeMarketDataServiceRaw extends BitcoindeBaseService {

  private final Bitcoinde bitcoinde;

  /**
   * Constructor
   * 
   * @param exchange
   */
  public BitcoindeMarketDataServiceRaw(Exchange exchange) {

    super(exchange);
    this.bitcoinde = RestProxyFactory.createProxy(Bitcoinde.class,
        exchange.getExchangeSpecification().getSslUri() + exchange.getExchangeSpecification().getApiKey() + "/");
  }

  public BitcoindeRate getBitcoindeRate() throws IOException {

    return bitcoinde.getRate();
  }

  public BitcoindeOrderBook getBitcoindeOrderBook() throws IOException {

    return bitcoinde.getOrderBook();
  }

  public BitcoindeTrade[] getBitcoindeTrades() throws IOException {

    return bitcoinde.getTrades();
  }

}
