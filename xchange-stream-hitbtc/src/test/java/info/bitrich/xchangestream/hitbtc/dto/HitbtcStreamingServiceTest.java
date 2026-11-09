package info.bitrich.xchangestream.hitbtc.dto;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatExceptionOfType;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import info.bitrich.xchangestream.hitbtc.HitbtcStreamingService;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import org.apache.commons.lang3.reflect.MethodUtils;
import org.junit.jupiter.api.Test;

/** Created by Pavel Chertalev on 15.03.2018. */
public class HitbtcStreamingServiceTest {

  private final ObjectMapper objectMapper = new ObjectMapper();
  private final HitbtcStreamingService streamingService = new HitbtcStreamingService("testUrl");

  @Test
  void getChannelNameFromMessageTest() throws Exception {

    Method method =
        MethodUtils.getMatchingMethod(
            HitbtcStreamingService.class, "getChannelNameFromMessage", JsonNode.class);
    method.setAccessible(true);

    String json = "{\"method\":\"aaa\"}";
    assertThat(method.invoke(streamingService, objectMapper.readTree(json))).isEqualTo("aaa");

    json = "{ \"method\": \"updateOrderbook\", \"params\": { \"symbol\": \"ETHBTC\" } }";
    assertThat(method.invoke(streamingService, objectMapper.readTree(json)))
        .isEqualTo("orderbook-ETHBTC");

    json = "{ \"method\": \"snapshotOrderbook\", \"params\": { \"symbol\": \"ETHBTC\" } }";
    assertThat(method.invoke(streamingService, objectMapper.readTree(json)))
        .isEqualTo("orderbook-ETHBTC");

    json = "{ \"method\": \"test\", \"params\": { \"symbol\": \"ETHBTC\" } }";
    assertThat(method.invoke(streamingService, objectMapper.readTree(json)))
        .isEqualTo("test-ETHBTC");

    JsonNode noMethod = objectMapper.readTree("{ \"noMethod\": \"updateOrderbook\" } }");

    assertThatExceptionOfType(InvocationTargetException.class)
        .isThrownBy(() -> method.invoke(streamingService, noMethod));
  }
}
