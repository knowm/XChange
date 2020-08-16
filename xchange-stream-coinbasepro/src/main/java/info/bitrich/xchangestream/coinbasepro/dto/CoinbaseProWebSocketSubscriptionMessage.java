package info.bitrich.xchangestream.coinbasepro.dto;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import info.bitrich.xchangestream.core.ProductSubscription;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Stream;
import org.knowm.xchange.coinbasepro.dto.account.CoinbaseProWebsocketAuthData;
import org.knowm.xchange.currency.CurrencyPair;

/** CoinbasePro subscription message. */
public class CoinbaseProWebSocketSubscriptionMessage {

  public static final String TYPE = "type";
  public static final String CHANNELS = "channels";
  public static final String PRODUCT_IDS = "product_ids";
  public static final String NAME = "name";

  // if authenticating
  public static final String SIGNATURE = "signature";
  public static final String KEY = "key";
  public static final String PASSPHRASE = "passphrase";
  public static final String TIMESTAMP = "timestamp";

  class CoinbaseProProductSubscription {
    @JsonProperty(NAME)
    private final String name;

    @JsonProperty(PRODUCT_IDS)
    private final String[] productIds;

    public CoinbaseProProductSubscription(
        String name, String[] productIds, CoinbaseProWebsocketAuthData authData) {
      this.name = name;
      this.productIds = productIds;
    }

    public String getName() {
      return name;
    }

    public String[] getProductIds() {
      return productIds;
    }
  }

  @JsonProperty(TYPE)
  private final String type;

  @JsonProperty(CHANNELS)
  private CoinbaseProProductSubscription[] channels;

  @JsonInclude(JsonInclude.Include.NON_EMPTY)
  @JsonProperty(SIGNATURE)
  String signature;

  @JsonInclude(JsonInclude.Include.NON_EMPTY)
  @JsonProperty(KEY)
  String key;

  @JsonInclude(JsonInclude.Include.NON_EMPTY)
  @JsonProperty(PASSPHRASE)
  String passphrase;

  @JsonInclude(JsonInclude.Include.NON_EMPTY)
  @JsonProperty(TIMESTAMP)
  String timestamp;

  public CoinbaseProWebSocketSubscriptionMessage(
      String type,
      ProductSubscription product,
      boolean l3orderbook,
      CoinbaseProWebsocketAuthData authData) {
    this.type = type;
    generateSubscriptionMessage(product, l3orderbook, authData);
  }

  public CoinbaseProWebSocketSubscriptionMessage(
      String type, String[] channelNames, CoinbaseProWebsocketAuthData authData) {
    this.type = type;
    generateSubscriptionMessage(channelNames, authData);
  }

  private String[] generateProductIds(CurrencyPair[] pairs) {
    List<String> productIds = new ArrayList<>(pairs.length);
    for (CurrencyPair pair : pairs) {
      productIds.add(pair.base.toString() + "-" + pair.counter.toString());
    }

    return productIds.toArray(new String[0]);
  }

  private CoinbaseProProductSubscription generateCoinbaseProProduct(
      String name, CurrencyPair[] pairs, CoinbaseProWebsocketAuthData authData) {
    String[] productsIds;
    productsIds = generateProductIds(pairs);
    return new CoinbaseProProductSubscription(name, productsIds, authData);
  }

  private void generateSubscriptionMessage(
      String[] channelNames, CoinbaseProWebsocketAuthData authData) {
    List<CoinbaseProProductSubscription> channels = new ArrayList<>(3);
    for (String name : channelNames) {
      channels.add(new CoinbaseProProductSubscription(name, null, authData));
    }

    this.channels = channels.toArray(new CoinbaseProProductSubscription[0]);
  }

  private void generateSubscriptionMessage(
      ProductSubscription productSubscription,
      boolean l3orderbook,
      CoinbaseProWebsocketAuthData authData) {
    List<CoinbaseProProductSubscription> channels = new ArrayList<>(3);
    Map<String, List<CurrencyPair>> pairs = new HashMap<>(3);

    if (l3orderbook) {
      pairs.put("full", productSubscription.getOrderBook());
    } else {
      pairs.put("level2", productSubscription.getOrderBook());
    }
    pairs.put("ticker", productSubscription.getTicker());
    pairs.put("matches", productSubscription.getTrades());
    if (authData != null) {
      ArrayList<CurrencyPair> userCurrencies = new ArrayList<>();
      Stream.of(
              productSubscription.getUserTrades().stream(),
              productSubscription.getOrders().stream())
          .flatMap(s -> s)
          .distinct()
          .forEach(userCurrencies::add);
      pairs.put("user", userCurrencies);
    }

    for (Map.Entry<String, List<CurrencyPair>> product : pairs.entrySet()) {
      List<CurrencyPair> currencyPairs = product.getValue();
      if (currencyPairs == null || currencyPairs.size() == 0) {
        continue;
      }
      CoinbaseProProductSubscription coinbaseProProduct =
          generateCoinbaseProProduct(
              product.getKey(), product.getValue().toArray(new CurrencyPair[0]), authData);
      channels.add(coinbaseProProduct);
    }

    this.channels = channels.toArray(new CoinbaseProProductSubscription[0]);

    if (authData != null) {
      this.key = authData.getKey();
      this.passphrase = authData.getPassphrase();
      this.signature = authData.getSignature();
      this.timestamp = String.valueOf(authData.getTimestamp());
    }
  }

  public String getType() {
    return type;
  }

  public CoinbaseProProductSubscription[] getChannels() {
    return channels;
  }
}
