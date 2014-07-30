package com.xeiam.xchange.btctrade.dto.account;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertTrue;

import java.io.IOException;

import org.junit.Test;

import com.fasterxml.jackson.databind.ObjectMapper;

public class BTCTradeWalletTest {

  private final ObjectMapper mapper = new ObjectMapper();

  @Test
  public void test() throws IOException {

    BTCTradeWallet wallet = mapper.readValue(getClass().getResource("wallet.json"), BTCTradeWallet.class);
    assertNull(wallet.getResult());
    assertNull(wallet.getMessage());
    assertTrue(wallet.isSuccess());
    assertEquals("MASKED ADDRESS", wallet.getAddress());
  }

}
