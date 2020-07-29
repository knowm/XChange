package info.bitrich.xchangestream.bitmex;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.JsonNodeType;
import info.bitrich.xchangestream.bitmex.dto.BitmexMarketDataEvent;
import info.bitrich.xchangestream.bitmex.dto.BitmexWebSocketSubscriptionMessage;
import info.bitrich.xchangestream.bitmex.dto.BitmexWebSocketTransaction;
import info.bitrich.xchangestream.service.netty.JsonNettyStreamingService;
import io.netty.handler.codec.http.DefaultHttpHeaders;
import io.netty.handler.codec.http.websocketx.extensions.WebSocketClientExtensionHandler;
import io.reactivex.Completable;
import io.reactivex.CompletableSource;
import io.reactivex.Observable;
import io.reactivex.ObservableEmitter;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;
import java.io.IOException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.ZoneOffset;
import java.util.*;
import java.util.concurrent.TimeUnit;
import org.knowm.xchange.bitmex.service.BitmexDigest;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/** Created by Lukas Zaoralek on 13.11.17. */
public class BitmexStreamingService extends JsonNettyStreamingService {
  private static final Logger LOG = LoggerFactory.getLogger(BitmexStreamingService.class);
  private final ObjectMapper mapper = new ObjectMapper();
  private List<ObservableEmitter<Long>> delayEmitters = new LinkedList<>();

  private final String apiKey;
  private final String secretKey;

  public static final int DMS_CANCEL_ALL_IN = 60000;
  public static final int DMS_RESUBSCRIBE = 15000;
  /** deadman's cancel time */
  private long dmsCancelTime;

  private Disposable dmsDisposable;

  public BitmexStreamingService(String apiUrl, String apiKey, String secretKey) {
    super(apiUrl, Integer.MAX_VALUE);
    this.apiKey = apiKey;
    this.secretKey = secretKey;
  }

  private void login() throws JsonProcessingException {
    long expires = System.currentTimeMillis() + 30;
    String path = "/realtime";
    String signature =
        BitmexAuthenticator.generateSignature(secretKey, "GET", path, String.valueOf(expires), "");

    List<Object> args = Arrays.asList(apiKey, expires, signature);

    Map<String, Object> cmd = new HashMap<>();
    cmd.put("op", "authKey");
    cmd.put("args", args);
    this.sendMessage(mapper.writeValueAsString(cmd));
  }

  @Override
  public Completable connect() {
    // Note that we must override connect method in streaming service instead of streaming exchange,
    // because of the auto reconnect feature of NettyStreamingService.
    // We must ensure the authentication message is also resend when the connection is rebuilt.
    Completable conn = super.connect();
    if (apiKey == null) {
      return conn;
    }
    return conn.andThen(
        (CompletableSource)
            (completable) -> {
              try {
                login();
                completable.onComplete();
              } catch (IOException e) {
                completable.onError(e);
              }
            });
  }

  @Override
  protected void handleMessage(JsonNode message) {
    if (!delayEmitters.isEmpty() && message.has("data")) {
      String table = "";
      if (message.has("table")) {
        table = message.get("table").asText();
      }
      JsonNode data = message.get("data");
      if (data.getNodeType().equals(JsonNodeType.ARRAY)) {
        Long current = System.currentTimeMillis();
        SimpleDateFormat formatter;
        formatter = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSS'Z'");
        formatter.setTimeZone(TimeZone.getTimeZone("UTC"));
        JsonNode d = data.get(0);
        if (d != null
            && d.has("timestamp")
            && (!"order".equals(table)
                || d.has("ordStatus") && "NEW".equals(d.get("ordStatus").asText()))) {
          try {
            String timestamp = d.get("timestamp").asText();
            Date date = formatter.parse(timestamp);
            long delay = current - date.getTime();
            for (ObservableEmitter<Long> emitter : delayEmitters) {
              emitter.onNext(delay);
            }
          } catch (ParseException e) {
            LOG.error("Parsing timestamp error: ", e);
          }
        }
      }
    }
    if (message.has("info") || message.has("success")) {
      return;
    }
    if (message.has("error")) {
      String error = message.get("error").asText();
      LOG.error("Error with message: " + error);
      return;
    }
    if (message.has("now") && message.has("cancelTime")) {
      handleDeadMansSwitchMessage(message);
      return;
    }
    super.handleMessage(message);
  }

