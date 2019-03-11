package org.knowm.xchange.bitmex.dto.trade;

import com.fasterxml.jackson.annotation.JsonProperty;
import java.math.BigDecimal;
import org.knowm.xchange.bitmex.AbstractHttpResponseAware;

public class BitmexPosition extends AbstractHttpResponseAware {
  @JsonProperty("account")
  private Integer account;

  @JsonProperty("symbol")
  private String symbol;

  @JsonProperty("currency")
  private String currency;

  @JsonProperty("underlying")
  private String underlying;

  @JsonProperty("quoteCurrency")
  private String quoteCurrency;

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

  @JsonProperty("openOrderBuyQty")
  private BigDecimal openOrderBuyQty;

  @JsonProperty("openOrderBuyCost")
  private BigDecimal openOrderBuyCost;

  @JsonProperty("openOrderBuyPremium")
  private BigDecimal openOrderBuyPremium;

  @JsonProperty("openOrderSellQty")
  private BigDecimal openOrderSellQty;

  @JsonProperty("openOrderSellCost")
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
  private String timestamp;

  @JsonProperty("lastPrice")
  private BigDecimal lastPrice;

  @JsonProperty("lastValue")
  private BigDecimal lastValue;

  public Integer getAccount() {
    return account;
  }

  public String getSymbol() {
    return symbol;
  }

  public String getCurrency() {
    return currency;
  }

  public String getUnderlying() {
    return underlying;
  }

  public String getQuoteCurrency() {
    return quoteCurrency;
  }

  public BigDecimal getCommission() {
    return commission;
  }

  public BigDecimal getInitMarginReq() {
    return initMarginReq;
  }

  public BigDecimal getMaintMarginReq() {
    return maintMarginReq;
  }

  public BigDecimal getRiskLimit() {
    return riskLimit;
  }

  public BigDecimal getLeverage() {
    return leverage;
  }

  public Boolean getCrossMargin() {
    return crossMargin;
  }

  public BigDecimal getDeleveragePercentile() {
    return deleveragePercentile;
  }

  public BigDecimal getRebalancedPnl() {
    return rebalancedPnl;
  }

  public BigDecimal getPrevRealisedPnl() {
    return prevRealisedPnl;
  }

  public BigDecimal getPrevUnrealisedPnl() {
    return prevUnrealisedPnl;
  }

  public BigDecimal getPrevClosePrice() {
    return prevClosePrice;
  }

  public String getOpeningTimestamp() {
    return openingTimestamp;
  }

  public BigDecimal getOpeningQty() {
    return openingQty;
  }

  public BigDecimal getOpeningCost() {
    return openingCost;
  }

  public BigDecimal getOpeningComm() {
    return openingComm;
  }

  public BigDecimal getOpenOrderBuyQty() {
    return openOrderBuyQty;
  }

  public BigDecimal getOpenOrderBuyCost() {
    return openOrderBuyCost;
  }

  public BigDecimal getOpenOrderBuyPremium() {
    return openOrderBuyPremium;
  }

  public BigDecimal getOpenOrderSellQty() {
    return openOrderSellQty;
  }

  public BigDecimal getOpenOrderSellCost() {
    return openOrderSellCost;
  }

  public BigDecimal getOpenOrderSellPremium() {
    return openOrderSellPremium;
  }

  public BigDecimal getExecBuyQty() {
    return execBuyQty;
  }

  public BigDecimal getExecBuyCost() {
    return execBuyCost;
  }

  public BigDecimal getExecSellQty() {
    return execSellQty;
  }

  public BigDecimal getExecSellCost() {
    return execSellCost;
  }

  public BigDecimal getExecQty() {
    return execQty;
  }

  public BigDecimal getExecCost() {
    return execCost;
  }

  public BigDecimal getExecComm() {
    return execComm;
  }

  public String getCurrentTimestamp() {
    return currentTimestamp;
  }

  public BigDecimal getCurrentQty() {
    return currentQty;
  }

  public BigDecimal getCurrentCost() {
    return currentCost;
  }

  public BigDecimal getCurrentComm() {
    return currentComm;
  }

  public BigDecimal getRealisedCost() {
    return realisedCost;
  }

  public BigDecimal getUnrealisedCost() {
    return unrealisedCost;
  }

  public BigDecimal getGrossOpenCost() {
    return grossOpenCost;
  }

  public BigDecimal getGrossOpenPremium() {
    return grossOpenPremium;
  }

