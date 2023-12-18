package org.knowm.xchange.btcturk.dto.trade;

import com.fasterxml.jackson.annotation.JsonProperty;
import org.knowm.xchange.btcturk.dto.BTCTurkOrderMethods;
import org.knowm.xchange.btcturk.dto.BTCTurkOrderTypes;
import org.knowm.xchange.btcturk.dto.BTCTurkPair;

/**
 * @author mertguner
 */
public class BTCTurkOrder {
  private BTCTurkOrderMethods OrderMethod;
  private String Price;
  private String PricePrecision;
  private String Amount;
  private String AmountPrecision;
  private String Total;
  private String TotalPrecision;
  private int DenominatorPrecision;
  private String TriggerPrice;
  private String TriggerPricePrecision;
  private BTCTurkOrderTypes OrderType;
  private BTCTurkPair PairSymbol;

  public BTCTurkOrder(
      @JsonProperty("OrderMethod") BTCTurkOrderMethods OrderMethod,
      @JsonProperty("Price") String Price,
      @JsonProperty("PricePrecision") String PricePrecision,
      @JsonProperty("Amount") String Amount,
      @JsonProperty("AmountPrecision") String AmountPrecision,
      @JsonProperty("Total") String Total,
      @JsonProperty("TotalPrecision") String TotalPrecision,
      @JsonProperty("DenominatorPrecision") int DenominatorPrecision,
      @JsonProperty("TriggerPrice") String TriggerPrice,
      @JsonProperty("TriggerPricePrecision") String TriggerPricePrecision,
      @JsonProperty("OrderType") BTCTurkOrderTypes OrderType,
      @JsonProperty("PairSymbol") BTCTurkPair PairSymbol) {
    this.OrderMethod = OrderMethod;
    this.Price = Price;
    this.PricePrecision = PricePrecision;
    this.Amount = Amount;
    this.AmountPrecision = AmountPrecision;
    this.Total = Total;
    this.TotalPrecision = TotalPrecision;
    this.DenominatorPrecision = DenominatorPrecision;
    this.TriggerPrice = TriggerPrice;
    this.TriggerPricePrecision = TriggerPricePrecision;
    this.OrderType = OrderType;
    this.OrderType = OrderType;
    this.PairSymbol = PairSymbol;
  }

  public BTCTurkOrderMethods getOrderMethod() {
    return OrderMethod;
  }

  public void setOrderMethod(BTCTurkOrderMethods orderMethod) {
    OrderMethod = orderMethod;
  }

  public String getPrice() {
    return Price;
  }

  public void setPrice(String price) {
    Price = price;
  }

  public String getPricePrecision() {
    return PricePrecision;
  }

  public void setPricePrecision(String pricePrecision) {
    PricePrecision = pricePrecision;
  }

  public String getAmount() {
    return Amount;
  }

  public void setAmount(String amount) {
    Amount = amount;
  }

  public String getAmountPrecision() {
    return AmountPrecision;
  }

  public void setAmountPrecision(String amountPrecision) {
    AmountPrecision = amountPrecision;
  }

  public String getTotal() {
    return Total;
  }

  public void setTotal(String total) {
    Total = total;
  }

  public String getTotalPrecision() {
    return TotalPrecision;
  }

  public void setTotalPrecision(String totalPrecision) {
    TotalPrecision = totalPrecision;
  }

  public int getDenominatorPrecision() {
    return DenominatorPrecision;
  }

  public void setDenominatorPrecision(int denominatorPrecision) {
    DenominatorPrecision = denominatorPrecision;
  }

  public String getTriggerPrice() {
    return TriggerPrice;
  }

  public void setTriggerPrice(String triggerPrice) {
    TriggerPrice = triggerPrice;
  }

  public String getTriggerPricePrecision() {
    return TriggerPricePrecision;
  }

  public void setTriggerPricePrecision(String triggerPricePrecision) {
    TriggerPricePrecision = triggerPricePrecision;
  }

  public BTCTurkOrderTypes getOrderType() {
    return OrderType;
  }

  public void setOrderType(BTCTurkOrderTypes orderType) {
    OrderType = orderType;
  }

  public BTCTurkPair getPairSymbol() {
    return PairSymbol;
  }

  public void setPairSymbol(BTCTurkPair pairSymbol) {
    PairSymbol = pairSymbol;
  }

  @Override
  public String toString() {
    return "BTCTurkOrder [OrderMethod="
        + OrderMethod
        + ", Price="
        + Price
        + ", PricePrecision="
        + PricePrecision
        + ", Amount="
        + Amount
        + ", AmountPrecision="
        + AmountPrecision
        + ", Total="
        + Total
        + ", TotalPrecision="
        + TotalPrecision
        + ", DenominatorPrecision="
        + DenominatorPrecision
        + ", TriggerPrice="
        + TriggerPrice
        + ", TriggerPricePrecision="
        + TriggerPricePrecision
        + ", OrderType="
        + OrderType
        + ", PairSymbol="
        + PairSymbol
        + "]";
  }
}
