package org.knowm.xchange.cexio.service;

import java.io.IOException;
import java.util.Collection;
import java.util.List;
import java.util.stream.Collectors;
import org.knowm.xchange.Exchange;
import org.knowm.xchange.cexio.CexIOAdapters;
import org.knowm.xchange.cexio.dto.marketdata.CexIODepth;
import org.knowm.xchange.cexio.dto.marketdata.CexIOTrade;
import org.knowm.xchange.currency.CurrencyPair;
import org.knowm.xchange.dto.marketdata.OrderBook;
import org.knowm.xchange.dto.marketdata.Ticker;
import org.knowm.xchange.dto.marketdata.Trades;
import org.knowm.xchange.exceptions.ExchangeException;
import org.knowm.xchange.service.marketdata.MarketDataService;
import org.knowm.xchange.service.marketdata.params.CurrencyPairsParam;
import org.knowm.xchange.service.marketdata.params.Params;

/** Author: brox Since: 2/6/14 */
public class CexIOMarketDataService extends CexIOMarketDataServiceRaw implements MarketDataService {

  /**
   * Constructor
   *
   * @param exchange
   */
  public CexIOMarketDataService(Exchange exchange) {

    super(exchange);
  }

  @Override
  public List<Ticker> getTickers(Params params) throws IOException {
    if (!(params instanceof CurrencyPairsParam)) {
      throw new IllegalArgumentException("Params must be instance of CurrencyPairsParam");
    }
    Collection<CurrencyPair> pairs = ((CurrencyPairsParam) params).getCurrencyPairs();
    return getAllCexIOTickers()
        .stream()
        .map(CexIOAdapters::adaptTicker)
        .filter(ticker -> pairs.contains(ticker.getCurrencyPair()))
        .collect(Collectors.toList());
  }

  @Override
  public Ticker getTicker(CurrencyPair currencyPair, Object... args) throws IOException {

    return CexIOAdapters.adaptTicker(getCexIOTicker(currencyPair), currencyPair);
  }

  @Override
  public OrderBook getOrderBook(CurrencyPair currencyPair, Object... args) throws IOException {

    CexIODepth cexIODepth = getCexIOOrderBook(currencyPair);

    if (cexIODepth.getError() != null) {
      // eg: 'Rate limit exceeded'
      throw new ExchangeException(
          "CexIO getOrderBook request for "
              + currencyPair
              + " failed with: "
              + cexIODepth.getError());
    }

    return CexIOAdapters.adaptOrderBook(cexIODepth, currencyPair);
  }

  @Override
  public Trades getTrades(CurrencyPair currencyPair, Object... args) throws IOException {

    CexIOTrade[] trades;

    if (args != null && args.length > 0) {
      Object arg0 = args[0];
      if (!(arg0 instanceof Number)) {
        throw new ExchangeException("arg[0] must be a Number used to represent since trade id.");
      } else {
        trades = getCexIOTrades(currencyPair, ((Number) arg0).longValue());
      }
    } else { // default to full available trade history
      trades = getCexIOTrades(currencyPair, null);
    }

    return CexIOAdapters.adaptTrades(trades, currencyPair);
  }
}