  public BigDecimal getGrossExecCost() {
    return grossExecCost;
  }

  public Boolean getOpen() {
    return isOpen;
  }

  public BigDecimal getMarkPrice() {
    return markPrice;
  }

  public BigDecimal getMarkValue() {
    return markValue;
  }

  public BigDecimal getRiskValue() {
    return riskValue;
  }

  public BigDecimal getHomeNotional() {
    return homeNotional;
  }

  public BigDecimal getForeignNotional() {
    return foreignNotional;
  }

  public String getPosState() {
    return posState;
  }

  public BigDecimal getPosCost() {
    return posCost;
  }

  public BigDecimal getPosCost2() {
    return posCost2;
  }

  public BigDecimal getPosCross() {
    return posCross;
  }

  public BigDecimal getPosInit() {
    return posInit;
  }

  public BigDecimal getPosComm() {
    return posComm;
  }

  public BigDecimal getPosLoss() {
    return posLoss;
  }

  public BigDecimal getPosMargin() {
    return posMargin;
  }

  public BigDecimal getPosMaint() {
    return posMaint;
  }

  public BigDecimal getPosAllowance() {
    return posAllowance;
  }

  public BigDecimal getTaxableMargin() {
    return taxableMargin;
  }

  public BigDecimal getInitMargin() {
    return initMargin;
  }

  public BigDecimal getMaintMargin() {
    return maintMargin;
  }

  public BigDecimal getSessionMargin() {
    return sessionMargin;
  }

  public BigDecimal getTargetExcessMargin() {
    return targetExcessMargin;
  }

  public BigDecimal getVarMargin() {
    return varMargin;
  }

  public BigDecimal getRealisedGrossPnl() {
    return realisedGrossPnl;
  }

  public BigDecimal getRealisedTax() {
    return realisedTax;
  }

  public BigDecimal getRealisedPnl() {
    return realisedPnl;
  }

  public BigDecimal getUnrealisedGrossPnl() {
    return unrealisedGrossPnl;
  }

  public BigDecimal getLongBankrupt() {
    return longBankrupt;
  }

  public BigDecimal getShortBankrupt() {
    return shortBankrupt;
  }

  public BigDecimal getTaxBase() {
    return taxBase;
  }

  public BigDecimal getIndicativeTaxRate() {
    return indicativeTaxRate;
  }

  public BigDecimal getIndicativeTax() {
    return indicativeTax;
  }

  public BigDecimal getUnrealisedTax() {
    return unrealisedTax;
  }

  public BigDecimal getUnrealisedPnl() {
    return unrealisedPnl;
  }

  public BigDecimal getUnrealisedPnlPcnt() {
    return unrealisedPnlPcnt;
  }

  public BigDecimal getUnrealisedRoePcnt() {
    return unrealisedRoePcnt;
  }

  public BigDecimal getSimpleQty() {
    return simpleQty;
  }

  public BigDecimal getSimpleCost() {
    return simpleCost;
  }

  public BigDecimal getSimpleValue() {
    return simpleValue;
  }

  public BigDecimal getSimplePnl() {
    return simplePnl;
  }

  public BigDecimal getSimplePnlPcnt() {
    return simplePnlPcnt;
  }

  public BigDecimal getAvgCostPrice() {
    return avgCostPrice;
  }

  public BigDecimal getAvgEntryPrice() {
    return avgEntryPrice;
  }

  public BigDecimal getBreakEvenPrice() {
    return breakEvenPrice;
  }

  public BigDecimal getMarginCallPrice() {
    return marginCallPrice;
  }

  public BigDecimal getLiquidationPrice() {
    return liquidationPrice;
  }

  public BigDecimal getBankruptPrice() {
    return bankruptPrice;
  }

  public String getTimestamp() {
    return timestamp;
  }

  public BigDecimal getLastPrice() {
    return lastPrice;
  }

  public BigDecimal getLastValue() {
    return lastValue;
  }

