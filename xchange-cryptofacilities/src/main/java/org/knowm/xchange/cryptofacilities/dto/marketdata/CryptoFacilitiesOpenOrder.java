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

public class CryptoFacilitiesOpenOrder extends CryptoFacilitiesResult {

  private static final SimpleDateFormat DATE_FORMAT = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss");

  private final Date timestamp;
  private final String uid;
  private final String unit;
  private final String tradeable;
  private final String direction;
  private final BigDecimal quantity;
  private final BigDecimal filled;
  private final String type;
  private final BigDecimal limitPrice;
  private final BigDecimal stopPrice;

  public CryptoFacilitiesOpenOrder(@JsonProperty("result") String result, @JsonProperty("error") String error,
      @JsonProperty("timestamp") String strTimestamp, @JsonProperty("uid") String uid, @JsonProperty("unit") String unit,
      @JsonProperty("tradeable") String tradeable, @JsonProperty("dir") String direction, @JsonProperty("qty") BigDecimal quantity,
      @JsonProperty("filled") BigDecimal filled, @JsonProperty("type") String type, @JsonProperty("lmt") BigDecimal limitPrice,
      @JsonProperty("stp") BigDecimal stopPrice) throws ParseException {

    super(result, error);

    this.timestamp = DATE_FORMAT.parse(strTimestamp);
    this.uid = uid;
    this.unit = unit;
    this.tradeable = tradeable;
    this.direction = direction;
    this.quantity = quantity;
    this.filled = filled;
    this.type = type;
    this.limitPrice = limitPrice;
    this.stopPrice = stopPrice;
  }

  public String getUnit() {
    return unit;
  }

  public String getTradeable() {
    return tradeable;
  }

  public Date getTimestamp() {
    return timestamp;
  }

  public String getUid() {
    return uid;
  }

  public String getDirection() {
    return direction;
  }

  public BigDecimal getQuantity() {
    return quantity;
  }

  public BigDecimal getFilled() {
    return filled;
  }

  public String getType() {
    return type;
  }

  public BigDecimal getLimitPrice() {
    return limitPrice;
  }

  public BigDecimal getStopPrice() {
    return stopPrice;
  }

  @Override
  public String toString() {
    return "CryptoFacilitiesOpenOrder [uid=" + uid + ", timestamp=" + DATE_FORMAT.format(timestamp) + ", tradeable=" + tradeable + ", unit=" + unit
        + ", dir=" + direction + ", qty=" + quantity + ", filled=" + filled + ", type=" + type + ", limitPrice=" + limitPrice + ", stopPrice="
        + stopPrice + " ]";
  }
}
