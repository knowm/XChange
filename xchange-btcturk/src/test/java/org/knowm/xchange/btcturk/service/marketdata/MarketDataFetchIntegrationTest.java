package org.knowm.xchange.btcturk.service.marketdata;

import static org.assertj.core.api.Assertions.assertThat;

import java.io.IOException;
import java.util.List;

import org.junit.Before;
import org.junit.Test;
import org.knowm.xchange.Exchange;
import org.knowm.xchange.ExchangeFactory;
import org.knowm.xchange.btcturk.BTCTurkExchange;
import org.knowm.xchange.btcturk.dto.marketdata.BTCTurkOHLC;
import org.knowm.xchange.btcturk.dto.marketdata.BTCTurkOrderBook;
import org.knowm.xchange.btcturk.service.BTCTurkMarketDataService;
import org.knowm.xchange.btcturk.service.BTCTurkMarketDataServiceRaw;
import org.knowm.xchange.currency.CurrencyPair;
import org.knowm.xchange.dto.marketdata.Ticker;
import org.knowm.xchange.dto.marketdata.Trades;
import org.knowm.xchange.service.marketdata.MarketDataService;
import org.knowm.xchange.service.marketdata.params.Params;

/** @author semihunaldi 
 *  @author mertguner */
public class MarketDataFetchIntegrationTest {

	private Exchange btcTurk;
	private BTCTurkMarketDataService btcTurkMarketDataService;
	private BTCTurkMarketDataServiceRaw btcTurkMarketDataServiceRaw;
	
	@Before
	public void InitExchange() throws IOException 
	{
    	btcTurk = ExchangeFactory.INSTANCE.createExchange(BTCTurkExchange.class.getName());
    	
    	MarketDataService marketDataService = btcTurk.getMarketDataService();
    	btcTurkMarketDataService = (BTCTurkMarketDataService)marketDataService;
    	btcTurkMarketDataServiceRaw = (BTCTurkMarketDataServiceRaw)marketDataService;
	}
	
  @Test
  public void TickerTest() throws Exception {
    Ticker ticker = btcTurkMarketDataService.getTicker(new CurrencyPair("BTC", "TRY"));
    System.out.println(ticker.toString());
    assertThat(ticker).isNotNull();
    
    List<Ticker> tickers = btcTurkMarketDataService.getTickers(new Params() {});
    for(Ticker _ticker : tickers)
    {
    	System.out.println(_ticker.toString());
        assertThat(_ticker).isNotNull();
    }
    
  }
  
  @Test
  public void TradesTest() throws Exception {
	  Trades trades = btcTurkMarketDataService.getTrades(CurrencyPair.BTC_TRY);
		assertThat(trades.getTrades().size()).isEqualTo(50);
		 
		trades = btcTurkMarketDataService.getTrades(CurrencyPair.BTC_TRY, 5);
		assertThat(trades.getTrades().size()).isEqualTo(5);
  }
  
  @Test
	public void OHCLTest() throws IOException 
	{
		List<BTCTurkOHLC> btcTurkBTCTurkOHLC = btcTurkMarketDataServiceRaw.getBTCTurkOHLC(CurrencyPair.BTC_TRY);
		assertThat(btcTurkBTCTurkOHLC.size()).isNotEqualTo(0); //Daily size is always changing
		
		List<BTCTurkOHLC> btcTurkBTCTurkOHLC2 = btcTurkMarketDataServiceRaw.getBTCTurkOHLC(CurrencyPair.BTC_TRY, 2);
		assertThat(btcTurkBTCTurkOHLC2.size()).isEqualTo(2);
	}
  
  @Test
	public void OrderBookTest() throws IOException 
	{
		BTCTurkOrderBook btcTurkBTCTurkOrderBook = btcTurkMarketDataServiceRaw.getBTCTurkOrderBook(CurrencyPair.BTC_TRY);
		assertThat(btcTurkBTCTurkOrderBook.getAsks().size()).isEqualTo(100); 
		assertThat(btcTurkBTCTurkOrderBook.getBids().size()).isEqualTo(100); 
	}
  
  
}
