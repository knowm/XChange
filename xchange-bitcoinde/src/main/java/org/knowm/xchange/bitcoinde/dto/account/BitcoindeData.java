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
@JsonPropertyOrder({"btc_balances", "fidor_reservation", "encrypted_information"})
public class BitcoindeData {

  @JsonProperty("balances")
  private BitcoindeBalances balances;

  @JsonProperty("fidor_reservation")
  private BitcoindeFidorReservation fidorReservation;

  @JsonProperty("encrypted_information")
  private BitcoindeEncryptedInformation encryptedInformation;

  @JsonIgnore private Map<String, Object> additionalProperties = new HashMap<String, Object>();

  /** No args constructor for use in serialization */
  public BitcoindeData() {}

  /**
   * @param btcBalances
   * @param encryptedInformation
   * @param fidorReservation
   */
  public BitcoindeData(
      BitcoindeBalances btcBalances,
      BitcoindeFidorReservation fidorReservation,
      BitcoindeEncryptedInformation encryptedInformation) {
    super();
    this.balances = btcBalances;
    this.fidorReservation = fidorReservation;
    this.encryptedInformation = encryptedInformation;
  }

  @JsonProperty("btc_balances")
  public BitcoindeBalances getBalances() {
    return balances;
  }

  @JsonProperty("btc_balances")
  public void setBalances(BitcoindeBalances btcBalances) {
    this.balances = btcBalances;
  }

  @JsonProperty("fidor_reservation")
  public BitcoindeFidorReservation getFidorReservation() {
    return fidorReservation;
  }

  @JsonProperty("fidor_reservation")
  public void setFidorReservation(BitcoindeFidorReservation fidorReservation) {
    this.fidorReservation = fidorReservation;
  }

  @JsonProperty("encrypted_information")
  public BitcoindeEncryptedInformation getEncryptedInformation() {
    return encryptedInformation;
  }

  @JsonProperty("encrypted_information")
  public void setEncryptedInformation(BitcoindeEncryptedInformation encryptedInformation) {
    this.encryptedInformation = encryptedInformation;
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
