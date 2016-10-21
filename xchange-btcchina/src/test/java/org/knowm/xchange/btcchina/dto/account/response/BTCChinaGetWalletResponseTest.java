package org.knowm.xchange.btcchina.dto.account.response;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import java.io.IOException;
import java.math.BigDecimal;

import org.junit.Test;
import org.knowm.xchange.btcchina.dto.account.BTCChinaProfile;

import com.fasterxml.jackson.databind.ObjectMapper;

public class BTCChinaGetWalletResponseTest {

  private final ObjectMapper mapper = new ObjectMapper();

  @Test
  public void testGetWallet() throws IOException {

    BTCChinaGetAccountInfoResponse response = mapper.readValue(getClass().getResource("getAccountInfo.json"), BTCChinaGetAccountInfoResponse.class);
    BTCChinaProfile profile = response.getResult().getProfile();
    assertProfile(profile);
  }

  @Test
  public void testUnmarshalGetAccountInfoProfile() throws IOException {

    BTCChinaGetAccountInfoResponse response = mapper.readValue(getClass().getResource("getAccountInfo.json"), BTCChinaGetAccountInfoResponse.class);
    BTCChinaProfile profile = response.getResult().getProfile();
    assertProfile(profile);
  }

  private void assertProfile(BTCChinaProfile profile) {

    assertEquals("xchange", profile.getUsername());
    assertTrue(profile.getTradePasswordEnabled());
    assertFalse(profile.getOtpEnabled());
    assertEquals(new BigDecimal("0"), profile.getTradeFee());
    assertEquals(new BigDecimal("0"), profile.getTradeFee("cnyltc"));
    assertEquals(new BigDecimal("0"), profile.getTradeFee("btcltc"));
    assertEquals(new BigDecimal("10"), profile.getDailyLimit("btc"));
    assertEquals(new BigDecimal("400"), profile.getDailyLimit("ltc"));
    assertEquals("1r56Yu5GsVAS7tnQP9iY7Ey9DEVZ38Ytx", profile.getDepositAddress("btc"));
    assertEquals("", profile.getWithdrawalAddress("btc"));
    assertEquals("LhW2HGPy5j2yFhBZ2uamZxgjbCjvL7iUzG", profile.getDepositAddress("ltc"));
    assertEquals("", profile.getWithdrawalAddress("ltc"));
    assertEquals(3, profile.getApiKeyPermission());
  }
}
