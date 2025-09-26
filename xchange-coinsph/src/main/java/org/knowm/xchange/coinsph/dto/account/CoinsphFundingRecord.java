package org.knowm.xchange.coinsph.dto.account;

import java.math.BigDecimal;
import java.util.Date;
import lombok.Getter;
import lombok.ToString;
import org.apache.commons.lang3.StringUtils;

/**
 * Represents a unified funding record (deposit or withdrawal) from Coins.ph API Used for adapting
 * to XChange's FundingRecord
 */
@Getter
@ToString
public class CoinsphFundingRecord {

  public enum Type {
    DEPOSIT,
    WITHDRAWAL,
    FIAT_DEPOSIT,
    FIAT_WITHDRAWAL
  }

  private final String id;
  private final Type type;
  private final String currency;
  private final BigDecimal amount;
  private final String address;
  private final String addressTag;
  private final Date timestamp;
  private final String txId;
  private final String description;
  private final int status;
  private final BigDecimal fee;

  // Constructor for deposit records
  public CoinsphFundingRecord(CoinsphDepositRecord depositRecord) {
    this.id = depositRecord.getId();
    this.type = Type.DEPOSIT;
    this.currency = depositRecord.getCoin();
    this.amount = depositRecord.getAmount();
    this.address = depositRecord.getAddress();
    this.addressTag = depositRecord.getAddressTag();
    this.timestamp = new Date(depositRecord.getInsertTime());
    this.txId = depositRecord.getTxId();
    this.description = "Deposit via " + depositRecord.getNetwork();
    this.status = depositRecord.getStatus();
    this.fee = BigDecimal.ZERO; // Deposits typically don't have fees
  }

  // Constructor for withdrawal records
  public CoinsphFundingRecord(CoinsphWithdrawalRecord withdrawalRecord) {
    this.id = withdrawalRecord.getId();
    this.type = Type.WITHDRAWAL;
    this.currency = withdrawalRecord.getCoin();
    this.amount = withdrawalRecord.getAmount();
    this.address = withdrawalRecord.getAddress();
    this.addressTag = withdrawalRecord.getAddressTag();
    this.timestamp = new Date(withdrawalRecord.getApplyTime());
    this.txId = withdrawalRecord.getTxId();
    this.description =
        withdrawalRecord.getInfo() != null && !withdrawalRecord.getInfo().isEmpty()
            ? withdrawalRecord.getInfo()
            : "Withdrawal via " + withdrawalRecord.getNetwork();
    this.status = withdrawalRecord.getStatus();
    this.fee = withdrawalRecord.getTransactionFee();
  }

  // Constructor for fiat history records
  public CoinsphFundingRecord(CoinsphFiatHistory fiatHistory) {
    this.id =
        fiatHistory.getInternalOrderId() != null
            ? fiatHistory.getInternalOrderId()
            : fiatHistory.getExternalOrderId();

    // Determine type based on transactionType: 1 = cash in (deposit), -1 = cash out (withdrawal)
    this.type =
        fiatHistory.getTransactionType() != null && fiatHistory.getTransactionType() == 1
            ? Type.FIAT_DEPOSIT
            : Type.FIAT_WITHDRAWAL;

    this.currency = fiatHistory.getFiatCurrency();
    this.amount = fiatHistory.getFiatAmount();
    this.timestamp =
        fiatHistory.getCreatedAt() != null
            ? Date.from(fiatHistory.getCreatedAt())
            : (fiatHistory.getCompletedTime() != null
                ? Date.from(fiatHistory.getCompletedTime())
                : new Date());
    this.txId = fiatHistory.getPaymentOrderId();

    // Build description from available information
    StringBuilder descBuilder = new StringBuilder();
    if (fiatHistory.getTransactionType() != null && fiatHistory.getTransactionType() == 1) {
      descBuilder.append("Fiat Cash In");
    } else {
      descBuilder.append("Fiat Cash Out");
    }

    if (fiatHistory.getTransactionChannelName() != null) {
      descBuilder.append(" via ").append(fiatHistory.getTransactionChannelName());
    } else if (fiatHistory.getTransactionChannel() != null) {
      descBuilder.append(" via ").append(fiatHistory.getTransactionChannel());
    }

    if (!StringUtils.isEmpty(fiatHistory.getErrorCode())) {
      descBuilder.append(" Error: ").append(fiatHistory.getErrorMessage());
    }

    this.description = descBuilder.toString();

    // Map status string to integer (simplified mapping)
    this.status = getFiatStatusFromString(fiatHistory.getStatus());

    // Calculate total fees
    BigDecimal totalFee = BigDecimal.ZERO;
    if (fiatHistory.getChannelFee() != null) {
      totalFee = totalFee.add(fiatHistory.getChannelFee());
    }
    if (fiatHistory.getPlatformFee() != null) {
      totalFee = totalFee.add(fiatHistory.getPlatformFee());
    }
    this.fee = totalFee;

    if (fiatHistory.getOrderExtendedMap() != null
        && !StringUtils.isEmpty(fiatHistory.getOrderExtendedMap().getTfrName())) {
      this.address = fiatHistory.getOrderExtendedMap().getTfrName();
      this.addressTag = fiatHistory.getOrderExtendedMap().getTfrAcctNo();
    } else {
      this.address = null;
      this.addressTag = null;
    }
  }

  private static int getFiatStatusFromString(String status) {
    switch (status.toUpperCase()) {
      case "PENDING":
        return 0;
      case "SUCCEEDED":
        return 1;
      case "FAILED":
      case "CANCEL":
        return 2;
      default:
        return 0; // Unknown status
    }
  }
}
