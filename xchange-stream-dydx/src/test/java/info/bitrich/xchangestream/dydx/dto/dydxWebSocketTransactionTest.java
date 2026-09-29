package info.bitrich.xchangestream.dydx.dto;

import static org.assertj.core.api.Assertions.assertThat;

import com.google.common.io.CharStreams;
import info.bitrich.xchangestream.dydx.dto.v3.dydxInitialOrderBookMessage;
import info.bitrich.xchangestream.service.netty.StreamingObjectMapperHelper;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.Reader;
import org.junit.jupiter.api.Test;

class dydxWebSocketTransactionTest {
  @Test
  void webSocketTransactionDeserializationInitialOrderBook() throws Exception {
    InputStream is = getClass().getClassLoader().getResourceAsStream("v3/InitialOrderBook.json");
    String body = null;
    try (final Reader reader = new InputStreamReader(is)) {
      body = CharStreams.toString(reader);
    }

    dydxInitialOrderBookMessage message =
        StreamingObjectMapperHelper.getObjectMapper()
            .readValue(body, dydxInitialOrderBookMessage.class);

    // Message Metadata
    assertThat(message.getType()).isEqualTo("subscribed");
    assertThat(message.getConnectionId()).isEqualTo("aa4f40c5-6dfd-4ec0-ac73-2ee2a8bcc754");
    assertThat(message.getMessageId()).isEqualTo("1");
    assertThat(message.getChannel()).isEqualTo("v3_orderbook");
    assertThat(message.getId()).isEqualTo("BTC-USD");

    // Bids
    assertThat(message.getContents().getBids().length).isEqualTo(11);
    assertThat(message.getContents().getBids()[0].getPrice()).isEqualTo("47419");
    assertThat(message.getContents().getBids()[0].getSize()).isEqualTo("0.105");

    // Asks
    assertThat(message.getContents().getAsks().length).isEqualTo(6);
    assertThat(message.getContents().getAsks()[0].getPrice()).isEqualTo("47502");
    assertThat(message.getContents().getAsks()[0].getSize()).isEqualTo("0.105");
  }
}
