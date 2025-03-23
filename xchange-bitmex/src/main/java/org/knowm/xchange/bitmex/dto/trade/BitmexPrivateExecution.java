package org.knowm.xchange.bitmex.dto.trade;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import java.math.BigDecimal;
import java.time.ZonedDateTime;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import org.knowm.xchange.bitmex.BitmexAdapters;
import org.knowm.xchange.currency.Currency;
import org.knowm.xchange.instrument.Instrument;

@Data
@Builder
@AllArgsConstructor
public class BitmexPrivateExecution {

  @JsonProperty("execID")
  private String executionId;

  @JsonProperty("orderID")
  private String orderId;

  @JsonProperty("clOrdID")
  private String clientOid;

  @JsonProperty("clOrdLinkID")
  private String clOrdLinkID;

  @JsonProperty("account")
  private long account;

  private Instrument instrument;
  private BigDecimal executedQuantity;
  private BigDecimal orderQuantity;

  @JsonProperty("side")
  private String side;

  @JsonProperty("lastPx")
  private BigDecimal price;

  @JsonProperty("underlyingLastPx")
  private BigDecimal underlyingLastPx;

  @JsonProperty("lastMkt")
  private String lastMkt;

  @JsonProperty("lastLiquidityInd")
  private String lastLiquidityInd;

  @JsonProperty("simpleOrderQty")
  private BigDecimal simpleOrderQty;

  @JsonProperty("displayQty")
  private BigDecimal displayQty;

  @JsonProperty("stopPx")
  private BigDecimal stopPx;

  @JsonProperty("pegOffsetValue")
  private BigDecimal pegOffsetValue;

  @JsonProperty("pegPriceType")
  private String pegPriceType;

  private Currency feeCurrency;

  @JsonProperty("settlCurrency")
  private String settlCurrency;

  @JsonProperty("execType")
  private String execType;

  @JsonProperty("ordType")
  private String ordType;

  @JsonProperty("timeInForce")
  private String timeInForce;

  @JsonProperty("execInst")
  private String execInst;

  @JsonProperty("contingencyType")
  private String contingencyType;

  @JsonProperty("exDestination")
  private String exDestination;

  @JsonProperty("ordStatus")
  private String ordStatus;

  @JsonProperty("triggered")
  private String triggered;

  @JsonProperty("workingIndicator")
  private boolean workingIndicator;

  @JsonProperty("ordRejReason")
  private String ordRejReason;

  @JsonProperty("simpleLeavesQty")
  private BigDecimal simpleLeavesQty;

  @JsonProperty("leavesQty")
  private BigDecimal leavesQty;

  @JsonProperty("simpleCumQty")
  private BigDecimal simpleCumQty;

  @JsonProperty("cumQty")
  private BigDecimal cumQty;

  @JsonProperty("avgPx")
  private BigDecimal avgPx;

  @JsonProperty("commission")
  private BigDecimal commission;

  @JsonProperty("tradePublishIndicator")
  private String tradePublishIndicator;

  @JsonProperty("multiLegReportingType")
  private String multiLegReportingType;

  @JsonProperty("text")
  private String text;

  @JsonProperty("trdMatchID")
  private String trdMatchID;

  @JsonProperty("execCost")
  private BigDecimal execCost;

  private BigDecimal feeAmount;

  @JsonProperty("homeNotional")
  private BigDecimal homeNotional;

  @JsonProperty("foreignNotional")
  private BigDecimal foreignNotional;

  @JsonProperty("transactTime")
  private ZonedDateTime createdAt;

  @JsonProperty("timestamp")
  private ZonedDateTime updatedAt;

  @JsonCreator
  public BitmexPrivateExecution(
      @JsonProperty("symbol") String symbol,
      @JsonProperty("lastQty") BigDecimal executedQuantity,
      @JsonProperty("orderQty") BigDecimal orderQuantity,
      @JsonProperty("currency") String feeCurrency,
      @JsonProperty("execComm") BigDecimal feeAmount) {
    // scale values
    this.instrument = BitmexAdapters.toInstrument(symbol);
    this.executedQuantity =
        BitmexAdapters.scaleToLocalAmount(executedQuantity, instrument.getBase());
    this.orderQuantity = BitmexAdapters.scaleToLocalAmount(orderQuantity, instrument.getBase());

    // fees are paid in quote currency
    this.feeCurrency = instrument.getCounter();
    this.feeAmount = BitmexAdapters.scaleToLocalAmount(feeAmount, this.feeCurrency);
  }
}
