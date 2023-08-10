package info.bitrich.xchangestream.gateio.dto.response;

import static org.assertj.core.api.Assertions.assertThat;

import com.fasterxml.jackson.databind.ObjectMapper;
import info.bitrich.xchangestream.gateio.config.Config;
import info.bitrich.xchangestream.gateio.dto.response.orderbook.GateioOrderBookNotification;
import info.bitrich.xchangestream.gateio.dto.response.ticker.GateioTickerNotification;
import info.bitrich.xchangestream.gateio.dto.response.trade.GateioTradeNotification;
import org.junit.jupiter.api.Test;

public class GateioWebSocketNotificationTest {

  ObjectMapper objectMapper = Config.getObjectMapper();

  @Test
  void deserialize_trades() throws Exception {
    GateioWebSocketNotification notification = objectMapper.readValue(
        getClass().getClassLoader().getResourceAsStream("spot.trades.update.json"),
        GateioWebSocketNotification.class);
    assertThat(notification).isInstanceOf(GateioTradeNotification.class);
  }


  @Test
  void deserialize_ticker() throws Exception {
    GateioWebSocketNotification notification = objectMapper.readValue(
        getClass().getClassLoader().getResourceAsStream("spot.ticker.update.json"),
        GateioWebSocketNotification.class);
    assertThat(notification).isInstanceOf(GateioTickerNotification.class);
  }


  @Test
  void deserialize_orderbook() throws Exception {
    GateioWebSocketNotification notification = objectMapper.readValue(
        getClass().getClassLoader().getResourceAsStream("spot.order_book.update.json"),
        GateioWebSocketNotification.class);
    assertThat(notification).isInstanceOf(GateioOrderBookNotification.class);
  }


}