package org.knowm.xchange.cryptocompare.service;

import java.io.IOException;
import java.math.BigDecimal;
import java.time.Instant;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;

import javax.ws.rs.QueryParam;

import org.knowm.xchange.Exchange;
import org.knowm.xchange.cryptocompare.Cryptocompare;
import org.knowm.xchange.cryptocompare.CryptocompareExchange;
import org.knowm.xchange.cryptocompare.dto.marketdata.CryptocompareHistory;
import org.knowm.xchange.cryptocompare.dto.marketdata.CryptocompareOHLCV;
import org.knowm.xchange.currency.CurrencyPair;
import org.knowm.xchange.exceptions.ExchangeException;

import lombok.NonNull;
import lombok.ToString;
import lombok.extern.slf4j.Slf4j;
import si.mazi.rescu.RestProxyFactory;

/**
 * <p>
 * Implementation of the raw market data service for cryptocompare
 * </p>
 * <ul>
 * <li>Provides access to various market data values</li>
 * </ul>
 */
@ToString
@Slf4j
public class CryptocompareMarketDataServiceRaw extends CryptocompareBaseService {

  private final Cryptocompare proxy;

  /**
   * Constructor
   *
   * @param exchange
   */
  public CryptocompareMarketDataServiceRaw(Exchange exchange) {

    super(exchange);
    this.proxy = RestProxyFactory.createProxy(Cryptocompare.class, exchange.getExchangeSpecification().getSslUri(), getClientConfig());
  }

   /**
    * Basic get of hour history. No post-processing is performed.
    * 
   * @param targetExchange
   * @param currencyPair
   * @param itemLimit
   * @param toTimestamp optional
   * @param aggregateValue optional
   * 
   * @return
   * @throws IOException
   */
  public CryptocompareHistory getCryptocompareHourHistory1(
		  @NonNull String targetExchange, 
		  @NonNull CurrencyPair currencyPair, 
		  @NonNull Integer itemLimit,
		  Long toTimestamp,
		  Integer aggregateValue ) 
		  throws IOException {
	  
	String baseCurrency = currencyPair.base.toString();
	String counterCurrency = currencyPair.counter.toString();

    // Request data
    CryptocompareHistory cryptocompareHistory = proxy.getHourHistory(targetExchange, baseCurrency, counterCurrency, itemLimit, toTimestamp, aggregateValue);
    		

    return cryptocompareHistory;
  }
  
  
  /**
   * Get hour history from latest value for as many hours back as specified.
   * Returns time-ordered list of hour history data
   * 
   * @param targetExchange
   * @param currencyPair
   * @param numberOfHoursToGet
   * @param aggregateValue optional
   * 
   * @return
   * @throws IOException
   */
	public List<CryptocompareOHLCV> getCryptocompareHourHistory2(
					@NonNull String targetExchange, 
					@NonNull CurrencyPair currencyPair, 
					@NonNull Integer numberOfHoursToGet,
					Integer aggregateValue 
					) 
					throws IOException {
		
		Map<Long, CryptocompareOHLCV> mapData = new TreeMap<>();
		
		String baseCurrency = currencyPair.base.toString();
		String counterCurrency = currencyPair.counter.toString();
		
		int hoursRemaining = numberOfHoursToGet; 
		Long timeFrom = null;
 		
		boolean done = false;
		
		while (!done) {
			// only returns ~2000 at a time
		    CryptocompareHistory cryptocompareHistory = proxy.getHourHistory(targetExchange, baseCurrency, counterCurrency, hoursRemaining, timeFrom, aggregateValue);

		    log.debug( "timeFrom: {}({}), timeTo: {}({}), data size: {} \n",
		    		cryptocompareHistory.getTimeFrom(),
		    		Instant.ofEpochSecond(cryptocompareHistory.getTimeFrom()).toString(),
		    		cryptocompareHistory.getTimeTo(),
		    		Instant.ofEpochSecond(cryptocompareHistory.getTimeTo()).toString(),
		    		cryptocompareHistory.getOhlcvTuples().length );
		    
		    if (!cryptocompareHistory.getResponse().equals("Success")) {
		    	log.error( "Unexpected response: {}", cryptocompareHistory.getResponse() );
		    	throw new ExchangeException( "Unexpected response text returned: " + cryptocompareHistory.getResponse() );
		    }

		    if (cryptocompareHistory.getType() != 100) {
		    	log.error( "Unexpected type: {}", cryptocompareHistory.getType() );
		    	throw new ExchangeException( "Unexpected response type returned: " + cryptocompareHistory.getType() );
		    }
		    
			timeFrom = cryptocompareHistory.getTimeFrom() - 1 ;	// start time of samples
			hoursRemaining -= cryptocompareHistory.getOhlcvTuples().length;
			
			// data returned is NOT sorted.  Add to a TREEMAP to sort it and remove any duplicates whilst we are there
			
			for (CryptocompareOHLCV data : cryptocompareHistory.getOhlcvTuples()) {			
				if (!mapData.containsKey(data.getTimestamp())) { // new entry
					mapData.put(data.getTimestamp(), data);
				} else {
					log.warn("Duplicate timestamp: {}, record ignored.", data.getTimestamp());
				}	
			}
			
			// The following no longer works with the latest changes to cryptocompare (see below). 
			// But leave it in place in case it reverts as it does no harm.
			
			if ((hoursRemaining <= 0)
				||(cryptocompareHistory.getTimeFrom() <= 0)
				||(cryptocompareHistory.getOhlcvTuples().length <= 0)
				){
				done = true;
			}
		}
		
		
		// CryptoCompare is now sending empty values instead of saying it has no more data, eg:
		// Data [time=1434664800 (2015-06-18T22:00:00Z), open=0.0, high=0.0, low=0.0, close=0.0, volumeFrom=0.0, volumeTo=0.0]
		// To allow for the possibility of data gaps we have to do all the GETs as far back as we need then scan forward.
		// Iterate through the time-sorted list deleting everything until we find the first real data tuple.
		// Leave any embedded null tuples in place.
		//TODO: Consider terminating GETs on a complete block of null tuples. 
				
		Iterator<Long> iter = mapData.keySet().iterator();
		
		while (iter.hasNext()) {
			Long k = iter.next();
			
			CryptocompareOHLCV v = mapData.get(k);
			
			if (v.getOpen().compareTo(BigDecimal.ZERO) == 0
				&& v.getHigh().compareTo(BigDecimal.ZERO) == 0
				&& v.getLow().compareTo(BigDecimal.ZERO) == 0
				&& v.getClose().compareTo(BigDecimal.ZERO) == 0
				&& v.getVolumeFrom().compareTo(BigDecimal.ZERO) == 0
				&& v.getVolumeTo().compareTo(BigDecimal.ZERO) == 0) {
				iter.remove();
			} else {
				break; // stop when we reach first real value
			}

		}

		return new ArrayList<CryptocompareOHLCV>(mapData.values()); 
	}

}
