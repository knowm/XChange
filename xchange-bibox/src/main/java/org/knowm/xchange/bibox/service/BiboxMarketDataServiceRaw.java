package org.knowm.xchange.bibox.service;

import static org.knowm.xchange.bibox.dto.BiboxAdapters.toBiboxPair;

import java.io.IOException;
import java.util.List;

import org.knowm.xchange.Exchange;
import org.knowm.xchange.bibox.BiboxException;
import org.knowm.xchange.bibox.dto.BiboxAdapters;
import org.knowm.xchange.bibox.dto.marketdata.BiboxMarket;
import org.knowm.xchange.bibox.dto.marketdata.BiboxTicker;
import org.knowm.xchange.bibox.dto.trade.BiboxOrderBook;
import org.knowm.xchange.currency.CurrencyPair;
import org.knowm.xchange.exceptions.ExchangeException;

/**
 * @author odrotleff
 */
public class BiboxMarketDataServiceRaw extends BiboxBaseService {

  private static final String TICKER_CMD = "ticker";
  private static final String DEPTH_CMD = "depth";
  private static final String ALL_TICKERS_CMD = "marketAll";

  protected BiboxMarketDataServiceRaw(Exchange exchange) {
    super(exchange);
  }
  
  public BiboxTicker getBiboxTicker(CurrencyPair currencyPair) throws IOException {
    try {
      return bibox.mdata(TICKER_CMD, toBiboxPair(currencyPair)).getResult();
    } catch (BiboxException e) {
      throw new ExchangeException(e.getMessage());
    }
  }
  
  public BiboxOrderBook getBiboxOrderBook(CurrencyPair currencyPair, Integer depth) throws IOException {
    try {
      return bibox.orderBook(DEPTH_CMD, BiboxAdapters.toBiboxPair(currencyPair), depth).getResult();
    } catch (BiboxException e) {
      throw new ExchangeException(e.getMessage());
    }
  }

  public List<BiboxMarket> getAllBiboxMarkets() throws IOException {
    try {
      return bibox.marketAll(ALL_TICKERS_CMD).getResult();
    } catch (BiboxException e) {
      throw new ExchangeException(e.getMessage());
    }
  }
}
