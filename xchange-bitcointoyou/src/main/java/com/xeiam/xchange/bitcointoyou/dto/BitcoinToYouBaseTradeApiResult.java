package com.xeiam.xchange.bitcointoyou.dto;

import com.fasterxml.jackson.annotation.JsonProperty;

/**
 * @author Felipe Micaroni Lalli
 */
public class BitcoinToYouBaseTradeApiResult<R> {

  private final Integer success;
  private final String error;
  private final R theReturn;
  private final String date;
  private final Long timestamp;

  public BitcoinToYouBaseTradeApiResult(@JsonProperty("success") Integer success, @JsonProperty("error") String error,
      @JsonProperty("oReturn") R theReturn, @JsonProperty("date") String date, @JsonProperty("timestamp") Long timestamp) {

    this.success = success;
    this.error = error;
    this.theReturn = theReturn;
    this.date = date;
    this.timestamp = timestamp;
  }

  public Long getTimestamp() {

    return timestamp;
  }

  public String getDate() {

    return date;
  }

  public Integer getSuccess() {

    return success;
  }

  public String getError() {

    return error;
  }

  public R getTheReturn() {

    return theReturn;
  }

  @Override
  public String toString() {

    return "BitcoinToYouBaseTradeApiResult [" + "success=" + success + ", error='" + error + '\'' + ", theReturn=" + theReturn + ", date='" + date
        + '\'' + ", timestamp=" + timestamp + ']';
  }
}
