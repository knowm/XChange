package org.knowm.xchange.cryptofacilities.dto.marketdata;

import com.fasterxml.jackson.annotation.JsonProperty;
import java.math.BigDecimal;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import org.knowm.xchange.cryptofacilities.dto.CryptoFacilitiesResult;

/** @author Neil Panchen */
public class CryptoFacilitiesInstrument extends CryptoFacilitiesResult {

  private static final SimpleDateFormat DATE_FORMAT =
      new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSSX");

  private final boolean tradeable;
  private final Date lastTradingTime;
  private final String symbol;
  private final String underlying;
  private final BigDecimal contractSize;
  private final String type;
  private final BigDecimal tickSize;

  public CryptoFacilitiesInstrument(
      @JsonProperty("result") String result,
      @JsonProperty("error") String error,
      @JsonProperty("tradeable") String strTradeable,
      @JsonProperty("lastTradingTime") String strLastTradingTime,
      @JsonProperty("symbol") String symbol,
      @JsonProperty("underlying") String underlying,
      @JsonProperty("contractSize") BigDecimal contractSize,
      @JsonProperty("type") String type,
      @JsonProperty("tickSize") BigDecimal tickSize)
      throws ParseException {

    super(result, error);

    this.tradeable = strTradeable.equalsIgnoreCase("true");
    this.lastTradingTime =
        strLastTradingTime == null ? null : DATE_FORMAT.parse(strLastTradingTime);
    this.symbol = symbol;
    this.underlying = underlying;
    this.contractSize = contractSize;
    this.type = type;
    this.tickSize = tickSize;
  }

  public Boolean getTradeable() {
    return tradeable;
  }

  public Date getLastTradingTime() {
    return lastTradingTime;
  }

  public String getSymbol() {
    return symbol;
  }

  public String getUnderlying() {
    return underlying;
  }

  public BigDecimal getContractSize() {
    return contractSize;
  }

  public String getType() {
    return type;
  }

  public BigDecimal getTickSize() {
    return tickSize;
  }

  @Override
  public String toString() {
    return "CryptoFacilitiesInstrument [tradeable="
        + tradeable
        + ", lastTradingTime="
        + (lastTradingTime == null ? "null" : DATE_FORMAT.format(lastTradingTime))
        + ", symbol="
        + symbol
        + ", underlying="
        + underlying
        + ", contractSize="
        + contractSize
        + ", type="
        + type
        + ", tickSize="
        + tickSize
        + " ]";
  }
}
