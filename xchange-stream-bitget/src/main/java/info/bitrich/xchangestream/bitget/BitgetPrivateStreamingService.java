package info.bitrich.xchangestream.bitget;

import info.bitrich.xchangestream.bitget.config.Config;
import info.bitrich.xchangestream.bitget.dto.common.Operation;
import info.bitrich.xchangestream.bitget.dto.request.BitgetLoginRequest;
import info.bitrich.xchangestream.bitget.dto.request.BitgetLoginRequest.LoginPayload;
import info.bitrich.xchangestream.bitget.dto.response.BitgetEventNotification;
import info.bitrich.xchangestream.bitget.dto.response.BitgetEventNotification.Event;
import info.bitrich.xchangestream.bitget.dto.response.BitgetWsNotification;
import java.io.IOException;
import java.time.Instant;
import java.util.Map.Entry;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;

@Slf4j
public class BitgetPrivateStreamingService extends BitgetStreamingService {

  private final String apiKey;
  private final String apiSecret;
  private final String apiPassword;

  public BitgetPrivateStreamingService(
      String apiUri, String apiKey, String apiSecret, String apiPassword) {
    super(apiUri);
    this.apiKey = apiKey;
    this.apiSecret = apiSecret;
    this.apiPassword = apiPassword;
  }

  /** Sends login message right after connecting */
  @Override
  public void resubscribeChannels() {
    sendLoginMessage();
  }

  public void resubscribeChannelsAfterLogin() {
    for (Entry<String, Subscription> entry : channels.entrySet()) {
      try {
        Subscription subscription = entry.getValue();
        sendMessage(getSubscribeMessage(subscription.getChannelName(), subscription.getArgs()));
      } catch (IOException e) {
        log.error("Failed to reconnect channel: {}", entry.getKey());
      }
    }
  }

  @SneakyThrows
  private void sendLoginMessage() {
    Instant timestamp = Instant.now(Config.getInstance().getClock());
    BitgetLoginRequest bitgetLoginRequest =
        BitgetLoginRequest.builder()
            .operation(Operation.LOGIN)
            .payload(
                LoginPayload.builder()
                    .apiKey(apiKey)
                    .passphrase(apiPassword)
                    .timestamp(timestamp)
                    .signature(BitgetStreamingAuthHelper.sign(timestamp, apiSecret))
                    .build())
            .build();
    sendMessage(objectMapper.writeValueAsString(bitgetLoginRequest));
  }

  @Override
  protected void handleMessage(BitgetWsNotification message) {
    // subscribe to channels after sucessful login confirmation
    if (message instanceof BitgetEventNotification) {
      BitgetEventNotification eventNotification = (BitgetEventNotification) message;
      if (eventNotification.getEvent() == Event.LOGIN && eventNotification.getCode() == 0) {
        resubscribeChannelsAfterLogin();
        return;
      }
    }
    super.handleMessage(message);
  }
}
