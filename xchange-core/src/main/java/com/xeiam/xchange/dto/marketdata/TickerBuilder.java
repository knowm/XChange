package com.xeiam.xchange.dto.marketdata;

import com.xeiam.xchange.utils.DateUtils;
import org.joda.money.BigMoney;

import java.math.BigDecimal;

/**
 * <p>Builder to provide the following to {@link Ticker}:</p>
 * <ul>
 * <li>Provision of fluent chained construction interface</li>
 * </ul>
 *
 * @since 0.0.1
 *         
 */
public class TickerBuilder {

  private String tradableIdentifier;
  private BigMoney last;
  private BigMoney bid;
  private BigMoney ask;
  private BigMoney high;
  private BigMoney low;
  private BigDecimal volume;
  private ErrorMessage errorMessage;

  /**
   * @return A new instance of the builder
   */
  public static TickerBuilder newInstance() {
    return new TickerBuilder();
  }

  // Prevent repeat builds
  private boolean isBuilt = false;

  public Ticker build() {
    validateState();

    Ticker ticker = new Ticker();
    ticker.setAsk(ask);
    ticker.setBid(bid);
    ticker.setHigh(high);
    ticker.setLow(low);
    ticker.setVolume(volume);
    ticker.setLast(last);
    ticker.setTradableIdentifier(tradableIdentifier);
    ticker.setTimestamp(DateUtils.nowUtc());
    ticker.setErrorMessage(errorMessage);

    isBuilt = true;

    return ticker;
  }

  private void validateState() {
    if (isBuilt) {
      throw new IllegalStateException("The entity has been built");
    }
  }

  public TickerBuilder withTradableIdentifier(String tradableIdentifier) {
    this.tradableIdentifier = tradableIdentifier;
    return this;
  }

  public TickerBuilder withLast(BigMoney last) {
    this.last = last;
    return this;
  }

  public TickerBuilder withBid(BigMoney bid) {
    this.bid = bid;
    return this;
  }

  public TickerBuilder withAsk(BigMoney ask) {
    this.ask = ask;
    return this;
  }

  public TickerBuilder withHigh(BigMoney high) {
    this.high = high;
    return this;
  }

  public TickerBuilder withLow(BigMoney low) {
    this.low = low;
    return this;
  }

  public TickerBuilder withVolume(BigDecimal volume) {
    this.volume = volume;
    return this;
  }

  public TickerBuilder withErrorMessage(ErrorMessage errorMessage) {
    this.errorMessage = errorMessage;
    return this;
  }

}
