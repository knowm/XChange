package org.knowm.xchange.cryptofacilities.dto.marketdata;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

import com.fasterxml.jackson.annotation.JsonProperty;

/**
 * @author Panchen
 */

public class CryptoFacilitiesCancelStatus {

  private static final SimpleDateFormat DATE_FORMAT = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSSX");

  private final Date receivedTime;
  private final String status;

  public CryptoFacilitiesCancelStatus(@JsonProperty("receivedTime") String strReceivedTime,
      @JsonProperty("status") String status) throws ParseException {

    this.receivedTime = strReceivedTime == null ? null : DATE_FORMAT.parse(strReceivedTime);
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
    return "CryptoFacilitiesCancelStatus [status=" + status + ", receivedTime=" + (receivedTime == null ? "" : DATE_FORMAT.format(receivedTime))
        + "]";
  }
}
