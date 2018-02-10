package org.knowm.xchange.bibox.service;

import java.io.IOException;
import java.util.List;

import org.knowm.xchange.Exchange;
import org.knowm.xchange.bibox.dto.BiboxAdapters;
import org.knowm.xchange.bibox.dto.marketdata.BiboxMarket;
import org.knowm.xchange.bibox.dto.trade.BiboxOrderBook;
import org.knowm.xchange.currency.CurrencyPair;
import org.knowm.xchange.dto.marketdata.OrderBook;
import org.knowm.xchange.dto.marketdata.Ticker;
import org.knowm.xchange.dto.marketdata.Trades;
import org.knowm.xchange.dto.meta.ExchangeMetaData;
import org.knowm.xchange.exceptions.NotYetImplementedForExchangeException;
import org.knowm.xchange.service.marketdata.MarketDataService;

/**
 * <p>
 * Implementation of the market data service for Bibox
 * </p>
 * <ul>
 * <li>Provides access to various market data values</li>
 * </ul>
 * 
 * @author odrotleff
 */
public class BiboxMarketDataService extends BiboxMarketDataServiceRaw implements MarketDataService {

  /**
   * Constructor
   *
   * @param exchange
   */
  public BiboxMarketDataService(Exchange exchange) {

    super(exchange);
  }

  @Override
  public Ticker getTicker(CurrencyPair currencyPair, Object... args) throws IOException {
    return BiboxAdapters.adaptTicker(getBiboxTicker(currencyPair), currencyPair);
  }

  @Override
  public OrderBook getOrderBook(CurrencyPair currencyPair, Object... args) throws IOException {
    Integer depth = 16; // default on website

    if (args != null && args.length > 0) {
      if (args[0] instanceof Integer && (Integer) args[0] > 0) {
        depth = (Integer) args[0];
      }
    }
    BiboxOrderBook biboxOrderBook = getBiboxOrderBook(currencyPair, depth);
    return BiboxAdapters.adaptOrderBook(biboxOrderBook, currencyPair);
  }

  @Override
  public Trades getTrades(CurrencyPair currencyPair, Object... args) throws IOException {
    throw new NotYetImplementedForExchangeException("This operation is not yet implemented for this exchange");
  }

  public ExchangeMetaData getMetadata() throws IOException {
    List<BiboxMarket> markets = getAllBiboxMarkets();
    return BiboxAdapters.adaptMetadata(markets);
  }
}
