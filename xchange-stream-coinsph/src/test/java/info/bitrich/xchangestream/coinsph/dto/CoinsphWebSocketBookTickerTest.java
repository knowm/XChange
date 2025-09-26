package info.bitrich.xchangestream.coinsph.dto;

import static org.assertj.core.api.Assertions.assertThat;

import com.fasterxml.jackson.databind.ObjectMapper;
import java.io.IOException;
import java.io.InputStream;
import java.math.BigDecimal;
import org.junit.jupiter.api.Test;

public class CoinsphWebSocketBookTickerTest {

  private final ObjectMapper objectMapper = new ObjectMapper();

  @Test
  public void testUnmarshalBookTicker() throws IOException {
    // given
    InputStream is =
        getClass()
            .getResourceAsStream(
                "/info/bitrich/xchangestream/coinsph/dto/example-websocket-bookticker.json");

    // when
    CoinsphWebSocketBookTicker bookTicker =
        objectMapper.readValue(is, CoinsphWebSocketBookTicker.class);

    // then
    assertThat(bookTicker).isNotNull();
    assertThat(bookTicker.getEventType()).isEqualTo("bookTicker");
    assertThat(bookTicker.getEventTime()).isEqualTo(1621234567890L);
    assertThat(bookTicker.getUpdateId()).isEqualTo(12345L);
    assertThat(bookTicker.getSymbol()).isEqualTo("BTCPHP");
    assertThat(bookTicker.getBidPrice()).isEqualByComparingTo(new BigDecimal("5800000.0"));
    assertThat(bookTicker.getBidQty()).isEqualByComparingTo(new BigDecimal("0.0005"));
    assertThat(bookTicker.getAskPrice()).isEqualByComparingTo(new BigDecimal("5810000.0"));
    assertThat(bookTicker.getAskQty()).isEqualByComparingTo(new BigDecimal("0.0003"));
  }

  @Test
  public void testMarshalBookTicker() throws IOException {
    // given
    CoinsphWebSocketBookTicker bookTicker =
        new CoinsphWebSocketBookTicker(
            "bookTicker",
            1621234567890L,
            12345L,
            "BTCPHP",
            new BigDecimal("5800000.0"),
            new BigDecimal("0.0005"),
            new BigDecimal("5810000.0"),
            new BigDecimal("0.0003"));

    // when
    String json = objectMapper.writeValueAsString(bookTicker);

    // then
    assertThat(json).isNotNull();
    CoinsphWebSocketBookTicker unmarshalledBookTicker =
        objectMapper.readValue(json, CoinsphWebSocketBookTicker.class);
    assertThat(unmarshalledBookTicker).isNotNull();
    assertThat(unmarshalledBookTicker.getEventType()).isEqualTo(bookTicker.getEventType());
    assertThat(unmarshalledBookTicker.getEventTime()).isEqualTo(bookTicker.getEventTime());
    assertThat(unmarshalledBookTicker.getUpdateId()).isEqualTo(bookTicker.getUpdateId());
    assertThat(unmarshalledBookTicker.getSymbol()).isEqualTo(bookTicker.getSymbol());
    assertThat(unmarshalledBookTicker.getBidPrice()).isEqualByComparingTo(bookTicker.getBidPrice());
    assertThat(unmarshalledBookTicker.getBidQty()).isEqualByComparingTo(bookTicker.getBidQty());
    assertThat(unmarshalledBookTicker.getAskPrice()).isEqualByComparingTo(bookTicker.getAskPrice());
    assertThat(unmarshalledBookTicker.getAskQty()).isEqualByComparingTo(bookTicker.getAskQty());
  }
}
