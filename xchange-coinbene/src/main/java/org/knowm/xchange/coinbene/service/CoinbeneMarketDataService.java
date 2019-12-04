package org.knowm.xchange.coinbene.service;

import java.io.IOException;
import java.util.List;
import java.util.stream.Collectors;
import org.knowm.xchange.Exchange;
import org.knowm.xchange.coinbene.dto.CoinbeneAdapters;
import org.knowm.xchange.coinbene.dto.marketdata.CoinbeneSymbol;
import org.knowm.xchange.currency.CurrencyPair;
import org.knowm.xchange.dto.marketdata.OrderBook;
import org.knowm.xchange.dto.marketdata.Ticker;
import org.knowm.xchange.dto.marketdata.Trade;
import org.knowm.xchange.dto.marketdata.Trades;
import org.knowm.xchange.dto.meta.ExchangeMetaData;
import org.knowm.xchange.service.marketdata.MarketDataService;
import org.knowm.xchange.service.marketdata.params.Params;

public class CoinbeneMarketDataService extends CoinbeneMarketDataServiceRaw
    implements MarketDataService {

  /**
   * Constructor
   *
   * @param exchange
   */
  public CoinbeneMarketDataService(Exchange exchange) {

    super(exchange);
  }

  @Override
  public Ticker getTicker(CurrencyPair currencyPair, Object... args) throws IOException {
    return CoinbeneAdapters.adaptTicker(getCoinbeneTicker(currencyPair));
  }

  @Override
  public List<Ticker> getTickers(Params params) throws IOException {
    return CoinbeneAdapters.adaptTickers(getCoinbeneTickers());
  }

  @Override
  public OrderBook getOrderBook(CurrencyPair currencyPair, Object... args) throws IOException {

    Integer limit = null;

    if (args != null && args.length > 0) {
      if (args[0] instanceof Integer && (Integer) args[0] > 0) {
        limit = (Integer) args[0];
      }
    }
    return CoinbeneAdapters.adaptOrderBook(getCoinbeneOrderBook(currencyPair, limit), currencyPair);
  }

  @Override
  public Trades getTrades(CurrencyPair currencyPair, Object... args) throws IOException {

    Integer limit = null;
    if (args != null && args.length > 0) {
      if (args[0] instanceof Integer && (Integer) args[0] > 0) {
        limit = (Integer) args[0];
      }
    }

    List<Trade> trades =
        getCoinbeneTrades(currencyPair, limit).getTrades().stream()
            .map(trade -> CoinbeneAdapters.adaptTrade(trade, currencyPair))
            .collect(Collectors.toList());
    return new Trades(trades, Trades.TradeSortType.SortByTimestamp);
  }

  public ExchangeMetaData getMetadata() throws IOException {

    List<CoinbeneSymbol> symbol = getSymbol().getSymbol();
    return CoinbeneAdapters.adaptMetadata(symbol);
  }
}
