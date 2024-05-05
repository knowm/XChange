package org.knowm.xchange.gateio.dto.account;

import com.fasterxml.jackson.annotation.JsonProperty;
import java.math.BigDecimal;
import lombok.Builder;
import lombok.Data;
import lombok.extern.jackson.Jacksonized;
import org.knowm.xchange.currency.Currency;

@Data
@Builder
@Jacksonized
public class GateioWithdrawalRequest {

  @JsonProperty("withdraw_order_id")
  String clientRecordId;

  @JsonProperty("amount")
  BigDecimal amount;

  @JsonProperty("currency")
  Currency currency;

  @JsonProperty("address")
  String address;

  @JsonProperty("memo")
  String tag;

  @JsonProperty("chain")
  String chain;

}
