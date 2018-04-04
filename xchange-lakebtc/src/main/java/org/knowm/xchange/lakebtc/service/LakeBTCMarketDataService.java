package org.knowm.xchange.lakebtc.service;

import java.io.IOException;
import java.util.Map;
import org.knowm.xchange.Exchange;
import org.knowm.xchange.currency.CurrencyPair;
import org.knowm.xchange.dto.marketdata.OrderBook;
import org.knowm.xchange.dto.marketdata.Ticker;
import org.knowm.xchange.dto.marketdata.Trades;
import org.knowm.xchange.exceptions.NotAvailableFromExchangeException;
import org.knowm.xchange.lakebtc.LakeBTCAdapters;
import org.knowm.xchange.lakebtc.dto.marketdata.LakeBTCOrderBook;
import org.knowm.xchange.lakebtc.dto.marketdata.LakeBTCTicker;
import org.knowm.xchange.service.marketdata.MarketDataService;

/** @author kpysniak */
public class LakeBTCMarketDataService extends LakeBTCMarketDataServiceRaw
    implements MarketDataService {

  /**
   * Constructor
   *
   * @param exchange
   */
  public LakeBTCMarketDataService(Exchange exchange) {

    super(exchange);
  }

  @Override
  public Ticker getTicker(CurrencyPair currencyPair, Object... args) throws IOException {

    LakeBTCTicker lakeBTCTicker;
    Map<String, LakeBTCTicker> lakeBTCTickers = getLakeBTCTickers();

    lakeBTCTicker = lakeBTCTickers.get(LakeBTCAdapters.adaptCurrencyPair(currencyPair));
    if (lakeBTCTicker == null) throw new NotAvailableFromExchangeException();

    return LakeBTCAdapters.adaptTicker(lakeBTCTicker, currencyPair);
  }

  @Override
  public OrderBook getOrderBook(CurrencyPair currencyPair, Object... args) throws IOException {

    LakeBTCOrderBook lakeBTCOrderBook = getLakeOrderBook(currencyPair);

    return LakeBTCAdapters.adaptOrderBook(lakeBTCOrderBook, currencyPair);
  }

  @Override
  public Trades getTrades(CurrencyPair currencyPair, Object... args) throws IOException {

    throw new NotAvailableFromExchangeException();
  }
}
