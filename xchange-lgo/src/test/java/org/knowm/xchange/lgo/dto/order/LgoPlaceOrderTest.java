package org.knowm.xchange.lgo.dto.order;

import static org.assertj.core.api.Assertions.assertThat;

import java.math.BigDecimal;
import java.time.Instant;
import org.junit.Test;

public class LgoPlaceOrderTest {

  @Test
  public void serializesToLgoLimitOrderPayload() {
    Instant now = Instant.now();
    LgoPlaceOrder order =
        new LgoPlaceLimitOrder(
            1, "B", "BTC-USD", new BigDecimal("10.5"), new BigDecimal("6001.50"), now);

    String payload = order.toPayload();

    assertThat(payload).isEqualTo("L,B,BTC-USD,10.5,6001.50,gtc," + now.toEpochMilli());
  }

  @Test
  public void serializesToLgoMarketOrderPayload() {
    Instant now = Instant.now();
    LgoPlaceOrder order = new LgoPlaceMarketOrder(1, "S", "BTC-USD", new BigDecimal("10.5"), now);

    String payload = order.toPayload();

    assertThat(payload).isEqualTo("M,S,BTC-USD,10.5,,," + now.toEpochMilli());
  }

  @Test
  public void serializesToLgoCancelOrderPayload() {
    Instant now = Instant.now();
    LgoPlaceCancelOrder order = new LgoPlaceCancelOrder(1, "orderId", now);

    String payload = order.toPayload();

    assertThat(payload).isEqualTo("C,orderId," + now.toEpochMilli());
  }
}
