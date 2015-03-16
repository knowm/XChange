package com.xeiam.xchange.bitcointoyou.dto.trade;

import static org.fest.assertions.api.Assertions.assertThat;

import java.io.IOException;
import java.io.InputStream;
import java.math.BigDecimal;

import org.junit.Test;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.xeiam.xchange.bitcointoyou.dto.BitcoinToYouBaseTradeApiResult;
import com.xeiam.xchange.bitcointoyou.dto.trade.BitcoinToYouOrder;

/**
 * Test BitcoinToYouUserOrders JSON parsing
 * 
 * @author Felipe Micaroni Lalli
 */
public class UserOrdersJSONTest {

  @Test
  public void testUnmarshal() throws IOException {

    // Read in the JSON from the example resources
    InputStream is = UserOrdersJSONTest.class.getResourceAsStream("/trade/example-userorders.json");

    // Use Jackson to parse it
    ObjectMapper mapper = new ObjectMapper();
    BitcoinToYouBaseTradeApiResult<BitcoinToYouOrder[]> apiResult = mapper.readValue(is,
        new TypeReference<BitcoinToYouBaseTradeApiResult<BitcoinToYouOrder[]>>() {
        });

    BitcoinToYouOrder[] userOrders = apiResult.getTheReturn();

    // Verify that the example data was unmarshalled correctly
    assertThat(userOrders[0].getAsset()).isEqualTo("BTC");
    assertThat(userOrders[0].getCurrency()).isEqualTo("BRL");
    assertThat(userOrders[0].getId()).isEqualTo(67233L);
    assertThat(userOrders[0].getAction()).isEqualTo("buy");
    assertThat(userOrders[0].getStatus()).isEqualTo("canceled");
    assertThat(userOrders[0].getPrice()).isEqualTo(new BigDecimal("500.9800000000"));
    assertThat(userOrders[0].getAmount()).isEqualTo(new BigDecimal("0.01000000"));
    assertThat(userOrders[0].getExecutedPriceAverage()).isEqualTo(new BigDecimal("0.00000000"));
    assertThat(userOrders[0].getExecutedAmount()).isEqualTo(new BigDecimal("0.00000000"));
    assertThat(userOrders[0].getDateCreated()).isEqualTo("2014-02-20 10:22:30.123");
  }
}
