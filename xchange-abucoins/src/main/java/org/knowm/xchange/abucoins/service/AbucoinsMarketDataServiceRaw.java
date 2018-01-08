package org.knowm.xchange.abucoins.service;

import java.io.IOException;

import org.knowm.xchange.Exchange;
import org.knowm.xchange.abucoins.Abucoins;
import org.knowm.xchange.abucoins.dto.marketdata.AbucoinsOrderBook;
import org.knowm.xchange.abucoins.dto.marketdata.AbucoinsProduct;
import org.knowm.xchange.abucoins.dto.marketdata.AbucoinsTicker;
import org.knowm.xchange.abucoins.dto.marketdata.AbucoinsTrade;

import si.mazi.rescu.RestProxyFactory;

/**
 * @author bryant_harris
 */
public class AbucoinsMarketDataServiceRaw extends AbucoinsBaseService {

  private final Abucoins abucoins;

  /**
   * Constructor
   *
   * @param exchange
   */
  public AbucoinsMarketDataServiceRaw(Exchange exchange) {

    super(exchange);

    this.abucoins = RestProxyFactory.createProxy(Abucoins.class, exchange.getExchangeSpecification().getSslUri(), getClientConfig());
  }
  
  public AbucoinsProduct[] getAbucoinsProducts() throws IOException {

    return abucoins.getProducts();
  }
  
  public AbucoinsProduct getAbucoinsProduct(String productID) throws IOException {

    return abucoins.getProduct(productID);
  }
  
  public AbucoinsOrderBook getAbucoinsOrderBook(String productID) throws IOException {
    return abucoins.getBook(productID);
  }
  
  public AbucoinsOrderBook getAbucoinsOrderBook(String productID, AbucoinsOrderBookLevel level) throws IOException {
    return abucoins.getBook(productID);
  }

  public AbucoinsTicker getAbucoinsTicker(String productID) throws IOException {

    AbucoinsTicker abucoinsTicker = abucoins.getTicker(productID);

    return abucoinsTicker;
  }
  
  public AbucoinsTrade[] getAbucoinsTrades(String productID) throws IOException {
    return abucoins.getTrades(productID);
  }

}
