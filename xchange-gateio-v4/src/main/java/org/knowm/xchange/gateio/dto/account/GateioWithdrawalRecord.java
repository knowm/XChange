package org.knowm.xchange.gateio.dto.account;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import java.math.BigDecimal;
import java.time.Instant;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.Getter;
import lombok.extern.jackson.Jacksonized;
import org.knowm.xchange.currency.Currency;
import org.knowm.xchange.gateio.config.converter.StringToCurrencyConverter;
import org.knowm.xchange.gateio.config.converter.TimestampSecondsToInstantConverter;

@Data
@Builder
@Jacksonized
public class GateioWithdrawalRecord {

  @JsonProperty("id")
  String id;

  @JsonProperty("txid")
  String txId;

  @JsonProperty("withdraw_order_id")
  String clientRecordId;

  @JsonProperty("timestamp")
  @JsonDeserialize(converter = TimestampSecondsToInstantConverter.class)
  Instant createdAt;

  @JsonProperty("amount")
  BigDecimal amount;

  @JsonProperty("currency")
  @JsonDeserialize(converter = StringToCurrencyConverter.class)
  Currency currency;

  @JsonProperty("address")
  String address;

  @JsonProperty("memo")
  String tag;

  @JsonProperty("status")
  Status status;

  @JsonProperty("chain")
  String chain;

  @JsonProperty("fee")
  BigDecimal fee;


  @Getter
  @AllArgsConstructor
  public static enum Status {
    DONE("done"),
    CANCEL("cancelled"),
    REQUEST("requesting"),
    MANUAL("pending manual approval"),
    BCODE("GateCode operation"),
    EXTPEND("pending confirm after sending"),
    FAIL("pending confirm when fail"),
    INVALID("invalid order"),
    VERIFY("verifying"),
    PROCES("processing"),
    PEND("pending"),
    DMOVE("required manual approval"),
    SPLITPEND("the order is automatically split due to large amount");

    private final String description;

  }

}
