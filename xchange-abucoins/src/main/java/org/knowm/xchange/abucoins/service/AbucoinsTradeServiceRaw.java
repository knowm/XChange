package org.knowm.xchange.abucoins.service;

import static org.knowm.xchange.utils.DateUtils.toUnixTimeNullSafe;

import java.io.IOException;
import java.util.Date;
import java.util.Map;

import org.knowm.xchange.Exchange;
import org.knowm.xchange.abucoins.dto.AbucoinsOrderRequest;
import org.knowm.xchange.abucoins.dto.trade.AbucoinsOrder;
import org.knowm.xchange.currency.CurrencyPair;
import org.knowm.xchange.dto.trade.LimitOrder;
import org.knowm.xchange.service.trade.params.TradeHistoryParamCurrencyPair;
import org.knowm.xchange.service.trade.params.TradeHistoryParamLimit;
import org.knowm.xchange.service.trade.params.TradeHistoryParams;
import org.knowm.xchange.service.trade.params.TradeHistoryParamsTimeSpan;

public class AbucoinsTradeServiceRaw extends AbucoinsBaseService {

  public AbucoinsTradeServiceRaw(Exchange exchange) {
    super(exchange);
  }
  
  public AbucoinsOrder[] getAbucoinsOrders(AbucoinsOrderRequest request) throws IOException {
    AbucoinsOrder.Status status = null;
    String productID = null;
    if ( request != null ) {
      status = request.getStatus();
      productID = request.getProductID();
    }
          
    if ( status != null ) {
      switch (status) {
      default:
      case open:
      case done:
        break;
                          
      case pending:
      case rejected:
        throw new IllegalArgumentException("/orders only accepts status of 'open' or 'done' not " + status);
      }
    }
          
    AbucoinsOrder[] retVal = null;
    if ( status == null ) {
      if ( productID == null )
        retVal = abucoinsAuthenticated.getOrders(exchange.getExchangeSpecification().getApiKey(),
                                                 signatureCreator,
                                                 exchange.getExchangeSpecification().getPassword(),
                                                 signatureCreator.timestamp());
      else
        retVal = abucoinsAuthenticated.getOrdersByProductID(exchange.getExchangeSpecification().getApiKey(),
                                                            signatureCreator,
                                                            exchange.getExchangeSpecification().getPassword(),
                                                            signatureCreator.timestamp(),
                                                            productID);
    }
    else {
      if ( productID == null )
        retVal = abucoinsAuthenticated.getOrdersByStatus(exchange.getExchangeSpecification().getApiKey(),
                                                         signatureCreator,
                                                         exchange.getExchangeSpecification().getPassword(),
                                                         signatureCreator.timestamp(),
                                                         status.name());
      else
        retVal = abucoinsAuthenticated.getOrdersByStatusAndProductID(exchange.getExchangeSpecification().getApiKey(),
                                                                     signatureCreator,
                                                                     exchange.getExchangeSpecification().getPassword(),
                                                                     signatureCreator.timestamp(),
                                                                     status.name(),
                                                                     productID);                  
    }
    
    return retVal;
  }
  
  public AbucoinsOrder getAbucoinsOrder(String orderID) throws IOException {
    return abucoinsAuthenticated.getOrder(exchange.getExchangeSpecification().getApiKey(),
                                          signatureCreator,
                                          exchange.getExchangeSpecification().getPassword(),
                                          signatureCreator.timestamp(),
                                          orderID);
  }
  
  public void deleteAbucoinsOrder(String orderID) throws IOException {
    abucoinsAuthenticated.deleteOrder(exchange.getExchangeSpecification().getApiKey(),
                                      signatureCreator,
                                      exchange.getExchangeSpecification().getPassword(),
                                      signatureCreator.timestamp(),
                                      orderID);
  }
  
  /**
   * Deletes all orders for the user.  If <em>productIDs</em> are supplied it will delete all order for the
   * supplied productIDs.
   * @param productIDs
   * @throws IOException
   */
  public void deleteAllAbucoinsOrders(String... productIDs) throws IOException {
    if ( productIDs.length == 0 )
      abucoinsAuthenticated.deleteAllOrders(exchange.getExchangeSpecification().getApiKey(),
                                            signatureCreator,
                                            exchange.getExchangeSpecification().getPassword(),
                                            signatureCreator.timestamp());
    else {
      for ( String productID : productIDs )
        abucoinsAuthenticated.deleteAllOrdersForProduct(exchange.getExchangeSpecification().getApiKey(),
                                                        signatureCreator,
                                                        exchange.getExchangeSpecification().getPassword(),
                                                        signatureCreator.timestamp(),
                                                        productID);
    }
  }

  public AbucoinsOrder placeAbucoinsLimitOrder(LimitOrder limitOrder) throws IOException {

    AbucoinsOrder order = null;/*AbucoinsAuthenticated.placeOrder(
        signatureCreator,
        limitOrder.getCurrencyPair().base.getCurrencyCode(),
        limitOrder.getCurrencyPair().counter.getCurrencyCode(),
        new PlaceOrderRequest(
            (limitOrder.getType() == BID ? AbucoinsOrder.Type.buy : AbucoinsOrder.Type.sell),
            limitOrder.getLimitPrice(),
            limitOrder.getOriginalAmount()
        ));
    if (order.getErrorMessage() != null) {
      throw new ExchangeException(order.getErrorMessage());
    }*/
    return order;
  }

}
