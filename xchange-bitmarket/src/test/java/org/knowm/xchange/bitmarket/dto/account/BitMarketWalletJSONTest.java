package org.knowm.xchange.bitmarket.dto.account;

import static org.assertj.core.api.Assertions.assertThat;

import java.io.IOException;
import java.math.BigDecimal;
import org.junit.Test;
import org.knowm.xchange.bitmarket.dto.BitMarketDtoTestSupport;

/** @author */
public class BitMarketWalletJSONTest extends BitMarketDtoTestSupport {
  @Test
  public void testUnmarshal() throws IOException {

    // when
    BitMarketAccountInfoResponse response =
        parse(
            "org/knowm/xchange/bitmarket/dto/account/example-info-data",
            BitMarketAccountInfoResponse.class);

    // then
    assertThat(response.getSuccess()).isTrue();
    assertThat(response.getData().getBalance().getAvailable().get("PLN"))
        .isEqualTo(new BigDecimal("4.166000000000"));
    assertThat(response.getData().getBalance().getBlocked().size()).isEqualTo(3);
  }
}
