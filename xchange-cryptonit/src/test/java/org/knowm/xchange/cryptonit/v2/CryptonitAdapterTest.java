package org.knowm.xchange.cryptonit.v2;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.Assert.assertEquals;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.type.CollectionLikeType;
import java.io.IOException;
import java.io.InputStream;
import java.math.BigDecimal;
import java.util.Collection;
import java.util.List;
import org.junit.Test;
import org.knowm.xchange.cryptonit.v2.dto.marketdata.CryptonitOrders;
import org.knowm.xchange.cryptonit.v2.dto.marketdata.CryptonitTicker;
import org.knowm.xchange.cryptonit.v2.dto.marketdata.CryptonitTradesJSONTest;
import org.knowm.xchange.currency.CurrencyPair;
import org.knowm.xchange.dto.Order.OrderType;
import org.knowm.xchange.dto.marketdata.Ticker;
import org.knowm.xchange.dto.marketdata.Trades;
import org.knowm.xchange.dto.trade.LimitOrder;
import org.knowm.xchange.utils.DateUtils;

/** Tests the cryptonitAdapter class */
public class CryptonitAdapterTest {

  @Test
  public void testOrderAdapterWithDepth() throws IOException {

    // Read in the JSON from the example resources
    InputStream is =
        CryptonitAdapterTest.class.getResourceAsStream(
            "/org/knowm/xchange/cryptonit/v2/dto/marketdata/example-depth-data.json");

    // Use Jackson to parse it
    ObjectMapper mapper = new ObjectMapper();
    CryptonitOrders cryptonitTrades = mapper.readValue(is, CryptonitOrders.class);

    List<LimitOrder> asks =
        CryptonitAdapters.adaptOrders(cryptonitTrades, CurrencyPair.BTC_USD, "ask", "");

    // Verify all fields filled
    assertEquals(new BigDecimal("604.449"), asks.get(0).getLimitPrice().stripTrailingZeros());
    assertThat(asks.get(0).getType()).isEqualTo(OrderType.ASK);
    assertEquals(new BigDecimal("0.16029"), asks.get(0).getOriginalAmount().stripTrailingZeros());
    assertThat(asks.get(0).getCurrencyPair().base.getCurrencyCode()).isEqualTo("BTC");
    assertThat(asks.get(0).getCurrencyPair().counter.getCurrencyCode()).isEqualTo("USD");
  }

  @Test
  public void testTradeAdapter() throws IOException {

    // Read in the JSON from the example resources
    InputStream is =
        CryptonitTradesJSONTest.class.getResourceAsStream(
            "/org/knowm/xchange/cryptonit/v2/dto/marketdata/example-trades-data.json");

    // Use Jackson to parse it
    ObjectMapper mapper = new ObjectMapper();
    CryptonitOrders cryptonitTrades = mapper.readValue(is, CryptonitOrders.class);

    Trades trades = CryptonitAdapters.adaptTrades(cryptonitTrades, CurrencyPair.BTC_USD);
    assertThat(trades.getTrades().size()).isEqualTo(100);

    // Verify all fields filled
    assertThat(trades.getlastID()).isEqualTo(268133L);
    assertThat(trades.getTrades().get(0).getPrice().doubleValue() == 605.997);
    assertThat(trades.getTrades().get(0).getOriginalAmount().doubleValue() == 1.189100000);
    assertThat(trades.getTrades().get(0).getCurrencyPair().base.getCurrencyCode() == "BTC");
    assertThat(DateUtils.toUTCString(trades.getTrades().get(0).getTimestamp()))
        .isEqualTo("2014-06-20 00:09:10 GMT");
  }

  @Test
  public void testTickerAdapter() throws IOException {

    // Read in the JSON from the example resources
    InputStream is =
        CryptonitAdapterTest.class.getResourceAsStream(
            "/org/knowm/xchange/cryptonit/v2/dto/marketdata/example-ticker-data.json");

    // Use Jackson to parse it
    ObjectMapper mapper = new ObjectMapper();
    CryptonitTicker cryptonitTicker = mapper.readValue(is, CryptonitTicker.class);

    Ticker ticker = CryptonitAdapters.adaptTicker(cryptonitTicker, CurrencyPair.BTC_USD);

    assertThat(ticker.getLast().toString()).isEqualTo("605.997");
    assertThat(ticker.getLow().toString()).isEqualTo("572.73768613");
    assertThat(ticker.getHigh().toString()).isEqualTo("610.00000000");
    assertThat(ticker.getVolume()).isEqualTo(new BigDecimal("8.28600851"));

    assertThat(ticker.getCurrencyPair().base.getCurrencyCode()).isEqualTo("BTC");
  }

  @Test
  public void testAdaptCurrencyPairs() throws IOException {

    // Read in the JSON from the example resources
    InputStream is =
        CryptonitAdapterTest.class.getResourceAsStream(
            "/org/knowm/xchange/cryptonit/v2/dto/marketdata/example-trading-pairs.json");

    // Use Jackson to parse it
    ObjectMapper mapper = new ObjectMapper();
    CollectionLikeType nestedListType =
        mapper
            .getTypeFactory()
            .constructCollectionType(
                List.class,
                mapper.getTypeFactory().constructCollectionType(List.class, String.class));
    List<List<String>> tradingPairs = mapper.readValue(is, nestedListType);

    Collection<CurrencyPair> currencyPairs = CryptonitAdapters.adaptCurrencyPairs(tradingPairs);

    assertThat(currencyPairs).hasSize(3);
    assertThat(currencyPairs.contains(CurrencyPair.BTC_LTC));
  }
}
