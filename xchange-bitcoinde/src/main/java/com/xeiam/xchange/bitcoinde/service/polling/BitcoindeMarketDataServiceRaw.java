package com.xeiam.xchange.bitcoinde.service.polling;

import java.io.IOException;

import com.xeiam.xchange.Exchange;
import com.xeiam.xchange.bitcoinde.Bitcoinde;
import com.xeiam.xchange.bitcoinde.dto.marketdata.BitcoindeOrderBook;
import com.xeiam.xchange.bitcoinde.dto.marketdata.BitcoindeRate;
import com.xeiam.xchange.bitcoinde.dto.marketdata.BitcoindeTrade;

import si.mazi.rescu.RestProxyFactory;

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
