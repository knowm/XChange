package org.knowm.xchange.gateio.dto.account;

import static org.assertj.core.api.Assertions.assertThat;

import java.io.IOException;
import java.io.InputStream;
import java.math.BigDecimal;
import java.util.Map;

import org.junit.Test;

import com.fasterxml.jackson.databind.ObjectMapper;

public class GateioAccountJsonTest {

  @Test
  public void testDeserializeFunds() throws IOException {

    // Read in the JSON from the example resources
    InputStream is = GateioAccountJsonTest.class.getResourceAsStream("/account/example-funds-data.json");

    // Use Jackson to parse it
    ObjectMapper mapper = new ObjectMapper();
    GateioFunds funds = mapper.readValue(is, GateioFunds.class);

    assertThat(funds.isResult()).isTrue();

    Map<String, BigDecimal> availableFunds = funds.getAvailableFunds();
    assertThat(availableFunds).hasSize(4);
    assertThat(availableFunds.get("FTC")).isEqualTo("0.00003326");

    Map<String, BigDecimal> lockedFunds = funds.getLockedFunds();
    assertThat(lockedFunds).hasSize(1);
    assertThat(lockedFunds.get("LTC")).isEqualTo("0.384");
  }
}
