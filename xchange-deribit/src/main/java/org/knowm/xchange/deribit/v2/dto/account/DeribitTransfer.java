package org.knowm.xchange.deribit.v2.dto.account;

import com.fasterxml.jackson.annotation.JsonEnumDefaultValue;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import java.math.BigDecimal;
import java.time.Instant;
import lombok.Builder;
import lombok.Data;
import lombok.extern.jackson.Jacksonized;
import org.knowm.xchange.currency.Currency;
import org.knowm.xchange.deribit.v2.config.converter.StringToCurrencyConverter;

@Data
@Builder
@Jacksonized
public class DeribitTransfer {

  @JsonProperty("id")
  private String id;

  @JsonProperty("currency")
  @JsonDeserialize(converter = StringToCurrencyConverter.class)
  private Currency currency;

  @JsonProperty("amount")
  private BigDecimal amount;

  @JsonProperty("direction")
  private Direction direction;

  @JsonProperty("note")
  private String note;

  @JsonProperty("type")
  private Type type;

  @JsonProperty("other_side")
  private String otherSide;

  @JsonProperty("state")
  private State state;

  @JsonProperty("created_timestamp")
  private Instant createdAt;

  @JsonProperty("updated_timestamp")
  private Instant updatedAt;


  public enum State {
    @JsonProperty("prepared")
    PREPARED,

    @JsonProperty("confirmed")
    CONFIRMED,

    @JsonProperty("cancelled")
    CANCELLED,

    @JsonProperty("waiting_for_admin")
    WAITING_FOR_ADMIN,

    @JsonProperty("insufficient_funds")
    INSUFFICIENT_FUNDS,

    @JsonProperty("withdrawal_limit")
    WITHDRAWAL_LIMIT,

    @JsonEnumDefaultValue
    UNKNOWN
  }

  public enum Type {
    @JsonProperty("user")
    USER,

    @JsonProperty("subaccount")
    SUBACCOUNT,

    @JsonEnumDefaultValue
    UNKNOWN
  }

  public enum Direction {
    @JsonProperty("payment")
    PAYMENT,

    @JsonProperty("income")
    INCOME,

    @JsonEnumDefaultValue
    UNKNOWN
  }

}
