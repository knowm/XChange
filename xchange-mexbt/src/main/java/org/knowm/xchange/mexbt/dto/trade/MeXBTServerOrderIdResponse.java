package org.knowm.xchange.mexbt.dto.trade;

import java.util.Date;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import org.knowm.xchange.mexbt.dto.MeXBTResponse;
import org.knowm.xchange.mexbt.dto.TickDeserializer;

public class MeXBTServerOrderIdResponse extends MeXBTResponse {

  private final long serverOrderId;
  private final Date dateTimeUtc;

  public MeXBTServerOrderIdResponse(@JsonProperty("isAccepted") boolean isAccepted, @JsonProperty("rejectReason") String rejectReason,
      @JsonProperty("serverOrderId") long serverOrderId,
      @JsonProperty("dateTimeUtc") @JsonDeserialize(using = TickDeserializer.class) Date dateTimeUtc) {
    super(isAccepted, rejectReason);
    this.serverOrderId = serverOrderId;
    this.dateTimeUtc = dateTimeUtc;
  }

  public long getServerOrderId() {
    return serverOrderId;
  }

  public Date getDateTimeUtc() {
    return dateTimeUtc;
  }

}
