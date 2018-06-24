package org.knowm.xchange.mercadobitcoin.dto.trade;

import static org.assertj.core.api.Assertions.assertThat;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import java.io.IOException;
import java.io.InputStream;
import java.math.BigDecimal;
import org.junit.Test;
import org.knowm.xchange.mercadobitcoin.dto.MercadoBitcoinBaseTradeApiResult;

/**
 * Test Transaction[] JSON parsing
 *
 * @author Felipe Micaroni Lalli
 */
public class PlaceLimitOrderJSONTest {

  @Test
  public void testUnmarshal() throws IOException {

    // Read in the JSON from the example resources
    InputStream is =
        PlaceLimitOrderJSONTest.class.getResourceAsStream(
            "/org/knowm/xchange/mercadobitcoin/dto/trade/example-place-limit-order.json");

    // Use Jackson to parse it
    ObjectMapper mapper = new ObjectMapper();
    MercadoBitcoinBaseTradeApiResult<MercadoBitcoinPlaceLimitOrderResult> apiResult =
        mapper.readValue(
            is,
            new TypeReference<
                MercadoBitcoinBaseTradeApiResult<MercadoBitcoinPlaceLimitOrderResult>>() {});

    MercadoBitcoinPlaceLimitOrderResult limitOrder = apiResult.getTheReturn();

    assertThat(limitOrder.get("27176").getStatus()).isEqualTo("canceled");
    assertThat(limitOrder.get("27176").getCreated()).isEqualTo(1381414719L);
    assertThat(limitOrder.get("27176").getPrice()).isEqualTo(new BigDecimal("400.00000"));
    assertThat(limitOrder.get("27176").getVolume()).isEqualTo(new BigDecimal("0.50000000"));
    assertThat(limitOrder.get("27176").getPair()).isEqualTo("btc_brl");
    assertThat(limitOrder.get("27176").getType()).isEqualTo("sell");
    assertThat(limitOrder.get("27176").getOperations().size()).isEqualTo(0);
  }
}
