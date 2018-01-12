package org.knowm.xchange.abucoins.service;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import org.knowm.xchange.Exchange;
import org.knowm.xchange.abucoins.AbucoinsAdapters;
import org.knowm.xchange.abucoins.dto.AbucoinsBaseCreateOrderRequest;
import org.knowm.xchange.abucoins.dto.AbucoinsOrderRequest;
import org.knowm.xchange.abucoins.dto.marketdata.AbucoinsCreateOrderResponse;
import org.knowm.xchange.abucoins.dto.trade.AbucoinsOrder;

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
  
  public AbucoinsCreateOrderResponse createAbucoinsOrder(AbucoinsBaseCreateOrderRequest req) throws IOException {
    return abucoinsAuthenticated.createOrder(exchange.getExchangeSpecification().getApiKey(),
                                             signatureCreator,
                                             exchange.getExchangeSpecification().getPassword(),
                                             signatureCreator.timestamp(),
                                             req);
  }
  
  public String deleteAbucoinsOrder(String orderID) throws IOException {
    String resp = abucoinsAuthenticated.deleteOrder(exchange.getExchangeSpecification().getApiKey(),
                                                    signatureCreator,
                                                    exchange.getExchangeSpecification().getPassword(),
                                                    signatureCreator.timestamp(),
                                                    orderID);
    String[] ids = AbucoinsAdapters.adaptToSetOfIDs(resp);
    return ids[0];
  }
  
  /**
   * Deletes all orders for the user.  If <em>productIDs</em> are supplied it will delete all order for the
   * supplied productIDs.
   * @param productIDs
   * @throws IOException
   */
  public String[] deleteAllAbucoinsOrders(String... productIDs) throws IOException {
    String res;
    if ( productIDs.length == 0 )
      return AbucoinsAdapters.adaptToSetOfIDs(abucoinsAuthenticated.deleteAllOrders(exchange.getExchangeSpecification().getApiKey(),
                                                                                    signatureCreator,
                                                                                    exchange.getExchangeSpecification().getPassword(),
                                                                                    signatureCreator.timestamp()));
    else {
      List<String> ids = new ArrayList<>();
      for ( String productID : productIDs ) {
        res = abucoinsAuthenticated.deleteAllOrdersForProduct(exchange.getExchangeSpecification().getApiKey(),
                                                              signatureCreator,
                                                              exchange.getExchangeSpecification().getPassword(),
                                                              signatureCreator.timestamp(),
                                                              productID);
        String[] deletedIds = AbucoinsAdapters.adaptToSetOfIDs(res);
        for ( String id : deletedIds )
            ids.add(id);
      }
      return ids.toArray(new String[ids.size()]);
    }
  }
}
