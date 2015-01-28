package com.xeiam.xchange.dto.trade;

import java.math.BigDecimal;
import java.util.Map;

import com.xeiam.xchange.currency.CurrencyPair;
import com.xeiam.xchange.dto.Order;

/**
 * This DTO holds information relating to trading constraints for a given exchange such as the trading fee, minimum amounts and scale factors. For
 * some exchanges, this information is relevant for all currencies, while for others it changes depending on the currency.
 */
public class TradeMetaInfo {

  private final BigDecimal tradingFee;
  private final BigDecimal minimumAmount;
  private final Integer scaleFactor;

  private final Map<CurrencyPair, CurrencyPairTradeInfo> currencyPairTradeInfoMap;

  /**
   * Constructor
   *
   * @param tradingFee
   * @param minimumAmount
   * @param scaleFactor
   * @param currencyPairTradeInfoMap
   */
  public TradeMetaInfo(BigDecimal tradingFee, BigDecimal minimumAmount, Integer scaleFactor,
      Map<CurrencyPair, CurrencyPairTradeInfo> currencyPairTradeInfoMap) {

    this.tradingFee = tradingFee;
    this.minimumAmount = minimumAmount;
    this.scaleFactor = scaleFactor;
    this.currencyPairTradeInfoMap = currencyPairTradeInfoMap;
  }

  public BigDecimal getTradingFee() {
    return tradingFee;
  }

  public BigDecimal getMinimumAmount() {

    return minimumAmount;
  }

  public Integer getScaleFactor() {

    return scaleFactor;
  }

  public Map<CurrencyPair, CurrencyPairTradeInfo> getCurrencyPairTradeInfoMap() {
    return currencyPairTradeInfoMap;
  }

  public BigDecimal getAmountStep() {

    return BigDecimal.ONE.movePointLeft(minimumAmount.scale());
  }

  public BigDecimal getPriceStep() {

    return BigDecimal.ONE.movePointLeft(scaleFactor);
  }

  public void verifyOrder(LimitOrder order) {

    verifyOrder((Order) order);

    BigDecimal price = order.getLimitPrice().stripTrailingZeros();

    if (price.scale() > scaleFactor) {
      throw new IllegalArgumentException("Unsupported price scale " + price.scale());
    }
  }

  public void verifyOrder(MarketOrder order) {

    verifyOrder((Order) order);
  }

  protected void verifyOrder(Order order) {

    BigDecimal amount = order.getTradableAmount().stripTrailingZeros();

    if (amount.scale() > minimumAmount.scale()) {
      throw new IllegalArgumentException("Unsupported amount scale " + amount.scale());
    } else if (amount.compareTo(minimumAmount) < 0) {
      throw new IllegalArgumentException("Order amount less than minimum");
    }
  }

  @Override
  public String toString() {

    return "BaseMarketMetadata{" + "minimumAmount=" + minimumAmount + ", priceScale=" + scaleFactor + '}';
  }
}
