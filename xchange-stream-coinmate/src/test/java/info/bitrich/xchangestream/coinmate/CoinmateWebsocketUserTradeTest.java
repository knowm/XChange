package info.bitrich.xchangestream.coinmate;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;

import com.fasterxml.jackson.core.type.TypeReference;
import info.bitrich.xchangestream.coinmate.dto.CoinmateWebSocketUserTrade;
import info.bitrich.xchangestream.service.netty.StreamingObjectMapperHelper;
import java.io.IOException;
import java.util.List;
import org.junit.Test;

public class CoinmateWebsocketUserTradeTest {

  @Test
  public void coinmateWebsocketOpenOrdersTest() throws IOException {
    String message =
        StreamingObjectMapperHelper.getObjectMapper()
            .readTree(this.getClass().getResource("/user-trade.json").openStream())
            .toString();

    List<CoinmateWebSocketUserTrade> websocketUserTrades =
        StreamingObjectMapperHelper.getObjectMapper()
            .readValue(message, new TypeReference<List<CoinmateWebSocketUserTrade>>() {});

    assertThat(websocketUserTrades).isNotNull();
    assertThat(websocketUserTrades.size()).isEqualTo(1);
    assertThat(websocketUserTrades.get(0).getTransactionId()).isEqualTo("11111111");
    assertThat(websocketUserTrades.get(0).getTimestamp()).isEqualTo(1567339757594L);
    assertThat(websocketUserTrades.get(0).getPrice()).isEqualTo(8741.2);
    assertThat(websocketUserTrades.get(0).getAmount()).isEqualTo(0.555);
    assertThat(websocketUserTrades.get(0).getBuyOrderId()).isEqualTo("11111111");
    assertThat(websocketUserTrades.get(0).getSellOrderId()).isEqualTo("11111112");
    assertThat(websocketUserTrades.get(0).getTakerOrderType()).isEqualTo("SELL");
    assertThat(websocketUserTrades.get(0).getUserOrderType()).isEqualTo("SELL");
    assertThat(websocketUserTrades.get(0).getFee()).isEqualTo(0.00600850);
    assertThat(websocketUserTrades.get(0).getUserFeeType()).isEqualTo("TAKER");
  }
}
