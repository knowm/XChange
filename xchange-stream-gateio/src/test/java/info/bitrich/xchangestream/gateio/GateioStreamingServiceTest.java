package info.bitrich.xchangestream.gateio;

import com.fasterxml.jackson.databind.ObjectMapper;
import info.bitrich.xchangestream.gateio.config.Config;
import info.bitrich.xchangestream.gateio.dto.response.GateioWsNotification;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.knowm.xchange.ExchangeSpecification;

import java.io.IOException;

import static info.bitrich.xchangestream.core.StreamingExchange.*;
import static info.bitrich.xchangestream.service.netty.NettyStreamingService.*;
import static org.assertj.core.api.Assertions.assertThat;

public class GateioStreamingServiceTest {
  static ExchangeSpecification exchangeSpecification = new ExchangeSpecification(GateioStreamingExchange.class);
  static GateioStreamingService gateioStreamingService;
  ObjectMapper objectMapper = Config.getInstance().getObjectMapper();

  @BeforeAll
  public static void init() {
    exchangeSpecification.setExchangeSpecificParametersItem(WS_CONNECTION_TIMEOUT, DEFAULT_CONNECTION_TIMEOUT);
    exchangeSpecification.setExchangeSpecificParametersItem(WS_RETRY_DURATION, DEFAULT_RETRY_DURATION);
    exchangeSpecification.setExchangeSpecificParametersItem(WS_IDLE_TIMEOUT, DEFAULT_IDLE_TIMEOUT);
    gateioStreamingService = new GateioStreamingService("", null, null, exchangeSpecification);
  }

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
    assertThat(actual).isEqualTo("spot.trades-GT/USDT");
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
        getClass().getClassLoader().getResourceAsStream(resourceName), GateioWsNotification.class);
  }
}
