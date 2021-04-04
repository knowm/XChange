package info.bitrich.xchangestream.dydx.dto;

import com.google.common.io.CharStreams;
import info.bitrich.xchangestream.dydx.dto.v3.dydxInitialOrderBookMessage;
import info.bitrich.xchangestream.service.netty.StreamingObjectMapperHelper;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.Reader;
import org.junit.Assert;
import org.junit.Test;

public class dydxWebSocketTransactionTest {
  @Test
  public void testWebSocketTransactionDeserialization_initialOrderBook() throws Exception {
    InputStream is = getClass().getClassLoader().getResourceAsStream("v3/InitialOrderBook.json");
    String body = null;
    try (final Reader reader = new InputStreamReader(is)) {
      body = CharStreams.toString(reader);
    }

    dydxInitialOrderBookMessage message =
        StreamingObjectMapperHelper.getObjectMapper()
            .readValue(body, dydxInitialOrderBookMessage.class);

    // Message Metadata
    Assert.assertEquals("subscribed", message.getType());
    Assert.assertEquals("aa4f40c5-6dfd-4ec0-ac73-2ee2a8bcc754", message.getConnectionId());
    Assert.assertEquals("1", message.getMessageId());
    Assert.assertEquals("v3_orderbook", message.getChannel());
    Assert.assertEquals("BTC-USD", message.getId());

    // Bids
    Assert.assertEquals(11, message.getContents().getBids().length);
    Assert.assertEquals("47419", message.getContents().getBids()[0].getPrice());
    Assert.assertEquals("0.105", message.getContents().getBids()[0].getSize());

    // Asks
    Assert.assertEquals(6, message.getContents().getAsks().length);
    Assert.assertEquals("47502", message.getContents().getAsks()[0].getPrice());
    Assert.assertEquals("0.105", message.getContents().getAsks()[0].getSize());
  }
}
