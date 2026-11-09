package org.knowm.xchange.gemini.v1.dto.marketdata;

import static org.assertj.core.api.Assertions.assertThat;

import com.fasterxml.jackson.databind.ObjectMapper;
import java.io.InputStream;
import java.math.BigDecimal;
import org.junit.jupiter.api.Test;
import org.knowm.xchange.currency.CurrencyPair;
import org.knowm.xchange.dto.Order.OrderType;
import org.knowm.xchange.gemini.v1.GeminiAdapters;

class GeminiMarketDataJSONTest {

  @Test
  void lendbookMarketData() throws Exception {

    InputStream resourceAsStream =
        GeminiMarketDataJSONTest.class.getResourceAsStream(
            "/org/knowm/xchange/gemini/v1/marketdata/example-marketdepth-lendbook-data.json");
    GeminiLendDepth lendDepth =
        new ObjectMapper().readValue(resourceAsStream, GeminiLendDepth.class);

    assertThat(lendDepth.getAsks().length).isEqualTo(50);
    assertThat(lendDepth.getBids().length).isEqualTo(50);
  }

  @Test
  void marketDepth() throws Exception {

    InputStream resourceAsStream =
        GeminiMarketDataJSONTest.class.getResourceAsStream(
            "/org/knowm/xchange/gemini/v1/marketdata/example-marketdepth-data.json");
    GeminiDepth depthRaw = new ObjectMapper().readValue(resourceAsStream, GeminiDepth.class);
    GeminiAdapters.OrdersContainer asksOrdersContainer =
        GeminiAdapters.adaptOrders(depthRaw.getAsks(), CurrencyPair.BTC_EUR, OrderType.ASK);
    GeminiAdapters.OrdersContainer bidsOrdersContainer =
        GeminiAdapters.adaptOrders(depthRaw.getBids(), CurrencyPair.BTC_EUR, OrderType.BID);

    assertThat(asksOrdersContainer.getLimitOrders().get(0).getLimitPrice())
        .isEqualTo(new BigDecimal("851.87"));
    assertThat(bidsOrdersContainer.getLimitOrders().get(0).getLimitPrice())
        .isEqualTo(new BigDecimal("849.59"));

    assertThat(asksOrdersContainer.getTimestamp()).isEqualTo(1387060950000L);
    assertThat(bidsOrdersContainer.getTimestamp()).isEqualTo(1387060435000L);
  }
}
