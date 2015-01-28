package com.xeiam.xchange.bitcointoyou.service.account;

import static org.fest.assertions.api.Assertions.assertThat;

import java.io.IOException;
import java.io.InputStream;
import java.math.BigDecimal;

import org.junit.Test;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.xeiam.xchange.bitcointoyou.dto.BitcoinToYouBaseTradeApiResult;
import com.xeiam.xchange.bitcointoyou.dto.account.BitcoinToYouBalance;

/**
 * Test BitcoinToYou Account Info JSON parsing
 * 
 * @author Felipe Micaroni Lalli
 */
public class AccountInfoJSONTest {

  @Test
  public void testUnmarshal() throws IOException {

    // Read in the JSON from the example resources
    InputStream is = AccountInfoJSONTest.class.getResourceAsStream("/account/example-accountinfo-data.json");

    // Use Jackson to parse it
    ObjectMapper mapper = new ObjectMapper();

    BitcoinToYouBaseTradeApiResult<BitcoinToYouBalance[]> apiResult = mapper.readValue(is,
        new TypeReference<BitcoinToYouBaseTradeApiResult<BitcoinToYouBalance[]>>() {
        });

    // Verify that the example data was unmarshalled correctly
    assertThat(apiResult.getSuccess()).isEqualTo(1);
    assertThat(apiResult.getError()).isNull();
    assertThat(apiResult.getTheReturn()).isNotNull();
    assertThat(apiResult.getTheReturn()[0].getCurrency()).isEqualTo("BRL");
    assertThat(apiResult.getTheReturn()[0].getBalanceAvailable()).isEqualTo(new BigDecimal("17628.7309736"));
  }
}
