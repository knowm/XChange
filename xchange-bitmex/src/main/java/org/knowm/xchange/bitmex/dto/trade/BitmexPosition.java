package org.knowm.xchange.bitmex.dto.trade;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import java.math.BigDecimal;
import java.time.ZonedDateTime;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import org.knowm.xchange.bitmex.AbstractHttpResponseAware;
import org.knowm.xchange.bitmex.BitmexAdapters;
import org.knowm.xchange.bitmex.config.converter.StringToCurrencyConverter;
import org.knowm.xchange.currency.Currency;
import org.knowm.xchange.instrument.Instrument;

@Data
@Builder
@AllArgsConstructor
public class BitmexPosition extends AbstractHttpResponseAware {

  @JsonProperty("account")
  private Integer account;

  private Instrument instrument;

  @JsonProperty("currency")
  @JsonDeserialize(converter = StringToCurrencyConverter.class)
  private Currency marginCurrency;

  @JsonProperty("underlying")
  private String underlying;

  @JsonProperty("quoteCurrency")
  @JsonDeserialize(converter = StringToCurrencyConverter.class)
  private Currency quoteCurrency;

  @JsonProperty("commission")
  private BigDecimal commission;

  @JsonProperty("initMarginReq")
  private BigDecimal initMarginReq;

  @JsonProperty("maintMarginReq")
  private BigDecimal maintMarginReq;

  @JsonProperty("riskLimit")
  private BigDecimal riskLimit;

  @JsonProperty("leverage")
  private BigDecimal leverage;

  @JsonProperty("crossMargin")
  private Boolean crossMargin;

  @JsonProperty("deleveragePercentile")
  private BigDecimal deleveragePercentile;

  @JsonProperty("rebalancedPnl")
  private BigDecimal rebalancedPnl;

  @JsonProperty("prevRealisedPnl")
  private BigDecimal prevRealisedPnl;

  @JsonProperty("prevUnrealisedPnl")
  private BigDecimal prevUnrealisedPnl;

  @JsonProperty("prevClosePrice")
  private BigDecimal prevClosePrice;

  @JsonProperty("openingTimestamp")
  private String openingTimestamp;

  @JsonProperty("openingQty")
  private BigDecimal openingQty;

  @JsonProperty("openingCost")
  private BigDecimal openingCost;

  @JsonProperty("openingComm")
  private BigDecimal openingComm;

  private BigDecimal openOrderBuyQty;

  private BigDecimal openOrderBuyCost;

  @JsonProperty("openOrderBuyPremium")
  private BigDecimal openOrderBuyPremium;

  private BigDecimal openOrderSellQty;

  private BigDecimal openOrderSellCost;

  @JsonProperty("openOrderSellPremium")
  private BigDecimal openOrderSellPremium;

  @JsonProperty("execBuyQty")
  private BigDecimal execBuyQty;

  @JsonProperty("execBuyCost")
  private BigDecimal execBuyCost;

  @JsonProperty("execSellQty")
  private BigDecimal execSellQty;

  @JsonProperty("execSellCost")
  private BigDecimal execSellCost;

  @JsonProperty("execQty")
  private BigDecimal execQty;

  @JsonProperty("execCost")
  private BigDecimal execCost;

  @JsonProperty("execComm")
  private BigDecimal execComm;

  @JsonProperty("currentTimestamp")
  private String currentTimestamp;

  @JsonProperty("currentQty")
  private BigDecimal currentQty;

  @JsonProperty("currentCost")
  private BigDecimal currentCost;

  @JsonProperty("currentComm")
  private BigDecimal currentComm;

  @JsonProperty("realisedCost")
  private BigDecimal realisedCost;

  @JsonProperty("unrealisedCost")
  private BigDecimal unrealisedCost;

  @JsonProperty("grossOpenCost")
  private BigDecimal grossOpenCost;

  @JsonProperty("grossOpenPremium")
  private BigDecimal grossOpenPremium;

  @JsonProperty("grossExecCost")
  private BigDecimal grossExecCost;

  @JsonProperty("isOpen")
  private Boolean isOpen;

  @JsonProperty("markPrice")
  private BigDecimal markPrice;

  @JsonProperty("markValue")
  private BigDecimal markValue;

  @JsonProperty("riskValue")
  private BigDecimal riskValue;

  @JsonProperty("homeNotional")
  private BigDecimal homeNotional;

  @JsonProperty("foreignNotional")
  private BigDecimal foreignNotional;

  @JsonProperty("posState")
  private String posState;

  @JsonProperty("posCost")
  private BigDecimal posCost;

