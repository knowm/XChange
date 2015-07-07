
package com.xeiam.xchange.gatecoin.service.polling;

import com.xeiam.xchange.Exchange;
import com.xeiam.xchange.currency.CurrencyPair;
import com.xeiam.xchange.dto.Order.OrderType;
import static com.xeiam.xchange.dto.Order.OrderType.BID;
import com.xeiam.xchange.dto.trade.LimitOrder;
import com.xeiam.xchange.dto.trade.MarketOrder;
import com.xeiam.xchange.dto.trade.OpenOrders;
import com.xeiam.xchange.dto.trade.UserTrades;
import com.xeiam.xchange.exceptions.ExchangeException;
import com.xeiam.xchange.gatecoin.GatecoinAdapters;

import com.xeiam.xchange.gatecoin.dto.trade.GatecoinOrder;
import com.xeiam.xchange.gatecoin.dto.trade.Results.GatecoinCancelOrderResult;
import com.xeiam.xchange.gatecoin.dto.trade.Results.GatecoinOrderResult;
import com.xeiam.xchange.gatecoin.dto.trade.Results.GatecoinPlaceOrderResult;
import com.xeiam.xchange.service.polling.trade.PollingTradeService;
import com.xeiam.xchange.service.polling.trade.params.DefaultTradeHistoryParamPaging;

import com.xeiam.xchange.service.polling.trade.params.TradeHistoryParams;
import com.xeiam.xchange.utils.DateUtils;
import java.io.IOException;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author sumedha
 */
public class GatecoinTradeService extends GatecoinTradeServiceRaw implements PollingTradeService {
    /**
   * Constructor
   *
   * @param exchange
   */
  public GatecoinTradeService(Exchange exchange) {

    super(exchange);
  }

  @Override
  public OpenOrders getOpenOrders() throws IOException {

    GatecoinOrderResult openOrdersResult = getGatecoinOpenOrders();

    List<LimitOrder> limitOrders = new ArrayList<LimitOrder>();
    for (GatecoinOrder gatecoinOrder : openOrdersResult.getOrders()) {
      OrderType orderType = gatecoinOrder.getType() == 0 ? OrderType.BID : OrderType.ASK;
      String id = gatecoinOrder.getClOrderId();
      BigDecimal price = gatecoinOrder.getPrice();
      CurrencyPair ccyPair = new CurrencyPair(gatecoinOrder.getCode().substring(0,2), gatecoinOrder.getCode().substring(3,5));
      limitOrders.add(new LimitOrder(orderType, gatecoinOrder.getInitialQuantity(), ccyPair, id, DateUtils.fromMillisUtc(Long.valueOf(gatecoinOrder.getDate()) * 1000L), price));
    }
    return new OpenOrders(limitOrders);
  }

  @Override
  public String placeMarketOrder(MarketOrder marketOrder) throws IOException {

     String ccyPair = marketOrder.getCurrencyPair().toString().replaceAll("/", "");
    GatecoinPlaceOrderResult gatecoinPlaceOrderResult;
    if (marketOrder.getType() == BID) {
      gatecoinPlaceOrderResult = placeGatecoinOrder(marketOrder.getTradableAmount(), BigDecimal.ZERO ,"BID",ccyPair);
    } else {
      gatecoinPlaceOrderResult = placeGatecoinOrder(marketOrder.getTradableAmount(),BigDecimal.ZERO ,"ASK",ccyPair);
    }
    
    return gatecoinPlaceOrderResult.getOrderId();
  }

  @Override
  public String placeLimitOrder(LimitOrder limitOrder) throws IOException {

    String ccyPair = limitOrder.getCurrencyPair().toString().replaceAll("/", "");
    GatecoinPlaceOrderResult gatecoinOrderResult;
    if (limitOrder.getType() == BID) {
      gatecoinOrderResult = placeGatecoinOrder(limitOrder.getTradableAmount(), limitOrder.getLimitPrice(),"BID",ccyPair);
    } else {
      gatecoinOrderResult = placeGatecoinOrder(limitOrder.getTradableAmount(), limitOrder.getLimitPrice(),"ASK",ccyPair);
    }  
    return gatecoinOrderResult.getOrderId();
  }

  @Override
  public boolean cancelOrder(String orderId) throws IOException {

      GatecoinCancelOrderResult response = null;
      if(orderId!= null && !orderId.isEmpty())
      {
          response = cancelGatecoinOrder(orderId);         
      }
      else
      {
          response = cancelAllGatecoinOrders();           
      }
      if(response!=null && response.getResponseStatus()!=null&& response.getResponseStatus().getMessage()!=null)
      {
        return response.getResponseStatus().getMessage().equalsIgnoreCase("OK");
      }
      else
      {
         return false;
      }
    
  }

  @Override
  public UserTrades getTradeHistory(Object... args) throws IOException {

    Long txId;
    int count ;
    if (args.length == 0)
    {
        return GatecoinAdapters.adaptTradeHistory(getGatecoinUserTrades());
    }
    else if (args.length == 1)
    {
      Object arg0 = args[0];
      if (!(arg0 instanceof Number)) {
        throw new ExchangeException("Argument must be a Number!");
      } else {
        count = ((Number) arg0).intValue();
        return GatecoinAdapters.adaptTradeHistory(getGatecoinUserTrades(count));
      }
    }
    else if (args.length == 2)
    {
      Object arg0 = args[0];
      Object arg1 = args[1];
      if (!(arg0 instanceof Number) || !(arg1 instanceof Number))
      {
        throw new ExchangeException("Both arguments must be a number!");
      }
      else
      {
        count = ((Number) arg0).intValue();
        txId = ((Number)arg1).longValue();
        return GatecoinAdapters.adaptTradeHistory(getGatecoinUserTrades(count,txId));
      }
    }
    else
    {
         throw new ExchangeException("Arguement length should be >=0 and <=2");   
    }    
  }

  
  @Override
  public UserTrades getTradeHistory(TradeHistoryParams params) throws IOException {

      throw new IOException("Not implemented.Use other overload.");
  }

  @Override
  public TradeHistoryParams createTradeHistoryParams() {

    return new DefaultTradeHistoryParamPaging(1000);
  }
}
