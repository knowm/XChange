package org.knowm.xchange.okcoin.v3.dto.trade;

import com.fasterxml.jackson.annotation.JsonProperty;
import java.math.BigDecimal;
import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@EqualsAndHashCode(callSuper = false)
public class FundsTransferResponse extends OkexResponse {

  // {"result":true,"amount":"0.01004973","from":"6","currency":"BTC","transfer_id":"49723149","to":"1"}
  private BigDecimal amount;
  private String from;
  private String currency;

  @JsonProperty("transfer_id")
  private String transferId;

  private String to;
}
