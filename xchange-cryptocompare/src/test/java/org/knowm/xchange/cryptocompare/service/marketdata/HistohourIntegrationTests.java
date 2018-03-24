package org.knowm.xchange.cryptocompare.service.marketdata;

import static org.junit.Assert.*;

import java.math.BigDecimal;
import java.util.List;

import org.junit.Test;
import org.knowm.xchange.Exchange;
import org.knowm.xchange.ExchangeFactory;
import org.knowm.xchange.ExchangeSpecification;
import org.knowm.xchange.cryptocompare.CryptocompareExchange;
import org.knowm.xchange.cryptocompare.dto.marketdata.CryptocompareOHLCV;
import org.knowm.xchange.cryptocompare.dto.marketdata.CryptocompareHistory;
import org.knowm.xchange.cryptocompare.service.CryptocompareMarketDataServiceRaw;
import org.knowm.xchange.currency.CurrencyPair;

/**
 * Test integration for getCryptocompareTickerHourHistory
 * 
 * @author Ian Worthington
 */
public class HistohourIntegrationTests {
	static final long timeStampBeforeNow = 1500000000;		// test value for timestamps.  Anything at least couple of hours before now is fine	

  @Test
  public void getHourHistory1() throws Exception {
    ExchangeSpecification exchangeSpecification = new ExchangeSpecification(CryptocompareExchange.class.getName());
    
    Exchange exchange = ExchangeFactory.INSTANCE.createExchange(exchangeSpecification);
    CryptocompareMarketDataServiceRaw mdsRaw = (CryptocompareMarketDataServiceRaw) exchange.getMarketDataService();

    CryptocompareHistory cryptocompareHistory = mdsRaw.getCryptocompareHourHistory1("Cryptopia", new CurrencyPair("ZOI", "BTC"), 2, null, null);
    
    // Verify that the sample get contains something resembling valid data
    
    assertEquals("Unexpected response", "Success", cryptocompareHistory.getResponse()); 
    assertEquals("Unexpected type", 100, cryptocompareHistory.getType()); 
    assertTrue("Unexpected TimeFrom", cryptocompareHistory.getTimeFrom() > timeStampBeforeNow);
    assertTrue("Unexpected TimeTo", cryptocompareHistory.getTimeTo() > timeStampBeforeNow);
 
    // NB: Cryptocompare currently returns one more item than requested
    assertTrue("Unexpected ohlcvs length", cryptocompareHistory.getOhlcvTuples().length >= 2); 
    
    CryptocompareOHLCV t = cryptocompareHistory.getOhlcvTuples()[0];
    assertTrue("Unexpected ohlcv timestamp",  t.getTimestamp() > timeStampBeforeNow); 
    assertTrue("Unexpected ohlcv open",       t.getOpen().compareTo(BigDecimal.ZERO) > 0 ); 
    assertTrue("Unexpected ohlcv high",       t.getHigh().compareTo(BigDecimal.ZERO) > 0 ); 
    assertTrue("Unexpected ohlcv low",        t.getLow().compareTo(BigDecimal.ZERO) > 0); 
    assertTrue("Unexpected ohlcv close",      t.getClose().compareTo(BigDecimal.ZERO) > 0); 
    assertTrue("Unexpected ohlcv volumeFrom", t.getVolumeFrom().compareTo(BigDecimal.ZERO) > 0); 
    assertTrue("Unexpected ohlcv volumeTo",   t.getVolumeTo().compareTo(BigDecimal.ZERO) > 0); 
  }
  

  @Test
  public void getHourHistory2() throws Exception {
    ExchangeSpecification exchangeSpecification = new ExchangeSpecification(CryptocompareExchange.class.getName());
    
    Exchange exchange = ExchangeFactory.INSTANCE.createExchange(exchangeSpecification);
    CryptocompareMarketDataServiceRaw mdsRaw = (CryptocompareMarketDataServiceRaw) exchange.getMarketDataService();

    List<CryptocompareOHLCV> cryptocompareHistoryList = mdsRaw.getCryptocompareHourHistory2("Cryptopia", new CurrencyPair("BTC", "USDT"), 6000, null);

    // NB: Cryptocompare currently returns one more item than requested
    
    assertEquals("Unexpected ohlcvs length", 6001, cryptocompareHistoryList.size()); 
    
    CryptocompareOHLCV t = cryptocompareHistoryList.get(0);
    System.out.println(t);
    assertTrue("Unexpected ohlcv timestamp",  t.getTimestamp() > timeStampBeforeNow-1000*60*60*6000); 
    assertTrue("Unexpected ohlcv open",       t.getOpen().compareTo(BigDecimal.ZERO) > 0 ); 
    assertTrue("Unexpected ohlcv high",       t.getHigh().compareTo(BigDecimal.ZERO) > 0 ); 
    assertTrue("Unexpected ohlcv low",        t.getLow().compareTo(BigDecimal.ZERO) > 0); 
    assertTrue("Unexpected ohlcv close",      t.getClose().compareTo(BigDecimal.ZERO) > 0); 
    assertTrue("Unexpected ohlcv volumeFrom", t.getVolumeFrom().compareTo(BigDecimal.ZERO) > 0); 
    assertTrue("Unexpected ohlcv volumeTo",   t.getVolumeTo().compareTo(BigDecimal.ZERO) > 0); 
    
    assertTrue("Unexpected ohlcv timestamp",  cryptocompareHistoryList.get(0).getTimestamp() < cryptocompareHistoryList.get(6000).getTimestamp()); 
  }
}
