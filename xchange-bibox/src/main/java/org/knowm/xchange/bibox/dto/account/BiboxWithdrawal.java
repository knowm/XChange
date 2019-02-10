package org.knowm.xchange.bibox.dto.account;

import com.fasterxml.jackson.annotation.JsonProperty;
import java.math.BigDecimal;
import java.util.Date;
import org.knowm.xchange.bibox.dto.BiboxAdapters;

public class BiboxWithdrawal {

  public final long userId;
  public final long coinId;
  public final String toAddress;
  public final String coinSymbol;
  public final BigDecimal amountReal;
  public final BigDecimal amount;
  private final Date updatedAt;
  private final Date createdAt;
  public final String url;
  public final String iconUrl;
  public final int status;

  public BiboxWithdrawal(
      @JsonProperty("user_id") long userId,
      @JsonProperty("coin_id") long coinId,
      @JsonProperty("to_address") String toAddress,
      @JsonProperty("coin_symbol") String coinSymbol,
      @JsonProperty("amount_real") BigDecimal amountReal,
      @JsonProperty("amount") BigDecimal amount,
      @JsonProperty("updatedAt") String updatedAt,
      @JsonProperty("createdAt") String createdAt,
      @JsonProperty("url") String url,
      @JsonProperty("icon_url") String iconUrl,
      @JsonProperty("status") int status) {
    this.userId = userId;
    this.coinId = coinId;
    this.toAddress = toAddress;
    this.coinSymbol = coinSymbol;
    this.amountReal = amountReal;
    this.amount = amount;
    this.updatedAt = BiboxAdapters.convert(updatedAt);
    this.createdAt = BiboxAdapters.convert(createdAt);
    this.url = url;
    this.iconUrl = iconUrl;
    this.status = status;
  }

  public Date getUpdatedAt() {
    return new Date(updatedAt.getTime());
  }

  public Date getCreatedAt() {
    return new Date(createdAt.getTime());
  }
}
