package info.bitrich.xchangestream.cexio;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import info.bitrich.xchangestream.cexio.dto.*;
import info.bitrich.xchangestream.service.netty.JsonNettyStreamingService;
import info.bitrich.xchangestream.service.netty.StreamingObjectMapperHelper;
import io.reactivex.Completable;
import io.reactivex.CompletableEmitter;
import io.reactivex.CompletableOnSubscribe;
import io.reactivex.Observable;
import io.reactivex.subjects.PublishSubject;
import java.io.IOException;
import org.knowm.xchange.currency.CurrencyPair;
import org.knowm.xchange.dto.Order;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class CexioStreamingRawService extends JsonNettyStreamingService {

  private static final Logger LOG = LoggerFactory.getLogger(CexioStreamingRawService.class);

  public static final String CONNECTED = "connected";
  public static final String AUTH = "auth";
  public static final String PING = "ping";
  public static final String PONG = "pong";
  public static final String ORDER = "order";
  public static final String TRANSACTION = "tx";
  public static final String ORDERBOOK = "order-book-subscribe";
  public static final String ORDERBOOK_UPDATE = "md_update";

  private String apiKey;
  private String apiSecret;
  private AuthCompletable authCompletable = new AuthCompletable();

  private PublishSubject<Order> subjectOrder = PublishSubject.create();
  private PublishSubject<CexioWebSocketTransaction> subjectTransaction = PublishSubject.create();

  public CexioStreamingRawService(String apiUrl) {
    super(apiUrl, Integer.MAX_VALUE);
  }

  public static String GetOrderBookChannelForCurrencyPair(CurrencyPair currencyPair) {
    return ORDERBOOK + "-" + currencyPair.toString();
  }

  public static CurrencyPair GetCurrencyPairForChannelName(String channelName) {
    return new CurrencyPair(channelName.substring(ORDERBOOK.length() + 1));
  }

  @Override
  protected String getChannelNameFromMessage(JsonNode message) throws IOException {
    JsonNode eNode = message.get("e");
    if (eNode.textValue().compareTo(ORDERBOOK_UPDATE) == 0) {
      final ObjectMapper mapper = StreamingObjectMapperHelper.getObjectMapper();
      JsonNode dataNode = message.get("data");
      CexioWebSocketOrderBookSubscribeResponse orderBookSubResp =
          mapper.readValue(dataNode.toString(), CexioWebSocketOrderBookSubscribeResponse.class);
      CurrencyPair currencyPair = CexioAdapters.adaptCurrencyPair(orderBookSubResp.pair);
      return GetOrderBookChannelForCurrencyPair(currencyPair);
    } else {
      JsonNode oidNode = message.get("oid");
      if (oidNode == null) {
        throw new IllegalArgumentException("Missing OID on message " + message);
      }
      return oidNode.textValue();
    }
  }

  private Object GetEventSubscriptionData(String channelName, boolean isSubscribe, Object... args) {
    switch (channelName) {
      case ORDERBOOK:
        {
          CurrencyPair currencyPair = (CurrencyPair) args[0];
          return new CexioWebSocketOrderBookSubscriptionData(currencyPair, isSubscribe);
        }
      default:
        {
          throw new IllegalArgumentException(
              "Cannot get subscription data for unknown channel name " + channelName);
        }
    }
  }

  private static String getEventNameFromChannel(String channelName) {
    if (channelName.contains(ORDERBOOK)) {
      return ORDERBOOK;
    }
    return null;
  }

  @Override
  public String getSubscribeMessage(String channelName, Object... args) throws IOException {
    String eventName = getEventNameFromChannel(channelName);
    final Object eventSubData = GetEventSubscriptionData(eventName, true, args);
    CexioWebSocketSubscriptionRequest subReq =
        new CexioWebSocketSubscriptionRequest(eventName, eventSubData, channelName);
    return objectMapper.writeValueAsString(subReq);
  }

  @Override
  public String getUnsubscribeMessage(String channelName) throws IOException {
    String eventName = getEventNameFromChannel(channelName);

    CurrencyPair currencyPairForChannel = null;
    if (eventName.compareTo(ORDERBOOK) == 0) {
      currencyPairForChannel = GetCurrencyPairForChannelName(channelName);
    }

    final Object eventSubData = GetEventSubscriptionData(eventName, false, currencyPairForChannel);
    CexioWebSocketSubscriptionRequest subReq =
        new CexioWebSocketSubscriptionRequest(eventName, eventSubData, channelName);
    return objectMapper.writeValueAsString(subReq);
  }

  @Override
  public void messageHandler(String message) {
    JsonNode jsonNode;
    try {
      jsonNode = objectMapper.readTree(message);
    } catch (IOException e) {
      LOG.error("Error parsing incoming message to JSON: {}", message);
      subjectOrder.onError(e);
      return;
    }
    handleMessage(jsonNode);
  }

  protected static class AuthCompletable implements CompletableOnSubscribe {
    private CompletableEmitter completableEmitter;

    @Override
    public void subscribe(CompletableEmitter e) throws Exception {
      this.completableEmitter = e;
    }

    public void SignalAuthComplete() {
      completableEmitter.onComplete();
    }

    public void SignalError(String error) {
      completableEmitter.onError(new IllegalStateException(error));
    }
  }

  @Override
  public Completable connect() {
    synchronized (authCompletable) {
      Completable parentCompletable = super.connect();
      parentCompletable.blockingAwait();
      return Completable.create(authCompletable);
    }
  }

  @Override
  protected void handleMessage(JsonNode message) {
    LOG.debug("Receiving message: {}", message);
    JsonNode cexioMessage = message.get("e");

    try {
      if (cexioMessage != null) {
        switch (cexioMessage.textValue()) {
          case CONNECTED:
            auth();
            break;
          case AUTH:
            CexioWebSocketAuthResponse response =
                deserialize(message, CexioWebSocketAuthResponse.class);
            if (response != null) {
              if (response.isSuccess()) {
                synchronized (authCompletable) {
                  authCompletable.SignalAuthComplete();
                }
              } else {
                String authErrorString =
                    new String("Authentication error: " + response.getData().getError());
                LOG.error(authErrorString);
                synchronized (authCompletable) {
                  authCompletable.SignalError(authErrorString);
                }
              }
            }
            break;
          case PING:
            pong();
            break;
          case ORDER:
            try {
              CexioWebSocketOrderMessage cexioOrder =
                  deserialize(message, CexioWebSocketOrderMessage.class);
              Order order = CexioAdapters.adaptOrder(cexioOrder.getData());
              LOG.debug(String.format("Order is updated: %s", order));
              subjectOrder.onNext(order);
            } catch (Exception e) {
              LOG.error("Order parsing error: {}", e.getMessage(), e);
              subjectOrder.onError(e);
            }
            break;
          case TRANSACTION:
            try {
              CexioWebSocketTransactionMessage transaction =
                  deserialize(message, CexioWebSocketTransactionMessage.class);
              LOG.debug(String.format("New transaction: %s", transaction.getData()));
              subjectTransaction.onNext(transaction.getData());
            } catch (Exception e) {
              LOG.error("Transaction parsing error: {}", e.getMessage(), e);
              subjectTransaction.onError(e);
            }
            break;
          case ORDERBOOK:
            JsonNode okNode = message.get("ok");
            if (okNode.textValue().compareTo("ok") != 0) {
              String errorString =
                  "Error response for order book subscription: %s" + message.toString();
              LOG.error(errorString);
              subjectOrder.onError(new IllegalArgumentException(errorString));
            } else {
              super.handleMessage(message);
            }
            break;
          case ORDERBOOK_UPDATE:
            super.handleMessage(message);
            break;
        }
      }
    } catch (JsonProcessingException e) {
      LOG.error("Json parsing error: {}", e.getMessage());
    }
  }

  private void auth() {
    if (apiSecret == null || apiKey == null) {
      throw new IllegalStateException("API keys must be provided to use cexio streaming exchange");
    }
    long timestamp = System.currentTimeMillis() / 1000;
    CexioDigest cexioDigest = CexioDigest.createInstance(apiSecret);
    String signature = cexioDigest.createSignature(timestamp, apiKey);
    CexioWebSocketAuthMessage message =
        new CexioWebSocketAuthMessage(new CexioWebSocketAuth(apiKey, signature, timestamp));
    sendMessage(message);
  }

  private void pong() {
    CexioWebSocketPongMessage message = new CexioWebSocketPongMessage();
    sendMessage(message);
  }

  private void sendMessage(Object message) {
    try {
      sendMessage(objectMapper.writeValueAsString(message));
    } catch (JsonProcessingException e) {
      LOG.error("Error creating json message: {}", e.getMessage());
    }
  }

  public void setApiKey(String apiKey) {
    this.apiKey = apiKey;
  }

  public void setApiSecret(String apiSecret) {
    this.apiSecret = apiSecret;
  }

  private <T> T deserialize(JsonNode message, Class<T> valueType) throws JsonProcessingException {
    return objectMapper.treeToValue(message, valueType);
  }

  public Observable<Order> getOrderData() {
    return subjectOrder.share();
  }

  public Observable<CexioWebSocketTransaction> getTransactions() {
    return subjectTransaction.share();
  }
}
