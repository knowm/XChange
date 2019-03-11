package org.knowm.xchange.kuna.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;

/**
 * Class encapsulates server time and ticker. Instances of this type are immutable, constructed with
 * a dedicated Builder implementation.
 *
 * @author Dat Bui
 */
@JsonDeserialize(builder = KunaTimeTicker.Builder.class)
public class KunaTimeTicker {

  public static final String PROP_AT = "at";
  public static final String PROP_TICKER = "ticker";

  private Long timestamp;
  private KunaTicker ticker;

  /** Hide default constructor. */
  private KunaTimeTicker() {}

  /**
   * Creates new builder.
   *
   * @return builder
   */
  public static Builder builder() {
    return new Builder();
  }

  /**
   * Returns server time.
   *
   * @return server time
   */
  public Long getTimestamp() {
    return timestamp;
  }

  /**
   * Returns ticker.
   *
   * @return ticker
   */
  public KunaTicker getTicker() {
    return ticker;
  }

  @Override
  public String toString() {
    return "KunaTimeTicker{" + "timestamp=" + timestamp + ", ticker=" + ticker + '}';
  }

  public static class Builder {

    private KunaTimeTicker target = new KunaTimeTicker();

    @JsonProperty(PROP_AT)
    public Builder withTimestamp(Long timestamp) {
      target.timestamp = timestamp;
      return this;
    }

    @JsonProperty(PROP_TICKER)
    public Builder withTicker(KunaTicker ticker) {
      target.ticker = ticker;
      return this;
    }

    public KunaTimeTicker build() {
      return this.target;
    }
  }
}
