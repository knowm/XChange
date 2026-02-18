package org.knowm.xchange.kucoin.dto.response;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import java.util.List;
import lombok.Data;

/** DTO for Kucoin Earn Holdings paginated response */
@Data
@JsonIgnoreProperties(ignoreUnknown = true)
public class KucoinEarnHoldingsResponse {

  @JsonProperty("totalNum")
  private Integer totalNum;

  @JsonProperty("totalPage")
  private Integer totalPage;

  @JsonProperty("currentPage")
  private Integer currentPage;

  @JsonProperty("pageSize")
  private Integer pageSize;

  @JsonProperty("items")
  private List<KucoinEarnHolding> items;
}
