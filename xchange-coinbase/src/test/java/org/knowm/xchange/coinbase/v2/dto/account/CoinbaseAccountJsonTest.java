package org.knowm.xchange.coinbase.v2.dto.account;

import com.fasterxml.jackson.databind.ObjectMapper;
import java.io.IOException;
import java.io.InputStream;
import java.math.BigDecimal;
import java.util.List;
import java.util.stream.Collectors;
import org.junit.Assert;
import org.junit.Test;
import org.knowm.xchange.coinbase.v2.dto.account.CoinbaseAccountData.CoinbaseAccount;

public class CoinbaseAccountJsonTest {

  @Test
  public void testDeserializeAccounts() throws IOException {

    // Read in the JSON from the example resources
    InputStream is =
        CoinbaseAccountJsonTest.class.getResourceAsStream(
            "/org/knowm/xchange/coinbase/dto/account/example-accounts-data.json");

    // Use Jackson to parse it
    ObjectMapper mapper = new ObjectMapper();
    List<CoinbaseAccount> accounts = mapper.readValue(is, CoinbaseAccountsData.class).getData();
    Assert.assertEquals(4, accounts.size());

    CoinbaseAccount btcAccount =
        accounts
            .stream()
            .filter(t -> t.getName().equals("BTC Wallet"))
            .collect(Collectors.toList())
            .get(0);
    Assert.assertEquals("xxx-xxx-xxx-xxx-xxx", btcAccount.getId());
    Assert.assertEquals(new BigDecimal("0.12234387"), btcAccount.getBalance().getAmount());
    Assert.assertEquals("BTC", btcAccount.getBalance().getCurrency());
  }
}
