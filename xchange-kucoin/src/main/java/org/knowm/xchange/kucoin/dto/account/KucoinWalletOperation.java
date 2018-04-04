package org.knowm.xchange.kucoin.dto.account;

import org.knowm.xchange.dto.account.FundingRecord;
import org.knowm.xchange.dto.account.FundingRecord.Type;

public enum KucoinWalletOperation {
  DEPOSIT(FundingRecord.Type.DEPOSIT),
  WITHDRAW(FundingRecord.Type.WITHDRAWAL);

  private FundingRecord.Type fundingRecordType;

  private KucoinWalletOperation(Type fundingRecordType) {
    this.fundingRecordType = fundingRecordType;
  }

  public static KucoinWalletOperation fromFundingRecordType(FundingRecord.Type fundingRecordType) {
    switch (fundingRecordType) {
      case DEPOSIT:
        return DEPOSIT;
      case WITHDRAWAL:
        return WITHDRAW;
      default:
        throw new RuntimeException("Unsupported FundingRecord.Type " + fundingRecordType);
    }
  }

  public FundingRecord.Type getFundingRecordType() {
    return fundingRecordType;
  }
}
