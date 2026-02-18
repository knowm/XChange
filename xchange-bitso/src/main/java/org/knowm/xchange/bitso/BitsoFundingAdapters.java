package org.knowm.xchange.bitso;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import lombok.experimental.UtilityClass;
import org.knowm.xchange.bitso.dto.funding.BitsoDepositDetails;
import org.knowm.xchange.bitso.dto.funding.BitsoFunding;
import org.knowm.xchange.bitso.dto.funding.BitsoWithdrawal;
import org.knowm.xchange.currency.Currency;
import org.knowm.xchange.dto.account.FundingRecord;

/** Adapters for converting Bitso funding DTOs to XChange DTOs */
@UtilityClass
public class BitsoFundingAdapters {

  private static String getIfExists(Map<String, Object> details, String key, String defaultValue) {
    if (details == null) {
      return defaultValue;
    }
    Object value = details.getOrDefault(key, defaultValue);
    if (value == null) {
      return defaultValue;
    }
    return value.toString();
  }

  /** Convert BitsoFunding to FundingRecord */
  public static FundingRecord adaptFundingRecord(BitsoFunding funding) {
    return FundingRecord.builder()
        .address(getIfExists(funding.getDetails(), "receiving_address", funding.getFundingId()))
        .date(funding.getCreatedAt())
        .currency(Currency.getInstance(BitsoAdapters.fromBitsoCurrency(funding.getCurrency())))
        .amount(funding.getAmount())
        .internalId(funding.getFundingId())
        .blockchainTransactionHash(
            getIfExists(funding.getDetails(), "tx_hash", funding.getTxHash()))
        .type(FundingRecord.Type.DEPOSIT)
        .status(adaptFundingStatus(funding.getStatus().name().toLowerCase()))
        .fee(funding.getFee())
        .description(funding.getDetails() != null ? funding.getDetails().toString() : null)
        .build();
  }

  /** Convert BitsoWithdrawal to FundingRecord */
  public static FundingRecord adaptFundingRecord(BitsoWithdrawal withdrawal) {
    return FundingRecord.builder()
        .address(
            getIfExists(withdrawal.getDetails(), "receiving_address", withdrawal.getWithdrawalId()))
        .date(withdrawal.getCreatedAt())
        .currency(Currency.getInstance(BitsoAdapters.fromBitsoCurrency(withdrawal.getCurrency())))
        .amount(withdrawal.getAmount())
        .internalId(withdrawal.getWithdrawalId())
        .blockchainTransactionHash(
            getIfExists(withdrawal.getDetails(), "tx_hash", withdrawal.getTxHash()))
        .type(FundingRecord.Type.WITHDRAWAL)
        .status(adaptFundingStatus(withdrawal.getStatus()))
        .fee(withdrawal.getFee())
        .description(withdrawal.getDetails() != null ? withdrawal.getDetails().toString() : null)
        .build();
  }

  /** Convert list of BitsoFunding to list of FundingRecord */
  public static List<FundingRecord> adaptFundingRecords(List<BitsoFunding> fundings) {
    List<FundingRecord> fundingRecords = new ArrayList<>();
    for (BitsoFunding funding : fundings) {
      fundingRecords.add(adaptFundingRecord(funding));
    }
    return fundingRecords;
  }

  /** Convert list of BitsoWithdrawal to list of FundingRecord */
  public static List<FundingRecord> adaptWithdrawalRecords(List<BitsoWithdrawal> withdrawals) {
    List<FundingRecord> fundingRecords = new ArrayList<>();
    for (BitsoWithdrawal withdrawal : withdrawals) {
      fundingRecords.add(adaptFundingRecord(withdrawal));
    }
    return fundingRecords;
  }

  /** Convert Bitso funding status to XChange funding status */
  public static FundingRecord.Status adaptFundingStatus(String status) {
    if (status == null) return FundingRecord.Status.PROCESSING;

    switch (status.toLowerCase()) {
      case "pending":
        return FundingRecord.Status.PROCESSING;
      case "complete":
        return FundingRecord.Status.COMPLETE;
      case "cancelled":
        return FundingRecord.Status.CANCELLED;
      case "failed":
        return FundingRecord.Status.FAILED;
      default:
        return FundingRecord.Status.PROCESSING;
    }
  }

  /** Get deposit address from BitsoDepositDetails */
  public static String getDepositAddress(BitsoDepositDetails depositDetails) {
    if (depositDetails.getAddress() != null) {
      return depositDetails.getAddress();
    } else if (depositDetails.getClabe() != null) {
      return depositDetails.getClabe();
    } else {
      return null;
    }
  }
}
