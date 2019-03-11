package org.knowm.xchange.cryptopia.dto;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import java.util.List;
import org.knowm.xchange.cryptopia.dto.marketdata.CryptopiaMarketHistory;

public class CryptopiaBaseResponseCryptopiaMarketHistory
    extends CryptopiaBaseResponse<List<CryptopiaMarketHistory>> {

  @JsonCreator
  public CryptopiaBaseResponseCryptopiaMarketHistory(
      @JsonProperty("Success") boolean success,
      @JsonProperty("Message") String message,
      @JsonProperty("Data") List<CryptopiaMarketHistory> data,
      @JsonProperty("Error") String error) {
    super(success, message, data, error);
  }
}
