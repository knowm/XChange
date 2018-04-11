package org.knowm.xchange.bitcointoyou.dto.marketdata;

import com.fasterxml.jackson.annotation.JsonAnyGetter;
import com.fasterxml.jackson.annotation.JsonAnySetter;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;
import java.math.BigDecimal;
import java.util.HashMap;
import java.util.Map;
import java.util.Objects;
import javax.annotation.Generated;

/**
 * Public trade Bitcointoyou Exchange representation.
 *
 * @author Danilo Guimaraes
 * @author Jonathas Carrijo
 */
@JsonInclude(JsonInclude.Include.NON_NULL)
@Generated("org.jsonschema2pojo")
@JsonPropertyOrder({"date", "price", "amount", "tid", "type", "currency"})
public class BitcointoyouPublicTrade {

  private final Integer date;
  private final BigDecimal price;
  private final BigDecimal amount;
  private final Integer tid;
  private final String type;
  private final String currency;
  @JsonIgnore private final Map<String, Object> additionalProperties = new HashMap<>();

  public BitcointoyouPublicTrade(
      @JsonProperty("date") Integer date,
      @JsonProperty("price") BigDecimal price,
      @JsonProperty("amount") BigDecimal amount,
      @JsonProperty("tid") Integer tid,
      @JsonProperty("type") String type,
      @JsonProperty("currency") String currency) {
    this.date = date;
    this.price = price;
    this.amount = amount;
    this.tid = tid;
    this.type = type;
    this.currency = currency;
  }

  @Override
  public boolean equals(Object o) {
    if (this == o) return true;
    if (!(o instanceof BitcointoyouPublicTrade)) return false;
    BitcointoyouPublicTrade that = (BitcointoyouPublicTrade) o;
    return Objects.equals(getDate(), that.getDate())
        && Objects.equals(getPrice(), that.getPrice())
        && Objects.equals(getAmount(), that.getAmount())
        && Objects.equals(getTid(), that.getTid())
        && Objects.equals(getType(), that.getType())
        && Objects.equals(getCurrency(), that.getCurrency())
        && Objects.equals(getAdditionalProperties(), that.getAdditionalProperties());
  }

  @Override
  public int hashCode() {

    return Objects.hash(
        getDate(),
        getPrice(),
        getAmount(),
        getTid(),
        getType(),
        getCurrency(),
        getAdditionalProperties());
  }

  @Override
  public String toString() {
    return "BitcointoyouPublicTrade{"
        + "date="
        + date
        + ", price="
        + price
        + ", amount="
        + amount
        + ", tid="
        + tid
        + ", type='"
        + type
        + '\''
        + ", currency='"
        + currency
        + '\''
        + ", additionalProperties="
        + additionalProperties
        + '}';
  }

  public Integer getDate() {

    return date;
  }

  public BigDecimal getPrice() {

    return price;
  }

  public BigDecimal getAmount() {

    return amount;
  }

  public Integer getTid() {

    return tid;
  }

  public String getType() {

    return type;
  }

  public String getCurrency() {

    return currency;
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
