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
@JsonPropertyOrder({"btc", "eth", "bch"})
public class BitcoindeAllocations {

  @JsonProperty("btc")
  private BitcoindeAllocation btc;

  @JsonProperty("eth")
  private BitcoindeAllocation eth;

  @JsonProperty("bch")
  private BitcoindeAllocation bch;

  @JsonIgnore private Map<String, Object> additionalProperties = new HashMap<String, Object>();

  /** No args constructor for use in serialization */
  public BitcoindeAllocations() {}

  /**
   * @param eth
   * @param bch
   * @param btc
   */
  public BitcoindeAllocations(
      BitcoindeAllocation btc, BitcoindeAllocation eth, BitcoindeAllocation bch) {
    super();
    this.btc = btc;
    this.eth = eth;
    this.bch = bch;
  }

  @JsonProperty("btc")
  public BitcoindeAllocation getBtc() {
    return btc;
  }

  @JsonProperty("btc")
  public void setBtc(BitcoindeAllocation btc) {
    this.btc = btc;
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
