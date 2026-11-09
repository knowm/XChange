package org.knowm.xchange.paymium.dto.marketdata;

import static org.assertj.core.api.Assertions.assertThat;

import com.fasterxml.jackson.databind.ObjectMapper;
import java.io.InputStream;
import java.math.BigDecimal;
import org.junit.jupiter.api.Test;

class MarketDataJSONTest {

  @Test
  void paymiumTickerRequest() throws Exception {

    // Read in the JSON from the example resources
    InputStream is =
        MarketDataJSONTest.class.getResourceAsStream(
            "/org/knowm/xchange/paymium/dto/marketdata/Example_TickerData.json");

    // Use Jackson to parse it
    ObjectMapper mapper = new ObjectMapper();
    PaymiumTicker PaymiumTicker = mapper.readValue(is, PaymiumTicker.class);

    assertThat(PaymiumTicker.getCurrency()).isEqualTo("EUR");
    assertThat(new BigDecimal("20.4")).isEqualTo(PaymiumTicker.getAsk());
    assertThat(new BigDecimal("20.1")).isEqualTo(PaymiumTicker.getBid());
    assertThat(new BigDecimal("20.74")).isEqualTo(PaymiumTicker.getHigh());
    assertThat(new BigDecimal("20.2")).isEqualTo(PaymiumTicker.getLow());
    assertThat(new BigDecimal("20.2")).isEqualTo(PaymiumTicker.getPrice());
    assertThat(new BigDecimal("148.80193218")).isEqualTo(PaymiumTicker.getVolume());
  }

  @Test
  void paymiumDepthRequest() throws Exception {

    // Read in the JSON from the example resources
    InputStream is =
        MarketDataJSONTest.class.getResourceAsStream(
            "/org/knowm/xchange/paymium/dto/marketdata/Example_DepthData.json");

    // Use Jackson to parse it
    ObjectMapper mapper = new ObjectMapper();
    PaymiumMarketDepth PaymiumMarketDepth = mapper.readValue(is, PaymiumMarketDepth.class);

    assertThat(new BigDecimal("0.48762"))
        .isEqualTo(PaymiumMarketDepth.getAsks().get(0).getAmount());
    assertThat(new BigDecimal("24.48996"))
        .isEqualTo(PaymiumMarketDepth.getAsks().get(0).getPrice());
    assertThat(new BigDecimal("0.77372456"))
        .isEqualTo(PaymiumMarketDepth.getBids().get(0).getAmount());
    assertThat(new BigDecimal("24.05")).isEqualTo(PaymiumMarketDepth.getBids().get(0).getPrice());
  }

  @Test
  void paymiumTradesRequest() throws Exception {

    // Read in the JSON from the example resources
    InputStream is =
        MarketDataJSONTest.class.getResourceAsStream(
            "/org/knowm/xchange/paymium/dto/marketdata/Example_TradesData.json");

    // Use Jackson to parse it
    ObjectMapper mapper = new ObjectMapper();
    PaymiumTrade[] PaymiumTrades = mapper.readValue(is, PaymiumTrade[].class);

    assertThat(new BigDecimal("5.0")).isEqualTo(PaymiumTrades[0].getPrice());
    assertThat(new BigDecimal("980.0")).isEqualTo(PaymiumTrades[0].getTraded_btc());
    assertThat(new BigDecimal("4940.0")).isEqualTo(PaymiumTrades[0].getTraded_currency());
  }
}
