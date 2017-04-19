package org.knowm.xchange.mercadobitcoin.dto.trade;

import static org.fest.assertions.api.Assertions.assertThat;

import java.io.IOException;
import java.io.InputStream;
import java.math.BigDecimal;

import org.junit.Test;
import org.knowm.xchange.mercadobitcoin.dto.MercadoBitcoinBaseTradeApiResult;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;

/**
 * Test Transaction[] JSON parsing
 *
 * @author Felipe Micaroni Lalli
 */
public class CancelOrderJSONTest {

  @Test
  public void testUnmarshal() throws IOException {

    // Read in the JSON from the example resources
    InputStream is = CancelOrderJSONTest.class.getResourceAsStream("/trade/example-cancel-order.json");

    // Use Jackson to parse it
    ObjectMapper mapper = new ObjectMapper();
    MercadoBitcoinBaseTradeApiResult<MercadoBitcoinPlaceLimitOrderResult> apiResult = mapper.readValue(is,
        new TypeReference<MercadoBitcoinBaseTradeApiResult<MercadoBitcoinPlaceLimitOrderResult>>() {
        });

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
