package org.knowm.xchange.cryptofacilities.dto.marketdata;

import com.fasterxml.jackson.annotation.JsonProperty;
import java.math.BigDecimal;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import org.knowm.xchange.cryptofacilities.dto.CryptoFacilitiesResult;
import org.knowm.xchange.currency.CurrencyPair;

/** @author Panchen */
public class CryptoFacilitiesOrderBook extends CryptoFacilitiesResult {

  private static final SimpleDateFormat DATE_FORMAT =
      new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSSX");

  private final Date serverTime;
  private final CryptoFacilitiesBidsAsks bidsAsks;
  private CurrencyPair currencyPair;

  public CryptoFacilitiesOrderBook(
      @JsonProperty("result") String result,
      @JsonProperty("serverTime") String strServerTime,
      @JsonProperty("error") String error,
      @JsonProperty("orderBook") CryptoFacilitiesBidsAsks bidsAsks)
      throws ParseException {

    super(result, error);

    this.serverTime = strServerTime == null ? null : DATE_FORMAT.parse(strServerTime);
    this.bidsAsks = bidsAsks;
  }

  public List<List<BigDecimal>> getBids() {
    return bidsAsks.getBids();
  }

  public List<List<BigDecimal>> getAsks() {
    return bidsAsks.getAsks();
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
      return "CryptoFacilitiesOrderBook [ccyPair="
          + currencyPair
          + ", serverTime="
          + DATE_FORMAT.format(serverTime)
          + ", orderBook="
          + bidsAsks.toString()
          + "]";
    } else {
      return super.toString();
    }
  }
}
