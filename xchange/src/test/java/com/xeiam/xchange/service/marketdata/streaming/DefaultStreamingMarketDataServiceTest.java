package com.xeiam.xchange.service.marketdata.streaming;

import org.junit.Ignore;
import org.junit.Test;

import java.net.MalformedURLException;
import java.util.concurrent.BlockingQueue;

@Ignore
public class DefaultStreamingMarketDataServiceTest {


  @Test
  public void testRegisterMarketDataListener_expectSuccess() throws MalformedURLException, InterruptedException {

    // Arrange
    DefaultStreamingMarketDataService testObject = new DefaultStreamingMarketDataService("intersango.com", 1337);
    RunnableMarketDataListener marketDataListener = new RunnableMarketDataListener() {
      @Override
      public void handleEvent(MarketDataEvent event) {
        System.out.println(event.getRawData());
      }

      @Override
      public BlockingQueue<MarketDataEvent> getMarketDataEventQueue() {
        return null;
      }
    };

    // Act
    testObject.registerMarketDataListener(marketDataListener);

    // Assert
    Thread.sleep(10000L);
  }

}
