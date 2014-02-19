/**
 * Copyright (C) 2012 - 2014 Xeiam LLC http://xeiam.com
 *
 * Permission is hereby granted, free of charge, to any person obtaining a copy of
 * this software and associated documentation files (the "Software"), to deal in
 * the Software without restriction, including without limitation the rights to
 * use, copy, modify, merge, publish, distribute, sublicense, and/or sell copies
 * of the Software, and to permit persons to whom the Software is furnished to do
 * so, subject to the following conditions:
 *
 * The above copyright notice and this permission notice shall be included in all
 * copies or substantial portions of the Software.
 *
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
 * IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
 * FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
 * AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
 * LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
 * OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE
 * SOFTWARE.
 */
package com.xeiam.xchange.dto.marketdata;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

/**
 * <p>
 * DTO representing a collection of trades
 * </p>
 */
public final class Trades {

  private final List<Trade> trades;
  private final long timestamp;
  private final TradeSortType tradeSortType;

  /**
   * Constructor
   * 
   * @param trades
   * @param tradeSortType
   */
  public Trades(List<Trade> trades, TradeSortType tradeSortType) {

    this(trades, 0L, tradeSortType);
  }

  /**
   * Constructor
   * 
   * @param trades The list of trades
   * @param timestamp
   */
  public Trades(List<Trade> trades, long timestamp, TradeSortType tradeSortType) {

    this.trades = new ArrayList<Trade>(trades);
    this.timestamp = timestamp;
    this.tradeSortType = tradeSortType;

    switch (tradeSortType) {
    case SortByTimestamp:
      Collections.sort(this.trades, new TradeTimestampComparator());
      break;
    case SortByID:
      Collections.sort(this.trades, new TradeIDComparator());
      break;

    default:
      break;
    }
  }

  /**
   * @return A list of trades ordered by id
   */
  public List<Trade> getTrades() {

    return trades;
  }

  /**
   * @return a Unique ID for the fetched trades
   */
  public long getTimestamp() {

    return timestamp;
  }

  public TradeSortType getTradeSortType() {

    return tradeSortType;
  }

  @Override
  public String toString() {

    StringBuilder sb = new StringBuilder("Trades\n");
    for (Trade trade : getTrades()) {
      sb.append("[trade=");
      sb.append(trade.toString());
      sb.append("]\n");
    }
    return sb.toString();
  }

  public enum TradeSortType {
    SortByTimestamp, SortByID
  }

  public class TradeTimestampComparator implements Comparator<Trade> {

    @Override
    public int compare(Trade trade1, Trade trade2) {

      return trade1.getTimestamp().compareTo(trade2.getTimestamp());
    }
  }

  public class TradeIDComparator implements Comparator<Trade> {

    @Override
    public int compare(Trade trade1, Trade trade2) {

      return trade1.getId().compareTo(trade2.getId());
    }
  }

}
