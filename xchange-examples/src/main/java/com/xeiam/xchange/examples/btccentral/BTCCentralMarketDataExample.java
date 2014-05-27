package com.xeiam.xchange.examples.btccentral;

import java.io.IOException;

import com.xeiam.xchange.Exchange;
import com.xeiam.xchange.ExchangeFactory;
import com.xeiam.xchange.btccentral.BTCCentralExchange;
import com.xeiam.xchange.btccentral.service.polling.BTCCentralMarketDataServiceRaw;

/**
 * @author ObsessiveOrange
 */
public class BTCCentralMarketDataExample {
  
  public static void main(String[] args) throws IOException {
  
    Exchange btcCentralExchange = ExchangeFactory.INSTANCE.createExchange(BTCCentralExchange.class.getName());
    BTCCentralMarketDataServiceRaw btcCentralMarketDataServiceRaw = (BTCCentralMarketDataServiceRaw) btcCentralExchange.getPollingMarketDataService();
    
    System.out.println(btcCentralMarketDataServiceRaw.getBTCCentralTicker());
    System.out.println("\n");
    
    System.out.println(btcCentralMarketDataServiceRaw.getBTCCentralMarketDepth());
    System.out.println("\n");
    
    System.out.println(btcCentralMarketDataServiceRaw.getBTCCentralTrades());
    System.out.println("\n");
  }
  
}
