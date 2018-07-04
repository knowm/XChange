package org.knowm.xchange.cobinhood.service;

import java.io.IOException;
import java.sql.Date;
import java.util.List;
import java.util.stream.Collectors;
import org.knowm.xchange.Exchange;
import org.knowm.xchange.cobinhood.dto.CobinhoodAdapters;
import org.knowm.xchange.cobinhood.dto.CobinhoodResponse;
import org.knowm.xchange.cobinhood.dto.marketdata.CobinhoodTrades;
import org.knowm.xchange.currency.CurrencyPair;
import org.knowm.xchange.dto.marketdata.OrderBook;
import org.knowm.xchange.dto.marketdata.Ticker;
import org.knowm.xchange.dto.marketdata.Trade;
import org.knowm.xchange.dto.marketdata.Trades;
import org.knowm.xchange.service.marketdata.MarketDataService;
import org.knowm.xchange.service.marketdata.params.Params;

public class CobinhoodMarketDataService extends CobinhoodMarketDataServiceRaw
    implements MarketDataService {

  /**
   * Constructor
   *
   * @param exchange
   */
  public CobinhoodMarketDataService(Exchange exchange) {

    super(exchange);
  }

  @Override
  public Ticker getTicker(CurrencyPair currencyPair, Object... args) throws IOException {
    return CobinhoodAdapters.adaptTicker(getCobinhoodTicker(currencyPair).getResult().getTicker());
  }

  @Override
  public List<Ticker> getTickers(Params params) throws IOException {
    return getCobinhoodTickers()
        .getResult()
        .getTickers()
        .stream()
        .map(CobinhoodAdapters::adaptTicker)
        .collect(Collectors.toList());
  }

  @Override
  public OrderBook getOrderBook(CurrencyPair currencyPair, Object... args) throws IOException {

    Integer limit = null;

    if (args != null && args.length > 0) {
      if (args[0] instanceof Integer && (Integer) args[0] > 0) {
        limit = (Integer) args[0];
      }
    }
    return CobinhoodAdapters.adaptOrderBook(
        getCobinhoodOrderBook(currencyPair, limit), currencyPair);
  }

  @Override
  public Trades getTrades(CurrencyPair currencyPair, Object... args) throws IOException {

    Integer limit = null;
    Long since = null;

    if (args != null && args.length > 0) {
      if (args[0] instanceof Integer && (Integer) args[0] > 0) {
        limit = (Integer) args[0];
      }
      if (args.length > 1) {
        if (args[1] instanceof Date) {
          since = ((Date) args[1]).getTime();
        }
      }
    }

    CobinhoodResponse<CobinhoodTrades> response = getCobinhoodTrades(currencyPair, limit);
    List<Trade> trades =
        response
            .getResult()
            .getTrades()
            .stream()
            .map(trade -> CobinhoodAdapters.adaptTrade(trade, currencyPair))
            .collect(Collectors.toList());
    return new Trades(trades, Trades.TradeSortType.SortByTimestamp);
  }
}
