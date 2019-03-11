package org.knowm.xchange.cryptopia.dto;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import org.knowm.xchange.cryptopia.dto.marketdata.CryptopiaTicker;

public class CryptopiaBaseResponseCryptopiaTicker extends CryptopiaBaseResponse<CryptopiaTicker> {

  @JsonCreator
  public CryptopiaBaseResponseCryptopiaTicker(
      @JsonProperty("Success") boolean success,
      @JsonProperty("Message") String message,
      @JsonProperty("Data") CryptopiaTicker data,
      @JsonProperty("Error") String error) {
    super(success, message, data, error);
  }
}
