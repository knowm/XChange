package org.knowm.xchange.cointrader.dto.trade;

import java.math.BigDecimal;

import javax.annotation.Nullable;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.databind.PropertyNamingStrategy;
import com.fasterxml.jackson.databind.annotation.JsonNaming;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.fasterxml.jackson.databind.ser.std.ToStringSerializer;
import org.knowm.xchange.cointrader.dto.CointraderRequest;

@JsonNaming(PropertyNamingStrategy.LowerCaseWithUnderscoresStrategy.class)
public class CointraderOrderRequest extends CointraderRequest {

  @JsonProperty
  @JsonSerialize(using = ToStringSerializer.class)
  private final BigDecimal totalQuantity;

  @Nullable
  @JsonProperty
  @JsonSerialize(using = ToStringSerializer.class)
  private final BigDecimal price;

  private CointraderOrderRequest(BigDecimal totalQuantity, BigDecimal price) {
    this.totalQuantity = totalQuantity;
    this.price = price;
  }

  private CointraderOrderRequest(BigDecimal totalQuantity) {
    this(totalQuantity, null);
  }

  public static CointraderOrderRequest marketOrder(BigDecimal totalQuantity) {
    return new CointraderOrderRequest(totalQuantity);
  }

  public static CointraderOrderRequest order(BigDecimal totalQuantity, BigDecimal price) {
    return new CointraderOrderRequest(totalQuantity, price);
  }
}
