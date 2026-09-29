package org.knowm.xchange.enigma.dto.marketdata;

import static org.assertj.core.api.Assertions.assertThat;

import com.fasterxml.jackson.databind.ObjectMapper;
import java.io.InputStream;
import org.junit.jupiter.api.Test;

class EnigmaProductJSONTest {

  @Test
  void unMarshal() throws Exception {
    InputStream is = getClass().getClassLoader().getResourceAsStream("product-list.json");
    ObjectMapper mapper = new ObjectMapper();
    EnigmaProduct[] products = mapper.readValue(is, EnigmaProduct[].class);
    assertThat(products[0].getProductName()).isEqualTo("BTC-EUR");
    assertThat(products[0].getProductId()).isEqualTo(1);
  }
}
