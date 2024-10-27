package info.bitrich.xchangestream.bitget;


import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ObjectNode;
import info.bitrich.xchangestream.bitget.config.Config;
import info.bitrich.xchangestream.bitget.dto.common.Action;
import info.bitrich.xchangestream.bitget.dto.common.BitgetChannel;
import info.bitrich.xchangestream.bitget.dto.common.BitgetChannel.MarketType;
import info.bitrich.xchangestream.bitget.dto.common.Operation;
import info.bitrich.xchangestream.bitget.dto.request.BitgetWsRequest;
import info.bitrich.xchangestream.bitget.dto.response.BitgetWsNotification;
import info.bitrich.xchangestream.service.netty.NettyStreamingService;
import java.io.IOException;
import java.util.Arrays;
import java.util.stream.Collectors;
import java.util.stream.Stream;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.ArrayUtils;
import org.apache.commons.lang3.StringUtils;
import org.knowm.xchange.bitget.BitgetAdapters;
import org.knowm.xchange.instrument.Instrument;

@Slf4j
public class BitgetStreamingService extends NettyStreamingService<BitgetWsNotification> {

  private final String apiKey;

  private final ObjectMapper objectMapper = Config.getInstance().getObjectMapper();

  public BitgetStreamingService(String apiUri, String apiKey) {
    super(apiUri, Integer.MAX_VALUE);
    this.apiKey = apiKey;
  }


  @Override
  protected String getChannelNameFromMessage(BitgetWsNotification message) {
    return BitgetStreamingAdapters.toSubscriptionId(message.getChannel());
  }


  /**
   * @param subscriptionId unique id for subscription, already contains params
   * @param args ignored
   * @return message to be sent for subscribing
   * @see BitgetStreamingAdapters#toSubscriptionId
   */
  @Override
  public String getSubscribeMessage(String subscriptionId, Object... args) throws IOException {
    BitgetChannel bitgetChannel = BitgetStreamingAdapters.toBitgetChannel(subscriptionId);

    BitgetWsRequest request = BitgetWsRequest.builder()
        .operation(Operation.SUBSCRIBE)
        .channel(bitgetChannel)
        .build();
    return objectMapper.writeValueAsString(request);
  }


  /**
   * @param subscriptionId unique id for subscription, already contains params
   * @param args ignored
   * @return message to be sent for unsubscribing
   * @see BitgetStreamingAdapters#toSubscriptionId
   */
  @Override
  public String getUnsubscribeMessage(String subscriptionId, Object... args) throws IOException {
    BitgetChannel bitgetChannel = BitgetStreamingAdapters.toBitgetChannel(subscriptionId);

    BitgetWsRequest request = BitgetWsRequest.builder()
        .operation(Operation.UNSUBSCRIBE)
        .channel(bitgetChannel)
        .build();
    return objectMapper.writeValueAsString(request);
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
    if (args == null || args.length == 0) {
      return StringUtils.upperCase(channelName);
    }

    MarketType marketType = (MarketType) ArrayUtils.get(args, 0);

    Stream<String> arguments = Arrays.stream(args)
        .filter(Instrument.class::isInstance)
        .map(Instrument.class::cast)
        .map(BitgetAdapters::toString);

    return Stream.concat(Stream.of(marketType, channelName), arguments)
        .map(String::valueOf)
        .collect(Collectors.joining("_"));
  }


  @Override
  public void messageHandler(String message) {
    log.debug("Received message: {}", message);
    BitgetWsNotification bitgetWsNotification;

    // Parse incoming message to JSON
    try {
      JsonNode jsonNode = objectMapper.readTree(message);

      // copy nested value of arg.channel to the root of json so that the deserialization type is detected properly
      ((ObjectNode) jsonNode).put("messageType", jsonNode.get("arg").get("channel").asText());

      bitgetWsNotification = objectMapper.treeToValue(jsonNode, BitgetWsNotification.class);

    } catch (IOException e) {
      log.error("Error parsing incoming message to JSON: {}", message);
      return;
    }

    handleMessage(bitgetWsNotification);
  }


}
