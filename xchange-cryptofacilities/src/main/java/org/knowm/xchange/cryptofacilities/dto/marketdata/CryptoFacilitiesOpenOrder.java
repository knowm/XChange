package org.knowm.xchange.cryptofacilities.dto.marketdata;

import com.fasterxml.jackson.annotation.JsonProperty;
import java.math.BigDecimal;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import org.knowm.xchange.cryptofacilities.dto.CryptoFacilitiesResult;

/** @author Jean-Christophe Laruelle */
public class CryptoFacilitiesOpenOrder extends CryptoFacilitiesResult {

  private static final SimpleDateFormat DATE_FORMAT =
      new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSSX");

  private final Date receivedTime;
  private final String status;
  private final String order_id;
  private final String orderType;
  private final String symbol;
  private final String side;
  private final BigDecimal unfilledSize;
  private final BigDecimal filledSize;
  private final BigDecimal limitPrice;
  private final BigDecimal stopPrice;

  public CryptoFacilitiesOpenOrder(
      @JsonProperty("result") String result,
      @JsonProperty("error") String error,
      @JsonProperty("receivedTime") String strReceivedTime,
      @JsonProperty("status") String status,
      @JsonProperty("order_id") String order_id,
      @JsonProperty("orderType") String orderType,
      @JsonProperty("symbol") String symbol,
      @JsonProperty("side") String side,
      @JsonProperty("unfilledSize") BigDecimal unfilledSize,
      @JsonProperty("filledSize") BigDecimal filledSize,
      @JsonProperty("limitPrice") BigDecimal limitPrice,
      @JsonProperty("stopPrice") BigDecimal stopPrice)
      throws ParseException {

    super(result, error);

    this.receivedTime = strReceivedTime == null ? null : DATE_FORMAT.parse(strReceivedTime);
    this.status = status;
    this.order_id = order_id;
    this.orderType = orderType;
    this.symbol = symbol;
    this.side = side;
    this.unfilledSize = unfilledSize;
    this.filledSize = filledSize;
    this.limitPrice = limitPrice;
    this.stopPrice = stopPrice;
  }

  public Date getTimestamp() {
    return receivedTime;
  }

  public String getStatus() {
    return status;
  }

  public String getId() {
    return order_id;
  }

  public String getType() {
    return orderType;
  }

  public String getSymbol() {
    return symbol;
  }

  public String getDirection() {
    return side;
  }

  public BigDecimal getUnfilled() {
    return unfilledSize;
  }

  public BigDecimal getFilled() {
    return filledSize;
  }

  public BigDecimal getQuantity() {
    return filledSize.add(unfilledSize);
  }

  public BigDecimal getLimitPrice() {
    return limitPrice;
  }

  public BigDecimal getStopPrice() {
    return stopPrice;
  }

  @Override
  public String toString() {
    return "CryptoFacilitiesOpenOrder [order_id="
        + order_id
        + ", status="
        + status
        + ", orderType="
        + orderType
        + ", symbol="
        + symbol
        + ", dir="
        + side
        + ", unfilled="
        + unfilledSize
        + ", filled="
        + filledSize
        + ", qty="
        + getQuantity()
        + ", limitPrice="
        + limitPrice
        + ", stopPrice="
        + stopPrice
        + ", timeStamp="
        + DATE_FORMAT.format(receivedTime)
        + "]";
  }
}
