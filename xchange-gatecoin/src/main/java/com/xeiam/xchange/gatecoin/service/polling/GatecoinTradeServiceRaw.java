
package com.xeiam.xchange.gatecoin.service.polling;

import com.xeiam.xchange.Exchange;
import com.xeiam.xchange.gatecoin.GatecoinAuthenticated;
import com.xeiam.xchange.gatecoin.dto.trade.Results.GatecoinCancelOrderResult;
import com.xeiam.xchange.gatecoin.dto.trade.Results.GatecoinOrderResult;
import com.xeiam.xchange.gatecoin.dto.trade.Results.GatecoinPlaceOrderResult;
import com.xeiam.xchange.gatecoin.dto.trade.Results.GatecoinTradeHistoryResult;
import com.xeiam.xchange.gatecoin.service.GatecoinDigest;
import java.io.IOException;
import java.math.BigDecimal;
import java.util.Date;
import si.mazi.rescu.RestProxyFactory;

/**
 *
 * @author sumedha
 */
public class GatecoinTradeServiceRaw extends GatecoinBasePollingService {
  private final GatecoinAuthenticated gatecoinAuthenticated;
  private final GatecoinDigest signatureCreator;
  private final long now ;

  /**
   * @param exchange
   */
  public GatecoinTradeServiceRaw(Exchange exchange) {

    super(exchange);
    now = GetUnixDateNow();
    this.gatecoinAuthenticated = RestProxyFactory.createProxy(GatecoinAuthenticated.class, exchange.getExchangeSpecification().getSslUri());
    this.signatureCreator = GatecoinDigest.createInstance(exchange.getExchangeSpecification().getSecretKey(),exchange.getExchangeSpecification().getApiKey(),now);
    
  }

  public GatecoinOrderResult getGatecoinOpenOrders() throws IOException {
      
    return gatecoinAuthenticated.getOpenOrders(exchange.getExchangeSpecification().getApiKey(), signatureCreator, String.valueOf(now));
  }

   public GatecoinPlaceOrderResult placeGatecoinOrder(BigDecimal tradableAmount, BigDecimal price, String way, String code) throws IOException
   {
       return gatecoinAuthenticated.placeOrder(exchange.getExchangeSpecification().getApiKey(), signatureCreator, String.valueOf(now), tradableAmount,
        price, way, code); 
   
   }

  public GatecoinCancelOrderResult cancelGatecoinOrder(String orderId) throws IOException {

    return gatecoinAuthenticated.cancelOrder(exchange.getExchangeSpecification().getApiKey(), signatureCreator, String.valueOf(now), orderId);
  }
  
  public GatecoinCancelOrderResult cancelAllGatecoinOrders() throws IOException {

    return gatecoinAuthenticated.cancelAllOrders(exchange.getExchangeSpecification().getApiKey(), signatureCreator, String.valueOf(now));
  }

  public GatecoinTradeHistoryResult getGatecoinUserTrades(int count, long transactionId) throws IOException {
    return gatecoinAuthenticated.getUserTrades(exchange.getExchangeSpecification().getApiKey(), signatureCreator, String.valueOf(now),
        count,transactionId);
  }
  
   public GatecoinTradeHistoryResult getGatecoinUserTrades(int count) throws IOException {
    return gatecoinAuthenticated.getUserTrades(exchange.getExchangeSpecification().getApiKey(), signatureCreator, String.valueOf(now),
        count);
  }
  
  public GatecoinTradeHistoryResult getGatecoinUserTrades() throws IOException {
    return gatecoinAuthenticated.getUserTrades(exchange.getExchangeSpecification().getApiKey(), signatureCreator, String.valueOf(now));
  }


  private Long GetUnixDateNow()
  {
     return (new Date()).getTime() / 1000;     
   }
}
