package info.bitrich.xchangestream.kraken.dto.response;

import static org.assertj.core.api.Assertions.assertThat;

import com.fasterxml.jackson.databind.ObjectMapper;
import info.bitrich.xchangestream.kraken.config.Config;
import org.junit.jupiter.api.Test;

class KrakenMessageTest {

  @Test
  void render() throws Exception {
    ObjectMapper om = Config.getInstance().getObjectMapper();

    assertThat(
            om.readValue(
                getClass().getResource("/sample-messages/status.json"), KrakenMessage.class))
        .isInstanceOf(KrakenStatusMessage.class);
    assertThat(
            om.readValue(
                getClass().getResource("/sample-messages/method.json"), KrakenMessage.class))
        .isInstanceOf(KrakenControlMessage.class);
    assertThat(
            om.readValue(
                getClass().getResource("/sample-messages/heartbeat.json"), KrakenMessage.class))
        .isInstanceOf(KrakenHeartbeatMessage.class);
    assertThat(
            om.readValue(
                getClass().getResource("/sample-messages/trade.json"), KrakenMessage.class))
        .isInstanceOf(KrakenTradeMessage.class);
    assertThat(
            om.readValue(
                getClass().getResource("/sample-messages/usertrade.json"), KrakenMessage.class))
        .isInstanceOf(KrakenExecutionsMessage.class);
  }
}
