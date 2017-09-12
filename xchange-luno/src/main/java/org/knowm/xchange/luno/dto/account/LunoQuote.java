package org.knowm.xchange.luno.dto.account;

import java.math.BigDecimal;
import java.util.Date;

import com.fasterxml.jackson.annotation.JsonProperty;

public class LunoQuote {

  public final String id;
  public final String type;
  public final String pair;
  public final BigDecimal baseAmount;
  public final BigDecimal counterAmount;
  public final long createdAt;
  public final long expiresAt;
  public final boolean discarded;
  public final boolean exercised;

  public LunoQuote(@JsonProperty(value = "id", required = true) String id, @JsonProperty(value = "type", required = true) String type
      , @JsonProperty(value = "pair", required = true) String pair
      , @JsonProperty(value = "base_amount", required = true) BigDecimal baseAmount
      , @JsonProperty(value = "counter_amount", required = true) BigDecimal counterAmount
      , @JsonProperty(value = "created_at", required = true) long createdAt
      , @JsonProperty(value = "expires_at", required = true) long expiresAt
      , @JsonProperty(value = "discarded", required = true) boolean discarded
      , @JsonProperty(value = "exercised", required = true) boolean exercised) {
    this.id = id;
    this.type = type;
    this.pair = pair;
    this.baseAmount = baseAmount;
    this.counterAmount = counterAmount;
    this.createdAt = createdAt;
    this.expiresAt = expiresAt;
    this.discarded = discarded;
    this.exercised = exercised;
  }

  public Date getCreatedAt() {
    return createdAt == 0 ? null : new Date(createdAt);
  }

  public Date getExpiresAt() {
    return expiresAt == 0 ? null : new Date(expiresAt);
  }

  @Override
  public String toString() {
    return "LunoQuote [id=" + id + ", type=" + type + ", pair=" + pair + ", baseAmount=" + baseAmount + ", counterAmount="
        + counterAmount + ", createdAt=" + getCreatedAt() + ", expiresAt=" + getExpiresAt() + ", discarded=" + discarded
        + ", exercised=" + exercised + "]";
  }
}
