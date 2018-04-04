package org.knowm.xchange.abucoins.service;

import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.TimeZone;
import org.knowm.xchange.Exchange;
import org.knowm.xchange.abucoins.dto.AbucoinsServerTime;
import org.knowm.xchange.abucoins.dto.marketdata.AbucoinsFullTicker;
import org.knowm.xchange.abucoins.dto.marketdata.AbucoinsHistoricRate;
import org.knowm.xchange.abucoins.dto.marketdata.AbucoinsHistoricRates;
import org.knowm.xchange.abucoins.dto.marketdata.AbucoinsOrderBook;
import org.knowm.xchange.abucoins.dto.marketdata.AbucoinsProduct;
import org.knowm.xchange.abucoins.dto.marketdata.AbucoinsProductStat;
import org.knowm.xchange.abucoins.dto.marketdata.AbucoinsProductStats;
import org.knowm.xchange.abucoins.dto.marketdata.AbucoinsTicker;
import org.knowm.xchange.abucoins.dto.marketdata.AbucoinsTrade;
import org.knowm.xchange.exceptions.ExchangeException;

/**
 * <p>Class providing a 1:1 proxy for the Abucoins market related
 * REST requests.</p>
 * <ul>
 * <li>{@link #getAbucoinsServerTime GET /time}</li>
 * <li>{@link #getAbucoinsProducts GET /products}</li>
 * <li>{@link #getAbucoinsProduct() GET /products/&#123;product-id&#125;}</li>
 * <li>{@link #getAbucoinsOrderBook GET /products/&#123;product-id&#125;/book}</li>
 * <li>{@link #getAbucoinsOrderBook(String, AbucoinsOrderBookLevel) GET /products/&#123;product-id&#125;/book?level=&#123;level&#125;}</li>
 * <li>{@link #getAbucoinsTickers GET /products/ticker}</li>
 * <li>{@link #getAbucoinsTicker GET /products/&#123;product-id&#125;/ticker}</li>
 * <li>{@link #getAbucoinsTrades(String) GET /products/&#123;product-id&#125;/trades}</li>
 * <li>{@link #getAbucoinsHistoricRates GET /products/&#123;product-id&#125;/candles?granularity=[granularity]&start=[UTC time of start]&end=[UTC time of end]}</li>
 * <li>{@link #getAbucoinsProductStats GET /products/stats</li>
 * <ol>
 *
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
   *
   * @return
   * @throws IOException
   */
  public AbucoinsServerTime getAbucoinsServerTime() throws IOException {
    return abucoins.getTime();
  }

  /**
   * Corresponds to <code>GET /products</code>
   *
   * @return
   * @throws IOException
   */
  public AbucoinsProduct[] getAbucoinsProducts() throws IOException {
    return abucoins.getProducts();
  }

  /**
   * Corresponds to <code>GET /products/{product-id}</code>
   *
   * @return
   * @throws IOException
   */
  public AbucoinsProduct getAbucoinsProduct(String productID) throws IOException {
    return abucoins.getProduct(productID);
  }

  /**
   * Corresponds to <code>GET /products/{product-id}/book</code>
   *
   * @return
   * @throws IOException
   */
  public AbucoinsOrderBook getAbucoinsOrderBook(String productID) throws IOException {
    return abucoins.getBook(productID);
  }

  /**
   * Corresponds to <code>GET /products/{product-id}/book?level={level}</code>
   *
   * @return
   * @throws IOException
   */
  public AbucoinsOrderBook getAbucoinsOrderBook(String productID, AbucoinsOrderBookLevel level)
      throws IOException {
    return abucoins.getBook(productID, level.name());
  }

  /**
   * Corresponds to <code>GET /products/ticker</code>
   *
   * @return
   * @throws IOException
   */
  public AbucoinsFullTicker[] getAbucoinsTickers() throws IOException {

    AbucoinsFullTicker[] abucoinsTickers = abucoins.getTicker();

    return abucoinsTickers;
  }

  /**
   * Corresponds to <code>GET /products/{product-id}/ticker</code>
   *
   * @return
   * @throws IOException
   */
  public AbucoinsTicker getAbucoinsTicker(String productID) throws IOException {

    AbucoinsTicker abucoinsTicker = abucoins.getTicker(productID);

    return abucoinsTicker;
  }

  /**
   * Corresponds to <code>GET /products/{product-id}/trades</code>
   *
   * @return
   * @throws IOException
   */
  public AbucoinsTrade[] getAbucoinsTrades(String productID) throws IOException {
    return abucoins.getTrades(productID);
  }

  /**
   * Corresponds to <code>
   * GET /products/&lt;product-id&gt;/candles?granularity=[granularity]&start=[UTC time of start]&end=[UTC time of end]
   * </code>
   *
   * @param productID
   * @param granularitySeconds Desired timeslice in seconds
   * @param start Start time
   * @param end End time
   * @return
   * @throws IOException
   */
  public AbucoinsHistoricRate[] getAbucoinsHistoricRates(
      String productID, long granularitySeconds, Date start, Date end) throws IOException {
    if (start == null || end == null)
      throw new IllegalArgumentException("Must provide begin and end dates");

    String granularity = String.valueOf(granularitySeconds);

    SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ssZ");
    dateFormat.setTimeZone(TimeZone.getTimeZone("UTC"));

    String startDate = dateFormat.format(start);
    String endDate = dateFormat.format(end);

    AbucoinsHistoricRates rates =
        abucoins.getHistoricRates(productID, granularity, startDate, endDate);
    if (rates.getMessage() != null) throw new ExchangeException(rates.getMessage());

    return rates.getHistoricRates();
  }

  /**
   * Corresponds to <code>GET /products/stats</code>
   *
   * @return
   * @throws IOException
   */
  public AbucoinsProductStat[] getAbucoinsProductStats() throws IOException {
    AbucoinsProductStats stats = abucoins.getProductStats();

    if (stats.getStats().length == 1 && stats.getStats()[0].getMessage() != null)
      throw new ExchangeException(stats.getStats()[0].getMessage());

    return stats.getStats();
  }
}
