package info.bitrich.xchangestream.coinjar;

import com.fasterxml.jackson.databind.JsonNode;
import info.bitrich.xchangestream.coinjar.dto.CoinjarHeartbeat;
import info.bitrich.xchangestream.coinjar.dto.CoinjarWebSocketSubscribeMessage;
import info.bitrich.xchangestream.coinjar.dto.CoinjarWebSocketUnsubscribeMessage;
import info.bitrich.xchangestream.service.netty.JsonNettyStreamingService;
import io.reactivex.Observable;
import java.io.IOException;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicInteger;

class CoinjarStreamingService extends JsonNettyStreamingService {

  private final AtomicInteger refCount = new AtomicInteger();

  private String apiKey;

  public CoinjarStreamingService(String apiUrl, String apiKey) {
    super(apiUrl);
    this.apiKey = apiKey;
    Observable.interval(30, TimeUnit.SECONDS)
        .subscribe(
            t -> {
              if (this.isSocketOpen()) {
                this.sendObjectMessage(new CoinjarHeartbeat(refCount.incrementAndGet()));
              }
            });
  }

  @Override
  protected String getChannelNameFromMessage(JsonNode message) {
    return message.get("topic").asText();
  }

  @Override
  public String getSubscribeMessage(String channelName, Object... args) throws IOException {
    return objectMapper.writeValueAsString(
        new CoinjarWebSocketSubscribeMessage(channelName, apiKey, refCount.incrementAndGet()));
  }

  @Override
  public String getUnsubscribeMessage(String channelName, Object... args) throws IOException {
    CoinjarWebSocketUnsubscribeMessage message = new CoinjarWebSocketUnsubscribeMessage();
    return objectMapper.writeValueAsString(message);
  }
}
