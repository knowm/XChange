package info.bitrich.xchangestream.coinsph.dto;

import static org.assertj.core.api.Assertions.assertThat;

import com.fasterxml.jackson.databind.ObjectMapper;
import java.io.IOException;
import java.io.InputStream;
import java.math.BigDecimal;
import org.junit.jupiter.api.Test;

public class CoinsphWebSocketOutboundAccountPositionTest {

  private final ObjectMapper objectMapper = new ObjectMapper();

  @Test
  public void testUnmarshalOutboundAccountPosition() throws IOException {
    // given
    InputStream is =
        getClass()
            .getResourceAsStream(
                "/info/bitrich/xchangestream/coinsph/dto/example-websocket-outbound-account-position.json");

    // when
    CoinsphWebSocketOutboundAccountPosition position =
        objectMapper.readValue(is, CoinsphWebSocketOutboundAccountPosition.class);

    // then
    assertThat(position).isNotNull();
    assertThat(position.getEventType()).isEqualTo("outboundAccountPosition");
    assertThat(position.getEventTime()).isEqualTo(1621234567890L);
    assertThat(position.getAccountUpdateTime()).isEqualTo(1621234567891L);

    // Check balances
    assertThat(position.getBalances()).hasSize(2);

    CoinsphWebSocketOutboundAccountPosition.Balance btcBalance = position.getBalances().get(0);
    assertThat(btcBalance.getAsset()).isEqualTo("BTC");
    assertThat(btcBalance.getFree()).isEqualByComparingTo(new BigDecimal("0.0005"));
    assertThat(btcBalance.getLocked()).isEqualByComparingTo(new BigDecimal("0.0001"));

    CoinsphWebSocketOutboundAccountPosition.Balance phpBalance = position.getBalances().get(1);
    assertThat(phpBalance.getAsset()).isEqualTo("PHP");
    assertThat(phpBalance.getFree()).isEqualByComparingTo(new BigDecimal("10000.0"));
    assertThat(phpBalance.getLocked()).isEqualByComparingTo(new BigDecimal("5000.0"));
  }

  @Test
  public void testMarshalOutboundAccountPosition() throws IOException {
    // given
    InputStream is =
        getClass()
            .getResourceAsStream(
                "/info/bitrich/xchangestream/coinsph/dto/example-websocket-outbound-account-position.json");
    CoinsphWebSocketOutboundAccountPosition position =
        objectMapper.readValue(is, CoinsphWebSocketOutboundAccountPosition.class);

    // when
    String json = objectMapper.writeValueAsString(position);

    // then
    assertThat(json).isNotNull();

    // Verify that the JSON contains the expected fields
    assertThat(json).contains("\"e\":\"outboundAccountPosition\"");
    assertThat(json).contains("\"E\":1621234567890");
    assertThat(json).contains("\"T\":1621234567891");

    // Check balances
    assertThat(json).contains("\"a\":\"BTC\"");
    assertThat(json).contains("\"f\":0.0005");
    assertThat(json).contains("\"l\":0.0001");

    assertThat(json).contains("\"a\":\"PHP\"");
    assertThat(json).contains("\"f\":10000.0");
    assertThat(json).contains("\"l\":5000.0");
  }
}
