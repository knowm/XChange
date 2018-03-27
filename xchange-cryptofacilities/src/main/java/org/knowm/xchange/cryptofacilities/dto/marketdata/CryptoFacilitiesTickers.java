package org.knowm.xchange.cryptofacilities.dto.marketdata;

import com.fasterxml.jackson.annotation.JsonProperty;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import org.knowm.xchange.cryptofacilities.dto.CryptoFacilitiesResult;

/** @author Neil Panchen */
public class CryptoFacilitiesTickers extends CryptoFacilitiesResult {

  private static final SimpleDateFormat DATE_FORMAT =
      new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSSX");

  private final Date serverTime;
  private final List<CryptoFacilitiesTicker> tickers;

  public CryptoFacilitiesTickers(
      @JsonProperty("result") String result,
      @JsonProperty("serverTime") String strServerTime,
      @JsonProperty("error") String error,
      @JsonProperty("tickers") List<CryptoFacilitiesTicker> tickers)
      throws ParseException {

    super(result, error);

    this.serverTime = strServerTime == null ? null : DATE_FORMAT.parse(strServerTime);
    this.tickers = tickers;
  }

  public Date getServerTime() {
    return serverTime;
  }

  public List<CryptoFacilitiesTicker> getTickers() {
    return tickers;
  }

  public CryptoFacilitiesTicker getTicker(String symbol) {
    if (isSuccess() && tickers != null) {
      for (CryptoFacilitiesTicker ticker : tickers) {
        if (ticker != null && ticker.getSymbol().equalsIgnoreCase(symbol)) {
          return ticker;
        }
      }
    }
    return null;
  }

  @Override
  public String toString() {

    if (isSuccess()) {
      StringBuilder res =
          new StringBuilder(
              "CryptoFacilitiesTickers [serverTime="
                  + DATE_FORMAT.format(serverTime)
                  + ", tickers=");
      for (CryptoFacilitiesTicker ticker : tickers) {
        res.append(ticker.toString()).append(", ");
      }
      res.append(" ]");

      return res.toString();
    } else {
      return super.toString();
    }
  }
}
