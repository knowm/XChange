package info.bitrich.xchangestream.bitflyer.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import java.math.BigDecimal;
import org.knowm.xchange.currency.CurrencyPair;
import org.knowm.xchange.dto.Order;
import org.knowm.xchange.dto.trade.LimitOrder;

/** Created by Lukas Zaoralek on 14.11.17. */
public class BitflyerLimitOrder {
  private final BigDecimal price;
  private final BigDecimal size;

  public BitflyerLimitOrder(
      @JsonProperty("price") BigDecimal price, @JsonProperty("size") BigDecimal size) {
    this.price = price;
    this.size = size;
  }

  public BigDecimal getPrice() {
    return price;
  }

  public BigDecimal getSize() {
    return size;
  }

  public LimitOrder toLimitOrder(CurrencyPair pair, Order.OrderType side) {
    return new LimitOrder(side, size, pair, "", null, price);
  }
}
