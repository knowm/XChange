package org.knowm.xchange.bitcointoyou.dto.trade;

import java.math.BigDecimal;
import java.util.HashMap;
import java.util.Map;

import com.fasterxml.jackson.annotation.JsonAnyGetter;
import com.fasterxml.jackson.annotation.JsonAnySetter;
import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;

/**
 * Bitcointoyou order details
 *
 * @author Danilo Guimaraes
 */
@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonPropertyOrder({"asset", "currency", "id", "action", "status", "price", "amount", "executedPriceAverage", "executedAmount", "dateCreated"})
public class BitcointoyouOrderInfo {

  @JsonIgnore
  private String content;
  private final String asset;
  private final String currency;
  private final String id;
  private final String action;
  private final String status;
  private final BigDecimal price;
  private final BigDecimal amount;
  private final String executedPriceAverage;
  private final String executedAmount;
  private final String dateCreated;
  @JsonIgnore
  private final Map<String, Object> additionalProperties = new HashMap<>();

  /**
   * Sometimes the {@code oReturn} field contains some text, like NO_RECORD_FOUND.
   *
   * @param content the content of the field {@code oReturn}.
   */
  @JsonCreator
  public BitcointoyouOrderInfo(String content) {
    super();
    this.content = content;
    asset = null;
    currency = null;
    id = null;
    action = null;
    status = null;
    price = null;
    amount = null;
    executedPriceAverage = null;
    executedAmount = null;
    dateCreated = null;
  }

  @JsonCreator
  public BitcointoyouOrderInfo(@JsonProperty("asset") String asset, @JsonProperty("currency") String currency, @JsonProperty("id") String id,
      @JsonProperty("action") String action, @JsonProperty("status") String status, @JsonProperty("price") BigDecimal price,
      @JsonProperty("amount") BigDecimal amount, @JsonProperty("executedPriceAverage") String executedPriceAverage,
      @JsonProperty("executedAmount") String executedAmount, @JsonProperty("dateCreated") String dateCreated) {
    this.asset = asset;
    this.currency = currency;
    this.id = id;
    this.action = action;
    this.status = status;
    this.price = price;
    this.amount = amount;
    this.executedPriceAverage = executedPriceAverage;
    this.executedAmount = executedAmount;
    this.dateCreated = dateCreated;
  }

  /**
   * Constructor
   *
   * @param map {@link Map} which keys is the fields.
   */
  @JsonCreator
  public BitcointoyouOrderInfo(Map<String, String> map) {
    this.asset = map.get("asset");
    this.currency = map.get("currency");
    this.id = map.get("id");
    this.action = map.get("action");
    this.status = map.get("status");

    String priceAsString = map.get("price");
    if (priceAsString != null && !priceAsString.isEmpty()) {
      this.price = new BigDecimal(priceAsString);
    } else {
      this.price = null;
    }

    String amountAsString = map.get("amount");
    if (amountAsString != null && !amountAsString.isEmpty()) {
      this.amount = new BigDecimal(amountAsString);
    } else {
      this.amount = null;
    }

    this.executedPriceAverage = map.get("executedPriceAverage");
    this.executedAmount = map.get("executedAmount");
    this.dateCreated = map.get("dateCreated");
  }

  @JsonIgnore
  public String getContent() {

    return content;
  }

  @JsonIgnore
  public void setContent(String content) {

    this.content = content;
  }

  @JsonProperty("asset")
  public String getAsset() {
    return asset;
  }

  @JsonProperty("currency")
  public String getCurrency() {

    return currency;
  }

  @JsonProperty("id")
  public String getId() {

    return id;
  }

  @JsonProperty("action")
  public String getAction() {

    return action;
  }

  @JsonProperty("status")
  public String getStatus() {

    return status;
  }

  @JsonProperty("price")
  public BigDecimal getPrice() {

    return price;
  }

  @JsonProperty("amount")
  public BigDecimal getAmount() {

    return amount;
  }

  @JsonProperty("executedPriceAverage")
  public String getExecutedPriceAverage() {

    return executedPriceAverage;
  }

  @JsonProperty("executedAmount")
  public String getExecutedAmount() {

    return executedAmount;
  }

  @JsonProperty("dateCreated")
  public String getDateCreated() {

    return dateCreated;
  }

  @JsonAnyGetter
  public Map<String, Object> getAdditionalProperties() {

    return this.additionalProperties;
  }

  @JsonAnySetter
  public void setAdditionalProperty(String name, Object value) {

    this.additionalProperties.put(name, value);
  }

}
