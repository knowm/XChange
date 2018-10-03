package org.knowm.xchange.coinmarketcap.dto.marketdata;

import com.fasterxml.jackson.databind.ObjectMapper;
import java.io.IOException;
import java.io.InputStream;
import java.util.List;
import org.junit.Test;

/** @author allenday */
public class CoinMarketCapMarketDataJsonTest {

  @Test
  public void testDeserializeTicker() throws IOException {

    // Read in the JSON from the example resources
    InputStream is =
        CoinMarketCapMarketDataJsonTest.class.getResourceAsStream(
            "/org/knowm/xchange/coinmarketcap/dto/marketdata/example-ticker-data-v2.json");

    // Use Jackson to parse it
    ObjectMapper mapper = new ObjectMapper();
    // MapLikeType mapType = mapper.getTypeFactory().constructMapLikeType(HashMap.class,
    // String.class, String.class);

    List<CoinMarketCapTicker> tickers =
        mapper.readValue(is, CoinMarketCapArrayData.class).getData();

    CoinMarketCapTicker eth = null;
    CoinMarketCapTicker btc = null;

    for (CoinMarketCapTicker t : tickers) {
      if (t.getSymbol().compareTo("ETH") == 0) eth = t;
      if (t.getSymbol().compareTo("BTC") == 0) btc = t;
    }

    assert (eth.getSymbol().compareTo("ETH") == 0);
    assert (btc.getSymbol().compareTo("BTC") == 0);
    assert (btc.getQuotes().get("USD").getPrice().doubleValue() == 5947.06);
    assert (eth.getQuotes().get("USD").getPrice().doubleValue() == 440.86);
    assert (eth.getQuotes().get("BTC").getPrice().doubleValue() == 0.074130747);

    //    Map<String, BigDecimal> exchangeRates = mapper.readValue(is, mapType);
    //
    //    assertThat(exchangeRates.size()).isEqualTo(632);
    //
    //    BigDecimal exchangeRate = exchangeRates.get("scr_to_btc");
    //    assertThat(exchangeRate).isEqualByComparingTo("0.000115");

  }
  /*
    @Test
    public void testDeserializeCurrencies() throws IOException {

      // Read in the JSON from the example resources
      InputStream is = CoinMarketCapMarketDataJsonTest.class.getResourceAsStream("/marketdata/example-ticker-data.json");

      // Use Jackson to parse it
      ObjectMapper mapper = new ObjectMapper();
      CollectionType collectionType = mapper.getTypeFactory().constructCollectionType(List.class, CoinMarketCapCurrency.class);
      List<CoinMarketCapCurrency> currencies = mapper.readValue(is, collectionType);

      assertThat(currencies.size()).isEqualTo(161);

      CoinMarketCapCurrency currency = currencies.get(160);
      assertThat(currency.getIsoCode()).isEqualTo("ZWL");
      assertThat(currency.getName()).isEqualTo("Zimbabwean Dollar (ZWL)");
    }

    @Test
    public void testDeserializePrice() throws IOException {

      // Read in the JSON from the example resources
      InputStream is = CoinMarketCapMarketDataJsonTest.class.getResourceAsStream("/marketdata/example-ticker-data.json");

      // Use Jackson to parse it
      ObjectMapper mapper = new ObjectMapper();
      CoinMarketCapPrice price = mapper.readValue(is, CoinMarketCapPrice.class);

      assertThat(price.getSubTotal()).isEqualToComparingFieldByField(new CoinMarketCapMoney("USD", new BigDecimal("723.09")));
      assertThat(price.getCoinMarketCapFee()).isEqualToComparingFieldByField(new CoinMarketCapMoney("USD", new BigDecimal("7.23")));
      assertThat(price.getBankFee()).isEqualToComparingFieldByField(new CoinMarketCapMoney("USD", new BigDecimal("0.15")));
      assertThat(price.getTotal()).isEqualToComparingFieldByField(new CoinMarketCapMoney("USD", new BigDecimal("730.47")));
    }

    @Test
    public void testDeserializeSpotRateHistory() throws IOException {

      // Read in the JSON from the example resources
      InputStream is = CoinMarketCapMarketDataJsonTest.class.getResourceAsStream("/marketdata/example-spot-rate-history-data.json");
      String spotPriceHistoryString;
      try (Scanner scanner = new Scanner(is)) {
        spotPriceHistoryString = scanner.useDelimiter("\\A").next();
      }

      CoinMarketCapSpotPriceHistory spotPriceHistory = CoinMarketCapSpotPriceHistory.fromRawString(spotPriceHistoryString);

      List<CoinMarketCapHistoricalSpotPrice> spotPriceHistoryList = spotPriceHistory.getSpotPriceHistory();
      assertThat(spotPriceHistoryList.size()).isEqualTo(10);

      CoinMarketCapHistoricalSpotPrice historicalSpotPrice = spotPriceHistoryList.get(0);
      assertThat(historicalSpotPrice.getSpotRate()).isEqualTo("719.79");
      assertThat(historicalSpotPrice.getTimestamp()).isEqualTo(DateUtils.fromISO8601DateString("2014-02-08T13:21:51-08:00"));
    }
  */
}
