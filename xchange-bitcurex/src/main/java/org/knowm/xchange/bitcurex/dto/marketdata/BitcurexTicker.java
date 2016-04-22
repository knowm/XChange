package org.knowm.xchange.bitcurex.dto.marketdata;

import java.math.BigDecimal;

import com.fasterxml.jackson.annotation.JsonProperty;

public class BitcurexTicker {

  private final BigDecimal average_price;
  private final BigDecimal total_spent;
  private final BigDecimal best_bid;
  private final BigDecimal lowest_tx_price;
  private final BigDecimal lowest_tx_spread;
  private final BigDecimal best_ask;
  private final BigDecimal price_change;
  private final BigDecimal last_tx_price;
  private final String currency;
  private final BigDecimal highest_tx_price;
  private final BigDecimal highest_tx_spread;
  private final String curr;
  private final String market;
  private final BigDecimal total_volume;

  public BitcurexTicker(@JsonProperty("average_price") BigDecimal average_price, @JsonProperty("total_spent") BigDecimal total_spent,
      @JsonProperty("best_bid") BigDecimal best_bid, @JsonProperty("lowest_tx_price") BigDecimal lowest_tx_price,
      @JsonProperty("lowest_tx_spread") BigDecimal lowest_tx_spread, @JsonProperty("best_ask") BigDecimal best_ask,
      @JsonProperty("price_change") BigDecimal price_change, @JsonProperty("last_tx_price") BigDecimal last_tx_price,
      @JsonProperty("currency") String currency, @JsonProperty("highest_tx_price") BigDecimal highest_tx_price,
      @JsonProperty("highest_tx_spread") BigDecimal highest_tx_spread, @JsonProperty("curr") String curr, @JsonProperty("market") String market,
      @JsonProperty("total_volume") BigDecimal total_volume) {

    this.average_price = average_price;
    this.total_spent = total_spent;
    this.best_bid = best_bid;
    this.lowest_tx_price = lowest_tx_price;
    this.lowest_tx_spread = lowest_tx_spread;
    this.best_ask = best_ask;
    this.price_change = price_change;
    this.last_tx_price = last_tx_price;
    this.currency = currency;
    this.highest_tx_price = highest_tx_price;
    this.highest_tx_spread = highest_tx_spread;
    this.curr = curr;
    this.market = market;
    this.total_volume = total_volume;
  }

  public BigDecimal getAverage() {

    return average_price.divide(new BigDecimal(10000));
  }

  public BigDecimal getTotalSpent() {

    return total_spent.divide(new BigDecimal(10000));
  }

  public BigDecimal getBid() {

    return best_bid.divide(new BigDecimal(10000));
  }

  public BigDecimal getLow() {

    return lowest_tx_price.divide(new BigDecimal(10000));
  }

  public BigDecimal getLowestTXSpread() {

    return lowest_tx_spread.divide(new BigDecimal(10000));
  }

  public BigDecimal getAsk() {

    return best_ask.divide(new BigDecimal(10000));
  }

  public BigDecimal getPriceChange() {

    return price_change.divide(new BigDecimal(10000));
  }

  public BigDecimal getLast() {

    return last_tx_price.divide(new BigDecimal(10000));
  }

  public String getCurrency() {

    return currency;
  }

  public BigDecimal getHigh() {

    return highest_tx_price.divide(new BigDecimal(10000));
  }

  public BigDecimal getHighestTXSpread() {

    return highest_tx_spread.divide(new BigDecimal(10000));
  }

  public String getCurr() {

    return curr;
  }

  public String getMarket() {

    return market;
  }

  public BigDecimal getVolume() {

    return total_volume.divide(new BigDecimal(100000000));
  }

  @Override
  public String toString() {

    return "BitcurexTicker [average_price=" + average_price + ", total_spent=" + total_spent + ", best_bid=" + best_bid + ", lowest_tx_price="
        + lowest_tx_price + ", lowest_tx_spread=" + lowest_tx_spread + ", best_ask=" + best_ask + ", price_change=" + price_change
        + ", last_tx_price=" + last_tx_price + ", currency=" + currency + ", highest_tx_price=" + highest_tx_price + ", highest_tx_spread="
        + highest_tx_spread + ", curr=" + curr + ", market=" + market + ", total_volume=" + total_volume + "]";
  }

}
