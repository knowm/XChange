package org.knowm.xchange.kucoin.dto.account;

import org.knowm.xchange.dto.account.FundingRecord;
import org.knowm.xchange.dto.account.FundingRecord.Status;

public enum KucoinWalletOperationStatus {
  SUCCESS(FundingRecord.Status.COMPLETE),
  CANCEL(FundingRecord.Status.CANCELLED),
  PENDING(FundingRecord.Status.PROCESSING);
  
  private FundingRecord.Status fundingRecordStatus;
  
  private KucoinWalletOperationStatus(Status fundingRecordStatus) {
    this.fundingRecordStatus = fundingRecordStatus;
  }

  public FundingRecord.Status getFundingRecordStatus() {

    return fundingRecordStatus;
  }
}
