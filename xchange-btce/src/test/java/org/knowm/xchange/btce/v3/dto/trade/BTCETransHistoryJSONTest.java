package org.knowm.xchange.btce.v3.dto.trade;

import static org.fest.assertions.api.Assertions.assertThat;

import java.io.IOException;
import java.io.InputStream;
import java.math.BigDecimal;
import java.util.Map;

import org.junit.Test;

import com.fasterxml.jackson.databind.ObjectMapper;

/**
 * @author Peter N. Steinmetz Date: 4/2/15 Time: 3:44 PM
 */
public class BTCETransHistoryJSONTest {

  @Test
  public void testUnmarshal() throws IOException {

    // Read in the JSON from the example resources
    InputStream is = BTCETransHistoryJSONTest.class.getResourceAsStream("/v3/trade/example-trans-history-data.json");

    // Use Jackson to parse it
    ObjectMapper mapper = new ObjectMapper();
    BTCETransHistoryReturn transactions = mapper.readValue(is, BTCETransHistoryReturn.class);
    Map<Long, BTCETransHistoryResult> result = transactions.getReturnValue();
    assertThat(result.size()).isEqualTo(1);
    Map.Entry<Long, BTCETransHistoryResult> firstEntry = result.entrySet().iterator().next();
    // Verify that the example data was unmarshalled correctly
    assertThat(firstEntry.getKey()).isEqualTo(1081672L);
    assertThat(firstEntry.getValue().getType()).isEqualTo(BTCETransHistoryResult.Type.BTC_deposit);
    assertThat(firstEntry.getValue().getAmount()).isEqualTo(new BigDecimal("1.00000000"));
    assertThat(firstEntry.getValue().getCurrency()).isEqualTo("BTC");
    assertThat(firstEntry.getValue().getDescription()).isEqualTo("BTC Payment");
    assertThat(firstEntry.getValue().getStatus()).isEqualTo(BTCETransHistoryResult.Status.complete);
    assertThat(firstEntry.getValue().getTimestamp()).isEqualTo(1342448420L);
  }

}
