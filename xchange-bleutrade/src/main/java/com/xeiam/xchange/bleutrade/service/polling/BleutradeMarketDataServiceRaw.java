package com.xeiam.xchange.bleutrade.service.polling;

import java.io.IOException;
import java.util.List;

import com.xeiam.xchange.Exchange;
import com.xeiam.xchange.bleutrade.BleutradeUtils;
import com.xeiam.xchange.bleutrade.dto.marketdata.*;
import com.xeiam.xchange.currency.CurrencyPair;
import com.xeiam.xchange.exceptions.ExchangeException;

/**
 * <p>
 * Implementation of the market data service for Bleutrade
 * </p>
 * <ul>
 * <li>Provides access to various market data values</li>
 * </ul>
 */
public class BleutradeMarketDataServiceRaw extends BleutradeBasePollingService {

  /**
   * Constructor
   *
   * @param exchange
   */
  public BleutradeMarketDataServiceRaw(Exchange exchange) {

    super(exchange);
  }

  public List<BleutradeTicker> getBleutradeTickers() throws IOException {

    BleutradeTickerReturn response = bleutrade.getBleutradeTickers();

    if (!response.getSuccess()) {
      throw new ExchangeException(response.getMessage());
    }

    return response.getResult();
  }

  public BleutradeTicker getBleutradeTicker(CurrencyPair currencyPair) throws IOException {

    String pairString = BleutradeUtils.toPairString(currencyPair);
    BleutradeTickerReturn response = bleutrade.getBleutradeTicker(pairString);

    if (!response.getSuccess()) {
      throw new ExchangeException(response.getMessage());
    }

    return response.getResult().get(0);
  }

  public BleutradeOrderBook getBleutradeOrderBook(CurrencyPair currencyPair, int depth) throws IOException {

    String pairString = BleutradeUtils.toPairString(currencyPair);

    BleutradeOrderBookReturn response = bleutrade.getBleutradeOrderBook(pairString, "ALL", depth);

    if (!response.getSuccess()) {
      throw new ExchangeException(response.getMessage());
    }

    return response.getResult();

  }

  public List<BleutradeCurrency> getBleutradeCurrencies() throws IOException {

    BleutradeCurrenciesReturn response = bleutrade.getBleutradeCurrencies();

    if (!response.getSuccess()) {
      throw new ExchangeException(response.getMessage());
    }

    return response.getResult();
  }

  public List<BleutradeMarket> getBleutradeMarkets() throws IOException {

    BleutradeMarketsReturn response = bleutrade.getBleutradeMarkets();

    if (!response.getSuccess()) {
      throw new ExchangeException(response.getMessage());
    }

    return response.getResult();

  }

  public List<BleutradeTrade> getBleutradeMarketHistory(CurrencyPair currencyPair, int count) throws IOException {

    String pairString = BleutradeUtils.toPairString(currencyPair);

    BleutradeMarketHistoryReturn response = bleutrade.getBleutradeMarketHistory(pairString, count);

    if (!response.getSuccess()) {
      throw new ExchangeException(response.getMessage());
    }

    return response.getResult();
  }

}
