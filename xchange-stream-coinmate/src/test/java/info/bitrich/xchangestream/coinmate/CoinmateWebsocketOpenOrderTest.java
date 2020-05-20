package info.bitrich.xchangestream.coinmate;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;

import com.fasterxml.jackson.core.type.TypeReference;
import info.bitrich.xchangestream.coinmate.dto.CoinmateWebsocketOpenOrder;
import info.bitrich.xchangestream.service.netty.StreamingObjectMapperHelper;
import java.io.IOException;
import java.util.List;
import org.junit.Test;

public class CoinmateWebsocketOpenOrderTest {

  @Test
  public void coinmateWebsocketOpenOrdersTest() throws IOException {
    String message =
        StreamingObjectMapperHelper.getObjectMapper()
            .readTree(this.getClass().getResource("/open-orders.json").openStream())
            .toString();

    List<CoinmateWebsocketOpenOrder> websocketOpenOrders =
        StreamingObjectMapperHelper.getObjectMapper()
            .readValue(message, new TypeReference<List<CoinmateWebsocketOpenOrder>>() {});

    assertThat(websocketOpenOrders).isNotNull();
    assertThat(websocketOpenOrders.size()).isEqualTo(2);
    assertThat(websocketOpenOrders.get(0).getId()).isEqualTo("11111111");
    assertThat(websocketOpenOrders.get(0).getTimestamp()).isEqualTo(1567331368945L);
    assertThat(websocketOpenOrders.get(0).getPrice()).isEqualTo(8780);
    assertThat(websocketOpenOrders.get(0).getOriginalOrderSize()).isEqualTo(0.5);
    assertThat(websocketOpenOrders.get(0).getAmount()).isEqualTo(0.5);
    assertThat(websocketOpenOrders.get(0).getAmount()).isEqualTo(0.5);
    assertThat(websocketOpenOrders.get(0).isTrailing()).isFalse();
    assertThat(websocketOpenOrders.get(0).isHidden()).isFalse();
  }
}
