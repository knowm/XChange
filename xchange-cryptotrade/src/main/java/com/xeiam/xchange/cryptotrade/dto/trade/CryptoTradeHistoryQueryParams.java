package com.xeiam.xchange.cryptotrade.dto.trade;

import com.xeiam.xchange.cryptotrade.CryptoTradeUtils;
import com.xeiam.xchange.currency.CurrencyPair;

public class CryptoTradeHistoryQueryParams {

  private final Long startId;
  private final Long endId;
  private final Long startDate;
  private final Long endDate;
  private final Integer count;
  private final CryptoTradeOrdering ordering;
  private final String currencyPair;

  private CryptoTradeHistoryQueryParams(Long startId, Long endId, Long startDate, Long endDate, Integer count, CryptoTradeOrdering ordering,
      String currencyPair) {

    this.startId = startId;
    this.endId = endId;
    this.startDate = startDate;
    this.endDate = endDate;
    this.count = count;
    this.ordering = ordering;
    this.currencyPair = currencyPair;
  }

  public Long getStartId() {

    return startId;
  }

  public Long getEndId() {

    return endId;
  }

  public Long getStartDate() {

    return startDate;
  }

  public Long getEndDate() {

    return endDate;
  }

  public Integer getCount() {

    return count;
  }

  public CryptoTradeOrdering getOrdering() {

    return ordering;
  }

  public String getCurrencyPair() {

    return currencyPair;
  }

  @Override
  public String toString() {

    return "CryptoTradeHistoryQueryParams [startId=" + startId + ", endId=" + endId + ", startDate=" + startDate + ", endDate=" + endDate
        + ", count=" + count + ", ordering=" + ordering + ", currencyPair=" + currencyPair + "]";
  }

  public static CryptoTradeQueryParamsBuilder getQueryParamsBuilder() {

    return new CryptoTradeQueryParamsBuilder();
  }

  public static class CryptoTradeQueryParamsBuilder {

    private Long startId;
    private Long endId;
    private Long startDate;
    private Long endDate;
    private Integer count;
    private CryptoTradeOrdering ordering;
    private String currencyPair;

    private CryptoTradeQueryParamsBuilder() {

    }

    public CryptoTradeHistoryQueryParams build() {

      return new CryptoTradeHistoryQueryParams(startId, endId, startDate, endDate, count, ordering, currencyPair);
    }

    public Long getStartId() {

      return startId;
    }

    public CryptoTradeQueryParamsBuilder withStartId(Long startId) {

      this.startId = startId;
      return this;
    }

    public Long getEndId() {

      return endId;
    }

    public CryptoTradeQueryParamsBuilder withEndId(Long endId) {

      this.endId = endId;
      return this;
    }

    public Long getStartDate() {

      return startDate;
    }

    public CryptoTradeQueryParamsBuilder withStartDate(Long startDate) {

      this.startDate = startDate;
      return this;
    }

    public Long getEndDate() {

      return endDate;
    }

    public CryptoTradeQueryParamsBuilder withEndDate(Long endDate) {

      this.endDate = endDate;
      return this;
    }

    public Integer getCount() {

      return count;
    }

    public CryptoTradeQueryParamsBuilder withCount(Integer count) {

      this.count = count;
      return this;
    }

    public CryptoTradeOrdering getOrdering() {

      return ordering;
    }

    public CryptoTradeQueryParamsBuilder withOrdering(CryptoTradeOrdering ordering) {

      this.ordering = ordering;
      return this;
    }

    public String getCurrencyPair() {

      return currencyPair;
    }

    public CryptoTradeQueryParamsBuilder withCurrencyPair(String currencyPair) {

      this.currencyPair = currencyPair;
      return this;
    }

    public CryptoTradeQueryParamsBuilder withCurrencyPair(CurrencyPair currencyPair) {

      this.currencyPair = CryptoTradeUtils.getCryptoTradeCurrencyPair(currencyPair);
      return this;
    }

    public CryptoTradeQueryParamsBuilder withCurrencyPair(String tradeCurrency, String priceCurrency) {

      this.currencyPair = CryptoTradeUtils.getCryptoTradeCurrencyPair(tradeCurrency, priceCurrency);
      return this;
    }

    @Override
    public String toString() {

      return "CryptoTradeQueryParamsBuilder [startId=" + startId + ", endId=" + endId + ", startDate=" + startDate + ", endDate=" + endDate
          + ", count=" + count + ", ordering=" + ordering + ", currencyPair=" + currencyPair + "]";
    }

  }
}
