package org.knowm.xchange.cryptofacilities.dto.marketdata;

import com.fasterxml.jackson.annotation.JsonProperty;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import org.knowm.xchange.cryptofacilities.dto.CryptoFacilitiesResult;

/** @author Jean-Christophe Laruelle */
public class CryptoFacilitiesOrder extends CryptoFacilitiesResult {

  private static final SimpleDateFormat DATE_FORMAT =
      new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSSX");

  private final Date serverTime;
  private final CryptoFacilitiesOrderStatus orderStatus;

  private final String orderId;

  public CryptoFacilitiesOrder(
      @JsonProperty("result") String result,
      @JsonProperty("serverTime") String strServerTime,
      @JsonProperty("sendStatus") CryptoFacilitiesOrderStatus orderStatus,
      @JsonProperty("error") String error,
      @JsonProperty("orderId") String orderId)
      throws ParseException {

    super(result, error);

    this.serverTime = strServerTime == null ? null : DATE_FORMAT.parse(strServerTime);
    this.orderStatus = orderStatus;
    this.orderId = orderStatus == null ? orderId : orderStatus.getOrderId();
  }

  public String getOrderId() {
    return orderId;
  }

  public String getStatus() {
    return (orderStatus == null ? this.getError() : orderStatus.getStatus());
  }

  @Override
  public String toString() {

    if (isSuccess() && serverTime != null && orderStatus != null) {
      String res =
          "CryptoFacilitiesOrder [result="
              + this.getResult()
              + ", serverTime="
              + DATE_FORMAT.format(serverTime)
              + ", "
              + orderStatus.toString()
              + "]";
      return res;
    } else return super.toString();
  }
}
