package org.knowm.xchange.mexbt.dto.account;

import java.util.Date;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import org.knowm.xchange.mexbt.dto.MeXBTResponse;
import org.knowm.xchange.mexbt.dto.TickDeserializer;

public class MeXBTTradeResponse extends MeXBTResponse {

  private final Date dateTimeUtc;
  private final String ins;
  private final long startIndex;
  private final int count;
  private final MeXBTUserTrade[] trades;

  public MeXBTTradeResponse(@JsonProperty("isAccepted") boolean isAccepted, @JsonProperty("rejectReason") String rejectReason,
      @JsonProperty("dateTimeUtc") @JsonDeserialize(using = TickDeserializer.class) Date dateTimeUtc, @JsonProperty("ins") String ins,
      @JsonProperty("startIndex") long startIndex, @JsonProperty("count") int count, @JsonProperty("trades") MeXBTUserTrade[] trades) {
    super(isAccepted, rejectReason);
    this.dateTimeUtc = dateTimeUtc;
    this.ins = ins;
    this.startIndex = startIndex;
    this.count = count;
    this.trades = trades;
  }

  public Date getDateTimeUtc() {
    return dateTimeUtc;
  }

  public String getIns() {
    return ins;
  }

  public long getStartIndex() {
    return startIndex;
  }

  public int getCount() {
    return count;
  }

  public MeXBTUserTrade[] getTrades() {
    return trades;
  }

}
