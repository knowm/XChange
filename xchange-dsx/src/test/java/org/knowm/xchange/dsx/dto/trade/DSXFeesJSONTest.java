package org.knowm.xchange.dsx.dto.trade;

import static org.fest.assertions.api.Assertions.assertThat;

import java.io.IOException;
import java.io.InputStream;
import java.util.Collections;
import java.util.Map;

import org.junit.Test;

import com.fasterxml.jackson.databind.ObjectMapper;

/**
 * @author Mikhail Wall
 */

public class DSXFeesJSONTest {

  @Test
  public void testGetFees() throws IOException {
    InputStream is = DSXFeesJSONTest.class.getResourceAsStream("/trade/example-fees-data.json");

    ObjectMapper mapper = new ObjectMapper();
    DSXFeesReturn fees = mapper.readValue(is, DSXFeesReturn.class);
    DSXFeesResult result = fees.getReturnValue();

    assertThat(result.getProgressiveCommissions().getCurrency()).isEqualTo("USD");
    assertThat(result.getProgressiveCommissions().getCommissions().length).isEqualTo(7);
    assertThat(result.getProgressiveCommissions().getIndexOfCurrentCommission()).isEqualTo(0);
  }
}
