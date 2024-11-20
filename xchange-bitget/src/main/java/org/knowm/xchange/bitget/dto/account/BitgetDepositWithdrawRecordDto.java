package org.knowm.xchange.bitget.dto.account;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import java.math.BigDecimal;
import java.time.Instant;
import lombok.Builder;
import lombok.Data;
import lombok.extern.jackson.Jacksonized;
import org.knowm.xchange.bitget.config.converter.StringToCurrencyConverter;
import org.knowm.xchange.bitget.config.converter.StringToFundingRecordStatusConverter;
import org.knowm.xchange.currency.Currency;
import org.knowm.xchange.dto.account.FundingRecord.Status;

@Data
@Builder
@Jacksonized
public class BitgetDepositWithdrawRecordDto {

  @JsonProperty("orderId")
  private String orderId;

  @JsonProperty("tradeId")
  private String tradeId;

  @JsonProperty("coin")
  @JsonDeserialize(converter = StringToCurrencyConverter.class)
  private Currency currency;

  @JsonProperty("clientOid")
  private String clientOid;

  @JsonProperty("type")
  private RecordType type;

  @JsonProperty("dest")
  private DepositType depositType;

  @JsonProperty("size")
  private BigDecimal size;

  @JsonProperty("fee")
  private BigDecimal fee;

  @JsonProperty("status")
  @JsonDeserialize(converter = StringToFundingRecordStatusConverter.class)
  private Status status;

  @JsonProperty("fromAddress")
  private String fromAddress;

  @JsonProperty("toAddress")
  private String toAddress;

  @JsonProperty("chain")
  private String chain;

  @JsonProperty("confirm")
  private Integer confirmCount;

  @JsonProperty("tag")
  private String toAddressTag;

  @JsonProperty("cTime")
  private Instant createdAt;

  @JsonProperty("uTime")
  private Instant updatedAt;

  public static enum RecordType {
    @JsonProperty("withdraw")
    WITHDRAW,

    @JsonProperty("deposit")
    DEPOSIT
  }

  public static enum DepositType {
    @JsonProperty("on_chain")
    ON_CHAIN,

    @JsonProperty("internal_transfer")
    INTERNAL_TRANSFER
  }
}
