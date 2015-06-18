package com.xeiam.xchange.bitso.service.polling;

import java.io.IOException;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.xeiam.xchange.Exchange;
import com.xeiam.xchange.bitso.BitsoAdapters;
import com.xeiam.xchange.bitso.dto.marketdata.BitsoOrderBook;
import com.xeiam.xchange.bitso.dto.marketdata.BitsoTicker;
import com.xeiam.xchange.bitso.dto.marketdata.BitsoTransaction;
import com.xeiam.xchange.currency.CurrencyPair;
import com.xeiam.xchange.dto.marketdata.OrderBook;
import com.xeiam.xchange.dto.marketdata.Ticker;
import com.xeiam.xchange.dto.marketdata.Trades;
import com.xeiam.xchange.exceptions.ExchangeException;
import com.xeiam.xchange.exceptions.NotAvailableFromExchangeException;
import com.xeiam.xchange.exceptions.NotYetImplementedForExchangeException;
import com.xeiam.xchange.service.polling.marketdata.PollingMarketDataService;

/**
 * @author Piotr Ładyżyński
 */
public class BitsoMarketDataService extends BitsoMarketDataServiceRaw implements PollingMarketDataService {
  
  private static final Logger logger = LoggerFactory.getLogger(BitsoMarketDataService.class);
  private final String tooManyRequestsErrorMessage = "{code=200, message=Too many requests} (HTTP status code: 200)"; // could potentially change in the future.

  public interface PollingFunction<T> {
    public T doPolling(Object...args) throws IOException;
  }

  public BitsoMarketDataService(Exchange exchange) {
    super(exchange);
  }
  
  /**
   * This function takes as its first argument a 'PollingFunction' interface.  That interface has one method 'doPolling'
   * which will be called to return the specified type.  If an error is received whose message matches the input 
   * expectedErrorMessage, then the call to doPolling will be retried at most maxNumRetries times, waiting 
   * waitTimeInSeconds seconds between each retry.  
   * @param pFunc Interface containing the method to call.
   * @param maxNumRetries Maximum number of times to retry the pFunc.doPolling() method before giving up
   * @param waitTimeInSeconds Number of seconds to wait in between each retry
   * @param expectedErrorMessage The message of the exception that is expected in order to want to retry
   * @return T The type returned by pFunc.doPolling() 
   */
  private <T> T retry(PollingFunction<T> pFunc, int maxNumRetries, int waitTimeInSeconds, String expectedErrorMessage) {
    T obj = null;
    
    int numTries = 0;
    boolean ranOutOfTries = numTries == maxNumRetries;
    
    do {
      try {
        numTries++;
        obj = pFunc.doPolling();
      } catch (Exception e) {
        String message = e.getMessage();
        if (message.equals(expectedErrorMessage)) {
          logger.warn("Received error: {}", message);
          logger.warn("Waiting {} seconds and trying again.", waitTimeInSeconds);
          
          try {
            Thread.sleep(waitTimeInSeconds * 1000); 
          } catch (InterruptedException ex) {
            Thread.currentThread().interrupt();
          }
        } else {
          logger.warn("Returning null.  Received error: {}.", e);
          break;
        }
      }
      ranOutOfTries = numTries == maxNumRetries;
    } while (!ranOutOfTries && obj == null);
    
    if (ranOutOfTries) {
      logger.warn("Retried {} times.  Giving up and returning null.", maxNumRetries);
    }
    
    return obj;
  }

  @Override
  public Ticker getTicker(CurrencyPair currencyPair, Object... args) throws ExchangeException, NotAvailableFromExchangeException, NotYetImplementedForExchangeException, IOException {
    PollingFunction<BitsoTicker> tickerPollingFunc = new PollingFunction<BitsoTicker>() {

      @Override
      public BitsoTicker doPolling(Object...args) throws IOException {
        return getBitsoTicker();
      }
      
    };
    BitsoTicker ticker = retry(tickerPollingFunc, 20, 5, tooManyRequestsErrorMessage);
    return BitsoAdapters.adaptTicker(ticker, currencyPair);
  }

  @Override
  public OrderBook getOrderBook(CurrencyPair currencyPair, Object... args) throws IOException {
    PollingFunction<BitsoOrderBook> obPollingFunc = new PollingFunction<BitsoOrderBook>() {

      @Override
      public BitsoOrderBook doPolling(Object...args) throws IOException {
        return getBitsoOrderBook();
      }
      
    };
    BitsoOrderBook bitsoOrderBook = retry(obPollingFunc, 20, 5, tooManyRequestsErrorMessage);
    return BitsoAdapters.adaptOrderBook(bitsoOrderBook, currencyPair, 1000);
  }

  @Override
  public Trades getTrades(CurrencyPair currencyPair, Object... args) throws ExchangeException, NotAvailableFromExchangeException, NotYetImplementedForExchangeException, IOException {
    PollingFunction<BitsoTransaction[]> transactionPollingFunc = new PollingFunction<BitsoTransaction[]>() {

      @Override
      public BitsoTransaction[] doPolling(Object...args) throws IOException {
        return getBitsoTransactions(args);
      }
      
    };
    BitsoTransaction[] bitsoTransactions = retry(transactionPollingFunc, 20, 5, tooManyRequestsErrorMessage);
    return BitsoAdapters.adaptTrades(bitsoTransactions, currencyPair);
  }
}
