package org.knowm.xchange.cryptofacilities.dto.marketdata;

import java.math.BigDecimal;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

import com.fasterxml.jackson.annotation.JsonProperty;
import org.knowm.xchange.cryptofacilities.dto.CryptoFacilitiesResult;

/**
 * @author Jean-Christophe Laruelle
 */

@Deprecated
public class CryptoFacilitiesContract extends CryptoFacilitiesResult {

  private static final SimpleDateFormat DATE_FORMAT = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss");

  private final String unit;
  private final String tradeable;
  private final Date lastTradingDayAndTime;
  private final BigDecimal contractSize;
  private final BigDecimal tickSize;
  private final Boolean suspended;

  public CryptoFacilitiesContract(@JsonProperty("result") String result, @JsonProperty("error") String error, @JsonProperty("unit") String unit,
      @JsonProperty("tradeable") String tradeable, @JsonProperty("lastTradingDayAndTime") String strLastTradingDayAndTime,
      @JsonProperty("contractSize") BigDecimal contractSize, @JsonProperty("tickSize") BigDecimal tickSize,
      @JsonProperty("suspended") String strSuspended) throws ParseException {

    super(result, error);

    this.unit = unit;
    this.tradeable = tradeable;
    this.lastTradingDayAndTime = DATE_FORMAT.parse(strLastTradingDayAndTime);
    this.contractSize = contractSize;
    this.tickSize = tickSize;
    if (strSuspended.equalsIgnoreCase("true"))
      this.suspended = true;
    else
      this.suspended = false;
  }

  public String getUnit() {
    return unit;
  }

  public String getTradeable() {
    return tradeable;
  }

  public Date getLastTradingDayAndTime() {
    return lastTradingDayAndTime;
  }

  public BigDecimal getContractSize() {
    return contractSize;
  }

  public BigDecimal getTickSize() {
    return tickSize;
  }

  public Boolean getSuspended() {
    return suspended;
  }

  @Override
  public String toString() {
    return "CryptoFacilitiesContract [tradeable=" + tradeable + ", unit=" + unit + ", lastTradingDayAndTime="
        + DATE_FORMAT.format(lastTradingDayAndTime) + ", contractSize=" + contractSize + ", tickSize=" + tickSize + ", suspended=" + suspended + " ]";
  }
}
