package org.knowm.xchange.bitcoinde.dto.marketdata;

import com.fasterxml.jackson.annotation.JsonProperty;
import java.math.BigDecimal;
import java.util.Arrays;
import java.util.Comparator;
import java.util.Date;

/** @author matthewdowney */
public class BitcoindeOrderBook {

  private final BigDecimal[][] asks;
  private final BigDecimal[][] bids;
  private final Date timeStamp = null;

  /**
   * Constructor.
   *
   * @param asks
   * @param bids
   */
  public BitcoindeOrderBook(
      @JsonProperty("asks") BigDecimal[][] asks, @JsonProperty("bids") BigDecimal[][] bids) {

    /* set the asks and bids */
    this.asks = asks;
    this.bids = bids;

    /* sort the asks in ascending order by price */
    Arrays.sort(
        this.asks,
        new Comparator<BigDecimal[]>() {
          @Override
          public int compare(final BigDecimal[] entry1, final BigDecimal[] entry2) {
            final BigDecimal price1 = entry1[0]; // the first element of entry
            final BigDecimal price2 = entry2[0]; // is the price in EUR

            return price1.compareTo(price2);
          }
        });

    /* sort the bids in descending order by price */
    Arrays.sort(
        this.bids,
        new Comparator<BigDecimal[]>() {
          @Override
          public int compare(final BigDecimal[] entry1, final BigDecimal[] entry2) {
            final BigDecimal price1 = entry1[0]; // the first elements of entry
            final BigDecimal price2 = entry2[0]; // is price in EUR
            return -1
                * price1.compareTo(
                    price2); // multiply by -1 to reverse the order (we want descending)
          }
        });
  }

  public BigDecimal[][] getAsks() {

    return asks;
  }

  public BigDecimal[][] getBids() {

    return bids;
  }

  public Date getTimeStamp() {
    return this.timeStamp;
  }

  @Override
  public String toString() {

    StringBuilder asksBuilder = new StringBuilder();
    StringBuilder bidsBuilder = new StringBuilder();

    for (BigDecimal[] ask : getAsks()) {
      asksBuilder.append(Arrays.toString(ask) + ";");
    }

    for (BigDecimal[] bid : getBids()) {
      bidsBuilder.append(Arrays.toString(bid) + ";");
    }

    return "BitcoindeOrderBook{" + "asks=" + asksBuilder + ", bids=" + bidsBuilder + '}';
  }
}
