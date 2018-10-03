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
@JsonPropertyOrder({"bic_short", "bic_full", "uid"})
public class BitcoindeEncryptedInformation {

  @JsonProperty("bic_short")
  private String bicShort;

  @JsonProperty("bic_full")
  private String bicFull;

  @JsonProperty("uid")
  private String uid;

  @JsonIgnore private Map<String, Object> additionalProperties = new HashMap<String, Object>();

  /** No args constructor for use in serialization */
  public BitcoindeEncryptedInformation() {}

  /**
   * @param uid
   * @param bicShort
   * @param bicFull
   */
  public BitcoindeEncryptedInformation(String bicShort, String bicFull, String uid) {
    super();
    this.bicShort = bicShort;
    this.bicFull = bicFull;
    this.uid = uid;
  }

  @JsonProperty("bic_short")
  public String getBicShort() {
    return bicShort;
  }

  @JsonProperty("bic_short")
  public void setBicShort(String bicShort) {
    this.bicShort = bicShort;
  }

  @JsonProperty("bic_full")
  public String getBicFull() {
    return bicFull;
  }

  @JsonProperty("bic_full")
  public void setBicFull(String bicFull) {
    this.bicFull = bicFull;
  }

  @JsonProperty("uid")
  public String getUid() {
    return uid;
  }

  @JsonProperty("uid")
  public void setUid(String uid) {
    this.uid = uid;
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
