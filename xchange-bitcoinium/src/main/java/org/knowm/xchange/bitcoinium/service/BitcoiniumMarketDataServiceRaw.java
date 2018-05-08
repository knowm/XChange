package org.knowm.xchange.bitcoinium.service;

import java.io.IOException;
import org.knowm.xchange.Exchange;
import org.knowm.xchange.bitcoinium.Bitcoinium;
import org.knowm.xchange.bitcoinium.BitcoiniumUtils;
import org.knowm.xchange.bitcoinium.dto.marketdata.BitcoiniumOrderbook;
import org.knowm.xchange.bitcoinium.dto.marketdata.BitcoiniumTicker;
import org.knowm.xchange.bitcoinium.dto.marketdata.BitcoiniumTickerHistory;
import org.knowm.xchange.utils.Assert;
import si.mazi.rescu.RestProxyFactory;

/**
 * Implementation of the raw market data service for Bitcoinium
 *
 * <ul>
 *   <li>Provides access to various market data values
 * </ul>
 */
public class BitcoiniumMarketDataServiceRaw extends BitcoiniumBaseService {

  private final Bitcoinium bitcoinium;

  /**
   * Constructor
   *
   * @param exchange
   */
  public BitcoiniumMarketDataServiceRaw(Exchange exchange) {

    super(exchange);
    this.bitcoinium =
        RestProxyFactory.createProxy(
            Bitcoinium.class, exchange.getExchangeSpecification().getSslUri(), getClientConfig());
  }

  /**
   * @param tradableIdentifier
   * @param currency
   * @return a Bitcoinium Ticker object
   * @throws IOException
   */
  public BitcoiniumTicker getBitcoiniumTicker(String tradableIdentifier, String currency)
      throws IOException {

    String pair = BitcoiniumUtils.createCurrencyPairString(tradableIdentifier, currency);

    // Request data
    BitcoiniumTicker bitcoiniumTicker =
        bitcoinium.getTicker(pair, exchange.getExchangeSpecification().getApiKey());

    // Adapt to XChange DTOs
    return bitcoiniumTicker;
  }

  /**
   * @param tradableIdentifier
   * @param currency
   * @param timeWindow - The time period of the requested ticker data. Value can be from set: {
   *     "10m", "1h", "3h", "12h", "24h", "3d", "7d", "30d", "2M" }
   * @return
   * @throws IOException
   */
  public BitcoiniumTickerHistory getBitcoiniumTickerHistory(
      String tradableIdentifier, String currency, String timeWindow) throws IOException {

    String pair = BitcoiniumUtils.createCurrencyPairString(tradableIdentifier, currency);

    verifyTimeWindow(timeWindow);

    // Request data
    BitcoiniumTickerHistory bitcoiniumTickerHistory =
        bitcoinium.getTickerHistory(
            pair, timeWindow, exchange.getExchangeSpecification().getApiKey());

    return bitcoiniumTickerHistory;
  }

  /**
   * @param tradableIdentifier
   * @param currency
   * @param orderbookwindow - The width of the Orderbook as a percentage plus and minus the current
   *     price. Value can be from set: { 2p, 5p, 10p, 20p, 50p, 100p }
   * @return
   */
  public BitcoiniumOrderbook getBitcoiniumOrderbook(
      String tradableIdentifier, String currency, String orderbookwindow) throws IOException {

    String pair = BitcoiniumUtils.createCurrencyPairString(tradableIdentifier, currency);

    verifyPriceWindow(orderbookwindow);

    // Request data
    BitcoiniumOrderbook bitcoiniumDepth =
        bitcoinium.getDepth(pair, orderbookwindow, exchange.getExchangeSpecification().getApiKey());

    return bitcoiniumDepth;
  }

  /** verify */
  private void verifyPriceWindow(String priceWindow) {

    Assert.isTrue(
        BitcoiniumUtils.isValidPriceWindow(priceWindow),
        priceWindow + " is not a valid price window!");
  }

  /** verify */
  private void verifyTimeWindow(String timeWindow) {

    Assert.isTrue(
        BitcoiniumUtils.isValidTimeWindow(timeWindow), timeWindow + " is not a valid time window!");
  }
}