  @Override
  public String toString() {
    return "BitmexPosition{"
        + "account="
        + account
        + ", symbol='"
        + symbol
        + '\''
        + ", currency='"
        + currency
        + '\''
        + ", underlying='"
        + underlying
        + '\''
        + ", quoteCurrency='"
        + quoteCurrency
        + '\''
        + ", commission="
        + commission
        + ", initMarginReq="
        + initMarginReq
        + ", maintMarginReq="
        + maintMarginReq
        + ", riskLimit="
        + riskLimit
        + ", leverage="
        + leverage
        + ", crossMargin="
        + crossMargin
        + ", deleveragePercentile="
        + deleveragePercentile
        + ", rebalancedPnl="
        + rebalancedPnl
        + ", prevRealisedPnl="
        + prevRealisedPnl
        + ", prevUnrealisedPnl="
        + prevUnrealisedPnl
        + ", prevClosePrice="
        + prevClosePrice
        + ", openingTimestamp='"
        + openingTimestamp
        + '\''
        + ", openingQty="
        + openingQty
        + ", openingCost="
        + openingCost
        + ", openingComm="
        + openingComm
        + ", openOrderBuyQty="
        + openOrderBuyQty
        + ", openOrderBuyCost="
        + openOrderBuyCost
        + ", openOrderBuyPremium="
        + openOrderBuyPremium
        + ", openOrderSellQty="
        + openOrderSellQty
        + ", openOrderSellCost="
        + openOrderSellCost
        + ", openOrderSellPremium="
        + openOrderSellPremium
        + ", execBuyQty="
        + execBuyQty
        + ", execBuyCost="
        + execBuyCost
        + ", execSellQty="
        + execSellQty
        + ", execSellCost="
        + execSellCost
        + ", execQty="
        + execQty
        + ", execCost="
        + execCost
        + ", execComm="
        + execComm
        + ", currentTimestamp='"
        + currentTimestamp
        + '\''
        + ", currentQty="
        + currentQty
        + ", currentCost="
        + currentCost
        + ", currentComm="
        + currentComm
        + ", realisedCost="
        + realisedCost
        + ", unrealisedCost="
        + unrealisedCost
        + ", grossOpenCost="
        + grossOpenCost
        + ", grossOpenPremium="
        + grossOpenPremium
        + ", grossExecCost="
        + grossExecCost
        + ", isOpen="
        + isOpen
        + ", markPrice="
        + markPrice
        + ", markValue="
        + markValue
        + ", riskValue="
        + riskValue
        + ", homeNotional="
        + homeNotional
        + ", foreignNotional="
        + foreignNotional
        + ", posState='"
        + posState
        + '\''
        + ", posCost="
        + posCost
        + ", posCost2="
        + posCost2
        + ", posCross="
        + posCross
        + ", posInit="
        + posInit
        + ", posComm="
        + posComm
        + ", posLoss="
        + posLoss
        + ", posMargin="
        + posMargin
        + ", posMaint="
        + posMaint
        + ", posAllowance="
        + posAllowance
        + ", taxableMargin="
        + taxableMargin
        + ", initMargin="
        + initMargin
        + ", maintMargin="
        + maintMargin
        + ", sessionMargin="
        + sessionMargin
        + ", targetExcessMargin="
        + targetExcessMargin
        + ", varMargin="
        + varMargin
        + ", realisedGrossPnl="
        + realisedGrossPnl
        + ", realisedTax="
        + realisedTax
        + ", realisedPnl="
        + realisedPnl
        + ", unrealisedGrossPnl="
        + unrealisedGrossPnl
        + ", longBankrupt="
        + longBankrupt
        + ", shortBankrupt="
        + shortBankrupt
        + ", taxBase="
        + taxBase
        + ", indicativeTaxRate="
        + indicativeTaxRate
        + ", indicativeTax="
        + indicativeTax
        + ", unrealisedTax="
        + unrealisedTax
        + ", unrealisedPnl="
        + unrealisedPnl
        + ", unrealisedPnlPcnt="
        + unrealisedPnlPcnt
        + ", unrealisedRoePcnt="
        + unrealisedRoePcnt
        + ", simpleQty="
        + simpleQty
        + ", simpleCost="
        + simpleCost
        + ", simpleValue="
        + simpleValue
        + ", simplePnl="
        + simplePnl
        + ", simplePnlPcnt="
        + simplePnlPcnt
        + ", avgCostPrice="
        + avgCostPrice
        + ", avgEntryPrice="
        + avgEntryPrice
        + ", breakEvenPrice="
        + breakEvenPrice
        + ", marginCallPrice="
        + marginCallPrice
        + ", liquidationPrice="
        + liquidationPrice
        + ", bankruptPrice="
        + bankruptPrice
        + ", timestamp='"
        + timestamp
        + '\''
        + ", lastPrice="
        + lastPrice
        + ", lastValue="
        + lastValue
        + '}';
  }
}
