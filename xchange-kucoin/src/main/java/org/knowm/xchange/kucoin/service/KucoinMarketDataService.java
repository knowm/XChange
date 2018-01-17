package org.knowm.xchange.kucoin.service;

import java.io.IOException;
import java.util.List;
import java.util.stream.Collectors;

import org.knowm.xchange.Exchange;
import org.knowm.xchange.currency.CurrencyPair;
import org.knowm.xchange.dto.marketdata.OrderBook;
import org.knowm.xchange.dto.marketdata.Ticker;
import org.knowm.xchange.dto.marketdata.Trades;
import org.knowm.xchange.exceptions.NotYetImplementedForExchangeException;
import org.knowm.xchange.kucoin.KucoinAdapters;
import org.knowm.xchange.service.marketdata.MarketDataService;

/**
 * @author Jan Akerman
 */
public class KucoinMarketDataService extends KucoinMarketDataServiceRaw implements MarketDataService {

  public KucoinMarketDataService(Exchange exchange) {
    super(exchange);
  }

  @Override
  public Ticker getTicker(CurrencyPair currencyPair, Object... args) throws IOException {
    return KucoinAdapters.adaptTicker(getKucoinTicker(currencyPair));
  }

  @Override
  public OrderBook getOrderBook(CurrencyPair currencyPair, Object... args) throws IOException {
    throw new NotYetImplementedForExchangeException("Not yet implemented for this exchange");
  }

  @Override
  public Trades getTrades(CurrencyPair currencyPair, Object... args) throws IOException {
    throw new NotYetImplementedForExchangeException("Not yet implemented for this exchange");
  }

  public List<Ticker> getTickers() throws IOException {
    return getKucoinTickers().stream()
        .map(KucoinAdapters::adaptTicker)
        .collect(Collectors.toList());
  }
}
