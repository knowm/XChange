package org.knowm.xchange.lgo.dto.product;

import com.fasterxml.jackson.annotation.JsonProperty;

public final class LgoProduct {

  private final String id;
  private final LgoProductTotal total;
  private final LgoProductCurrency base;
  private final LgoProductCurrency quote;

  public LgoProduct(
      @JsonProperty("id") String id,
      @JsonProperty("total") LgoProductTotal total,
      @JsonProperty("base") LgoProductCurrency base,
      @JsonProperty("quote") LgoProductCurrency quote) {
    this.id = id;
    this.total = total;
    this.base = base;
    this.quote = quote;
  }

  public String getId() {
    return id;
  }

  public LgoProductCurrency getBase() {
    return base;
  }

  public LgoProductCurrency getQuote() {
    return quote;
  }

  public LgoProductTotal getTotal() {
    return total;
  }
}
