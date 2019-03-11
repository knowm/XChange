package org.knowm.xchange.cryptopia.dto;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import org.knowm.xchange.cryptopia.dto.marketdata.CryptopiaOrderBook;

public class CryptopiaBaseResponseCryptopiaOrderBook
    extends CryptopiaBaseResponse<CryptopiaOrderBook> {

  @JsonCreator
  public CryptopiaBaseResponseCryptopiaOrderBook(
      @JsonProperty("Success") boolean success,
      @JsonProperty("Message") String message,
      @JsonProperty("Data") CryptopiaOrderBook data,
      @JsonProperty("Error") String error) {
    super(success, message, data, error);
  }
}
