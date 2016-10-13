package org.knowm.xchange.coinsetter.dto.trade;

import static org.junit.Assert.assertEquals;

import java.io.IOException;
import java.math.BigDecimal;
import java.util.UUID;

import org.junit.Test;
import org.knowm.xchange.coinsetter.ObjectMapperHelper;

public class CoinsetterOrderStatusTest {

  @Test
  public void test() throws IOException {

    CoinsetterOrderStatus orderStatus = getCoinsetterOrderStatus("order-status.json");
    assertEquals(UUID.fromString("2d0332cf-5170-4c4a-bbf2-5bff9f227c4a"), orderStatus.getUuid());
    assertEquals(UUID.fromString("2d0332cf-5170-4c4a-bbf2-5bff9f227c4b"), orderStatus.getCustomerUuid());
    assertEquals(new BigDecimal("0.05"), orderStatus.getFilledQuantity());
    assertEquals("LIMIT", orderStatus.getOrderType());
    assertEquals("DONE", orderStatus.getStage());
    assertEquals(new BigDecimal("0.05"), orderStatus.getRequestedQuantity());
    assertEquals(new BigDecimal("500"), orderStatus.getRequestedPrice());
    assertEquals("bid", orderStatus.getSide());
    assertEquals("BTCUSD", orderStatus.getSymbol());
    assertEquals("SMART", orderStatus.getExchId());
  }

  private CoinsetterOrderStatus getCoinsetterOrderStatus(String resource) throws IOException {

    CoinsetterOrderStatus coinsetterOrderStatus = ObjectMapperHelper.readValue(getClass().getResource(resource), CoinsetterOrderStatus.class);
    return coinsetterOrderStatus;
  }

}
