package org.knowm.xchange.gatecoin.dto.account;

import static org.assertj.core.api.Assertions.assertThat;

import com.fasterxml.jackson.databind.ObjectMapper;
import java.io.IOException;
import java.io.InputStream;
import java.math.BigDecimal;
import org.junit.Test;
import org.knowm.xchange.gatecoin.dto.account.Results.GatecoinBalanceResult;

public class WalletJSONTest {

  @Test
  public void testUnmarshal() throws IOException {

    // Read in the JSON from the example resources
    InputStream is =
        WalletJSONTest.class.getResourceAsStream(
            "/org/knowm/xchange/gatecoin/dto/account/example-accountinfo-data.json");

    // Use Jackson to parse it
    ObjectMapper mapper = new ObjectMapper();
    GatecoinBalanceResult gatecoinBalanceResult = mapper.readValue(is, GatecoinBalanceResult.class);
    GatecoinBalance gatecoinBalance[] = gatecoinBalanceResult.getBalances();

    // Verify that the example data was unmarshalled correctly

    assertThat(gatecoinBalanceResult.getResponseStatus().getMessage()).isEqualTo("OK");

    assertThat(gatecoinBalance[0].getCurrency()).isEqualTo("BTC");
    assertThat(gatecoinBalance[0].getBalance()).isEqualTo(BigDecimal.valueOf(2.94137538));
    assertThat(gatecoinBalance[0].getAvailableBalance()).isEqualTo(BigDecimal.valueOf(2.94137538));
    assertThat(gatecoinBalance[0].getOpenOrder()).isEqualTo(BigDecimal.valueOf(0));
  }
}
