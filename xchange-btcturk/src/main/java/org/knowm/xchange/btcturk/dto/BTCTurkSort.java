package org.knowm.xchange.btcturk.dto;

/**
 * @author mertguner
 */
public enum BTCTurkSort {
  SORT_ASCENDING("asc"),
  SORT_DESCENDING("desc");

  private final String sort;

  BTCTurkSort(String sort) {

    this.sort = sort;
  }

  @Override
  public String toString() {

    return sort;
  }
}
