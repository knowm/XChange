package org.knowm.xchange.coingi.dto.marketdata;

import com.fasterxml.jackson.annotation.JsonProperty;
import java.util.Collection;
import java.util.Collections;
import java.util.List;
import java.util.Objects;

/** Current order book. */
public class CoingiOrderBook {
  private Collection<CoingiOrderGroup> asks;

  private Collection<CoingiOrderGroup> bids;

  private List<DepthRange> askDepthRange;

  private List<DepthRange> bidDepthRange;

  public CoingiOrderBook(
      @JsonProperty("asks") Collection<CoingiOrderGroup> asks,
      @JsonProperty("bids") Collection<CoingiOrderGroup> bids,
      @JsonProperty("askDepthRange") List<DepthRange> askDepthRange,
      @JsonProperty("bidDepthRange") List<DepthRange> bidDepthRange) {
    this.asks = Objects.requireNonNull(asks);
    this.bids = Objects.requireNonNull(bids);
    this.askDepthRange = Objects.requireNonNull(askDepthRange);
    this.bidDepthRange = Objects.requireNonNull(bidDepthRange);
  }

  public Collection<CoingiOrderGroup> getAsks() {
    return Collections.unmodifiableCollection(asks);
  }

  public Collection<CoingiOrderGroup> getBids() {
    return Collections.unmodifiableCollection(bids);
  }

  public Collection<DepthRange> getAskDepthRange() {
    return Collections.unmodifiableCollection(askDepthRange);
  }

  public Collection<DepthRange> getBidDepthRange() {
    return Collections.unmodifiableCollection(bidDepthRange);
  }

  @Override
  public boolean equals(final Object o) {
    if (this == o) return true;
    if (o == null || getClass() != o.getClass()) return false;
    CoingiOrderBook coingiOrderBook = (CoingiOrderBook) o;
    return Objects.equals(asks, coingiOrderBook.asks)
        && Objects.equals(bids, coingiOrderBook.bids)
        && Objects.equals(askDepthRange, coingiOrderBook.askDepthRange)
        && Objects.equals(bidDepthRange, coingiOrderBook.bidDepthRange);
  }

  @Override
  public int hashCode() {
    return Objects.hash(asks, bids, askDepthRange, bidDepthRange);
  }
}
