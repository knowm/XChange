package org.knowm.xchange.dto.account;

import java.math.BigDecimal;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import org.knowm.xchange.currency.Currency;

/**
 * <p>
 * DTO representing funding information
 * </p>
 * <p>
 * Funding information contains the detail of deposit/withdrawal transaction for a specific currency
 * </p>
 */
public final class FundingRecord {

  /**
   * Crypto currency address for deposit/withdrawal
   */
  private final String address;

  /**
   * Date/Time of transaction
   */
  private final Date date;

  /**
   * The transaction currency
   */
  private final Currency currency;

  /**
   * Amount deposited/withdrawn in given transaction currency (always positive)
   */
  private final BigDecimal amount;

  /**
   * Internal transaction identifier, specific to the Exchange.
   */
  private final String internalId;

  /**
   * External Transaction id that identifies the transaction within the public ledger, eg. blockchain transaction hash.
   */
  private final String externalId;

  /**
   * Description of the transaction
   */
  private String description;

  /**
   * Transaction Type
   */
  private final Type type;

  /**
   * Status of the transaction whenever available (e.g. Open, Completed or any descriptive status of transaction)
   */
  private final Status status;

  /**
   * Balance of the associated account after the transaction is performed
   */
  private final BigDecimal balance;

  /**
   * Transaction Fee Amount in given transaction currency (always positive)
   */
  private final BigDecimal fee;

  /**
   * Constructs a {@link FundingRecord}.
   *
   * @param address Crypto currency address for deposit/withdrawal
   * @param date Date/Time of transaction
   * @param currency The transaction currency
   * @param amount Amount deposited/withdrawn (always positive)
   * @param internalId Internal transaction identifier, specific to the Exchange
   * @param externalId External Transaction id that identifies the transaction within the public ledger, eg. blockchain transaction hash
   * @param type Transaction Type {@link Type}
   * @param status Status of the transaction whenever available (e.g. Pending, Completed or any descriptive status of transaction). Will be naively converted to Status enum if possible, or else be prefixed to description.
   * @param balance Balance of the associated account after the transaction is performed
   * @param fee Transaction Fee Amount (always positive)
   * @param description Description of the transaction. It is a good idea to put here any extra info sent back from the exchange that doesn't fit elsewhere so users can still access it.
   *
   * @deprecated Use the constructor with enum status parameter.
   */
  @Deprecated
  public FundingRecord(final String address, final Date date, final Currency currency, final BigDecimal amount,
                       final String internalId, final String externalId,
                       final Type type, final String status, final BigDecimal balance, final BigDecimal fee,
                       final String description){
    this(address, date, currency, amount, internalId, externalId, type, Status.resolveStatus(status), balance, fee, description);
    if (this.status == null && status != null) {
      this.description = this.description == null || this.description.isEmpty()
          ? status
          : status + ": " + this.description;
    }
  }

  /**
   * Constructs a {@link FundingRecord}.
   *
   * @param address Crypto currency address for deposit/withdrawal
   * @param date Date/Time of transaction
   * @param currency The transaction currency
   * @param amount Amount deposited/withdrawn (always positive)
   * @param internalId Internal transaction identifier, specific to the Exchange
   * @param externalId External Transaction id that identifies the transaction within the public ledger, eg. blockchain transaction hash
   * @param type Transaction Type {@link Type}
   * @param status Status of the transaction whenever available
   * @param balance Balance of the associated account after the transaction is performed
   * @param fee Transaction Fee Amount (always positive)
   * @param description Description of the transaction. It is a good idea to put here any extra info sent back from the exchange that doesn't fit elsewhere so users can still access it.
   */
  public FundingRecord(final String address, final Date date, final Currency currency, final BigDecimal amount,
                       final String internalId, final String externalId,
                       final Type type, final Status status, final BigDecimal balance, final BigDecimal fee,
                       final String description){
    this.address = address;
    this.date = date;
    this.currency = currency;
    this.amount = amount == null ? null : amount.abs();
    this.internalId = internalId;
    this.externalId = externalId;
    this.type = type;
    this.status = status;
    this.balance = balance;
    this.fee = fee == null ? null : fee.abs();
    this.description = description;
  }

