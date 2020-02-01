package org.knowm.xchange.okcoin.v3.dto.trade;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;
import com.fasterxml.jackson.annotation.JsonProperty;
import java.math.BigDecimal;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
@JsonInclude(Include.NON_NULL)
public class FundsTransferRequest {

  private String currency;
  private BigDecimal amount;
  private String from;
  private String to;

  @JsonProperty("sub_account")
  private String subAccount;

  @JsonProperty("instrument_id")
  private String instrumentId;
}
