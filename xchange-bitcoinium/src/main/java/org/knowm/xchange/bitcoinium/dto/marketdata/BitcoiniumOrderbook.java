package org.knowm.xchange.bitcoinium.dto.marketdata;

import com.fasterxml.jackson.annotation.JsonProperty;
import java.math.BigDecimal;

/** Data object representing Orderbook from Bitcoinium WebService */
public final class BitcoiniumOrderbook {

  private final BitcoiniumTicker bitcoiniumTicker;
  private final CondensedOrder[] bids;
  private final CondensedOrder[] asks;

  /**
   * Constructor
   *
   * @param bitcoiniumTicker
   * @param bids
   * @param asks
   */
  public BitcoiniumOrderbook(
      @JsonProperty("ticker") BitcoiniumTicker bitcoiniumTicker,
      @JsonProperty("bids") CondensedOrder[] bids,
      @JsonProperty("asks") CondensedOrder[] asks) {

    this.bitcoiniumTicker = bitcoiniumTicker;
    this.bids = bids;
    this.asks = asks;
  }

  public CondensedOrder[] getBids() {

    return bids;
  }

  public CondensedOrder[] getAsks() {

    return asks;
  }

  public BitcoiniumTicker getBitcoiniumTicker() {

    return bitcoiniumTicker;
  }

  /**
   * This class represents not just a single order in the orderbook, but a bunch of them condensed
   * into one.
   */
  public static final class CondensedOrder {

    private final BigDecimal price;
    private final BigDecimal volume;

    /**
     * Constructor
     *
     * @param price
     * @param volume
     */
    public CondensedOrder(
        @JsonProperty("p") BigDecimal price, @JsonProperty("v") BigDecimal volume) {

      this.price = price;
      this.volume = volume;
    }

    public BigDecimal getPrice() {

      return price;
    }

    public BigDecimal getVolume() {

      return volume;
    }
  }
}
