package org.knowm.xchange.bitfinex.service;

import com.fasterxml.jackson.databind.node.ArrayNode;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.List;
import org.knowm.xchange.Exchange;
import org.knowm.xchange.bitfinex.dto.BitfinexException;
import org.knowm.xchange.bitfinex.v1.dto.marketdata.BitfinexDepth;
import org.knowm.xchange.bitfinex.v1.dto.marketdata.BitfinexLend;
import org.knowm.xchange.bitfinex.v1.dto.marketdata.BitfinexLendDepth;
import org.knowm.xchange.bitfinex.v1.dto.marketdata.BitfinexSymbolDetail;
import org.knowm.xchange.bitfinex.v1.dto.marketdata.BitfinexTicker;
import org.knowm.xchange.bitfinex.v1.dto.marketdata.BitfinexTrade;
import org.knowm.xchange.bitfinex.v2.dto.marketdata.BitfinexPublicFundingTrade;
import org.knowm.xchange.bitfinex.v2.dto.marketdata.BitfinexPublicTrade;
import org.knowm.xchange.bitfinex.v2.dto.marketdata.Status;
import org.knowm.xchange.currency.Currency;
import org.knowm.xchange.currency.CurrencyPair;
import si.mazi.rescu.HttpStatusIOException;

/**
 * Implementation of the market data service for Bitfinex
 *
 * <ul>
 *   <li>Provides access to various market data values
 * </ul>
 */
public class BitfinexMarketDataServiceRaw extends BitfinexBaseService {

  /**
   * Constructor
   *
   * @param exchange
   */
  public BitfinexMarketDataServiceRaw(Exchange exchange) {

    super(exchange);
  }

  public BitfinexTicker getBitfinexTicker(String pair) throws IOException {
    BitfinexTicker bitfinexTicker = bitfinex.getTicker(pair);
    return bitfinexTicker;
  }

  public BitfinexDepth getBitfinexOrderBook(String pair, Integer limitBids, Integer limitAsks)
      throws IOException {
    BitfinexDepth bitfinexDepth;
    if (limitBids == null && limitAsks == null) {
      bitfinexDepth = bitfinex.getBook(pair);
    } else {
      bitfinexDepth = bitfinex.getBook(pair, limitBids, limitAsks);
    }
    return bitfinexDepth;
  }

  public BitfinexLendDepth getBitfinexLendBook(String currency, int limitBids, int limitAsks)
      throws IOException {
    BitfinexLendDepth bitfinexLendDepth = bitfinex.getLendBook(currency, limitBids, limitAsks);
    return bitfinexLendDepth;
  }

  public BitfinexTrade[] getBitfinexTrades(String pair, long sinceTimestamp) throws IOException {
    BitfinexTrade[] bitfinexTrades = bitfinex.getTrades(pair, sinceTimestamp);
    return bitfinexTrades;
  }

  public BitfinexLend[] getBitfinexLends(String currency, long sinceTimestamp, int limitTrades)
      throws IOException {
    BitfinexLend[] bitfinexLends = bitfinex.getLends(currency, sinceTimestamp, limitTrades);
    return bitfinexLends;
  }

  public Collection<String> getBitfinexSymbols() throws IOException {
    return bitfinex.getSymbols();
  }

  public List<CurrencyPair> getExchangeSymbols() throws IOException {
    List<CurrencyPair> currencyPairs = new ArrayList<>();
    for (String symbol : bitfinex.getSymbols()) {
      currencyPairs.add(BitfinexAdapters.adaptCurrencyPair(symbol));
    }
    return currencyPairs;
  }

  public List<BitfinexSymbolDetail> getSymbolDetails() throws IOException {
    return bitfinex.getSymbolsDetails();
  }

  //////// v2

  public org.knowm.xchange.bitfinex.v2.dto.marketdata.BitfinexTicker[] getBitfinexTickers(
      Collection<CurrencyPair> currencyPairs) throws IOException {
    List<ArrayNode> tickers =
        bitfinexV2.getTickers(BitfinexAdapters.adaptCurrencyPairsToTickersParam(currencyPairs));
    return BitfinexAdapters.adoptBitfinexTickers(tickers);
  }

  public org.knowm.xchange.bitfinex.v2.dto.marketdata.BitfinexTicker getBitfinexTickerV2(
      CurrencyPair currencyPair) throws IOException {
    List<ArrayNode> tickers =
        bitfinexV2.getTickers(
            BitfinexAdapters.adaptCurrencyPairsToTickersParam(
                Collections.singletonList(currencyPair)));
    org.knowm.xchange.bitfinex.v2.dto.marketdata.BitfinexTicker[] ticker =
        BitfinexAdapters.adoptBitfinexTickers(tickers);
    if (ticker.length == 0) {
      throw new BitfinexException("Unknown Symbol");
    } else {
      return ticker[0];
    }
  }

  public BitfinexPublicTrade[] getBitfinexPublicTrades(
      CurrencyPair currencyPair, int limitTrades, long startTimestamp, long endTimestamp, int sort)
      throws IOException {
    try {
      return bitfinexV2.getPublicTrades(
          BitfinexAdapters.adaptCurrencyPair(currencyPair),
          limitTrades,
          startTimestamp,
          endTimestamp,
          sort);
    } catch (HttpStatusIOException e) {
      throw new BitfinexException(e.getHttpBody());
    }
  }

  public BitfinexPublicFundingTrade[] getBitfinexPublicFundingTrades(
      Currency currency, int limitTrades, long startTimestamp, long endTimestamp, int sort)
      throws IOException {
    try {
      return bitfinexV2.getPublicFundingTrades(
          "f" + currency.toString(), limitTrades, startTimestamp, endTimestamp, sort);
    } catch (HttpStatusIOException e) {
      throw new BitfinexException(e.getHttpBody());
    }
  }

  public List<Status> getStatus(List<CurrencyPair> pairs) throws IOException {
    try {
      return bitfinexV2.getStatus(
          "deriv", BitfinexAdapters.adaptCurrencyPairsToTickersParam(pairs));
    } catch (HttpStatusIOException e) {
      throw new BitfinexException(e.getHttpBody());
    }
  }
}
