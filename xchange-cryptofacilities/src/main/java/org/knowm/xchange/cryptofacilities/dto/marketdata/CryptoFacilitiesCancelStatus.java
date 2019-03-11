package org.knowm.xchange.cryptofacilities.dto.marketdata;

import com.fasterxml.jackson.annotation.JsonProperty;
import java.text.ParseException;
import java.util.Date;
import org.knowm.xchange.cryptofacilities.Util;

/** @author Panchen */
public class CryptoFacilitiesCancelStatus {

  private final Date receivedTime;
  private final String status;

  public CryptoFacilitiesCancelStatus(
      @JsonProperty("receivedTime") String strReceivedTime, @JsonProperty("status") String status)
      throws ParseException {

    this.receivedTime = Util.parseDate(strReceivedTime);
    this.status = status;
  }

  public Date getReceivedTime() {
    return receivedTime;
  }

  public String getStatus() {
    return status;
  }

  @Override
  public String toString() {
    return "CryptoFacilitiesCancelStatus [status="
        + status
        + ", receivedTime="
        + receivedTime
        + "]";
  }
}
