package org.knowm.xchange.hitbtc.v2.dto;

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