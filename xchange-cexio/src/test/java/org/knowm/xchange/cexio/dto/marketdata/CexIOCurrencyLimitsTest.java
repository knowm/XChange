package org.knowm.xchange.cexio.dto.marketdata;

import static org.assertj.core.api.Assertions.assertThat;

import com.fasterxml.jackson.databind.ObjectMapper;
import java.io.IOException;
import java.io.InputStream;
import org.junit.Test;

/** @author ujjwal on 13/02/18. */
public class CexIOCurrencyLimitsTest {
  @Test
  public void jsonMapperTest() throws IOException {
    InputStream is =
        getClass()
            .getResourceAsStream(
                "/org/knowm/xchange/cexio/dto/marketdata/sample_currency_limits.json");
    ObjectMapper mapper = new ObjectMapper();
    final CexIOCurrencyLimits cexIOCurrencyLimits = mapper.readValue(is, CexIOCurrencyLimits.class);
    assertThat(cexIOCurrencyLimits).isNotNull();
    assertThat(cexIOCurrencyLimits.getData().getPairs().size()).isBetween(1, 30);
    assertThat(cexIOCurrencyLimits.getData().getPairs())
        .allSatisfy(
            pair -> {
              assertThat(pair.getSymbol1()).isNotNull();
              assertThat(pair.getSymbol2()).isNotNull();
              assertThat(pair.getMinLotSize()).isPositive();
              // we don't care about the other parameters for now.
            });
  }
}
