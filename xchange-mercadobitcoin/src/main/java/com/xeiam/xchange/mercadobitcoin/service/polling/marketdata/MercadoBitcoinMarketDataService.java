package com.xeiam.xchange.mercadobitcoin.service.polling.marketdata;

import java.io.IOException;

import com.xeiam.xchange.ExchangeSpecification;
import com.xeiam.xchange.currency.CurrencyPair;
import com.xeiam.xchange.dto.marketdata.OrderBook;
import com.xeiam.xchange.dto.marketdata.Ticker;
import com.xeiam.xchange.dto.marketdata.Trades;
import com.xeiam.xchange.mercadobitcoin.MercadoBitcoinAdapters;
import com.xeiam.xchange.service.polling.marketdata.PollingMarketDataService;

/**
 * @author Matija Mazi
 * @author Felipe Micaroni Lalli
 */
public class MercadoBitcoinMarketDataService extends MercadoBitcoinMarketDataServiceRaw implements PollingMarketDataService {

  /**
   * Constructor
   *
   * @param exchangeSpecification The
   *          {@link com.xeiam.xchange.ExchangeSpecification}
   */
  public MercadoBitcoinMarketDataService(ExchangeSpecification exchangeSpecification) {

    super(exchangeSpecification);

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
