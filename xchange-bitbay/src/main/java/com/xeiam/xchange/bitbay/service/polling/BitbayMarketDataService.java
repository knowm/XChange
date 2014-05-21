package com.xeiam.xchange.bitbay.service.polling;

import com.xeiam.xchange.*;
import com.xeiam.xchange.bitbay.BitbayAdapters;
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
public class BitbayMarketDataService extends BitbayMarketDataServiceRaw implements PollingMarketDataService {

  /**
   * Constructor Initialize common properties from the exchange specification
   *
   * @param exchangeSpecification The {@link com.xeiam.xchange.ExchangeSpecification}
   */
  public BitbayMarketDataService(ExchangeSpecification exchangeSpecification) {
    super(exchangeSpecification);
  }

  @Override
  public Ticker getTicker(CurrencyPair currencyPair, Object... args) throws ExchangeException, NotAvailableFromExchangeException, NotYetImplementedForExchangeException, IOException {
    verify(currencyPair);

    return BitbayAdapters.adaptTicker(getBitbayTicker(currencyPair), currencyPair);
  }

  @Override
  public OrderBook getOrderBook(CurrencyPair currencyPair, Object... args) throws ExchangeException, NotAvailableFromExchangeException, NotYetImplementedForExchangeException, IOException {
    verify(currencyPair);

    return BitbayAdapters.adaptOrderBook(getBitbayOrderBook(currencyPair), currencyPair);
  }

  @Override
  public Trades getTrades(CurrencyPair currencyPair, Object... args) throws ExchangeException, NotAvailableFromExchangeException, NotYetImplementedForExchangeException, IOException {
    verify(currencyPair);

    return BitbayAdapters.adaptTrades(getBitbayTrades(currencyPair), currencyPair);
  }

  @Override
  public ExchangeInfo getExchangeInfo() throws ExchangeException, IOException, NotAvailableFromExchangeException, NotYetImplementedForExchangeException {
    throw new NotYetImplementedForExchangeException();
  }
}
