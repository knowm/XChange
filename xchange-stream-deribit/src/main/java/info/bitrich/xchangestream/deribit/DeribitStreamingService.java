package info.bitrich.xchangestream.deribit;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ObjectNode;
import info.bitrich.xchangestream.deribit.config.Config;
import info.bitrich.xchangestream.deribit.dto.request.DeribitWsRequest;
import info.bitrich.xchangestream.deribit.dto.request.DeribitWsRequest.Method;
import info.bitrich.xchangestream.deribit.dto.request.DeribitWsRequest.Params;
import info.bitrich.xchangestream.deribit.dto.response.DeribitEventNotification;
import info.bitrich.xchangestream.deribit.dto.response.DeribitWsNotification;
import info.bitrich.xchangestream.service.netty.NettyStreamingService;
import java.io.IOException;
import java.util.List;
import lombok.extern.slf4j.Slf4j;

@Slf4j
public class DeribitStreamingService extends NettyStreamingService<DeribitWsNotification> {

  protected final ObjectMapper objectMapper = Config.getInstance().getObjectMapper();

  public DeribitStreamingService(String apiUri) {
    super(apiUri, Integer.MAX_VALUE);
  }

  @Override
  protected String getChannelNameFromMessage(DeribitWsNotification message) {
    return message.getParams().getChannel();
  }

  @Override
  public String getSubscribeMessage(String channelName, Object... args) throws IOException {
    var deribitWsRequest =
        DeribitWsRequest.builder()
            .method(Method.SUBSCRIBE)
            .params(Params.builder().channels(List.of(channelName)).build())
            .build();
    return objectMapper.writeValueAsString(deribitWsRequest);
  }

  @Override
  public String getUnsubscribeMessage(String channelName, Object... args) throws IOException {
    var deribitWsRequest =
        DeribitWsRequest.builder()
            .method(Method.UNSUBSCRIBE)
            .params(Params.builder().channels(List.of(channelName)).build())
            .build();
    return objectMapper.writeValueAsString(deribitWsRequest);
  }

  @Override
  protected void handleMessage(DeribitWsNotification message) {
    log.debug("Processing {}", message.toString());
    // no special processing of event messages
    if (message instanceof DeribitEventNotification) {
      return;
    }
    super.handleMessage(message);
  }

  @Override
  public void messageHandler(String message) {
    log.debug("Received message: {}", message);
    DeribitWsNotification deribitWsNotification;

    // Parse incoming message to JSON
    try {
      JsonNode jsonNode = objectMapper.readTree(message);

      // try to parse event
      if (jsonNode.has("result")) {
        ((ObjectNode) jsonNode).put("messageType", "event");
      }
      // copy nested value of params.channel to the root of json to detect deserialization type
      else if (jsonNode.has("params") && jsonNode.get("params").has("channel")) {
        var channelWords = jsonNode.get("params").get("channel").asText().split("\\.");
        var channelText = channelWords[0];

        // if name starts with 'user.' it is a 2-words name
        if ("user".equals(channelWords[0]) && channelWords.length > 1) {
          channelText += "." + channelWords[1];
        }

        ((ObjectNode) jsonNode).put("messageType", channelText);
      }

      deribitWsNotification = objectMapper.treeToValue(jsonNode, DeribitWsNotification.class);

    } catch (IOException e) {
      log.error("Error parsing incoming message to JSON: {}", message);
      log.error(e.getMessage(), e);
      return;
    }

    if (deribitWsNotification.hasSinglePayload()) {
      handleMessage(deribitWsNotification);
    } else {
      // process several payloads separately
      ((List) deribitWsNotification.getParams().getData())
          .stream()
              .forEach(
                  payload -> {
                    var singleNotification = deribitWsNotification.toBuilder().build();
                    singleNotification.getParams().setData(List.of(payload));
                    handleMessage(singleNotification);
                  });
    }
  }
}
