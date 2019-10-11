package org.knowm.xchange.latoken.dto.account;

import com.fasterxml.jackson.annotation.JsonProperty;

/**
 * Response schema:
 *
 * <pre>
 * {
 * 	"currencyId": 102,
 * 	"symbol": "LA",
 * 	"name": "Latoken",
 * 	"amount": 1054.66,
 * 	"available": 900.66,
 * 	"frozen": 154,
 * 	"pending": 0
 * }
 * </pre>
 *
 * @author Ezer
 */
public final class LatokenBalance {

  private final String currencyId;
  private final String symbol;
  private final String name;
  private final double amount;
  private final double available;
  private final double frozen;
  private final double pending;

  public LatokenBalance(
      @JsonProperty("currencyId") String currencyId,
      @JsonProperty("symbol") String symbol,
      @JsonProperty("name") String name,
      @JsonProperty("amount") double amount,
      @JsonProperty("available") double available,
      @JsonProperty("frozen") double frozen,
      @JsonProperty("pending") double pending) {

    this.currencyId = currencyId;
    this.symbol = symbol;
    this.name = name;
    this.amount = amount;
    this.available = available;
    this.frozen = frozen;
    this.pending = pending;
  }

  /**
   * Id of currency
   *
   * @return
   */
  public String getCurrencyId() {
    return currencyId;
  }

  /**
   * Symbol of currency
   *
   * @return
   */
  public String getSymbol() {
    return symbol;
  }

  /**
   * Name of currency
   *
   * @return
   */
  public String getName() {
    return name;
  }

  /**
   * Total amount of balance
   *
   * @return
   */
  public double getAmount() {
    return amount;
  }

  /**
   * Available amount of balance
   *
   * @return
   */
  public double getAvailable() {
    return available;
  }

  /**
   * Amount frozen by orders or transactions amount
   *
   * @return
   */
  public double getFrozen() {
    return frozen;
  }

  public double getPending() {
    return pending;
  }

  @Override
  public String toString() {
    return "LatokenOrderbook [currencyId = "
        + currencyId
        + ", symbol = "
        + symbol
        + ", name = "
        + name
        + ", amount = "
        + amount
        + ", available = "
        + available
        + ", frozen = "
        + frozen
        + ", pending = "
        + pending
        + "]";
  }
}
