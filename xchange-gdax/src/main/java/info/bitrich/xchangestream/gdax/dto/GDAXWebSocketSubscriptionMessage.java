package info.bitrich.xchangestream.gdax.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import info.bitrich.xchangestream.core.ProductSubscription;
import org.knowm.xchange.currency.CurrencyPair;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * GDAX subscription message.
 */
public class GDAXWebSocketSubscriptionMessage {
  
  public static final String TYPE = "type";
  public static final String CHANNELS = "channels";
  public static final String PRODUCT_IDS = "product_ids";
  public static final String NAME = "name";

  class GDAXProductSubsctiption {
    @JsonProperty(NAME)
    private String name;

    @JsonProperty(PRODUCT_IDS)
    private String[] productIds;

    public GDAXProductSubsctiption(String name, String[] productIds) {
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
  private String type;

  @JsonProperty(CHANNELS)
  private GDAXProductSubsctiption[] channels;

  public GDAXWebSocketSubscriptionMessage(String type, ProductSubscription product) {
    this.type = type;
    generateSubscriptionMessage(product);
  }

  public GDAXWebSocketSubscriptionMessage(String type, String[] channelNames) {
    this.type = type;
    generateSubscriptionMessage(channelNames);
  }

  private String[] generateProductIds(CurrencyPair[] pairs) {
    List<String> productIds = new ArrayList<>(pairs.length);
    for (CurrencyPair pair : pairs) {
      productIds.add(pair.base.toString() + "-" + pair.counter.toString());
    }

    return productIds.toArray(new String[productIds.size()]);
  }

  private GDAXProductSubsctiption generateGDAXProduct(String name, CurrencyPair[] pairs) {
    String[] productsIds;
    productsIds = generateProductIds(pairs);
    return new GDAXProductSubsctiption(name, productsIds);
  }

  private void generateSubscriptionMessage(String[] channelNames) {
    List<GDAXProductSubsctiption> channels = new ArrayList<>(3);
    for (String name : channelNames) {
      channels.add(new GDAXProductSubsctiption(name, null));
    }

    this.channels = channels.toArray(new GDAXProductSubsctiption[channels.size()]);
  }

  private void generateSubscriptionMessage(ProductSubscription productSubscription) {
    List<GDAXProductSubsctiption> channels = new ArrayList<>(3);
    Map<String, List<CurrencyPair>> pairs = new HashMap<>(3);

    pairs.put("level2", productSubscription.getOrderBook());
    pairs.put("ticker", productSubscription.getTicker());
    pairs.put("matches", productSubscription.getTrades());

    for (Map.Entry<String, List<CurrencyPair>> product : pairs.entrySet()) {
      List<CurrencyPair> currencyPairs = product.getValue();
      if (currencyPairs == null || currencyPairs.size() == 0) {
        continue;
      }
      GDAXProductSubsctiption gdaxProduct = generateGDAXProduct(product.getKey(), product.getValue().toArray(new CurrencyPair[product.getValue().size()]));
      channels.add(gdaxProduct);
    }

    this.channels = channels.toArray(new GDAXProductSubsctiption[channels.size()]);
  }

  public String getType() {
    return type;
  }

  public GDAXProductSubsctiption[] getChannels() {
    return channels;
  }
}
