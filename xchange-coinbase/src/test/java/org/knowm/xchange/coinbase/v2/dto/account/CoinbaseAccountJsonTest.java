package org.knowm.xchange.coinbase.v2.dto.account;

import static org.assertj.core.api.Assertions.assertThat;

import com.fasterxml.jackson.databind.ObjectMapper;
import java.io.InputStream;
import java.math.BigDecimal;
import java.util.List;
import java.util.stream.Collectors;
import org.junit.jupiter.api.Test;
import org.knowm.xchange.coinbase.v2.dto.account.CoinbaseAccountData.CoinbaseAccount;

class CoinbaseAccountJsonTest {

  @Test
  void deserializeAccounts() throws Exception {

    // Read in the JSON from the example resources
    InputStream is =
        CoinbaseAccountJsonTest.class.getResourceAsStream(
            "/org/knowm/xchange/coinbase/dto/account/example-accounts-data.json");

    // Use Jackson to parse it
    ObjectMapper mapper = new ObjectMapper();
    List<CoinbaseAccount> accounts = mapper.readValue(is, CoinbaseAccountsData.class).getData();
    assertThat(accounts.size()).isEqualTo(4);

    CoinbaseAccount btcAccount =
        accounts.stream()
            .filter(t -> t.getName().equals("BTC Wallet"))
            .collect(Collectors.toList())
            .get(0);
    assertThat(btcAccount.getId()).isEqualTo("xxx-xxx-xxx-xxx-xxx");
    assertThat(btcAccount.getBalance().getAmount()).isEqualTo(new BigDecimal("0.12234387"));
    assertThat(btcAccount.getBalance().getCurrency()).isEqualTo("BTC");
  }
}
