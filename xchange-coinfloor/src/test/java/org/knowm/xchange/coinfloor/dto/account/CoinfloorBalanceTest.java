package org.knowm.xchange.coinfloor.dto.account;

import static org.fest.assertions.api.Assertions.assertThat;

import java.io.IOException;
import java.io.InputStream;

import org.junit.Test;

import com.fasterxml.jackson.databind.ObjectMapper;

public class CoinfloorBalanceTest {
  @Test
  public void unmarshalTest() throws IOException {
    InputStream is = getClass().getResourceAsStream("/account/example-balance-btcgbp.json");
    ObjectMapper mapper = new ObjectMapper();
    CoinfloorBalance balance = mapper.readValue(is, CoinfloorBalance.class);

    assertThat(balance.btcBalance).isEqualTo("120.3500");
    assertThat(balance.gbpBalance).isEqualTo("50000.00");
    assertThat(balance.btcReserved).isEqualTo("0");
    assertThat(balance.gbpReserved).isEqualTo("20000.00");
    assertThat(balance.btcAvailable).isEqualTo("120.3500");
    assertThat(balance.gbpAvailable).isEqualTo("0.00");
  }
}
