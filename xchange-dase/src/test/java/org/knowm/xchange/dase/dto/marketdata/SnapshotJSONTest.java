package org.knowm.xchange.dase.dto.marketdata;

import static org.assertj.core.api.Assertions.assertThat;

import com.fasterxml.jackson.databind.ObjectMapper;
import java.io.InputStream;
import org.junit.Test;

public class SnapshotJSONTest {

  @Test
  public void unmarshal() throws Exception {
    InputStream is =
        SnapshotJSONTest.class.getResourceAsStream(
            "/org/knowm/xchange/dase/dto/marketdata/example-snapshot.json");
    ObjectMapper mapper = new ObjectMapper();
    DaseOrderBookSnapshot s = mapper.readValue(is, DaseOrderBookSnapshot.class);

    assertThat(s.getBids()).isNotEmpty();
    assertThat(s.getAsks()).isNotEmpty();
    assertThat(s.getEventId()).isEqualTo(123456L);
  }
}
