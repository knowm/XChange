package com.xeiam.xchange.lakebtc.service.polling;

import com.xeiam.xchange.*;
import com.xeiam.xchange.currency.CurrencyPair;
import com.xeiam.xchange.dto.ExchangeInfo;
import com.xeiam.xchange.dto.marketdata.*;
import com.xeiam.xchange.lakebtc.LakeBTCAdapters;
import com.xeiam.xchange.lakebtc.dto.marketdata.LakeBTCOrderBook;
import com.xeiam.xchange.lakebtc.dto.marketdata.LakeBTCTicker;
import com.xeiam.xchange.lakebtc.dto.marketdata.LakeBTCTickers;
import com.xeiam.xchange.service.polling.PollingMarketDataService;

import java.io.IOException;

/**
 * @author kpysniak
 */
public class LakeBTCMarketDataService extends LakeBTCMarketDataServiceRaw implements PollingMarketDataService {

  /**
   * Constructor Initialize common properties from the exchange specification
   *
   * @param exchangeSpecification The {@link com.xeiam.xchange.ExchangeSpecification}
   */
  public LakeBTCMarketDataService(ExchangeSpecification exchangeSpecification) {
    super(exchangeSpecification);
  }

  @Override
  public Ticker getTicker(CurrencyPair currencyPair, Object... args) throws ExchangeException, NotAvailableFromExchangeException, NotYetImplementedForExchangeException, IOException {
    verify(currencyPair);

    LakeBTCTicker lakeBTCTicker;
    LakeBTCTickers lakeBTCTickers = getLakeBTCTickers();

    if (CurrencyPair.BTC_USD.equals(currencyPair)) {
      lakeBTCTicker = lakeBTCTickers.getUsd();
    } else if (CurrencyPair.BTC_CNY.equals(currencyPair)) {
      lakeBTCTicker = lakeBTCTickers.getCny();
    } else {
      throw new NotAvailableFromExchangeException();
    }

    return LakeBTCAdapters.adaptTicker(lakeBTCTicker, currencyPair);
  }

  @Override
  public OrderBook getOrderBook(CurrencyPair currencyPair, Object... args) throws ExchangeException, NotAvailableFromExchangeException, NotYetImplementedForExchangeException, IOException {
    verify(currencyPair);

    LakeBTCOrderBook lakeBTCOrderBook;

    if (CurrencyPair.BTC_USD.equals(currencyPair)) {
      lakeBTCOrderBook = getLakeBTCOrderBookUSD();
    } else if (CurrencyPair.BTC_CNY.equals(currencyPair)) {
      lakeBTCOrderBook = getLakeBTCOrderBookCNY();
    } else {
      throw new NotAvailableFromExchangeException();
    }

    return LakeBTCAdapters.adaptOrderBook(lakeBTCOrderBook, currencyPair);
  }

  @Override
  public Trades getTrades(CurrencyPair currencyPair, Object... args) throws ExchangeException, NotAvailableFromExchangeException, NotYetImplementedForExchangeException, IOException {
    throw new NotAvailableFromExchangeException();
  }

  @Override
  public ExchangeInfo getExchangeInfo() throws ExchangeException, IOException, NotAvailableFromExchangeException, NotYetImplementedForExchangeException {
    throw new NotAvailableFromExchangeException();
  }
}
