package org.knowm.xchange.paymium.service;

import java.io.IOException;
import org.knowm.xchange.Exchange;
import org.knowm.xchange.currency.CurrencyPair;
import org.knowm.xchange.dto.marketdata.OrderBook;
import org.knowm.xchange.dto.marketdata.Ticker;
import org.knowm.xchange.dto.marketdata.Trades;
import org.knowm.xchange.paymium.PaymiumAdapters;
import org.knowm.xchange.service.marketdata.MarketDataService;

public class PaymiumMarketDataService extends PaymiumMarketDataServiceRaw
    implements MarketDataService {

  /**
   * Constructor
   *
   * @param exchange
   */
  public PaymiumMarketDataService(Exchange exchange) {

    super(exchange);
  }

  @Override
  public Ticker getTicker(CurrencyPair currencyPair, Object... args) throws IOException {
    return PaymiumAdapters.adaptTicker(getPaymiumTicker(), currencyPair);
  }

  @Override
  public OrderBook getOrderBook(CurrencyPair currencyPair, Object... args) throws IOException {
    return PaymiumAdapters.adaptMarketDepth(getPaymiumMarketDepth(), currencyPair);
  }

  @Override
  public Trades getTrades(CurrencyPair currencyPair, Object... args) throws IOException {
    return PaymiumAdapters.adaptTrade(getPaymiumTrades(), currencyPair);
  }
}
