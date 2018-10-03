package org.knowm.xchange.bitflyer.service;

import java.io.IOException;
import java.util.List;
import org.knowm.xchange.Exchange;
import org.knowm.xchange.bitflyer.dto.BitflyerException;
import org.knowm.xchange.bitflyer.dto.account.BitflyerMarket;
import org.knowm.xchange.bitflyer.dto.marketdata.BitflyerOrderbook;
import org.knowm.xchange.bitflyer.dto.marketdata.BitflyerTicker;

/**
 * Implementation of the market data service for Bitflyer
 *
 * <ul>
 *   <li>Provides access to various market data values
 * </ul>
 */
public class BitflyerMarketDataServiceRaw extends BitflyerBaseService {
  /**
   * Constructor
   *
   * @param exchange baseExchange
   */
  public BitflyerMarketDataServiceRaw(Exchange exchange) {
    super(exchange);
  }

  public List<BitflyerMarket> getMarkets() throws IOException {
    try {
      return bitflyer.getMarkets();
    } catch (BitflyerException e) {
      throw handleError(e);
    }
  }

  public BitflyerTicker getTicker() throws IOException {
    try {
      return bitflyer.getTicker();
    } catch (BitflyerException e) {
      throw handleError(e);
    }
  }

  public BitflyerTicker getTicker(String productCode) throws IOException {
    try {
      return bitflyer.getTicker(productCode);
    } catch (BitflyerException e) {
      throw handleError(e);
    }
  }

  public BitflyerOrderbook getOrderbook() throws IOException {
    try {
      return bitflyer.getBoard();
    } catch (BitflyerException e) {
      throw handleError(e);
    }
  }

  public BitflyerOrderbook getOrderbook(String productCode) throws IOException {
    try {
      return bitflyer.getBoard(productCode);
    } catch (BitflyerException e) {
      throw handleError(e);
    }
  }
}
