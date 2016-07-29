package org.knowm.xchange.bter.dto.account;

import static org.fest.assertions.api.Assertions.assertThat;

import java.io.IOException;
import java.io.InputStream;
import java.math.BigDecimal;
import java.util.Map;

import org.junit.Test;

import com.fasterxml.jackson.databind.ObjectMapper;

public class BTERAccountJsonTest {

  @Test
  public void testDeserializeFunds() throws IOException {

    // Read in the JSON from the example resources
    InputStream is = BTERAccountJsonTest.class.getResourceAsStream("/account/example-funds-data.json");

    // Use Jackson to parse it
    ObjectMapper mapper = new ObjectMapper();
    BTERFunds funds = mapper.readValue(is, BTERFunds.class);

    assertThat(funds.isResult()).isTrue();

    Map<String, BigDecimal> availableFunds = funds.getAvailableFunds();
    assertThat(availableFunds).hasSize(4);
    assertThat(availableFunds.get("FTC")).isEqualTo("0.00003326");

    Map<String, BigDecimal> lockedFunds = funds.getLockedFunds();
    assertThat(lockedFunds).hasSize(1);
    assertThat(lockedFunds.get("LTC")).isEqualTo("0.384");
  }
}
