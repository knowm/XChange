package org.knowm.xchange.latoken.dto.marketdata;

import com.fasterxml.jackson.annotation.JsonProperty;
import java.util.List;

/**
 * Response schema:
 *
 * <pre>
 * {
 * 	"pairId": 502,
 * 	"symbol": "LAETH",
 * 	"tradeCount": 1,
 * 	"trades": [
 * 		{
 * 			"side": "sell",
 * 			"price": 136.2,
 * 			"amount": 0.57,
 * 			"timestamp": 1555515807369
 * 		}
 * 	]
 * }
 * </pre>
 *
 * @author Ezer
 */
public final class LatokenTrades {

  private final long pairId;
  private final String symbol;
  private final int tradeCount;
  private final List<LatokenTrade> trades;

  public LatokenTrades(
      @JsonProperty("pairId") long pairId,
      @JsonProperty("symbol") String symbol,
      @JsonProperty("tradeCount") int tradeCount,
      @JsonProperty("trades") List<LatokenTrade> trades) {

    this.pairId = pairId;
    this.symbol = symbol;
    this.tradeCount = tradeCount;
    this.trades = trades;
  }

  /**
   * ID of trading pair
   *
   * @return
   */
  public long getPairId() {
    return pairId;
  }

  /**
   * Trading pair symbol
   *
   * @return
   */
  public String getSymbol() {
    return symbol;
  }

  /**
   * Count of trades in response
   *
   * @return
   */
  public int getTradeCount() {
    return tradeCount;
  }

  /**
   * List of {@link LatokenTrade}s
   *
   * @return
   */
  public List<LatokenTrade> getTrades() {
    return trades;
  }

  @Override
  public String toString() {
    return "LatokenTrades [pairId = "
        + pairId
        + ", symbol = "
        + symbol
        + ", tradeCount = "
        + tradeCount
        + ", trades = "
        + trades
        + "]";
  }
}
