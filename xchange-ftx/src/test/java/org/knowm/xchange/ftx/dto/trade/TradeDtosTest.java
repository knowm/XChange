package org.knowm.xchange.ftx.dto.trade;

import static org.assertj.core.api.Assertions.assertThat;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import java.io.IOException;
import java.io.InputStream;
import java.math.BigDecimal;
import java.util.List;
import org.junit.Test;
import org.knowm.xchange.ftx.dto.FtxResponse;

public class TradeDtosTest {

  @Test
  public void openOrdersDtoUnmarshall() throws IOException {
    // Read in the JSON from the example resources
    InputStream is =
        TradeDtosTest.class.getResourceAsStream("/responses/example-ftxOpenOrders.json");

    // Use Jackson to parse it
    ObjectMapper mapper = new ObjectMapper();
    FtxResponse<List<FtxOrderDto>> ftxResponse =
        mapper.readValue(is, new TypeReference<FtxResponse<List<FtxOrderDto>>>() {});

    // Verify that the example data was unmarshalled correctly
    assertThat(ftxResponse.getResult().size()).isEqualTo(1);

    assertThat(ftxResponse.getResult().get(0).getId()).isEqualTo(String.valueOf(9596912));
    assertThat(ftxResponse.getResult().get(0).getMarket()).isEqualTo("BTC-PERP");
    assertThat(ftxResponse.getResult().get(0).getSide()).isEqualTo(FtxOrderSide.buy);
    assertThat(ftxResponse.getResult().get(0).getType()).isEqualTo(FtxOrderType.limit);
    assertThat(ftxResponse.getResult().get(0).getStatus()).isEqualTo(FtxOrderStatus.NEW);
  }

  @Test
  public void userTradesDtoUnmarshall() throws IOException {
    // Read in the JSON from the example resources
    InputStream is =
        TradeDtosTest.class.getResourceAsStream("/responses/example-ftxOrderHistory.json");

    // Use Jackson to parse it
    ObjectMapper mapper = new ObjectMapper();
    FtxResponse<List<FtxOrderDto>> ftxResponse =
        mapper.readValue(is, new TypeReference<FtxResponse<List<FtxOrderDto>>>() {});

    // Verify that the example data was unmarshalled correctly
    assertThat(ftxResponse.getResult().size()).isEqualTo(1);

    assertThat(ftxResponse.getResult().get(0).getId()).isEqualTo(String.valueOf(257132591));
    assertThat(ftxResponse.getResult().get(0).getMarket()).isEqualTo("BTC-PERP");
    assertThat(ftxResponse.getResult().get(0).getSide()).isEqualTo(FtxOrderSide.buy);
    assertThat(ftxResponse.getResult().get(0).getType()).isEqualTo(FtxOrderType.limit);
    assertThat(ftxResponse.getResult().get(0).getStatus()).isEqualTo(FtxOrderStatus.NEW);
    assertThat(ftxResponse.getResult().get(0).getFuture()).isEqualTo("BTC-PERP");
  }

  @Test
  public void openConditionalOrdersDtoUnmarshall() throws IOException {
    // Read in the JSON from the example resources
    InputStream is =
        TradeDtosTest.class.getResourceAsStream("/responses/example-ftxConditionalOpenOrders.json");

    // Use Jackson to parse it
    ObjectMapper mapper = new ObjectMapper();
    FtxResponse<List<FtxConditionalOrderDto>> ftxResponse =
        mapper.readValue(is, new TypeReference<FtxResponse<List<FtxConditionalOrderDto>>>() {});

    // Verify that the example data was unmarshalled correctly
    assertThat(ftxResponse.getResult().size()).isEqualTo(1);
    assertThat(ftxResponse.getResult().get(0).getFuture()).isEqualTo("XRP-PERP");
    assertThat(ftxResponse.getResult().get(0).getId()).isEqualTo("50001");
    assertThat(ftxResponse.getResult().get(0).getMarket()).isEqualTo("XRP-PERP");
    assertThat(ftxResponse.getResult().get(0).getOrderPrice())
        .isEqualTo(BigDecimal.valueOf(321.12));
    assertThat(ftxResponse.getResult().get(0).isReduceOnly()).isEqualTo(false);
    assertThat(ftxResponse.getResult().get(0).getSide()).isEqualTo(FtxOrderSide.buy);
    assertThat(ftxResponse.getResult().get(0).getSize()).isEqualTo(BigDecimal.valueOf(0.003));
    assertThat(ftxResponse.getResult().get(0).getStatus()).isEqualTo(FtxOrderStatus.OPEN);
    assertThat(ftxResponse.getResult().get(0).getTrailStart())
        .isEqualTo(BigDecimal.valueOf(432.21));
    assertThat(ftxResponse.getResult().get(0).getTrailValue())
        .isEqualTo(BigDecimal.valueOf(654.43));
    assertThat(ftxResponse.getResult().get(0).getTriggerPrice())
        .isEqualTo(BigDecimal.valueOf(0.49));
    assertThat(ftxResponse.getResult().get(0).getType()).isEqualTo(FtxConditionalOrderType.stop);
    assertThat(ftxResponse.getResult().get(0).getOrderType()).isEqualTo(FtxOrderType.market);
    assertThat(ftxResponse.getResult().get(0).getFilledSize()).isEqualTo(BigDecimal.valueOf(42.42));
    assertThat(ftxResponse.getResult().get(0).getAvgFillPrice())
        .isEqualTo(BigDecimal.valueOf(234.65));
    assertThat(ftxResponse.getResult().get(0).isRetryUntilFilled()).isEqualTo(true);
  }

  @Test
  public void triggerDtoUnmarshall() throws IOException {
    // Read in the JSON from the example resources
    InputStream is =
        TradeDtosTest.class.getResourceAsStream(
            "/responses/example-ftxConditionalOrderTriggers.json");

    // Use Jackson to parse it
    ObjectMapper mapper = new ObjectMapper();
    FtxResponse<List<FtxTriggerDto>> ftxResponse =
        mapper.readValue(is, new TypeReference<FtxResponse<List<FtxTriggerDto>>>() {});

    // Verify that the example data was unmarshalled correctly
    assertThat(ftxResponse.getResult().size()).isEqualTo(1);
    assertThat(ftxResponse.getResult().get(0).getError()).isEqualTo("error");
    assertThat(ftxResponse.getResult().get(0).getFilledSize()).isEqualTo(BigDecimal.valueOf(4.0));
    assertThat(ftxResponse.getResult().get(0).getOrderSize()).isEqualTo(BigDecimal.valueOf(10.0));
    assertThat(ftxResponse.getResult().get(0).getOrderId()).isEqualTo("38066650");
  }
}
