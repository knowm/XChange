package org.knowm.xchange.cryptofacilities.dto.marketdata;

import com.fasterxml.jackson.annotation.JsonProperty;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import org.knowm.xchange.cryptofacilities.dto.CryptoFacilitiesResult;

/** @author Panchen */
public class CryptoFacilitiesOpenPositions extends CryptoFacilitiesResult {

  private static final SimpleDateFormat DATE_FORMAT =
      new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSSX");

  private final Date serverTime;
  private final List<CryptoFacilitiesOpenPosition> openPositions;

  public CryptoFacilitiesOpenPositions(
      @JsonProperty("result") String result,
      @JsonProperty("serverTime") String strServerTime,
      @JsonProperty("error") String error,
      @JsonProperty("openPositions") List<CryptoFacilitiesOpenPosition> openPositions)
      throws ParseException {

    super(result, error);

    this.serverTime = strServerTime == null ? null : DATE_FORMAT.parse(strServerTime);
    this.openPositions = openPositions;
  }

  public List<CryptoFacilitiesOpenPosition> getOpenPositions() {
    return openPositions;
  }

  public Date getServerTime() {
    return serverTime;
  }

  @Override
  public String toString() {

    if (isSuccess()) {
      StringBuilder res =
          new StringBuilder(
              "CryptoFacilitiesOpenPositions [serverTime="
                  + DATE_FORMAT.format(serverTime)
                  + ", openPositions=");
      for (CryptoFacilitiesOpenPosition openPosition : openPositions)
        res.append(openPosition.toString()).append(", ");
      res.append(" ]");

      return res.toString();
    } else {
      return super.toString();
    }
  }
}
