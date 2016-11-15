package org.knowm.xchange.taurus.dto.trade;

import static org.fest.assertions.api.Assertions.assertThat;

import java.io.IOException;
import java.io.InputStream;
import java.math.BigDecimal;
import java.text.SimpleDateFormat;
import java.util.TimeZone;

import org.junit.Test;
import org.knowm.xchange.dto.Order;

import com.fasterxml.jackson.databind.ObjectMapper;

/**
 * Test Transaction[] JSON parsing
 */
public class OpenOrdersJSONTest {

  @Test
  public void testUnmarshal() throws IOException {

    // Read in the JSON from the example resources
    InputStream is = OpenOrdersJSONTest.class.getResourceAsStream("/trade/example-openorders.json");

    // Use Jackson to parse it
    ObjectMapper mapper = new ObjectMapper();
    TaurusOrder[] orders = mapper.readValue(is, TaurusOrder[].class);

    assertThat(orders.length).isEqualTo(1);

    //    [{"amount":"0.01000000","datetime":"2015-03-25 09:31:36","id":"musi0joa54mzpj0vvpo811mr53g6cj4zewieg7plccl2wlxrbm0cnm3tqkz3343i","price":"400.00","status":"0","type":"1"}]
    // Verify that the example data was unmarshalled correctly
    assertThat(orders[0].getId()).isEqualTo("musi0joa54mzpj0vvpo811mr53g6cj4zewieg7plccl2wlxrbm0cnm3tqkz3343i");
    assertThat(orders[0].getPrice()).isEqualTo(new BigDecimal("400.00"));
    assertThat(orders[0].getAmount()).isEqualTo(new BigDecimal("0.01000000"));
    assertThat(orders[0].getType()).isEqualTo(Order.OrderType.ASK);
    assertThat(orders[0].getStatus()).isEqualTo(TaurusOrder.Status.active);
    final SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
    format.setTimeZone(TimeZone.getTimeZone("Europe/Paris")); // The json date is UTC; Paris is UTC+1.
    assertThat(format.format(orders[0].getDatetime())).isEqualTo("2015-03-25 10:31:36");
  }
}
