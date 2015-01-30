package com.xeiam.xchange.lakebtc.service.polling;

import java.io.IOException;

import com.xeiam.xchange.Exchange;
import com.xeiam.xchange.currency.CurrencyPair;
import com.xeiam.xchange.dto.marketdata.OrderBook;
import com.xeiam.xchange.dto.marketdata.Ticker;
import com.xeiam.xchange.dto.marketdata.Trades;
import com.xeiam.xchange.exceptions.NotAvailableFromExchangeException;
import com.xeiam.xchange.lakebtc.LakeBTCAdapters;
import com.xeiam.xchange.lakebtc.dto.marketdata.LakeBTCOrderBook;
import com.xeiam.xchange.lakebtc.dto.marketdata.LakeBTCTicker;
import com.xeiam.xchange.lakebtc.dto.marketdata.LakeBTCTickers;
import com.xeiam.xchange.service.polling.marketdata.PollingMarketDataService;

/**
 * @author kpysniak
 */
public class LakeBTCMarketDataService extends LakeBTCMarketDataServiceRaw implements PollingMarketDataService {

  /**
   * Constructor
   *
   * @param exchange
   */
  public LakeBTCMarketDataService(Exchange exchange) {

    super(exchange);
  }

  @Override
  public Ticker getTicker(CurrencyPair currencyPair, Object... args) throws IOException {

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
  public OrderBook getOrderBook(CurrencyPair currencyPair, Object... args) throws IOException {

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
  public Trades getTrades(CurrencyPair currencyPair, Object... args) throws IOException {

    throw new NotAvailableFromExchangeException();
  }

}
