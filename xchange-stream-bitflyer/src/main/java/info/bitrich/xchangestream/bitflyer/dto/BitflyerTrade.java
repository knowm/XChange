package info.bitrich.xchangestream.bitflyer.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import java.math.BigDecimal;
import org.knowm.xchange.currency.CurrencyPair;
import org.knowm.xchange.dto.Order;
import org.knowm.xchange.dto.marketdata.Trade;

/** Created by Lukas Zaoralek on 15.11.17. */
public class BitflyerTrade extends BitflyerMarketEvent {
  private final String id;
  private final String side;
  private final BigDecimal price;
  private final BigDecimal size;
  private final String buy_child_order_acceptance_id;
  private final String sell_child_order_acceptance_id;

  public BitflyerTrade(
      @JsonProperty("id") String id,
      @JsonProperty("side") String side,
      @JsonProperty("price") BigDecimal price,
      @JsonProperty("size") BigDecimal size,
      @JsonProperty("exec_date") String timestamp,
      @JsonProperty("buy_child_order_acceptance_id") String buy_child_order_acceptance_id,
      @JsonProperty("sell_child_order_acceptance_id") String sell_child_order_acceptance_id) {
    super(timestamp);
    this.id = id;
    this.side = side;
    this.price = price;
    this.size = size;
    this.buy_child_order_acceptance_id = buy_child_order_acceptance_id;
    this.sell_child_order_acceptance_id = sell_child_order_acceptance_id;
  }

  public String getId() {
    return id;
  }

  public String getSide() {
    return side;
  }

  public BigDecimal getPrice() {
    return price;
  }

  public BigDecimal getSize() {
    return size;
  }

  public String getBuy_child_order_acceptance_id() {
    return buy_child_order_acceptance_id;
  }

  public String getSell_child_order_acceptance_id() {
    return sell_child_order_acceptance_id;
  }

  public Order.OrderType getOrderSide() {
    return side.equals("SELL") ? Order.OrderType.ASK : Order.OrderType.BID;
  }

  public Trade toTrade(CurrencyPair pair) {
    Order.OrderType orderType = getOrderSide();
    return new Trade.Builder()
        .type(orderType)
        .originalAmount(size)
        .currencyPair(pair)
        .price(price)
        .timestamp(getDate())
        .id(id)
        .build();
  }
}
