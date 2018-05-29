package org.knowm.xchange.bibox.dto.account;

import com.fasterxml.jackson.annotation.JsonProperty;
import java.math.BigDecimal;
import java.util.Date;
import org.knowm.xchange.bibox.dto.BiboxAdapters;

public class BiboxDeposit {

  public final long userId;
  public final long coinId;
  public final String to;
  public final String coinSymbol;
  public final String confirmCount;
  public final BigDecimal amount;
  private final Date updatedAt;
  private final Date createdAt;
  public final String url;
  public final String iconUrl;
  public final int status;

  public BiboxDeposit(
      @JsonProperty("user_id") long userId,
      @JsonProperty("coin_id") long coinId,
      @JsonProperty("to") String to,
      @JsonProperty("coin_symbol") String coinSymbol,
      @JsonProperty("confirmCount") String confirmCount,
      @JsonProperty("amount") BigDecimal amount,
      @JsonProperty("updatedAt") String updatedAt,
      @JsonProperty("createdAt") String createdAt,
      @JsonProperty("url") String url,
      @JsonProperty("icon_url") String iconUrl,
      @JsonProperty("status") int status) {
    this.userId = userId;
    this.coinId = coinId;
    this.to = to;
    this.coinSymbol = coinSymbol;
    this.confirmCount = confirmCount;
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
