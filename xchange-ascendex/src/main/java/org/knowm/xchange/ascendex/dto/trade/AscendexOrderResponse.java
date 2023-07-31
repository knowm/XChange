package org.knowm.xchange.ascendex.dto.trade;

import com.fasterxml.jackson.annotation.JsonAlias;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.knowm.xchange.ascendex.dto.enums.AscendexOrderType;

import java.util.Date;
@Data
@NoArgsConstructor
@AllArgsConstructor
public class AscendexOrderResponse {

  private String ac;

  private String accountId;

  private String action;

  private AscendexPlaceOrderInfo info;

  private String status;

  private String message;

  private String reason;

  private String code;

  @Data

  public static class AscendexPlaceOrderInfo {
    private  String id;

    private  String orderId;

    private  String orderType;

    private  String symbol;
    @JsonProperty("timestamp") @JsonAlias({"lastExecTime"})
    private  Date timestamp;
  }
}
