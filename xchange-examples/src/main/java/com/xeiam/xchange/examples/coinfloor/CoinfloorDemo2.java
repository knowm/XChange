/**
 * Copyright (C) 2012 - 2014 Xeiam LLC http://xeiam.com
 *
 * Permission is hereby granted, free of charge, to any person obtaining a copy of
 * this software and associated documentation files (the "Software"), to deal in
 * the Software without restriction, including without limitation the rights to
 * use, copy, modify, merge, publish, distribute, sublicense, and/or sell copies
 * of the Software, and to permit persons to whom the Software is furnished to do
 * so, subject to the following conditions:
 *
 * The above copyright notice and this permission notice shall be included in all
 * copies or substantial portions of the Software.
 *
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
 * IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
 * FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
 * AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
 * LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
 * OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE
 * SOFTWARE.
 */
package com.xeiam.xchange.examples.coinfloor;

import java.math.BigDecimal;
import java.util.Map;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.TimeUnit;

import com.xeiam.xchange.Exchange;
import com.xeiam.xchange.ExchangeFactory;
import com.xeiam.xchange.ExchangeSpecification;
import com.xeiam.xchange.coinfloor.CoinfloorExchange;
import com.xeiam.xchange.coinfloor.dto.streaming.CoinfloorOrder;
import com.xeiam.xchange.coinfloor.dto.streaming.CoinfloorStreamingConfiguration;
import com.xeiam.xchange.coinfloor.dto.streaming.trade.CoinfloorOpenOrders;
import com.xeiam.xchange.coinfloor.streaming.CoinfloorStreamingExchangeService;
import com.xeiam.xchange.currency.CurrencyPair;
import com.xeiam.xchange.dto.Order.OrderType;
import com.xeiam.xchange.dto.trade.LimitOrder;
import com.xeiam.xchange.dto.trade.MarketOrder;
import com.xeiam.xchange.service.streaming.ExchangeStreamingConfiguration;
import com.xeiam.xchange.service.streaming.StreamingExchangeService;

/**
 * The purpose of this class is to show an alternative way. The methods themselves DO return data, and thus allows interaction
 * much in the same way as the polling data services. It looks messier, since the request and output are all clustered together,
 * but it allows for much more flexibility when retrieving data (Specifically, look at the section that cancels all orders). 
 * 
 * Doing it this way removes the need for a secondary queue to pull data that has been handled already by the executorService.
 * 
 * @author obsessiveOrange
 */
public class CoinfloorDemo2 {
	
