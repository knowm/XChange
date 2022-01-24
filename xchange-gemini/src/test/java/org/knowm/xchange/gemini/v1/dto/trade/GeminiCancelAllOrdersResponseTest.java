package org.knowm.xchange.gemini.v1.dto.trade;

import static org.junit.Assert.assertEquals;

import com.fasterxml.jackson.databind.ObjectMapper;
import java.io.InputStream;
import org.junit.Test;

public class GeminiCancelAllOrdersResponseTest {
  @Test
  public void testParseResponse() throws Exception {
    InputStream resourceAsStream =
        GeminiTradeDataJSONTest.class.getResourceAsStream(
            "/org/knowm/xchange/gemini/v1/trade/example-cancel-all-orders-data.json");
    GeminiCancelAllOrdersResponse response =
        new ObjectMapper().readValue(resourceAsStream, GeminiCancelAllOrdersResponse.class);

    assertEquals("ok", response.getResult());
    assertEquals(0, response.getDetails().getCancelRejects().length);
    assertEquals(3, response.getDetails().getCancelledOrders().length);
    assertEquals(330429106, response.getDetails().getCancelledOrders()[0]);
    assertEquals(330429079, response.getDetails().getCancelledOrders()[1]);
    assertEquals(330429082, response.getDetails().getCancelledOrders()[2]);
  }
}
