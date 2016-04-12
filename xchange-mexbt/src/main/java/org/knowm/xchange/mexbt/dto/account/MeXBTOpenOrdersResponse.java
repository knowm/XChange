package org.knowm.xchange.mexbt.dto.account;

import java.util.Date;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import org.knowm.xchange.mexbt.dto.MeXBTResponse;
import org.knowm.xchange.mexbt.dto.TickDeserializer;

public class MeXBTOpenOrdersResponse extends MeXBTResponse {

  private final Date dateTimeUtc;
  private final MeXBTOpenOrdersInfo[] openOrdersInfo;

  public MeXBTOpenOrdersResponse(@JsonProperty("isAccepted") boolean isAccepted, @JsonProperty("rejectReason") String rejectReason,
      @JsonProperty("dateTimeUtc") @JsonDeserialize(using = TickDeserializer.class) Date dateTimeUtc,
      @JsonProperty("openOrdersInfo") MeXBTOpenOrdersInfo[] openOrdersInfo) {
    super(isAccepted, rejectReason);
    this.dateTimeUtc = dateTimeUtc;
    this.openOrdersInfo = openOrdersInfo;
  }

  public Date getDateTimeUtc() {
    return dateTimeUtc;
  }

  public MeXBTOpenOrdersInfo[] getOpenOrdersInfo() {
    return openOrdersInfo;
  }

}
