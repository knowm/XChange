package org.knowm.xchange.coinsetter.dto.trade;

import static org.junit.Assert.assertEquals;

import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.math.BigDecimal;
import java.nio.charset.Charset;
import java.util.UUID;

import org.junit.Test;

import com.google.gson.Gson;

public class CoinsetterOrderStatusTest {

  private final Gson gson = new Gson();

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

    InputStream inputStream = getClass().getResourceAsStream(resource);
    InputStreamReader reader = new InputStreamReader(inputStream, Charset.forName("UTF-8"));
    try {
      CoinsetterOrderStatus coinsetterOrderStatus = gson.fromJson(reader, CoinsetterOrderStatus.class);
      return coinsetterOrderStatus;
    } finally {
      reader.close();
      inputStream.close();
    }
  }

}
