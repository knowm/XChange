package org.knowm.xchange.coinbasepro.dto.account;

import java.util.List;
import lombok.Getter;
import lombok.ToString;
import org.knowm.xchange.dto.account.FundingRecord;

@Getter
@ToString
public class CoinbaseProTransfersWithHeader {

  private final List<FundingRecord> fundingRecords;
  private final String cbAfter;
  private final String cbBefore;

  public CoinbaseProTransfersWithHeader(
      List<FundingRecord> fundingRecords,
      String cbAfter,
      String cbBefore
  ) {
    this.fundingRecords = fundingRecords;
    this.cbAfter = cbAfter;
    this.cbBefore = cbBefore;
  }

}
