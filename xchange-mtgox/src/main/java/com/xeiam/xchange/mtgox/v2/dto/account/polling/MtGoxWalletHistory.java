/**
 * Copyright (C) 2012 - 2013 Xeiam LLC http://xeiam.com
 *
 * Permission is hereby granted, free of charge, to any person obtaining a copy of this software and associated documentation files (the "Software"), to deal in
 * the Software without restriction, including without limitation the rights to use, copy, modify, merge, publish, distribute, sublicense, and/or sell copies of
 * the Software, and to permit persons to whom the Software is furnished to do so, subject to the following conditions:
 *
 * The above copyright notice and this permission notice shall be included in all copies or substantial portions of the Software.
 *
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY, FITNESS
 * FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER LIABILITY, WHETHER
 * IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM, OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE SOFTWARE.
 */
package com.xeiam.xchange.mtgox.v2.dto.account.polling;

import com.fasterxml.jackson.annotation.JsonProperty;

/**
 * Data object representing a MtGox Wallet History Entry
 */
public final class MtGoxWalletHistory {

  private final int records;
  private final MtGoxWalletHistoryEntry[] mtGoxWalletHistoryEntries;
  private final int currentPage;
  private final int maxPage;
  private final int maxResults;

  /**
   * Constructor
   * 
   * @param records
   * @param mtGoxWalletHistoryEntries
   */
  public MtGoxWalletHistory(@JsonProperty("records") int records, @JsonProperty("result") MtGoxWalletHistoryEntry[] mtGoxWalletHistoryEntries, @JsonProperty("current_page") int currentPage,
      @JsonProperty("max_page") int maxPage, @JsonProperty("max_results") int maxResults) {

    this.records = records;
    this.mtGoxWalletHistoryEntries = mtGoxWalletHistoryEntries;
    this.currentPage = currentPage;
    this.maxPage = maxPage;
    this.maxResults = maxResults;
  }

  public int getRecords() {

    return records;
  }

  public MtGoxWalletHistoryEntry[] getMtGoxWalletHistoryEntries() {

    return mtGoxWalletHistoryEntries;
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
