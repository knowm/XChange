package org.knowm.xchange.cryptofacilities.dto.marketdata;

import com.fasterxml.jackson.annotation.JsonProperty;
import java.text.ParseException;
import java.util.Date;
import java.util.List;
import org.knowm.xchange.cryptofacilities.Util;
import org.knowm.xchange.cryptofacilities.dto.CryptoFacilitiesResult;
import org.knowm.xchange.currency.CurrencyPair;

/** @author Panchen */
public class CryptoFacilitiesPublicFills extends CryptoFacilitiesResult {

  private final Date serverTime;
  private final List<CryptoFacilitiesPublicFill> fills;
  private CurrencyPair currencyPair;

  public CryptoFacilitiesPublicFills(
      @JsonProperty("result") String result,
      @JsonProperty("serverTime") String strServerTime,
      @JsonProperty("error") String error,
      @JsonProperty("history") List<CryptoFacilitiesPublicFill> fills)
      throws ParseException {

    super(result, error);

    this.serverTime = Util.parseDate(strServerTime);
    this.fills = fills;
  }

  public List<CryptoFacilitiesPublicFill> getFills() {
    return fills;
  }

  public Date getServerTime() {
    return serverTime;
  }

  public CurrencyPair getCurrencyPair() {
    return currencyPair;
  }

  public void setCurrencyPair(CurrencyPair currencyPair) {
    this.currencyPair = currencyPair;
  }

  @Override
  public String toString() {

    if (isSuccess()) {
      StringBuilder res =
          new StringBuilder(
              "CryptoFacilitiesPublicFills [serverTime="
                  + serverTime
                  + ", ccyPair="
                  + currencyPair
                  + ", fills=");
      for (CryptoFacilitiesPublicFill fill : fills) res.append(fill.toString()).append(", ");
      res.append(" ]");

      return res.toString();
    } else {
      return super.toString();
    }
  }
}
