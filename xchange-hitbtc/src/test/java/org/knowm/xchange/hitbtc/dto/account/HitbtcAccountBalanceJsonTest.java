package org.knowm.xchange.hitbtc.dto.account;

import static org.fest.assertions.api.Assertions.assertThat;

import java.io.IOException;
import java.io.InputStream;
import java.math.BigDecimal;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.junit.Test;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;

public class HitbtcAccountBalanceJsonTest {

  @Test
  public void testUnmarshal() throws IOException {

    // Read in the JSON from the example resources
    InputStream is = HitbtcAccountBalanceJsonTest.class.getResourceAsStream("/marketdata/example-account-balance-data.json");

    // Use Jackson to parse it
    ObjectMapper mapper = new ObjectMapper();
    List<HitbtcBalance> balances = mapper.readValue(is, new TypeReference<List<HitbtcBalance>>() { });

    Map<String, HitbtcBalance> balanceMap = new HashMap<>();
    for (HitbtcBalance hitbtcBalance : balances) {
      balanceMap.put(hitbtcBalance.getCurrency(), hitbtcBalance);
    }

    assertThat(balances).hasSize(118);
    HitbtcBalance balance = balanceMap.get("BTC");

    assertThat(balance.getCurrency()).isEqualTo("BTC");
    assertThat(balance.getReserved()).isEqualTo(BigDecimal.ZERO);
    assertThat(balance.getAvailable()).isEqualTo(new BigDecimal(5497));
  }

}
