package org.knowm.xchange.coinbasepro.dto.trade;

import com.fasterxml.jackson.annotation.JsonProperty;
import java.math.BigDecimal;
import lombok.Builder;
import lombok.Getter;
import lombok.ToString;

@Getter
@ToString
@Builder
public class CoinbaseProFill {
  private final String tradeId;
  private final String productId;
  private final String orderId;
  private final String userId;
  private final String profileId;
  private final Liquidity liquidity;
  private final BigDecimal price;
  private final BigDecimal size;
  private final BigDecimal fee;
  private final String createdAt;
  private final Side side;
  private final boolean settled;
  private final String usdVolume;
  private final String marketType;
  private final String fundingCurrency;

  public CoinbaseProFill(
      @JsonProperty("trade_id") String tradeId,
      @JsonProperty("product_id") String productId,
      @JsonProperty("order_id") String orderId,
      @JsonProperty("user_id") String userId,
      @JsonProperty("profile_id") String profileId,
      @JsonProperty("liquidity") Liquidity liquidity,
      @JsonProperty("price") BigDecimal price,
      @JsonProperty("size") BigDecimal size,
      @JsonProperty("fee") BigDecimal fee,
      @JsonProperty("created_at") String createdAt,
      @JsonProperty("side") Side side,
      @JsonProperty("settled") boolean settled,
      @JsonProperty("usd_volume") String usdVolume,
      @JsonProperty("market_type") String marketType,
      @JsonProperty("funding_currency") String fundingCurrency
  ) {
    this.tradeId = tradeId;
    this.productId = productId;
    this.orderId = orderId;
    this.userId = userId;
    this.profileId = profileId;
    this.liquidity = liquidity;
    this.price = price;
    this.size = size;
    this.fee = fee;
    this.createdAt = createdAt;
    this.side = side;
    this.settled = settled;
    this.usdVolume = usdVolume;
    this.marketType = marketType;
    this.fundingCurrency = fundingCurrency;
  }

  public enum Liquidity {
    M,
    T,
    O
  }

  public enum Side {
    buy,
    sell
  }
}
