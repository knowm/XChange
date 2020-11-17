package org.knowm.xchange.bitcoinde.v4.dto.account;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import java.util.Map;
import lombok.Value;

@Value
@JsonIgnoreProperties(ignoreUnknown = true)
public class BitcoindeData {

  Map<String, BitcoindeBalance> balances;
  BitcoindeFidorReservation fidorReservation;
  BitcoindeEncryptedInformation encryptedInformation;

  @JsonCreator
  public BitcoindeData(
      @JsonProperty("balances") Map<String, BitcoindeBalance> btcBalances,
      @JsonProperty("fidor_reservation") BitcoindeFidorReservation fidorReservation,
      @JsonProperty("encrypted_information") BitcoindeEncryptedInformation encryptedInformation) {
    this.balances = btcBalances;
    this.fidorReservation = fidorReservation;
    this.encryptedInformation = encryptedInformation;
  }
}
