package org.knowm.xchange.campbx.dto;

import static org.assertj.core.api.Assertions.assertThat;

import com.fasterxml.jackson.databind.ObjectMapper;
import java.io.IOException;
import java.math.BigDecimal;
import java.util.List;
import org.junit.Test;
import org.knowm.xchange.campbx.dto.trade.MyOpenOrders;

/** Test BitStamp Full Depth JSON parsing */
public class MyOrdersJSONTest {

  @Test
  public void testUnmarshal() throws IOException {

    MyOpenOrders orderBook =
        new ObjectMapper()
            .readValue(
                MyOrdersJSONTest.class.getResourceAsStream(
                    "/org/knowm/xchange/campbx/trade/open-orders.json"),
                MyOpenOrders.class);

    // Verify that the example data was unmarshalled correctly
    List<CampBXOrder> buy = orderBook.getBuy();
    assertThat(buy.size()).isEqualTo(1);
    assertThat(buy.get(0).getInfo()).isNotNull();
    assertThat(buy.get(0).getPrice()).isNull();

    List<CampBXOrder> sell = orderBook.getSell();
    CampBXOrder order = sell.get(0);
    assertThat(order.getPrice()).isEqualTo(new BigDecimal("110.00"));
    assertThat(order.getOrderID()).isEqualTo("599254");
    assertThat(order.getQuantity()).isEqualTo(new BigDecimal("0.10000000"));
    assertThat(order.getDarkPool()).isFalse();
    assertThat(order.getOrderType()).isEqualTo("Quick Sell");
    assertThat(order.getOrderExpiry().toString()).contains("2013");
    assertThat(order.getOrderExpiry().toString()).contains("29");
  }
}
