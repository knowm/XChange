/**
 * Copyright (C) 2012 - 2014 Xeiam LLC http://xeiam.com
 *
 * Permission is hereby granted, free of charge, to any person obtaining a copy of
 * this software and associated documentation files (the "Software"), to deal in
 * the Software without restriction, including without limitation the rights to
 * use, copy, modify, merge, publish, distribute, sublicense, and/or sell copies
 * of the Software, and to permit persons to whom the Software is furnished to do
 * so, subject to the following conditions:
 *
 * The above copyright notice and this permission notice shall be included in all
 * copies or substantial portions of the Software.
 *
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
 * IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
 * FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
 * AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
 * LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
 * OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE
 * SOFTWARE.
 */
package com.xeiam.xchange.coinbase.dto.marketdata;

import static org.fest.assertions.api.Assertions.assertThat;

import java.io.IOException;
import java.io.InputStream;
import java.math.BigDecimal;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.junit.Test;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.type.CollectionType;
import com.fasterxml.jackson.databind.type.MapLikeType;

/**
 * @author jamespedwards42
 */
public class CoinbaseMarketDataJsonTest {

  @Test
  public void testDeserializeExchangeRates() throws IOException {

    // Read in the JSON from the example resources
    InputStream is = CoinbaseMarketDataJsonTest.class.getResourceAsStream("/marketdata/example-exchange-rate-data.json");

    // Use Jackson to parse it
    ObjectMapper mapper = new ObjectMapper();
    MapLikeType mapType = mapper.getTypeFactory().constructMapLikeType(HashMap.class, String.class, BigDecimal.class);
    Map<String, BigDecimal> exchangeRates = mapper.readValue(is, mapType);

    assertThat(exchangeRates.size()).isEqualTo(632);

    BigDecimal exchangeRate = exchangeRates.get("scr_to_btc");
    assertThat(exchangeRate).isEqualByComparingTo("0.000115");
  }

  @Test
  public void testDeserializeCurrencies() throws IOException {

    // Read in the JSON from the example resources
    InputStream is = CoinbaseMarketDataJsonTest.class.getResourceAsStream("/marketdata/example-currencies-data.json");

    // Use Jackson to parse it
    ObjectMapper mapper = new ObjectMapper();
    CollectionType collectionType = mapper.getTypeFactory().constructCollectionType(List.class, CoinbaseCurrency.class);
    List<CoinbaseCurrency> currencies = mapper.readValue(is, collectionType);

    assertThat(currencies.size()).isEqualTo(161);

    CoinbaseCurrency currency = currencies.get(160);
    assertThat(currency.getIsoCode()).isEqualTo("ZWL");
    assertThat(currency.getName()).isEqualTo("Zimbabwean Dollar (ZWL)");
  }

  @Test
  public void testDeserializePrice() throws IOException {

    // Read in the JSON from the example resources
    InputStream is = CoinbaseMarketDataJsonTest.class.getResourceAsStream("/marketdata/example-price-data.json");

    // Use Jackson to parse it
    ObjectMapper mapper = new ObjectMapper();
    CoinbasePrice price = mapper.readValue(is, CoinbasePrice.class);

    assertThat(price.getSubTotal()).isEqualsToByComparingFields(new CoinbaseMoney("USD", new BigDecimal("723.09")));
    assertThat(price.getCoinbaseFee()).isEqualsToByComparingFields(new CoinbaseMoney("USD", new BigDecimal("7.23")));
    assertThat(price.getBankFee()).isEqualsToByComparingFields(new CoinbaseMoney("USD", new BigDecimal("0.15")));
    assertThat(price.getTotal()).isEqualsToByComparingFields(new CoinbaseMoney("USD", new BigDecimal("730.47")));
  }

  // @Test
  // public void testDeserializeSpotRateHistory() throws IOException {
  //
  // // Read in the JSON from the example resources
  // InputStream is = CoinbaseMarketDataJsonTest.class.getResourceAsStream("/marketdata/example-spot-rate-history-data.json");
  // String spotPriceHistoryString;
  // Scanner scanner = null;
  // try {
  // scanner = new Scanner(is);
  // spotPriceHistoryString = scanner.useDelimiter("\\A").next();
  // } finally {
  // scanner.close();
  // }
  //
  // // Use Jackson to parse it
  // ObjectMapper mapper = new ObjectMapper();
  // CoinbaseSpotPriceHistory spotPriceHistory = mapper.readValue(spotPriceHistoryString, CoinbaseSpotPriceHistory.class);
  //
  // List<CoinbaseHistoricalSpotPrice> spotPriceHistoryList = spotPriceHistory.getSpotPriceHistory();
  // assertThat(spotPriceHistoryList.size()).isEqualTo(10);
  //
  // CoinbaseHistoricalSpotPrice historicalSpotPrice = spotPriceHistoryList.get(0);
  // assertThat(historicalSpotPrice.getSpotRate()).isEqualTo("719.79");
  // assertThat(historicalSpotPrice.getTimestamp()).isEqualTo(DateUtils.fromISO8601DateString("2014-02-08T13:21:51-08:00"));
  // }
}
