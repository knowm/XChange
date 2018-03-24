package org.knowm.xchange.cryptopia.dto;

import java.util.List;

import org.knowm.xchange.cryptopia.dto.marketdata.CryptopiaCurrency;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;

public class CryptopiaBaseResponseCryptopiaCurrency extends CryptopiaBaseResponse<List<CryptopiaCurrency>> {

  @JsonCreator
  public CryptopiaBaseResponseCryptopiaCurrency(@JsonProperty("Success") boolean success, @JsonProperty("Message") String message,
      @JsonProperty("Data") List<CryptopiaCurrency> data, @JsonProperty("Error") String error) {
    super(success, message, data, error);
  }
}
