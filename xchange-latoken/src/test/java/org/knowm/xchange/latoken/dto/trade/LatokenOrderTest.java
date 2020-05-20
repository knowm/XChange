package org.knowm.xchange.latoken.dto.trade;

import static org.junit.Assert.assertNotNull;

import com.fasterxml.jackson.databind.ObjectMapper;
import java.io.InputStream;
import org.junit.Before;
import org.junit.Test;
import org.knowm.xchange.latoken.dto.account.LatokenBalanceTest;

public class LatokenOrderTest {
  LatokenOrder order;

  @Before
  public void testSetup() throws Exception {
    // Read in the JSON from the example resources
    InputStream is =
        LatokenBalanceTest.class.getResourceAsStream(
            "/org/knowm/xchange/latoken/dto/trade/latoken-order-response.json");

    // Use Jackson to parse it
    ObjectMapper mapper = new ObjectMapper();
    order = mapper.readValue(is, LatokenOrder.class);
  }

  @Test
  public void testLatokenOrder() {
    assertNotNull(order);
  }

  @Test
  public void testGetOrderId() {
    assertNotNull(order.getOrderId());
  }

  @Test
  public void testGetClientOrderId() {
    assertNotNull(order.getClientOrderId());
  }

  @Test
  public void testGetPairId() {
    assertNotNull(order.getPairId());
  }

  @Test
  public void testGetSymbol() {
    assertNotNull(order.getSymbol());
  }

  @Test
  public void testGetSide() {
    assertNotNull(order.getSide());
  }

  @Test
  public void testGetType() {
    assertNotNull(order.getType());
  }

  @Test
  public void testGetPrice() {
    assertNotNull(order.getPrice());
  }

  @Test
  public void testGetAmount() {
    assertNotNull(order.getAmount());
  }

  @Test
  public void testGetOrderStatus() {
    assertNotNull(order.getOrderStatus());
  }

  @Test
  public void testGetExecutedAmount() {
    assertNotNull(order.getAmount());
  }

  @Test
  public void testGetReaminingAmount() {
    assertNotNull(order.getReaminingAmount());
  }

  @Test
  public void testGetTimeCreated() {
    assertNotNull(order.getTimeCreated());
  }

  @Test
  public void testGetTimeFilled() {
    assertNotNull(order.getTimeFilled());
  }

  @Test
  public void testToString() {
    assertNotNull(order.toString());
  }
}
