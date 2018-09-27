package org.knowm.xchange.coindirect.service;

import java.io.IOException;
import java.util.List;
import org.knowm.xchange.Exchange;
import org.knowm.xchange.coindirect.CoindirectAdapters;
import org.knowm.xchange.coindirect.dto.marketdata.CoindirectMarket;
import org.knowm.xchange.coindirect.dto.marketdata.CoindirectOrderbook;
import org.knowm.xchange.coindirect.dto.marketdata.CoindirectTicker;
import org.knowm.xchange.coindirect.dto.marketdata.CoindirectTrades;
import org.knowm.xchange.currency.CurrencyPair;

public class CoindirectMarketDataServiceRaw extends CoindirectBaseService {
  /**
   * Constructor
   *
   * @param exchange
   */
  public CoindirectMarketDataServiceRaw(Exchange exchange) {
    super(exchange);
  }

  public CoindirectOrderbook getCoindirectOrderbook(CurrencyPair pair) throws IOException {
    return coindirect.getExchangeOrderBook(CoindirectAdapters.toSymbol(pair));
  }

  public CoindirectTrades getCoindirectTrades(CurrencyPair pair, String history)
      throws IOException {
    return coindirect.getHistoricalExchangeTrades(CoindirectAdapters.toSymbol(pair), history);
  }

  public CoindirectTicker getCoindirectTicker(CurrencyPair pair, String history, String grouping)
      throws IOException {
    return coindirect.getHistoricalExchangeData(
        CoindirectAdapters.toSymbol(pair), history, grouping);
  }

  public List<CoindirectMarket> getCoindirectMarkets(long max) throws IOException {
    return coindirect.listExchangeMarkets(max);
  }
}
