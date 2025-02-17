package info.bitrich.xchangestream.kraken.dto;

import java.math.BigDecimal;

public class KrakenOwnTrade {
  /** order responsible for execution of trade */
  public String ordertxid;

  /** an optional, alphanumeric client identifier associated with this order. Available on update messages only. */
  public String cl_ord_id;

  /** an optional, numeric identifier associated with one or more orders. Available on update messages only. */
  public Integer userref;

  /** Position trade id */
  public String postxid;

  public String pair;

  /** unix timestamp of trade */
  public Double time;

  /** type of order (buy/sell) */
  public String type;

  public String ordertype;

  /** average price order was executed at (quote currency) */
  public BigDecimal price;

  /** total cost of order (quote currency) */
  public BigDecimal cost;

  /** total fee (quote currency) */
  public BigDecimal fee;

  /** volume (base currency) */
  public BigDecimal vol;

  /** initial margin (quote currency) */
  public BigDecimal margin;
}
