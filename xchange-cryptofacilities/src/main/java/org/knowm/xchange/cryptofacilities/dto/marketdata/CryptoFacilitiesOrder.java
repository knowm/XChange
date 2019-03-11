package org.knowm.xchange.cryptofacilities.dto.marketdata;

import com.fasterxml.jackson.annotation.JsonProperty;
import java.text.ParseException;
import java.util.Date;
import org.knowm.xchange.cryptofacilities.Util;
import org.knowm.xchange.cryptofacilities.dto.CryptoFacilitiesResult;

/** @author Jean-Christophe Laruelle */
public class CryptoFacilitiesOrder extends CryptoFacilitiesResult {

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

    this.serverTime = Util.parseDate(strServerTime);
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
              + serverTime
              + ", "
              + orderStatus.toString()
              + "]";
      return res;
    } else return super.toString();
  }
}
