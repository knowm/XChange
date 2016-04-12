package org.knowm.xchange.coinsetter.dto.financialtransaction;

import java.math.BigDecimal;
import java.util.Date;
import java.util.UUID;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonProperty;

/**
 * A specified financial transaction.
 */
public class CoinsetterFinancialTransaction {

  private final UUID uuid;
  private final UUID customerUuid;
  private final UUID accountUuid;
  private final BigDecimal amount;
  private final String amountDenomination;
  private final BigDecimal originalAmount;
  private final String originalAmountDenomination;
  private final UUID orderId;
  private final String orderNumber;
  private final String referenceNumber;
  private final String transactionCategoryDescription;
  private final String transactionCategoryName;
  private final String transferTypeDescription;
  private final String transferTypeName;
  private final Date createDate;

  /**
   * @param uuid Financial transaction UUID
   * @param customerUuid Customer UUID
   * @param accountUuid Account UUID
   * @param amount Transaction amount
   * @param amountDenomination Currency code of amount
   * @param originalAmount Original amount before exchange into account currency
   * @param originalAmountDenomination Currency code of original transaction
   * @param orderId Order id if an order created this financial transaction (i.e. commission, realized P/L, etc.)
   * @param orderNumber Order # if an order created this financial transaction (i.e. commission, realized P/L, etc.)
   * @param referenceNumber Reference # for external transactions (i.e. wire #, ACH #, etc.)
   * @param transactionCategoryDescription Transaction category description
   * @param transactionCategoryName Transaction category name (i.e. "Deposit", "Withdrawal", "Commission", etc.)
   * @param transferTypeDescription Transfer type description
   * @param transferTypeName Transfer type name
   * @param createDate Date/time of creation (format = "dd/MM/yyyy HH:mm:ss.SSS")
   */
  public CoinsetterFinancialTransaction(@JsonProperty("uuid") UUID uuid, @JsonProperty("customerUuid") UUID customerUuid,
      @JsonProperty("accountUuid") UUID accountUuid, @JsonProperty("amount") BigDecimal amount,
      @JsonProperty("amountDemonination") String amountDenomination, @JsonProperty("originalAmount") BigDecimal originalAmount,
      @JsonProperty("originalAmountdenomination") String originalAmountDenomination, @JsonProperty("orderId") UUID orderId,
      @JsonProperty("orderNumber") String orderNumber, @JsonProperty("referenceNumber") String referenceNumber,
      @JsonProperty("transactionCategoryDescription") String transactionCategoryDescription,
      @JsonProperty("transactionCategoryName") String transactionCategoryName,
      @JsonProperty("transferTypeDescription") String transferTypeDescription, @JsonProperty("transferTypeName") String transferTypeName,
      @JsonProperty("createDate") @JsonFormat(pattern = "dd/MM/yyyy HH:mm:ss.SSS", timezone = "EST", locale = "us") Date createDate) {

    super();
    this.uuid = uuid;
    this.customerUuid = customerUuid;
    this.accountUuid = accountUuid;
    this.amount = amount;
    this.amountDenomination = amountDenomination;
    this.originalAmount = originalAmount;
    this.originalAmountDenomination = originalAmountDenomination;
    this.orderId = orderId;
    this.orderNumber = orderNumber;
    this.referenceNumber = referenceNumber;
    this.transactionCategoryDescription = transactionCategoryDescription;
    this.transactionCategoryName = transactionCategoryName;
    this.transferTypeDescription = transferTypeDescription;
    this.transferTypeName = transferTypeName;
    this.createDate = createDate;
  }

  public UUID getUuid() {

    return uuid;
  }

  public UUID getCustomerUuid() {

    return customerUuid;
  }

  public UUID getAccountUuid() {

    return accountUuid;
  }

  public BigDecimal getAmount() {

    return amount;
  }

  public String getAmountDenomination() {

    return amountDenomination;
  }

  public BigDecimal getOriginalAmount() {

    return originalAmount;
  }

  public String getOriginalAmountDenomination() {

    return originalAmountDenomination;
  }

  public UUID getOrderId() {

    return orderId;
  }

  public String getOrderNumber() {

    return orderNumber;
  }

  public String getReferenceNumber() {

    return referenceNumber;
  }

  public String getTransactionCategoryDescription() {

    return transactionCategoryDescription;
  }

  public String getTransactionCategoryName() {

    return transactionCategoryName;
  }

  public String getTransferTypeDescription() {

    return transferTypeDescription;
  }

  public String getTransferTypeName() {

    return transferTypeName;
  }

  public Date getCreateDate() {

    return createDate;
  }

  @Override
  public String toString() {

    return "CoinsetterFinancialTransaction [uuid=" + uuid + ", customerUuid=" + customerUuid + ", accountUuid=" + accountUuid + ", amount=" + amount
        + ", amountDenomination=" + amountDenomination + ", originalAmount=" + originalAmount + ", originalAmountDenomination="
        + originalAmountDenomination + ", orderId=" + orderId + ", orderNumber=" + orderNumber + ", referenceNumber=" + referenceNumber
        + ", transactionCategoryDescription=" + transactionCategoryDescription + ", transactionCategoryName=" + transactionCategoryName
        + ", transferTypeDescription=" + transferTypeDescription + ", transferTypeName=" + transferTypeName + ", createDate=" + createDate + "]";
  }

}
