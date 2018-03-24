package org.knowm.xchange.taurus.dto.account;

import static org.assertj.core.api.Assertions.assertThat;

import java.io.IOException;
import java.io.InputStream;
import java.math.BigDecimal;

import org.junit.Test;

import com.fasterxml.jackson.databind.ObjectMapper;

/**
 * Test Taurus Full Depth JSON parsing
 */
public class WalletJSONTest {

  @Test
  public void testUnmarshal() throws IOException {

    // Read in the JSON from the example resources
    InputStream is = WalletJSONTest.class.getResourceAsStream("/account/example-accountinfo-data.json");

    // Use Jackson to parse it
    ObjectMapper mapper = new ObjectMapper();
    TaurusBalance taurusBalance = mapper.readValue(is, TaurusBalance.class);

    // Verify that the example data was unmarshalled correctly
    //    {"btc_available":"0.02350921","btc_reserved":"0.00000000","btc_balance":"0.02350921","cad_available":"6.16","cad_reserved":"0.00","cad_balance":"6.16","fee":"0.5000"}
    assertThat(taurusBalance.getBtcAvailable()).isEqualTo(new BigDecimal("0.02350921"));
    assertThat(taurusBalance.getBtcReserved()).isEqualTo(new BigDecimal("0.00000000"));
    assertThat(taurusBalance.getBtcBalance()).isEqualTo(new BigDecimal("0.02350921"));
    assertThat(taurusBalance.getCadAvailable()).isEqualTo(new BigDecimal("6.16"));
    assertThat(taurusBalance.getCadBalance()).isEqualTo(new BigDecimal("6.16"));
    assertThat(taurusBalance.getCadReserved()).isEqualTo(new BigDecimal("0.00"));
    assertThat(taurusBalance.getFee()).isEqualTo(new BigDecimal("0.5000"));
  }
}