  /**
   * @return Crypto currency address
   */
  public String getAddress() {
    return address;
  }

  /**
   * @return Date/Time of transaction
   */
  public Date getDate() {
    return date;
  }

  /**
   * @return The transaction currency
   */
  public Currency getCurrency() {
    return currency;
  }

  /**
   * @return Amount deposited/withdrawn in given transaction currency (always positive)
   */
  public BigDecimal getAmount() {
    return amount;
  }

  /**
   * @return Internal transaction identifier, specific to the Exchange.
   */
  public String getInternalId() {
    return internalId;
  }

  /**
   * @return External Transaction id that identifies the transaction within the public ledger, eg. blockchain transaction hash.
   */
  public String getExternalId() {
    return externalId;
  }

  /**
   * @return Transaction Type {@link Type}
   */
  public Type getType() {
    return type;
  }

  /**
   * @return Status of the transaction whenever available (e.g. Open, Completed or any descriptive status of transaction)
   */
  public Status getStatus() {
    return status;
  }

  /**
   * @return Balance of the associated account after the transaction is performed
   */
  public BigDecimal getBalance() {
    return balance;
  }

  /**
   * @return Transaction Fee Amount in given transaction currency (always positive)
   */
  public BigDecimal getFee() {
    return fee;
  }

  /**
   * @return Description of the transaction
   */
  public String getDescription() {
    return description;
  }

  @Override
  public String toString() {
    return String.format("FundingRecord{address='%s', date=%s, currency=%s, amount=%s, internalId=%s, externalId=%s, description='%s', type=%s, status=%s, balance=%s, fee=%s}",
        address, date, currency, amount, internalId, externalId, description, type, status, balance, fee);
  }

  /**
   * <p>
   * Enum representing funding transaction type
   * </p>
   */
  public enum Type {
    WITHDRAWAL, DEPOSIT;

    private static final Map<String, Type> fromString = new HashMap<>();

    static {
      for (Type type : values())
        fromString.put(type.toString(), type);
    }

    public static Type fromString(String ledgerTypeString) {
      return fromString.get(ledgerTypeString.toUpperCase());
    }
  }

  public enum Status {
    /** The user has requested the withdrawal or deposit, or the exchange has detected an initiated deposit,
     * but the exchange still has to fully process the funding.
     * The funds are not available to the user. The funding request may possibly still be cancelled though. */
    PROCESSING("WAIT CONFIRMATION","EMAIL CONFIRMATION","EMAIL SENT","AWAITING APPROVAL","VERIFYING","PENDING_APPROVAL","PENDING"),

    /** The exchange has processed the transfer fully and successfully.
     * The funding typically cannot be cancelled any more.
     * For withdrawals, the funds are gone from the exchange, though they may have not reached their destination yet.
     * For deposits, the funds are available to the user. */
    COMPLETE("COMPLETED"),

    /** The transfer was cancelled either by the user or by the exchange. */
    CANCELLED("REVOKED","CANCEL","REFUND"),

    /** The transfer has failed for any reason other than user cancellation after it was initiated and before it was successfully processed.
     * For withdrawals, the funds are available to the user again. */
    FAILED("FAILURE"),
    ;

    private String[] statusArray;

    private static final Map<String, Status> fromString = new HashMap<String, Status>();

    static {
      for (final Status status : values()){
        final String[] statusArray = status.statusArray;
        if (statusArray != null){
          for (final String statusStr : statusArray){
            fromString.put(statusStr, status);
          }
        }
        fromString.put(status.toString(), status);
      }
    }

    Status(String... statusArray){
      this.statusArray = statusArray;
    }

    public static Status resolveStatus(String str) {
      if (str == null) {
        return null;
      }
      return fromString.get(str.toUpperCase());
    }

  }
}
