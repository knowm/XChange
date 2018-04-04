package org.knowm.xchange.quoine.dto.marketdata;

import com.fasterxml.jackson.annotation.JsonProperty;
import java.math.BigDecimal;

/** @author timmolter */
public final class QuoineProduct {

  @JsonProperty("id")
  private final int id;

  @JsonProperty("product_type")
  private final String productType;

  @JsonProperty("code")
  private final String code;

  @JsonProperty("name")
  private final String name;

  @JsonProperty("market_ask")
  private final BigDecimal marketAsk;

  @JsonProperty("market_bid")
  private final BigDecimal marketBid;

  @JsonProperty("indicator")
  private final int indicator;

  @JsonProperty("currency_pair_id")
  private final String currencyPairId;

  @JsonProperty("currency")
  private final String currency;

  @JsonProperty("currency_pair_code")
  private final String currencyPairCode;

  @JsonProperty("symbol")
  private final String symbol;

  @JsonProperty("btc_minimum_withdraw")
  private final BigDecimal btcMinimumWithdraw;

  @JsonProperty("fiat_minimum_withdraw")
  private final BigDecimal fiatMinimumWithdraw;

  @JsonProperty("pusher_channel")
  private final String pusherChannel;

  @JsonProperty("taker_fee")
  private final BigDecimal takerFee;

  @JsonProperty("maker_fee")
  private final BigDecimal makerFee;

  @JsonProperty("low_market_bid")
  private final BigDecimal lowMarketBid;

  @JsonProperty("high_market_ask")
  private final BigDecimal highMarketAsk;

  @JsonProperty("volume_24h")
  private final BigDecimal volume24h;

  @JsonProperty("last_price_24h")
  private final BigDecimal lastPrice24h;

  @JsonProperty("cash_spot_ask")
  private final BigDecimal cashSpotAsk;

  @JsonProperty("cash_spot_bid")
  private final BigDecimal cashSpotBid;

  @JsonProperty("last_traded_price")
  private final BigDecimal lastTradedPrice;

  @JsonProperty("quoted_currency")
  private final String quotedCurrency;

  @JsonProperty("base_currency")
  private final String baseCurrency;

  /**
   * Constructor
   *
   * @param id
   * @param productType
   * @param code
   * @param name
   * @param marketAsk
   * @param marketBid
   * @param indicator
   * @param currencyPairId
   * @param currency
   * @param currencyPairCode
   * @param symbol
   * @param btcMinimumWithdraw
   * @param fiatMinimumWithdraw
   * @param pusherChannel
   * @param takerFee
   * @param makerFee
   * @param lowMarketBid
   * @param highMarketAsk
   * @param volume24h
   * @param lastPrice24h
   * @param cashSpotAsk
   * @param cashSpotBid
   * @param lastTradedPrice
   */
  public QuoineProduct(
      @JsonProperty("id") int id,
      @JsonProperty("product_type") String productType,
      @JsonProperty("code") String code,
      @JsonProperty("name") String name,
      @JsonProperty("market_ask") BigDecimal marketAsk,
      @JsonProperty("market_bid") BigDecimal marketBid,
      @JsonProperty("indicator") int indicator,
      @JsonProperty("currency_pair_id") String currencyPairId,
      @JsonProperty("currency") String currency,
      @JsonProperty("currency_pair_code") String currencyPairCode,
      @JsonProperty("symbol") String symbol,
      @JsonProperty("btc_minimum_withdraw") BigDecimal btcMinimumWithdraw,
      @JsonProperty("fiat_minimum_withdraw") BigDecimal fiatMinimumWithdraw,
      @JsonProperty("pusher_channel") String pusherChannel,
      @JsonProperty("taker_fee") BigDecimal takerFee,
      @JsonProperty("maker_fee") BigDecimal makerFee,
      @JsonProperty("low_market_bid") BigDecimal lowMarketBid,
      @JsonProperty("high_market_ask") BigDecimal highMarketAsk,
      @JsonProperty("volume_24h") BigDecimal volume24h,
      @JsonProperty("last_price_24h") BigDecimal lastPrice24h,
      @JsonProperty("cash_spot_ask") BigDecimal cashSpotAsk,
      @JsonProperty("cash_spot_bid") BigDecimal cashSpotBid,
      @JsonProperty("last_traded_price") BigDecimal lastTradedPrice,
      @JsonProperty("quoted_currency") String quotedCurrency,
      @JsonProperty("base_currency") String baseCurrency) {
    this.id = id;
    this.productType = productType;
    this.code = code;
    this.name = name;
    this.marketAsk = marketAsk;
    this.marketBid = marketBid;
    this.indicator = indicator;
    this.currencyPairId = currencyPairId;
    this.currency = currency;
    this.currencyPairCode = currencyPairCode;
    this.symbol = symbol;
    this.btcMinimumWithdraw = btcMinimumWithdraw;
    this.fiatMinimumWithdraw = fiatMinimumWithdraw;
    this.pusherChannel = pusherChannel;
    this.takerFee = takerFee;
    this.makerFee = makerFee;
    this.lowMarketBid = lowMarketBid;
    this.highMarketAsk = highMarketAsk;
    this.volume24h = volume24h;
    this.lastPrice24h = lastPrice24h;
    this.cashSpotAsk = cashSpotAsk;
    this.cashSpotBid = cashSpotBid;
    this.lastTradedPrice = lastTradedPrice;
    this.quotedCurrency = quotedCurrency;
    this.baseCurrency = baseCurrency;
  }

