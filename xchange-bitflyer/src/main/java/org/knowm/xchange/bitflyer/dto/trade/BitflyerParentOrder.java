package org.knowm.xchange.bitflyer.dto.trade;

import static org.knowm.xchange.bitflyer.dto.trade.BitflyerOrderMethod.SIMPLE;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import org.knowm.xchange.bitflyer.BitflyerUtils;
import org.knowm.xchange.currency.CurrencyPair;
import org.knowm.xchange.dto.Order;

@JsonInclude(JsonInclude.Include.NON_NULL)
public class BitflyerParentOrder {
  private BitflyerOrderMethod orderMethod;
  private Long minuteToExpire;
  private BitflyerTimeInForce timeInForce;
  private List<BitflyerParentOrderParameter> parameters;

  public BitflyerParentOrder(
      BitflyerOrderMethod orderMethod,
      Long minuteToExpire,
      BitflyerTimeInForce timeInForce,
      List<BitflyerParentOrderParameter> parameters) {

    this.orderMethod = orderMethod;
    this.minuteToExpire = minuteToExpire;
    this.timeInForce = timeInForce;
    this.parameters = parameters;
  }

  public static BitflyerParentOrderBuilder getOrderBuilder() {
    return new BitflyerParentOrderBuilder();
  }

  @JsonProperty("order_method")
  public BitflyerOrderMethod getOrderMethod() {
    return orderMethod;
  }

  public void setOrderMethod(BitflyerOrderMethod orderMethod) {
    this.orderMethod = orderMethod;
  }

  @JsonProperty("minute_to_expire")
  public Long getMinuteToExpire() {
    return minuteToExpire;
  }

  public void setMinuteToExpire(Long minuteToExpire) {
    this.minuteToExpire = minuteToExpire;
  }

  @JsonProperty("time_in_force")
  public BitflyerTimeInForce getTimeInForce() {
    return timeInForce;
  }

  public void setTimeInForce(BitflyerTimeInForce timeInForce) {
    this.timeInForce = timeInForce;
  }

  @JsonProperty("parameters")
  public List<BitflyerParentOrderParameter> getParameters() {
    return parameters;
  }

  public void setParameters(List<BitflyerParentOrderParameter> parameters) {
    this.parameters = parameters;
  }

  @Override
  public String toString() {
    return "BitflyerParentOrder{"
        + "orderMethod="
        + orderMethod
        + ", minuteToExpire="
        + minuteToExpire
        + ", timeInForce="
        + timeInForce
        + ", parameters="
        + parameters
        + '}';
  }

  public static class BitflyerParentOrderBuilder {
    private BitflyerOrderMethod orderMethod = SIMPLE;
    private Long minuteToExpire = null;
    private BitflyerTimeInForce timeInForce = BitflyerTimeInForce.GTC;
    private List<BitflyerParentOrderParameter> parameters = new ArrayList<>();

    public BitflyerParentOrderBuilder withOrderMethod(BitflyerOrderMethod orderMethod) {
      this.orderMethod = orderMethod;

      return this;
    }

    public BitflyerParentOrderBuilder withMinuteToExpire(Long minuteToExpire) {
      this.minuteToExpire = minuteToExpire;

      return this;
    }

    public BitflyerParentOrderBuilder withTimeInForce(BitflyerTimeInForce timeInForce) {
      this.timeInForce = timeInForce;

      return this;
    }

    public BitflyerParentOrderBuilder withParameter(
        CurrencyPair productCode,
        BitflyerParentOrderConditionType conditionType,
        Order.OrderType side,
        BigDecimal price,
        BigDecimal triggerPrice,
        BigDecimal size,
        BigDecimal offset) {

      BitflyerParentOrderParameter parameter =
          new BitflyerParentOrderParameter(
              BitflyerUtils.bitflyerProductCode(productCode),
              conditionType,
              BitflyerSide.fromOrderType(side),
              price,
              triggerPrice,
              size,
              offset);

      parameters.add(parameter);

      return this;
    }

    public BitflyerParentOrder buildOrder() {
      return new BitflyerParentOrder(orderMethod, minuteToExpire, timeInForce, parameters);
    }
  }
}
