package org.knowm.xchange.mercadobitcoin.dto.account;

import static org.fest.assertions.api.Assertions.assertThat;

import java.io.IOException;
import java.io.InputStream;
import java.math.BigDecimal;

import org.junit.Test;
import org.knowm.xchange.mercadobitcoin.dto.MercadoBitcoinBaseTradeApiResult;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;

/**
 * Test Mercado Bitcoin Account Info JSON parsing
 *
 * @author Felipe Micaroni Lalli
 */
public class WalletJSONTest {

  @Test
  public void testUnmarshal() throws IOException {

    // Read in the JSON from the example resources
    InputStream is = WalletJSONTest.class.getResourceAsStream("/account/example-accountinfo-data.json");

    // Use Jackson to parse it
    ObjectMapper mapper = new ObjectMapper();

    MercadoBitcoinBaseTradeApiResult<MercadoBitcoinAccountInfo> accountInfo = mapper.readValue(is,
        new TypeReference<MercadoBitcoinBaseTradeApiResult<MercadoBitcoinAccountInfo>>() {
        });

    // Verify that the example data was unmarshalled correctly
    assertThat(accountInfo.getSuccess()).isEqualTo(1);
    assertThat(accountInfo.getError()).isNull();
    assertThat(accountInfo.getTheReturn()).isNotNull();
    assertThat(accountInfo.getTheReturn().getServerTime()).isEqualTo(1417409950);
    assertThat(accountInfo.getTheReturn().getOpenOrders()).isEqualTo(0);
    assertThat(accountInfo.getTheReturn().getFunds().getBrl()).isEqualTo(new BigDecimal("248.29516"));
    assertThat(accountInfo.getTheReturn().getFunds().getBtc()).isEqualTo(new BigDecimal("0.25000000"));
    assertThat(accountInfo.getTheReturn().getFunds().getLtc()).isEqualTo(new BigDecimal("0.00000000"));
  }
}
