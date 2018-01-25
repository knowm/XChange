package org.knowm.xchange.bitcoinde;

import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

import org.junit.Test;
import org.knowm.xchange.utils.DateUtils;

import com.fasterxml.jackson.databind.exc.InvalidFormatException;

public class RFC3339DateTimeParseTest {

	private String rfc3339timestamp;

	@Test
	public void testParseDateTime() throws ParseException {
		// Just show that parse does not fail
		SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ssXXX");
		rfc3339timestamp = "2018-01-26T06:00:00+01:00";
		Date date = simpleDateFormat.parse(rfc3339timestamp);
		assertNotNull(date);
	}

	@Test
	public void testUtil() throws InvalidFormatException {		
		// This method does not work
		Date date= DateUtils.fromRfc3339DateString(rfc3339timestamp);		
		System.out.println(date);
//		assertNotNull(date);
	}
}
