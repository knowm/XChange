package org.knowm.xchange.bitso.dto.account;

import java.math.BigDecimal;
import java.util.List;
import lombok.Builder;
import lombok.Value;
import lombok.extern.jackson.Jacksonized;

/**
 * Bitso Balance DTO for API v3
 *
 * @see <a href="https://docs.bitso.com/bitso-api/docs/get-account-balance">Get Account Balance</a>
 * @author Matija Mazi
 */
@Value
@Builder
@Jacksonized
public class BitsoBalance {

  /** List of currency balances */
  private final List<CurrencyBalance> balances;

  /** Individual currency balance */
  @Value
  @Builder
  @Jacksonized
  public static class CurrencyBalance {

    /** The currency in which the balances are specified */
    private final String currency;

    /** The total balance for the given currency */
    private final BigDecimal total;

    /** The balance locked away in open orders for the given currency */
    private final BigDecimal locked;

    /** The balance available for use in the given currency */
    private final BigDecimal available;

    /** The currency balance for deposits awaiting confirmation */
    private final BigDecimal pendingDeposit;

    /** The currency balance for withdrawals awaiting completion */
    private final BigDecimal pendingWithdrawal;
  }
}
