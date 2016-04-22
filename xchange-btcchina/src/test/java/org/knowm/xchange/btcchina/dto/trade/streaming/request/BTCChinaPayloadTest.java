package org.knowm.xchange.btcchina.dto.trade.streaming.request;

import static org.junit.Assert.assertEquals;

import org.junit.Test;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

public class BTCChinaPayloadTest {

  private ObjectMapper mapper = new ObjectMapper();

  @Test
  public void test() throws JsonProcessingException {

    final long tonce = 0;
    final String accessKey = "myAccessKey";
    final String market = "cnybtc";

    BTCChinaPayload payload = new BTCChinaPayload(0, "myAccessKey", "post", "subscribe", new String[] { "order_cnybtc" });
    String payloadString = mapper.writeValueAsString(payload);

    String postdata = String.format(
        "{\"tonce\":\"%1$d\",\"accesskey\":\"%2$s\",\"requestmethod\":\"post\",\"id\":\"%1$s\",\"method\":\"subscribe\",\"params\":[\"order_%3$s\"]}",
        tonce, accessKey, market);

    assertEquals(postdata, payloadString);
  }

}
