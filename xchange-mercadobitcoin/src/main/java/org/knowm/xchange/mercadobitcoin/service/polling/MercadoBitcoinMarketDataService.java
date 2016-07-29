package org.knowm.xchange.mercadobitcoin.service.polling;

import java.io.IOException;

import org.knowm.xchange.Exchange;
import org.knowm.xchange.currency.CurrencyPair;
import org.knowm.xchange.dto.marketdata.OrderBook;
import org.knowm.xchange.dto.marketdata.Ticker;
import org.knowm.xchange.dto.marketdata.Trades;
import org.knowm.xchange.mercadobitcoin.MercadoBitcoinAdapters;
import org.knowm.xchange.service.polling.marketdata.PollingMarketDataService;

/**
 * @author Felipe Micaroni Lalli
 */
public class MercadoBitcoinMarketDataService extends MercadoBitcoinMarketDataServiceRaw implements PollingMarketDataService {

  /**
   * Constructor
   *
   * @param exchange
   */
  public MercadoBitcoinMarketDataService(Exchange exchange) {

    super(exchange);

  }

  @Override
  public Ticker getTicker(CurrencyPair currencyPair, Object... args) throws IOException {

    return MercadoBitcoinAdapters.adaptTicker(getMercadoBitcoinTicker(currencyPair), currencyPair);
  }

  @Override
  public OrderBook getOrderBook(CurrencyPair currencyPair, Object... args) throws IOException {

    return MercadoBitcoinAdapters.adaptOrderBook(getMercadoBitcoinOrderBook(currencyPair), currencyPair);
  }

  @Override
  public Trades getTrades(CurrencyPair currencyPair, Object... args) throws IOException {

    return MercadoBitcoinAdapters.adaptTrades(getMercadoBitcoinTransactions(currencyPair, args), currencyPair);
  }

}
