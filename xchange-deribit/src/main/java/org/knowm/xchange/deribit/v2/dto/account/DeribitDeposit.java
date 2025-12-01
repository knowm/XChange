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
import org.knowm.xchange.deribit.v2.config.converter.StringToFundingRecordStatusConverter;
import org.knowm.xchange.dto.account.FundingRecord.Status;

@Data
@Builder
@Jacksonized
public class DeribitDeposit {

  @JsonProperty("state")
  @JsonDeserialize(converter = StringToFundingRecordStatusConverter.class)
  private Status status;

  @JsonProperty("address")
  private String address;

  @JsonProperty("note")
  private String note;

  @JsonProperty("transaction_id")
  private String transactionId;

  @JsonProperty("refund_transaction_id")
  private String refundTransactionId;

  @JsonProperty("source_address")
  private String sourceAddress;

  @JsonProperty("currency")
  @JsonDeserialize(converter = StringToCurrencyConverter.class)
  private Currency currency;

  @JsonProperty("amount")
  private BigDecimal amount;

  @JsonProperty("received_timestamp")
  private Instant createdAt;

  @JsonProperty("updated_timestamp")
  private Instant updatedAt;

  @JsonProperty("clearance_state")
  private ClearanceState clearanceState;


  public enum ClearanceState {
    @JsonProperty("in_progress")
    IN_PROGRESS,

    @JsonProperty("pending_admin_decision")
    PENDING_ADMIN_DECISION,

    @JsonProperty("pending_user_input")
    PENDING_USER_INPUT,

    @JsonProperty("success")
    SUCCESS,

    @JsonProperty("failed")
    FAILED,

    @JsonProperty("cancelled")
    CANCELLED,

    @JsonProperty("refund_initiated")
    REFUND_INITIATED,

    @JsonProperty("refunded")
    REFUNDED,

    @JsonEnumDefaultValue
    UNKNOWN
  }

}
