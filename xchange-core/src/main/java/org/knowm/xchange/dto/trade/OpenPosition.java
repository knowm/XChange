package org.knowm.xchange.dto.trade;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.databind.annotation.JsonPOJOBuilder;
import java.math.BigDecimal;
import java.util.Date;
import java.util.Set;
import org.knowm.xchange.currency.CurrencyPair;
import org.knowm.xchange.dto.Order;
import org.knowm.xchange.instrument.Instrument;

public class OpenPosition extends Order {

  public OpenPosition(
      OrderType type,
      BigDecimal originalAmount,
      Instrument instrument,
      String id,
      Date timestamp,
      BigDecimal averagePrice,
      BigDecimal fee) {
    super(type, originalAmount, instrument, id, timestamp);
    setAveragePrice(averagePrice);
    setFee(fee);
  }

  @JsonPOJOBuilder(withPrefix = "")
  public static class Builder extends Order.Builder {

    @JsonCreator
    public Builder(
        @JsonProperty("orderType") OrderType orderType,
        @JsonProperty("instrument") Instrument instrument) {

      super(orderType, instrument);
    }

    @Override
    public OpenPosition.Builder orderType(OrderType orderType) {

      return (OpenPosition.Builder) super.orderType(orderType);
    }

    @Override
    public OpenPosition.Builder originalAmount(BigDecimal originalAmount) {

      return (OpenPosition.Builder) super.originalAmount(originalAmount);
    }

    @Override
    public LimitOrder.Builder cumulativeAmount(BigDecimal originalAmount) {

      return (LimitOrder.Builder) super.cumulativeAmount(originalAmount);
    }

    @Override
    public LimitOrder.Builder currencyPair(CurrencyPair currencyPair) {

      return (LimitOrder.Builder) super.currencyPair(currencyPair);
    }

    @Override
    public LimitOrder.Builder id(String id) {

      return (LimitOrder.Builder) super.id(id);
    }

    @Override
    public LimitOrder.Builder userReference(String userReference) {

      return (LimitOrder.Builder) super.userReference(userReference);
    }

    @Override
    public LimitOrder.Builder timestamp(Date timestamp) {

      return (LimitOrder.Builder) super.timestamp(timestamp);
    }

    @Override
    public LimitOrder.Builder averagePrice(BigDecimal averagePrice) {

      return (LimitOrder.Builder) super.averagePrice(averagePrice);
    }

    @Override
    public LimitOrder.Builder flag(IOrderFlags flag) {

      return (LimitOrder.Builder) super.flag(flag);
    }

    @Override
    public LimitOrder.Builder flags(Set<IOrderFlags> flags) {

      return (LimitOrder.Builder) super.flags(flags);
    }

    @Override
    public LimitOrder.Builder fee(BigDecimal fee) {
      return (LimitOrder.Builder) super.fee(fee);
    }

    @Override
    public OpenPosition build() {

      return new OpenPosition(
          orderType, originalAmount, instrument, id, timestamp, averagePrice, fee);
    }
  }
}
