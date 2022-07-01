package org.knowm.xchange.dvchain.service;

import java.io.IOException;
import org.knowm.xchange.Exchange;
import org.knowm.xchange.currency.CurrencyPair;
import org.knowm.xchange.dto.marketdata.OrderBook;
import org.knowm.xchange.dvchain.DVChainAdapters;
import org.knowm.xchange.dvchain.dto.marketdata.DVChainMarketData;
import org.knowm.xchange.dvchain.dto.marketdata.DVChainMarketResponse;
import org.knowm.xchange.exceptions.RateLimitExceededException;
import org.knowm.xchange.service.marketdata.MarketDataService;

public class DVChainMarketDataService extends DVChainMarketDataServiceRaw
    implements MarketDataService {
  public DVChainMarketDataService(Exchange exchange) {
    super(exchange);
  }

  @Override
  public OrderBook getOrderBook(CurrencyPair currencyPair, Object... args)
      throws IOException, RateLimitExceededException {
    DVChainMarketResponse marketResponse = getMarketData();
    DVChainMarketData marketData =
        marketResponse.getMarketData().get(currencyPair.base.getSymbol());
    return DVChainAdapters.adaptOrderBook(marketData, marketData.getExpiresAt(), currencyPair);
  }
}
