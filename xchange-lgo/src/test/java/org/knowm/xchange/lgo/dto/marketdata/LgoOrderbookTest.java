package org.knowm.xchange.lgo.dto.marketdata;

import static org.assertj.core.api.Assertions.assertThat;

import com.fasterxml.jackson.databind.ObjectMapper;
import java.io.IOException;
import java.io.InputStream;
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
    assertThat(lgoOrderbook.getLastBatchId()).isEqualTo(10);
    assertThat(lgoOrderbook.getAsks()).hasSize(2);
    assertThat(lgoOrderbook.getAsks().get(0))
        .containsExactly(new String[] {"2921.9000", "4.44440000"});
    assertThat(lgoOrderbook.getAsks().get(1))
        .containsExactly(new String[] {"2926.5000", "8.38460000"});
    assertThat(lgoOrderbook.getBids()).hasSize(2);
    assertThat(lgoOrderbook.getBids().get(0))
        .containsExactly(new String[] {"2896.6000", "8.35030000"});
    assertThat(lgoOrderbook.getBids().get(1))
        .containsExactly(new String[] {"1850.0000", "931.83050000"});
  }
}
