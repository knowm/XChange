package info.bitrich.xchangestream.gateio.dto.response;

import com.fasterxml.jackson.databind.ObjectMapper;
import info.bitrich.xchangestream.gateio.config.Config;
import info.bitrich.xchangestream.gateio.dto.response.wsPlaceOrder.GateioWsPlaceOrderSpotResponse;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

public class GateioWsPlaceOrderSpotResponseTest {

  private final ObjectMapper objectMapper = Config.getInstance().getObjectMapper();

  @Test
  void testDeserializeResult() throws Exception {
    String json = "{\"req_id\":\"1789075537148230\",\"req_param\":{\"text\":\"t-1789075537115246\",\"currency_pair\":\"SOL_USDT\",\"type\":\"market\",\"side\":\"buy\",\"amount\":\"4\",\"time_in_force\":\"ioc\"}}";
    GateioWsPlaceOrderSpotResponse.Result result = objectMapper.readValue(json, GateioWsPlaceOrderSpotResponse.Result.class);

    assertThat(result).isNotNull();
    assertThat(result.getReqId()).isEqualTo("1789075537148230");
    assertThat(result.getReqParam()).isNotNull();
    assertThat(result.getReqParam().getText()).isEqualTo("t-1789075537115246");
    assertThat(result.getReqParam().getCurrencyPair()).isEqualTo("SOL_USDT");
    assertThat(result.getReqParam().getType()).isEqualTo("market");
    assertThat(result.getReqParam().getSide()).isEqualTo("buy");
    assertThat(result.getReqParam().getAmount()).isEqualTo("4");
    assertThat(result.getReqParam().getTimeInForce()).isEqualTo("ioc");
  }

  @Test
  void testDeserializeFullResponse() throws Exception {
    String json = "{\n"
        + "  \"request_id\": \"1789075537148230\",\n"
        + "  \"ack\": true,\n"
        + "  \"header\": {\n"
        + "    \"status\": \"200\",\n"
        + "    \"channel\": \"spot.order_place\"\n"
        + "  },\n"
        + "  \"data\": {\n"
        + "    \"result\": {\n"
        + "      \"req_id\": \"1789075537148230\",\n"
        + "      \"req_param\": {\n"
        + "        \"text\": \"t-1789075537115246\",\n"
        + "        \"currency_pair\": \"SOL_USDT\",\n"
        + "        \"type\": \"market\",\n"
        + "        \"side\": \"buy\",\n"
        + "        \"amount\": \"4\",\n"
        + "        \"time_in_force\": \"ioc\"\n"
        + "      }\n"
        + "    }\n"
        + "  }\n"
        + "}";

    GateioWsPlaceOrderSpotResponse response = objectMapper.readValue(json, GateioWsPlaceOrderSpotResponse.class);

    assertThat(response).isNotNull();
    assertThat(response.getRequestId()).isEqualTo("1789075537148230");
    assertThat(response.getAck()).isTrue();
    assertThat(response.getHeader().getStatus()).isEqualTo("200");
    assertThat(response.getData().getResult().getReqId()).isEqualTo("1789075537148230");
    assertThat(response.getData().getResult().getReqParam().getCurrencyPair()).isEqualTo("SOL_USDT");
  }
}
