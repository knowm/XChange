package org.knowm.xchange.dase.dto.marketdata;

import static org.assertj.core.api.Assertions.assertThat;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import java.io.InputStream;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;
import org.junit.Test;

public class TradesJSONTest {

  @Test
  public void unmarshal() throws Exception {
    InputStream is =
        TradesJSONTest.class.getResourceAsStream(
            "/org/knowm/xchange/dase/dto/marketdata/example-trades.json");
    ObjectMapper mapper = new ObjectMapper();
    JsonNode root = mapper.readTree(is);
    List<DaseTrade> trades =
        StreamSupport.stream(root.get("trades").spliterator(), false)
            .map(n -> mapper.convertValue(n, DaseTrade.class))
            .collect(Collectors.toList());

    assertThat(trades).hasSize(2);
    assertThat(trades.get(0).getPrice()).isEqualByComparingTo("18.51");
    assertThat(trades.get(1).getMakerSide()).isEqualToIgnoringCase("buy");
  }
}
