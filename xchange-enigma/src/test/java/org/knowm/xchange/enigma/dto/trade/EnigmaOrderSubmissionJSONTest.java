package org.knowm.xchange.enigma.dto.trade;

import static org.assertj.core.api.Assertions.assertThat;

import com.fasterxml.jackson.databind.ObjectMapper;
import java.io.IOException;
import java.io.InputStream;
import java.math.BigDecimal;
import org.junit.Test;

public class EnigmaOrderSubmissionJSONTest {

  @Test
  public void testUnMarshal() throws IOException {
    InputStream is = getClass().getClassLoader().getResourceAsStream("order-submission.json");
    ObjectMapper mapper = new ObjectMapper();
    EnigmaOrderSubmission product = mapper.readValue(is, EnigmaOrderSubmission.class);
    assertThat(product.isResult()).isEqualTo(true);
    assertThat(product.getMessage()).isEqualTo("Order executed");
    assertThat(product.getId()).isEqualTo(195);
    assertThat(product.getProductId()).isEqualTo(2);
    assertThat(product.getProductName()).isEqualTo("BTC-USD");
    assertThat(product.getSide()).isEqualTo("sell");
    assertThat(product.getQuantity()).isEqualTo(new BigDecimal("0.0050"));
    assertThat(product.getPrice()).isEqualTo(new BigDecimal("10200.5865"));
    assertThat(product.getNominal()).isEqualTo(new BigDecimal("51.0029"));
    assertThat(product.getUserName()).isEqualTo("iSemyonova");
    assertThat(product.getInfrastructure()).isEqualTo("dev");
  }
}
