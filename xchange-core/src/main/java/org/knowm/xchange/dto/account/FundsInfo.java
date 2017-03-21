package org.knowm.xchange.dto.account;

import java.util.List;

public final class FundsInfo {
  private final List<FundsRecord> fundsRecordList;

  public FundsInfo(List<FundsRecord> fundsRecordList){
    this.fundsRecordList = fundsRecordList;
  }

  public List<FundsRecord> getFundsRecordList(){
    return fundsRecordList;
  }

  @Override
  public String toString() {
    return "FundsInfo [fundsRecordList=" + fundsRecordList + "]";
  }
}
