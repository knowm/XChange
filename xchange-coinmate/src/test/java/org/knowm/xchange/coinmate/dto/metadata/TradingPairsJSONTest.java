package org.knowm.xchange.coinmate.dto.metadata;

import static org.assertj.core.api.Assertions.assertThat;

import com.fasterxml.jackson.databind.ObjectMapper;
import java.io.IOException;
import java.io.InputStream;
import org.junit.Test;

public class TradingPairsJSONTest {
  @Test
  public void testUnmarshal() throws IOException {

    // Read in the JSON from the example resources
    InputStream is =
        TradingPairsJSONTest.class.getResourceAsStream(
            "/org/knowm/xchange/coinmate/dto/metadata/example-tradingpairs.json");

    ObjectMapper mapper = new ObjectMapper();
    CoinmateTradingPairs coinmateTradingPairs = mapper.readValue(is, CoinmateTradingPairs.class);

    // Verify that the example data was unmarshalled correctly
    assertThat(coinmateTradingPairs.getData().size()).isEqualTo(2);
    assertThat(coinmateTradingPairs.getData().get(0).getBaseCurrency()).isEqualTo("BTC");
    assertThat(coinmateTradingPairs.getData().get(0).getCounterCurrency()).isEqualTo("EUR");
    assertThat(coinmateTradingPairs.getData().get(0).getName()).isEqualTo("BTC_EUR");
    assertThat(coinmateTradingPairs.getData().get(0).getMinAmount()).isEqualTo(0.0002);

    assertThat(coinmateTradingPairs.getData().get(0).getPriceScale()).isEqualTo(1);
    assertThat(coinmateTradingPairs.getData().get(0).getVolumeScale()).isEqualTo(8);
    assertThat(coinmateTradingPairs.getData().get(0).getOrderBookWebSocketChannelId())
        .isEqualTo("order_book-BTC_EUR");
    assertThat(coinmateTradingPairs.getData().get(0).getTradeStatisticsWebSocketChannelId())
        .isEqualTo("statistics-BTC_EUR");
    assertThat(coinmateTradingPairs.getData().get(0).getTradesWebSocketChannelId())
        .isEqualTo("trades-BTC_EUR");
  }
}
