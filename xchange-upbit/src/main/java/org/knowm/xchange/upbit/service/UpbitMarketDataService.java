package org.knowm.xchange.upbit.service;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import org.knowm.xchange.Exchange;
import org.knowm.xchange.currency.CurrencyPair;
import org.knowm.xchange.dto.marketdata.OrderBook;
import org.knowm.xchange.dto.marketdata.Ticker;
import org.knowm.xchange.dto.marketdata.Trades;
import org.knowm.xchange.dto.meta.ExchangeMetaData;
import org.knowm.xchange.service.marketdata.MarketDataService;
import org.knowm.xchange.service.marketdata.params.Params;
import org.knowm.xchange.upbit.UpbitAdapters;

/** @author interwater */
public class UpbitMarketDataService extends UpbitMarketDataServiceRaw implements MarketDataService {

  /**
   * Constructor
   *
   * @param exchange
   */
  public UpbitMarketDataService(Exchange exchange) {

    super(exchange);
  }

  @Override
  public List<Ticker> getTickers(Params params) throws IOException {
    final List<CurrencyPair> currencyPairs =
        new ArrayList<>(exchange.getExchangeMetaData().getCurrencyPairs().keySet());
    return UpbitAdapters.adaptTickers(super.getTickers(currencyPairs));
  }

  @Override
  public Ticker getTicker(CurrencyPair currencyPair, Object... args) throws IOException {
    return UpbitAdapters.adaptTicker(super.getTicker(currencyPair));
  }

  @Override
  public OrderBook getOrderBook(CurrencyPair currencyPair, Object... args) throws IOException {
    return UpbitAdapters.adaptOrderBook(getUpbitOrderBook(currencyPair));
  }

  @Override
  public Trades getTrades(CurrencyPair currencyPair, Object... args) throws IOException {
    return UpbitAdapters.adaptTrades(super.getTrades(currencyPair), currencyPair);
  }

  public ExchangeMetaData getMetaData() throws IOException {
    return UpbitAdapters.adaptMetadata(getMarketAll());
  }
}
