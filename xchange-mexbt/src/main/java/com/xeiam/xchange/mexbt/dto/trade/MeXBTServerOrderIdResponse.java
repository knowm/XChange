package com.xeiam.xchange.mexbt.dto.trade;

import java.util.Date;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.xeiam.xchange.mexbt.dto.MeXBTResponse;
import com.xeiam.xchange.mexbt.dto.TickDeserializer;

public class MeXBTServerOrderIdResponse extends MeXBTResponse {

  private final String serverOrderId;
  private final Date dateTimeUtc;

  public MeXBTServerOrderIdResponse(@JsonProperty("isAccepted") boolean isAccepted, @JsonProperty("rejectReason") String rejectReason, @JsonProperty("serverOrderId") String serverOrderId,
      @JsonProperty("dateTimeUtc") @JsonDeserialize(using = TickDeserializer.class) Date dateTimeUtc) {
    super(isAccepted, rejectReason);
    this.serverOrderId = serverOrderId;
    this.dateTimeUtc = dateTimeUtc;
  }

  public String getServerOrderId() {
    return serverOrderId;
  }

  public Date getDateTimeUtc() {
    return dateTimeUtc;
  }

}
