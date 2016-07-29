package org.knowm.xchange.coinsetter.service.polling;

import static org.knowm.xchange.coinsetter.CoinsetterExchange.DEPTH_FORMAT_LIST;

import java.io.IOException;
import java.math.BigDecimal;

import org.knowm.xchange.Exchange;
import org.knowm.xchange.coinsetter.dto.marketdata.CoinsetterLast;
import org.knowm.xchange.coinsetter.dto.marketdata.CoinsetterListDepth;
import org.knowm.xchange.coinsetter.dto.marketdata.CoinsetterPairedDepth;
import org.knowm.xchange.coinsetter.dto.marketdata.CoinsetterQuote;
import org.knowm.xchange.coinsetter.dto.marketdata.CoinsetterTicker;
import org.knowm.xchange.coinsetter.rs.CoinsetterMarketData;

import si.mazi.rescu.RestProxyFactory;

/**
 * Market data raw service.
 */
public class CoinsetterMarketDataServiceRaw extends CoinsetterBasePollingService {

  protected final CoinsetterMarketData marketData;

  /**
   * Constructor
   *
   * @param exchange
   */
  public CoinsetterMarketDataServiceRaw(Exchange exchange) {

    super(exchange);

    String baseUrl = exchange.getExchangeSpecification().getSslUri();
    marketData = RestProxyFactory.createProxy(CoinsetterMarketData.class, baseUrl);
  }

  /**
   * @throws IOException indicates I/O exception.
   * @see #getCoinsetterLast(int).
   */
  public CoinsetterLast getCoinsetterLast() throws IOException {

    return marketData.getLast();
  }

  /**
   * Retrieves the last available trades data.
   *
   * @param lookback retrieve the last <code>n</code> trades from newest to oldest (maximum supported value is 500).
   * @return the last available trades data.
   * @throws IOException indicates I/O exception.
   * @see <a href="https://www.coinsetter.com/api/marketdata/last">Market Data: Last</a>
   */
  public CoinsetterLast getCoinsetterLast(int lookback) throws IOException {

    return marketData.getLast(lookback);
  }

  /**
   * Retrieves Level 1 market data.
   *
   * @return the Level 1 market data.
   * @throws IOException indicates I/O exception.
   * @see <a href="https://www.coinsetter.com/api/marketdata/ticker">Market Data: Ticker</a>
   */
  public CoinsetterTicker getCoinsetterTicker() throws IOException {

    return marketData.getTicker();
  }

  /**
   * Retrieves the latest Level 2 market data for the top n price levels of the book.
   *
   * @param depth number of levels or pass "MAX" (Optional). Market Depth to request.
   * @param exchange "SMART" (Default) or "COINSETTER" for Coinsetter-only data (Optional). Filter for given exchange or SMART for aggregated data.
   * @return the latest Level 2 market data for the top n price levels of the book.
   * @throws IOException indicates I/O exception.
   * @see #getCoinsetterFullDepth(String)
   * @see <a href="https://www.coinsetter.com/api/marketdata/depth">Market Data: Depth</a>
   */
  public CoinsetterPairedDepth getCoinsetterPairedDepth(int depth, String exchange) throws IOException {

    return marketData.getPairedDepth(depth, exchange);
  }

  /**
   * @throws IOException indicates I/O exception.
   * @see #getCoinsetterPairedDepth(int, String).
   */
  public CoinsetterListDepth getCoinsetterListDepth(int depth, String exchange) throws IOException {

    return marketData.getListDepth(depth, DEPTH_FORMAT_LIST, exchange);
  }

  /**
   * @see #getCoinsetterFullDepth(String).
   */
  public CoinsetterListDepth getCoinsetterFullDepth() throws IOException {

    return marketData.getFullDepth();
  }

  /**
   * Retrieves the whole aggregated order book.
   * <p>
   * New full-depth snapshot is generated every 60 seconds. So, there is no need to call this more than once a minute.
   * </p>
   *
   * @param exchange "SMART" (Default) or "COINSETTER" for Coinsetter-only data (Optional). Filter for given exchange or deafult to SMART for
   *        aggregated data.
   * @return the whole aggreated order book.
   * @throws IOException indicates I/O exception.
   * @see <a href="https://www.coinsetter.com/api/marketdata/full_depth">Market Data: Full-Depth</a>
   */
  public CoinsetterListDepth getCoinsetterFullDepth(String exchange) throws IOException {

    return marketData.getFullDepth(exchange);
  }

  /**
   * Ever want to know at a glance how much it will cost to buy 10 BTC or 30BTC? Use this call to do so. It returns an indicative or synthetic quote
   * for a given quantity based on the current state of the order book. Using this request you can retrieve a VWAP (volume-weighted average price)
   * quote in USD for a given BTC quantity. For example, if you want to know what you will pay to buy 10 BTC, you make a GET request to our api and
   * provide 10 as the quantity, the value returned will be the VWAP in dollars (ex. $572.68, total $5726.80).
   *
   * @param quantity if a positive quantity is passed in, it will query the BID side to determine the volume-weighted average price and total order
   *        cost. For negative quantity, the ASK side. (Must be less than 150 and divisible by 5).
   * @param symbol trade symbol to find amount in (Currently only BTCUSD is supported and is the default).
   * @return the quote.
   * @throws IOException indicates I/O exception.
   * @see <a href="https://www.coinsetter.com/api/marketdata/quote">Market Data: Quote</a>
   */
  public CoinsetterQuote getCoinsetterQuote(BigDecimal quantity, String symbol) throws IOException {

    return marketData.getQuote(quantity, symbol);
  }

}
