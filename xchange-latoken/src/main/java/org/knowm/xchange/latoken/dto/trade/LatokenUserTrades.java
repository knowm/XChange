package org.knowm.xchange.latoken.dto.trade;

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
 * 			"id": "1555492358.126073.126767@0502:2",
 * 			"orderId": "1555492358.126073.126767@0502:2",
 * 			"commission": 0.012,
 * 			"side": "buy",
 * 			"price": 136.2,
 * 			"amount": 0.7,
 * 			"time": 1555515807369
 * 		}
 * 	]
 * }
 * </pre>
 *
 * @author Ezer
 */
public class LatokenUserTrades {

  private final String pairId;
  private final String symbol;
  private final int tradeCount;
  private final List<LatokenUserTrade> trades;

  /**
   * C'tor
   *
   * @param pairId
   * @param symbol
   * @param tradeCount
   * @param trades
   */
  public LatokenUserTrades(
      @JsonProperty("pairId") String pairId,
      @JsonProperty("symbol") String symbol,
      @JsonProperty("tradeCount") int tradeCount,
      @JsonProperty("trades") List<LatokenUserTrade> trades) {

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
  public String getPairId() {
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
   * Number of trades in response
   *
   * @return
   */
  public int getTradeCount() {
    return tradeCount;
  }

  /**
   * List of {@link LatokenUserTrade} objects.
   *
   * @return
   */
  public List<LatokenUserTrade> getTrades() {
    return trades;
  }

  @Override
  public String toString() {
    return "LatokenUserTrade [pairId = "
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
