package org.knowm.xchange.simulated;

/**
 * Entry point for assertions of different data types. Each method in this class is a static factory
 * for the type-specific assertion objects.
 */
@javax.annotation.Generated(value = "assertj-assertions-generator")
class Assertions {

  /**
   * Creates a new instance of <code>{@link org.knowm.xchange.simulated.TradeAssert}</code>.
   *
   * @param actual the actual value.
   * @return the created assertion object.
   */
  @org.assertj.core.util.CheckReturnValue
  public static org.knowm.xchange.simulated.TradeAssert assertThat(
      org.knowm.xchange.dto.marketdata.Trade actual) {
    return new org.knowm.xchange.simulated.TradeAssert(actual);
  }

  /**
   * Creates a new instance of <code>{@link org.knowm.xchange.simulated.UserTradeAssert}</code>.
   *
   * @param actual the actual value.
   * @return the created assertion object.
   */
  @org.assertj.core.util.CheckReturnValue
  public static org.knowm.xchange.simulated.UserTradeAssert assertThat(
      org.knowm.xchange.dto.trade.UserTrade actual) {
    return new org.knowm.xchange.simulated.UserTradeAssert(actual);
  }

  /**
   * Creates a new instance of <code>{@link org.knowm.xchange.simulated.FillAssert}</code>.
   *
   * @param actual the actual value.
   * @return the created assertion object.
   */
  @org.assertj.core.util.CheckReturnValue
  public static org.knowm.xchange.simulated.FillAssert assertThat(
      org.knowm.xchange.simulated.Fill actual) {
    return new org.knowm.xchange.simulated.FillAssert(actual);
  }

  /** Creates a new <code>{@link Assertions}</code>. */
  protected Assertions() {
    // empty
  }
}