  public int getId() {
    return id;
  }

  public String getProductType() {
    return productType;
  }

  public String getCode() {
    return code;
  }

  public String getName() {
    return name;
  }

  public BigDecimal getMarketAsk() {
    return marketAsk;
  }

  public BigDecimal getMarketBid() {
    return marketBid;
  }

  public int getIndicator() {
    return indicator;
  }

  public String getCurrencyPairId() {
    return currencyPairId;
  }

  public String getCurrency() {
    return currency;
  }

  public String getCurrencyPairCode() {
    return currencyPairCode;
  }

  public String getSymbol() {
    return symbol;
  }

  public BigDecimal getBtcMinimumWithdraw() {
    return btcMinimumWithdraw;
  }

  public BigDecimal getFiatMinimumWithdraw() {
    return fiatMinimumWithdraw;
  }

  public String getPusherChannel() {
    return pusherChannel;
  }

  public BigDecimal getTakerFee() {
    return takerFee;
  }

  public BigDecimal getMakerFee() {
    return makerFee;
  }

  public BigDecimal getLowMarketBid() {
    return lowMarketBid;
  }

  public BigDecimal getHighMarketAsk() {
    return highMarketAsk;
  }

  public BigDecimal getVolume24h() {
    return volume24h;
  }

  public BigDecimal getLastPrice24h() {
    return lastPrice24h;
  }

  public BigDecimal getCashSpotAsk() {
    return cashSpotAsk;
  }

  public BigDecimal getCashSpotBid() {
    return cashSpotBid;
  }

  public BigDecimal getLastTradedPrice() {
    return lastTradedPrice;
  }

  public String getQuotedCurrency() {
    return quotedCurrency;
  }

  public String getBaseCurrency() {
    return baseCurrency;
  }

  @Override
  public String toString() {
    return "QuoineProduct{"
        + "id="
        + id
        + ", productType='"
        + productType
        + '\''
        + ", code='"
        + code
        + '\''
        + ", name='"
        + name
        + '\''
        + ", marketAsk="
        + marketAsk
        + ", marketBid="
        + marketBid
        + ", indicator="
        + indicator
        + ", currencyPairId='"
        + currencyPairId
        + '\''
        + ", currency='"
        + currency
        + '\''
        + ", currencyPairCode='"
        + currencyPairCode
        + '\''
        + ", symbol='"
        + symbol
        + '\''
        + ", btcMinimumWithdraw="
        + btcMinimumWithdraw
        + ", fiatMinimumWithdraw="
        + fiatMinimumWithdraw
        + ", pusherChannel='"
        + pusherChannel
        + '\''
        + ", takerFee="
        + takerFee
        + ", makerFee="
        + makerFee
        + ", lowMarketBid="
        + lowMarketBid
        + ", highMarketAsk="
        + highMarketAsk
        + ", volume24h="
        + volume24h
        + ", lastPrice24h="
        + lastPrice24h
        + ", cashSpotAsk="
        + cashSpotAsk
        + ", cashSpotBid="
        + cashSpotBid
        + ", lastTradedPrice="
        + lastTradedPrice
        + ", quotedCurrency='"
        + quotedCurrency
        + '\''
        + ", baseCurrency='"
        + baseCurrency
        + '\''
        + '}';
  }
}
