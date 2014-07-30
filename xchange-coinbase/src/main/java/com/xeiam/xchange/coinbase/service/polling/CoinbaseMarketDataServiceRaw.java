package com.xeiam.xchange.coinbase.service.polling;

import java.io.IOException;
import java.math.BigDecimal;
import java.util.Map;

import com.xeiam.xchange.ExchangeSpecification;
import com.xeiam.xchange.coinbase.Coinbase;
import com.xeiam.xchange.coinbase.dto.marketdata.CoinbaseMoney;
import com.xeiam.xchange.coinbase.dto.marketdata.CoinbasePrice;
import com.xeiam.xchange.coinbase.dto.marketdata.CoinbaseSpotPriceHistory;

/**
 * @author jamespedwards42
 */
class CoinbaseMarketDataServiceRaw extends CoinbaseBasePollingService<Coinbase> {

  /**
   * Constructor
   * 
   * @param exchangeSpecification
   */
  public CoinbaseMarketDataServiceRaw(final ExchangeSpecification exchangeSpecification) {

    super(Coinbase.class, exchangeSpecification);
  }

  /**
   * Unauthenticated resource that returns BTC to fiat (and vice versus) exchange rates in various currencies.
   * 
   * @see <a href="https://coinbase.com/api/doc/1.0/currencies/exchange_rates.html">coinbase.com/api/doc/1.0/currencies/exchange_rates.html</a>
   * @return Map of lower case directional currency pairs, i.e. btc_to_xxx and xxx_to_btc, to exchange rates.
   * @throws IOException
   */
  public Map<String, BigDecimal> getCoinbaseCurrencyExchangeRates() throws IOException {

    return coinbase.getCurrencyExchangeRates();
  }

  /**
   * Unauthenticated resource that tells you the total price in USD to buy 1 Bitcoin.
   * 
   * @see <a href="https://coinbase.com/api/doc/1.0/prices/buy.html">coinbase.com/api/doc/1.0/prices/buy.html</a>
   * @return The price to buy 1 BTC.
   * @throws IOException
   */
  public CoinbasePrice getCoinbaseBuyPrice() throws IOException {

    return getCoinbaseBuyPrice(null, null);
  }

  /**
   * Unauthenticated resource that tells you the total price in USD to buy some quantity of Bitcoin.
   * 
   * @see <a href="https://coinbase.com/api/doc/1.0/prices/buy.html">coinbase.com/api/doc/1.0/prices/buy.html</a>
   * @param quantity The quantity of Bitcoin you would like to buy (default is 1 if null).
   * @return The price to buy the given {@code quantity} BTC.
   * @throws IOException
   */
  public CoinbasePrice getCoinbaseBuyPrice(BigDecimal quantity) throws IOException {

    return getCoinbaseBuyPrice(quantity, null);
  }

  /**
   * Unauthenticated resource that tells you the total price to buy some quantity of Bitcoin.
   * 
   * @see <a href="https://coinbase.com/api/doc/1.0/prices/buy.html">coinbase.com/api/doc/1.0/prices/buy.html</a>
   * @param quantity The quantity of Bitcoin you would like to buy (default is 1 if null).
   * @param currency Default is USD. Right now this is the only value allowed.
   * @return The price in the desired {@code currency} to buy the given {@code quantity} BTC.
   * @throws IOException
   */
  public CoinbasePrice getCoinbaseBuyPrice(BigDecimal quantity, String currency) throws IOException {

    return coinbase.getBuyPrice(quantity, currency);
  }

  /**
   * Unauthenticated resource that tells you the total amount in USD you can get if you sell 1 Bitcoin.
   * 
   * @see <a href="https://coinbase.com/api/doc/1.0/prices/sell.html">coinbase.com/api/doc/1.0/prices/sell.html</a>
   * @return The price to sell 1 BTC.
   * @throws IOException
   */
  public CoinbasePrice getCoinbaseSellPrice() throws IOException {

    return getCoinbaseSellPrice(null, null);
  }

  /**
   * Unauthenticated resource that tells you the total amount in USD you can get if you sell some quantity Bitcoin.
   * 
   * @see <a href="https://coinbase.com/api/doc/1.0/prices/sell.html">coinbase.com/api/doc/1.0/prices/sell.html</a>
   * @param quantity The quantity of Bitcoin you would like to sell (default is 1 if null).
   * @return The price to sell the given {@code quantity} BTC.
   * @throws IOException
   */
  public CoinbasePrice getCoinbaseSellPrice(BigDecimal quantity) throws IOException {

    return getCoinbaseSellPrice(quantity, null);
  }

  /**
   * Unauthenticated resource that tells you the total amount you can get if you sell some quantity Bitcoin.
   * 
   * @see <a href="https://coinbase.com/api/doc/1.0/prices/sell.html">coinbase.com/api/doc/1.0/prices/sell.html</a>
   * @param quantity The quantity of Bitcoin you would like to sell (default is 1 if null).
   * @param currency Default is USD. Right now this is the only value allowed.
   * @return The price in the desired {@code currency} to sell the given {@code quantity} BTC.
   * @throws IOException
   */
  public CoinbasePrice getCoinbaseSellPrice(BigDecimal quantity, String currency) throws IOException {

    return coinbase.getSellPrice(quantity, currency);
  }

  /**
   * Unauthenticated resource that tells you the current price of Bitcoin.
   * This is usually somewhere in between the buy and sell price, current to within a few minutes.
   * 
   * @see <a href="https://coinbase.com/api/doc/1.0/prices/spot_rate.html">coinbase.com/api/doc/1.0/prices/spot_rate.html</a>
   * @param currency ISO 4217 currency code. Default is USD if null.
   * @return
   * @throws IOException
   */
  public CoinbaseMoney getCoinbaseSpotRate(String currency) throws IOException {

    return coinbase.getSpotRate(currency);
  }

  /**
   * Unauthenticated resource that displays historical spot rates for Bitcoin in USD.
   * This is a paged resource and will return the first page by default.
   * 
   * @see <a href="https://coinbase.com/api/doc/1.0/prices/historical.html">coinbase.com/api/doc/1.0/prices/historical.html</a>
   * @return One thousand historical spot prices representing page 1.
   * @throws IOException
   */
  public CoinbaseSpotPriceHistory getCoinbaseHistoricalSpotRates() throws IOException {

    return getCoinbaseHistoricalSpotRates(null);
  }

  /**
   * Unauthenticated resource that displays historical spot rates for Bitcoin in USD.
   * 
   * @param page Optional parameter to request a desired page of results. Will return page 1 if the supplied page is null or less than 1.
   * @return One thousand historical spot prices for the given page.
   * @throws IOException
   */
  public CoinbaseSpotPriceHistory getCoinbaseHistoricalSpotRates(Integer page) throws IOException {

    return CoinbaseSpotPriceHistory.fromRawString(coinbase.getHistoricalSpotRates(page));
  }
}
