package com.xeiam.xchange.quoine.dto.trade;

import java.util.Arrays;

import com.fasterxml.jackson.annotation.JsonProperty;

/**
 * @author timmolter
 */
public final class QuoineOrdersList {

  private final Model[] models;
  private final Integer currentPage;
  private final Integer totalPages;

  /**
   * Constructor
   *
   * @param models
   * @param currentPage
   * @param totalPages
   */
  public QuoineOrdersList(@JsonProperty("models") Model[] models, @JsonProperty("current_page") Integer currentPage,
      @JsonProperty("total_pages") Integer totalPages) {
    this.models = models;
    this.currentPage = currentPage;
    this.totalPages = totalPages;
  }

  public Model[] getModels() {
    return models;
  }

  public Integer getCurrentPage() {
    return currentPage;
  }

  public Integer getTotalPages() {
    return totalPages;
  }

  @Override
  public String toString() {
    return "QuoineOrdersList [models=" + Arrays.toString(models) + ", currentPage=" + currentPage + ", totalPages=" + totalPages + "]";
  }

}
