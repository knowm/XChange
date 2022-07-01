package org.knowm.xchange.lgo.dto.product;

import static org.assertj.core.api.Assertions.assertThat;

import com.fasterxml.jackson.databind.ObjectMapper;
import java.io.IOException;
import java.io.InputStream;
import java.math.BigDecimal;
import org.junit.Test;

public class LgoProductsTest {

  @Test
  public void itCanReadJson() throws IOException {
    InputStream is =
        LgoProductsTest.class.getResourceAsStream(
            "/org/knowm/xchange/lgo/product/example-products-data.json");
    ObjectMapper mapper = new ObjectMapper();

    LgoProducts products = mapper.readValue(is, LgoProducts.class);

    assertThat(products.getProducts()).hasSize(1);
    LgoProduct product = products.getProducts().get(0);
    assertThat(product.getId()).isEqualTo("BTC-USD");
    assertThat(product.getBase())
        .usingRecursiveComparison()
        .isEqualTo(
            new LgoProductCurrency(
                "BTC", null, new LgoLimit(new BigDecimal("0.001"), new BigDecimal("1000"))));
    assertThat(product.getQuote())
        .usingRecursiveComparison()
        .isEqualTo(
            new LgoProductCurrency(
                "USD",
                new BigDecimal("0.10"),
                new LgoLimit(new BigDecimal("10"), new BigDecimal("1000000"))));
    assertThat(product.getTotal())
        .usingRecursiveComparison()
        .isEqualTo(
            new LgoProductTotal(new LgoLimit(new BigDecimal("10"), new BigDecimal("50000000"))));
  }
}
