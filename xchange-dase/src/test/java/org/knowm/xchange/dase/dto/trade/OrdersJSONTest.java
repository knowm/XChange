package org.knowm.xchange.dase.dto.trade;

import static org.assertj.core.api.Assertions.assertThat;

import com.fasterxml.jackson.databind.ObjectMapper;
import java.io.InputStream;
import org.junit.Test;

public class OrdersJSONTest {

  @Test
  public void unmarshal_list() throws Exception {
    InputStream is =
        OrdersJSONTest.class.getResourceAsStream(
            "/org/knowm/xchange/dase/dto/trade/example-orders.json");
    ObjectMapper mapper = new ObjectMapper();
    DaseOrdersListResponse res = mapper.readValue(is, DaseOrdersListResponse.class);

    assertThat(res.getOrders()).isNotNull();
    assertThat(res.getOrders()).isNotEmpty();
    DaseOrder first = res.getOrders().get(0);
    assertThat(first.getId()).isNotBlank();
    assertThat(first.getMarket()).isNotBlank();
  }

  @Test
  public void unmarshal_place_response() throws Exception {
    String json = "{\n  \"order_id\": \"12345678-1234-1234-1234-123456789abc\"\n}";
    ObjectMapper mapper = new ObjectMapper();
    DasePlaceOrderResponse res = mapper.readValue(json, DasePlaceOrderResponse.class);
    assertThat(res.getOrderId()).isEqualTo("12345678-1234-1234-1234-123456789abc");
  }
}
