package org.knowm.xchange.cexio.dto.trade;

import com.fasterxml.jackson.annotation.JsonProperty;
import java.math.BigDecimal;

/** Author: brox Since: 2/5/14 */
public class CexioClosePosition {

  private final String id;
  private final Long ctime;
  private final CexioPositionType ptype;
  private final String msymbol;
  private final BigDecimal price;
  private final CexioPositionPair pair;
  private final BigDecimal profit;

  /**
   * @param id the position id
   * @param ctime timestamp the position was closed at
   * @param ptype position type. long - buying product, profitable if product price grows; short -
   *     selling product, profitable if product price falls;
   * @param msymbol currency, in which user gained or lost
   * @param price price the position was closed at, calculated as average of underlying executed
   *     orders
   * @param pair trading pair as a list of two symbols, presents the pair according to requested URL
   * @param profit positive if user gained, negative - if lost, presented in currency msymbol
   */
  public CexioClosePosition(
      @JsonProperty("id") String id,
      @JsonProperty("ctime") Long ctime,
      @JsonProperty("ptype") CexioPositionType ptype,
      @JsonProperty("msymbol") String msymbol,
      @JsonProperty("price") BigDecimal price,
      @JsonProperty("pair") CexioPositionPair pair,
      @JsonProperty("profit") BigDecimal profit) {
    this.id = id;
    this.ctime = ctime;
    this.ptype = ptype;
    this.msymbol = msymbol;
    this.price = price;
    this.pair = pair;
    this.profit = profit;
  }

  public String getId() {
    return id;
  }

  public Long getCtime() {
    return ctime;
  }

  public CexioPositionType getPtype() {
    return ptype;
  }

  public String getMsymbol() {
    return msymbol;
  }

  public BigDecimal getPrice() {
    return price;
  }

  public CexioPositionPair getPair() {
    return pair;
  }

  public BigDecimal getProfit() {
    return profit;
  }
}
