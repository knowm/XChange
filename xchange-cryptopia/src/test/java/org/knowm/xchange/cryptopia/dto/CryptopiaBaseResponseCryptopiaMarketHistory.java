package org.knowm.xchange.cryptopia.dto;

import java.util.List;

import org.knowm.xchange.cryptopia.dto.marketdata.CryptopiaMarketHistory;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;

public class CryptopiaBaseResponseCryptopiaMarketHistory extends CryptopiaBaseResponse<List<CryptopiaMarketHistory>> {

  @JsonCreator
  public CryptopiaBaseResponseCryptopiaMarketHistory(@JsonProperty("Success") boolean success, @JsonProperty("Message") String message,
      @JsonProperty("Data") List<CryptopiaMarketHistory> data, @JsonProperty("Error") String error) {
    super(success, message, data, error);
  }
}
