package com.xeiam.xchange.cryptofacilities.dto.marketdata;

import java.util.List;
import java.util.Date;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.xeiam.xchange.cryptofacilities.dto.CryptoFacilitiesResult;
import java.text.ParseException;
import java.text.SimpleDateFormat;

/**
 * @author Panchen
 */

public class CryptoFacilitiesFills extends CryptoFacilitiesResult {

  private static final SimpleDateFormat DATE_FORMAT = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSSX");

  private final Date serverTime;
  private final List<CryptoFacilitiesFill> fills;

  public CryptoFacilitiesFills(@JsonProperty("result") String result, @JsonProperty("serverTime") String strServerTime,
      @JsonProperty("error") String error, @JsonProperty("fills") List<CryptoFacilitiesFill> fills) throws ParseException {

    super(result, error);

    this.serverTime = DATE_FORMAT.parse(strServerTime);
    this.fills = fills;
  }

  public List<CryptoFacilitiesFill> getFills() {
    return fills;
  }

  public Date getServerTime() {
    return serverTime;
  }

  @Override
  public String toString() {

    if (isSuccess()) {
      String res = "CryptoFacilitiesFills [serverTime=" + DATE_FORMAT.format(serverTime) + ", fills=";
      for (CryptoFacilitiesFill fill : fills)
        res = res + fill.toString() + ", ";
      res = res + " ]";

      return res;
    } else {
      return super.toString();
    }
  }

}
