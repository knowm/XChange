package org.knowm.xchange.dsx.dto.trade;

import static org.fest.assertions.api.Assertions.assertThat;

import java.io.IOException;
import java.io.InputStream;
import java.math.BigDecimal;
import java.util.Map;

import org.junit.Test;

import com.fasterxml.jackson.databind.ObjectMapper;

/**
 * @author Mikhail Wall
 */
public class DSXTransHistoryJSONTest {

  @Test
  public void testUnmarshal() throws IOException {

    InputStream is = DSXTransHistoryJSONTest.class.getResourceAsStream("/trade/example-trans-history-data.json");

    ObjectMapper mapper = new ObjectMapper();
    DSXTransHistoryReturn transactions = mapper.readValue(is, DSXTransHistoryReturn.class);
    Map<Long, DSXTransHistoryResult> result = transactions.getReturnValue();
    assertThat(result.size()).isEqualTo(1);
    Map.Entry<Long, DSXTransHistoryResult> firstEntry = result.entrySet().iterator().next();
    assertThat(firstEntry.getKey()).isEqualTo(1000L);
    assertThat(firstEntry.getValue().getType()).isEqualTo(DSXTransHistoryResult.Type.Incoming);
    assertThat(firstEntry.getValue().getAmount()).isEqualTo(new BigDecimal("2.5"));
    assertThat(firstEntry.getValue().getCurrency()).isEqualTo("USD");
    assertThat(firstEntry.getValue().getDesc()).isEqualTo("Income");
    assertThat(firstEntry.getValue().getStatus()).isEqualTo(DSXTransHistoryResult.Status.Completed);
    assertThat(firstEntry.getValue().getTimestamp()).isEqualTo(142123698L);
    assertThat(firstEntry.getValue().getCommission()).isEqualTo(new BigDecimal("1.0"));
    assertThat(firstEntry.getValue().getAddress()).isEqualTo("address string");
    assertThat(firstEntry.getValue().getTxId()).isEqualTo("uid2");
  }
}
