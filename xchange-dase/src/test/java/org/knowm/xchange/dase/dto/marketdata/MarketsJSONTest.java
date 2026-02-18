package org.knowm.xchange.dase.dto.marketdata;

import static org.assertj.core.api.Assertions.assertThat;

import com.fasterxml.jackson.databind.ObjectMapper;
import java.io.InputStream;
import org.junit.Test;

public class MarketsJSONTest {

  @Test
  public void unmarshal() throws Exception {
    InputStream is =
        MarketsJSONTest.class.getResourceAsStream(
            "/org/knowm/xchange/dase/dto/marketdata/example-markets.json");
    ObjectMapper mapper = new ObjectMapper();
    DaseMarketsResponse res = mapper.readValue(is, DaseMarketsResponse.class);

    assertThat(res.markets).hasSize(2);
    DaseMarketConfig a = res.markets.get(0);
    assertThat(a.market).isEqualTo("ADA-EUR");
    assertThat(a.base).isEqualTo("ADA");
    assertThat(a.quote).isEqualTo("EUR");
    assertThat(a.pricePrecision).isEqualTo(6);
  }
}
