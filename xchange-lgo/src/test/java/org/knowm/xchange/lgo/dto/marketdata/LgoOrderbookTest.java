package org.knowm.xchange.lgo.dto.marketdata;


import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.entry;

import com.fasterxml.jackson.databind.ObjectMapper;
import java.io.IOException;
import java.io.InputStream;
import java.math.BigDecimal;
import org.junit.Test;
import org.knowm.xchange.lgo.dto.product.LgoProductsTest;

public class LgoOrderbookTest {

  @Test
  public void itCanReadJson() throws IOException {
    InputStream is =
        LgoProductsTest.class.getResourceAsStream(
            "/org/knowm/xchange/lgo/marketdata/example-orderbook-data.json");
    ObjectMapper mapper = new ObjectMapper();

    LgoOrderbook lgoOrderbook = mapper.readValue(is, LgoOrderbook.class);

    assertThat(lgoOrderbook).isNotNull();
    assertThat(lgoOrderbook.asks).hasSize(2);
    assertThat(lgoOrderbook.bids).hasSize(2);
    assertThat(lgoOrderbook.asks).containsExactly(
        entry(new BigDecimal("2921.9000"), new BigDecimal("4.44440000")),
        entry(new BigDecimal("2926.5000"), new BigDecimal("8.38460000"))
    );
    assertThat(lgoOrderbook.bids).containsExactly(
        entry(new BigDecimal("2896.6000"), new BigDecimal("8.35030000")),
        entry(new BigDecimal("1850.0000"), new BigDecimal("931.83050000"))
    );
  }

}