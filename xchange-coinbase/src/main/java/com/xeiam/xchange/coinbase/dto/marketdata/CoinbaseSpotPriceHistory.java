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
package com.xeiam.xchange.coinbase.dto.marketdata;

import java.io.IOException;
import java.io.StringReader;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.JsonDeserializer;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.xeiam.xchange.coinbase.dto.marketdata.CoinbaseSpotPriceHistory.CoibaseSpotPriceHistoryDeserializer;
import com.xeiam.xchange.utils.DateUtils;

/**
 * @author jamespedwards42
 */
@JsonDeserialize(using = CoibaseSpotPriceHistoryDeserializer.class)
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

  static class CoibaseSpotPriceHistoryDeserializer extends JsonDeserializer<CoinbaseSpotPriceHistory> {

    private static final Pattern historicalRateStringPatternInReverse = Pattern.compile("(\\d{1,2}\\.\\d+),(\\d{2}:\\d{2}-\\d{2}:\\d{2}:\\d{2}T\\d{2}\\-\\d{2}-\\d{4})");

    @Override
    public CoinbaseSpotPriceHistory deserialize(JsonParser jp, DeserializationContext ctxt) throws IOException, JsonProcessingException {

      final List<CoinbaseHistoricalSpotPrice> historicalPrices = new ArrayList<CoinbaseHistoricalSpotPrice>();

      // If there is a better way to get to the actual String returned please replace this section.
      final StringReader reader = (StringReader) ctxt.getParser().getTokenLocation().getSourceRef();
      reader.reset();
      final StringBuilder historyStringBuilder = new StringBuilder();
      for (int c = reader.read(); c > 0; c = reader.read())
        historyStringBuilder.append((char) c);

      // Parse in reverse because they are inconsistent with the number of decimals for the rates
      // which makes it difficult to differentiate from the following year. Going in reverse
      // we can rely on the comma.
      final String entireHistoryString = historyStringBuilder.reverse().toString();
      final Matcher matcher = historicalRateStringPatternInReverse.matcher(entireHistoryString);
      while (matcher.find()) {
        final String rateString = new StringBuilder(matcher.group(1)).reverse().toString();
        final BigDecimal spotRate = new BigDecimal(rateString);
        final String timestampString = new StringBuilder(matcher.group(2)).reverse().toString();
        final Date timestamp = DateUtils.fromISO8601DateString(timestampString);

        final CoinbaseHistoricalSpotPrice historicalSpotPrice = new CoinbaseHistoricalSpotPrice(timestamp, spotRate);
        historicalPrices.add(historicalSpotPrice);
      }

      return new CoinbaseSpotPriceHistory(historicalPrices);
    }
  }
}
