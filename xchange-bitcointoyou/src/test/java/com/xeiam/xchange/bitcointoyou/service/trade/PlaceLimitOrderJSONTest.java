package com.xeiam.xchange.bitcointoyou.service.trade;

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
 * Test Transaction[] JSON parsing
 * 
 * @author Felipe Micaroni Lalli
 */
public class PlaceLimitOrderJSONTest {

  @Test
  public void testUnmarshal() throws IOException {

    // Read in the JSON from the example resources
    InputStream is = UserOrdersJSONTest.class.getResourceAsStream("/trade/example-place-limit-order.json");

    // Use Jackson to parse it
    ObjectMapper mapper = new ObjectMapper();
    BitcoinToYouBaseTradeApiResult<BitcoinToYouOrder[]> apiResult = mapper.readValue(is,
        new TypeReference<BitcoinToYouBaseTradeApiResult<BitcoinToYouOrder[]>>() {
        });

    BitcoinToYouOrder[] userOrders = apiResult.getTheReturn();

    // Verify that the example data was unmarshalled correctly
    assertThat(userOrders[0].getAsset()).isEqualTo("BTC");
    assertThat(userOrders[0].getCurrency()).isEqualTo("BRL");
    assertThat(userOrders[0].getId()).isEqualTo(67229L);
    assertThat(userOrders[0].getAction()).isEqualTo("buy");
    assertThat(userOrders[0].getStatus()).isEqualTo("executed");
    assertThat(userOrders[0].getPrice()).isEqualTo(new BigDecimal("990.0000000000"));
    assertThat(userOrders[0].getAmount()).isEqualTo(new BigDecimal("0.00966625"));
    assertThat(userOrders[0].getExecutedPriceAverage()).isEqualTo(new BigDecimal("9.56958750"));
    assertThat(userOrders[0].getExecutedAmount()).isEqualTo(new BigDecimal("0.00966625"));
    assertThat(userOrders[0].getDateCreated()).isEqualTo("2014-10-09 14:05:13.500");
  }

}
