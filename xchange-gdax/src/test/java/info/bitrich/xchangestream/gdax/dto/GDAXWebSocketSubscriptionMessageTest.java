package info.bitrich.xchangestream.gdax.dto;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.Assert;
import org.junit.Test;

/**
 * Created by luca on 5/3/17.
 */
public class GDAXWebSocketSubscriptionMessageTest {

  @Test
  public void testWebSocketMessageSerialization() throws JsonProcessingException {
    
    GDAXWebSocketSubscriptionMessage message = new GDAXWebSocketSubscriptionMessage("subscribe", "BTC-USD");

    final ObjectMapper mapper = new ObjectMapper();
    mapper.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, true);
    
    String serialized = mapper.writeValueAsString(message);

    Assert.assertEquals("{\"type\":\"subscribe\",\"product_ids\":[\"BTC-USD\"],\"channels\":[\"level2\",\"ticker\",\"matches\"]}", serialized);
    
  }
}
