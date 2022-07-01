package org.knowm.xchange.enigma.dto.trade;

import static org.assertj.core.api.Assertions.assertThat;

import com.fasterxml.jackson.databind.ObjectMapper;
import java.io.IOException;
import java.io.InputStream;
import java.math.BigDecimal;
import org.junit.Test;

public class EnigmaQuoteJSONTest {

  @Test
  public void testUnMarshal() throws IOException {
    InputStream is = getClass().getClassLoader().getResourceAsStream("ask-rfq.json");
    ObjectMapper mapper = new ObjectMapper();
    EnigmaQuote quote = mapper.readValue(is, EnigmaQuote.class);
    assertThat(quote.isResult()).isEqualTo(true);
    assertThat(quote.getMessage()).isEqualTo("rfq Inserted");
    assertThat(quote.getRfqClientId()).isEqualTo("7f2e2e8e-d8ee-464e-ab41-0ed8d8057f00");
    assertThat(quote.getProductId()).isEqualTo(2);
    assertThat(quote.getProductName()).isEqualTo("BTC-USD");
    assertThat(quote.getSide()).isEqualTo("buy");
    assertThat(quote.getQuantity()).isEqualTo(new BigDecimal("0.002"));
    assertThat(quote.getNominal()).isEqualTo(new BigDecimal("21.4849"));
    assertThat(quote.getPrice()).isEqualTo(new BigDecimal("10742.4675"));
    assertThat(quote.getInfra()).isEqualTo("dev");
  }
}
