package org.knowm.xchange.cryptopia.dto;

import java.util.List;

import org.knowm.xchange.cryptopia.dto.marketdata.CryptopiaTradePair;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;

public class CryptopiaBaseResponseCryptopiaTradePair extends CryptopiaBaseResponse<List<CryptopiaTradePair>> {

  @JsonCreator
  public CryptopiaBaseResponseCryptopiaTradePair(@JsonProperty("Success") boolean success, @JsonProperty("Message") String message,
      @JsonProperty("Data") List<CryptopiaTradePair> data, @JsonProperty("Error") String error) {
    super(success, message, data, error);
  }
}
