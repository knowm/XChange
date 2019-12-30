package org.knowm.xchange.bitfinex.v2.dto.marketdata;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.Test;

import java.io.IOException;
import java.io.InputStream;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

public class BitfinexTickerJSONTest {
  @Test
  public void testUnmarshal() throws IOException {

    // Read in the JSON from the example resources
    InputStream is =
        BitfinexTickerJSONTest.class.getResourceAsStream(
            "/org/knowm/xchange/bitfinex/v2/dto/marketdata/example-ticker-data.json");

    // Use Jackson to parse it
    ObjectMapper mapper = new ObjectMapper();
    List<BitfinexTicker> tickers =
        mapper.readValue(
            is, mapper.getTypeFactory().constructCollectionType(List.class, BitfinexTicker.class));

    // Verify that the example data was unmarshalled correctly
    BitfinexTicker bitfinexTicker = tickers.get(0);
    assertThat(bitfinexTicker.getSymbol()).isEqualTo("fLEO");
    assertThat(bitfinexTicker.getFrr()).isEqualTo("1.0958904109589042e-08");
    assertThat(bitfinexTicker.getBid()).isEqualTo("0");
    assertThat(bitfinexTicker.getBidPeriod()).isEqualTo("0");
    assertThat(bitfinexTicker.getBidSize()).isEqualTo("0");
    assertThat(bitfinexTicker.getAsk()).isEqualTo("1e-08");
    assertThat(bitfinexTicker.getAskPeriod()).isEqualTo("2");
    assertThat(bitfinexTicker.getAskSize()).isEqualTo("2663861.8810786298");
    assertThat(bitfinexTicker.getDailyChange()).isEqualTo("0");
    assertThat(bitfinexTicker.getDailyChangePerc()).isEqualTo("0");
    assertThat(bitfinexTicker.getLastPrice()).isEqualTo("1e-08");
    assertThat(bitfinexTicker.getVolume()).isEqualTo("664.1085");
    assertThat(bitfinexTicker.getHigh()).isEqualTo("1e-08");
    assertThat(bitfinexTicker.getLow()).isEqualTo("1e-08");
    assertThat(bitfinexTicker.getPlaceHolder0()).isNull();
    assertThat(bitfinexTicker.getPlaceHolder1()).isNull();
    assertThat(bitfinexTicker.getFrrAmountAvailable()).isEqualTo("2594257.74114297");
  }
}
