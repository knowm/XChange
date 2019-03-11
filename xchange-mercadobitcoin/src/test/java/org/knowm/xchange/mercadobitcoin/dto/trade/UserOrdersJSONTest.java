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
 * Test MercadoBitcoinUserOrders JSON parsing
 *
 * @author Felipe Micaroni Lalli
 */
public class UserOrdersJSONTest {

  @Test
  public void testUnmarshal() throws IOException {

    // Read in the JSON from the example resources
    InputStream is =
        UserOrdersJSONTest.class.getResourceAsStream(
            "/org/knowm/xchange/mercadobitcoin/dto/trade/example-userorders.json");

    // Use Jackson to parse it
    ObjectMapper mapper = new ObjectMapper();
    MercadoBitcoinBaseTradeApiResult<MercadoBitcoinUserOrders> apiResult =
        mapper.readValue(
            is, new TypeReference<MercadoBitcoinBaseTradeApiResult<MercadoBitcoinUserOrders>>() {});

    MercadoBitcoinUserOrders userOrders = apiResult.getTheReturn();

    // Verify that the example data was unmarshalled correctly
    assertThat(userOrders.get("1212").getStatus()).isEqualTo("completed");
    assertThat(userOrders.get("1212").getCreated()).isEqualTo(1378929161L);
    assertThat(userOrders.get("1212").getPrice()).isEqualTo(new BigDecimal("6.00000"));
    assertThat(userOrders.get("1212").getVolume()).isEqualTo(new BigDecimal("165.47309607"));
    assertThat(userOrders.get("1212").getPair()).isEqualTo("ltc_brl");
    assertThat(userOrders.get("1212").getType()).isEqualTo("sell");
    assertThat(userOrders.get("1212").getOperations().get("442").getVolume())
        .isEqualTo(new BigDecimal("30.00000000"));
    assertThat(userOrders.get("1212").getOperations().get("442").getPrice())
        .isEqualTo(new BigDecimal("6.00000"));
    assertThat(userOrders.get("1212").getOperations().get("442").getRate())
        .isEqualTo(new BigDecimal("0.70"));
    assertThat(userOrders.get("1212").getOperations().get("442").getCreated())
        .isEqualTo(1378929161L);
  }
}
