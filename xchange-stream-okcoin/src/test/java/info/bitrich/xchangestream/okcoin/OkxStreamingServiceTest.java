package info.bitrich.xchangestream.okcoin;

import static org.assertj.core.api.Assertions.assertThat;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import info.bitrich.xchangestream.okcoin.dto.okx.OkxSubscribeMessage;
import java.io.IOException;
import java.net.URISyntaxException;
import java.nio.file.Files;
import java.nio.file.Paths;
import org.junit.Before;
import org.junit.Test;

public class OkxStreamingServiceTest {

  private OkxStreamingService streamingService;

  @Before
  public void setUp() {
    streamingService =
        new OkxStreamingService("wss://wspap.okx.com:8443/ws/v5/public?brokerId=9999", null);
  }

  @Test
  public void testGetChannelNameFromMessage() throws Exception {
    String expected =
        new String(
            Files.readAllBytes(
                Paths.get(ClassLoader.getSystemResource("okx-ws-tickers-v5.json").toURI())));
    JsonNode data = new ObjectMapper().readTree(expected);
    String channel = streamingService.getChannelNameFromMessage(data);

    assertThat(channel).isEqualTo("tickers");
  }

  @Test
  public void testGetSubscribeMessage() throws IOException, URISyntaxException {
    OkxSubscribeMessage.SubscriptionTopic t =
        new OkxSubscribeMessage.SubscriptionTopic("tickers", null, null, "LTC-USD-200327");
    OkxSubscribeMessage message = new OkxSubscribeMessage();
    message.setOp("subscribe");
    message.getArgs().add(t);
    String subscribeMessage = streamingService.getSubscribeMessage("tickers", message);
    String expected =
        new String(
            Files.readAllBytes(
                Paths.get(ClassLoader.getSystemResource("okx-ws-tickers-subscribe.json").toURI())));
    assertThat(subscribeMessage).isEqualTo(expected);
  }

  @Test
  public void testGetUnsubscribeMessage() throws IOException, URISyntaxException {
    OkxSubscribeMessage.SubscriptionTopic t =
        new OkxSubscribeMessage.SubscriptionTopic("tickers", null, null, "LTC-USD-200327");
    OkxSubscribeMessage message = new OkxSubscribeMessage();
    message.setOp("unsubscribe");
    message.getArgs().add(t);
    String subscribeMessage = streamingService.getUnsubscribeMessage("tickers", message);
    String expected =
        new String(
            Files.readAllBytes(
                Paths.get(
                    ClassLoader.getSystemResource("okx-ws-tickers-unsubscribe.json").toURI())));
    assertThat(subscribeMessage).isEqualTo(expected);
  }
}
