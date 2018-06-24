package org.knowm.xchange.coinbase.v2.dto.marketdata;

import static org.assertj.core.api.Assertions.assertThat;

import com.fasterxml.jackson.databind.JavaType;
import com.fasterxml.jackson.databind.ObjectMapper;
import java.io.IOException;
import java.io.InputStream;
import java.math.BigDecimal;
import java.util.List;
import java.util.Map;
import org.junit.Test;
import org.knowm.xchange.coinbase.v2.dto.CoinbasePrice;
import org.knowm.xchange.coinbase.v2.dto.marketdata.CoinbaseCurrencyData.CoinbaseCurrency;

public class CoinbaseMarketDataJsonTest {

  @Test
  public void testDeserializeExchangeRates() throws IOException {

    // Read in the JSON from the example resources
    InputStream is =
        CoinbaseMarketDataJsonTest.class.getResourceAsStream(
            "/org/knowm/xchange/coinbase/marketdata/example-exchange-rate-data.json");

    // Use Jackson to parse it
    ObjectMapper mapper = new ObjectMapper();
    JavaType javaType = mapper.getTypeFactory().constructType(CoinbaseExchangeRateData.class);
    CoinbaseExchangeRateData rawdata = mapper.readValue(is, javaType);

    Map<String, BigDecimal> exchangeRates = rawdata.getData().getRates();
    assertThat(exchangeRates.size()).isEqualTo(170);

    BigDecimal exchangeRate = exchangeRates.get("EUR");
    assertThat(exchangeRate).isEqualByComparingTo("9980.00");
  }

  @Test
  public void testDeserializeCurrencies() throws IOException {

    // Read in the JSON from the example resources
    InputStream is =
        CoinbaseMarketDataJsonTest.class.getResourceAsStream(
            "/org/knowm/xchange/coinbase/marketdata/example-currencies-data.json");

    // Use Jackson to parse it
    ObjectMapper mapper = new ObjectMapper();
    JavaType javaType = mapper.getTypeFactory().constructType(CoinbaseCurrencyData.class);
    CoinbaseCurrencyData rawdata = mapper.readValue(is, javaType);

    List<CoinbaseCurrency> currencies = rawdata.getData();
    assertThat(currencies.size()).isEqualTo(168);

    CoinbaseCurrency currency = currencies.get(167);
    assertThat(currency.getId()).isEqualTo("ZWL");
    assertThat(currency.getName()).isEqualTo("Zimbabwean Dollar");
  }

  @Test
  public void testDeserializePrice() throws IOException {

    // Read in the JSON from the example resources
    InputStream is =
        CoinbaseMarketDataJsonTest.class.getResourceAsStream(
            "/org/knowm/xchange/coinbase/marketdata/example-price-data.json");

    // Use Jackson to parse it
    ObjectMapper mapper = new ObjectMapper();
    JavaType javaType = mapper.getTypeFactory().constructType(CoinbasePriceData.class);
    CoinbasePriceData rawdata = mapper.readValue(is, javaType);

    CoinbasePrice price = rawdata.getData();
    assertThat(price)
        .isEqualToComparingFieldByField(new CoinbasePrice(new BigDecimal("11832.46"), "USD"));
  }
}
