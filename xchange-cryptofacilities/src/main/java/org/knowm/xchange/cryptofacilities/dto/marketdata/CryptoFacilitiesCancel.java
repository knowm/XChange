package org.knowm.xchange.cryptofacilities.dto.marketdata;

import com.fasterxml.jackson.annotation.JsonProperty;
import java.text.ParseException;
import java.util.Date;
import org.knowm.xchange.cryptofacilities.Util;
import org.knowm.xchange.cryptofacilities.dto.CryptoFacilitiesResult;

/** @author Jean-Christophe Laruelle */
public class CryptoFacilitiesCancel extends CryptoFacilitiesResult {

  private final Date serverTime;
  private final CryptoFacilitiesCancelStatus cancelStatus;

  public CryptoFacilitiesCancel(
      @JsonProperty("result") String result,
      @JsonProperty("serverTime") String strServerTime,
      @JsonProperty("cancelStatus") CryptoFacilitiesCancelStatus cancelStatus,
      @JsonProperty("error") String error)
      throws ParseException {

    super(result, error);

    this.serverTime = Util.parseDate(strServerTime);
    this.cancelStatus = cancelStatus;
  }

  public String getStatus() {
    return (cancelStatus == null ? this.getError() : cancelStatus.getStatus());
  }

  @Override
  public String toString() {

    if (isSuccess() && serverTime != null && cancelStatus != null) {
      String res =
          "CryptoFacilitiesCancel [result="
              + this.getResult()
              + ", serverTime="
              + serverTime
              + ", "
              + cancelStatus.toString()
              + "]";

      return res;
    } else {
      return super.toString();
    }
  }
}
