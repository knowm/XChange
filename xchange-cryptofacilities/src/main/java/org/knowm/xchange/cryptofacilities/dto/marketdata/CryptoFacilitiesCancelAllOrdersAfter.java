package org.knowm.xchange.cryptofacilities.dto.marketdata;

import com.fasterxml.jackson.annotation.JsonProperty;
import java.text.ParseException;
import java.util.Date;
import lombok.Data;
import org.knowm.xchange.cryptofacilities.Util;
import org.knowm.xchange.cryptofacilities.dto.CryptoFacilitiesResult;

@Data
public class CryptoFacilitiesCancelAllOrdersAfter extends CryptoFacilitiesResult {

  private final Date serverTime;
  private final Status status;

  public CryptoFacilitiesCancelAllOrdersAfter(
      @JsonProperty("result") String result,
      @JsonProperty("serverTime") String strServerTime,
      @JsonProperty("status") Status status,
      @JsonProperty("error") String error)
      throws ParseException {

    super(result, error);
    this.serverTime = Util.parseDate(strServerTime);
    this.status = status;
  }

  @Data
  public static class Status {
    private Date currentTime;
    private Date triggerTime;
  }
}
