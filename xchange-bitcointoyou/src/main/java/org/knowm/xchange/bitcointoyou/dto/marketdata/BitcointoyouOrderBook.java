package org.knowm.xchange.bitcointoyou.dto.marketdata;

import java.math.BigDecimal;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Generated;

import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;
import org.apache.commons.lang3.builder.ToStringBuilder;

import com.fasterxml.jackson.annotation.JsonAnyGetter;
import com.fasterxml.jackson.annotation.JsonAnySetter;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;

/**
 * @author Jonathas Carrijo
 * @author Danilo Guimaraes
 */
@JsonInclude(JsonInclude.Include.NON_NULL)
@Generated("org.jsonschema2pojo")
@JsonPropertyOrder({"asks", "bids"})
public class BitcointoyouOrderBook {

  private final List<List<BigDecimal>> asks ;
  private final List<List<BigDecimal>> bids;
  @JsonIgnore
  private final Map<String, Object> additionalProperties = new HashMap<>();

  public BitcointoyouOrderBook(@JsonProperty("asks") List<List<BigDecimal>> asks, @JsonProperty("bids")List<List<BigDecimal>> bids) {
    this.asks = asks;
    this.bids = bids;
  }

  /**
   * @return The asks
   */
  public List<List<BigDecimal>> getAsks() {

    return asks;
  }

  /**
   * @return The bids
   */
  public List<List<BigDecimal>> getBids() {

    return bids;
  }

  @Override
  public String toString() {
    return ToStringBuilder.reflectionToString(this);
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
  public int hashCode() {
    return new HashCodeBuilder().append(asks).append(bids).append(additionalProperties).toHashCode();
  }

  @Override
  public boolean equals(Object other) {
    if (other == this) {
      return true;
    }
    if ((other instanceof BitcointoyouOrderBook) == false) {
      return false;
    }
    BitcointoyouOrderBook rhs = ((BitcointoyouOrderBook) other);
    return new EqualsBuilder().append(asks, rhs.asks).append(bids, rhs.bids).append(additionalProperties, rhs.additionalProperties).isEquals();
  }

}