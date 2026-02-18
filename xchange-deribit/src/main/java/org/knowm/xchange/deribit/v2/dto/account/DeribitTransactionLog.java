package org.knowm.xchange.deribit.v2.dto.account;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonEnumDefaultValue;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import java.math.BigDecimal;
import java.time.Instant;
import java.util.HashMap;
import java.util.Map;
import java.util.Optional;
import lombok.Data;
import org.knowm.xchange.currency.Currency;
import org.knowm.xchange.deribit.v2.config.converter.StringToCurrencyConverter;

@Data
public class DeribitTransactionLog {

  @JsonProperty("change")
  BigDecimal change;

  @JsonProperty("cashflow")
  BigDecimal cashflow;

  @JsonProperty("user_id")
  String userId;

  @JsonProperty("trade_id")
  String tradeId;

  @JsonProperty("type")
  TransactionType transactionType;

  @JsonProperty("order_id")
  String orderId;

  @JsonProperty("position")
  BigDecimal positionSize;

  @JsonProperty("side")
  Side side;

  @JsonProperty("contracts")
  BigDecimal contracts;

  @JsonProperty("interest_pl")
  BigDecimal interestPl;

  @JsonProperty("user_role")
  UserRole userRole;

  @JsonProperty("fee_role")
  UserRole feeRole;

  @JsonProperty("id")
  String id;

  Map<String, Object> infoMap = new HashMap<>();

  String infoString;

  @JsonProperty("currency")
  @JsonDeserialize(converter = StringToCurrencyConverter.class)
  Currency currency;

  @JsonProperty("price")
  BigDecimal price;

  @JsonProperty("user_seq")
  Long userSequenceId;

  @JsonProperty("settlement_price")
  BigDecimal settlementPrice;

  @JsonProperty("price_currency")
  @JsonDeserialize(converter = StringToCurrencyConverter.class)
  Currency priceCurrency;

  @JsonProperty("equity")
  BigDecimal equity;

  @JsonProperty("total_interest_pl")
  BigDecimal totalInterestPl;

  @JsonProperty("balance")
  BigDecimal balance;

  @JsonProperty("session_upl")
  BigDecimal sessionUnrealizedPnl;

  @JsonProperty("timestamp")
  Instant timestamp;

  @JsonProperty("profit_as_cashflow")
  Boolean profitAsCashflow;

  @JsonProperty("commission")
  BigDecimal commission;

  @JsonProperty("session_rpl")
  BigDecimal sessionRealizedPnl;

  @JsonProperty("mark_price")
  BigDecimal markPrice;

  @JsonProperty("block_rfq_id")
  Long blockRfqId;

  @JsonProperty("ip")
  String ipAddress;

  @JsonProperty("amount")
  BigDecimal amount;

  @JsonProperty("username")
  String username;

  @JsonProperty("instrument_name")
  String instrumentName;

  @JsonCreator
  public DeribitTransactionLog(@JsonProperty("info") Object infoObject) {
    if (infoObject instanceof Map) {
      infoMap = (Map<String, Object>) infoObject;
    } else if (infoObject instanceof String) {
      infoString = (String) infoObject;
    }
  }

  public String getAddress() {
    return Optional.ofNullable(infoMap.getOrDefault("addr", infoMap.get("other_user_id")))
        .map(Object::toString)
        .orElse(null);
  }

  public String getAddressTag() {
    return (String) infoMap.get("note");
  }

  public String getBlockchainTransactionHash() {
    return (String) infoMap.get("transaction");
  }

  public BigDecimal getAmount() {
    if (amount != null) {
      return amount;
    }
    return change;
  }

  public enum TransactionType {
    @JsonProperty("trade")
    TRADE,

    @JsonProperty("deposit")
    DEPOSIT,

    @JsonProperty("withdrawal")
    WITHDRAWAL,

    @JsonProperty("settlement")
    SETTLEMENT,

    @JsonProperty("delivery")
    DELIVERY,

    @JsonProperty("transfer")
    TRANSFER,

    @JsonProperty("swap")
    SWAP,

    @JsonProperty("correction")
    CORRECTION,

    @JsonEnumDefaultValue
    UNKNOWN
  }

  public enum Side {
    @JsonProperty("short")
    SHORT,

    @JsonProperty("long")
    LONG,

    @JsonProperty("close sell")
    CLOSE_SELL,

    @JsonProperty("close buy")
    CLOSE_BUY,

    @JsonProperty("open sell")
    OPEN_SELL,

    @JsonProperty("open buy")
    OPEN_BUY,

    @JsonEnumDefaultValue
    UNKNOWN
  }

  public static enum UserRole {
    @JsonProperty("taker")
    TAKER,

    @JsonProperty("maker")
    MAKER
  }
}
