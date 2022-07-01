package org.knowm.xchange.cryptofacilities.dto.marketdata;

import com.fasterxml.jackson.annotation.JsonProperty;
import java.text.ParseException;
import java.util.Date;
import java.util.List;
import org.knowm.xchange.cryptofacilities.Util;
import org.knowm.xchange.cryptofacilities.dto.CryptoFacilitiesResult;

/** @author Neil Panchen */
public class CryptoFacilitiesInstruments extends CryptoFacilitiesResult {

  private final Date serverTime;
  private final List<CryptoFacilitiesInstrument> instruments;

  public CryptoFacilitiesInstruments(
      @JsonProperty("result") String result,
      @JsonProperty("serverTime") String strServerTime,
      @JsonProperty("error") String error,
      @JsonProperty("instruments") List<CryptoFacilitiesInstrument> instruments)
      throws ParseException {

    super(result, error);

    this.serverTime = Util.parseDate(strServerTime);
    this.instruments = instruments;
  }

  public List<CryptoFacilitiesInstrument> getInstruments() {
    return instruments;
  }

  @Override
  public String toString() {

    if (isSuccess()) {
      StringBuilder res =
          new StringBuilder(
              "CryptoFacilitiesInstruments [serverTime=" + serverTime + ",instruments=");
      for (CryptoFacilitiesInstrument ct : instruments) res.append(ct.toString()).append(", ");
      res.append(" ]");

      return res.toString();
    } else {
      return super.toString();
    }
  }
}
