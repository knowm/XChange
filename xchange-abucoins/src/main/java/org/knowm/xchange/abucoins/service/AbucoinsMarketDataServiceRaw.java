package org.knowm.xchange.abucoins.service;

import java.io.IOException;

import org.knowm.xchange.Exchange;
import org.knowm.xchange.abucoins.dto.AbucoinsServerTime;
import org.knowm.xchange.abucoins.dto.marketdata.AbucoinsOrderBook;
import org.knowm.xchange.abucoins.dto.marketdata.AbucoinsProduct;
import org.knowm.xchange.abucoins.dto.marketdata.AbucoinsTicker;
import org.knowm.xchange.abucoins.dto.marketdata.AbucoinsTrade;

/**
 * <p>Class providing a 1:1 proxy for the Abucoins market related
 * REST requests.</p>
 * 
 * <ul>
 * <li>{@link #getAbucoinsServerTime GET /time}</li>
 * <li>{@link #getAbucoinsProducts GET /products}</li>
 * <li>{@link #getAbucoinsProduct() GET /products/&#123;product-id&#125;}</li>
 * <li>{@link #getAbucoinsOrderBook GET /products/&#123;product-id&#125;/book}</li>
 * <li>{@link #getAbucoinsOrderBook(String, AbucoinsOrderBookLevel) GET /products/&#123;product-id&#125;/book?level=&#123;level&#125;}</li>
 * <li>{@link #getAbucoinsTicker GET /products/&#123;product-id&#125;/ticker}</li>
 * <li>{@link #getAbucoinsTrades(String) GET /products/&#123;product-id&#125;/trades}</li>
 * <ol>
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
  
  /**
   * Corresponds to <code>GET /time</code>
   * @return
   * @throws IOException
   */
  public AbucoinsServerTime getAbucoinsServerTime() throws IOException {
    return abucoins.getTime();
  }

  /**
   * Corresponds to <code>GET /products</code>
   * @return
   * @throws IOException
   */
  public AbucoinsProduct[] getAbucoinsProducts() throws IOException {
    return abucoins.getProducts();
  }
  
  /**
   * Corresponds to <code>GET /products/{product-id}</code>
   * @return
   * @throws IOException
   */  
  public AbucoinsProduct getAbucoinsProduct(String productID) throws IOException {
    return abucoins.getProduct(productID);
  }
  
  /**
   * Corresponds to <code>GET /products/{product-id}/book</code>
   * @return
   * @throws IOException
   */
  public AbucoinsOrderBook getAbucoinsOrderBook(String productID) throws IOException {
    return abucoins.getBook(productID);
  }

  /**
   * Corresponds to <code>GET /products/{product-id}/book?level={level}</code>
   * @return
   * @throws IOException
   */
  public AbucoinsOrderBook getAbucoinsOrderBook(String productID, AbucoinsOrderBookLevel level) throws IOException {
    return abucoins.getBook(productID, level.name());
  }

  /**
   * Corresponds to <code>GET /products/{product-id}/ticker</code>
   * @return
   * @throws IOException
   */
  public AbucoinsTicker getAbucoinsTicker(String productID) throws IOException {

    AbucoinsTicker abucoinsTicker = abucoins.getTicker(productID);

    return abucoinsTicker;
  }
  
  /**
   * Corresponds to <code>GET /products/{product-id}/trades</code>
   * @return
   * @throws IOException
   */
  public AbucoinsTrade[] getAbucoinsTrades(String productID) throws IOException {
    return abucoins.getTrades(productID);
  }

}
