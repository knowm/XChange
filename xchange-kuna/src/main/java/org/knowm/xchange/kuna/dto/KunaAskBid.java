package org.knowm.xchange.kuna.dto;

import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import java.util.Arrays;

/**
 * Asks and bids of crypto currency. Instances of this type are immutable, constructed with a
 * dedicated Builder implementation.
 *
 * @author Dat Bui
 */
@JsonDeserialize(builder = KunaAskBid.Builder.class)
public class KunaAskBid {
  private KunaOrder[] asks;
  private KunaOrder[] bids;

  /** Hide default constructor. */
  private KunaAskBid() {}

  /**
   * Creates new builder.
   *
   * @return builder
   */
  public static Builder builder() {
    return new Builder();
  }

  public KunaOrder[] getAsks() {
    return asks;
  }

  public KunaOrder[] getBids() {
    return bids;
  }

  @Override
  public String toString() {
    return "KunaAskBid{"
        + "asks="
        + Arrays.toString(asks)
        + ", bids="
        + Arrays.toString(bids)
        + '}';
  }

  public static class Builder {

    private KunaAskBid target = new KunaAskBid();

    public Builder withAsks(KunaOrder[] asks) {
      target.asks = asks;
      return this;
    }

    public Builder withBids(KunaOrder[] bids) {
      target.bids = bids;
      return this;
    }

    public KunaAskBid build() {
      return this.target;
    }
  }
}
