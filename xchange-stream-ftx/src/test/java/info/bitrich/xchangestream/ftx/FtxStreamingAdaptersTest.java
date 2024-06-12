package info.bitrich.xchangestream.ftx;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import java.io.IOException;
import java.io.InputStream;
import java.math.BigDecimal;
import org.junit.Test;
import org.knowm.xchange.dto.Order;
import org.knowm.xchange.dto.trade.UserTrade;

public class FtxStreamingAdaptersTest {

  @Test
  public void testAdaptOrders() throws IOException {
    // Read in the JSON from the example resources
    InputStream is =
        FtxStreamingMarketDataServiceIntegration.class.getResourceAsStream(
            "/ftxOrderStatusUpdate-example-1.json");

    // Use Jackson to parse it

    ObjectMapper mapper = new ObjectMapper();
    JsonNode node = mapper.readTree(is);

    Order order = FtxStreamingAdapters.adaptOrders(node);

    assertThat(order.getCumulativeAmount().compareTo(BigDecimal.ZERO)).isEqualTo(0);
    assertThat(order.getRemainingAmount().compareTo(new BigDecimal("0.001"))).isEqualTo(0);
  }

  @Test
  public void testAdaptOrders2() throws IOException {
    // Read in the JSON from the example resources
    InputStream is =
        FtxStreamingMarketDataServiceIntegration.class.getResourceAsStream(
            "/ftxOrderStatusUpdate-example-2.json");

    // Use Jackson to parse it

    ObjectMapper mapper = new ObjectMapper();
    JsonNode node = mapper.readTree(is);

    Order order = FtxStreamingAdapters.adaptOrders(node);

    assertThat(order.getRemainingAmount().compareTo(BigDecimal.ZERO)).isEqualTo(0);
    assertThat(order.getCumulativeAmount().compareTo(new BigDecimal("0.001"))).isEqualTo(0);
  }

  @Test
  public void testAdaptUserTrade() throws IOException {
    // Read in the JSON from the example resources
    InputStream is =
        FtxStreamingMarketDataServiceIntegration.class.getResourceAsStream(
            "/ftxTrade-example.json");

    // Use Jackson to parse it

    ObjectMapper mapper = new ObjectMapper();
    JsonNode node = mapper.readTree(is);

    UserTrade userTrade = FtxStreamingAdapters.adaptUserTrade(node);

    assertThat(userTrade.getOrderUserReference()).isEqualTo("381ba32f-deff-4791-851f-30efd8c858a9");
  }
}
