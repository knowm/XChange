package org.knowm.xchange.coinsetter.dto.order.response;

import static org.junit.Assert.assertEquals;

import java.io.IOException;
import java.math.BigDecimal;
import java.util.UUID;

import org.junit.Test;

import org.knowm.xchange.coinsetter.ObjectMapperHelper;

public class CoinsetterOrderTest {

  @Test
  public void test() throws IOException {

    CoinsetterOrder coinsetterOrder = ObjectMapperHelper.readValue(getClass().getResource("order.json"), CoinsetterOrder.class);
    assertEquals(UUID.fromString("5ab97b65-ab90-49f6-b7bc-007ddb27c870"), coinsetterOrder.getUuid());
    assertEquals(UUID.fromString("d0966294-279b-4a08-bc1c-b1d53d97a7a4"), coinsetterOrder.getAccountUuid());
    assertEquals(new BigDecimal("0.000000"), coinsetterOrder.getCostBasis());
    assertEquals(UUID.fromString("7310999e-3fda-44d4-9ee9-c71b6fe94f1c"), coinsetterOrder.getCustomerUuid());
    assertEquals(new BigDecimal("0.00"), coinsetterOrder.getFilledQuantity());
    assertEquals(new BigDecimal("0.00"), coinsetterOrder.getOpenQuantity());
    assertEquals("CS00000017", coinsetterOrder.getOrderNumber());
    assertEquals("MARKET", coinsetterOrder.getOrderType());
    assertEquals("NEW", coinsetterOrder.getStage());
    assertEquals(new BigDecimal("10.00"), coinsetterOrder.getRequestedQuantity());
    assertEquals(new BigDecimal("123.450000"), coinsetterOrder.getRequestedPrice());
    assertEquals("BUY", coinsetterOrder.getSide());
    assertEquals("BTCUSD", coinsetterOrder.getSymbol());
    assertEquals(0, coinsetterOrder.getRoutingMethod());
    assertEquals(1382548976485L, coinsetterOrder.getCreateDate().getTime());
  }

}
