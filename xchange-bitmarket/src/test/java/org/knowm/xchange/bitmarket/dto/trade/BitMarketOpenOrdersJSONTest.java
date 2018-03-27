package org.knowm.xchange.bitmarket.dto.trade;

import static org.assertj.core.api.Assertions.assertThat;

import java.io.IOException;
import java.math.BigDecimal;
import org.junit.Test;
import org.knowm.xchange.bitmarket.dto.BitMarketDtoTestSupport;

/** @author kfonal */
public class BitMarketOpenOrdersJSONTest extends BitMarketDtoTestSupport {
  @Test
  public void testUnmarshal() throws IOException {
    // when
    BitMarketOrdersResponse response =
        parse(
            "org/knowm/xchange/bitmarket/dto/trade/example-orders-data",
            BitMarketOrdersResponse.class);

    // then
    BitMarketOrder buyOrderBTCPLN = response.getData().get("BTCPLN").get("buy").get(0);
    BitMarketOrder sellOrderBTCPLN = response.getData().get("BTCPLN").get("sell").get(0);

    assertThat(response.getSuccess()).isTrue();
    assertThat(buyOrderBTCPLN.getAmount()).isEqualTo(new BigDecimal("0.20000000"));
    assertThat(buyOrderBTCPLN.getRate()).isEqualTo(new BigDecimal("3000.0000"));
    assertThat(buyOrderBTCPLN.getId()).isEqualTo(31393);
    assertThat(sellOrderBTCPLN.getAmount()).isEqualTo(new BigDecimal("0.08000000"));
    assertThat(sellOrderBTCPLN.getRate()).isEqualTo(new BigDecimal("4140.0000"));
    assertThat(sellOrderBTCPLN.getId()).isEqualTo(31391);
  }
}
