package org.knowm.xchange.bitmex.dto.marketdata;

import com.fasterxml.jackson.annotation.JsonProperty;
import java.math.BigDecimal;
import java.util.Date;
import org.knowm.xchange.bitmex.AbstractHttpResponseAware;
import org.knowm.xchange.bitmex.dto.trade.BitmexSide;

/** see field description at http://www.onixs.biz/fix-dictionary/5.0.SP2/fields_by_name.html */
public class BitmexPrivateOrder extends AbstractHttpResponseAware {

  private final BigDecimal price;
  private final BigDecimal size;
  private final String symbol;
  private final String id;
  private final String clOrdID;
  private final BitmexSide side;
  private final Date timestamp;
  private final OrderStatus orderStatus;
  private final String currency;
  private final String settleCurrency;

  private final String clOrdLinkID;

  private final BigDecimal simpleOrderQty;
  private final BigDecimal displayQty;
  private final BigDecimal stopPx;
  private final BigDecimal pegOffsetValue;
  private final String pegPriceType;
  private final String ordType;
  private final String timeInForce;
  private final String execInst;
  private final String contingencyType;
  private final String exDestination;
  private final String triggered;
  private final boolean workingIndicator;
  private final String ordRejReason;

  private final BigDecimal simpleLeavesQty;
  private final BigDecimal leavesQty;
  private final BigDecimal simpleCumQty;
  private final BigDecimal cumQty;
  private final BigDecimal avgPx;

  private final String multiLegReportingType;
  private final String text;

  // "2018-06-03T05:22:49.018Z"
  private final Date transactTime;

  private final String error;

  public BitmexPrivateOrder(
      @JsonProperty("price") BigDecimal price,
      @JsonProperty("orderID") String id,
      @JsonProperty("orderQty") BigDecimal size,
      @JsonProperty("side") BitmexSide side,
      @JsonProperty("symbol") String symbol,
      @JsonProperty("clOrdID") String clOrdID,
      @JsonProperty("timestamp") Date timestamp,
      @JsonProperty("ordStatus") OrderStatus orderStatus,
      @JsonProperty("currency") String currency,
      @JsonProperty("settlCurrency") String settleCurrency,
      @JsonProperty("clOrdLinkID") String clOrdLinkID,
      @JsonProperty("simpleOrderQty") BigDecimal simpleOrderQty,
      @JsonProperty("displayQty") BigDecimal displayQty,
      @JsonProperty("stopPx") BigDecimal stopPx,
      @JsonProperty("pegOffsetValue") BigDecimal pegOffsetValue,
      @JsonProperty("pegPriceType") String pegPriceType,
      @JsonProperty("ordType") String ordType,
      @JsonProperty("timeInForce") String timeInForce,
      @JsonProperty("execInst") String execInst,
      @JsonProperty("contingencyType") String contingencyType,
      @JsonProperty("exDestination") String exDestination,
      @JsonProperty("triggered") String triggered,
      @JsonProperty("workingIndicator") boolean workingIndicator,
      @JsonProperty("ordRejReason") String ordRejReason,
      @JsonProperty("simpleLeavesQty") BigDecimal simpleLeavesQty,
      @JsonProperty("leavesQty") BigDecimal leavesQty,
      @JsonProperty("simpleCumQty") BigDecimal simpleCumQty,
      @JsonProperty("cumQty") BigDecimal cumQty,
      @JsonProperty("avgPx") BigDecimal avgPx,
      @JsonProperty("multiLegReportingType") String multiLegReportingType,
      @JsonProperty("text") String text,
      @JsonProperty("transactTime") Date transactTime,
      @JsonProperty("error") String error) {

    this.symbol = symbol;
    this.id = id;
    this.side = side;
    this.size = size;
    this.price = price;
    this.clOrdID = clOrdID;
    this.timestamp = timestamp;
    this.orderStatus = orderStatus;
    this.currency = currency;
    this.settleCurrency = settleCurrency;

    this.clOrdLinkID = clOrdLinkID;
    this.simpleOrderQty = simpleOrderQty;
    this.displayQty = displayQty;
    this.stopPx = stopPx;
    this.pegOffsetValue = pegOffsetValue;
    this.pegPriceType = pegPriceType;
    this.ordType = ordType;
    this.timeInForce = timeInForce;
    this.execInst = execInst;
    this.contingencyType = contingencyType;
    this.exDestination = exDestination;
    this.triggered = triggered;
    this.workingIndicator = workingIndicator;
    this.ordRejReason = ordRejReason;
    this.simpleLeavesQty = simpleLeavesQty;
    this.leavesQty = leavesQty;
    this.simpleCumQty = simpleCumQty;
    this.cumQty = cumQty;
    this.avgPx = avgPx;
    this.multiLegReportingType = multiLegReportingType;
    this.text = text;
    this.transactTime = transactTime;
    this.error = error;
  }

