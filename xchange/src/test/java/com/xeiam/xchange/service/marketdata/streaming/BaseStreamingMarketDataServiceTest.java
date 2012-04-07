package com.xeiam.xchange.service.marketdata.streaming;

import mockit.Expectations;
import mockit.Mocked;
import org.junit.Ignore;
import org.junit.Test;

import java.net.MalformedURLException;

import static org.junit.Assert.fail;

@Ignore
public class BaseStreamingMarketDataServiceTest {

  @Mocked
  MarketDataListener marketDataListener;

  @Mocked
  MarketDataEvent marketDataEvent;

  @Test
  public void testFireMarketDataEvent_expectSuccess() throws MalformedURLException {
    
    BaseStreamingMarketDataService testObject = new BaseStreamingMarketDataService("example.com", 80) {

      @Override
      public void connect() {
      }

      @Override
      public void disconnect() {
      }
    };

    new Expectations() {{
      marketDataListener.onUpdate(marketDataEvent);
    }};
    
    testObject.fireMarketDataEvent(marketDataEvent);
    
  }

  @Test
  public void testFireMarketDataEvent_throwRuntime() throws MalformedURLException {

    BaseStreamingMarketDataService testObject = new BaseStreamingMarketDataService("example.com", 80) {

      @Override
      public void connect() {
      }

      @Override
      public void disconnect() {
      }
    };

    new Expectations() {{
      marketDataListener.onUpdate(marketDataEvent);
      result=new IllegalArgumentException();
    }};

    testObject.fireMarketDataEvent(marketDataEvent);

    try {
      testObject.removeListener(marketDataListener);
      fail("Expected exception");
    } catch (IllegalArgumentException e) {
      // Do nothing
    }

  }

}
