package org.knowm.xchange.coinsetter.dto.order.response;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNull;

import java.io.IOException;
import java.util.UUID;

import org.junit.Test;

import org.knowm.xchange.coinsetter.ObjectMapperHelper;

public class CoinsetterOrderResponseTest {

  @Test
  public void testSuccess() throws IOException {

    CoinsetterOrderResponse response = ObjectMapperHelper.readValue(getClass().getResource("success.json"), CoinsetterOrderResponse.class);
    assertEquals(UUID.fromString("a8836f1d-53ee-4fbf-882b-577c90a711ff"), response.getUuid());
    assertEquals("OK", response.getMessage());
    assertEquals("CS00000016", response.getOrderNumber());
    assertEquals("SUCCESS", response.getRequestStatus());
  }

  @Test
  public void testInvalidSymbol() throws IOException {

    CoinsetterOrderResponse response = ObjectMapperHelper.readValue(getClass().getResource("invalid-symbol.json"), CoinsetterOrderResponse.class);
    assertNull(response.getUuid());
    assertEquals("Invalid value: symbol", response.getMessage());
    assertNull(response.getOrderNumber());
    assertEquals("FAILURE", response.getRequestStatus());
  }

  @Test
  public void testInvalidPrice() throws IOException {

    CoinsetterOrderResponse response = ObjectMapperHelper.readValue(getClass().getResource("invalid-price.json"), CoinsetterOrderResponse.class);
    assertNull(response.getUuid());
    assertEquals("Invalid value: requestedPrice", response.getMessage());
    assertNull(response.getOrderNumber());
    assertEquals("FAILURE", response.getRequestStatus());
  }

  @Test
  public void testInvalidQuantity() throws IOException {

    CoinsetterOrderResponse response = ObjectMapperHelper.readValue(getClass().getResource("invalid-quantity.json"), CoinsetterOrderResponse.class);
    assertNull(response.getUuid());
    assertEquals("Invalid value: requestedQuantity", response.getMessage());
    assertNull(response.getOrderNumber());
    assertEquals("FAILURE", response.getRequestStatus());
  }

  @Test
  public void testInvalidSide() throws IOException {

    CoinsetterOrderResponse response = ObjectMapperHelper.readValue(getClass().getResource("invalid-side.json"), CoinsetterOrderResponse.class);
    assertNull(response.getUuid());
    assertEquals("Invalid value: side", response.getMessage());
    assertNull(response.getOrderNumber());
    assertEquals("FAILURE", response.getRequestStatus());
  }

}
