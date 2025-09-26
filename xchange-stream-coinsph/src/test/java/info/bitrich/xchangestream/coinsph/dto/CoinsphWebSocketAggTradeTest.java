package info.bitrich.xchangestream.coinsph.dto;

import static org.assertj.core.api.Assertions.assertThat;

import com.fasterxml.jackson.databind.ObjectMapper;
import java.io.IOException;
import java.io.InputStream;
import java.math.BigDecimal;
import org.junit.jupiter.api.Test;

public class CoinsphWebSocketAggTradeTest {

  private final ObjectMapper objectMapper = new ObjectMapper();

  @Test
  public void testUnmarshalAggTrade() throws IOException {
    // given
    InputStream is =
        getClass()
            .getResourceAsStream(
                "/info/bitrich/xchangestream/coinsph/dto/example-websocket-aggtrade.json");

    // when
    CoinsphWebSocketAggTrade aggTrade = objectMapper.readValue(is, CoinsphWebSocketAggTrade.class);

    // then
    assertThat(aggTrade).isNotNull();
    assertThat(aggTrade.getEventType()).isEqualTo("aggTrade");
    assertThat(aggTrade.getEventTime()).isEqualTo(1621234567890L);
    assertThat(aggTrade.getSymbol()).isEqualTo("BTCPHP");
    assertThat(aggTrade.getAggregateTradeId()).isEqualTo(12345L);
    assertThat(aggTrade.getPrice()).isEqualByComparingTo(new BigDecimal("5803095.5"));
    assertThat(aggTrade.getQuantity()).isEqualByComparingTo(new BigDecimal("0.0000824"));
    assertThat(aggTrade.getFirstTradeId()).isEqualTo(12340L);
    assertThat(aggTrade.getLastTradeId()).isEqualTo(12345L);
    assertThat(aggTrade.getTradeTime()).isEqualTo(1621234560000L);
    assertThat(aggTrade.isBuyerMaker()).isTrue();
  }

  @Test
  public void testMarshalAggTrade() throws IOException {
    // given
    CoinsphWebSocketAggTrade aggTrade =
        new CoinsphWebSocketAggTrade(
            "aggTrade",
            1621234567890L,
            "BTCPHP",
            12345L,
            new BigDecimal("5803095.5"),
            new BigDecimal("0.0000824"),
            12340L,
            12345L,
            1621234560000L,
            true);

    // when
    String json = objectMapper.writeValueAsString(aggTrade);

    // then
    assertThat(json).isNotNull();
    CoinsphWebSocketAggTrade unmarshalledAggTrade =
        objectMapper.readValue(json, CoinsphWebSocketAggTrade.class);
    assertThat(unmarshalledAggTrade).isNotNull();
    assertThat(unmarshalledAggTrade.getEventType()).isEqualTo(aggTrade.getEventType());
    assertThat(unmarshalledAggTrade.getEventTime()).isEqualTo(aggTrade.getEventTime());
    assertThat(unmarshalledAggTrade.getSymbol()).isEqualTo(aggTrade.getSymbol());
    assertThat(unmarshalledAggTrade.getAggregateTradeId())
        .isEqualTo(aggTrade.getAggregateTradeId());
    assertThat(unmarshalledAggTrade.getPrice()).isEqualByComparingTo(aggTrade.getPrice());
    assertThat(unmarshalledAggTrade.getQuantity()).isEqualByComparingTo(aggTrade.getQuantity());
    assertThat(unmarshalledAggTrade.getFirstTradeId()).isEqualTo(aggTrade.getFirstTradeId());
    assertThat(unmarshalledAggTrade.getLastTradeId()).isEqualTo(aggTrade.getLastTradeId());
    assertThat(unmarshalledAggTrade.getTradeTime()).isEqualTo(aggTrade.getTradeTime());
    assertThat(unmarshalledAggTrade.isBuyerMaker()).isEqualTo(aggTrade.isBuyerMaker());
  }
}
