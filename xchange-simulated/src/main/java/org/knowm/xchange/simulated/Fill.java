package org.knowm.xchange.simulated;

import lombok.Data;
import org.knowm.xchange.dto.trade.UserTrade;

/**
 * Represents a trade against a {@link Level3OrderBook} for a given {@link #getApiKey()} (user),
 * indicating whether the fill was executed as the market maker.
 *
 * @author Graham Crockford
 */
@Data
final class Fill {
  private final String apiKey;
  private final UserTrade trade;
  private final boolean taker;
}
