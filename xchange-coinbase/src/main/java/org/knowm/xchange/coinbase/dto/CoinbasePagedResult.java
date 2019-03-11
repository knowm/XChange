package org.knowm.xchange.coinbase.dto;

/** @author jamespedwards42 */
public abstract class CoinbasePagedResult {

  private final int totalCount;
  private final int numPages;
  private final int currentPage;

  protected CoinbasePagedResult(int totalCount, final int numPages, final int currentPage) {

    this.totalCount = totalCount;
    this.numPages = numPages;
    this.currentPage = currentPage;
  }

  public int getTotalCount() {

    return totalCount;
  }

  public int getNumPages() {

    return numPages;
  }

  public int getCurrentPage() {

    return currentPage;
  }

  @Override
  public String toString() {

    return "CoinbasePagedResult [totalCount="
        + totalCount
        + ", numPages="
        + numPages
        + ", currentPage="
        + currentPage
        + "]";
  }
}
