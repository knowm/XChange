package org.knowm.xchange.hitbtc.dto.marketdata;

import java.math.BigDecimal;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonValue;

/**
 * @author kpysniak
 */
public class HitbtcTrade {

  private final long date;
  private final BigDecimal price;
  private final BigDecimal amount;
  private final String tid;
  private final HitbtcTradeSide side;

  /**
   * Constructor
   *
   * @param date
   * @param price
   * @param amount
   * @param tid
   * @param side
   */
  public HitbtcTrade(@JsonProperty("date") long date, @JsonProperty("price") BigDecimal price, @JsonProperty("amount") BigDecimal amount,
      @JsonProperty("tid") String tid, @JsonProperty("side") HitbtcTradeSide side) {

    this.date = date;
    this.price = price;
    this.amount = amount;
    this.tid = tid;
    this.side = side;
  }

  public long getDate() {

    return date;
  }

  public BigDecimal getPrice() {

    return price;
  }

  public BigDecimal getAmount() {

    return amount;
  }

  public String getTid() {

    return tid;
  }

  public HitbtcTradeSide getSide() {

    return side;
  }

  @Override
  public String toString() {

    return "HitbtcTrade{" + "date=" + date + ", price=" + price + ", amount=" + amount + ", tid='" + tid + "', side='" + side + "'" + "}";
  }

  public enum HitbtcTradeSide {

    BUY("buy"), SELL("sell");

    private final String hitbtcTradeSide;

    HitbtcTradeSide(String hitbtcTradeSide) {

      this.hitbtcTradeSide = hitbtcTradeSide;
    }

    @Override
    @JsonValue
    public String toString() {

      return hitbtcTradeSide;
    }
  }
}
