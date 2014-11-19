package com.xeiam.xchange.bleutrade.service.polling;

import java.io.IOException;
import java.util.List;

import com.xeiam.xchange.ExchangeException;
import com.xeiam.xchange.ExchangeSpecification;
import com.xeiam.xchange.bleutrade.Bleutrade;
import com.xeiam.xchange.bleutrade.BleutradeUtils;
import com.xeiam.xchange.bleutrade.dto.marketdata.BleutradeMarketHistoryReturn;
import com.xeiam.xchange.bleutrade.dto.marketdata.BleutradeOrderBook;
import com.xeiam.xchange.bleutrade.dto.marketdata.BleutradeOrderBookReturn;
import com.xeiam.xchange.bleutrade.dto.marketdata.BleutradeTicker;
import com.xeiam.xchange.bleutrade.dto.marketdata.BleutradeTickerReturn;
import com.xeiam.xchange.bleutrade.dto.marketdata.BleutradeTrade;
import com.xeiam.xchange.currency.CurrencyPair;

/**
 * <p>
 * Implementation of the market data service for Bleutrade
 * </p>
 * <ul>
 * <li>Provides access to various market data values</li>
 * </ul>
 */
public class BleutradeMarketDataServiceRaw extends BleutradeBasePollingService<Bleutrade> {

  /**
   * Constructor
   * 
   * @param exchangeSpecification The {@link ExchangeSpecification}
   */
  public BleutradeMarketDataServiceRaw(ExchangeSpecification exchangeSpecification) {

    super(Bleutrade.class, exchangeSpecification);
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

  public List<BleutradeTrade> getBleutradeMarketHistory(CurrencyPair currencyPair, int count) throws IOException {

    String pairString = BleutradeUtils.toPairString(currencyPair);

    BleutradeMarketHistoryReturn response = bleutrade.getBleutradeMarketHistory(pairString, count);

    if (!response.getSuccess()) {
      throw new ExchangeException(response.getMessage());
    }

    return response.getResult();
  }

}
