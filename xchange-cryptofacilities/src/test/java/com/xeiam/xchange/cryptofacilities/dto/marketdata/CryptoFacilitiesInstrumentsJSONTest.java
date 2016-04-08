package com.xeiam.xchange.cryptofacilities.dto.marketdata;

import static org.fest.assertions.api.Assertions.assertThat;

import java.io.IOException;
import java.io.InputStream;
import java.math.BigDecimal;
import java.util.Calendar;
import java.util.GregorianCalendar;
import java.util.Iterator;
import java.util.List;

import org.junit.Test;

import com.fasterxml.jackson.databind.ObjectMapper;

/**
 * @author Neil Panchen
 */

public class CryptoFacilitiesInstrumentsJSONTest {

	@Test
	public void testUnmarshal() throws IOException {

	    // Read in the JSON from the example resources
	    InputStream is = CryptoFacilitiesInstrumentsJSONTest.class.getResourceAsStream("/marketdata/example-instruments-data.json");

	    // Use Jackson to parse it
	    ObjectMapper mapper = new ObjectMapper();
	    CryptoFacilitiesInstruments cryptoFacilitiesInstruments = mapper.readValue(is, CryptoFacilitiesInstruments.class);
	    
	    // Verify that the example data was unmarshalled correctly
	    assertThat(cryptoFacilitiesInstruments.isSuccess()).isTrue();
	    
	    List<CryptoFacilitiesInstrument> instruments = cryptoFacilitiesInstruments.getInstruments();
	    assertThat(instruments.size()).isEqualTo(10);
	    
	    Iterator<CryptoFacilitiesInstrument> it = instruments.iterator();
	    CryptoFacilitiesInstrument ct = it.next();
	    
	    assertThat(ct.getTradeable()).isTrue();
	    assertThat(ct.getSymbol()).isEqualTo("f-xbt:usd-apr16-w5");
	    assertThat(ct.getUnderlying()).isEqualTo("cf-hbpi");
	    assertThat(ct.getContractSize()).isEqualTo(new BigDecimal("1"));
	    assertThat(ct.getType()).isEqualTo("futures");
	    assertThat(ct.getTickSize()).isEqualTo(new BigDecimal("0.01"));
	    
	    Calendar cal = new GregorianCalendar();
	    //2016-04-29 17:00:00
	    cal.set(Calendar.YEAR, 2016);
	    cal.set(Calendar.MONTH, Calendar.APRIL);
	    cal.set(Calendar.DAY_OF_MONTH, 29);
	    cal.set(Calendar.HOUR_OF_DAY, 17);
	    cal.set(Calendar.MINUTE, 0);
	    cal.set(Calendar.SECOND, 0);
	    cal.set(Calendar.MILLISECOND, 0);
	    assertThat(ct.getLastTradingTime()).isEqualTo(cal.getTime());
	    
	  }


}
