package org.knowm.xchange.okcoin.dto.account;

import com.fasterxml.jackson.annotation.JsonProperty;
import org.knowm.xchange.okcoin.dto.trade.OkCoinErrorResult;

public class OkCoinAccountRecords extends OkCoinErrorResult {
  private final OkCoinRecords[] records;
  private final String symbol;
  private final int errorCode;

  public OkCoinAccountRecords(
      @JsonProperty("error_code") final int errorCode,
      @JsonProperty("records") final OkCoinRecords[] records,
      @JsonProperty("symbol") final String symbol) {
    super(true, errorCode);
    this.errorCode = errorCode;
    this.records = records;
    this.symbol = symbol;
  }

  public OkCoinRecords[] getRecords() {
    return records;
  }

  public String getSymbol() {
    return symbol;
  }

  public int getErrorCode() {
    return errorCode;
  }

  public boolean isResult() {
    return (errorCode == 0);
  }
}
