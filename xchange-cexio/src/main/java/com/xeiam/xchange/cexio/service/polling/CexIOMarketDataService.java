package com.xeiam.xchange.cexio.service.polling;

import java.io.IOException;

import com.xeiam.xchange.ExchangeException;
import com.xeiam.xchange.ExchangeSpecification;
import com.xeiam.xchange.NotAvailableFromExchangeException;
import com.xeiam.xchange.NotYetImplementedForExchangeException;
import com.xeiam.xchange.cexio.CexIOAdapters;
import com.xeiam.xchange.cexio.dto.marketdata.CexIOTrade;
import com.xeiam.xchange.currency.CurrencyPair;
import com.xeiam.xchange.dto.marketdata.OrderBook;
import com.xeiam.xchange.dto.marketdata.Ticker;
import com.xeiam.xchange.dto.marketdata.Trades;
import com.xeiam.xchange.service.polling.PollingMarketDataService;

/**
 * Author: brox
 * Since: 2/6/14
 */

public class CexIOMarketDataService extends CexIOMarketDataServiceRaw implements PollingMarketDataService {

  /**
   * Initialize common properties from the exchange specification
   * 
   * @param exchangeSpecification The {@link com.xeiam.xchange.ExchangeSpecification}
   */
  public CexIOMarketDataService(ExchangeSpecification exchangeSpecification) {

    super(exchangeSpecification);
  }

  @Override
  public Ticker getTicker(CurrencyPair currencyPair, Object... args) throws ExchangeException, NotAvailableFromExchangeException, NotYetImplementedForExchangeException, IOException {

    return CexIOAdapters.adaptTicker(getCexIOTicker(currencyPair), currencyPair);
  }

  @Override
  public OrderBook getOrderBook(CurrencyPair currencyPair, Object... args) throws ExchangeException, NotAvailableFromExchangeException, NotYetImplementedForExchangeException, IOException {

    return CexIOAdapters.adaptOrderBook(getCexIOOrderBook(currencyPair), currencyPair);
  }

  @Override
  public Trades getTrades(CurrencyPair currencyPair, Object... args) throws ExchangeException, NotAvailableFromExchangeException, NotYetImplementedForExchangeException, IOException {

    CexIOTrade[] trades;

    if (args.length > 0) {
      Object arg0 = args[0];
      if (!(arg0 instanceof Number)) {
        throw new ExchangeException("arg[0] must be a Number used to represent since trade id.");
      }
      else {
        trades = getCexIOTrades(currencyPair, ((Number) arg0).longValue());
      }
    }
    else { // default to full available trade history
      trades = getCexIOTrades(currencyPair, null);
    }

    return CexIOAdapters.adaptTrades(trades, currencyPair);
  }

}
