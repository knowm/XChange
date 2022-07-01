package info.bitrich.xchangestream.bitmex.dto;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import java.math.BigDecimal;
import java.util.Date;
import org.knowm.xchange.bitmex.dto.marketdata.BitmexPrivateOrder;
import org.knowm.xchange.bitmex.dto.trade.BitmexSide;

/** @author Nikita Belenkiy on 05/06/2018. */
public class BitmexExecution {

  protected String execID;
  protected String orderID;
  protected String clOrdID;
  protected String clOrdLinkID;
  protected long account;
  protected String symbol;
  protected BitmexSide side;
  protected Long lastQty;
  protected BigDecimal lastPx;
  protected BigDecimal underlyingLastPx;
  protected String lastMkt;
  protected String lastLiquidityInd;
  protected BigDecimal simpleOrderQty;
  protected Long orderQty;
  protected BigDecimal price;
  protected Long displayQty;
  protected BigDecimal stopPx;
  protected BigDecimal pegOffsetValue;
  protected String pegPriceType;
  protected String currency;
  protected String settlCurrency;
  protected String execType;
  protected String ordType;
  protected String timeInForce;
  protected String execInst;
  protected String contingencyType;
  protected String exDestination;
  protected BitmexPrivateOrder.OrderStatus ordStatus;
  protected String triggered;
  protected Boolean workingIndicator;
  protected String ordRejReason;
  protected BigDecimal simpleLeavesQty;
  protected Long leavesQty;
  protected BigDecimal simpleCumQty;
  protected BigDecimal cumQty;
  protected BigDecimal avgPx;
  protected BigDecimal commission;
  protected String tradePublishIndicator;
  protected String multiLegReportingType;
  protected String text;
  protected String trdMatchID;
  protected Long execCost;
  protected Long execComm;
  protected BigDecimal homeNotional;
  protected BigDecimal foreignNotional;
  protected Date transactTime;
  protected Date timestamp;

  @JsonCreator
  public BitmexExecution(
      @JsonProperty("execID") String execID,
      @JsonProperty("orderID") String orderID,
      @JsonProperty("clOrdID") String clOrdID,
      @JsonProperty("clOrdLinkID") String clOrdLinkID,
      @JsonProperty("account") int account,
      @JsonProperty("symbol") String symbol,
      @JsonProperty("side") BitmexSide side,
      @JsonProperty("lastQty") Long lastQty,
      @JsonProperty("lastPx") BigDecimal lastPx,
      @JsonProperty("underlyingLastPx") BigDecimal underlyingLastPx,
      @JsonProperty("lastMkt") String lastMkt,
      @JsonProperty("lastLiquidityInd") String lastLiquidityInd,
      @JsonProperty("simpleOrderQty") BigDecimal simpleOrderQty,
      @JsonProperty("orderQty") long orderQty,
      @JsonProperty("price") BigDecimal price,
      @JsonProperty("displayQty") Long displayQty,
      @JsonProperty("stopPx") BigDecimal stopPx,
      @JsonProperty("pegOffsetValue") BigDecimal pegOffsetValue,
      @JsonProperty("pegPriceType") String pegPriceType,
      @JsonProperty("currency") String currency,
      @JsonProperty("settlCurrency") String settlCurrency,
      @JsonProperty("execType") String execType,
      @JsonProperty("ordType") String ordType,
      @JsonProperty("timeInForce") String timeInForce,
      @JsonProperty("execInst") String execInst,
      @JsonProperty("contingencyType") String contingencyType,
      @JsonProperty("exDestination") String exDestination,
      @JsonProperty("ordStatus") BitmexPrivateOrder.OrderStatus ordStatus,
      @JsonProperty("triggered") String triggered,
      @JsonProperty("workingIndicator") boolean workingIndicator,
      @JsonProperty("ordRejReason") String ordRejReason,
      @JsonProperty("simpleLeavesQty") BigDecimal simpleLeavesQty,
      @JsonProperty("leavesQty") Long leavesQty,
      @JsonProperty("simpleCumQty") BigDecimal simpleCumQty,
      @JsonProperty("cumQty") BigDecimal cumQty,
      @JsonProperty("avgPx") BigDecimal avgPx,
      @JsonProperty("commission") BigDecimal commission,
      @JsonProperty("tradePublishIndicator") String tradePublishIndicator,
      @JsonProperty("multiLegReportingType") String multiLegReportingType,
      @JsonProperty("text") String text,
      @JsonProperty("trdMatchID") String trdMatchID,
      @JsonProperty("execCost") Long execCost,
      @JsonProperty("execComm") Long execComm,
      @JsonProperty("homeNotional") BigDecimal homeNotional,
      @JsonProperty("foreignNotional") BigDecimal foreignNotional,
      @JsonProperty("transactTime") Date transactTime,
      @JsonProperty("timestamp") Date timestamp) {
    this.execID = execID;
    this.orderID = orderID;
    this.clOrdID = clOrdID;
    this.clOrdLinkID = clOrdLinkID;
    this.account = account;
    this.symbol = symbol;
    this.side = side;
    this.lastQty = lastQty;
    this.lastPx = lastPx;
    this.underlyingLastPx = underlyingLastPx;
    this.lastMkt = lastMkt;
    this.lastLiquidityInd = lastLiquidityInd;
    this.simpleOrderQty = simpleOrderQty;
    this.orderQty = orderQty;
    this.price = price;
    this.displayQty = displayQty;
    this.stopPx = stopPx;
    this.pegOffsetValue = pegOffsetValue;
    this.pegPriceType = pegPriceType;
    this.currency = currency;
    this.settlCurrency = settlCurrency;
    this.execType = execType;
    this.ordType = ordType;
    this.timeInForce = timeInForce;
    this.execInst = execInst;
    this.contingencyType = contingencyType;
    this.exDestination = exDestination;
    this.ordStatus = ordStatus;
    this.triggered = triggered;
    this.workingIndicator = workingIndicator;
    this.ordRejReason = ordRejReason;
    this.simpleLeavesQty = simpleLeavesQty;
    this.leavesQty = leavesQty;
    this.simpleCumQty = simpleCumQty;
    this.cumQty = cumQty;
    this.avgPx = avgPx;
    this.commission = commission;
    this.tradePublishIndicator = tradePublishIndicator;
    this.multiLegReportingType = multiLegReportingType;
    this.text = text;
    this.trdMatchID = trdMatchID;
    this.execCost = execCost;
    this.execComm = execComm;
    this.homeNotional = homeNotional;
    this.foreignNotional = foreignNotional;
    this.transactTime = transactTime;
    this.timestamp = timestamp;
  }

