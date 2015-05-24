package com.xeiam.xchange.bitmarket;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.xeiam.xchange.bitmarket.dto.account.BitMarketAccountInfoResponse;
import com.xeiam.xchange.currency.Currencies;
import com.xeiam.xchange.dto.account.AccountInfo;
import org.junit.Test;

import java.io.IOException;
import java.io.InputStream;
import java.math.BigDecimal;

import static org.fest.assertions.api.Assertions.assertThat;

/**
 * @author kfonal
 */
public class BitMarketAdaptersTest {
  @Test
  public void testAccountInfoAdapter() throws IOException {

    // Read in the JSON from the example resources
    InputStream is = BitMarketAdaptersTest.class.getResourceAsStream("/account/example-info-data.json");

    // Use Jackson to parse it
    ObjectMapper mapper = new ObjectMapper();
    BitMarketAccountInfoResponse response = mapper.readValue(is, BitMarketAccountInfoResponse.class);

    AccountInfo accountInfo = BitMarketAdapters.adaptAccountInfo(response.getData().getBalance(), "Jan Kowalski");
    assertThat(accountInfo.getUsername()).isEqualTo("Jan Kowalski");
    assertThat(accountInfo.getTradingFee()).isNull();
    assertThat(accountInfo.getWallet(Currencies.PLN).getCurrency()).isEqualTo("PLN");
    assertThat(accountInfo.getWallet(Currencies.PLN).getAvailable().toString()).isEqualTo("4.166000000000");
    assertThat(accountInfo.getWallet(Currencies.BTC).getCurrency()).isEqualTo("BTC");
    assertThat(accountInfo.getWallet(Currencies.BTC).getBalance().toString()).isEqualTo("0.029140000000");
    assertThat(accountInfo.getWallet(Currencies.BTC).getAvailable().toString()).isEqualTo("0.029140000000");
    assertThat(accountInfo.getWallet(Currencies.BTC).getFrozen().toString()).isEqualTo("0");
    assertThat(accountInfo.getWallet(Currencies.LTC).getCurrency()).isEqualTo("LTC");
  }
}
