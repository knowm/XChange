package org.knowm.xchange.bitbay.v3.dto;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonPOJOBuilder;
import java.math.BigDecimal;
import java.util.UUID;
import lombok.Builder;
import lombok.Value;

@Value
@Builder
@JsonDeserialize(builder = BitbayBalanceHistoryEntry.BitbayBalanceHistoryEntryBuilder.class)
public class BitbayBalanceHistoryEntry {

  UUID historyId;
  BalanceInfo balance;
  UUID detailId;
  long time;
  String type;
  BigDecimal value;
  FundsInfo fundsBefore;
  FundsInfo fundsAfter;
  FundsInfo change;

  @JsonPOJOBuilder(withPrefix = "")
  public static class BitbayBalanceHistoryEntryBuilder {}

  @Value
  @Builder
  @JsonDeserialize(builder = BalanceInfo.BalanceInfoBuilder.class)
  public static class BalanceInfo {
    UUID id;
    String currency;
    BalanceType type;
    UUID userId;
    String name;

    @JsonPOJOBuilder(withPrefix = "")
    public static class BalanceInfoBuilder {}
  }

  public enum BalanceType {
    FIAT,
    CRYPTO;

    @JsonCreator
    public static BalanceType fromValue(String strValue) {
      for (BalanceType balanceType : BalanceType.values()) {
        if (balanceType.name().equals(strValue)) {
          return balanceType;
        }
      }
      return null;
    }
  }

  @Value
  @Builder
  @JsonDeserialize(builder = FundsInfo.FundsInfoBuilder.class)
  public static class FundsInfo {
    BigDecimal total;
    BigDecimal available;
    BigDecimal locked;

    @JsonPOJOBuilder(withPrefix = "")
    public static class FundsInfoBuilder {}
  }
}
