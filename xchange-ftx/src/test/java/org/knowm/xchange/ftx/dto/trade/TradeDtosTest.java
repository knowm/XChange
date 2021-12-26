package org.knowm.xchange.ftx.dto.trade;

import static org.assertj.core.api.Assertions.assertThat;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import java.io.IOException;
import java.io.InputStream;
import java.util.List;
import org.junit.Test;
import org.knowm.xchange.dto.Order;
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
    assertThat(ftxResponse.getResult().get(0).getStatus()).isEqualTo(Order.OrderStatus.NEW);
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
    assertThat(ftxResponse.getResult().get(0).getStatus()).isEqualTo(Order.OrderStatus.NEW);
    assertThat(ftxResponse.getResult().get(0).getFuture()).isEqualTo("BTC-PERP");
  }
}
