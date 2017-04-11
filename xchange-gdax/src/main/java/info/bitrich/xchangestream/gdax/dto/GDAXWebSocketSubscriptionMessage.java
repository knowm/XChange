package info.bitrich.xchangestream.gdax.dto;

import com.fasterxml.jackson.annotation.JsonProperty;

/**
 * GDAX subscription message.
 */
public class GDAXWebSocketSubscriptionMessage {
  
  public static final String TYPE = "type";
  public static final String PRODUCT_ID = "product_id";

  @JsonProperty(TYPE)
  private String type;

  @JsonProperty(PRODUCT_ID)
  private String productId;

  public GDAXWebSocketSubscriptionMessage(String type, String productId) {
    this.type = type;
    this.productId = productId;
  }

  public String getType() {
    return type;
  }

  public String getProductId() {
    return productId;
  }
}
