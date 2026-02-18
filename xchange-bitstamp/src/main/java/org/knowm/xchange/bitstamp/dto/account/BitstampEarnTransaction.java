package org.knowm.xchange.bitstamp.dto.account;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import java.math.BigDecimal;
import java.util.Date;
import lombok.Builder;
import lombok.Getter;
import lombok.ToString;
import lombok.extern.jackson.Jacksonized;
import org.knowm.xchange.bitstamp.config.converter.StringToCurrencyConverter;
import org.knowm.xchange.bitstamp.config.converter.StringToDateConverter;
import org.knowm.xchange.currency.Currency;

@Getter
@ToString
@Builder
@Jacksonized
public class BitstampEarnTransaction {

  @JsonProperty("datetime")
  @JsonDeserialize(converter = StringToDateConverter.class)
  private final Date datetime;

  @JsonProperty("type")
  private final TransactionType type;

  @JsonProperty("amount")
  private final BigDecimal amount;

  @JsonProperty("currency")
  @JsonDeserialize(converter = StringToCurrencyConverter.class)
  private final Currency currency;

  @JsonProperty("value")
  private final BigDecimal value;

  @JsonProperty("quote_currency")
  @JsonDeserialize(converter = StringToCurrencyConverter.class)
  private final Currency quoteCurrency;

  @JsonProperty("status")
  private final TransactionStatus status;

  public enum TransactionType {
    SUBSCRIBE,
    UNSUBSCRIBE,
    REWARD_RECEIVED
  }

  public enum TransactionStatus {
    COMPLETED
  }
}
