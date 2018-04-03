package org.knowm.xchange.bleutrade.service;

import java.io.IOException;
import java.util.List;
import org.knowm.xchange.Exchange;
import org.knowm.xchange.bleutrade.BleutradeUtils;
import org.knowm.xchange.bleutrade.dto.marketdata.BleutradeCurrenciesReturn;
import org.knowm.xchange.bleutrade.dto.marketdata.BleutradeCurrency;
import org.knowm.xchange.bleutrade.dto.marketdata.BleutradeMarket;
import org.knowm.xchange.bleutrade.dto.marketdata.BleutradeMarketHistoryReturn;
import org.knowm.xchange.bleutrade.dto.marketdata.BleutradeMarketsReturn;
import org.knowm.xchange.bleutrade.dto.marketdata.BleutradeOrderBook;
import org.knowm.xchange.bleutrade.dto.marketdata.BleutradeOrderBookReturn;
import org.knowm.xchange.bleutrade.dto.marketdata.BleutradeTicker;
import org.knowm.xchange.bleutrade.dto.marketdata.BleutradeTickerReturn;
import org.knowm.xchange.bleutrade.dto.marketdata.BleutradeTrade;
import org.knowm.xchange.currency.CurrencyPair;
import org.knowm.xchange.exceptions.ExchangeException;
import si.mazi.rescu.IRestProxyFactory;

/**
 * Implementation of the market data service for Bleutrade
 *
 * <ul>
 *   <li>Provides access to various market data values
 * </ul>
 */
public class BleutradeMarketDataServiceRaw extends BleutradeBaseService {

  /**
   * Constructor
   *
   * @param exchange
   */
  public BleutradeMarketDataServiceRaw(Exchange exchange, IRestProxyFactory restProxyFactory) {

    super(exchange, restProxyFactory);
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

  public BleutradeOrderBook getBleutradeOrderBook(CurrencyPair currencyPair, int depth)
      throws IOException {

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

  public List<BleutradeTrade> getBleutradeMarketHistory(CurrencyPair currencyPair, int count)
      throws IOException {

    String pairString = BleutradeUtils.toPairString(currencyPair);

    BleutradeMarketHistoryReturn response = bleutrade.getBleutradeMarketHistory(pairString, count);

    if (!response.getSuccess()) {
      throw new ExchangeException(response.getMessage());
    }

    return response.getResult();
  }
}
