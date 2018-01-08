package org.knowm.xchange.abucoins.service;

import java.io.IOException;

import org.knowm.xchange.Exchange;
import org.knowm.xchange.abucoins.dto.AbucoinsServerTime;
import org.knowm.xchange.abucoins.dto.marketdata.AbucoinsOrderBook;
import org.knowm.xchange.abucoins.dto.marketdata.AbucoinsProduct;
import org.knowm.xchange.abucoins.dto.marketdata.AbucoinsTicker;
import org.knowm.xchange.abucoins.dto.marketdata.AbucoinsTrade;

/**
 * @author bryant_harris
 */
public class AbucoinsMarketDataServiceRaw extends AbucoinsBaseService {


  /**
   * Constructor
   *
   * @param exchange
   */
  public AbucoinsMarketDataServiceRaw(Exchange exchange) {

    super(exchange);

  }
  
  public AbucoinsServerTime getAbucoinsServerTime() throws IOException {
    return abucoins.getTime();
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
