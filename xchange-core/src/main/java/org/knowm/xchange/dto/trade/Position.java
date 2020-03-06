package org.knowm.xchange.dto.trade;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.databind.annotation.JsonPOJOBuilder;
import java.math.BigDecimal;
import java.util.Date;
import java.util.Set;
import org.knowm.xchange.currency.CurrencyPair;
import org.knowm.xchange.dto.Order;

public class Position extends Order {

  private final BigDecimal unRealisedPnL;

  private final BigDecimal realisedPnL;

  private final BigDecimal realTimePositionValue;

  public Position(
      OrderType type,
      BigDecimal originalAmount,
      CurrencyPair currencyPair,
      String id,
      Date timestamp,
      BigDecimal averagePrice,
      BigDecimal fee,
      BigDecimal unRealisedPnL,
      BigDecimal realisedPnL,
      BigDecimal realTimePositionValue) {
    super(type, originalAmount, currencyPair, id, timestamp);
    setAveragePrice(averagePrice);
    setFee(fee);
    this.unRealisedPnL = unRealisedPnL;
    this.realisedPnL = realisedPnL;
    this.realTimePositionValue = realTimePositionValue;
  }

  public BigDecimal getUnRealisedPnL() {
    return unRealisedPnL;
  }

  public BigDecimal getRealisedPnL() {
    return realisedPnL;
  }

  public BigDecimal getRealTimePositionValue() {
    return realTimePositionValue;
  }

  @JsonPOJOBuilder(withPrefix = "")
  public static class Builder extends Order.Builder {

    protected BigDecimal unRealisedPnL;

    protected BigDecimal realisedPnL;

    protected BigDecimal realTimePositionValue;

    @JsonCreator
    public Builder(
        @JsonProperty("orderType") OrderType orderType,
        @JsonProperty("currencyPair") CurrencyPair currencyPair) {

      super(orderType, currencyPair);
    }

    @Override
    public Position.Builder orderType(OrderType orderType) {

      return (Position.Builder) super.orderType(orderType);
    }

    @Override
    public Position.Builder originalAmount(BigDecimal originalAmount) {

      return (Position.Builder) super.originalAmount(originalAmount);
    }

    @Override
    public Position.Builder cumulativeAmount(BigDecimal originalAmount) {

      return (Position.Builder) super.cumulativeAmount(originalAmount);
    }

    @Override
    public Position.Builder currencyPair(CurrencyPair currencyPair) {

      return (Position.Builder) super.currencyPair(currencyPair);
    }

    @Override
    public Position.Builder id(String id) {

      return (Position.Builder) super.id(id);
    }

    @Override
    public Position.Builder userReference(String userReference) {

      return (Position.Builder) super.userReference(userReference);
    }

    @Override
    public Position.Builder timestamp(Date timestamp) {

      return (Position.Builder) super.timestamp(timestamp);
    }

    @Override
    public Position.Builder averagePrice(BigDecimal averagePrice) {

      return (Position.Builder) super.averagePrice(averagePrice);
    }

    @Override
    public Position.Builder flag(IOrderFlags flag) {

      return (Position.Builder) super.flag(flag);
    }

    @Override
    public Position.Builder flags(Set<IOrderFlags> flags) {

      return (Position.Builder) super.flags(flags);
    }

    @Override
    public Position.Builder fee(BigDecimal fee) {
      return (Position.Builder) super.fee(fee);
    }

    public Position.Builder unRealisedPnL(BigDecimal unRealisedPnL) {
      this.unRealisedPnL = unRealisedPnL;
      return this;
    }

    public Position.Builder realisedPnL(BigDecimal realisedPnL) {
      this.realisedPnL = realisedPnL;
      return this;
    }

    public Position.Builder realTimePositionValue(BigDecimal realTimePositionValue) {
      this.realTimePositionValue = realTimePositionValue;
      return this;
    }

    @Override
    public Position build() {

      return new Position(
          orderType,
          originalAmount,
          currencyPair,
          id,
          timestamp,
          averagePrice,
          fee,
          unRealisedPnL,
          realisedPnL,
          realTimePositionValue);
    }

    @Override
    public String toString() {
      return "Position{"
          + "unRealisedPnL="
          + unRealisedPnL
          + ", realisedPnL="
          + realisedPnL
          + ", realTimePositionValue="
          + realTimePositionValue
          + ", flags="
          + flags
          + ", orderType="
          + orderType
          + ", originalAmount="
          + originalAmount
          + ", cumulativeAmount="
          + cumulativeAmount
          + ", remainingAmount="
          + remainingAmount
          + ", currencyPair="
          + currencyPair
          + ", id='"
          + id
          + '\''
          + ", timestamp="
          + timestamp
          + ", averagePrice="
          + averagePrice
          + ", fee="
          + fee
          + ", leverage='"
          + leverage
          + '\''
          + '}';
    }
  }
}
