package org.knowm.xchange.bitcoinde.dto.account;

import com.fasterxml.jackson.annotation.JsonAnyGetter;
import com.fasterxml.jackson.annotation.JsonAnySetter;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;
import java.util.HashMap;
import java.util.Map;

@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonPropertyOrder({"percent", "max_eur_volume", "eur_volume_open_orders"})
public class BitcoindeAllocation {

  @JsonProperty("percent")
  private Integer percent;

  @JsonProperty("max_eur_volume")
  private Integer maxEurVolume;

  @JsonProperty("eur_volume_open_orders")
  private Integer eurVolumeOpenOrders;

  @JsonIgnore private Map<String, Object> additionalProperties = new HashMap<String, Object>();

  /** No args constructor for use in serialization */
  public BitcoindeAllocation() {}

  /**
   * @param percent
   * @param eurVolumeOpenOrders
   * @param maxEurVolume
   */
  public BitcoindeAllocation(Integer percent, Integer maxEurVolume, Integer eurVolumeOpenOrders) {
    super();
    this.percent = percent;
    this.maxEurVolume = maxEurVolume;
    this.eurVolumeOpenOrders = eurVolumeOpenOrders;
  }

  @JsonProperty("percent")
  public Integer getPercent() {
    return percent;
  }

  @JsonProperty("percent")
  public void setPercent(Integer percent) {
    this.percent = percent;
  }

  @JsonProperty("max_eur_volume")
  public Integer getMaxEurVolume() {
    return maxEurVolume;
  }

  @JsonProperty("max_eur_volume")
  public void setMaxEurVolume(Integer maxEurVolume) {
    this.maxEurVolume = maxEurVolume;
  }

  @JsonProperty("eur_volume_open_orders")
  public Integer getEurVolumeOpenOrders() {
    return eurVolumeOpenOrders;
  }

  @JsonProperty("eur_volume_open_orders")
  public void setEurVolumeOpenOrders(Integer eurVolumeOpenOrders) {
    this.eurVolumeOpenOrders = eurVolumeOpenOrders;
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
