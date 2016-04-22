package org.knowm.xchange.btccentral.service.polling;

import java.io.IOException;

import org.knowm.xchange.Exchange;
import org.knowm.xchange.btccentral.BTCCentralAdapters;
import org.knowm.xchange.currency.CurrencyPair;
import org.knowm.xchange.dto.marketdata.OrderBook;
import org.knowm.xchange.dto.marketdata.Ticker;
import org.knowm.xchange.dto.marketdata.Trades;
import org.knowm.xchange.service.polling.marketdata.PollingMarketDataService;

/**
 * @author kpysniak
 */
public class BTCCentralMarketDataService extends BTCCentralMarketDataServiceRaw implements PollingMarketDataService {

  /**
   * Constructor
   *
   * @param exchange
   */
  public BTCCentralMarketDataService(Exchange exchange) {

    super(exchange);
  }

  @Override
  public Ticker getTicker(CurrencyPair currencyPair, Object... args) throws IOException {

    return BTCCentralAdapters.adaptTicker(getBTCCentralTicker(), currencyPair);
  }

  @Override
  public OrderBook getOrderBook(CurrencyPair currencyPair, Object... args) throws IOException {

    return BTCCentralAdapters.adaptMarketDepth(getBTCCentralMarketDepth(), currencyPair);
  }

  @Override
  public Trades getTrades(CurrencyPair currencyPair, Object... args) throws IOException {

    return BTCCentralAdapters.adaptTrade(getBTCCentralTrades(), currencyPair);
  }

}
