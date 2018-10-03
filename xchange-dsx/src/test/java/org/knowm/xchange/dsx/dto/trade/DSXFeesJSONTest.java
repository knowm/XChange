package org.knowm.xchange.dsx.dto.trade;

import static org.assertj.core.api.Assertions.assertThat;

import com.fasterxml.jackson.databind.ObjectMapper;
import java.io.IOException;
import java.io.InputStream;
import org.junit.Test;

/** @author Mikhail Wall */
public class DSXFeesJSONTest {

  @Test
  public void testGetFees() throws IOException {
    InputStream is =
        DSXFeesJSONTest.class.getResourceAsStream(
            "/org/knowm/xchange/dsx/dto/trade/example-fees-data.json");

    ObjectMapper mapper = new ObjectMapper();
    DSXFeesReturn fees = mapper.readValue(is, DSXFeesReturn.class);
    DSXFeesResult result = fees.getReturnValue();

    assertThat(result.getProgressiveCommissions().getCurrency()).isEqualTo("USD");
    assertThat(result.getProgressiveCommissions().getCommissions().length).isEqualTo(7);
    assertThat(result.getProgressiveCommissions().getIndexOfCurrentCommission()).isEqualTo(0);
  }
}
