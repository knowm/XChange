package org.knowm.xchange.latoken.dto.account;

import com.fasterxml.jackson.annotation.JsonProperty;
import java.math.BigDecimal;

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
  private final BigDecimal amount;
  private final BigDecimal available;
  private final BigDecimal frozen;
  private final BigDecimal pending;

  public LatokenBalance(
      @JsonProperty("currencyId") String currencyId,
      @JsonProperty("symbol") String symbol,
      @JsonProperty("name") String name,
      @JsonProperty("amount") BigDecimal amount,
      @JsonProperty("available") BigDecimal available,
      @JsonProperty("frozen") BigDecimal frozen,
      @JsonProperty("pending") BigDecimal pending) {

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
  public BigDecimal getAmount() {
    return amount;
  }

  /**
   * Available amount of balance
   *
   * @return
   */
  public BigDecimal getAvailable() {
    return available;
  }

  /**
   * Amount frozen by orders or transactions amount
   *
   * @return
   */
  public BigDecimal getFrozen() {
    return frozen;
  }

  public BigDecimal getPending() {
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
