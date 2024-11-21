package info.bitrich.xchangestream.bitget;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ObjectNode;
import info.bitrich.xchangestream.bitget.config.Config;
import info.bitrich.xchangestream.bitget.dto.common.Action;
import info.bitrich.xchangestream.bitget.dto.common.BitgetChannel;
import info.bitrich.xchangestream.bitget.dto.common.Operation;
import info.bitrich.xchangestream.bitget.dto.request.BitgetWsRequest;
import info.bitrich.xchangestream.bitget.dto.response.BitgetEventNotification;
import info.bitrich.xchangestream.bitget.dto.response.BitgetWsNotification;
import info.bitrich.xchangestream.service.netty.NettyStreamingService;
import java.io.IOException;
import lombok.extern.slf4j.Slf4j;

@Slf4j
public class BitgetStreamingService extends NettyStreamingService<BitgetWsNotification> {

  protected final ObjectMapper objectMapper = Config.getInstance().getObjectMapper();

  public BitgetStreamingService(String apiUri) {
    super(apiUri, Integer.MAX_VALUE);
  }

  @Override
  protected String getChannelNameFromMessage(BitgetWsNotification message) {
    return BitgetStreamingAdapters.toSubscriptionId(message.getChannel());
  }

  /**
   * @param channelName ignored
   * @param args [{@code ChannelType}, {@code MarketType}, {@code Instrument}/{@code null}]
   * @return message to be sent for subscribing
   * @see BitgetStreamingAdapters#toSubscriptionId
   */
  @Override
  public String getSubscribeMessage(String channelName, Object... args) throws IOException {
    BitgetChannel bitgetChannel = BitgetStreamingAdapters.toBitgetChannel(args);

    BitgetWsRequest request =
        BitgetWsRequest.builder().operation(Operation.SUBSCRIBE).channel(bitgetChannel).build();
    return objectMapper.writeValueAsString(request);
  }

  /**
   * @param channelName ignored
   * @param args [{@code ChannelType}, {@code MarketType}, {@code Instrument}/{@code null}]
   * @return message to be sent for unsubscribing
   * @see BitgetStreamingAdapters#toSubscriptionId
   */
  @Override
  public String getUnsubscribeMessage(String channelName, Object... args) throws IOException {
    BitgetChannel bitgetChannel = BitgetStreamingAdapters.toBitgetChannel(args);

    BitgetWsRequest request =
        BitgetWsRequest.builder().operation(Operation.UNSUBSCRIBE).channel(bitgetChannel).build();
    return objectMapper.writeValueAsString(request);
  }

  @Override
  protected void handleMessage(BitgetWsNotification message) {
    log.debug("Processing {}", message.toString());
    // no special processing of event messages
    if (message instanceof BitgetEventNotification) {
      return;
    }
    super.handleMessage(message);
  }

  @Override
  protected void handleChannelMessage(String channel, BitgetWsNotification message) {
    if (message.getAction() == null || message.getAction() != Action.SNAPSHOT) {
      return;
    }
    super.handleChannelMessage(channel, message);
  }

  /**
   * @param channelName name of channel
   * @param args array with [{@code MarketType}, {@code Instrument}, ...]
   * @return subscription id in form of "marketType_channelName_instrument1_instrumentX"
   */
  @Override
  public String getSubscriptionUniqueId(String channelName, Object... args) {
    BitgetChannel bitgetChannel = BitgetStreamingAdapters.toBitgetChannel(args);

    return BitgetStreamingAdapters.toSubscriptionId(bitgetChannel);
  }

  @Override
  public void messageHandler(String message) {
    log.debug("Received message: {}", message);
    BitgetWsNotification bitgetWsNotification;

    // Parse incoming message to JSON
    try {
      JsonNode jsonNode = objectMapper.readTree(message);

      // try to parse event
      if (jsonNode.has("event")) {
        ((ObjectNode) jsonNode).put("messageType", "event");
      }
      // copy nested value of arg.channel to the root of json to detect deserialization type
      else if (jsonNode.has("arg") && jsonNode.get("arg").has("channel")) {
        ((ObjectNode) jsonNode).put("messageType", jsonNode.get("arg").get("channel").asText());
      }

      bitgetWsNotification = objectMapper.treeToValue(jsonNode, BitgetWsNotification.class);

    } catch (IOException e) {
      log.error("Error parsing incoming message to JSON: {}", message);
      log.error(e.getMessage(), e);
      return;
    }

    // if payload has several items process each item as a separate notification
    if (bitgetWsNotification.getPayloadItems() != null
        && bitgetWsNotification.getPayloadItems().size() > 1) {
      for (Object payloadItem : bitgetWsNotification.getPayloadItems()) {
        handleMessage(bitgetWsNotification.toBuilder().payloadItem(payloadItem).build());
      }
    } else {
      handleMessage(bitgetWsNotification);
    }
  }
}
