package org.knowm.xchange.dto.account;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import lombok.Getter;
import lombok.ToString;
import lombok.experimental.SuperBuilder;
import org.knowm.xchange.currency.Currency;

/**
 * DTO representing funding information
 *
 * <p>Funding information contains the detail of deposit/withdrawal transaction for a specific
 * currency
 */
@Getter
@SuperBuilder
@ToString
public class FundingRecord implements Serializable {

  private static final long serialVersionUID = 3788398035845873448L;

  /** Crypto currency address for deposit/withdrawal */
  private final String address;

  /** Crypto currency destination tag for deposit/withdrawal */
  private final String addressTag;

  /** Date/Time of transaction */
  private final Date date;

  /** The transaction currency */
  private final Currency currency;

  /** Amount deposited/withdrawn in given transaction currency (always positive) */
  private final BigDecimal amount;

  /** Internal transaction identifier, specific to the Exchange. */
  private final String internalId;

  /**
   * External Transaction id that identifies the transaction within the public ledger, eg.
   * blockchain transaction hash.
   */
  private final String blockchainTransactionHash;
  /** Transaction Type */
  private final Type type;
  /**
   * Status of the transaction whenever available (e.g. Open, Completed or any descriptive status of
   * transaction)
   */
  private final Status status;
  /** Balance of the associated account after the transaction is performed */
  private final BigDecimal balance;
  /** Transaction Fee Amount in given transaction currency (always positive) */
  private final BigDecimal fee;
  /** Description of the transaction */
  private String description;

  private final String fromWallet;

  private final String toWallet;

  private final String fromSubAccount;

  private final String toSubAccount;

  @Deprecated // for backward compatibility.  Will be removed
  public String getExternalId() {
    return blockchainTransactionHash;
  }

  /** Enum representing funding transaction type */
  public enum Type {
    WITHDRAWAL(false),
    WITHDRAW(false),
    DEPOSIT(true),
    AIRDROP(true),
    /**
     * Used for inflows that are not a regular users deposit and are either different the inflows
     * defined above or their nature could not have been deduced from the exchanges response
     */
    OTHER_INFLOW(true),
    /**
     * Used for outflows that are not a regular users withdrawal and are either different the
     * outflows defined above or their nature could not have been deduced from the exchanges
     * response
     */
    OTHER_OUTFLOW(false),
    INTEREST(false),
    DELIVERY(false),
    SETTLEMENT(false),
    INTERNAL_WALLET_TRANSFER(false),
    INTERNAL_SUB_ACCOUNT_TRANSFER(false),

    /** Used for transfers between exchanges accounts */
    INTERNAL_WITHDRAWAL(false),
    INTERNAL_WITHDRAW(false),

    /** Used for transfers between exchanges accounts */
    INTERNAL_DEPOSIT(true),

    /** Used for realised losses from derivatives */
    REALISED_LOSS(false),

    /** Used for realised profits from derivatives */
    REALISED_PROFIT(true),

    TRADE(false);

    private static final Map<String, Type> fromString = new HashMap<>();

    static {
      for (Type type : values()) fromString.put(type.toString(), type);
    }

    private final boolean inflow;

    Type(final boolean inflow) {
      this.inflow = inflow;
    }

    public static Type fromString(String ledgerTypeString) {
      return fromString.get(ledgerTypeString.toUpperCase());
    }

    public boolean isInflowing() {
      return this.inflow;
    }

    public boolean isOutflowing() {
      return !this.inflow;
    }
  }

  public enum Status {
    /**
     * The user has requested the withdrawal or deposit, or the exchange has detected an initiated
     * deposit, but the exchange still has to fully process the funding. The funds are not available
     * to the user. The funding request may possibly still be cancelled though.
     */
    PROCESSING(
        "WAIT CONFIRMATION",
        "EMAIL CONFIRMATION",
        "EMAIL SENT",
        "AWAITING APPROVAL",
        "VERIFYING",
        "PENDING_APPROVAL",
        "PENDING",
        "SECURITY_CHECK",
        "TO_BE_CONFIRMED",
        "PROCESSING",
        "PENDING_TO_BE_CREDITED_TO_FUNDING_POOL"),

    /**
     * The exchange has processed the transfer fully and successfully. The funding typically cannot
     * be cancelled any more. For withdrawals, the funds are gone from the exchange, though they may
     * have not reached their destination yet. For deposits, the funds are available to the user.
     */
    COMPLETE("COMPLETED", "SUCCESS","BLOCKCHAIN_CONFIRMED","CREDITED_TO_FUNDING_POOL_SUCCESSFULLY"),

    /** The transfer was cancelled either by the user or by the exchange. */
    CANCELLED("REVOKED", "CANCEL", "REFUND", "CANCEL_BY_USER"),

    /**
     * The transfer has failed for any reason other than user cancellation after it was initiated
     * and before it was successfully processed. For withdrawals, the funds are available to the
     * user again.
     */
    FAILED("FAILURE", "FAILED", "REJECT", "FAIL","UNKNOWN","DEPOSIT_FAILED"),
    ;

    private static final Map<String, Status> fromString = new HashMap<>();

    static {
      for (final Status status : values()) {
        final String[] statusArray = status.statusArray;
        if (statusArray != null) {
          for (final String statusStr : statusArray) {
            fromString.put(statusStr, status);
          }
        }
        fromString.put(status.toString(), status);
      }
    }

    private String[] statusArray;

    Status(String... statusArray) {
      this.statusArray = statusArray;
    }

    public static Status resolveStatus(String str) throws IllegalArgumentException{
      if (str == null) {
        return null;
      }
      return fromString.get(str.toUpperCase());
    }
  }
}
