package org.knowm.xchange.bibox.dto.account;

import com.fasterxml.jackson.annotation.JsonProperty;

public class BiboxFundsCommandBody {

  /** coin symbol */
  @JsonProperty public final String search;

  /** 0：all，1：in progress，2：completed，3：failed */
  @JsonProperty("filter_type")
  public final int filterType;

  /** page number，start from 1 */
  @JsonProperty public final int page;

  /** how many */
  @JsonProperty public final int size;

  public BiboxFundsCommandBody(String search, int filterType, int page, int size) {
    super();
    this.search = search;
    this.filterType = filterType;
    this.page = page;
    this.size = size;
  }

  public BiboxFundsCommandBody(String coinSymbol) {
    this(coinSymbol, 0, 1, 100);
  }
}
