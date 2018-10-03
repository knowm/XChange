package org.knowm.xchange.anx.v2.dto.account;

import com.fasterxml.jackson.annotation.JsonProperty;

/** Data object representing a ANX Wallet History Entry */
public final class ANXWalletHistory {

  private final int records;
  private final ANXWalletHistoryEntry[] anxWalletHistoryEntries;
  private final int currentPage;
  private final int maxPage;
  private final int maxResults;

  /**
   * Constructor
   *
   * @param records
   * @param anxWalletHistoryEntries
   */
  public ANXWalletHistory(
      @JsonProperty("records") int records,
      @JsonProperty("result") ANXWalletHistoryEntry[] anxWalletHistoryEntries,
      @JsonProperty("current_page") int currentPage,
      @JsonProperty("max_page") int maxPage,
      @JsonProperty("max_results") int maxResults) {

    this.records = records;
    this.anxWalletHistoryEntries = anxWalletHistoryEntries;
    this.currentPage = currentPage;
    this.maxPage = maxPage;
    this.maxResults = maxResults;
  }

  public int getRecords() {

    return records;
  }

  public ANXWalletHistoryEntry[] getANXWalletHistoryEntries() {

    return anxWalletHistoryEntries;
  }

  public int getCurrentPage() {

    return currentPage;
  }

  public int getMaxPage() {

    return maxPage;
  }

  public int getMaxResults() {

    return maxResults;
  }
}
