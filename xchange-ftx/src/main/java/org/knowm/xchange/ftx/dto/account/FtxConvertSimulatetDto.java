package org.knowm.xchange.ftx.dto.account;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;

public class FtxConvertSimulatetDto {

  @JsonProperty("quoteId")
  private final int quoteId;

  @JsonCreator
  public FtxConvertSimulatetDto(@JsonProperty("quoteId") int quoteId) {
    this.quoteId = quoteId;
  }

  public int getQuoteId() {
    return quoteId;
  }

  @Override
  public String toString() {
    return "FtxConvertSimulatetDto [quoteId=" + quoteId + "]";
  }
}