  public BigDecimal getPrice() {

    return price;
  }

  public BigDecimal getVolume() {

    return size;
  }

  public BitmexSide getSide() {

    return side;
  }

  public String getId() {

    return id;
  }

  public String getSymbol() {

    return symbol;
  }

  public Date getTimestamp() {

    return timestamp;
  }

  public OrderStatus getOrderStatus() {

    return orderStatus;
  }

  public String getCurrency() {
    return currency;
  }

  public String getSettleCurrency() {
    return settleCurrency;
  }

  public String getClOrdID() {
    return clOrdID;
  }

  public String getClOrdLinkID() {
    return clOrdLinkID;
  }

  public BigDecimal getSimpleOrderQty() {
    return simpleOrderQty;
  }

  public BigDecimal getDisplayQty() {
    return displayQty;
  }

  public BigDecimal getStopPx() {
    return stopPx;
  }

  public BigDecimal getPegOffsetValue() {
    return pegOffsetValue;
  }

  public String getPegPriceType() {
    return pegPriceType;
  }

  public String getOrdType() {
    return ordType;
  }

  public String getTimeInForce() {
    return timeInForce;
  }

  public String getExecInst() {
    return execInst;
  }

  public String getContingencyType() {
    return contingencyType;
  }

  public String getExDestination() {
    return exDestination;
  }

  public String getTriggered() {
    return triggered;
  }

  public boolean isWorkingIndicator() {
    return workingIndicator;
  }

  public String getOrdRejReason() {
    return ordRejReason;
  }

  public BigDecimal getSimpleLeavesQty() {
    return simpleLeavesQty;
  }

  public BigDecimal getLeavesQty() {
    return leavesQty;
  }

  public BigDecimal getSimpleCumQty() {
    return simpleCumQty;
  }

  public BigDecimal getCumQty() {
    return cumQty;
  }

  public BigDecimal getAvgPx() {
    return avgPx;
  }

  public String getMultiLegReportingType() {
    return multiLegReportingType;
  }

  public String getText() {
    return text;
  }

  public Date getTransactTime() {
    return transactTime;
  }

  public String getError() {
    return error;
  }

  @Override
  public String toString() {
    return "BitmexPrivateOrder{"
        + "price="
        + price
        + ", size="
        + size
        + ", symbol='"
        + symbol
        + '\''
        + ", id='"
        + id
        + '\''
        + ", side="
        + side
        + ", timestamp="
        + timestamp
        + ", orderStatus="
        + orderStatus
        + ", currency='"
        + currency
        + '\''
        + ", settleCurrency='"
        + settleCurrency
        + '\''
        + ", clOrdID='"
        + clOrdID
        + '\''
        + ", clOrdLinkID='"
        + clOrdLinkID
        + '\''
        + ", simpleOrderQty="
        + simpleOrderQty
        + ", displayQty="
        + displayQty
        + ", stopPx="
        + stopPx
        + ", pegOffsetValue='"
        + pegOffsetValue
        + '\''
        + ", pegPriceType='"
        + pegPriceType
        + '\''
        + ", ordType='"
        + ordType
        + '\''
        + ", timeInForce='"
        + timeInForce
        + '\''
        + ", execInst='"
        + execInst
        + '\''
        + ", contingencyType='"
        + contingencyType
        + '\''
        + ", exDestination='"
        + exDestination
        + '\''
        + ", triggered='"
        + triggered
        + '\''
        + ", workingIndicator="
        + workingIndicator
        + ", ordRejReason='"
        + ordRejReason
        + '\''
        + ", simpleLeavesQty="
        + simpleLeavesQty
        + ", leavesQty="
        + leavesQty
        + ", simpleCumQty="
        + simpleCumQty
        + ", cumQty="
        + cumQty
        + ", avgPx="
        + avgPx
        + ", multiLegReportingType='"
        + multiLegReportingType
        + '\''
        + ", text='"
        + text
        + '\''
        + ", transactTime='"
        + transactTime
        + '\''
        + ", error='"
        + error
        + '\''
        + '}';
  }

  public enum OrderStatus {
    New,
    PartiallyFilled,
    Filled,
    Canceled,
    Rejected,
    Replaced
  }
}
