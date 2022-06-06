package org.knowm.xchange.bitcointoyou.dto.marketdata;

import com.fasterxml.jackson.annotation.JsonAnyGetter;
import com.fasterxml.jackson.annotation.JsonAnySetter;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;
import java.math.BigDecimal;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import javax.annotation.Generated;

/**
 * @author Jonathas Carrijo
 * @author Danilo Guimaraes
 */
@JsonInclude(JsonInclude.Include.NON_NULL)
@Generated("org.jsonschema2pojo")
@JsonPropertyOrder({"asks", "bids"})
public class BitcointoyouOrderBook {

  private final List<List<BigDecimal>> asks;
  private final List<List<BigDecimal>> bids;
  @JsonIgnore private final Map<String, Object> additionalProperties = new HashMap<>();

  @Override
  public boolean equals(Object o) {
    if (this == o) return true;
    if (!(o instanceof BitcointoyouOrderBook)) return false;
    BitcointoyouOrderBook that = (BitcointoyouOrderBook) o;
    return Objects.equals(getAsks(), that.getAsks())
        && Objects.equals(getBids(), that.getBids())
        && Objects.equals(getAdditionalProperties(), that.getAdditionalProperties());
  }

  @Override
  public int hashCode() {

    return Objects.hash(getAsks(), getBids(), getAdditionalProperties());
  }

  @Override
  public String toString() {
    return "BitcointoyouOrderBook{"
        + "asks="
        + asks
        + ", bids="
        + bids
        + ", additionalProperties="
        + additionalProperties
        + '}';
  }

  public BitcointoyouOrderBook(
      @JsonProperty("asks") List<List<BigDecimal>> asks,
      @JsonProperty("bids") List<List<BigDecimal>> bids) {
    this.asks = asks;
    this.bids = bids;
  }

  /** @return The asks */
  public List<List<BigDecimal>> getAsks() {

    return asks;
  }

  /** @return The bids */
  public List<List<BigDecimal>> getBids() {

    return bids;
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
