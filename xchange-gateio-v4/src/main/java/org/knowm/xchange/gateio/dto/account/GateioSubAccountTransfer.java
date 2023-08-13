package org.knowm.xchange.gateio.dto.account;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import java.math.BigDecimal;
import java.time.Instant;
import lombok.Builder;
import lombok.Data;
import lombok.extern.jackson.Jacksonized;
import org.knowm.xchange.currency.Currency;
import org.knowm.xchange.gateio.config.converter.StringToCurrencyConverter;
import org.knowm.xchange.gateio.config.converter.TimestampSecondsToInstantConverter;

@Data
@Builder
@Jacksonized
public class GateioSubAccountTransfer {

  @JsonProperty("uid")
  Integer mainAccountId;

  @JsonProperty("timest")
  @JsonDeserialize(converter = TimestampSecondsToInstantConverter.class)
  private Instant timestamp;

  @JsonProperty("source")
  String source;

  @JsonProperty("client_order_id")
  String clientOrderId;

  @JsonProperty("currency")
  @JsonDeserialize(converter = StringToCurrencyConverter.class)
  Currency currency;

  @JsonProperty("sub_account")
  Integer subAccountId;

  @JsonProperty("direction")
  String direction;

  @JsonProperty("amount")
  BigDecimal amount;

  @JsonProperty("sub_account_type")
  String subAccountType;

}