  @JsonProperty("posCost2")
  private BigDecimal posCost2;

  @JsonProperty("posCross")
  private BigDecimal posCross;

  @JsonProperty("posInit")
  private BigDecimal posInit;

  @JsonProperty("posComm")
  private BigDecimal posComm;

  @JsonProperty("posLoss")
  private BigDecimal posLoss;

  @JsonProperty("posMargin")
  private BigDecimal posMargin;

  @JsonProperty("posMaint")
  private BigDecimal posMaint;

  @JsonProperty("posAllowance")
  private BigDecimal posAllowance;

  @JsonProperty("taxableMargin")
  private BigDecimal taxableMargin;

  @JsonProperty("initMargin")
  private BigDecimal initMargin;

  @JsonProperty("maintMargin")
  private BigDecimal maintMargin;

  @JsonProperty("sessionMargin")
  private BigDecimal sessionMargin;

  @JsonProperty("targetExcessMargin")
  private BigDecimal targetExcessMargin;

  @JsonProperty("varMargin")
  private BigDecimal varMargin;

  @JsonProperty("realisedGrossPnl")
  private BigDecimal realisedGrossPnl;

  @JsonProperty("realisedTax")
  private BigDecimal realisedTax;

  @JsonProperty("realisedPnl")
  private BigDecimal realisedPnl;

  @JsonProperty("unrealisedGrossPnl")
  private BigDecimal unrealisedGrossPnl;

  @JsonProperty("longBankrupt")
  private BigDecimal longBankrupt;

  @JsonProperty("shortBankrupt")
  private BigDecimal shortBankrupt;

  @JsonProperty("taxBase")
  private BigDecimal taxBase;

  @JsonProperty("indicativeTaxRate")
  private BigDecimal indicativeTaxRate;

  @JsonProperty("indicativeTax")
  private BigDecimal indicativeTax;

  @JsonProperty("unrealisedTax")
  private BigDecimal unrealisedTax;

  @JsonProperty("unrealisedPnl")
  private BigDecimal unrealisedPnl;

  @JsonProperty("unrealisedPnlPcnt")
  private BigDecimal unrealisedPnlPcnt;

  @JsonProperty("unrealisedRoePcnt")
  private BigDecimal unrealisedRoePcnt;

  @JsonProperty("simpleQty")
  private BigDecimal simpleQty;

  @JsonProperty("simpleCost")
  private BigDecimal simpleCost;

  @JsonProperty("simpleValue")
  private BigDecimal simpleValue;

  @JsonProperty("simplePnl")
  private BigDecimal simplePnl;

  @JsonProperty("simplePnlPcnt")
  private BigDecimal simplePnlPcnt;

  @JsonProperty("avgCostPrice")
  private BigDecimal avgCostPrice;

  @JsonProperty("avgEntryPrice")
  private BigDecimal avgEntryPrice;

  @JsonProperty("breakEvenPrice")
  private BigDecimal breakEvenPrice;

  @JsonProperty("marginCallPrice")
  private BigDecimal marginCallPrice;

  @JsonProperty("liquidationPrice")
  private BigDecimal liquidationPrice;

  @JsonProperty("bankruptPrice")
  private BigDecimal bankruptPrice;

  @JsonProperty("timestamp")
  private ZonedDateTime timestamp;

  @JsonProperty("lastPrice")
  private BigDecimal lastPrice;

  @JsonProperty("lastValue")
  private BigDecimal lastValue;

  @JsonCreator
  public BitmexPosition(@JsonProperty("symbol") String symbol,
      @JsonProperty("openOrderBuyQty") BigDecimal openOrderBuyQty,
      @JsonProperty("openOrderBuyCost") BigDecimal openOrderBuyCost,
      @JsonProperty("openOrderSellQty") BigDecimal openOrderSellQty,
      @JsonProperty("openOrderSellCost") BigDecimal openOrderSellCost
  ) {
    // scale values
    this.instrument = BitmexAdapters.toInstrument(symbol);

    this.openOrderBuyQty = BitmexAdapters.scaleToLocalAmount(openOrderBuyQty, instrument.getCounter());
    this.openOrderBuyCost = BitmexAdapters.scaleToLocalAmount(openOrderBuyCost, instrument.getCounter());
    this.openOrderSellQty = BitmexAdapters.scaleToLocalAmount(openOrderSellQty, instrument.getCounter());
    this.openOrderSellCost = BitmexAdapters.scaleToLocalAmount(openOrderSellCost, instrument.getCounter());
  }

}
