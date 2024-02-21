package info.bitrich.xchangestream.gateio.dto.response;

import static org.assertj.core.api.Assertions.assertThat;

import com.fasterxml.jackson.databind.ObjectMapper;
import info.bitrich.xchangestream.gateio.config.Config;
import info.bitrich.xchangestream.gateio.dto.response.balance.GateioMultipleSpotBalanceNotification;
import info.bitrich.xchangestream.gateio.dto.response.orderbook.GateioOrderBookNotification;
import info.bitrich.xchangestream.gateio.dto.response.ticker.GateioTickerNotification;
import info.bitrich.xchangestream.gateio.dto.response.trade.GateioTradeNotification;
import info.bitrich.xchangestream.gateio.dto.response.usertrade.GateioMultipleUserTradeNotification;
import java.io.IOException;
import org.junit.jupiter.api.Test;

public class GateioWsNotificationTest {

  ObjectMapper objectMapper = Config.getInstance().getObjectMapper();

  @Test
  void deserialize_trades() throws Exception {
    GateioWsNotification notification = readNotification("spot.trades.update.json");
    assertThat(notification).isInstanceOf(GateioTradeNotification.class);
  }


  @Test
  void deserialize_ticker() throws Exception {
    GateioWsNotification notification = readNotification("spot.ticker.update.json");
    assertThat(notification).isInstanceOf(GateioTickerNotification.class);
  }


  @Test
  void deserialize_orderbook() throws Exception {
    GateioWsNotification notification = readNotification("spot.order_book.update.json");
    assertThat(notification).isInstanceOf(GateioOrderBookNotification.class);
  }


  @Test
  void deserialize_usertrades() throws Exception {
    GateioWsNotification notification = readNotification("spot.usertrades.update.json");
    assertThat(notification).isInstanceOf(GateioMultipleUserTradeNotification.class);
  }


  @Test
  void deserialize_balances() throws Exception {
    GateioWsNotification notification = readNotification("spot.balance.update.json");
    assertThat(notification).isInstanceOf(GateioMultipleSpotBalanceNotification.class);
  }


  private GateioWsNotification readNotification(String resourceName) throws IOException {
    return objectMapper.readValue(
        getClass().getClassLoader().getResourceAsStream(resourceName),
        GateioWsNotification.class
    );
  }


}