package org.knowm.xchange.deribit.v2.dto.account;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import java.math.BigDecimal;
import java.time.Instant;
import lombok.Builder;
import lombok.Data;
import lombok.extern.jackson.Jacksonized;
import org.knowm.xchange.currency.Currency;
import org.knowm.xchange.deribit.v2.config.converter.StringToCurrencyConverter;
import org.knowm.xchange.deribit.v2.config.converter.StringToFundingRecordStatusConverter;
import org.knowm.xchange.dto.account.FundingRecord.Status;

@Data
@Builder
@Jacksonized
public class DeribitWithdrawal {

  @JsonProperty("id")
  private String id;

  @JsonProperty("address")
  private String targetAddress;

  @JsonProperty("note")
  private String note;

  @JsonProperty("currency")
  @JsonDeserialize(converter = StringToCurrencyConverter.class)
  private Currency currency;

  @JsonProperty("amount")
  private BigDecimal amount;

  @JsonProperty("fee")
  private BigDecimal fee;

  @JsonProperty("state")
  @JsonDeserialize(converter = StringToFundingRecordStatusConverter.class)
  private Status status;

  @JsonProperty("priority")
  private String priority;

  @JsonProperty("transaction_id")
  private String transactionId;

  @JsonProperty("created_timestamp")
  private Instant createdAt;

  @JsonProperty("updated_timestamp")
  private Instant updatedAt;

  @JsonProperty("confirmed_timestamp")
  private Instant confirmedAt;

}
