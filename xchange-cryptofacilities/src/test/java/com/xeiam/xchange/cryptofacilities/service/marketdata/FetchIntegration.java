package com.xeiam.xchange.cryptofacilities.service.marketdata;

import static org.fest.assertions.api.Assertions.assertThat;

import java.math.BigDecimal;
import java.security.KeyManagementException;
import java.security.NoSuchAlgorithmException;
import java.security.cert.X509Certificate;
import java.util.Date;

import javax.net.ssl.HostnameVerifier;
import javax.net.ssl.HttpsURLConnection;
import javax.net.ssl.SSLContext;
import javax.net.ssl.SSLSession;
import javax.net.ssl.TrustManager;
import javax.net.ssl.X509TrustManager;

import org.junit.Test;

import com.xeiam.xchange.Exchange;
import com.xeiam.xchange.ExchangeFactory;
import com.xeiam.xchange.ExchangeSpecification;
import com.xeiam.xchange.cryptofacilities.CryptoFacilitiesExchange;
import com.xeiam.xchange.cryptofacilities.dto.CryptoFacilitiesResult;
import com.xeiam.xchange.cryptofacilities.dto.marketdata.CryptoFacilitiesContract;
import com.xeiam.xchange.cryptofacilities.dto.marketdata.CryptoFacilitiesContracts;
import com.xeiam.xchange.cryptofacilities.dto.marketdata.CryptoFacilitiesIndex;
import com.xeiam.xchange.cryptofacilities.dto.marketdata.CryptoFacilitiesVolatility;
import com.xeiam.xchange.cryptofacilities.service.polling.CryptoFacilitiesMarketDataService;
import com.xeiam.xchange.cryptofacilities.service.polling.CryptoFacilitiesTradeService;
import com.xeiam.xchange.currency.CurrencyPair;
import com.xeiam.xchange.dto.Order.OrderType;
import com.xeiam.xchange.dto.marketdata.OrderBook;
import com.xeiam.xchange.dto.marketdata.Ticker;
import com.xeiam.xchange.dto.trade.LimitOrder;
import com.xeiam.xchange.dto.trade.OpenOrders;
import com.xeiam.xchange.dto.trade.UserTrades;
import com.xeiam.xchange.service.polling.account.PollingAccountService;

/**
 * @author Jean-Christophe Laruelle
 */

public class FetchIntegration {

	static {
	    
		disableSslVerification();
	}

	private static void disableSslVerification() {
	    try
	    {
	        // Create a trust manager that does not validate certificate chains
	        TrustManager[] trustAllCerts = new TrustManager[] {new X509TrustManager() {
	            public java.security.cert.X509Certificate[] getAcceptedIssuers() {
	                return null;
	            }
	            public void checkClientTrusted(X509Certificate[] certs, String authType) {
	            }
	            public void checkServerTrusted(X509Certificate[] certs, String authType) {
	            }
	        }
	        };

	        // Install the all-trusting trust manager
	        SSLContext sc = SSLContext.getInstance("SSL");
	        sc.init(null, trustAllCerts, new java.security.SecureRandom());
	        HttpsURLConnection.setDefaultSSLSocketFactory(sc.getSocketFactory());

	        // Create all-trusting host name verifier
	        HostnameVerifier allHostsValid = new HostnameVerifier() {
	            public boolean verify(String hostname, SSLSession session) {
	                return true;
	            }
	        };

	        // Install the all-trusting host verifier
	        HttpsURLConnection.setDefaultHostnameVerifier(allHostsValid);
	    } catch (NoSuchAlgorithmException e) {
	        e.printStackTrace();
	    } catch (KeyManagementException e) {
	        e.printStackTrace();
	    }
	}

