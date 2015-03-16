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
 * Test Transaction[] JSON parsing
 * 
 * @author Felipe Micaroni Lalli
 */
public class CancelOrderJSONTest {

  @Test
  public void testUnmarshal() throws IOException {

    // Read in the JSON from the example resources
    InputStream is = UserOrdersJSONTest.class.getResourceAsStream("/trade/example-cancel-order.json");

    // Use Jackson to parse it
    ObjectMapper mapper = new ObjectMapper();
    BitcoinToYouBaseTradeApiResult<BitcoinToYouOrder> apiResult = mapper.readValue(is,
        new TypeReference<BitcoinToYouBaseTradeApiResult<BitcoinToYouOrder>>() {
        });

    BitcoinToYouOrder userOrder = apiResult.getTheReturn();

    // Verify that the example data was unmarshalled correctly
    assertThat(userOrder.getAsset()).isEqualTo("BTC");
    assertThat(userOrder.getCurrency()).isEqualTo("BRL");
    assertThat(userOrder.getId()).isEqualTo(67233L);
    assertThat(userOrder.getAction()).isEqualTo("buy");
    assertThat(userOrder.getStatus()).isEqualTo("canceled");
    assertThat(userOrder.getPrice()).isEqualTo(new BigDecimal("500.9800000000"));
    assertThat(userOrder.getAmount()).isEqualTo(new BigDecimal("0.01000000"));
    assertThat(userOrder.getExecutedPriceAverage()).isEqualTo(new BigDecimal("0.00000000"));
    assertThat(userOrder.getExecutedAmount()).isEqualTo(new BigDecimal("0.00000000"));
    assertThat(userOrder.getDateCreated()).isEqualTo("2014-10-09 14:14:04.543");
  }

}
