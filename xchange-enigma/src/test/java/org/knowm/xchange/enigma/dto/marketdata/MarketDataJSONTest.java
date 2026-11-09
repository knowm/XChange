package org.knowm.xchange.enigma.dto.marketdata;

import static org.assertj.core.api.Assertions.assertThat;

import com.fasterxml.jackson.databind.ObjectMapper;
import java.io.InputStream;
import java.math.BigDecimal;
import org.junit.jupiter.api.Test;

class MarketDataJSONTest {

  @Test
  void unMarshal() throws Exception {
    InputStream is = getClass().getClassLoader().getResourceAsStream("market-data.json");
    ObjectMapper mapper = new ObjectMapper();
    EnigmaProductMarketData product = mapper.readValue(is, EnigmaProductMarketData.class);
    assertThat(product.getProductName()).isEqualTo("BTC-USD");
    assertThat(product.getProductId()).isEqualTo(2);
    assertThat(product.getAsk()).isEqualTo(new BigDecimal("10331.2213"));
    assertThat(product.getBid()).isEqualTo(new BigDecimal("10243.7571"));
  }
}
