package org.knowm.xchange.cryptocompare.dto.marketdata;

import static org.junit.Assert.assertEquals;

import java.io.IOException;
import java.io.InputStream;
import java.math.BigDecimal;

import org.junit.Test;
import org.knowm.xchange.cryptocompare.dto.marketdata.CryptocompareHistory;

import com.fasterxml.jackson.databind.ObjectMapper;

/**
 * Test cryptocompareHistohour JSON parsing
 */
public class CyryptocompareHistohourTest {

  @Test
  public void testUnmarshal() throws IOException {

    // Read in the JSON from the example resources
	  
    InputStream is = CyryptocompareHistohourTest.class.getResourceAsStream("/marketdata/cryptocompare-histohour-data.json");

    // Use Jackson to parse it
    
    ObjectMapper mapper = new ObjectMapper();
    CryptocompareHistory cryptocompareTickerHistory = mapper.readValue(is, CryptocompareHistory.class);

    // Verify that the example data was unmarshalled correctly
    
    assertEquals("Unexpected response", "Success", cryptocompareTickerHistory.getResponse()); 
    assertEquals("Unexpected type", 100, cryptocompareTickerHistory.getType()); 
    assertEquals("Unexpected TimeFrom", 1452675600, cryptocompareTickerHistory.getTimeFrom()); 
    assertEquals("Unexpected TimeTo", 1452679200, cryptocompareTickerHistory.getTimeTo()); 
    
    assertEquals("Unexpected tickers length", 2, cryptocompareTickerHistory.getOhlcvTuples().length); 
    
    CryptocompareOHLCV t = cryptocompareTickerHistory.getOhlcvTuples()[0];
//    System.out.println(t);
    assertEquals("Unexpected ticker timestamp", 1452675600, t.getTimestamp()); 
    assertEquals("Unexpected ticker open",       new BigDecimal("0.002514"), t.getOpen()); 
    assertEquals("Unexpected ticker high",       new BigDecimal("0.002549"), t.getHigh()); 
    assertEquals("Unexpected ticker low",        new BigDecimal("0.002501"), t.getLow()); 
    assertEquals("Unexpected ticker close",      new BigDecimal("0.002536"), t.getClose()); 
    assertEquals("Unexpected ticker volumeFrom", new BigDecimal("24753.98"), t.getVolumeFrom()); 
    assertEquals("Unexpected ticker volumeTo",   new BigDecimal("62.27"),    t.getVolumeTo()); 
  }
}
