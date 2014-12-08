package com.xeiam.xchange.mercadobitcoin.service.trade;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.xeiam.xchange.mercadobitcoin.dto.MercadoBitcoinBaseTradeApiResult;
import com.xeiam.xchange.mercadobitcoin.dto.trade.MercadoBitcoinCancelOrderResult;
import org.junit.Test;

import java.io.IOException;
import java.io.InputStream;
import java.math.BigDecimal;

import static org.fest.assertions.api.Assertions.assertThat;

/**
 * Test MercadoBitcoinUserOrders JSON parsing
 * @author Felipe Micaroni Lalli
 */
public class UserOrdersJSONTest {

  @Test
  public void testUnmarshal() throws IOException {

    // Read in the JSON from the example resources
    InputStream is = UserOrdersJSONTest.class.getResourceAsStream("/trade/example-userorders.json");

    // Use Jackson to parse it
    ObjectMapper mapper = new ObjectMapper();
    MercadoBitcoinBaseTradeApiResult<MercadoBitcoinCancelOrderResult> apiResult = mapper.readValue(is, new TypeReference<MercadoBitcoinBaseTradeApiResult<MercadoBitcoinCancelOrderResult>>() {
    });

    MercadoBitcoinCancelOrderResult userOrders = apiResult.getTheReturn();

    // Verify that the example data was unmarshalled correctly
    assertThat(userOrders.get("1212").getStatus()).isEqualTo("completed");
    assertThat(userOrders.get("1212").getCreated()).isEqualTo(1378929161L);
    assertThat(userOrders.get("1212").getPrice()).isEqualTo(new BigDecimal("6.00000"));
    assertThat(userOrders.get("1212").getVolume()).isEqualTo(new BigDecimal("165.47309607"));
    assertThat(userOrders.get("1212").getPair()).isEqualTo("ltc_brl");
    assertThat(userOrders.get("1212").getType()).isEqualTo("sell");
    assertThat(userOrders.get("1212").getOperations().get("442").getVolume()).isEqualTo(new BigDecimal("30.00000000"));
    assertThat(userOrders.get("1212").getOperations().get("442").getPrice()).isEqualTo(new BigDecimal("6.00000"));
    assertThat(userOrders.get("1212").getOperations().get("442").getRate()).isEqualTo(new BigDecimal("0.70"));
    assertThat(userOrders.get("1212").getOperations().get("442").getCreated()).isEqualTo(1378929161L);

  }
}
