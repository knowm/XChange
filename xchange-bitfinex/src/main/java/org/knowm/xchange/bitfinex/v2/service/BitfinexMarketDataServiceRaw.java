package org.knowm.xchange.bitfinex.v2.service;

import com.fasterxml.jackson.databind.exc.MismatchedInputException;
import java.io.IOException;
import java.util.Collections;
import java.util.List;
import org.knowm.xchange.Exchange;
import org.knowm.xchange.bitfinex.common.dto.BitfinexException;
import org.knowm.xchange.bitfinex.v2.BitfinexAdapters;
import org.knowm.xchange.bitfinex.v2.dto.marketdata.BitfinexPublicFundingTrade;
import org.knowm.xchange.bitfinex.v2.dto.marketdata.BitfinexPublicTrade;
import org.knowm.xchange.bitfinex.v2.dto.marketdata.BitfinexTicker;
import org.knowm.xchange.currency.Currency;
import org.knowm.xchange.currency.CurrencyPair;
import si.mazi.rescu.HttpStatusIOException;

public class BitfinexMarketDataServiceRaw extends BitfinexBaseService {
  /**
   * Constructor
   *
   * @param exchange
   */
  public BitfinexMarketDataServiceRaw(Exchange exchange) {
    super(exchange);
  }

  public BitfinexTicker[] getBitfinexTickers(List<CurrencyPair> currencyPairs) throws IOException {
    return bitfinex.getTickers(BitfinexAdapters.adaptCurrencyPairsToTickersParam(currencyPairs));
  }

  public BitfinexTicker getBitfinexTicker(CurrencyPair currencyPair) throws IOException {
    BitfinexTicker[] ticker =
        bitfinex.getTickers(
            BitfinexAdapters.adaptCurrencyPairsToTickersParam(
                Collections.singletonList(currencyPair)));
    if (ticker.length == 0) {
      throw handleException(new BitfinexException("Unknown Symbol"));
    } else {
      return ticker[0];
    }
  }

  public BitfinexPublicTrade[] getBitfinexPublicTrades(CurrencyPair currencyPair, int limitTrades, long startTimestamp, long endTimestamp, int sort) throws IOException {
    try {
      return bitfinex.getPublicTrades("t" + currencyPair.base.toString() + currencyPair.counter.toString(), limitTrades, startTimestamp, endTimestamp, sort);
    } catch ( HttpStatusIOException e) {
        throw handleException(new BitfinexException(e.getHttpBody()));
    } 
  }

  public BitfinexPublicFundingTrade[] getBitfinexPublicFundingTrades(Currency currency, int limitTrades, long startTimestamp, long endTimestamp, int sort) throws IOException {
    try {
      return bitfinex.getPublicFundingTrades("f" + currency.toString(), limitTrades, startTimestamp, endTimestamp, sort);
    } catch ( HttpStatusIOException e) {
        throw handleException(new BitfinexException(e.getHttpBody()));
    } 
  }
}
