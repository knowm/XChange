package org.knowm.xchange.wex.v3.service;

import java.io.IOException;
import org.knowm.xchange.Exchange;
import org.knowm.xchange.wex.v3.dto.marketdata.WexDepthWrapper;
import org.knowm.xchange.wex.v3.dto.marketdata.WexExchangeInfo;
import org.knowm.xchange.wex.v3.dto.marketdata.WexTickerWrapper;
import org.knowm.xchange.wex.v3.dto.marketdata.WexTradesWrapper;

/** @author brox */
public class WexMarketDataServiceRaw extends WexBaseService {

  protected static final int FULL_SIZE = 5000;

  /**
   * Constructor
   *
   * @param exchange
   */
  public WexMarketDataServiceRaw(Exchange exchange) {

    super(exchange);
  }

  /**
   * @param pairs Dash-delimited string of currency pairs to retrieve (e.g. "btc_usd-ltc_btc")
   * @return WexTickerWrapper object
   * @throws IOException
   */
  public WexTickerWrapper getBTCETicker(String pairs) throws IOException {

    return btce.getTicker(pairs.toLowerCase(), 1);
  }

  /**
   * Get market depth from exchange
   *
   * @param pairs Dash-delimited string of currency pairs to retrieve (e.g. "btc_usd-ltc_btc")
   * @param size Integer value from 1 to 2000 -> get corresponding number of items
   * @return WexDepthWrapper object
   * @throws IOException
   */
  public WexDepthWrapper getBTCEDepth(String pairs, int size) throws IOException {

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
   * @return WexTradesWrapper object
   * @throws IOException
   */
  public WexTradesWrapper getBTCETrades(String pairs, int size) throws IOException {

    size = size < 1 ? 1 : (size > FULL_SIZE ? FULL_SIZE : size);

    return btce.getTrades(pairs.toLowerCase(), size, 1);
  }

  /**
   * Get BTC-e exchange info
   *
   * @return WexExchangeInfo object
   * @throws IOException
   */
  public WexExchangeInfo getBTCEInfo() throws IOException {

    return btce.getInfo();
  }
}
