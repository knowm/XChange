package org.knowm.xchange.gatecoin.service.polling;

import java.io.IOException;

import org.knowm.xchange.Exchange;
import org.knowm.xchange.gatecoin.Gatecoin;
import org.knowm.xchange.gatecoin.dto.marketdata.Results.GatecoinDepthResult;
import org.knowm.xchange.gatecoin.dto.marketdata.Results.GatecoinTickerResult;
import org.knowm.xchange.gatecoin.dto.marketdata.Results.GatecoinTransactionResult;

import si.mazi.rescu.RestProxyFactory;

/**
 * @author sumedha
 */
public class GatecoinMarketDataServiceRaw extends GatecoinBasePollingService {

  private final Gatecoin gatecoin;

  /**
   * Constructor
   *
   * @param exchange
   */
  public GatecoinMarketDataServiceRaw(Exchange exchange) {

    super(exchange);
    this.gatecoin = RestProxyFactory.createProxy(Gatecoin.class, exchange.getExchangeSpecification().getSslUri());
  }

  public GatecoinTickerResult getGatecoinTicker() throws IOException {

    return gatecoin.getTicker();
  }

  public GatecoinDepthResult getGatecoinOrderBook(String currencyPair) throws IOException {

    String ccyPair = currencyPair.replaceAll("/", "");
    return gatecoin.getOrderBook(ccyPair);
  }

  public GatecoinTransactionResult getGatecoinTransactions(String currencyPair) throws IOException {

    String ccyPair = currencyPair.replaceAll("/", "");
    return gatecoin.getTransactions(ccyPair);
  }

  public GatecoinTransactionResult getGatecoinTransactions(String currencyPair, int count, long tid) throws IOException {

    String ccyPair = currencyPair.replaceAll("/", "");
    return gatecoin.getTransactions(ccyPair, count, tid);
  }

}
