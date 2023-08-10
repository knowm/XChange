package info.bitrich.xchangestream.gateio;


import static org.assertj.core.api.Assertions.assertThat;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import info.bitrich.xchangestream.gateio.config.ObjecMapperHelper;
import org.junit.jupiter.api.Test;

public class GateioStreamingServiceTest {

  GateioStreamingService gateioStreamingService = new GateioStreamingService("");
  ObjectMapper objectMapper = ObjecMapperHelper.getObjectMapper();

  @Test
  void channel_name_from_orderbook_update() throws Exception {
    JsonNode orderBookUpdate = objectMapper.readTree(getClass().getClassLoader().getResourceAsStream("spot.order_book.update.json"));
    String actual = gateioStreamingService.getChannelNameFromMessage(orderBookUpdate);
    assertThat(actual).isEqualTo("spot.order_book-BTC_USDT");
  }


  @Test
  void channel_name_from_ticker_update() throws Exception {
    JsonNode orderBookUpdate = objectMapper.readTree(getClass().getClassLoader().getResourceAsStream("spot.ticker.update.json"));
    String actual = gateioStreamingService.getChannelNameFromMessage(orderBookUpdate);
    assertThat(actual).isEqualTo("spot.tickers-BTC_USDT");
  }


  @Test
  void channel_name_from_trade_update() throws Exception {
    JsonNode orderBookUpdate = objectMapper.readTree(getClass().getClassLoader().getResourceAsStream("spot.trades.update.json"));
    String actual = gateioStreamingService.getChannelNameFromMessage(orderBookUpdate);
    assertThat(actual).isEqualTo("spot.trades-BTC_USDT");
  }


  @Test
  void empty_channel_name_from_subscribe_event() throws Exception {
    JsonNode orderBookUpdate = objectMapper.readTree(getClass().getClassLoader().getResourceAsStream("subscribe.event.json"));
    String actual = gateioStreamingService.getChannelNameFromMessage(orderBookUpdate);
    assertThat(actual).isEqualTo("spot.order_book-");
  }


  @Test
  void empty_channel_name_from_unsubscribe_event() throws Exception {
    JsonNode orderBookUpdate = objectMapper.readTree(getClass().getClassLoader().getResourceAsStream("unsubscribe.event.json"));
    String actual = gateioStreamingService.getChannelNameFromMessage(orderBookUpdate);
    assertThat(actual).isEqualTo("spot.trades-");
  }


}