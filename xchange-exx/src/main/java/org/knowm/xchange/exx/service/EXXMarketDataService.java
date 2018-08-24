package org.knowm.xchange.exx.service;

import java.io.IOException;
import java.util.List;
import org.knowm.xchange.Exchange;
import org.knowm.xchange.currency.CurrencyPair;
import org.knowm.xchange.dto.marketdata.OrderBook;
import org.knowm.xchange.dto.marketdata.Ticker;
import org.knowm.xchange.dto.marketdata.Trades;
import org.knowm.xchange.exx.EXXAdapters;
import org.knowm.xchange.exx.dto.marketdata.EXXOrderbook;
import org.knowm.xchange.exx.dto.marketdata.EXXTicker;
import org.knowm.xchange.exx.dto.marketdata.EXXTickerResponse;
import org.knowm.xchange.exx.dto.marketdata.EXXTransaction;
import org.knowm.xchange.service.marketdata.MarketDataService;
import org.knowm.xchange.service.marketdata.params.Params;

public class EXXMarketDataService extends EXXMarketDataServiceRaw implements MarketDataService {
  public EXXMarketDataService(Exchange exchange) {
    super(exchange);
  }

  public List<EXXTicker> ticketAll() {
    return null;
  }

  @Override
  public Ticker getTicker(CurrencyPair currencyPair, Object... args) throws IOException {
    EXXTickerResponse exxTicker = getExxTicker(currencyPair);

    if (exxTicker != null && exxTicker.getTicker() != null) {
      return EXXAdapters.convertTicker(exxTicker);
    } else {
      return null;
    }
  }

  @Override
  public List<Ticker> getTickers(Params params) throws IOException {

    return EXXAdapters.convertTickerMap(getExxTickers());
  }

  /** */
  @Override
  public OrderBook getOrderBook(CurrencyPair currencyPair, Object... args) throws IOException {

    EXXOrderbook exxOrderbook = getExxOrderBook(currencyPair);

    return exxOrderbook != null ? EXXAdapters.adaptOrderBook(exxOrderbook, currencyPair) : null;
  }

  @Override
  public Trades getTrades(CurrencyPair currencyPair, Object... args) throws IOException {
    EXXTransaction[] exxTransactions = getTransactions(currencyPair);

    return EXXAdapters.adaptTrades(exxTransactions, currencyPair);
  }
}
