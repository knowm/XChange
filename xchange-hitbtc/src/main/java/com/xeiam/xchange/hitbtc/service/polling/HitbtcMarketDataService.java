package com.xeiam.xchange.hitbtc.service.polling;

import java.io.IOException;
import java.util.HashSet;
import java.util.Set;

import com.xeiam.xchange.ExchangeException;
import com.xeiam.xchange.ExchangeSpecification;
import com.xeiam.xchange.NotAvailableFromExchangeException;
import com.xeiam.xchange.NotYetImplementedForExchangeException;
import com.xeiam.xchange.currency.CurrencyPair;
import com.xeiam.xchange.dto.marketdata.OrderBook;
import com.xeiam.xchange.dto.marketdata.Ticker;
import com.xeiam.xchange.dto.marketdata.Trades;
import com.xeiam.xchange.hitbtc.HitbtcAdapters;
import com.xeiam.xchange.hitbtc.dto.marketdata.HitbtcTrades.HitbtcTradesSortOrder;
import com.xeiam.xchange.service.polling.PollingMarketDataService;

/**
 * @author kpysniak
 */
public class HitbtcMarketDataService extends HitbtcMarketDataServiceRaw implements PollingMarketDataService {

  private final Set<CurrencyPair> currencyPairs = new HashSet<CurrencyPair>();

  /**
   * Constructor Initialize common properties from the exchange specification
   * 
   * @param exchangeSpecification The {@link com.xeiam.xchange.ExchangeSpecification}
   */
  public HitbtcMarketDataService(ExchangeSpecification exchangeSpecification) {

    super(exchangeSpecification);
  }

  @Override
  public Ticker getTicker(CurrencyPair currencyPair, Object... args) throws ExchangeException, NotAvailableFromExchangeException, NotYetImplementedForExchangeException, IOException {

    verify(currencyPair);

    return HitbtcAdapters.adaptTicker(getHitbtcTicker(currencyPair), currencyPair);
  }

  @Override
  public OrderBook getOrderBook(CurrencyPair currencyPair, Object... args) throws ExchangeException, NotAvailableFromExchangeException, NotYetImplementedForExchangeException, IOException {

    verify(currencyPair);

    return HitbtcAdapters.adaptOrderBook(getHitbtcOrderBook(currencyPair), currencyPair);
  }

  @Override
  public Trades getTrades(CurrencyPair currencyPair, Object... args) throws ExchangeException, NotAvailableFromExchangeException, NotYetImplementedForExchangeException, IOException {

    verify(currencyPair);

    long from = (Long) args[0];
    HitbtcTradesSortOrder sortBy = (HitbtcTradesSortOrder) args[1];
    long startIndex = (Long) args[2];
    long max_results = (Long) args[3];

    return HitbtcAdapters.adaptTrades(getHitbtcTrades(currencyPair, from, sortBy, startIndex, max_results), currencyPair);
  }

}
