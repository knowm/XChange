package org.knowm.xchange.bitcointoyou.dto.marketdata;

import java.math.BigDecimal;
import java.util.HashMap;
import java.util.Map;

import javax.annotation.Generated;

import org.apache.commons.lang3.builder.ToStringBuilder;

import com.fasterxml.jackson.annotation.JsonAnyGetter;
import com.fasterxml.jackson.annotation.JsonAnySetter;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;

/**
 * Public trade Bitcointoyou Exchange representation.
 *
 * @author Danilo Guimaraes
 * @author Jonathas Carrijo
 */
@JsonInclude(JsonInclude.Include.NON_NULL)
@Generated("org.jsonschema2pojo")
@JsonPropertyOrder({ "date", "price", "amount", "tid", "type", "currency" })
public class BitcointoyouPublicTrade {

  private final Integer date;
  private final BigDecimal price;
  private final BigDecimal amount;
  private final Integer tid;
  private final String type;
  private final String currency;
  @JsonIgnore
  private final Map<String, Object> additionalProperties = new HashMap<>();

  public BitcointoyouPublicTrade(@JsonProperty("date") Integer date, @JsonProperty("price") BigDecimal price,
      @JsonProperty("amount") BigDecimal amount, @JsonProperty("tid") Integer tid, @JsonProperty("type") String type,
      @JsonProperty("currency") String currency) {
    this.date = date;
    this.price = price;
    this.amount = amount;
    this.tid = tid;
    this.type = type;
    this.currency = currency;
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

  @Override
  public String toString() {

    return new ToStringBuilder(this).append("date", date).append("price", price).append("amount", amount)
        .append("tid", tid).append("type", type).append("currency", currency).append("additionalProperties",
            additionalProperties).toString();
  }
}
