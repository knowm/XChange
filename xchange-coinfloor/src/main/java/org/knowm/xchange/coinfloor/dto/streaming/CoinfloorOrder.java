package org.knowm.xchange.coinfloor.dto.streaming;

import java.math.BigDecimal;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import org.knowm.xchange.coinfloor.CoinfloorUtils;
import org.knowm.xchange.coinfloor.CoinfloorUtils.CoinfloorCurrency;

/**
 * @author obsessiveOrange
 */

@JsonIgnoreProperties(ignoreUnknown = true)
public class CoinfloorOrder {

  private final int id;
  private final int bidID;
  private final int askID;
  private final CoinfloorCurrency base;
  private final CoinfloorCurrency counter;
  private final BigDecimal baseQty;
  private final BigDecimal price;
  private final BigDecimal counterQty;
  private final BigDecimal bidRem;
  private final BigDecimal askRem;
  private final long time;
  private final BigDecimal bidBaseFee;
  private final BigDecimal bidCounterFee;
  private final BigDecimal askBaseFee;
  private final BigDecimal askCounterFee;

  public CoinfloorOrder(@JsonProperty("id") int id, @JsonProperty("bid") int bidID, @JsonProperty("ask") int askID, @JsonProperty("base") int base,
      @JsonProperty("counter") int counter, @JsonProperty("quantity") int baseQty, @JsonProperty("price") int price,
      @JsonProperty("total") int counterQty, @JsonProperty("bid_rem") int bidRem, @JsonProperty("ask_rem") int askRem,
      @JsonProperty("time") long time, @JsonProperty("bid_base_fee") int bidBaseFee, @JsonProperty("bid_counter_fee") int bidCounterFee,
      @JsonProperty("ask_base_fee") int askBaseFee, @JsonProperty("ask_counter_fee") int askCounterFee) {

    this.id = id;
    this.bidID = bidID;
    this.askID = askID;
    this.base = (base == 0 ? CoinfloorCurrency.BTC : CoinfloorUtils.getCurrency(base));
    this.counter = (counter == 0 ? CoinfloorCurrency.GBP : CoinfloorUtils.getCurrency(counter));
    this.baseQty = CoinfloorUtils.scaleToBigDecimal(this.base, baseQty);
    ;
    this.price = CoinfloorUtils.scalePriceToBigDecimal(this.base, this.counter, price);
    this.counterQty = CoinfloorUtils.scaleToBigDecimal(this.counter, counterQty);
    this.bidRem = CoinfloorUtils.scaleToBigDecimal(this.base, bidRem);
    this.askRem = CoinfloorUtils.scaleToBigDecimal(this.counter, askRem);
    this.time = time / 1000;
    this.bidBaseFee = CoinfloorUtils.scaleToBigDecimal(this.base, bidBaseFee);
    this.bidCounterFee = CoinfloorUtils.scaleToBigDecimal(this.counter, bidCounterFee);
    this.askBaseFee = CoinfloorUtils.scaleToBigDecimal(this.base, askBaseFee);
    this.askCounterFee = CoinfloorUtils.scaleToBigDecimal(this.counter, askCounterFee);
  }

  public int getId() {

    if (id == 0) {
      return (bidID > askID ? bidID : askID);
    }
    return id;
  }

  public int getBidId() {

    return bidID;
  }

  public int getAskId() {

    return askID;
  }

  public CoinfloorCurrency getBase() {

    return base;
  }

  public CoinfloorCurrency getCounter() {

    return counter;
  }

  public BigDecimal getBaseQty() {

    return baseQty;
  }

  public BigDecimal getPrice() {

    return price;
  }

  public BigDecimal getCounterQty() {

    return counterQty;
  }

  public BigDecimal getBidRem() {

    return bidRem;
  }

  public BigDecimal getAskRem() {

    return askRem;
  }

  public long getTime() {

    return time;
  }

  public BigDecimal getBaseFee() {

    if (bidBaseFee != null) {
      return bidBaseFee;
    } else if (askBaseFee != null) {
      return askBaseFee;
    }
    return null;
  }

  public BigDecimal getCounterFee() {

    if (bidCounterFee != null) {
      return bidCounterFee;
    } else if (askCounterFee != null) {
      return askCounterFee;
    }
    return null;
  }

  @Override
  public String toString() {

    return "CoinfloorOrder{id='" + id + "', bidID='" + bidID + "', askID='" + askID + "', base='" + base + "', counter='" + counter + "', baseQty='"
        + baseQty + "', price='" + price + "', counterQty='" + counterQty + "', bidRem='" + bidRem + "', askRem='" + askRem + "', bidBaseFee='"
        + bidBaseFee + "', bidCounterFee='" + bidCounterFee + "', askBaseFee='" + askBaseFee + "', askCounterFee='" + askCounterFee + "', time='"
        + time + "'}";
  }
}
