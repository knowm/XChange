package com.xeiam.xchange.bitmarket.service.polling;

import com.xeiam.xchange.*;
import com.xeiam.xchange.bitmarket.BitMarketAdapters;
import com.xeiam.xchange.currency.CurrencyPair;
import com.xeiam.xchange.dto.ExchangeInfo;
import com.xeiam.xchange.dto.marketdata.OrderBook;
import com.xeiam.xchange.dto.marketdata.Ticker;
import com.xeiam.xchange.dto.marketdata.Trades;
import com.xeiam.xchange.service.polling.PollingMarketDataService;

import java.io.IOException;

/**
 * @author kpysniak
 */
public class BitMarketDataService extends BitMarketDataServiceRaw implements PollingMarketDataService {

  /**
   * Constructor Initialize common properties from the exchange specification
   *
   * @param exchangeSpecification The {@link com.xeiam.xchange.ExchangeSpecification}
   */
  public BitMarketDataService(ExchangeSpecification exchangeSpecification) {
    super(exchangeSpecification);
  }

  @Override
  public Ticker getTicker(CurrencyPair currencyPair, Object... args) throws ExchangeException, NotAvailableFromExchangeException, NotYetImplementedForExchangeException, IOException {
    verify(currencyPair);

    return BitMarketAdapters.adaptTicker(getBitMarketTicker(currencyPair), currencyPair);
  }

  @Override
  public OrderBook getOrderBook(CurrencyPair currencyPair, Object... args) throws ExchangeException, NotAvailableFromExchangeException, NotYetImplementedForExchangeException, IOException {
    verify(currencyPair);

    return BitMarketAdapters.adaptOrderBook(getBitMarketOrderBook(currencyPair), currencyPair);
  }

  @Override
  public Trades getTrades(CurrencyPair currencyPair, Object... args) throws ExchangeException, NotAvailableFromExchangeException, NotYetImplementedForExchangeException, IOException {
    verify(currencyPair);

    return BitMarketAdapters.adaptTrades(getBitMarketTrades(currencyPair), currencyPair);
  }

  @Override
  public ExchangeInfo getExchangeInfo() throws ExchangeException, IOException, NotAvailableFromExchangeException, NotYetImplementedForExchangeException {
    throw new NotYetImplementedForExchangeException();
  }
}
