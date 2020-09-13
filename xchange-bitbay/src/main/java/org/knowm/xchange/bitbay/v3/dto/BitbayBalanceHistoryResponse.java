package org.knowm.xchange.bitbay.v3.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import java.util.List;
import lombok.Getter;
import lombok.ToString;

@Getter
@ToString(callSuper = true)
public class BitbayBalanceHistoryResponse extends BitbayBaseResponse {

  private final List<BitbayBalanceHistoryEntry> items;
  private final boolean hasNextPage;

  public BitbayBalanceHistoryResponse(
      @JsonProperty("status") String status,
      @JsonProperty("errors") List<String> errors,
      @JsonProperty("items") List<BitbayBalanceHistoryEntry> items,
      @JsonProperty("hasNextPage") boolean hasNextPage) {
    super(status, errors);
    this.items = items;
    this.hasNextPage = hasNextPage;
  }
}