  private void handleDeadMansSwitchMessage(JsonNode message) {
    // handle dead man's switch confirmation
    try {
      String cancelTime = message.get("cancelTime").asText();
      if (cancelTime.equals("0")) {
        LOG.info("Dead man's switch disabled");
        dmsDisposable.dispose();
        dmsDisposable = null;
        dmsCancelTime = 0;
      } else {
        SimpleDateFormat sdf = new SimpleDateFormat(BitmexMarketDataEvent.BITMEX_TIMESTAMP_FORMAT);
        sdf.setTimeZone(TimeZone.getTimeZone(ZoneOffset.UTC));
        long now = sdf.parse(message.get("now").asText()).getTime();
        dmsCancelTime = sdf.parse(cancelTime).getTime();
      }
    } catch (ParseException e) {
      LOG.error("Error parsing deadman's confirmation ");
    }
    return;
  }

  @Override
  protected WebSocketClientExtensionHandler getWebSocketClientExtensionHandler() {
    return null;
  }

  public Observable<BitmexWebSocketTransaction> subscribeBitmexChannel(String channelName) {
    return subscribeChannel(channelName)
        .map(
            s -> {
              BitmexWebSocketTransaction transaction =
                  objectMapper.treeToValue(s, BitmexWebSocketTransaction.class);
              return transaction;
            })
        .share();
  }

  @Override
  protected DefaultHttpHeaders getCustomHeaders() {
    DefaultHttpHeaders customHeaders = super.getCustomHeaders();
    if (secretKey == null || apiKey == null) {
      return customHeaders;
    }
    long expires = System.currentTimeMillis() / 1000 + 5;

    BitmexDigest bitmexDigester = BitmexDigest.createInstance(secretKey, apiKey);
    String stringToDigest = "GET/realtime" + expires;
    String signature = bitmexDigester.digestString(stringToDigest);

    customHeaders.add("api-key", apiKey);
    customHeaders.add("api-signature", signature);
    return customHeaders;
  }

  @Override
  protected String getChannelNameFromMessage(JsonNode message) throws IOException {
    String table = message.get("table").asText();
    if (table.equals("order") || table.equals("funding") || table.equals("position")) {
      return table;
    }
    JsonNode data = message.get("data");
    String instrument =
        data.size() > 0
            ? data.get(0).get("symbol").asText()
            : message.get("filter").get("symbol").asText();
    return String.format("%s:%s", table, instrument);
  }

  @Override
  public String getSubscribeMessage(String channelName, Object... args) throws IOException {
    BitmexWebSocketSubscriptionMessage subscribeMessage =
        new BitmexWebSocketSubscriptionMessage("subscribe", new String[] {channelName});
    return objectMapper.writeValueAsString(subscribeMessage);
  }

  @Override
  public String getUnsubscribeMessage(String channelName) throws IOException {
    BitmexWebSocketSubscriptionMessage subscribeMessage =
        new BitmexWebSocketSubscriptionMessage("unsubscribe", new String[] {channelName});
    return objectMapper.writeValueAsString(subscribeMessage);
  }

  public void enableDeadMansSwitch(long rate, long timeout) throws IOException {
    if (dmsDisposable != null) {
      LOG.warn("You already have Dead Man's switch enabled. Doing nothing");
      return;
    }
    final BitmexWebSocketSubscriptionMessage subscriptionMessage =
        new BitmexWebSocketSubscriptionMessage("cancelAllAfter", new Object[] {timeout});
    String message = objectMapper.writeValueAsString(subscriptionMessage);
    dmsDisposable =
        Schedulers.single()
            .schedulePeriodicallyDirect(() -> sendMessage(message), 0, rate, TimeUnit.MILLISECONDS);
    Schedulers.single().start();
  }

  public void disableDeadMansSwitch() throws IOException {
    final BitmexWebSocketSubscriptionMessage subscriptionMessage =
        new BitmexWebSocketSubscriptionMessage("cancelAllAfter", new Object[] {0});
    String message = objectMapper.writeValueAsString(subscriptionMessage);
    sendMessage(message);
  }

  public boolean isDeadMansSwitchEnabled() {
    return dmsCancelTime > 0 && System.currentTimeMillis() < dmsCancelTime;
  }

  public void addDelayEmitter(ObservableEmitter<Long> delayEmitter) {
    delayEmitters.add(delayEmitter);
  }
}
