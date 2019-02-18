package org.knowm.xchange.cryptofacilities.dto.marketdata;

import com.fasterxml.jackson.annotation.JsonProperty;
import java.text.ParseException;
import java.util.Date;
import java.util.List;
import org.knowm.xchange.cryptofacilities.Util;
import org.knowm.xchange.cryptofacilities.dto.CryptoFacilitiesResult;

/** @author Neil Panchen */
public class CryptoFacilitiesTickers extends CryptoFacilitiesResult {

  private final Date serverTime;
  private final List<CryptoFacilitiesTicker> tickers;

  public CryptoFacilitiesTickers(
      @JsonProperty("result") String result,
      @JsonProperty("serverTime") String strServerTime,
      @JsonProperty("error") String error,
      @JsonProperty("tickers") List<CryptoFacilitiesTicker> tickers)
      throws ParseException {

    super(result, error);

    this.serverTime = Util.parseDate(strServerTime);
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
          new StringBuilder("CryptoFacilitiesTickers [serverTime=" + serverTime + ", tickers=");
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
