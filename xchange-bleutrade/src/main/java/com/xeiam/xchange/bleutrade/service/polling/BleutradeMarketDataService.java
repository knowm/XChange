package com.xeiam.xchange.bleutrade.service.polling;

import java.io.IOException;
import java.util.List;

import com.xeiam.xchange.ExchangeException;
import com.xeiam.xchange.ExchangeSpecification;
import com.xeiam.xchange.NotAvailableFromExchangeException;
import com.xeiam.xchange.NotYetImplementedForExchangeException;
import com.xeiam.xchange.bleutrade.BleutradeAdapters;
import com.xeiam.xchange.bleutrade.dto.marketdata.BleutradeOrderBook;
import com.xeiam.xchange.bleutrade.dto.marketdata.BleutradeTicker;
import com.xeiam.xchange.bleutrade.dto.marketdata.BleutradeTrade;
import com.xeiam.xchange.currency.CurrencyPair;
import com.xeiam.xchange.dto.marketdata.OrderBook;
import com.xeiam.xchange.dto.marketdata.Ticker;
import com.xeiam.xchange.dto.marketdata.Trades;
import com.xeiam.xchange.service.polling.PollingMarketDataService;

/**
 * <p>
 * Implementation of the market data service for Bittrex
 * </p>
 * <ul>
 * <li>Provides access to various market data values</li>
 * </ul>
 */
public class BleutradeMarketDataService extends BleutradeMarketDataServiceRaw implements PollingMarketDataService {

  /**
   * Constructor
   * 
   * @param exchangeSpecification The {@link ExchangeSpecification}
   */
  public BleutradeMarketDataService(ExchangeSpecification exchangeSpecification) {

    super(exchangeSpecification);
  }

  @Override
  public Ticker getTicker(CurrencyPair currencyPair, Object... args) throws ExchangeException, NotAvailableFromExchangeException, NotYetImplementedForExchangeException, IOException {

    BleutradeTicker bleutradeTicker = getBleutradeTicker(currencyPair);
    return BleutradeAdapters.adaptBleutradeTicker(bleutradeTicker);
  }

  @Override
  public OrderBook getOrderBook(CurrencyPair currencyPair, Object... args) throws ExchangeException, NotAvailableFromExchangeException, NotYetImplementedForExchangeException, IOException {

    int depth = 50;

    if (args.length > 0) {
      if (args[0] instanceof Integer) {
        depth = (Integer) args[0];
      }
    }

    BleutradeOrderBook bleutradeOrderBook = getBleutradeOrderBook(currencyPair, depth);
    return BleutradeAdapters.adaptBleutradeOrderBook(bleutradeOrderBook, currencyPair);
  }

  @Override
  public Trades getTrades(CurrencyPair currencyPair, Object... args) throws ExchangeException, NotAvailableFromExchangeException, NotYetImplementedForExchangeException, IOException {

    int count = 50;

    if (args.length > 0) {
      if (args[0] instanceof Integer) {
        count = (Integer) args[0];
      }
    }

    if (count < 1) {
      count = 1;
    }
    else if (count > 200) {
      count = 200;
    }

    List<BleutradeTrade> bleutradeTrades = getBleutradeMarketHistory(currencyPair, count);
    return BleutradeAdapters.adaptBleutradeMarketHistory(bleutradeTrades, currencyPair);
  }

}
