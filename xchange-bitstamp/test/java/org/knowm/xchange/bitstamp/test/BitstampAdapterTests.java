package org.knowm.xchange.bitstamp.test;

import static org.assertj.core.api.Assertions.assertThat;

import java.io.IOException;
import java.io.InputStream;
import java.math.BigDecimal;

import org.junit.Test;
import org.knowm.xchange.bitstamp.BitstampAdapters;
import org.knowm.xchange.bitstamp.dto.trade.BitstampOrderStatusResponse;
import org.knowm.xchange.bitstamp.order.dto.BitstampGenericOrder;
import org.knowm.xchange.currency.CurrencyPair;

import com.fasterxml.jackson.databind.ObjectMapper;

public class BitstampAdapterTests {

  @Test
  public void testAdaptMarketOrder() throws IOException {

    // Read in the JSON from the example resources
    InputStream is = BitstampAdapters.class.getResourceAsStream("/order/example-market-order-filled.json");

    // Use Jackson to parse it
    ObjectMapper mapper = new ObjectMapper();
    BitstampOrderStatusResponse krakenQueryOrderResult = mapper.readValue(is, BitstampOrderStatusResponse.class);

    BitstampGenericOrder order = BitstampAdapters.adaptOrder("123", krakenQueryOrderResult);

    assertThat(order.getId()).isEqualTo("123");
    assertThat(order.getAveragePrice()).isEqualTo(new BigDecimal("256.08000000"));
    assertThat(order.getCumulativeAmount()).isEqualTo(new BigDecimal("0.20236360"));
    assertThat(order.getCurrencyPair()).isEqualTo(CurrencyPair.LTC_USD);

  }

}
