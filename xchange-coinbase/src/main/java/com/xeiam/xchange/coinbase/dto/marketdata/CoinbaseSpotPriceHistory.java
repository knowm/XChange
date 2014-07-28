/**
 * The MIT License
 * Copyright (c) 2012 Xeiam LLC http://xeiam.com
 *
 * Permission is hereby granted, free of charge, to any person obtaining a copy
 * of this software and associated documentation files (the "Software"), to deal
 * in the Software without restriction, including without limitation the rights
 * to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
 * copies of the Software, and to permit persons to whom the Software is
 * furnished to do so, subject to the following conditions:
 *
 * The above copyright notice and this permission notice shall be included in
 * all copies or substantial portions of the Software.
 *
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
 * IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
 * FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
 * AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
 * LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
 * OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN
 * THE SOFTWARE.
 */
package com.xeiam.xchange.coinbase.dto.marketdata;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import com.fasterxml.jackson.databind.exc.InvalidFormatException;
import com.xeiam.xchange.utils.DateUtils;

/**
 * @author jamespedwards42
 */
public class CoinbaseSpotPriceHistory {

  private final List<CoinbaseHistoricalSpotPrice> spotPriceHistory;

  private CoinbaseSpotPriceHistory(final List<CoinbaseHistoricalSpotPrice> spotPriceHistory) {

    this.spotPriceHistory = spotPriceHistory;
  }

  public List<CoinbaseHistoricalSpotPrice> getSpotPriceHistory() {

    return spotPriceHistory;
  }

  @Override
  public String toString() {

    return "CoinbaseSpotPriceHistory [spotPriceHistory=" + spotPriceHistory + "]";
  }

  private static final Pattern historicalRateStringPatternInReverse = Pattern.compile("(\\d{1,2}\\.\\d+),(\\d{2}:\\d{2}-\\d{2}:\\d{2}:\\d{2}T\\d{2}\\-\\d{2}-\\d{4})");

  public static CoinbaseSpotPriceHistory fromRawString(String spotPriceHistoryString) {

    final List<CoinbaseHistoricalSpotPrice> historicalPrices = new ArrayList<CoinbaseHistoricalSpotPrice>();
    // Parse in reverse because they are inconsistent with the number of decimals for the rates
    // which makes it difficult to differentiate from the following year. Going in reverse
    // we can rely on the comma.
    final String entireHistoryString = new StringBuilder(spotPriceHistoryString).reverse().toString();
    final Matcher matcher = historicalRateStringPatternInReverse.matcher(entireHistoryString);
    while (matcher.find()) {
      final String rateString = new StringBuilder(matcher.group(1)).reverse().toString();
      final BigDecimal spotRate = new BigDecimal(rateString);
      final String timestampString = new StringBuilder(matcher.group(2)).reverse().toString();
      Date timestamp = null;
      try {
        timestamp = DateUtils.fromISO8601DateString(timestampString);
      } catch (InvalidFormatException e) {
        e.printStackTrace();
      }

      final CoinbaseHistoricalSpotPrice historicalSpotPrice = new CoinbaseHistoricalSpotPrice(timestamp, spotRate);
      historicalPrices.add(historicalSpotPrice);
    }
    Collections.sort(historicalPrices, Collections.reverseOrder());
    return new CoinbaseSpotPriceHistory(historicalPrices);
  }
}
