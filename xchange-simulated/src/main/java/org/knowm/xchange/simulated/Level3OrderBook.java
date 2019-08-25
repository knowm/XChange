package org.knowm.xchange.simulated;

import java.util.List;
import lombok.Data;
import org.knowm.xchange.dto.trade.LimitOrder;

/**
 * A full order book, consisting of every single limit order on the book on both the ask and bid
 * sides.
 *
 * @author Graham Crockford
 */
@Data
public class Level3OrderBook {
  private final List<LimitOrder> asks;
  private final List<LimitOrder> bids;
}
