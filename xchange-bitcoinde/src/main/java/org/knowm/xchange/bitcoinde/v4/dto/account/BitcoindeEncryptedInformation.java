package org.knowm.xchange.bitcoinde.v4.dto.account;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Value;

@Value
@JsonIgnoreProperties(ignoreUnknown = true)
public class BitcoindeEncryptedInformation {

  String bicShort;
  String bicFull;
  String uid;

  @JsonCreator
  public BitcoindeEncryptedInformation(
      @JsonProperty("bic_short") String bicShort,
      @JsonProperty("bic_full") String bicFull,
      @JsonProperty("uid") String uid) {
    this.bicShort = bicShort;
    this.bicFull = bicFull;
    this.uid = uid;
  }
}
