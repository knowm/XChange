package info.bitrich.xchangestream.lgo;

import static info.bitrich.xchangestream.lgo.TestUtils.*;
import static org.assertj.core.api.Assertions.*;

import com.fasterxml.jackson.databind.JsonNode;
import java.io.IOException;
import org.junit.*;
import org.knowm.xchange.ExchangeSpecification;
import org.knowm.xchange.lgo.LgoEnv;
import org.knowm.xchange.lgo.LgoEnv.SignatureService;
import org.knowm.xchange.lgo.service.LgoSignatureService;

public class LgoStreamingServiceTest {

  private LgoStreamingService service;

  @Before
  public void setUp() {
    ExchangeSpecification env = LgoEnv.sandbox();
    env.getExchangeSpecificParameters()
        .put(LgoEnv.SIGNATURE_SERVICE, SignatureService.PASSTHROUGHS);
    service = new LgoStreamingService(LgoSignatureService.createInstance(env), "apiUrl");
  }

  @Test
  public void it_returns_the_trades_subscribe_message() throws IOException {
    String channelName = "trades-BTC-USD";

    String subscribeMessage = service.getSubscribeMessage(channelName);

    assertThat(asJsonNode(subscribeMessage))
        .isEqualTo(getJsonContent("/subscribe/trades-subscribe.json"));
  }

  @Test
  public void it_returns_the_level2_subscribe_message() throws IOException {
    String channelName = "level2-BTC-USD";

    String subscribeMessage = service.getSubscribeMessage(channelName);

    assertThat(asJsonNode(subscribeMessage))
        .isEqualTo(getJsonContent("/subscribe/level2-subscribe.json"));
  }

  @Test
  public void it_returns_the_balance_subscribe_message() throws IOException {
    String channelName = "balance";

    String subscribeMessage = service.getSubscribeMessage(channelName);

    assertThat(asJsonNode(subscribeMessage))
        .isEqualTo(getJsonContent("/subscribe/balance-subscribe.json"));
  }

  @Test
  public void it_returns_the_right_channel_name_for_trades() throws IOException {
    JsonNode message = getJsonContent("/marketdata/trades-update.json");

    String name = service.getChannelNameFromMessage(message);

    assertThat(name).isEqualTo("trades-BTC-USD");
  }

  @Test
  public void it_returns_the_right_channel_name_for_level2() throws IOException {
    JsonNode message = getJsonContent("/marketdata/level2-snapshot.json");

    String name = service.getChannelNameFromMessage(message);

    assertThat(name).isEqualTo("level2-BTC-USD");
  }

  @Test
  public void it_returns_the_right_channel_name_for_balances() throws IOException {
    JsonNode message = getJsonContent("/account/balance-snapshot.json");

    String name = service.getChannelNameFromMessage(message);

    assertThat(name).isEqualTo("balance");
  }

  @Test
  public void it_returns_the_right_channel_name_for_user() throws IOException {
    JsonNode message = getJsonContent("/trade/orders-snapshot.json");

    String name = service.getChannelNameFromMessage(message);

    assertThat(name).isEqualTo("user-BTC-USD");
  }
}
