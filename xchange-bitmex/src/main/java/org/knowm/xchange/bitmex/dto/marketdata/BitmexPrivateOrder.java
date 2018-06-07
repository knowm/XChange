package org.knowm.xchange.bitmex.dto.marketdata;

import com.fasterxml.jackson.annotation.JsonProperty;
import java.math.BigDecimal;
import java.util.Date;
import org.knowm.xchange.bitmex.dto.trade.BitmexSide;

/** see field description at http://www.onixs.biz/fix-dictionary/5.0.SP2/fields_by_name.html */
public class BitmexPrivateOrder {

  private final BigDecimal price;
  private final BigDecimal size;
  private final String symbol;
  private final String id;
  private final BitmexSide side;
  private final Date timestamp;
  private final OrderStatus orderStatus;
  private final String currency;
  private final String settleCurrency;

  private String clOrdID;
  private String clOrdLinkID;
  private String account;
  private BigDecimal simpleOrderQty;
  private BigDecimal displayQty;
  private BigDecimal stopPx;
  private String pegOffsetValue;
  private String pegPriceType;
  private String ordType;
  private String timeInForce;
  private String execInst;
  private String contingencyType;
  private String exDestination;
  private String triggered;
  private Boolean workingIndicator;
  private String ordRejReason;
  private BigDecimal simpleLeavesQty;
  private BigDecimal leavesQty;
  private BigDecimal simpleCumQty;
  private BigDecimal cumQty;
  private BigDecimal avgPx;
  private String multiLegReportingType;
  private String text;
  private String transactTime;

  /**
   * orderID (string), clOrdID (string, optional), clOrdLinkID (string, optional), account (number,
   * optional), symbol (string, optional), side (string, optional), simpleOrderQty (number,
   * optional), orderQty (number, optional), price (number, optional), displayQty (number,
   * optional), stopPx (number, optional), pegOffsetValue (number, optional), pegPriceType (string,
   * optional), currency (string, optional), settlCurrency (string, optional), ordType (string,
   * optional), timeInForce (string, optional), execInst (string, optional), contingencyType
   * (string, optional), exDestination (string, optional), ordStatus (string, optional), triggered
   * (string, optional), workingIndicator (boolean, optional), ordRejReason (string, optional),
   * simpleLeavesQty (number, optional), leavesQty (number, optional), simpleCumQty (number,
   * optional), cumQty (number, optional), avgPx (number, optional), multiLegReportingType (string,
   * optional), text (string, optional), transactTime (string, optional), timestamp (string,
   * optional)
   *
   * @param price
   * @param id
   * @param size
   * @param side
   * @param symbol
   * @param timestamp
   * @param orderStatus
   * @param currency
   * @param settleCurrency
   */
  public BitmexPrivateOrder(
      @JsonProperty("price") BigDecimal price,
      @JsonProperty("orderID") String id,
      @JsonProperty("orderQty") BigDecimal size,
      @JsonProperty("side") BitmexSide side,
      @JsonProperty("symbol") String symbol,
      @JsonProperty("timestamp") Date timestamp,
      @JsonProperty("ordStatus") OrderStatus orderStatus,
      @JsonProperty("currency") String currency,
      @JsonProperty("settlCurrency") String settleCurrency,
      // @JsonProperty ("orderID")orderID String; (string),
      @JsonProperty("clOrdID") String clOrdID, // (string, optional),
      @JsonProperty("clOrdLinkID") String clOrdLinkID, // (string, optional),
      @JsonProperty("account") String account, // (number, optional),
      // @JsonProperty("symbol") String symbol ,// (string, optional),
      // @JsonProperty("side") String side ,// (string, optional),
      @JsonProperty("simpleOrderQty") BigDecimal simpleOrderQty, // (number, optional),
      // @JsonProperty("orderQty") BigDecimal orderQty,// (number, optional),
      // @JsonProperty("price") String price ,// (number, optional),
      @JsonProperty("displayQty") BigDecimal displayQty, // (number, optional),
      @JsonProperty("stopPx") BigDecimal stopPx, // (number, optional),
      @JsonProperty("pegOffsetValue") String pegOffsetValue, // (number, optional),
      @JsonProperty("pegPriceType") String pegPriceType, // (string, optional),
      // @JsonProperty("currency") String currency ,// (string, optional),
      // @JsonProperty("settlCurrency") String settlCurrency,// (string, optional),
      @JsonProperty("ordType") String ordType, // (string, optional),
      @JsonProperty("timeInForce") String timeInForce, // (string, optional),
      @JsonProperty("execInst") String execInst, // (string, optional),
      @JsonProperty("contingencyType") String contingencyType, // (string, optional),
      @JsonProperty("exDestination") String exDestination, // (string, optional),
      // @JsonProperty("ordStatus") String ordStatus,// (string, optional),
      @JsonProperty("triggered") String triggered, // (string, optional),
      @JsonProperty("workingIndicator") Boolean workingIndicator, // (boolean, optional),
      @JsonProperty("ordRejReason") String ordRejReason, // (string, optional),
      @JsonProperty("simpleLeavesQty") BigDecimal simpleLeavesQty, // (number, optional),
      @JsonProperty("leavesQty") BigDecimal leavesQty, // (number, optional),
      @JsonProperty("simpleCumQty") BigDecimal simpleCumQty, // (number, optional),
      @JsonProperty("cumQty") BigDecimal cumQty, // (number, optional),
      @JsonProperty("avgPx") BigDecimal avgPx, // (number, optional),
      @JsonProperty("multiLegReportingType") String multiLegReportingType, // (string, optional),
      @JsonProperty("text") String text, // (string, optional),
      @JsonProperty("transactTime") String transactTime // (string, optional),
      // @JsonProperty("timestamp") String timestamp ,// (string, optional)
      ) {

    this.symbol = symbol;
    this.id = id;
    this.side = side;
    this.size = size;
    this.price = price;
    this.timestamp = timestamp;
    this.orderStatus = orderStatus;
    this.currency = currency;
    this.settleCurrency = settleCurrency;

    this.clOrdID = clOrdID;
    this.clOrdLinkID = clOrdLinkID;
    this.account = account;
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

  public BigDecimal getSize() {
    return size;
  }

  public String getClOrdID() {
    return clOrdID;
  }

  public String getClOrdLinkID() {
    return clOrdLinkID;
  }

  public String getAccount() {
    return account;
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

  public String getPegOffsetValue() {
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

  public Boolean getWorkingIndicator() {
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

  public String getTransactTime() {
    return transactTime;
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
        + ", account='"
        + account
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
        + '}';
  }

  public enum OrderStatus {
    New,
    Partially_filled,
    Filled,
    Canceled
  }
}
