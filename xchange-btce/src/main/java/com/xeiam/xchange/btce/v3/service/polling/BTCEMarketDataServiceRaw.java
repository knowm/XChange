package com.xeiam.xchange.btce.v3.service.polling;

import com.xeiam.xchange.ExchangeSpecification;
import com.xeiam.xchange.btce.v3.BTCE;
import com.xeiam.xchange.btce.v3.BTCEUtils;
import com.xeiam.xchange.btce.v3.dto.marketdata.*;
import com.xeiam.xchange.currency.CurrencyPair;
import com.xeiam.xchange.service.polling.BasePollingExchangeService;
import com.xeiam.xchange.utils.Assert;
import si.mazi.rescu.RestProxyFactory;

import java.io.IOException;
import java.util.List;

/**
 * Author: brox
 * Since: 2014-02-12
 */

public class BTCEMarketDataServiceRaw extends BasePollingExchangeService {

  private final BTCE btce;
  protected static final int FULL_SIZE = 2000;

  /**
   * Initialize common properties from the exchange specification
   *
   * @param exchangeSpecification The {@link com.xeiam.xchange.ExchangeSpecification}
   */
  public BTCEMarketDataServiceRaw(ExchangeSpecification exchangeSpecification) {
    super(exchangeSpecification);
    this.btce = RestProxyFactory.createProxy(BTCE.class, exchangeSpecification.getSslUri());
  }

  /**
   * @param pairs List of CurrencyPairs to retrieve
   * @return BTCETickerWrapper object
   * @throws IOException
   */
  public BTCETickerWrapper getBTCETicker(List<CurrencyPair> pairs) throws IOException {

    return btce.getTicker(buildPairs(pairs), 1);
  }

  /**
   * Get market depth from exchange
   *
   * @param pairs List of CurrencyPairs to retrieve
   * @param size Integer value from 1 to 2000 -> get corresponding number of items
   * @return BTCEDepthWrapper object
   * @throws IOException
   */
  public BTCEDepthWrapper getBTCEDepth(List<CurrencyPair> pairs, int size) throws IOException {

    if (size < 1)
      size = 1;

    if (size > FULL_SIZE)
      size = FULL_SIZE;

    return btce.getDepth(buildPairs(pairs), size, 1);
  }

  /**
   * Get recent trades from exchange
   *
   * @param pairs List of CurrencyPairs to retrieve
   * @param size Integer value from 1 to 2000 -> get corresponding number of items
   * @return BTCETradesWrapper object
   * @throws IOException
   */
  public BTCETradesWrapper getBTCETrades(List<CurrencyPair> pairs, int size) throws IOException {

    if (size < 1)
      size = 1;

    if (size > FULL_SIZE)
      size = FULL_SIZE;

    return btce.getTrades(buildPairs(pairs), size, 1);
  }

  public BTCEExchangeInfo getBTCEInfo() throws IOException {

    return btce.getInfo();
  }

  /**
   * Verify that currency pair is valid for BTC-e exchange
   *
   * @param pair The currency pair
   */
  private void verify(CurrencyPair pair) {

    Assert.notNull(pair, "CurrencyPair cannot be null");
    Assert.isTrue(BTCEUtils.isValidCurrencyPair(pair), "currencyPair is not valid:" + pair.toString());
  }

  private String buildPairs(List<CurrencyPair> pairs) {

    Assert.notNull(pairs, "List of Currency Pairs should not be null");

    StringBuilder btcePairs = null;
    for (CurrencyPair p : pairs) {
      verify(p);
      if (btcePairs == null)
        btcePairs = new StringBuilder((p.baseCurrency).concat("_").concat(p.counterCurrency));
      else
        btcePairs.append("-").append((p.baseCurrency).concat("_").concat(p.counterCurrency));
    }
    Assert.notNull(btcePairs, "List should contain at least one CurrencyPair");

    return btcePairs.toString().toLowerCase();
  }

}
