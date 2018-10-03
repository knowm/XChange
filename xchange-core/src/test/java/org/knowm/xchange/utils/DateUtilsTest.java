package org.knowm.xchange.utils;

import static org.junit.Assert.assertEquals;

import com.fasterxml.jackson.databind.exc.InvalidFormatException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.TimeZone;
import org.junit.Test;

public class DateUtilsTest {

  private final DateFormat isoDateFormat;
  private final DateFormat rfc3339DateFormat;

  public DateUtilsTest() {
    TimeZone tz = TimeZone.getTimeZone("UTC");
    isoDateFormat = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSS");
    isoDateFormat.setTimeZone(tz);

    rfc3339DateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
  }

  @Test
  public void testFromISODateString() throws Exception {
    String input = "2016-06-10T12:16:11.717Z";
    Date expectedOutput = isoDateFormat.parse("2016-06-10T12:16:11.717");

    assertEquals(expectedOutput, DateUtils.fromISODateString(input));
  }

  @Test(expected = InvalidFormatException.class)
  public void testFromISODateStringWrongTimezone() throws Exception {

    String input = "2016-06-10T12:16:11.717";
    Date expectedOutput = isoDateFormat.parse("2016-06-10T12:16:11.717");

    assertEquals(expectedOutput, DateUtils.fromISODateString(input));
  }

  @Test
  public void testFromRFC3339DateString() throws Exception {
    String input = "2018-01-15 12:16:11";
    Date expectedOutput = rfc3339DateFormat.parse("2018-01-15 12:16:11");

    assertEquals(expectedOutput, DateUtils.fromRfc3339DateString(input));
  }
}
