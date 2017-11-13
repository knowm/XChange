package info.bitrich.xchangestream.gdax.dto;

import com.fasterxml.jackson.annotation.JsonProperty;

/**
 * GDAX subscription message.
 */
public class GDAXWebSocketSubscriptionMessage {
  
  public static final String TYPE = "type";
  public static final String PRODUCT_ID = "product_id";
  public static final String PRODUCT_IDS = "product_ids";
  public static final String CHANNELS = "channels";

  @JsonProperty(TYPE)
  private String type;

  @JsonProperty(PRODUCT_IDS)
  private String[] productIds;

  @JsonProperty(CHANNELS)
  private String[] channels;

  public GDAXWebSocketSubscriptionMessage(String type, String productId) {
    this(type, new String[]{productId});
  }

  public GDAXWebSocketSubscriptionMessage(String type, String[] productIds) {
    this.type = type;
    this.productIds = productIds;
    this.channels = new String[]{"level2", "ticker", "matches"};
  }

  public String getType() {
    return type;
  }

  public String[] getProductIds() {
    return productIds;
  }

  public String[] getChannels() {
    return channels;
  }
}
