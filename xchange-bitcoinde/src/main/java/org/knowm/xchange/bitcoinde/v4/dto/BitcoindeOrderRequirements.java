package org.knowm.xchange.bitcoinde.v4.dto;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Value;

@Value
@JsonIgnoreProperties(ignoreUnknown = true)
public class BitcoindeOrderRequirements {

  BitcoindeTrustLevel minTrustLevel;
  Boolean onlyKycFull;
  String[] seatOfBank;
  BitcoindePaymentOption paymentOption;

  @JsonCreator
  public BitcoindeOrderRequirements(
      @JsonProperty("min_trust_level") BitcoindeTrustLevel minTrustLevel,
      @JsonProperty("only_kyc_full") Boolean onlyKycFull,
      @JsonProperty("seat_of_bank") String[] seatOfBank,
      @JsonProperty("payment_option") BitcoindePaymentOption paymentOption) {
    this.minTrustLevel = minTrustLevel;
    this.onlyKycFull = onlyKycFull;
    this.seatOfBank = seatOfBank;
    this.paymentOption = paymentOption;
  }
}
