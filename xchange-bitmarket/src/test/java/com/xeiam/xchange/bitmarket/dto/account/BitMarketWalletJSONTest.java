package com.xeiam.xchange.bitmarket.dto.account;

import com.xeiam.xchange.bitmarket.dto.BitMarketDtoTestSupport;
import org.junit.Test;

import java.io.IOException;
import java.math.BigDecimal;

import static org.fest.assertions.api.Assertions.assertThat;

/**
 * @author
 */
public class BitMarketWalletJSONTest extends BitMarketDtoTestSupport {
  @Test
  public void testUnmarshal() throws IOException {

    // when
    BitMarketAccountInfoResponse response = parse("account/example-info-data", BitMarketAccountInfoResponse.class);

    // then
    assertThat(response.getSuccess()).isTrue();
    assertThat(response.getData().getBalance().getAvailable().get("PLN")).isEqualTo(new BigDecimal("4.166000000000"));
    assertThat(response.getData().getBalance().getBlocked().size()).isEqualTo(3);
  }
}
