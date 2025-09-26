package info.bitrich.xchangestream.coinsph.dto;

import static org.assertj.core.api.Assertions.assertThat;

import com.fasterxml.jackson.databind.ObjectMapper;
import java.io.IOException;
import java.io.InputStream;
import java.math.BigDecimal;
import org.junit.jupiter.api.Test;

public class CoinsphWebSocketExecutionReportTest {

  private final ObjectMapper objectMapper = new ObjectMapper();

  @Test
  public void testUnmarshalExecutionReport() throws IOException {
    // given
    InputStream is =
        getClass()
            .getResourceAsStream(
                "/info/bitrich/xchangestream/coinsph/dto/example-websocket-execution-report.json");

    // when
    CoinsphWebSocketExecutionReport executionReport =
        objectMapper.readValue(is, CoinsphWebSocketExecutionReport.class);

    // then
    assertThat(executionReport).isNotNull();
    assertThat(executionReport.getEventType()).isEqualTo("executionReport");
    assertThat(executionReport.getEventTime()).isEqualTo(1621234567890L);
    assertThat(executionReport.getSymbol()).isEqualTo("BTCPHP");
    assertThat(executionReport.getClientOrderId()).isEqualTo("myOrder1");
    assertThat(executionReport.getSide()).isEqualTo("BUY");
    assertThat(executionReport.getOrderType()).isEqualTo("LIMIT");
    assertThat(executionReport.getTimeInForce()).isEqualTo("GTC");
    assertThat(executionReport.getQuantity()).isEqualByComparingTo(new BigDecimal("0.0005"));
    assertThat(executionReport.getPrice()).isEqualByComparingTo(new BigDecimal("5800000.0"));
    assertThat(executionReport.getOrderStatus()).isEqualTo("FILLED");
    assertThat(executionReport.getOrderId()).isEqualTo(12345L);
    assertThat(executionReport.getCumulativeFilledQuantity())
        .isEqualByComparingTo(new BigDecimal("0.0005"));
    assertThat(executionReport.getLastExecutedPrice())
        .isEqualByComparingTo(new BigDecimal("5800000.0"));
    assertThat(executionReport.getLastExecutedQuantity())
        .isEqualByComparingTo(new BigDecimal("0.0005"));
    assertThat(executionReport.getCommission()).isEqualByComparingTo(new BigDecimal("0.00000075"));
    assertThat(executionReport.getCommissionAsset()).isEqualTo("BTC");
    assertThat(executionReport.getTradeTime()).isEqualTo(1621234560000L);
    assertThat(executionReport.getTradeId()).isEqualTo(54321L);
    assertThat(executionReport.isMarketMaker()).isFalse();
    assertThat(executionReport.getExecutionType()).isEqualTo("TRADE");
  }

  @Test
  public void testMarshalExecutionReport() throws IOException {
    // given
    CoinsphWebSocketExecutionReport executionReport =
        new CoinsphWebSocketExecutionReport(
            "executionReport",
            1621234567890L,
            "BTCPHP",
            "myOrder1",
            "BUY",
            "LIMIT",
            "GTC",
            new BigDecimal("0.0005"),
            new BigDecimal("5800000.0"),
            "FILLED",
            12345L,
            new BigDecimal("0.0005"),
            new BigDecimal("5800000.0"),
            new BigDecimal("0.0005"),
            new BigDecimal("0.00000075"),
            "BTC",
            1621234560000L,
            54321L,
            false,
            "TRADE");

    // when
    String json = objectMapper.writeValueAsString(executionReport);

    // then
    assertThat(json).isNotNull();

    // Verify that the JSON contains the expected fields
    assertThat(json).contains("\"e\":\"executionReport\"");
    assertThat(json).contains("\"E\":1621234567890");
    assertThat(json).contains("\"s\":\"BTCPHP\"");
    assertThat(json).contains("\"c\":\"myOrder1\"");
    assertThat(json).contains("\"S\":\"BUY\"");
    assertThat(json).contains("\"o\":\"LIMIT\"");
    assertThat(json).contains("\"f\":\"GTC\"");
    assertThat(json).contains("\"q\":0.0005");
    assertThat(json).contains("\"p\":5800000.0");
    assertThat(json).contains("\"X\":\"FILLED\"");
    assertThat(json).contains("\"i\":12345");
    assertThat(json).contains("\"z\":0.0005");
    assertThat(json).contains("\"L\":5800000.0");
    assertThat(json).contains("\"l\":0.0005");
    assertThat(json).contains("\"n\":7.5E-7"); // 0.00000075
    assertThat(json).contains("\"N\":\"BTC\"");
    assertThat(json).contains("\"T\":1621234560000");
    assertThat(json).contains("\"t\":54321");
    assertThat(json).contains("\"m\":false");
    assertThat(json).contains("\"x\":\"TRADE\"");
  }
}