  public static void main(String[] args) throws InterruptedException, ExecutionException {
	  ExchangeSpecification exSpec = new ExchangeSpecification(CoinfloorExchange.class);
		exSpec.setUserName("163");
		exSpec.setExchangeSpecificParametersItem("cookie", "X1UC55QE4WXNZMKfP4FfCsxKVfw=");
		exSpec.setPassword("2QvxAyUvPTIX8mrCvH");
		exSpec.setPlainTextUriStreaming("ws://api.coinfloor.co.uk");
		exSpec.setSslUriStreaming("wss://api.coinfloor.co.uk");
		exSpec.setHost("coinfloor.co.uk");
		exSpec.setPort(80);
		  
	    Exchange coinfloorExchange = ExchangeFactory.INSTANCE.createExchange(exSpec);
	    
	    //Streaming exchange service can be instantiated in one of two ways:
	    ////Using default vars (commented out to prevent conflicts for this demo)
	    //StreamingExchangeService streamingExchangeServiceDefault = ((CoinfloorExchange)coinfloorExchange).getStreamingExchangeService();
	    
	    ////Or with new vars:
	    ExchangeStreamingConfiguration exchangeStreamingConfiguration = new CoinfloorStreamingConfiguration(10, 10000, 30000, false, true, true);
	    StreamingExchangeService streamingExchangeService = coinfloorExchange.getStreamingExchangeService(exchangeStreamingConfiguration);
	        
	    //connect, and authenicate using username/cookie/password provided in exSpec
	    streamingExchangeService.connect();
	    
	    
	    
	    Map<String, Object> resultMap;
	    	    
	    //request AccountInfo data (balances)
	    resultMap = ((CoinfloorStreamingExchangeService)streamingExchangeService).getBalances().getPayload();
    	System.out.println("\n\n\n\n\nUser balances returned: ");
    	System.out.println("Raw Object: " + resultMap.get("raw"));
    	System.out.println("Generic Object: " + resultMap.get("generic"));
	    TimeUnit.MILLISECONDS.sleep(1000);

	    //request OpenOrders data
	    ((CoinfloorStreamingExchangeService)streamingExchangeService).getOrders().getPayload();
    	System.out.println("\n\n\n\n\nUser Open Orders: ");
    	System.out.println("Raw Object: " + resultMap.get("raw"));
    	System.out.println("Generic Object: " + resultMap.get("generic"));
	    TimeUnit.MILLISECONDS.sleep(1000);

	    //request 30-day trading volume for this user
	    ((CoinfloorStreamingExchangeService)streamingExchangeService).getTradeVolume("BTC").getPayload();
    	System.out.println("\n\n\n\n\nUser 30-Day Trade Volume: ");
    	System.out.println("Raw Object: " + resultMap.get("raw"));
    	System.out.println("Generic Object: " + resultMap.get("generic"));
	    TimeUnit.MILLISECONDS.sleep(1000);

	    //subscribe to ticker feed
	    ((CoinfloorStreamingExchangeService)streamingExchangeService).watchTicker("BTC", "GBP").getPayload();
    	System.out.println("\n\n\n\n\nSubscribed to Ticker feed: ");
    	System.out.println("Raw Object: " + resultMap.get("raw"));
    	System.out.println("Generic Object: " + resultMap.get("generic"));
	    TimeUnit.MILLISECONDS.sleep(1000);

	    //subscribe to orderbook
		((CoinfloorStreamingExchangeService)streamingExchangeService).watchOrders("BTC", "GBP").getPayload();
    	System.out.println("\n\n\n\n\nSubscribed to OrderBook feed: ");
    	System.out.println("Raw Object: " + resultMap.get("raw"));
    	System.out.println("Generic Object: " + resultMap.get("generic"));
	    TimeUnit.MILLISECONDS.sleep(1000);

	    //send two orders, that will (partially) fulfill each other, to generate a trade.
	    LimitOrder buyLimitOrder = new LimitOrder(OrderType.BID, new BigDecimal(1), new CurrencyPair("BTC", "GBP"), null, null, new BigDecimal(3.20));
	    ((CoinfloorStreamingExchangeService)streamingExchangeService).placeOrder(buyLimitOrder).getPayload();
    	System.out.println("\n\n\n\n\nBuy Limit Order Placed: ");
    	System.out.println("Raw Object: " + resultMap.get("raw"));
    	System.out.println("Generic Object: " + resultMap.get("generic"));
	    TimeUnit.MILLISECONDS.sleep(1000);
	    
	    	//check for new balance before submitting second order
		    System.out.println("\n\n\n\n\nCached Account Info: ");
		    System.out.println(((CoinfloorStreamingExchangeService)streamingExchangeService).getCachedAccountInfo());
	    
	    LimitOrder sellLimitOrder = new LimitOrder(OrderType.ASK, new BigDecimal(1.52321512784), new CurrencyPair("BTC", "GBP"), null, null, new BigDecimal(3.19));
	    ((CoinfloorStreamingExchangeService)streamingExchangeService).placeOrder(sellLimitOrder).getPayload();
    	System.out.println("\n\n\n\n\nSell Limit Order Placed: ");
    	System.out.println("Raw Object: " + resultMap.get("raw"));
    	System.out.println("Generic Object: " + resultMap.get("generic"));
	    TimeUnit.MILLISECONDS.sleep(1000);
	    
	    	//check for new balance again
		    System.out.println("\n\n\n\n\nCached Account Info: ");
		    System.out.println(((CoinfloorStreamingExchangeService)streamingExchangeService).getCachedAccountInfo());
	    
	    //then send another order, that will never be fulfilled
	    LimitOrder bigLimitOrder = new LimitOrder(OrderType.ASK, new BigDecimal(1.152), new CurrencyPair("BTC", "GBP"), null, null, new BigDecimal(500));
	    ((CoinfloorStreamingExchangeService)streamingExchangeService).placeOrder(bigLimitOrder).getPayload();
    	System.out.println("\n\n\n\n\nBig Limit Order Placed: ");
    	System.out.println("Raw Object: " + resultMap.get("raw"));
    	System.out.println("Generic Object: " + resultMap.get("generic"));
	    TimeUnit.MILLISECONDS.sleep(1000);
	    
	    	//check for new balance before submitting second order
		    System.out.println("\n\n\n\n\nCached Account Info: ");
		    System.out.println(((CoinfloorStreamingExchangeService)streamingExchangeService).getCachedAccountInfo());
	    
	    //request outcome of theoretical marketOrder 
	    MarketOrder estMarketOrder = new MarketOrder(OrderType.ASK, new BigDecimal(1), new CurrencyPair("BTC", "GBP"));
	    ((CoinfloorStreamingExchangeService)streamingExchangeService).estimateMarketOrder(estMarketOrder).getPayload();
    	System.out.println("\n\n\n\n\nEstimated Market Order: ");
    	System.out.println("Raw Object: " + resultMap.get("raw"));
    	System.out.println("Generic Object: " + resultMap.get("generic"));
	    TimeUnit.MILLISECONDS.sleep(1000);
	    
	    //get user's current open orders, cancel all of them.
	    CoinfloorOpenOrders openOrders = (CoinfloorOpenOrders) ((CoinfloorStreamingExchangeService)streamingExchangeService).getOrders().getPayloadItem("raw");
	    for(CoinfloorOrder order : openOrders.getOrders()){
	        ((CoinfloorStreamingExchangeService)streamingExchangeService).cancelOrder(order.getId()).getPayload();
	    	System.out.println("\n\n\n\n\nCancelled order: ");
	    	System.out.println("Raw Object: " + resultMap.get("raw"));
	    	System.out.println("Generic Object: " + resultMap.get("generic"));
		    TimeUnit.MILLISECONDS.sleep(1000);
	    	
		    
		    //check for new balance after each order cancelled
		    System.out.println("\n\n\n\n\nCached Account Info: ");
		    System.out.println(((CoinfloorStreamingExchangeService)streamingExchangeService).getCachedAccountInfo());
	    }
	    
	    //unsubscribe to ticker feed
	    ((CoinfloorStreamingExchangeService)streamingExchangeService).unwatchTicker("BTC", "GBP").getPayload();
    	System.out.println("\n\n\n\n\nUnwatched Ticker: ");
    	System.out.println("Raw Object: " + resultMap.get("raw"));
    	System.out.println("Generic Object: " + resultMap.get("generic"));
	    TimeUnit.MILLISECONDS.sleep(1000);

	    //unsubscribe to orderbook
		((CoinfloorStreamingExchangeService)streamingExchangeService).unwatchOrders("BTC", "GBP").getPayload();
    	System.out.println("\n\n\n\n\nUnwatched Orders: ");
    	System.out.println("Raw Object: " + resultMap.get("raw"));
    	System.out.println("Generic Object: " + resultMap.get("generic"));
	    TimeUnit.MILLISECONDS.sleep(1000);

	    TimeUnit.MINUTES.sleep(1);
	    
	    //These next three methods cache all the relevant information, and store them in memory. As new data comes in, it gets updated.
	    //This way, the user is not required to update the accountInfo, orderbook, or trades history.
	    //These methods are experimental, but still provided for convenience. 
	    
	    System.out.println("\n\n\n\n\nCached Account Info: ");
	    System.out.println(((CoinfloorStreamingExchangeService)streamingExchangeService).getCachedAccountInfo());

	    System.out.println("\n\n\n\n\nCached OrderBook: ");
	    System.out.println(((CoinfloorStreamingExchangeService)streamingExchangeService).getCachedOrderBook());

	    System.out.println("\n\n\n\n\nCached Trades: ");
	    System.out.println(((CoinfloorStreamingExchangeService)streamingExchangeService).getCachedTrades());

	    // Disconnect and exit
	    System.out.println(Thread.currentThread().getName() + ": Disconnecting...");
	    streamingExchangeService.disconnect();
	    System.exit(0);
  }
}
