package org.knowm.xchange.simulated;

import org.hamcrest.BaseMatcher;
import org.hamcrest.Description;
import org.hamcrest.Matcher;
import org.hamcrest.Matchers;
import org.knowm.xchange.dto.trade.UserTrade;

final class FillMatcher extends BaseMatcher<Fill> {

  static FillMatcher fillMatcher() {
    return new FillMatcher();
  }

  private Matcher<String> apiKey = Matchers.any(String.class);
  private Matcher<UserTrade> trade = Matchers.any(UserTrade.class);

  private FillMatcher() {}

  FillMatcher whereApiKey(Matcher<String> apiKey) {
    this.apiKey = apiKey;
    return this;
  }

  FillMatcher whereTrade(Matcher<UserTrade> trade) {
    this.trade = trade;
    return this;
  }

  @Override
  public boolean matches(Object actual) {
    Fill other = (Fill) actual;
    return apiKey.matches(other.getApiKey())
        && trade.matches(other.getTrade());
  }

  @Override
  public void describeTo(Description description) {
    description.appendText("Fill with ")
      .appendText("apiKey that matches [").appendDescriptionOf(apiKey)
      .appendText("], trade that matches [").appendDescriptionOf(trade);
  }
}
