package org.knowm.xchange.cryptofacilities.dto.marketdata;

import com.fasterxml.jackson.annotation.JsonProperty;
import java.text.ParseException;
import java.util.Date;
import org.knowm.xchange.cryptofacilities.Util;

/** @author Panchen */
public class CryptoFacilitiesOrderStatus {

  private final Date receivedTime;
  private final String status;
  private final String order_id;

  public CryptoFacilitiesOrderStatus(
      @JsonProperty("receivedTime") String strReceivedTime,
      @JsonProperty("status") String status,
      @JsonProperty("order_id") String order_id)
      throws ParseException {

    this.receivedTime = Util.parseDate(strReceivedTime);
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
        + receivedTime
        + "]";
  }
}
