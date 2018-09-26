package org.knowm.xchange.getbtc.service;

import java.io.IOException;
import java.util.Map;
import org.knowm.xchange.Exchange;
import org.knowm.xchange.currency.CurrencyPair;
import org.knowm.xchange.getbtc.Getbtc;
import org.knowm.xchange.getbtc.GetbtcAdapters;
import org.knowm.xchange.getbtc.dto.marketdata.GetbtcOrderbook;
import org.knowm.xchange.getbtc.dto.marketdata.GetbtcTicker;
import org.knowm.xchange.getbtc.dto.marketdata.GetbtcTickerResponse;
import org.knowm.xchange.getbtc.dto.marketdata.GetbtcTransaction;
import si.mazi.rescu.RestProxyFactory;
import si.mazi.rescu.SynchronizedValueFactory;

/**
 * kevinobamatheus@gmail.com
 *
 * @author kevingates
 */
public class GetbtcMarketDataServiceRaw extends GetbtcBaseService {
  private final Getbtc exx;

  private String apiKey;
  private String secretKey;
  private SynchronizedValueFactory<Long> nonceFactory;

  public GetbtcMarketDataServiceRaw(Exchange exchange) {

    super(exchange);

    this.exx =
        RestProxyFactory.createProxy(
            Getbtc.class, exchange.getExchangeSpecification().getSslUri(), getClientConfig());

    this.apiKey = super.apiKey;
    this.secretKey = super.secretKey;
    this.nonceFactory = exchange.getNonceFactory();
  }

  /**
   * @return Object
   * @throws IOException
   */
  public GetbtcOrderbook getGetbtcOrderBook(CurrencyPair currencyPair) throws IOException {

    return exx.getOrderBook(GetbtcAdapters.getCurrency(currencyPair));
  }

  /**
   * getGetbtcTicker
   *
   * @return Object
   * @throws IOException
   */
  public GetbtcTickerResponse getGetbtcTicker(CurrencyPair currencyPair) throws IOException {

    return exx.getTicker(GetbtcAdapters.toSymbol(currencyPair));
  }

  protected Map<String, GetbtcTicker> getGetbtcTickers() throws IOException {
    Map<String, GetbtcTicker> data = exx.getTickers();

    return data;
  }

  /**
   * @param currencyPair
   * @return
   * @throws IOException
   */
  public GetbtcTransaction[] getTransactions(CurrencyPair currencyPair) throws IOException {
    try {
      return exx.getTransactions(GetbtcAdapters.toSymbol(currencyPair));
    } catch (IOException e) {
      System.out.println(e.getMessage());
    }
    return null;
  }
}
