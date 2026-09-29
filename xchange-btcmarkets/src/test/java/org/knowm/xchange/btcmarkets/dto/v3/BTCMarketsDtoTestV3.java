package org.knowm.xchange.btcmarkets.dto.v3;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.jupiter.api.Test;
import org.knowm.xchange.btcmarkets.dto.v3.trade.BTCMarketsPlaceOrderResponse;
import org.knowm.xchange.btcmarkets.service.BTCMarketsTestSupport;

class BTCMarketsDtoTestV3 extends BTCMarketsTestSupport {

  @Test
  void shouldParsePlaceOrderResponse() throws Exception {
    // when
    final BTCMarketsPlaceOrderResponse response = parse(BTCMarketsPlaceOrderResponse.class, "v3");

    // then
    assertThat(response.orderId).isEqualTo("7524");
  }
}
