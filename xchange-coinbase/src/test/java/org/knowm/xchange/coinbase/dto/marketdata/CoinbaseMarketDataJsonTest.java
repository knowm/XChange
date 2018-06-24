package org.knowm.xchange.coinbase.dto.marketdata;

import static org.assertj.core.api.Assertions.assertThat;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.type.CollectionType;
import com.fasterxml.jackson.databind.type.MapLikeType;
import java.io.IOException;
import java.io.InputStream;
import java.math.BigDecimal;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Scanner;
import org.junit.Test;
import org.knowm.xchange.utils.DateUtils;

/** @author jamespedwards42 */
public class CoinbaseMarketDataJsonTest {

  @Test
  public void testDeserializeExchangeRates() throws IOException {

    // Read in the JSON from the example resources
    InputStream is =
        CoinbaseMarketDataJsonTest.class.getResourceAsStream(
            "/org/knowm/xchange/coinbase/dto/marketdata/example-exchange-rate-data.json");

    // Use Jackson to parse it
    ObjectMapper mapper = new ObjectMapper();
    MapLikeType mapType =
        mapper.getTypeFactory().constructMapLikeType(HashMap.class, String.class, BigDecimal.class);
    Map<String, BigDecimal> exchangeRates = mapper.readValue(is, mapType);

    assertThat(exchangeRates.size()).isEqualTo(632);

    BigDecimal exchangeRate = exchangeRates.get("scr_to_btc");
    assertThat(exchangeRate).isEqualByComparingTo("0.000115");
  }

  @Test
  public void testDeserializeCurrencies() throws IOException {

    // Read in the JSON from the example resources
    InputStream is =
        CoinbaseMarketDataJsonTest.class.getResourceAsStream(
            "/org/knowm/xchange/coinbase/dto/marketdata/example-currencies-data.json");

    // Use Jackson to parse it
    ObjectMapper mapper = new ObjectMapper();
    CollectionType collectionType =
        mapper.getTypeFactory().constructCollectionType(List.class, CoinbaseCurrency.class);
    List<CoinbaseCurrency> currencies = mapper.readValue(is, collectionType);

    assertThat(currencies.size()).isEqualTo(161);

    CoinbaseCurrency currency = currencies.get(160);
    assertThat(currency.getIsoCode()).isEqualTo("ZWL");
    assertThat(currency.getName()).isEqualTo("Zimbabwean Dollar (ZWL)");
  }

  @Test
  public void testDeserializePrice() throws IOException {

    // Read in the JSON from the example resources
    InputStream is =
        CoinbaseMarketDataJsonTest.class.getResourceAsStream(
            "/org/knowm/xchange/coinbase/dto/marketdata/example-price-data.json");

    // Use Jackson to parse it
    ObjectMapper mapper = new ObjectMapper();
    CoinbasePrice price = mapper.readValue(is, CoinbasePrice.class);

    assertThat(price.getSubTotal())
        .isEqualToComparingFieldByField(new CoinbaseMoney("USD", new BigDecimal("723.09")));
    assertThat(price.getCoinbaseFee())
        .isEqualToComparingFieldByField(new CoinbaseMoney("USD", new BigDecimal("7.23")));
    assertThat(price.getBankFee())
        .isEqualToComparingFieldByField(new CoinbaseMoney("USD", new BigDecimal("0.15")));
    assertThat(price.getTotal())
        .isEqualToComparingFieldByField(new CoinbaseMoney("USD", new BigDecimal("730.47")));
  }

  @Test
  public void testDeserializeSpotRateHistory() throws IOException {

    // Read in the JSON from the example resources
    InputStream is =
        CoinbaseMarketDataJsonTest.class.getResourceAsStream(
            "/org/knowm/xchange/coinbase/dto/marketdata/example-spot-rate-history-data.txt");
    String spotPriceHistoryString;
    try (Scanner scanner = new Scanner(is)) {
      spotPriceHistoryString = scanner.useDelimiter("\\A").next();
    }

    CoinbaseSpotPriceHistory spotPriceHistory =
        CoinbaseSpotPriceHistory.fromRawString(spotPriceHistoryString);

    List<CoinbaseHistoricalSpotPrice> spotPriceHistoryList = spotPriceHistory.getSpotPriceHistory();
    assertThat(spotPriceHistoryList.size()).isEqualTo(10);

    CoinbaseHistoricalSpotPrice historicalSpotPrice = spotPriceHistoryList.get(0);
    assertThat(historicalSpotPrice.getSpotRate()).isEqualTo("719.79");
    assertThat(historicalSpotPrice.getTimestamp())
        .isEqualTo(DateUtils.fromISO8601DateString("2014-02-08T13:21:51-08:00"));
  }
}
