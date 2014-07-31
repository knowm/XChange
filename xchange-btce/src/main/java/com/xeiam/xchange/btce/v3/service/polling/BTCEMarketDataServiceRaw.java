package com.xeiam.xchange.btce.v3.service.polling;

import java.io.IOException;

import com.xeiam.xchange.ExchangeSpecification;
import com.xeiam.xchange.btce.v3.BTCE;
import com.xeiam.xchange.btce.v3.dto.marketdata.BTCEDepthWrapper;
import com.xeiam.xchange.btce.v3.dto.marketdata.BTCEExchangeInfo;
import com.xeiam.xchange.btce.v3.dto.marketdata.BTCETickerWrapper;
import com.xeiam.xchange.btce.v3.dto.marketdata.BTCETradesWrapper;

/**
 * Author: brox
 * Since: 2014-02-12
 */
public class BTCEMarketDataServiceRaw extends BTCEBasePollingService<BTCE> {

  protected static final int FULL_SIZE = 2000;

  /**
   * Initialize common properties from the exchange specification
   * 
   * @param exchangeSpecification The {@link com.xeiam.xchange.ExchangeSpecification}
   */
  public BTCEMarketDataServiceRaw(ExchangeSpecification exchangeSpecification) {

    super(BTCE.class, exchangeSpecification);
  }

  /**
   * @param pairs Dash-delimited string of currency pairs to retrieve (e.g. "btc_usd-ltc_btc")
   * @return BTCETickerWrapper object
   * @throws IOException
   */
  public BTCETickerWrapper getBTCETicker(String pairs) throws IOException {

    return btce.getTicker(pairs.toLowerCase(), 1);
  }

  /**
   * Get market depth from exchange
   * 
   * @param pairs Dash-delimited string of currency pairs to retrieve (e.g. "btc_usd-ltc_btc")
   * @param size Integer value from 1 to 2000 -> get corresponding number of items
   * @return BTCEDepthWrapper object
   * @throws IOException
   */
  public BTCEDepthWrapper getBTCEDepth(String pairs, int size) throws IOException {

    if (size < 1) {
      size = 1;
    }

    if (size > FULL_SIZE) {
      size = FULL_SIZE;
    }

    return btce.getDepth(pairs.toLowerCase(), size, 1);
  }

  /**
   * Get recent trades from exchange
   * 
   * @param pairs Dash-delimited string of currency pairs to retrieve (e.g. "btc_usd-ltc_btc")
   * @param size Integer value from 1 to 2000 -> get corresponding number of items
   * @return BTCETradesWrapper object
   * @throws IOException
   */
  public BTCETradesWrapper getBTCETrades(String pairs, int size) throws IOException {

    if (size < 1) {
      size = 1;
    }

    if (size > FULL_SIZE) {
      size = FULL_SIZE;
    }

    return btce.getTrades(pairs.toLowerCase(), size, 1);
  }

  /**
   * Get BTC-e exchange info
   * 
   * @return BTCEExchangeInfo object
   * @throws IOException
   */
  public BTCEExchangeInfo getBTCEInfo() throws IOException {

    return btce.getInfo();
  }

}
