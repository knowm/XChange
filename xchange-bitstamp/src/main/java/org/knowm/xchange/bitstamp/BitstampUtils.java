package org.knowm.xchange.bitstamp;

import java.time.Instant;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeFormatterBuilder;
import java.time.format.DateTimeParseException;
import java.time.temporal.ChronoField;
import java.util.Date;
import org.knowm.xchange.exceptions.ExchangeException;

/** A central place for shared Bitstamp properties */
public final class BitstampUtils {

  public static final int MAX_TRANSACTIONS_PER_QUERY = 1000;

  private static final ZoneId BITSTAMP_DATE_TIME_ZONE_ID = ZoneId.of("UTC");
  private static final DateTimeFormatter BITSTAMP_DATE_TIME_PATTERN =
      new DateTimeFormatterBuilder()
          .appendPattern("yyyy-MM-dd HH:mm:ss")
          .appendFraction(ChronoField.MICRO_OF_SECOND, 0, 6, true)
          .toFormatter();

  /** private Constructor */
  private BitstampUtils() {}

  /**
   * Format a date String for Bitstamp
   *
   * @param dateString A {@code String} whose beginning should be parsed.
   * @return A {@link Date}
   */
  public static Date parseDate(String dateString) {
    try {
      if (dateString == null) {
        return null;
      }
      final Instant instant =
          LocalDateTime.parse(dateString, BITSTAMP_DATE_TIME_PATTERN)
              .atZone(BITSTAMP_DATE_TIME_ZONE_ID)
              .toInstant();
      return Date.from(instant);
    } catch (DateTimeParseException e) {
      throw new ExchangeException("Illegal date/time format", e);
    }
  }
}
