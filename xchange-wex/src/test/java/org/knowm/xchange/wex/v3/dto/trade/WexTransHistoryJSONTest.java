package org.knowm.xchange.wex.v3.dto.trade;

import static org.assertj.core.api.Assertions.assertThat;

import com.fasterxml.jackson.databind.ObjectMapper;
import java.io.IOException;
import java.io.InputStream;
import java.math.BigDecimal;
import java.util.Map;
import org.junit.Test;

/** @author Peter N. Steinmetz Date: 4/2/15 Time: 3:44 PM */
public class WexTransHistoryJSONTest {

  @Test
  public void testUnmarshal() throws IOException {

    // Read in the JSON from the example resources
    InputStream is =
        WexTransHistoryJSONTest.class.getResourceAsStream(
            "/org/knowm/xchange/wex/v3/trade/example-trans-history-data.json");

    // Use Jackson to parse it
    ObjectMapper mapper = new ObjectMapper();
    WexTransHistoryReturn transactions = mapper.readValue(is, WexTransHistoryReturn.class);
    Map<Long, WexTransHistoryResult> result = transactions.getReturnValue();
    assertThat(result.size()).isEqualTo(1);
    Map.Entry<Long, WexTransHistoryResult> firstEntry = result.entrySet().iterator().next();
    // Verify that the example data was unmarshalled correctly
    assertThat(firstEntry.getKey()).isEqualTo(1081672L);
    assertThat(firstEntry.getValue().getType()).isEqualTo(WexTransHistoryResult.Type.BTC_deposit);
    assertThat(firstEntry.getValue().getAmount()).isEqualTo(new BigDecimal("1.00000000"));
    assertThat(firstEntry.getValue().getCurrency()).isEqualTo("BTC");
    assertThat(firstEntry.getValue().getDescription()).isEqualTo("BTC Payment");
    assertThat(firstEntry.getValue().getStatus()).isEqualTo(WexTransHistoryResult.Status.complete);
    assertThat(firstEntry.getValue().getTimestamp()).isEqualTo(1342448420L);
  }
}
