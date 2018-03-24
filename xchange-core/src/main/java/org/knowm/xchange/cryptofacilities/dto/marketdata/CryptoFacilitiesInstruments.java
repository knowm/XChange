package org.knowm.xchange.cryptofacilities.dto.marketdata;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import org.knowm.xchange.cryptofacilities.dto.CryptoFacilitiesResult;

import com.fasterxml.jackson.annotation.JsonProperty;

/**
 * @author Neil Panchen
 */

public class CryptoFacilitiesInstruments extends CryptoFacilitiesResult {

  private static final SimpleDateFormat DATE_FORMAT = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSSX");

  private final Date serverTime;
  private final List<CryptoFacilitiesInstrument> instruments;

  public CryptoFacilitiesInstruments(@JsonProperty("result") String result, @JsonProperty("serverTime") String strServerTime,
      @JsonProperty("error") String error, @JsonProperty("instruments") List<CryptoFacilitiesInstrument> instruments) throws ParseException {

    super(result, error);

    this.serverTime = DATE_FORMAT.parse(strServerTime);
    this.instruments = instruments;
  }

  public List<CryptoFacilitiesInstrument> getInstruments() {
    return instruments;
  }

  @Override
  public String toString() {

    if (isSuccess()) {
      StringBuilder res = new StringBuilder("CryptoFacilitiesInstruments [serverTime=" + DATE_FORMAT.format(serverTime) + ",instruments=");
      for (CryptoFacilitiesInstrument ct : instruments)
        res.append(ct.toString()).append(", ");
      res.append(" ]");

      return res.toString();
    } else {
      return super.toString();
    }
  }

}
