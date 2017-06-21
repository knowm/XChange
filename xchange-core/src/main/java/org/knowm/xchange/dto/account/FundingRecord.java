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
   * Amount deposited/withdrawn in given transaction currency
   */
  private final BigDecimal amount;


  /**
   * Transaction id or reference whenever available
   */
  private final String id;


  /**
   * Description of the transaction
   */
  private final String description;


  /**
   * Transaction Type
   */
  private final Type type;

  /**
   * Status of the transaction whenever available (e.g. Open, Completed or any descriptive status of transaction)
   */
  private final String status;

  /**
   * Balance of the associated account after the transaction is performed
   */
  private final BigDecimal balance;

  /**
   * Transaction Fee Amount in given transaction currency
   */
  private final BigDecimal fee;

  /**
   * Constructs a {@link FundingRecord}.
   *
   * @param address Crypto currency address for deposit/withdrawal
   * @param date Date/Time of transaction
   * @param currency The transaction currency
   * @param amount Amount deposited/withdrawn
   * @param id Transaction id or reference whenever available
   * @param type Transaction Type {@link Type}
   * @param status Status of the transaction whenever available (e.g. Open, Completed or any descriptive status of transaction)
   * @param balance Balance of the associated account after the transaction is performed
   * @param fee Transaction Fee Amount
   * @param description Description of the transaction
   */
  public FundingRecord(final String address, final Date date, final Currency currency, final BigDecimal amount, final String id,
                       final Type type, final String status, final BigDecimal balance, final BigDecimal fee,
                       final String description){
    this.address = address;
    this.date = date;
    this.currency = currency;
    this.amount = amount;
    this.id = id;
    this.type = type;
    this.status = status;
    this.balance = balance;
    this.fee = fee;
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
   * @return Amount deposited/withdrawn in given transaction currency
   */
  public BigDecimal getAmount() {
    return amount;
  }

  /**
   * @return Transaction id or reference whenever available
   */
  public String getId() {
    return id;
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
  public String getStatus() {
    return status;
  }

  /**
   * @return Balance of the associated account after the transaction is performed
   */
  public BigDecimal getBalance() {
    return balance;
  }

  /**
   * @return Transaction Fee Amount in given transaction currency
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
    return "FundingRecord{" +
            "address='" + address + '\'' +
            ", date=" + date +
            ", currency='" + currency + '\'' +
            ", amount=" + amount +
            ", id='" + id + '\'' +
            ", description='" + description + '\'' +
            ", type='" + type + '\'' +
            ", status='" + status + '\'' +
            ", balance=" + balance +
            ", fee=" + fee +
            '}';
  }

  /**
   * <p>
   * Enum representing funding transaction type
   * </p>
   */
  public enum Type {
    WITHDRAWAL, DEPOSIT;

    private static final Map<String, Type> fromString = new HashMap<String, Type>();

    static {
      for (Type type : values())
        fromString.put(type.toString(), type);
    }

    public static Type fromString(String ledgerTypeString) {
      return fromString.get(ledgerTypeString.toUpperCase());
    }
  }

}
