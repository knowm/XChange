package info.bitrich.xchangestream.coinsph.dto;

import static org.assertj.core.api.Assertions.assertThat;

import com.fasterxml.jackson.databind.ObjectMapper;
import java.io.IOException;
import java.io.InputStream;
import java.math.BigDecimal;
import org.junit.jupiter.api.Test;

public class CoinsphWebSocketTickerTest {

  private final ObjectMapper objectMapper = new ObjectMapper();

  @Test
  public void testUnmarshalTicker() throws IOException {
    // given
    InputStream is =
        getClass()
            .getResourceAsStream(
                "/info/bitrich/xchangestream/coinsph/dto/example-websocket-ticker.json");

    // when
    CoinsphWebSocketTicker ticker = objectMapper.readValue(is, CoinsphWebSocketTicker.class);

    // then
    assertThat(ticker).isNotNull();
    assertThat(ticker.getEventType()).isEqualTo("24hrTicker");
    assertThat(ticker.getEventTime()).isEqualTo(1621234567890L);
    assertThat(ticker.getSymbol()).isEqualTo("BTCPHP");
    assertThat(ticker.getPriceChange()).isEqualByComparingTo(new BigDecimal("1000.0"));
    assertThat(ticker.getPriceChangePercent()).isEqualByComparingTo(new BigDecimal("1.5"));
    assertThat(ticker.getWeightedAvgPrice()).isEqualByComparingTo(new BigDecimal("5800000.0"));
    assertThat(ticker.getPrevClosePrice()).isEqualByComparingTo(new BigDecimal("5750000.0"));
    assertThat(ticker.getLastPrice()).isEqualByComparingTo(new BigDecimal("5850000.0"));
    assertThat(ticker.getLastQty()).isEqualByComparingTo(new BigDecimal("0.0001"));
    assertThat(ticker.getBidPrice()).isEqualByComparingTo(new BigDecimal("5849000.0"));
    assertThat(ticker.getBidQty()).isEqualByComparingTo(new BigDecimal("0.0005"));
    assertThat(ticker.getAskPrice()).isEqualByComparingTo(new BigDecimal("5851000.0"));
    assertThat(ticker.getAskQty()).isEqualByComparingTo(new BigDecimal("0.0003"));
    assertThat(ticker.getOpenPrice()).isEqualByComparingTo(new BigDecimal("5750000.0"));
    assertThat(ticker.getHighPrice()).isEqualByComparingTo(new BigDecimal("5900000.0"));
    assertThat(ticker.getLowPrice()).isEqualByComparingTo(new BigDecimal("5700000.0"));
    assertThat(ticker.getVolume()).isEqualByComparingTo(new BigDecimal("10.5"));
    assertThat(ticker.getQuoteVolume()).isEqualByComparingTo(new BigDecimal("60900000.0"));
    assertThat(ticker.getOpenTime()).isEqualTo(1621148167890L);
    assertThat(ticker.getCloseTime()).isEqualTo(1621234567890L);
    assertThat(ticker.getFirstId()).isEqualTo(12340L);
    assertThat(ticker.getLastId()).isEqualTo(12345L);
    assertThat(ticker.getCount()).isEqualTo(5L);
  }

  @Test
  public void testMarshalTicker() throws IOException {
    // given
    CoinsphWebSocketTicker ticker =
        new CoinsphWebSocketTicker(
            "24hrTicker",
            1621234567890L,
            "BTCPHP",
            new BigDecimal("1000.0"),
            new BigDecimal("1.5"),
            new BigDecimal("5800000.0"),
            new BigDecimal("5750000.0"),
            new BigDecimal("5850000.0"),
            new BigDecimal("0.0001"),
            new BigDecimal("5849000.0"),
            new BigDecimal("0.0005"),
            new BigDecimal("5851000.0"),
            new BigDecimal("0.0003"),
            new BigDecimal("5750000.0"),
            new BigDecimal("5900000.0"),
            new BigDecimal("5700000.0"),
            new BigDecimal("10.5"),
            new BigDecimal("60900000.0"),
            1621148167890L,
            1621234567890L,
            12340L,
            12345L,
            5L);

    // when
    String json = objectMapper.writeValueAsString(ticker);

    // then
    assertThat(json).isNotNull();
    CoinsphWebSocketTicker unmarshalledTicker =
        objectMapper.readValue(json, CoinsphWebSocketTicker.class);
    assertThat(unmarshalledTicker).isNotNull();
    assertThat(unmarshalledTicker.getEventType()).isEqualTo(ticker.getEventType());
    assertThat(unmarshalledTicker.getEventTime()).isEqualTo(ticker.getEventTime());
    assertThat(unmarshalledTicker.getSymbol()).isEqualTo(ticker.getSymbol());
    assertThat(unmarshalledTicker.getPriceChange()).isEqualByComparingTo(ticker.getPriceChange());
    assertThat(unmarshalledTicker.getPriceChangePercent())
        .isEqualByComparingTo(ticker.getPriceChangePercent());
    assertThat(unmarshalledTicker.getWeightedAvgPrice())
        .isEqualByComparingTo(ticker.getWeightedAvgPrice());
    assertThat(unmarshalledTicker.getPrevClosePrice())
        .isEqualByComparingTo(ticker.getPrevClosePrice());
    assertThat(unmarshalledTicker.getLastPrice()).isEqualByComparingTo(ticker.getLastPrice());
    assertThat(unmarshalledTicker.getLastQty()).isEqualByComparingTo(ticker.getLastQty());
    assertThat(unmarshalledTicker.getBidPrice()).isEqualByComparingTo(ticker.getBidPrice());
    assertThat(unmarshalledTicker.getBidQty()).isEqualByComparingTo(ticker.getBidQty());
    assertThat(unmarshalledTicker.getAskPrice()).isEqualByComparingTo(ticker.getAskPrice());
    assertThat(unmarshalledTicker.getAskQty()).isEqualByComparingTo(ticker.getAskQty());
    assertThat(unmarshalledTicker.getOpenPrice()).isEqualByComparingTo(ticker.getOpenPrice());
    assertThat(unmarshalledTicker.getHighPrice()).isEqualByComparingTo(ticker.getHighPrice());
    assertThat(unmarshalledTicker.getLowPrice()).isEqualByComparingTo(ticker.getLowPrice());
    assertThat(unmarshalledTicker.getVolume()).isEqualByComparingTo(ticker.getVolume());
    assertThat(unmarshalledTicker.getQuoteVolume()).isEqualByComparingTo(ticker.getQuoteVolume());
    assertThat(unmarshalledTicker.getOpenTime()).isEqualTo(ticker.getOpenTime());
    assertThat(unmarshalledTicker.getCloseTime()).isEqualTo(ticker.getCloseTime());
    assertThat(unmarshalledTicker.getFirstId()).isEqualTo(ticker.getFirstId());
    assertThat(unmarshalledTicker.getLastId()).isEqualTo(ticker.getLastId());
    assertThat(unmarshalledTicker.getCount()).isEqualTo(ticker.getCount());
  }
}
