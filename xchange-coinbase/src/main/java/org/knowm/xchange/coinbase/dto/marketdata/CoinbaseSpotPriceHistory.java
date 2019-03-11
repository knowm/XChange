package org.knowm.xchange.coinbase.dto.marketdata;

import com.fasterxml.jackson.databind.exc.InvalidFormatException;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import org.knowm.xchange.utils.DateUtils;

/** @author jamespedwards42 */
public class CoinbaseSpotPriceHistory {

  private static final Pattern historicalRateStringPatternInReverse =
      Pattern.compile(
          "(\\d{1,2}\\.\\d+),(\\d{2}:\\d{2}-\\d{2}:\\d{2}:\\d{2}T\\d{2}\\-\\d{2}-\\d{4})");
  private final List<CoinbaseHistoricalSpotPrice> spotPriceHistory;

  private CoinbaseSpotPriceHistory(List<CoinbaseHistoricalSpotPrice> spotPriceHistory) {

    this.spotPriceHistory = spotPriceHistory;
  }

  public static CoinbaseSpotPriceHistory fromRawString(String spotPriceHistoryString) {

    final List<CoinbaseHistoricalSpotPrice> historicalPrices = new ArrayList<>();
    // Parse in reverse because they are inconsistent with the number of decimals for the rates
    // which makes it difficult to differentiate from the following year. Going in reverse
    // we can rely on the comma.
    final String entireHistoryString =
        new StringBuilder(spotPriceHistoryString).reverse().toString();
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

      final CoinbaseHistoricalSpotPrice historicalSpotPrice =
          new CoinbaseHistoricalSpotPrice(timestamp, spotRate);
      historicalPrices.add(historicalSpotPrice);
    }
    Collections.sort(historicalPrices, Collections.reverseOrder());
    return new CoinbaseSpotPriceHistory(historicalPrices);
  }

  public List<CoinbaseHistoricalSpotPrice> getSpotPriceHistory() {

    return spotPriceHistory;
  }

  @Override
  public String toString() {

    return "CoinbaseSpotPriceHistory [spotPriceHistory=" + spotPriceHistory + "]";
  }
}
