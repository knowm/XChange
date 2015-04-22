package com.xeiam.xchange.bitcoinde.service.polling;

import java.io.IOException;

import si.mazi.rescu.RestProxyFactory;

import com.xeiam.xchange.Exchange;
import com.xeiam.xchange.bitcoinde.Bitcoinde;
import com.xeiam.xchange.bitcoinde.dto.marketdata.BitcoindeOrderBook;
import com.xeiam.xchange.bitcoinde.dto.marketdata.BitcoindeRate;
import com.xeiam.xchange.bitcoinde.dto.marketdata.BitcoindeTrade;

/**
 * @author matthewdowney
 */
public class BitcoindeMarketDataServiceRaw extends BitcoindeBasePollingService {

  private final Bitcoinde bitcoinde;

  /**
   * Constructor
   * 
   * @param exchange
   */
  public BitcoindeMarketDataServiceRaw(Exchange exchange) {

    super(exchange);
    this.bitcoinde = RestProxyFactory.createProxy(Bitcoinde.class, exchange.getExchangeSpecification().getSslUri());
  }

  public BitcoindeRate getBitcoindeRate(String key) throws IOException {

    return bitcoinde.getRate(key);
  }

  public BitcoindeOrderBook getBitcoindeOrderBook(String key) throws IOException {

    return bitcoinde.getOrderBook(key);
  }

  public BitcoindeTrade[] getBitcoindeTrades(String key) throws IOException {

    return bitcoinde.getTrades(key);
  }

}
