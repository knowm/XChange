package org.knowm.xchange.bibox.dto.trade;

import com.fasterxml.jackson.annotation.JsonProperty;

/**
 * @author odrotleff
 */
public class BiboxOrderPendingListCommandBody {

  /**
   * the currency pair
   */
  private String pair;

  @JsonProperty("account_type")
  private BiboxAccountType accountType;

  private int page;

  private int size;

  /**
   * the base currency
   */
  @JsonProperty("coin_symbol")
  private String coinSymbol;

  /**
   * the counter currency
   */
  @JsonProperty("currency_symbol")
  private String currencySymbol;

  @JsonProperty("order_side")
  private BiboxOrderSide orderSide;

  public BiboxOrderPendingListCommandBody(int page, int size) {
    this(null, null, page, size, null, null, null);
  }

  public BiboxOrderPendingListCommandBody(String pair, BiboxAccountType accountType, int page, int size, String coinSymbol, String currencySymbol,
      BiboxOrderSide orderSide) {
    super();
    this.pair = pair;
    this.accountType = accountType;
    this.page = page;
    this.size = size;
    this.coinSymbol = coinSymbol;
    this.currencySymbol = currencySymbol;
    this.orderSide = orderSide;
  }

  public String getPair() {
    return pair;
  }

  public BiboxAccountType getAccountType() {
    return accountType;
  }

  public int getPage() {
    return page;
  }

  public int getSize() {
    return size;
  }

  public String getCoinSymbol() {
    return coinSymbol;
  }

  public String getCurrencySymbol() {
    return currencySymbol;
  }

  public BiboxOrderSide getOrderSide() {
    return orderSide;
  }
}
