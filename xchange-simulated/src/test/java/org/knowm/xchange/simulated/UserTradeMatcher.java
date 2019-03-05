package org.knowm.xchange.simulated;

import java.math.BigDecimal;
import org.hamcrest.BaseMatcher;
import org.hamcrest.Description;
import org.hamcrest.Matcher;
import org.hamcrest.Matchers;
import org.knowm.xchange.currency.Currency;
import org.knowm.xchange.dto.Order.OrderType;
import org.knowm.xchange.dto.trade.UserTrade;

final class UserTradeMatcher extends BaseMatcher<UserTrade> {

  static UserTradeMatcher userTradeMatcher() {
    return new UserTradeMatcher();
  }

  private Matcher<String> orderId = Matchers.any(String.class);
  private Matcher<String> id = Matchers.any(String.class);
  private Matcher<BigDecimal> fee = Matchers.any(BigDecimal.class);
  private Matcher<Currency> feeCurrency = Matchers.any(Currency.class);
  private Matcher<BigDecimal> originalAmount = Matchers.any(BigDecimal.class);
  private Matcher<BigDecimal> price = Matchers.any(BigDecimal.class);
  private Matcher<OrderType> type = Matchers.any(OrderType.class);

  private UserTradeMatcher() {}

  UserTradeMatcher whereOrderId(Matcher<String> orderId) {
    this.orderId = orderId;
    return this;
  }

  UserTradeMatcher whereId(Matcher<String> id) {
    this.id = id;
    return this;
  }

  UserTradeMatcher whereFeeAmount(Matcher<BigDecimal> fee) {
    this.fee = fee;
    return this;
  }

  UserTradeMatcher whereFeeCurrency(Matcher<Currency> feeCurrency) {
    this.feeCurrency = feeCurrency;
    return this;
  }

  UserTradeMatcher whereOriginalAmount(Matcher<BigDecimal> originalAmount) {
    this.originalAmount = originalAmount;
    return this;
  }

  UserTradeMatcher wherePrice(Matcher<BigDecimal> price) {
    this.price = price;
    return this;
  }

  UserTradeMatcher whereType(Matcher<OrderType> type) {
    this.type = type;
    return this;
  }

  @Override
  public boolean matches(Object actual) {
    UserTrade other = (UserTrade) actual;
    return orderId.matches(other.getOrderId())
        && id.matches(other.getId())
        && fee.matches(other.getFeeAmount())
        && feeCurrency.matches(other.getFeeCurrency())
        && originalAmount.matches(other.getOriginalAmount())
        && price.matches(other.getPrice())
        && type.matches(other.getType());
  }

  @Override
  public void describeTo(Description description) {
    description
        .appendText("UserTrade with ")
        .appendText("orderId that matches [")
        .appendDescriptionOf(orderId)
        .appendText("], id that matches [")
        .appendDescriptionOf(id)
        .appendText("], fee that matches [")
        .appendDescriptionOf(fee)
        .appendText("], feeCurrency that matches [")
        .appendDescriptionOf(feeCurrency)
        .appendText("], originalAmount that matches [")
        .appendDescriptionOf(originalAmount)
        .appendText("], price that matches [")
        .appendDescriptionOf(price)
        .appendText("], type that matches [")
        .appendDescriptionOf(type);
  }
}
