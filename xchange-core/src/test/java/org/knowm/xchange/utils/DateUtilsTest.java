package org.knowm.xchange.utils;

import static org.junit.Assert.assertEquals;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.TimeZone;

import org.junit.Test;

import com.fasterxml.jackson.databind.exc.InvalidFormatException;

public class DateUtilsTest {

  private final DateFormat dateFormat;

  public DateUtilsTest() {
    TimeZone tz = TimeZone.getTimeZone("UTC");
    dateFormat = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSS");
    dateFormat.setTimeZone(tz);
  }

  @Test
  public void testFromISODateString() throws Exception {
    String input = "2016-06-10T12:16:11.717Z";
    Date expectedOutput = dateFormat.parse("2016-06-10T12:16:11.717");

    assertEquals(expectedOutput, DateUtils.fromISODateString(input));
  }

  @Test(expected = InvalidFormatException.class)
  public void testFromISODateStringWrongTimezone() throws Exception {

    String input = "2016-06-10T12:16:11.717";
    Date expectedOutput = dateFormat.parse("2016-06-10T12:16:11.717");

    assertEquals(expectedOutput, DateUtils.fromISODateString(input));
  }
}
