package org.knowm.xchange.dsx.dto;

public enum DsxSort {
  SORT_ASCENDING("asc"),
  SORT_DESCENDING("desc");

  private final String sort;

  DsxSort(String sort) {

    this.sort = sort;
  }

  @Override
  public String toString() {

    return sort;
  }
}
