package org.knowm.xchange.binance.dto.trade.futures;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;
import lombok.ToString;

@ToString
@Getter
@JsonInclude(JsonInclude.Include.NON_NULL)
public class BinanceChangeStatus {
  private final boolean success;
  private final int code;
  private final String msg;

  public BinanceChangeStatus(@JsonProperty("code") int code, @JsonProperty("msg") String msg) {
    this.success = code == 200;
    this.code = code;
    this.msg = msg;
  }
}
