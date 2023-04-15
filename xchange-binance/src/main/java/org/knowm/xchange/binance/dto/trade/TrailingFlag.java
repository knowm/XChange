package org.knowm.xchange.binance.dto.trade;

import org.knowm.xchange.dto.Order.IOrderFlags;

/**
 * @see <a
 *     href="https://github.com/binance/binance-spot-api-docs/blob/master/faqs/trailing-stop-faq.md">trailing-stop-faq</a>
 * @author mrmx
 */
public enum TrailingFlag implements IOrderFlags {
  /** Trailing of 0.01% */
  P0_01(1),
  /** Trailing of 0.1% */
  P0_1(10),
  /** Trailing of 1% */
  P1(100),
  /** Trailing of 10% */
  P10(1000);
  /** Basis Points, also known as BIP or BIPS, are used to indicate a percentage change. */
  private final long trailingBip;

  private TrailingFlag(long trailingBip) {
    this.trailingBip = trailingBip;
  }

  public long getTrailingBip() {
    return trailingBip;
  }

  static TrailingFlag of(Number percent) {
    switch (percent.toString()) {
      case "0.01":
        return P0_01;
      case "0.1":
        return P0_1;
      case "1":
        return P1;
      case "10":
        return P10;
    }
    throw new IllegalArgumentException("Invalid trailing " + percent);
  }
}