package org.knowm.xchange.btcturk.dto.trade;

import org.knowm.xchange.btcturk.dto.BTCTurkOrderMethods;
import org.knowm.xchange.btcturk.dto.BTCTurkOrderTypes;
import org.knowm.xchange.currency.CurrencyPair;

/** @author mertguner */
public class BTCTurkOrder {
  private String Id;
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
  private CurrencyPair PairSymbol;
  private String DateTime;

  public String getId() {
    return Id;
  }

  public void setId(String id) {
    Id = id;
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

  public CurrencyPair getPairSymbol() {
    return PairSymbol;
  }

  public void setPairSymbol(CurrencyPair pairSymbol) {
    PairSymbol = pairSymbol;
  }

  public String getDateTime() {
    return DateTime;
  }

  public void setDateTime(String dateTime) {
    DateTime = dateTime;
  }

  @Override
  public String toString() {
    return "BTCTurkOrder [Id="
        + Id
        + ", OrderMethod="
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
        + ", DateTime="
        + DateTime
        + "]";
  }
}