	private void sleep()
	{
		try {
			Thread.sleep(1000);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
	}
	
  @Test
  public void publicTest() throws Exception {
	  
	  	Exchange exchange = ExchangeFactory.INSTANCE.createExchange(CryptoFacilitiesExchange.class.getName());
		CryptoFacilitiesMarketDataService marketDataService = (CryptoFacilitiesMarketDataService) exchange.getPollingMarketDataService();        
		
	    CryptoFacilitiesContracts contracts = marketDataService.getCryptoFacilitiesContracts();
	    System.out.println(contracts.toString());
	    
	    CryptoFacilitiesContract firstContract = contracts.getContracts().iterator().next();
			
		Ticker ticker = marketDataService.getTicker(new CurrencyPair(firstContract.getTradeable(), firstContract.getUnit()));
		System.out.println(ticker.toString());
		assertThat(ticker).isNotNull();
		
		OrderBook orderBook = marketDataService.getOrderBook(new CurrencyPair(firstContract.getTradeable(), firstContract.getUnit()));
		System.out.println(orderBook);
		
		CryptoFacilitiesIndex index = marketDataService.getCryptoFacilitiesIndex();
		System.out.println(index.toString());

		CryptoFacilitiesVolatility volatility = marketDataService.getCryptoFacilitiesVolatility();
		System.out.println(volatility.toString());

  }

  @Test
  public void authenticatedTest() throws Exception {
		
	    ExchangeSpecification exchangeSpecification = new ExchangeSpecification(CryptoFacilitiesExchange.class.getCanonicalName());
	    exchangeSpecification.setSslUri("https://176.31.224.165:9090/derivatives"); //Test Platform
	    exchangeSpecification.setHost("www.cryptofacilities.com");
	    exchangeSpecification.setPort(443);
	    exchangeSpecification.setExchangeName("CryptoFacilities");
	    exchangeSpecification.setExchangeDescription("CryptoFacilities is a bitcoin derivatives exchange operated by Crypto Facilities Ltd.");
	    exchangeSpecification.setApiKey("1u/l8oMu6AFN4f3uGYMGo+clvozEKPwlKSow36XNptqnzam+q9zXvfKr");
	    exchangeSpecification.setSecretKey("fv7o4MSNDhq7i8ZhX/VE6+b802DgHy+MHuSqO1xkY2vjgsau9c6Et/zk8b7s91CJgBmYlBvs++KpvcgluB4sOiuG");
	  
	  	Exchange exchange = ExchangeFactory.INSTANCE.createExchange(exchangeSpecification);
	  	PollingAccountService accountService = exchange.getPollingAccountService();        
		
	  	System.out.println(accountService.getAccountInfo().toString());
	  	
	  	sleep();
	  	
	    CryptoFacilitiesMarketDataService marketDataService = (CryptoFacilitiesMarketDataService) exchange.getPollingMarketDataService();        		
	    CryptoFacilitiesContracts contracts = marketDataService.getCryptoFacilitiesContracts();	    
	    CryptoFacilitiesContract firstContract = contracts.getContracts().iterator().next();

	    sleep();
	    
	  	CryptoFacilitiesTradeService tradeService = (CryptoFacilitiesTradeService) exchange.getPollingTradeService();
//	  	LimitOrder order = new LimitOrder(OrderType.BID, new BigDecimal("1.0"), new CurrencyPair(firstContract.getTradeable(), firstContract.getUnit()), "1", new Date(), new BigDecimal("300.07"));
//	  	String orderId = tradeService.placeLimitOrder(order);
//	  	System.out.println("orderId="+orderId);
	  	
//	  	sleep();
//	  	
//	  	CryptoFacilitiesResult res = tradeService.cancelCryptoFacilitiesOrder(orderId, new CurrencyPair(firstContract.getTradeable(), firstContract.getUnit()));
//	  	System.out.println("cancelled="+res.isSuccess());
	  	
	  	sleep();
	  	
	  	OpenOrders orders = tradeService.getOpenOrders();
	  	System.out.println(orders.toString());
	  	
	  	sleep();
	  	
	  	UserTrades trades = tradeService.getTradeHistory(null);
	  	System.out.println(trades.toString());
  }	

}
