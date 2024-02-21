package info.bitrich.xchangestream.gateio;


import static org.assertj.core.api.Assertions.assertThat;

import com.fasterxml.jackson.databind.ObjectMapper;
import info.bitrich.xchangestream.gateio.config.Config;
import info.bitrich.xchangestream.gateio.dto.response.GateioWsNotification;
import java.io.IOException;
import org.junit.jupiter.api.Test;

public class GateioStreamingServiceTest {

  GateioStreamingService gateioStreamingService = new GateioStreamingService("", null, null);
  ObjectMapper objectMapper = Config.getInstance().getObjectMapper();

  @Test
  void channel_name_from_orderbook_update() throws Exception {
    GateioWsNotification notification = readNotification("spot.order_book.update.json");
    String actual = gateioStreamingService.getChannelNameFromMessage(notification);
    assertThat(actual).isEqualTo("spot.order_book-BTC/USDT");
  }


  @Test
  void channel_name_from_ticker_update() throws Exception {
    GateioWsNotification notification = readNotification("spot.ticker.update.json");
    String actual = gateioStreamingService.getChannelNameFromMessage(notification);
    assertThat(actual).isEqualTo("spot.tickers-BTC/USDT");
  }


  @Test
  void channel_name_from_trade_update() throws Exception {
    GateioWsNotification notification = readNotification("spot.trades.update.json");
    String actual = gateioStreamingService.getChannelNameFromMessage(notification);
    assertThat(actual).isEqualTo("spot.trades-BTC/USDT");
  }


  @Test
  void channel_name_from_subscribe_event() throws Exception {
    GateioWsNotification notification = readNotification("subscribe.event.json");
    String actual = gateioStreamingService.getChannelNameFromMessage(notification);
    assertThat(actual).isEqualTo("spot.order_book");
  }


  @Test
  void channel_name_from_unsubscribe_event() throws Exception {
    GateioWsNotification notification = readNotification("unsubscribe.event.json");
    String actual = gateioStreamingService.getChannelNameFromMessage(notification);
    assertThat(actual).isEqualTo("spot.trades");
  }

  private GateioWsNotification readNotification(String resourceName) throws IOException {
    return objectMapper.readValue(
        getClass().getClassLoader().getResourceAsStream(resourceName),
        GateioWsNotification.class
    );
  }


}