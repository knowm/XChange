package org.knowm.xchange.enigma.dto.marketdata;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.Test;

import java.io.IOException;
import java.io.InputStream;

import static org.assertj.core.api.Assertions.assertThat;

public class EnigmaProductJSONTest {

  @Test
  public void testUnMarshal() throws IOException {
    InputStream is =
        getClass().getClassLoader().getResourceAsStream(
            "product-list.json");
    ObjectMapper mapper = new ObjectMapper();
    EnigmaProduct[] products = mapper.readValue(is, EnigmaProduct[].class);
    assertThat(products[0].getProductName()).isEqualTo("BTC-EUR");
    assertThat(products[0].getProductId()).isEqualTo(1);
  }
}
