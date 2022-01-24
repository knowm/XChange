package org.knowm.xchange.coinmarketcap.pro.v1.service;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import org.knowm.xchange.Exchange;
import org.knowm.xchange.coinmarketcap.pro.v1.CmcAdapter;
import org.knowm.xchange.coinmarketcap.pro.v1.CmcErrorAdapter;
import org.knowm.xchange.coinmarketcap.pro.v1.dto.marketdata.CmcTicker;
import org.knowm.xchange.currency.Currency;
import org.knowm.xchange.currency.CurrencyPair;
import org.knowm.xchange.dto.marketdata.OrderBook;
import org.knowm.xchange.dto.marketdata.Ticker;
import org.knowm.xchange.dto.marketdata.Trades;
import org.knowm.xchange.exceptions.NotAvailableFromExchangeException;
import org.knowm.xchange.service.marketdata.MarketDataService;
import org.knowm.xchange.service.marketdata.params.CurrencyPairsParam;
import org.knowm.xchange.service.marketdata.params.Params;
import si.mazi.rescu.HttpStatusIOException;

public class CmcMarketDataService extends CmcMarketDataServiceRaw implements MarketDataService {

  public CmcMarketDataService(Exchange exchange) {
    super(exchange);
  }

  @Override
  public Ticker getTicker(CurrencyPair currencyPair, Object... args) throws IOException {

    CmcTicker ticker = null;
    try {
      ticker = super.getCmcLatestQuote(currencyPair).get(currencyPair.base.getCurrencyCode());
    } catch (HttpStatusIOException ex) {
      CmcErrorAdapter.adapt(ex);
    }

    return CmcAdapter.adaptTicker(ticker, currencyPair);
  }

  @Override
  public List<Ticker> getTickers(Params params) throws IOException {

    if (!(params instanceof CurrencyPairsParam)) {
      throw new IllegalArgumentException("Params must be instance of CurrencyPairsParam");
    }

    Collection<CurrencyPair> pairs = ((CurrencyPairsParam) params).getCurrencyPairs();

    Set<Currency> baseSymbols = new HashSet<>();
    Set<Currency> convertSymbols = new HashSet<>();
    for (CurrencyPair pair : pairs) {
      baseSymbols.add(pair.base);
      convertSymbols.add(pair.counter);
    }

    Map<String, CmcTicker> cmcTickerMap = super.getCmcLatestQuotes(baseSymbols, convertSymbols);
    return CmcAdapter.adaptTickerMap(cmcTickerMap);
  }

  public List<Ticker> getAllTickers() throws IOException {

    List<CmcTicker> cmcTickerList = new ArrayList<>();
    try {
      cmcTickerList = super.getCmcLatestDataForAllCurrencies();
    } catch (HttpStatusIOException ex) {
      CmcErrorAdapter.adapt(ex);
    }

    return CmcAdapter.adaptTickerList(cmcTickerList);
  }

  @Override
  public OrderBook getOrderBook(CurrencyPair currencyPair, Object... objects) {
    throw new NotAvailableFromExchangeException();
  }

  @Override
  public Trades getTrades(CurrencyPair currencyPair, Object... objects) {
    throw new NotAvailableFromExchangeException();
  }
}
