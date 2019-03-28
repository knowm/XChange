package org.knowm.xchange.cryptofacilities.dto.marketdata;

import com.fasterxml.jackson.annotation.JsonProperty;
import java.text.ParseException;
import java.util.Date;
import java.util.List;
import org.knowm.xchange.cryptofacilities.Util;
import org.knowm.xchange.cryptofacilities.dto.CryptoFacilitiesResult;

/** @author Panchen */
public class CryptoFacilitiesFills extends CryptoFacilitiesResult {

  private final Date serverTime;
  private final List<CryptoFacilitiesFill> fills;

  public CryptoFacilitiesFills(
      @JsonProperty("result") String result,
      @JsonProperty("serverTime") String strServerTime,
      @JsonProperty("error") String error,
      @JsonProperty("fills") List<CryptoFacilitiesFill> fills)
      throws ParseException {

    super(result, error);

    this.serverTime = Util.parseDate(strServerTime);
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
      StringBuilder res =
          new StringBuilder("CryptoFacilitiesFills [serverTime=" + serverTime + ", fills=");
      for (CryptoFacilitiesFill fill : fills) res.append(fill.toString()).append(", ");
      res.append(" ]");

      return res.toString();
    } else {
      return super.toString();
    }
  }
}
