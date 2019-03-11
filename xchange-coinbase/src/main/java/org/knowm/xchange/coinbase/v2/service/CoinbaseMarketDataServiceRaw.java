package org.knowm.xchange.coinbase.v2.service;

import java.io.IOException;
import java.math.BigDecimal;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Map;
import org.knowm.xchange.Exchange;
import org.knowm.xchange.coinbase.v2.Coinbase;
import org.knowm.xchange.coinbase.v2.dto.CoinbasePrice;
import org.knowm.xchange.currency.Currency;

class CoinbaseMarketDataServiceRaw extends CoinbaseBaseService {

  public CoinbaseMarketDataServiceRaw(Exchange exchange) {

    super(exchange);
  }

  /**
   * Unauthenticated resource that returns BTC to fiat (and vice versus) exchange rates in various
   * currencies.
   *
   * @return Map of lower case directional currency pairs, i.e. btc_to_xxx and xxx_to_btc, to
   *     exchange rates.
   * @throws IOException
   * @see <a
   *     href="https://developers.coinbase.com/api/v2#exchange-rates">developers.coinbase.com/api/v2#exchange-rates</a>
   */
  public Map<String, BigDecimal> getCoinbaseExchangeRates() throws IOException {

    return coinbase.getCurrencyExchangeRates(Coinbase.CB_VERSION_VALUE).getData().getRates();
  }

  /**
   * Unauthenticated resource that tells you the price to buy one unit.
   *
   * @param pair The currency pair.
   * @return The price in the desired {@code currency} to buy one unit.
   * @throws IOException
   * @see <a
   *     href="https://developers.coinbase.com/api/v2#get-buy-price">developers.coinbase.com/api/v2#get-buy-price</a>
   */
  public CoinbasePrice getCoinbaseBuyPrice(Currency base, Currency counter) throws IOException {

    return coinbase.getBuyPrice(Coinbase.CB_VERSION_VALUE, base + "-" + counter).getData();
  }

  /**
   * Unauthenticated resource that tells you the amount you can get if you sell one unit.
   *
   * @param pair The currency pair.
   * @return The price in the desired {@code currency} to sell one unit.
   * @throws IOException
   * @see <a
   *     href="https://developers.coinbase.com/api/v2#get-sell-price">developers.coinbase.com/api/v2#get-sell-price</a>
   */
  public CoinbasePrice getCoinbaseSellPrice(Currency base, Currency counter) throws IOException {

    return coinbase.getSellPrice(Coinbase.CB_VERSION_VALUE, base + "-" + counter).getData();
  }

  /**
   * Unauthenticated resource that tells you the current price of one unit. This is usually
   * somewhere in between the buy and sell price, current to within a few minutes.
   *
   * @param pair The currency pair.
   * @return The price in the desired {@code currency} for one unit.
   * @throws IOException
   * @see <a
   *     href="https://developers.coinbase.com/api/v2#get-spot-price">developers.coinbase.com/api/v2#get-spot-price</a>
   */
  public CoinbasePrice getCoinbaseSpotRate(Currency base, Currency counter) throws IOException {

    return coinbase.getSpotRate(Coinbase.CB_VERSION_VALUE, base + "-" + counter).getData();
  }

  /**
   * Unauthenticated resource that tells you the current price of one unit. This is usually
   * somewhere in between the buy and sell price, current to within a few minutes.
   *
   * @param pair The currency pair.
   * @param date The given date.
   * @return The price in the desired {@code currency} ont the give {@code date} for one unit.
   * @throws IOException
   * @see <a
   *     href="https://developers.coinbase.com/api/v2#get-spot-price">developers.coinbase.com/api/v2#get-spot-price</a>
   */
  public CoinbasePrice getCoinbaseHistoricalSpotRate(Currency base, Currency counter, Date date)
      throws IOException {
    String datespec = new SimpleDateFormat("yyyy-MM-dd").format(date);
    return coinbase
        .getHistoricalSpotRate(Coinbase.CB_VERSION_VALUE, base + "-" + counter, datespec)
        .getData();
  }
}