  public String getExecID() {
    return execID;
  }

  public String getOrderID() {
    return orderID;
  }

  public String getClOrdID() {
    return clOrdID;
  }

  public String getClOrdLinkID() {
    return clOrdLinkID;
  }

  public long getAccount() {
    return account;
  }

  public String getSymbol() {
    return symbol;
  }

  public BitmexSide getSide() {
    return side;
  }

  public Long getLastQty() {
    return lastQty;
  }

  public BigDecimal getLastPx() {
    return lastPx;
  }

  public BigDecimal getUnderlyingLastPx() {
    return underlyingLastPx;
  }

  public String getLastMkt() {
    return lastMkt;
  }

  public String getLastLiquidityInd() {
    return lastLiquidityInd;
  }

  public BigDecimal getSimpleOrderQty() {
    return simpleOrderQty;
  }

  public Long getOrderQty() {
    return orderQty;
  }

  public BigDecimal getPrice() {
    return price;
  }

  public Long getDisplayQty() {
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

  public String getCurrency() {
    return currency;
  }

  public String getSettlCurrency() {
    return settlCurrency;
  }

  public String getExecType() {
    return execType;
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

  public BitmexPrivateOrder.OrderStatus getOrdStatus() {
    return ordStatus;
  }

  public String getTriggered() {
    return triggered;
  }

  public Boolean isWorkingIndicator() {
    return workingIndicator;
  }

  public String getOrdRejReason() {
    return ordRejReason;
  }

  public BigDecimal getSimpleLeavesQty() {
    return simpleLeavesQty;
  }

  public Long getLeavesQty() {
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

  public BigDecimal getCommission() {
    return commission;
  }

  public String getTradePublishIndicator() {
    return tradePublishIndicator;
  }

  public String getMultiLegReportingType() {
    return multiLegReportingType;
  }

  public String getText() {
    return text;
  }

  public String getTrdMatchID() {
    return trdMatchID;
  }

  public Long getExecCost() {
    return execCost;
  }

  public Long getExecComm() {
    return execComm;
  }

  public BigDecimal getHomeNotional() {
    return homeNotional;
  }

  public BigDecimal getForeignNotional() {
    return foreignNotional;
  }

  public Date getTransactTime() {
    return transactTime;
  }

  public Date getTimestamp() {
    return timestamp;
  }

  @Override
  public String toString() {
    return "BitmexExecution{"
        + "execID='"
        + execID
        + '\''
        + ", orderID='"
        + orderID
        + '\''
        + ", clOrdID='"
        + clOrdID
        + '\''
        + ", clOrdLinkID='"
        + clOrdLinkID
        + '\''
        + ", account="
        + account
        + ", symbol='"
        + symbol
        + '\''
        + ", side="
        + side
        + ", lastQty="
        + lastQty
        + ", lastPx="
        + lastPx
        + ", underlyingLastPx="
        + underlyingLastPx
        + ", lastMkt='"
        + lastMkt
        + '\''
        + ", lastLiquidityInd='"
        + lastLiquidityInd
        + '\''
        + ", simpleOrderQty="
        + simpleOrderQty
        + ", orderQty="
        + orderQty
        + ", price="
        + price
        + ", displayQty="
        + displayQty
        + ", stopPx="
        + stopPx
        + ", pegOffsetValue="
        + pegOffsetValue
        + ", pegPriceType='"
        + pegPriceType
        + '\''
        + ", currency='"
        + currency
        + '\''
        + ", settlCurrency='"
        + settlCurrency
        + '\''
        + ", execType='"
        + execType
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
        + ", ordStatus='"
        + ordStatus
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
        + ", commission="
        + commission
        + ", tradePublishIndicator='"
        + tradePublishIndicator
        + '\''
        + ", multiLegReportingType='"
        + multiLegReportingType
        + '\''
        + ", text='"
        + text
        + '\''
        + ", trdMatchID='"
        + trdMatchID
        + '\''
        + ", execCost="
        + execCost
        + ", execComm="
        + execComm
        + ", homeNotional="
        + homeNotional
        + ", foreignNotional="
        + foreignNotional
        + ", transactTime="
        + transactTime
        + ", timestamp="
        + timestamp
        + '}';
  }
}
