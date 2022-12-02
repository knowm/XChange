package info.bitrich.xchangestream.huobi;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.node.JsonNodeFactory;
import com.fasterxml.jackson.databind.node.ObjectNode;
import info.bitrich.xchangestream.service.netty.JsonNettyStreamingService;
import info.bitrich.xchangestream.service.netty.WebSocketClientHandler;
import info.bitrich.xchangestream.service.netty.WebSocketClientHandler.WebSocketMessageHandler;
import io.netty.buffer.ByteBuf;
import io.netty.buffer.ByteBufInputStream;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.http.websocketx.BinaryWebSocketFrame;
import io.netty.handler.codec.http.websocketx.WebSocketClientHandshaker;
import io.netty.handler.codec.http.websocketx.WebSocketFrame;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.time.Duration;
import java.util.zip.DataFormatException;
import java.util.zip.GZIPInputStream;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class HuobiStreamingService extends JsonNettyStreamingService {

  private final Logger LOG = LoggerFactory.getLogger(this.getClass());

  public HuobiStreamingService(String apiUrl) {
    super(apiUrl, Integer.MAX_VALUE, Duration.ofSeconds(5), Duration.ofSeconds(20), 20);
  }

  @Override
  protected String getChannelNameFromMessage(JsonNode message) throws IOException {
    String status = null;
    String ch = null;

    if (message.has("ch")) {
      ch = message.get("ch").textValue();
    }
    if (message.has("status")) {
      status = message.get("status").textValue();
    }
    if (message.has("ping")) {
      long ping = message.get("ping").longValue();
      sendMessage("{\"pong\": " + ping + "}");
      return null;
    }
    if ("ok".equals(status)) {
      String subbed = message.get("subbed").textValue();
      LOG.debug("Subscribe [{}] is ok", subbed);
      return null;
    }
    if (message.has("err-msg")) {
      LOG.warn("error: {}", message.get("err-msg").textValue());
    }
    return ch;
  }

  /**
   * @param channelName market.$symbol.depth.$type
   * @param args null
   * @return
   * @throws IOException
   */
  @Override
  public String getSubscribeMessage(String channelName, Object... args) throws IOException {
    ObjectNode send = JsonNodeFactory.instance.objectNode();
    send.put("sub", channelName);
    send.put("id", System.currentTimeMillis());
    return send.toString();
  }

  @Override
  public String getUnsubscribeMessage(String channelName, Object... args) throws IOException {
    return channelName;
  }

  @Override
  protected WebSocketClientHandler getWebSocketClientHandler(
      WebSocketClientHandshaker handshaker, WebSocketMessageHandler handler) {
    return new HuobiWebSocketClientHandler(handshaker, handler);
  }

  /**
   * Custom client handler in order to execute an external, user-provided handler on channel events.
   * This is useful because it seems Kraken unexpectedly closes the web socket connection.
   */
  private class HuobiWebSocketClientHandler extends NettyWebSocketClientHandler {
    public HuobiWebSocketClientHandler(
        WebSocketClientHandshaker handshaker, WebSocketMessageHandler handler) {
      super(handshaker, handler);
    }

    @Override
    public void channelRead0(ChannelHandlerContext ctx, Object msg) throws Exception {

      if (!handshaker.isHandshakeComplete()) {
        super.channelRead0(ctx, msg);
        return;
      }
      super.channelRead0(ctx, msg);

      WebSocketFrame frame = (WebSocketFrame) msg;
      if (frame instanceof BinaryWebSocketFrame) {
        BinaryWebSocketFrame binaryFrame = (BinaryWebSocketFrame) frame;
        ByteBuf byteBuf = binaryFrame.content();
        try {
          handler.onMessage(decodeByteBuff(byteBuf));
        } catch (Exception e) {
          LOG.warn("Error when inflate websocket binary message: {}", e.toString());
        }
      }
    }

    public String decodeByteBuff(ByteBuf buf) throws IOException, DataFormatException {
      StringBuilder sb = new StringBuilder();

      byte[] temp = new byte[1024];
      try (GZIPInputStream biz = new GZIPInputStream(new ByteBufInputStream(buf))) {
        int data = biz.read(temp);
        while (data != -1) {
          sb.append(new String(temp, 0, data, StandardCharsets.UTF_8));
          data = biz.read(temp);
        }
      }
      return sb.toString();
    }
  }
}
