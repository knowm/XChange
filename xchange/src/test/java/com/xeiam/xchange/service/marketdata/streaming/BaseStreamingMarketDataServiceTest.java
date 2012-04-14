package com.xeiam.xchange.service.marketdata.streaming;

import org.junit.Ignore;
import org.junit.Test;

@Ignore
public class BaseStreamingMarketDataServiceTest {

  @Test
  public void testRegisterMarketDataListener_expectSuccess() throws Exception {

    // TODO Add mock ExchangeSpecification and mock producer

    // Arrange
    BaseStreamingMarketDataService testObject = new BaseStreamingMarketDataService(null);
    RunnableMarketDataListener marketDataListener = new RunnableMarketDataListener() {
      @Override
      public void handleEvent(MarketDataEvent event) {
        System.out.println(event.getRawData());
      }

    };

    // Act
    testObject.start(marketDataListener);

    // Assert
    Thread.sleep(10000L);
  }

}
