package info.bitrich.xchangestream.kraken;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import info.bitrich.xchangestream.kraken.config.Config;
import info.bitrich.xchangestream.kraken.dto.response.KrakenDataMessage;
import info.bitrich.xchangestream.kraken.dto.response.KrakenMessage;
import info.bitrich.xchangestream.service.netty.NettyStreamingService;
import info.bitrich.xchangestream.service.netty.WebSocketClientCompressionAllowClientNoContextHandler;
import io.netty.handler.codec.http.websocketx.extensions.WebSocketClientExtensionHandler;
import java.io.IOException;
import java.util.List;
import lombok.extern.slf4j.Slf4j;
import org.knowm.xchange.currency.CurrencyPair;
import org.knowm.xchange.utils.ArrayUtils;

@Slf4j
public class KrakenStreamingService extends NettyStreamingService<KrakenMessage> {

  protected final ObjectMapper objectMapper = Config.getInstance().getObjectMapper();

  public KrakenStreamingService(String apiUri) {
    super(apiUri, Integer.MAX_VALUE);
  }

  @Override
  protected String getChannelNameFromMessage(KrakenMessage message) throws IOException {
    return message.getChannelId();
  }

  @Override
  public String getSubscribeMessage(String channelName, Object... args) throws IOException {
    CurrencyPair currencyPair = ArrayUtils.getElement(0, args, CurrencyPair.class, null);
    var message = KrakenStreamingAdapters.toSubscribeMessage(channelName, currencyPair);

    return objectMapper.writeValueAsString(message);
  }

  @Override
  protected WebSocketClientExtensionHandler getWebSocketClientExtensionHandler() {
    return WebSocketClientCompressionAllowClientNoContextHandler.INSTANCE;
  }

  @Override
  public String getSubscriptionUniqueId(String channelName, Object... args) {
    CurrencyPair currencyPair = ArrayUtils.getElement(0, args, CurrencyPair.class, null);
    return KrakenStreamingAdapters.toSubscriptionUniqueId(channelName, currencyPair);
  }

  @Override
  public String getUnsubscribeMessage(String subscriptionUniqueId, Object... args)
      throws IOException {
    var message = KrakenStreamingAdapters.toUnsubscribeMessage(subscriptionUniqueId);
    return objectMapper.writeValueAsString(message);
  }

  @Override
  public void messageHandler(String message) {
    log.debug("Received message: {}", message);

    try {
      KrakenMessage krakenMessage = objectMapper.readValue(message, KrakenMessage.class);

      // if there are several data entries split them and process separately
      if (krakenMessage instanceof KrakenDataMessage
          && ((KrakenDataMessage) krakenMessage).getData() != null
          && ((KrakenDataMessage) krakenMessage).getData().size() > 1) {

        KrakenDataMessage krakenDataMessage = (KrakenDataMessage) krakenMessage;

        for (int i = 0; i < krakenDataMessage.getData().size(); i++) {
          var currentDataEntry = krakenDataMessage.getData().get(i);
          var copiedDataMessage =
              krakenDataMessage.toBuilder().data(List.of(currentDataEntry)).build();
          handleMessage(copiedDataMessage);
        }

      } else {
        handleMessage(krakenMessage);
      }

    } catch (JsonProcessingException e) {
      log.error("Error parsing incoming message to JSON: {}", message);
      log.error(e.getMessage(), e);
    }
  }
}
