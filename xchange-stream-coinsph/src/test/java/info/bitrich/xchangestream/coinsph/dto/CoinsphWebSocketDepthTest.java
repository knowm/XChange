package info.bitrich.xchangestream.coinsph.dto;

import static org.assertj.core.api.Assertions.assertThat;

import com.fasterxml.jackson.databind.ObjectMapper;
import java.io.IOException;
import java.io.InputStream;
import java.math.BigDecimal;
import java.util.Arrays;
import java.util.List;
import org.junit.jupiter.api.Test;

public class CoinsphWebSocketDepthTest {

  private final ObjectMapper objectMapper = new ObjectMapper();

  @Test
  public void testUnmarshalDepth() throws IOException {
    // given
    InputStream is =
        getClass()
            .getResourceAsStream(
                "/info/bitrich/xchangestream/coinsph/dto/example-websocket-depth.json");

    // when
    CoinsphWebSocketDepth depth = objectMapper.readValue(is, CoinsphWebSocketDepth.class);

    // then
    assertThat(depth).isNotNull();
    assertThat(depth.getEventType()).isEqualTo("depthUpdate");
    assertThat(depth.getEventTime()).isEqualTo(1621234567890L);
    assertThat(depth.getSymbol()).isEqualTo("BTCPHP");
    assertThat(depth.getFirstUpdateId()).isEqualTo(12345L);
    assertThat(depth.getFinalUpdateId()).isEqualTo(12350L);

    // Check bids
    assertThat(depth.getBids()).hasSize(2);
    List<BigDecimal> firstBid = depth.getBids().get(0);
    assertThat(firstBid).hasSize(2);
    assertThat(firstBid.get(0)).isEqualByComparingTo(new BigDecimal("5800000.0"));
    assertThat(firstBid.get(1)).isEqualByComparingTo(new BigDecimal("0.0005"));

    List<BigDecimal> secondBid = depth.getBids().get(1);
    assertThat(secondBid).hasSize(2);
    assertThat(secondBid.get(0)).isEqualByComparingTo(new BigDecimal("5790000.0"));
    assertThat(secondBid.get(1)).isEqualByComparingTo(new BigDecimal("0.0010"));

    // Check asks
    assertThat(depth.getAsks()).hasSize(2);
    List<BigDecimal> firstAsk = depth.getAsks().get(0);
    assertThat(firstAsk).hasSize(2);
    assertThat(firstAsk.get(0)).isEqualByComparingTo(new BigDecimal("5810000.0"));
    assertThat(firstAsk.get(1)).isEqualByComparingTo(new BigDecimal("0.0003"));

    List<BigDecimal> secondAsk = depth.getAsks().get(1);
    assertThat(secondAsk).hasSize(2);
    assertThat(secondAsk.get(0)).isEqualByComparingTo(new BigDecimal("5820000.0"));
    assertThat(secondAsk.get(1)).isEqualByComparingTo(new BigDecimal("0.0008"));
  }

  @Test
  public void testMarshalDepth() throws IOException {
    // given
    List<List<BigDecimal>> bids =
        Arrays.asList(
            Arrays.asList(new BigDecimal("5800000.0"), new BigDecimal("0.0005")),
            Arrays.asList(new BigDecimal("5790000.0"), new BigDecimal("0.0010")));

    List<List<BigDecimal>> asks =
        Arrays.asList(
            Arrays.asList(new BigDecimal("5810000.0"), new BigDecimal("0.0003")),
            Arrays.asList(new BigDecimal("5820000.0"), new BigDecimal("0.0008")));

    CoinsphWebSocketDepth depth =
        new CoinsphWebSocketDepth(
            "depthUpdate", 1621234567890L, "BTCPHP", 12345L, 12350L, bids, asks);

    // when
    String json = objectMapper.writeValueAsString(depth);

    // then
    assertThat(json).isNotNull();
    CoinsphWebSocketDepth unmarshalledDepth =
        objectMapper.readValue(json, CoinsphWebSocketDepth.class);
    assertThat(unmarshalledDepth).isNotNull();
    assertThat(unmarshalledDepth.getEventType()).isEqualTo(depth.getEventType());
    assertThat(unmarshalledDepth.getEventTime()).isEqualTo(depth.getEventTime());
    assertThat(unmarshalledDepth.getSymbol()).isEqualTo(depth.getSymbol());
    assertThat(unmarshalledDepth.getFirstUpdateId()).isEqualTo(depth.getFirstUpdateId());
    assertThat(unmarshalledDepth.getFinalUpdateId()).isEqualTo(depth.getFinalUpdateId());

    // Check bids
    assertThat(unmarshalledDepth.getBids()).hasSize(depth.getBids().size());
    for (int i = 0; i < depth.getBids().size(); i++) {
      List<BigDecimal> originalBid = depth.getBids().get(i);
      List<BigDecimal> unmarshalledBid = unmarshalledDepth.getBids().get(i);
      assertThat(unmarshalledBid).hasSize(originalBid.size());
      for (int j = 0; j < originalBid.size(); j++) {
        assertThat(unmarshalledBid.get(j)).isEqualByComparingTo(originalBid.get(j));
      }
    }

    // Check asks
    assertThat(unmarshalledDepth.getAsks()).hasSize(depth.getAsks().size());
    for (int i = 0; i < depth.getAsks().size(); i++) {
      List<BigDecimal> originalAsk = depth.getAsks().get(i);
      List<BigDecimal> unmarshalledAsk = unmarshalledDepth.getAsks().get(i);
      assertThat(unmarshalledAsk).hasSize(originalAsk.size());
      for (int j = 0; j < originalAsk.size(); j++) {
        assertThat(unmarshalledAsk.get(j)).isEqualByComparingTo(originalAsk.get(j));
      }
    }
  }
}
