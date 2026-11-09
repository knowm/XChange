package org.knowm.xchange.gemini.v1.dto.trade;

import static org.assertj.core.api.Assertions.assertThat;

import com.fasterxml.jackson.databind.ObjectMapper;
import java.io.IOException;
import java.io.InputStream;
import java.math.BigDecimal;
import org.junit.jupiter.api.Test;

class GeminiTradeDataJSONTest {

  /**
   * This test will currently fail since the JSON field "order_id" cannot be matched to a field in
   * GeminiOrderStatusResponse.
   *
   * @throws IOException
   */
  @Test
  void placeOrder() throws Exception {

    InputStream resourceAsStream =
        GeminiTradeDataJSONTest.class.getResourceAsStream(
            "/org/knowm/xchange/gemini/v1/trade/example-place-order-data.json");
    GeminiOrderStatusResponse response =
        new ObjectMapper().readValue(resourceAsStream, GeminiOrderStatusResponse.class);

    assertThat(response.getId()).isEqualTo(4003264);
    assertThat(response.getSymbol()).isEqualTo("btcusd");
    assertThat(response.getPrice()).isEqualTo(new BigDecimal("900.0"));
    assertThat(response.getAvgExecutionPrice()).isEqualTo(new BigDecimal("0.0"));
    assertThat(response.getSide()).isEqualTo("sell");
    assertThat(response.getType()).isEqualTo("exchange limit");
    assertThat(response.getTimestamp()).isEqualTo("1387061558.610016778");
    assertThat(response.isLive()).isTrue();
    assertThat(response.isCancelled()).isFalse();
    assertThat(response.isWasForced()).isFalse();
    assertThat(response.getOriginalAmount()).isEqualTo(new BigDecimal("0.01"));
    assertThat(response.getRemainingAmount()).isEqualTo(new BigDecimal("0.01"));
    assertThat(response.getExecutedAmount()).isEqualTo(new BigDecimal("0.0"));
  }

  @Test
  void cancelOrder() throws Exception {

    InputStream resourceAsStream =
        GeminiTradeDataJSONTest.class.getResourceAsStream(
            "/org/knowm/xchange/gemini/v1/trade/example-cancel-order-data.json");
    GeminiOrderStatusResponse response =
        new ObjectMapper().readValue(resourceAsStream, GeminiOrderStatusResponse.class);

    assertThat(response.getId()).isEqualTo(4003242);
    assertThat(response.getSymbol()).isEqualTo("btcusd");
    assertThat(response.getPrice()).isEqualTo(new BigDecimal("900.0"));
    assertThat(response.getAvgExecutionPrice()).isEqualTo(new BigDecimal("0.0"));
    assertThat(response.getSide()).isEqualTo("sell");
    assertThat(response.getType()).isEqualTo("exchange limit");
    assertThat(response.getTimestamp()).isEqualTo("1387061342.0");
    assertThat(response.isLive()).isFalse();
    assertThat(response.isCancelled()).isTrue();
    assertThat(response.isWasForced()).isFalse();
    assertThat(response.getOriginalAmount()).isEqualTo(new BigDecimal("0.01"));
    assertThat(response.getRemainingAmount()).isEqualTo(new BigDecimal("0.01"));
    assertThat(response.getExecutedAmount()).isEqualTo(new BigDecimal("0.0"));
  }

  @Test
  void openOrders() throws Exception {

    InputStream resourceAsStream =
        GeminiTradeDataJSONTest.class.getResourceAsStream(
            "/org/knowm/xchange/gemini/v1/trade/example-open-orders-data.json");
    GeminiOrderStatusResponse response =
        new ObjectMapper().readValue(resourceAsStream, GeminiOrderStatusResponse.class);

    assertThat(response.getId()).isEqualTo(4003242);
    assertThat(response.getSymbol()).isEqualTo("btcusd");
    assertThat(response.getPrice()).isEqualTo(new BigDecimal("900.0"));
    assertThat(response.getAvgExecutionPrice()).isEqualTo(new BigDecimal("0.0"));
    assertThat(response.getSide()).isEqualTo("sell");
    assertThat(response.getType()).isEqualTo("exchange limit");
    assertThat(response.getTimestamp()).isEqualTo("1387061342.0");
    assertThat(response.isLive()).isTrue();
    assertThat(response.isCancelled()).isFalse();
    assertThat(response.isWasForced()).isFalse();
    assertThat(response.getOriginalAmount()).isEqualTo(new BigDecimal("0.08"));
    assertThat(response.getRemainingAmount()).isEqualTo(new BigDecimal("0.06"));
    assertThat(response.getExecutedAmount()).isEqualTo(new BigDecimal("0.02"));
  }

  @Test
  void pastTrades() throws Exception {

    InputStream resourceAsStream =
        GeminiTradeDataJSONTest.class.getResourceAsStream(
            "/org/knowm/xchange/gemini/v1/trade/example-past-trades-data.json");
    GeminiTradeResponse[] responses =
        new ObjectMapper().readValue(resourceAsStream, GeminiTradeResponse[].class);

    assertThat(responses[0].getPrice()).isEqualTo(new BigDecimal("854.01"));
    assertThat(responses[0].getAmount()).isEqualTo(new BigDecimal("0.0072077"));
    assertThat(responses[0].getTimestamp()).isEqualTo(new BigDecimal("1387057315.0"));
    assertThat(responses[0].getType()).isEqualTo("Sell");

    assertThat(responses[1].getPrice()).isEqualTo(new BigDecimal("857.92"));
    assertThat(responses[1].getAmount()).isEqualTo(new BigDecimal("0.0027923"));
    assertThat(responses[1].getTimestamp()).isEqualTo(new BigDecimal("1387057259.0"));
    assertThat(responses[1].getType()).isEqualTo("Sell");
  }
}
