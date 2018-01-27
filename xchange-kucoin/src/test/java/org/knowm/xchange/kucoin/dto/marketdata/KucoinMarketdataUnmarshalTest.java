
package org.knowm.xchange.kucoin.dto.marketdata;

import static org.assertj.core.api.Assertions.assertThat;

import java.io.IOException;
import java.io.InputStream;
import java.math.BigDecimal;

import org.junit.Test;

import com.fasterxml.jackson.databind.ObjectMapper;

/**
 * Test Marketdata JSON parsing
 */
public class KucoinMarketdataUnmarshalTest {

  private ObjectMapper mapper = new ObjectMapper();

  @Test
  public void testCoinUnmarshal() throws IOException {

    // Read in the JSON from the example resources
    InputStream is = KucoinMarketdataUnmarshalTest.class.getResourceAsStream("/marketdata/example-coin.json");
    KucoinCoin coin = mapper.readValue(is, KucoinCoin.class);

    // Verify that the example data was unmarshalled correctly
    assertThat(coin.getWithdrawMinFee()).isEqualTo(BigDecimal.valueOf(0.05));
    assertThat(coin.getWithdrawMinAmount()).isEqualTo(BigDecimal.valueOf(0.1));
    assertThat(coin.getWithdrawFeeRate()).isEqualTo(BigDecimal.valueOf(0.001));
    assertThat(coin.getConfirmationCount()).isEqualTo(12);
    assertThat(coin.getWithdrawRemark()).isEqualTo("");
    assertThat(coin.getInfoUrl()).isNull();
    assertThat(coin.getName()).isEqualTo("RaiBlocks");
    assertThat(coin.getTradePrecision()).isEqualTo(6);
    assertThat(coin.getDepositRemark()).isEqualTo("");
    assertThat(coin.getEnableWithdraw()).isTrue();
    assertThat(coin.getEnableDeposit()).isTrue();
    assertThat(coin.getCoin()).isEqualTo("XRB");
  }

}