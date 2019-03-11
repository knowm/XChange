package org.knowm.xchange.itbit.v1.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import java.util.List;

public class ItBitFundingHistoryResponse {
  public final List<ItBitFunding> fundingHistory;
  public final int totalNumberOfRecords;
  public final int currentPageNumber;
  public final int recordsPerPage;
  public final String requestTime;

  public ItBitFundingHistoryResponse(
      @JsonProperty("fundingHistory") List<ItBitFunding> fundingHistory,
      @JsonProperty("totalNumberOfRecords") int totalNumberOfRecords,
      @JsonProperty("currentPageNumber") int currentPageNumber,
      @JsonProperty("recordsPerPage") int recordsPerPage,
      @JsonProperty("requestTime") String requestTime) {
    this.fundingHistory = fundingHistory;
    this.totalNumberOfRecords = totalNumberOfRecords;
    this.currentPageNumber = currentPageNumber;
    this.recordsPerPage = recordsPerPage;
    this.requestTime = requestTime;
  }

  @Override
  public String toString() {
    return "ItBitFunding{"
        + "fundingHistory="
        + fundingHistory
        + ", totalNumberOfRecords="
        + totalNumberOfRecords
        + ", currentPageNumber="
        + currentPageNumber
        + ", recordsPerPage="
        + recordsPerPage
        + ", requestTime='"
        + requestTime
        + '\''
        + '}';
  }
}
