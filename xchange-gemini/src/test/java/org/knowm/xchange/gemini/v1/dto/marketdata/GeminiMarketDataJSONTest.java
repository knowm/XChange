package org.knowm.xchange.gemini.v1.dto.marketdata;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.Assert.assertEquals;

import com.fasterxml.jackson.databind.ObjectMapper;
import java.io.IOException;
import java.io.InputStream;
import java.math.BigDecimal;
import org.junit.Test;
import org.knowm.xchange.currency.CurrencyPair;
import org.knowm.xchange.dto.Order.OrderType;
import org.knowm.xchange.gemini.v1.GeminiAdapters;

public class GeminiMarketDataJSONTest {

  @Test
  public void testLendbookMarketData() throws IOException {

    InputStream resourceAsStream =
        GeminiMarketDataJSONTest.class.getResourceAsStream(
            "/org/knowm/xchange/gemini/v1/marketdata/example-marketdepth-lendbook-data.json");
    GeminiLendDepth lendDepth =
        new ObjectMapper().readValue(resourceAsStream, GeminiLendDepth.class);

    assertEquals(lendDepth.getAsks().length, 50);
    assertEquals(lendDepth.getBids().length, 50);
  }

  @Test
  public void testMarketDepth() throws Exception {

    InputStream resourceAsStream =
        GeminiMarketDataJSONTest.class.getResourceAsStream(
            "/org/knowm/xchange/gemini/v1/marketdata/example-marketdepth-data.json");
    GeminiDepth depthRaw = new ObjectMapper().readValue(resourceAsStream, GeminiDepth.class);
    GeminiAdapters.OrdersContainer asksOrdersContainer =
        GeminiAdapters.adaptOrders(depthRaw.getAsks(), CurrencyPair.BTC_EUR, OrderType.ASK);
    GeminiAdapters.OrdersContainer bidsOrdersContainer =
        GeminiAdapters.adaptOrders(depthRaw.getBids(), CurrencyPair.BTC_EUR, OrderType.BID);

    assertEquals(
        new BigDecimal("851.87"), asksOrdersContainer.getLimitOrders().get(0).getLimitPrice());
    assertEquals(
        new BigDecimal("849.59"), bidsOrdersContainer.getLimitOrders().get(0).getLimitPrice());

    assertThat(asksOrdersContainer.getTimestamp()).isEqualTo(1387060950000L);
    assertThat(bidsOrdersContainer.getTimestamp()).isEqualTo(1387060435000L);
  }
}
