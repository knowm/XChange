package org.knowm.xchange.bitcoinde.v4.dto;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Value;

@Value
@JsonIgnoreProperties(ignoreUnknown = true)
public class BitcoindeTradingPartnerInformation {

  String userName;
  Boolean kycFull;
  BitcoindeTrustLevel trustLevel;
  String depositor;
  String iban;
  String bankName;
  String bic;
  String seatOfBank;
  Integer rating;
  Integer amountTrades;

  @JsonCreator
  public BitcoindeTradingPartnerInformation(
      @JsonProperty("username") String userName,
      @JsonProperty("is_kyc_full") Boolean kycFull,
      @JsonProperty("trust_level") BitcoindeTrustLevel trustLevel,
      @JsonProperty("depositor") String depositor,
      @JsonProperty("iban") String iban,
      @JsonProperty("bank_name") String bankName,
      @JsonProperty("bic") String bic,
      @JsonProperty("seat_of_bank") String seatOfBank,
      @JsonProperty("rating") Integer rating,
      @JsonProperty("amount_trades") Integer amountTrades) {
    this.userName = userName;
    this.kycFull = kycFull;
    this.trustLevel = trustLevel;
    this.depositor = depositor;
    this.iban = iban;
    this.bankName = bankName;
    this.bic = bic;
    this.seatOfBank = seatOfBank;
    this.rating = rating;
    this.amountTrades = amountTrades;
  }
}
