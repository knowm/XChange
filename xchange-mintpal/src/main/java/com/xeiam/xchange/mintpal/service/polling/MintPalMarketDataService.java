package com.xeiam.xchange.mintpal.service.polling;

import java.io.IOException;

import com.xeiam.xchange.ExchangeException;
import com.xeiam.xchange.ExchangeSpecification;
import com.xeiam.xchange.currency.CurrencyPair;
import com.xeiam.xchange.dto.marketdata.OrderBook;
import com.xeiam.xchange.dto.marketdata.Ticker;
import com.xeiam.xchange.dto.marketdata.Trades;
import com.xeiam.xchange.mintpal.MintPalAdapters;
import com.xeiam.xchange.service.polling.PollingMarketDataService;

/**
 * @author jamespedwards42
 */
public class MintPalMarketDataService extends MintPalMarketDataServiceRaw implements PollingMarketDataService {

  public MintPalMarketDataService(ExchangeSpecification exchangeSpecification) {

    super(exchangeSpecification);
  }

  @Override
  public Ticker getTicker(final CurrencyPair currencyPair, final Object... args) throws ExchangeException, IOException {

    return MintPalAdapters.adaptTicker(super.getMintPalTicker(currencyPair));
  }

  @Override
  public OrderBook getOrderBook(final CurrencyPair currencyPair, final Object... args) throws ExchangeException, IOException {

    return MintPalAdapters.adaptOrderBook(currencyPair, super.getMintPalOrders(currencyPair));
  }

  @Override
  public Trades getTrades(final CurrencyPair currencyPair, final Object... args) throws ExchangeException, IOException {

    return MintPalAdapters.adaptPublicTrades(currencyPair, super.getMintPalTrades(currencyPair));
  }

}
