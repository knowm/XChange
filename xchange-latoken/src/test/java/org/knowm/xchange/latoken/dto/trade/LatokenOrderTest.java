package org.knowm.xchange.latoken.dto.trade;

import static org.assertj.core.api.Assertions.assertThat;

import com.fasterxml.jackson.databind.ObjectMapper;
import java.io.InputStream;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.knowm.xchange.latoken.dto.account.LatokenBalanceTest;

class LatokenOrderTest {
  LatokenOrder order;

  @BeforeEach
  void testSetup() throws Exception {
    // Read in the JSON from the example resources
    InputStream is =
        LatokenBalanceTest.class.getResourceAsStream(
            "/org/knowm/xchange/latoken/dto/trade/latoken-order-response.json");

    // Use Jackson to parse it
    ObjectMapper mapper = new ObjectMapper();
    order = mapper.readValue(is, LatokenOrder.class);
  }

  @Test
  void latokenOrder() {
    assertThat(order).isNotNull();
  }

  @Test
  void getOrderId() {
    assertThat(order.getOrderId()).isNotNull();
  }

  @Test
  void getClientOrderId() {
    assertThat(order.getClientOrderId()).isNotNull();
  }

  @Test
  void getPairId() {
    assertThat(order.getPairId()).isNotNull();
  }

  @Test
  void getSymbol() {
    assertThat(order.getSymbol()).isNotNull();
  }

  @Test
  void getSide() {
    assertThat(order.getSide()).isNotNull();
  }

  @Test
  void getType() {
    assertThat(order.getType()).isNotNull();
  }

  @Test
  void getPrice() {
    assertThat(order.getPrice()).isNotNull();
  }

  @Test
  void getAmount() {
    assertThat(order.getAmount()).isNotNull();
  }

  @Test
  void getOrderStatus() {
    assertThat(order.getOrderStatus()).isNotNull();
  }

  @Test
  void getExecutedAmount() {
    assertThat(order.getAmount()).isNotNull();
  }

  @Test
  void getReaminingAmount() {
    assertThat(order.getReaminingAmount()).isNotNull();
  }

  @Test
  void getTimeCreated() {
    assertThat(order.getTimeCreated()).isNotNull();
  }

  @Test
  void getTimeFilled() {
    assertThat(order.getTimeFilled()).isNotNull();
  }

  @Test
  void testToString() {
    assertThat(order.toString()).isNotNull();
  }
}
