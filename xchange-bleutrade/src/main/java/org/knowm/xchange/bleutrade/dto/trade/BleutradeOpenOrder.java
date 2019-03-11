package org.knowm.xchange.bleutrade.dto.trade;

import com.fasterxml.jackson.annotation.JsonAnyGetter;
import com.fasterxml.jackson.annotation.JsonAnySetter;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;
import java.math.BigDecimal;
import java.util.HashMap;
import java.util.Map;
import javax.annotation.Generated;

@JsonInclude(JsonInclude.Include.NON_NULL)
@Generated("org.jsonschema2pojo")
@JsonPropertyOrder({
  "OrderId",
  "Exchange",
  "Type",
  "Quantity",
  "QuantityRemaining",
  "QuantityBaseTraded",
  "Price",
  "Status",
  "Created",
  "Comments"
})
public class BleutradeOpenOrder {

  @JsonProperty("OrderId")
  private String OrderId;

  @JsonProperty("Exchange")
  private String Exchange;

  @JsonProperty("Type")
  private String Type;

  @JsonProperty("Quantity")
  private BigDecimal Quantity;

  @JsonProperty("QuantityRemaining")
  private BigDecimal QuantityRemaining;

  @JsonProperty("QuantityBaseTraded")
  private String QuantityBaseTraded;

  @JsonProperty("Price")
  private BigDecimal Price;

  @JsonProperty("Status")
  private String Status;

  @JsonProperty("Created")
  private String Created;

  @JsonProperty("Comments")
  private String Comments;

  @JsonIgnore private Map<String, Object> additionalProperties = new HashMap<String, Object>();

  /** @return The OrderId */
  @JsonProperty("OrderId")
  public String getOrderId() {

    return OrderId;
  }

  /** @param OrderId The OrderId */
  @JsonProperty("OrderId")
  public void setOrderId(String OrderId) {

    this.OrderId = OrderId;
  }

  /** @return The Exchange */
  @JsonProperty("Exchange")
  public String getExchange() {

    return Exchange;
  }

  /** @param Exchange The Exchange */
  @JsonProperty("Exchange")
  public void setExchange(String Exchange) {

    this.Exchange = Exchange;
  }

  /** @return The Type */
  @JsonProperty("Type")
  public String getType() {

    return Type;
  }

  /** @param Type The Type */
  @JsonProperty("Type")
  public void setType(String Type) {

    this.Type = Type;
  }

  /** @return The Quantity */
  @JsonProperty("Quantity")
  public BigDecimal getQuantity() {

    return Quantity;
  }

  /** @param Quantity The Quantity */
  @JsonProperty("Quantity")
  public void setQuantity(BigDecimal Quantity) {

    this.Quantity = Quantity;
  }

  /** @return The QuantityRemaining */
  @JsonProperty("QuantityRemaining")
  public BigDecimal getQuantityRemaining() {

    return QuantityRemaining;
  }

  /** @param QuantityRemaining The QuantityRemaining */
  @JsonProperty("QuantityRemaining")
  public void setQuantityRemaining(BigDecimal QuantityRemaining) {

    this.QuantityRemaining = QuantityRemaining;
  }

  /** @return The QuantityBaseTraded */
  @JsonProperty("QuantityBaseTraded")
  public String getQuantityBaseTraded() {

    return QuantityBaseTraded;
  }

  /** @param QuantityBaseTraded The QuantityBaseTraded */
  @JsonProperty("QuantityBaseTraded")
  public void setQuantityBaseTraded(String QuantityBaseTraded) {

    this.QuantityBaseTraded = QuantityBaseTraded;
  }

  /** @return The Price */
  @JsonProperty("Price")
  public BigDecimal getPrice() {

    return Price;
  }

  /** @param Price The Price */
  @JsonProperty("Price")
  public void setPrice(BigDecimal Price) {

    this.Price = Price;
  }

  /** @return The Status */
  @JsonProperty("Status")
  public String getStatus() {

    return Status;
  }

  /** @param Status The Status */
  @JsonProperty("Status")
  public void setStatus(String Status) {

    this.Status = Status;
  }

  /** @return The Created */
  @JsonProperty("Created")
  public String getCreated() {

    return Created;
  }

  /** @param Created The Created */
  @JsonProperty("Created")
  public void setCreated(String Created) {

    this.Created = Created;
  }

  /** @return The Comments */
  @JsonProperty("Comments")
  public String getComments() {

    return Comments;
  }

  /** @param Comments The Comments */
  @JsonProperty("Comments")
  public void setComments(String Comments) {

    this.Comments = Comments;
  }

  @JsonAnyGetter
  public Map<String, Object> getAdditionalProperties() {

    return this.additionalProperties;
  }

  @JsonAnySetter
  public void setAdditionalProperty(String name, Object value) {

    this.additionalProperties.put(name, value);
  }

  @Override
  public String toString() {

    return "BleutradeOpenOrder [OrderId="
        + OrderId
        + ", Exchange="
        + Exchange
        + ", Type="
        + Type
        + ", Quantity="
        + Quantity
        + ", QuantityRemaining="
        + QuantityRemaining
        + ", QuantityBaseTraded="
        + QuantityBaseTraded
        + ", Price="
        + Price
        + ", Status="
        + Status
        + ", Created="
        + Created
        + ", Comments="
        + Comments
        + ", additionalProperties="
        + additionalProperties
        + "]";
  }
}
