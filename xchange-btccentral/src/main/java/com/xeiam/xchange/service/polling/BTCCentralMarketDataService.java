package com.xeiam.xchange.service.polling;

import com.xeiam.xchange.*;
import com.xeiam.xchange.currency.CurrencyPair;
import com.xeiam.xchange.dto.ExchangeInfo;
import com.xeiam.xchange.dto.marketdata.OrderBook;
import com.xeiam.xchange.dto.marketdata.Ticker;
import com.xeiam.xchange.dto.marketdata.Trades;

import java.io.IOException;

/**
 * @author kpysniak
 */
public class BTCCentralMarketDataService extends BTCCentralMarketDataServiceRaw implements PollingMarketDataService {

  /**
   * Constructor Initialize common properties from the exchange specification
   *
   * @param exchangeSpecification The {@link com.xeiam.xchange.ExchangeSpecification}
   */
  public BTCCentralMarketDataService(ExchangeSpecification exchangeSpecification) {
    super(exchangeSpecification);
  }

  @Override
  public Ticker getTicker(CurrencyPair currencyPair, Object... args) throws ExchangeException, NotAvailableFromExchangeException, NotYetImplementedForExchangeException, IOException {
    verify(currencyPair);

    return BTCCentralAdapters.adaptTicker(getTicker(), currencyPair);
  }

  @Override
  public OrderBook getOrderBook(CurrencyPair currencyPair, Object... args) throws ExchangeException, NotAvailableFromExchangeException, NotYetImplementedForExchangeException, IOException {
    throw new NotYetImplementedForExchangeException();
  }

  @Override
  public Trades getTrades(CurrencyPair currencyPair, Object... args) throws ExchangeException, NotAvailableFromExchangeException, NotYetImplementedForExchangeException, IOException {
    throw new NotYetImplementedForExchangeException();
  }

  @Override
  public ExchangeInfo getExchangeInfo() throws ExchangeException, IOException, NotAvailableFromExchangeException, NotYetImplementedForExchangeException {
    throw new NotYetImplementedForExchangeException();
  }
}
