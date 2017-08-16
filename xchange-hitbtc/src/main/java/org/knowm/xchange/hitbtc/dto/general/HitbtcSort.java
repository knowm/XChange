package org.knowm.xchange.hitbtc.dto.general;

public enum HitbtcSort {

  SORT_ASCENDING("asc"),
  SORT_DESCENDING("desc");

  private final String sort;

  HitbtcSort(String sort) {

    this.sort = sort;
  }

  @Override
  public String toString() {

    return sort;
  }
}
