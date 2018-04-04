package org.knowm.xchange.cryptofacilities.dto.marketdata;

import com.fasterxml.jackson.annotation.JsonProperty;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

/** @author Panchen */
public class CryptoFacilitiesOrderStatus {

  private static final SimpleDateFormat DATE_FORMAT =
      new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSSX");

  private final Date receivedTime;
  private final String status;
  private final String order_id;

  public CryptoFacilitiesOrderStatus(
      @JsonProperty("receivedTime") String strReceivedTime,
      @JsonProperty("status") String status,
      @JsonProperty("order_id") String order_id)
      throws ParseException {

    this.receivedTime = strReceivedTime == null ? null : DATE_FORMAT.parse(strReceivedTime);
    this.status = status;
    this.order_id = order_id;
  }

  public Date getReceivedTime() {
    return receivedTime;
  }

  public String getStatus() {
    return status;
  }

  public String getOrderId() {
    return order_id;
  }

  public String toString() {
    return "CryptoFacilitiesOrderStatus [order_id="
        + order_id
        + ", status="
        + status
        + ", receivedTime="
        + DATE_FORMAT.format(receivedTime)
        + "]";
  }
}
