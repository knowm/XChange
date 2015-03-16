package com.xeiam.xchange.bitfinex.v1.service.polling;

import java.io.IOException;
import java.util.Collection;

import com.xeiam.xchange.Exchange;
import com.xeiam.xchange.bitfinex.v1.dto.BitfinexException;
import com.xeiam.xchange.bitfinex.v1.dto.marketdata.*;
import com.xeiam.xchange.exceptions.ExchangeException;

/**
 * <p>
 * Implementation of the market data service for Bitfinex
 * </p>
 * <ul>
 * <li>Provides access to various market data values</li>
 * </ul>
 */
public class BitfinexMarketDataServiceRaw extends BitfinexBasePollingService {

  /**
   * Constructor
   *
   * @param exchange
   */
  public BitfinexMarketDataServiceRaw(Exchange exchange) {

    super(exchange);
  }

  public BitfinexTicker getBitfinexTicker(String pair) throws IOException {

    try {
      BitfinexTicker bitfinexTicker = bitfinex.getTicker(pair);
      return bitfinexTicker;
    } catch (BitfinexException e) {
      throw new ExchangeException(e.getMessage());
    }
  }

  public BitfinexDepth getBitfinexOrderBook(String pair, Integer limitBids, Integer limitAsks) throws IOException {

    try {
      BitfinexDepth bitfinexDepth;
      if (limitBids == null && limitAsks == null) {
        bitfinexDepth = bitfinex.getBook(pair);
      } else {
        bitfinexDepth = bitfinex.getBook(pair, limitBids, limitAsks);
      }
      return bitfinexDepth;
    } catch (BitfinexException e) {
      throw new ExchangeException(e.getMessage());
    }
  }

  public BitfinexLendDepth getBitfinexLendBook(String currency, int limitBids, int limitAsks) throws IOException {

    try {
      BitfinexLendDepth bitfinexLendDepth = bitfinex.getLendBook(currency, limitBids, limitAsks);
      return bitfinexLendDepth;
    } catch (BitfinexException e) {
      throw new ExchangeException("Bitfinex returned an error: " + e.getMessage());
    }
  }

  public BitfinexTrade[] getBitfinexTrades(String pair, long sinceTimestamp) throws IOException {

    try {
      BitfinexTrade[] bitfinexTrades = bitfinex.getTrades(pair, sinceTimestamp);
      return bitfinexTrades;
    } catch (BitfinexException e) {
      throw new ExchangeException("Bitfinex returned an error: " + e.getMessage());
    }
  }

  public BitfinexLend[] getBitfinexLends(String currency, long sinceTimestamp, int limitTrades) throws IOException {

    try {
      BitfinexLend[] bitfinexLends = bitfinex.getLends(currency, sinceTimestamp, limitTrades);
      return bitfinexLends;
    } catch (BitfinexException e) {
      throw new ExchangeException("Bitfinex returned an error: " + e.getMessage());
    }
  }

  public Collection<String> getBitfinexSymbols() throws IOException {

    try {
      return bitfinex.getSymbols();
    } catch (BitfinexException e) {
      throw new ExchangeException("Bitfinex returned an error: " + e.getMessage());
    }
  }

  public Collection<BitfinexSymbolDetails> getSymbolsDetails() throws IOException {
    return bitfinex.getSymbolsDetails();
  }
}
