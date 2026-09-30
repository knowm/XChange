package info.bitrich.xchangestream.okcoin;

import static org.assertj.core.api.Assertions.assertThat;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import java.nio.file.Files;
import java.nio.file.Paths;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class OkCoinStreamingServiceTest {

  private OkCoinStreamingService streamingService;

  @BeforeEach
  void setUp() throws Exception {
    streamingService = new OkCoinStreamingService("wss://example.com/websocket");
  }

  @Test
  void getSubscribeMessage() throws Exception {
    String subscribeMessage = streamingService.getSubscribeMessage("ok_sub_spot_btc_usd_depth");
    String expected =
        new String(
            Files.readAllBytes(Paths.get(ClassLoader.getSystemResource("subscribe.json").toURI())));
    assertThat(subscribeMessage).isEqualTo(expected);
  }

  @Test
  void getUnsubscribeMessage() throws Exception {
    String subscribeMessage = streamingService.getUnsubscribeMessage("orderbook");
    String expected =
        new String(
            Files.readAllBytes(
                Paths.get(ClassLoader.getSystemResource("unsubscribe.json").toURI())));
    assertThat(subscribeMessage).isEqualTo(expected);
  }

  @Test
  void getChannelFromMessage() throws Exception {
    String expected =
        new String(
            Files.readAllBytes(
                Paths.get(ClassLoader.getSystemResource("order-book.json").toURI())));
    JsonNode data = new ObjectMapper().readTree(expected);
    String channel = streamingService.getChannelNameFromMessage(data);

    assertThat(channel).isEqualTo("ok_sub_spot_btc_usd_depth");
  }
}
