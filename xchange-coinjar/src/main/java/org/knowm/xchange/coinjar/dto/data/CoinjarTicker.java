package org.knowm.xchange.coinjar.dto.data;

import com.fasterxml.jackson.annotation.JsonProperty;
import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;
import org.apache.commons.lang3.builder.ToStringBuilder;

public class CoinjarTicker {

  public final String volume24h;

  public final String volume;

  public final String transitionTime;

  public final String status;

  public final Integer session;

  public final String prevClose;

  public final String last;

  public final String currentTime;

  public final String bid;

  public final String ask;

  /**
   * @param currentTime
   * @param volume24h
   * @param prevClose
   * @param last
   * @param session
   * @param status
   * @param transitionTime
   * @param volume
   * @param ask
   * @param bid
   */
  public CoinjarTicker(
      @JsonProperty("volume_24h") String volume24h,
      @JsonProperty("volume") String volume,
      @JsonProperty("transition_time") String transitionTime,
      @JsonProperty("status") String status,
      @JsonProperty("session") Integer session,
      @JsonProperty("prev_close") String prevClose,
      @JsonProperty("last") String last,
      @JsonProperty("current_time") String currentTime,
      @JsonProperty("bid") String bid,
      @JsonProperty("ask") String ask) {
    super();
    this.volume24h = volume24h;
    this.volume = volume;
    this.transitionTime = transitionTime;
    this.status = status;
    this.session = session;
    this.prevClose = prevClose;
    this.last = last;
    this.currentTime = currentTime;
    this.bid = bid;
    this.ask = ask;
  }

  @Override
  public String toString() {
    return new ToStringBuilder(this)
        .append("volume24h", volume24h)
        .append("volume", volume)
        .append("transitionTime", transitionTime)
        .append("status", status)
        .append("session", session)
        .append("prevClose", prevClose)
        .append("last", last)
        .append("currentTime", currentTime)
        .append("bid", bid)
        .append("ask", ask)
        .toString();
  }

  @Override
  public boolean equals(Object o) {
    if (this == o) return true;

    if (o == null || getClass() != o.getClass()) return false;

    CoinjarTicker ticker = (CoinjarTicker) o;

    return new EqualsBuilder()
        .append(volume24h, ticker.volume24h)
        .append(volume, ticker.volume)
        .append(transitionTime, ticker.transitionTime)
        .append(status, ticker.status)
        .append(session, ticker.session)
        .append(prevClose, ticker.prevClose)
        .append(last, ticker.last)
        .append(currentTime, ticker.currentTime)
        .append(bid, ticker.bid)
        .append(ask, ticker.ask)
        .isEquals();
  }

  @Override
  public int hashCode() {
    return new HashCodeBuilder(17, 37)
        .append(volume24h)
        .append(volume)
        .append(transitionTime)
        .append(status)
        .append(session)
        .append(prevClose)
        .append(last)
        .append(currentTime)
        .append(bid)
        .append(ask)
        .toHashCode();
  }
}
