package org.knowm.xchange.jubi.dto.account;

import static org.fest.assertions.api.Assertions.assertThat;

import java.io.IOException;
import java.io.InputStream;
import java.math.BigDecimal;
import java.util.Map;

import org.junit.Test;
import org.knowm.xchange.currency.Currency;
import org.knowm.xchange.dto.account.AccountInfo;
import org.knowm.xchange.jubi.JubiAdapters;

import com.fasterxml.jackson.databind.ObjectMapper;

/**
 * Created by Dzf on 2017/7/13.
 */

public class JubiAccountJsonTest {

  @Test
  public void testJubiBalanceAdapter() throws IOException {
    // Read in the JSON from the example resources
    InputStream is = JubiAccountJsonTest.class.getResourceAsStream("/example-balance-data.json");
    //Use Jackson to parse it
    ObjectMapper mapper = new ObjectMapper();
    JubiBalance jubiBalance = mapper.readValue(is, JubiBalance.class);
    //Balance user id
    assertThat(jubiBalance.getUid()).isEqualTo("7");
    //Available funds
    Map<String, BigDecimal> availableFunds = jubiBalance.getAvailableFunds();
    assertThat(availableFunds).hasSize(49);
    assertThat(availableFunds.get("cny")).isEqualTo(new BigDecimal("54.32"));
    //Locked funds
    Map<String, BigDecimal> lockedFunds = jubiBalance.getLockedFunds();
    assertThat(lockedFunds).hasSize(49);
    assertThat(lockedFunds.get("doge")).isEqualTo(new BigDecimal("65.2213212"));
  }

  @Test
  public void testErrorJubiBalanceAdapter() throws IOException {
    // Read in the JSON from the example resources
    InputStream is = JubiAccountJsonTest.class.getResourceAsStream("/example-balance-data_error.json");
    //Use Jackson to parse it
    ObjectMapper mapper = new ObjectMapper();
    JubiBalance jubiBalance = mapper.readValue(is, JubiBalance.class);
    //Result and error code
    assertThat(jubiBalance.getCode()).isEqualTo("100");
    assertThat(jubiBalance.isResult()).isFalse();
    assertThat(jubiBalance.getUid()).isNull();
    //Funds
    Map<String, BigDecimal> availableFunds = jubiBalance.getAvailableFunds();
    assertThat(availableFunds).hasSize(0);
    Map<String, BigDecimal> lockedFunds = jubiBalance.getLockedFunds();
    assertThat(lockedFunds).hasSize(0);
  }

  @Test
  public void testAccountInfoAdapter() throws IOException {
    // Read in the JSON from the example resources
    InputStream is = JubiAccountJsonTest.class.getResourceAsStream("/example-balance-data.json");
    //Use Jackson to parse it
    ObjectMapper mapper = new ObjectMapper();
    JubiBalance jubiBalance = mapper.readValue(is, JubiBalance.class);
    AccountInfo info = JubiAdapters.adaptAccountInfo(jubiBalance, null);
    assertThat(info.getWallet()).isNotNull();
    assertThat(info.getWallet().getId()).isEqualTo("7");
    assertThat(info.getWallet().getBalance(new Currency("CNY")).getAvailable()).isEqualTo(new BigDecimal("54.32"));
    assertThat(info.getWallet().getBalance(new Currency("DOGE")).getFrozen()).isEqualTo(new BigDecimal("65.2213212"));
  }
}
