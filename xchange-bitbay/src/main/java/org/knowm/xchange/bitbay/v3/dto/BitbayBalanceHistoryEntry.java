package org.knowm.xchange.bitbay.v3.dto;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonPOJOBuilder;
import java.math.BigDecimal;
import java.util.UUID;
import lombok.Builder;
import lombok.Value;
import lombok.extern.jackson.Jacksonized;

@Value
@Builder
@Jacksonized
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

  @Value
  @Builder
  @Jacksonized
  public static class BalanceInfo {
    UUID id;
    String currency;
    BalanceType type;
    UUID userId;
    String name;
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
  @Jacksonized
  public static class FundsInfo {
    BigDecimal total;
    BigDecimal available;
    BigDecimal locked;
  }
}
