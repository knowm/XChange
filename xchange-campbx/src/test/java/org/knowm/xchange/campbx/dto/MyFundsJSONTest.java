package org.knowm.xchange.campbx.dto;

import static org.fest.assertions.api.Assertions.assertThat;

import java.io.IOException;
import java.math.BigDecimal;

import org.junit.Test;
import org.knowm.xchange.campbx.dto.account.MyFunds;

import com.fasterxml.jackson.databind.ObjectMapper;

/**
 * Test BitStamp Full Depth JSON parsing
 */
public class MyFundsJSONTest {

  @Test
  public void testUnmarshal() throws IOException {

    MyFunds myFunds = new ObjectMapper().readValue(MyFundsJSONTest.class.getResourceAsStream("/account/myfunds.json"), MyFunds.class);

    // Verify that the example data was unmarshalled correctly
    assertThat(myFunds.getLiquidUSD()).isEqualTo(new BigDecimal("0.00"));
    assertThat(myFunds.getTotalBTC()).isEqualTo(new BigDecimal("0.10000000"));
  }
}
