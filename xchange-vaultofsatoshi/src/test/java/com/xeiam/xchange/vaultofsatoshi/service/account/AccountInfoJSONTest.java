package com.xeiam.xchange.vaultofsatoshi.service.account;

import static org.fest.assertions.api.Assertions.assertThat;

import java.io.IOException;
import java.io.InputStream;
import java.math.BigDecimal;

import org.junit.Test;

import com.fasterxml.jackson.databind.JavaType;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.xeiam.xchange.vaultofsatoshi.dto.account.VosAccount;
import com.xeiam.xchange.vaultofsatoshi.dto.marketdata.VosCurrency;
import com.xeiam.xchange.vaultofsatoshi.dto.marketdata.VosResponse;

/**
 * Test BitStamp Full Depth JSON parsing
 */
public class AccountInfoJSONTest {

  @Test
  public void testUnmarshal() throws IOException {

    // Read in the JSON from the example resources
    InputStream is = AccountInfoJSONTest.class.getResourceAsStream("/account/account.json");

    // Use Jackson to parse it
    ObjectMapper mapper = new ObjectMapper();
    JavaType type = mapper.getTypeFactory().constructParametricType(VosResponse.class, VosAccount.class);
    VosResponse<VosAccount> vosAccount = mapper.readValue(is, type);

    // Verify that the example data was unmarshalled correctly
    assertThat(vosAccount.getData().getWallets().getVosMap().get("BTC").getBalance().getValue()).isEqualTo(new BigDecimal("0.61305182"));
    assertThat(vosAccount.getData().getTrade_fee().getVosMap().get("DOGE").getValue()).isEqualTo(new BigDecimal("0.00400"));
    assertThat(vosAccount.getData().getRights()[0]).isEqualTo("getinfo");

    // Read in the JSON from the example resources
    is = AccountInfoJSONTest.class.getResourceAsStream("/account/balance_btc.json");

    // Use Jackson to parse it
    JavaType type2 = mapper.getTypeFactory().constructParametricType(VosResponse.class, VosCurrency.class);
    VosResponse<VosCurrency> vosBalance = mapper.readValue(is, type2);

    // Verify that the example data was unmarshalled correctly
    assertThat(vosBalance.getData().getValue()).isEqualTo(new BigDecimal("0.61305182"));

  }
}
