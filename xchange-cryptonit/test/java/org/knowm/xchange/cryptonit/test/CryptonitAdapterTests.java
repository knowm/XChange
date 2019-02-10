package org.knowm.xchange.cryptonit2.test;

import static org.assertj.core.api.Assertions.assertThat;

import java.io.IOException;
import java.io.InputStream;
import java.math.BigDecimal;

import org.junit.Test;
import org.knowm.xchange.cryptonit2.CryptonitAdapters;
import org.knowm.xchange.cryptonit2.dto.trade.CryptonitOrderStatusResponse;
import org.knowm.xchange.cryptonit2.order.dto.CryptonitGenericOrder;
import org.knowm.xchange.currency.CurrencyPair;

import com.fasterxml.jackson.databind.ObjectMapper;

public class CryptonitAdapterTests {

  @Test
  public void testAdaptMarketOrder() throws IOException {

    // Read in the JSON from the example resources
    InputStream is = CryptonitAdapters.class.getResourceAsStream("/order/example-market-order-filled.json");

    // Use Jackson to parse it
    ObjectMapper mapper = new ObjectMapper();
    CryptonitOrderStatusResponse cryptonitQueryOrderResult = mapper.readValue(is, CryptonitOrderStatusResponse.class);

    CryptonitGenericOrder order = CryptonitAdapters.adaptOrder("123", cryptonitQueryOrderResult);

    assertThat(order.getId()).isEqualTo("123");
    assertThat(order.getAveragePrice()).isEqualTo(new BigDecimal("256.08000000"));
    assertThat(order.getCumulativeAmount()).isEqualTo(new BigDecimal("0.20236360"));
    assertThat(order.getCurrencyPair()).isEqualTo(CurrencyPair.LTC_USD);

  }

}
