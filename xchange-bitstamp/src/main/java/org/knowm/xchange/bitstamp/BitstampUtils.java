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

  // ISO 8601 format with timezone (e.g., "2025-11-15T02:09:13+00:00")
  private static final DateTimeFormatter ISO_8601_FORMATTER =
      DateTimeFormatter.ISO_OFFSET_DATE_TIME;

  /** private Constructor */
  private BitstampUtils() {}

  /**
   * Format a date String for Bitstamp. Supports both the legacy format ("yyyy-MM-dd HH:mm:ss") and
   * ISO 8601 format with timezone ("yyyy-MM-dd'T'HH:mm:ssXXX").
   *
   * @param dateString A {@code String} whose beginning should be parsed.
   * @return A {@link Date}
   */
  public static Date parseDate(String dateString) {
    try {
      if (dateString == null) {
        return null;
      }

      Instant instant;

      // Try ISO 8601 format first (with 'T' separator and timezone)
      if (dateString.contains("T")) {
        instant = Instant.from(ISO_8601_FORMATTER.parse(dateString));
      } else {
        // Try legacy format (space separator, no timezone)
        instant =
            LocalDateTime.parse(dateString, BITSTAMP_DATE_TIME_PATTERN)
                .atZone(BITSTAMP_DATE_TIME_ZONE_ID)
                .toInstant();
      }

      return Date.from(instant);
    } catch (DateTimeParseException e) {
      throw new ExchangeException("Illegal date/time format: " + dateString, e);
    }
  }
}
