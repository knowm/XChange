package org.knowm.xchange.ftx.dto.account;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;

public class FtxConvertAcceptPayloadRequestDto {

  @JsonProperty("quoteId")
  private final int quoteId;

  @JsonCreator
  public FtxConvertAcceptPayloadRequestDto(@JsonProperty("quoteId") int quoteId) {
    this.quoteId = quoteId;
  }

  public int getQuoteId() {
    return quoteId;
  }
}
