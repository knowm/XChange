package com.xeiam.xchange.anx.v2.dto.account;

import static com.xeiam.xchange.currency.Currencies.*;
import static org.fest.assertions.api.Assertions.assertThat;

import java.io.IOException;
import java.io.InputStream;
import java.math.BigDecimal;

import org.junit.Test;

import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.xeiam.xchange.anx.v2.dto.account.polling.ANXAccountInfo;

/**
 * Test BitStamp Full Depth JSON parsing
 */
public class AccountInfoJSONTest {

  @Test
  public void testUnmarshal() throws IOException {

    // Read in the JSON from the example resources
    InputStream is = AccountInfoJSONTest.class.getResourceAsStream("/v2/account/example-accountinfo-data.json");

    // Use Jackson to parse it
    ObjectMapper mapper = new ObjectMapper();
    mapper.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);
    ANXAccountInfo anxAccountInfo = mapper.readValue(is, ANXAccountInfo.class);

    // Verify that the example data was unmarshalled correctly
    assertThat(anxAccountInfo.getLogin()).isEqualTo("test@anxpro.com");

    // Get Balance
    assertThat(anxAccountInfo.getWallets().get(BTC).getBalance().getValue()).isEqualTo(new BigDecimal("100000.01988000"));
    assertThat(anxAccountInfo.getWallets().get(USD).getBalance().getValue()).isEqualTo(new BigDecimal("100000.00000"));
    assertThat(anxAccountInfo.getWallets().get(HKD).getBalance().getValue()).isEqualTo(new BigDecimal("99863.07000"));

    assertThat(anxAccountInfo.getWallets().get(LTC).getBalance().getValue()).isEqualTo(new BigDecimal("100000.00000000"));
    assertThat(anxAccountInfo.getWallets().get(DOGE).getBalance().getValue()).isEqualTo(new BigDecimal("9999781.09457936"));

    // Get Other Balance
    assertThat(anxAccountInfo.getWallets().get(BTC).getMaxWithdraw().getValue()).isEqualTo(new BigDecimal("20.00000000"));
    assertThat(anxAccountInfo.getWallets().get(BTC).getDailyWithdrawLimit().getValue()).isEqualTo(new BigDecimal("20.00000000"));
  }
}
