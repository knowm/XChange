package org.knowm.xchange.quoine.dto.marketdata;

import static org.assertj.core.api.Assertions.assertThat;

import com.fasterxml.jackson.databind.ObjectMapper;
import java.io.IOException;
import java.io.InputStream;
import java.math.BigDecimal;
import org.junit.Test;

/** Test QuoineTicker JSON parsing */
public class QuoineTickerJSONTest {

  @Test
  public void testUnmarshal() throws IOException {

    // Read in the JSON from the example resources
    InputStream is =
        QuoineTickerJSONTest.class.getResourceAsStream(
            "/org/knowm/xchange/quoine/dto/marketdata/example-ticker-data.json");

    // Use Jackson to parse it
    ObjectMapper mapper = new ObjectMapper();
    QuoineProduct quoineTicker = mapper.readValue(is, QuoineProduct.class);

    // Verify that the example data was unmarshalled correctly
    assertThat(quoineTicker.getId()).isEqualTo(1);
    assertThat(quoineTicker.getProductType()).isEqualTo("CASH");
    assertThat(quoineTicker.getCode()).isEqualTo("CASH");
    assertThat(quoineTicker.getName()).isEqualTo(" CASH Trading");
    assertThat(quoineTicker.getMarketAsk()).isEqualTo(new BigDecimal("227.09383"));
    assertThat(quoineTicker.getMarketBid()).isEqualTo(new BigDecimal("226.78383"));
    assertThat(quoineTicker.getIndicator()).isEqualTo(-1);
    assertThat(quoineTicker.getCurrencyPairId()).isEqualTo("1");
    assertThat(quoineTicker.getCurrency()).isEqualTo("USD");
    assertThat(quoineTicker.getCurrencyPairCode()).isEqualTo("BTCUSD");
    assertThat(quoineTicker.getSymbol()).isEqualTo("$");
    assertThat(quoineTicker.getBtcMinimumWithdraw()).isEqualTo(new BigDecimal("0.02"));
    assertThat(quoineTicker.getFiatMinimumWithdraw()).isEqualTo(new BigDecimal("15"));
    assertThat(quoineTicker.getPusherChannel()).isEqualTo("product_cash_btcusd_1");
    assertThat(quoineTicker.getTakerFee()).isEqualTo(new BigDecimal("0.005"));
    assertThat(quoineTicker.getMakerFee()).isEqualTo(new BigDecimal("0.0"));
    assertThat(quoineTicker.getLowMarketBid()).isEqualTo(new BigDecimal("222.71"));
    assertThat(quoineTicker.getHighMarketAsk()).isEqualTo(new BigDecimal("228.92"));
    assertThat(quoineTicker.getVolume24h()).isEqualTo(new BigDecimal("0.16"));
    assertThat(quoineTicker.getLastPrice24h()).isEqualTo(new BigDecimal("227.38976"));
    assertThat(quoineTicker.getCashSpotAsk()).isEqualTo(new BigDecimal("227.09383"));
    assertThat(quoineTicker.getCashSpotBid()).isEqualTo(new BigDecimal("226.78383"));
    assertThat(quoineTicker.getLastTradedPrice()).isEqualTo(new BigDecimal("227.38586"));
  }
}
