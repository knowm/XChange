package info.bitrich.xchangestream.okex;

import static org.assertj.core.api.Assertions.assertThat;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import info.bitrich.xchangestream.okex.dto.OkexSubscribeMessage;
import java.io.IOException;
import java.net.URISyntaxException;
import java.nio.file.Files;
import java.nio.file.Paths;
import org.junit.Before;
import org.junit.Test;

public class OkexStreamingServiceTest {

  private OkexStreamingService streamingService;

  @Before
  public void setUp() {
    streamingService =
        new OkexStreamingService("wss://wspap.okx.com:8443/ws/v5/public?brokerId=9999", null);
  }

  @Test
  public void testGetChannelNameFromMessage() throws Exception {
    String expected =
        new String(
            Files.readAllBytes(
                Paths.get(ClassLoader.getSystemResource("okex-ws-tickers-v5.json").toURI())));
    JsonNode data = new ObjectMapper().readTree(expected);
    String channel = streamingService.getChannelNameFromMessage(data);

    assertThat(channel).isEqualTo("tickers");
  }

  @Test
  public void testGetSubscribeMessage() throws IOException, URISyntaxException {
    OkexSubscribeMessage.SubscriptionTopic t =
        new OkexSubscribeMessage.SubscriptionTopic("tickers", null, null, "LTC-USD-200327");
    OkexSubscribeMessage message = new OkexSubscribeMessage();
    message.setOp("subscribe");
    message.getArgs().add(t);
    String subscribeMessage = streamingService.getSubscribeMessage("tickers", message);
    String expected =
        new String(
            Files.readAllBytes(
                Paths.get(
                    ClassLoader.getSystemResource("okex-ws-tickers-subscribe.json").toURI())));
    assertThat(subscribeMessage).isEqualTo(expected);
  }

  @Test
  public void testGetUnsubscribeMessage() throws IOException, URISyntaxException {
    OkexSubscribeMessage.SubscriptionTopic t =
        new OkexSubscribeMessage.SubscriptionTopic("tickers", null, null, "LTC-USD-200327");
    OkexSubscribeMessage message = new OkexSubscribeMessage();
    message.setOp("unsubscribe");
    message.getArgs().add(t);
    String subscribeMessage = streamingService.getUnsubscribeMessage("tickers", message);
    String expected =
        new String(
            Files.readAllBytes(
                Paths.get(
                    ClassLoader.getSystemResource("okex-ws-tickers-unsubscribe.json").toURI())));
    assertThat(subscribeMessage).isEqualTo(expected);
  }
}
