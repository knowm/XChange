package org.knowm.xchange.binance.dto.account;

import com.fasterxml.jackson.annotation.JsonProperty;
import java.util.List;
import lombok.Data;

@Data
public final class BinanceFiatOrdersResponse {

  @JsonProperty("code")
  private String code;

  @JsonProperty("message")
  private String message;

  @JsonProperty("data")
  private List<BinanceFiatOrder> data;

  @JsonProperty("total")
  private Integer total;

  @JsonProperty("success")
  private Boolean success;
}
