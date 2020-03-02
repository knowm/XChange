package info.bitrich.xchangestream.kraken.dto;

import java.math.BigDecimal;

/** https://docs.kraken.com/websockets/#message-openOrders */
public class KrakenOpenOrder {
  /** Referral order transaction id that created this order */
  public String refid;
  /** user reference id */
  public Integer userref;
  /** status of order: */
  public String status;
  /** unix timestamp of when order was placed */
  public Double opentm;
  /** unix timestamp of order start time (if set) */
  public Double starttm;
  /** unix timestamp of order end time (if set) */
  public Double expiretm;

  public KrakenDtoDescr descr;

  /** volume of order (base currency unless viqc set in oflags) */
  public BigDecimal vol;
  /** volume executed (base currency unless viqc set in oflags) */
  public BigDecimal vol_exec;
  /** total cost (quote currency unless unless viqc set in oflags) */
  public BigDecimal cost;
  /** total fee (quote currency) */
  public BigDecimal fee;
  /** average price (quote currency unless viqc set in oflags) */
  public BigDecimal avg_price;
  /** stop price (quote currency, for trailing stops) */
  public BigDecimal stopprice;
  /** triggered limit price (quote currency, when limit based order type triggered) */
  public BigDecimal limitprice;
  /**
   * comma delimited list of miscellaneous info: stopped=triggered by stop price, touched=triggered
   * by touch price, liquidation=liquidation, partial=partial fill
   */
  public String misc;
  /**
   * Comma delimited list of order flags (optional). viqc = volume in quote currency (not available
   * for leveraged orders), fcib = prefer fee in base currency, fciq = prefer fee in quote currency,
   * nompp = no market price protection, post = post only order (available when ordertype = limit)
   */
  public String oflags;

  public static class KrakenDtoDescr {
    public String pair;
    /** type of order (buy/sell) */
    public String type;
    /** order type */
    public String ordertype;
    /** primary price */
    public BigDecimal price;
    /** secondary price */
    public BigDecimal price2;
    /** amount of leverage */
    public Double leverage;
    /** order description */
    public String order;
    /** conditional close order description (if conditional close set) */
    public String close;
  }
}
