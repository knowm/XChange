package org.knowm.xchange.zaif.service;

import java.io.IOException;
import java.util.List;

import org.knowm.xchange.Exchange;
import org.knowm.xchange.currency.CurrencyPair;
import org.knowm.xchange.dto.marketdata.OrderBook;
import org.knowm.xchange.dto.marketdata.Ticker;
import org.knowm.xchange.dto.marketdata.Trades;
import org.knowm.xchange.dto.meta.ExchangeMetaData;
import org.knowm.xchange.exceptions.NotYetImplementedForExchangeException;
import org.knowm.xchange.service.marketdata.MarketDataService;
import org.knowm.xchange.zaif.ZaifAdapters;
import org.knowm.xchange.zaif.dto.marketdata.ZaifMarket;

public class ZaifMarketDataService extends ZaifMarketDataServiceRaw implements MarketDataService {

  public ZaifMarketDataService(Exchange exchange) {
    super(exchange);
  }

  @Override
  public Ticker getTicker(CurrencyPair currencyPair, Object... args) throws IOException {
    throw new NotYetImplementedForExchangeException();
  }

  @Override
  public OrderBook getOrderBook(CurrencyPair currencyPair, Object... args) throws IOException {
    return ZaifAdapters.adaptOrderBook(this.getZaifFullBook(currencyPair), currencyPair);
  }

  @Override
  public Trades getTrades(CurrencyPair currencyPair, Object... args) throws IOException {
    throw new NotYetImplementedForExchangeException();
  }

  public ExchangeMetaData getMetadata() throws IOException {
    List<ZaifMarket> markets = this.getAllMarkets();
    return ZaifAdapters.adaptMetadata(markets);
  }
}
