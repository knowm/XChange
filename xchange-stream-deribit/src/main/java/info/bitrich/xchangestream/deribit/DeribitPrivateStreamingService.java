package info.bitrich.xchangestream.deribit;

import info.bitrich.xchangestream.deribit.dto.request.DeribitLoginRequest;
import info.bitrich.xchangestream.deribit.dto.request.DeribitLoginRequest.Params;
import info.bitrich.xchangestream.deribit.dto.response.DeribitEventNotification;
import info.bitrich.xchangestream.deribit.dto.response.DeribitWsNotification;
import java.io.IOException;
import java.util.Map.Entry;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;

@Slf4j
public class DeribitPrivateStreamingService extends DeribitStreamingService {

  private final String apiKey;
  private final String apiSecret;

  public DeribitPrivateStreamingService(String apiUri, String apiKey, String apiSecret) {
    super(apiUri);
    this.apiKey = apiKey;
    this.apiSecret = apiSecret;
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
    DeribitLoginRequest deribitLoginRequest =
        DeribitLoginRequest.builder()
            .params(Params.builder().clientId(apiKey).clientSecret(apiSecret).build())
            .build();

    sendMessage(objectMapper.writeValueAsString(deribitLoginRequest));
  }

  @Override
  protected void handleMessage(DeribitWsNotification message) {
    // subscribe to channels after sucessful login confirmation
    if (message instanceof DeribitEventNotification
        && ((DeribitEventNotification) message).hasToken()) {
      resubscribeChannelsAfterLogin();
      return;
    }
    super.handleMessage(message);
  }
}
