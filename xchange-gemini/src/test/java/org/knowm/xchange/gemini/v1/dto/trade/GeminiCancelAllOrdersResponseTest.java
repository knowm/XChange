package org.knowm.xchange.gemini.v1.dto.trade;

import static org.assertj.core.api.Assertions.assertThat;

import com.fasterxml.jackson.databind.ObjectMapper;
import java.io.InputStream;
import org.junit.jupiter.api.Test;

class GeminiCancelAllOrdersResponseTest {
  @Test
  void parseResponse() throws Exception {
    InputStream resourceAsStream =
        GeminiTradeDataJSONTest.class.getResourceAsStream(
            "/org/knowm/xchange/gemini/v1/trade/example-cancel-all-orders-data.json");
    GeminiCancelAllOrdersResponse response =
        new ObjectMapper().readValue(resourceAsStream, GeminiCancelAllOrdersResponse.class);

    assertThat(response.getResult()).isEqualTo("ok");
    assertThat(response.getDetails().getCancelRejects().length).isEqualTo(0);
    assertThat(response.getDetails().getCancelledOrders().length).isEqualTo(3);
    assertThat(response.getDetails().getCancelledOrders()[0]).isEqualTo(330429106);
    assertThat(response.getDetails().getCancelledOrders()[1]).isEqualTo(330429079);
    assertThat(response.getDetails().getCancelledOrders()[2]).isEqualTo(330429082);
  }
}
