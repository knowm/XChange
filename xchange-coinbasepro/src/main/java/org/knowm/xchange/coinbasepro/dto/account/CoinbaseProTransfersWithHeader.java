package org.knowm.xchange.coinbasepro.dto.account;

import java.util.ArrayList;
import java.util.List;
import org.knowm.xchange.dto.account.FundingRecord;

public class CoinbaseProTransfersWithHeader {
  List<FundingRecord> fundingRecords = new ArrayList<>();
  String cbAfter;
  String cbBefore;

  public List<FundingRecord> getFundingRecords() {
    return fundingRecords;
  }

  public void setFundingRecords(List<FundingRecord> fundingRecords) {
    this.fundingRecords = fundingRecords;
  }

  public String getCbAfter() {
    return cbAfter;
  }

  public void setCbAfter(String cbAfter) {
    this.cbAfter = cbAfter;
  }

  public String getCbBefore() {
    return cbBefore;
  }

  public void setCbBefore(String cbBefore) {
    this.cbBefore = cbBefore;
  }
}
