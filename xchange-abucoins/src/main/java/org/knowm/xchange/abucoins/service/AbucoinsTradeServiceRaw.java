package org.knowm.xchange.abucoins.service;

import java.io.IOException;
import java.rmi.server.ExportException;
import java.util.ArrayList;
import java.util.List;
import org.knowm.xchange.Exchange;
import org.knowm.xchange.abucoins.AbucoinsAdapters;
import org.knowm.xchange.abucoins.dto.AbucoinsBaseCreateOrderRequest;
import org.knowm.xchange.abucoins.dto.AbucoinsError;
import org.knowm.xchange.abucoins.dto.AbucoinsOrderRequest;
import org.knowm.xchange.abucoins.dto.account.AbucoinsFills;
import org.knowm.xchange.abucoins.dto.marketdata.AbucoinsCreateOrderResponse;
import org.knowm.xchange.abucoins.dto.trade.AbucoinsOrder;
import org.knowm.xchange.abucoins.dto.trade.AbucoinsOrders;
import org.knowm.xchange.exceptions.ExchangeException;
import org.knowm.xchange.utils.ObjectMapperHelper;
import si.mazi.rescu.HttpStatusIOException;

/**
 * Class providing a 1:1 proxy for the Abucoins market related REST requests.
 *
 * <ul>
 *   <li>{@link #getAbucoinsOrders(AbucoinsOrderRequest) GET orders/&#123;order-id&#125;}
 *   <li>{@link #createAbucoinsOrder POST orders}
 *   <li>{@link #deleteAbucoinsOrder DELETE orders/&#123;order-id&#125;}
 *   <li>{@link #deleteAllAbucoinsOrders DELETE /orders or DELETE
 *       orders?product_id=&#123;product-id&#125;}
 *   <li>{@link #getAbucoinsFills GET /fills}
 *       <ol>
 *
 * @author bryant_harris
 */
public class AbucoinsTradeServiceRaw extends AbucoinsBaseService {

  public AbucoinsTradeServiceRaw(Exchange exchange) {
    super(exchange);
  }

  /**
   * Corresponds to <code>GET orders/{order-id}</delete> or <code>GET orders?status={status}</code> or
   * <code>orders?product_id={product-id}</code> or <code>orders?status={status}&product_id={product-id}</code>.
   *
   * @return
   * @throws IOException
   */
  public AbucoinsOrder[] getAbucoinsOrders(AbucoinsOrderRequest request) throws IOException {
    AbucoinsOrder.Status status = null;
    String productID = null;
    if (request != null) {
      status = request.getStatus();
      productID = request.getProductID();
    }

    if (status != null) {
      switch (status) {
        default:
        case open:
        case done:
          break;

        case pending:
        case rejected:
          throw new IllegalArgumentException(
              "/orders only accepts status of 'open' or 'done' not " + status);
      }
    }

    AbucoinsOrders retVal = null;
    if (status == null) {
      if (productID == null)
        retVal =
            abucoinsAuthenticated.getOrders(
                exchange.getExchangeSpecification().getApiKey(),
                signatureCreator,
                exchange.getExchangeSpecification().getPassword(),
                timestamp());
      else
        retVal =
            abucoinsAuthenticated.getOrdersByProductID(
                productID,
                exchange.getExchangeSpecification().getApiKey(),
                signatureCreator,
                exchange.getExchangeSpecification().getPassword(),
                timestamp());
    } else {
      if (productID == null)
        retVal =
            abucoinsAuthenticated.getOrdersByStatus(
                status.name(),
                exchange.getExchangeSpecification().getApiKey(),
                signatureCreator,
                exchange.getExchangeSpecification().getPassword(),
                timestamp());
      else
        retVal =
            abucoinsAuthenticated.getOrdersByStatusAndProductID(
                status.name(),
                productID,
                exchange.getExchangeSpecification().getApiKey(),
                signatureCreator,
                exchange.getExchangeSpecification().getPassword(),
                timestamp());
    }

    if (retVal.getOrders().length == 1 && retVal.getOrders()[0].getMessage() != null)
      throw new ExchangeException(retVal.getOrders()[0].getMessage());

    return retVal.getOrders();
  }

  /**
   * Helper method that wraps {@link #getAbucoinsOrders(AbucoinsOrderRequest)} allowing you to get
   * an order by order-id.
   *
   * @param orderID The OrderID of the order to retreive.
   * @return
   * @throws IOException
   */
  public AbucoinsOrder getAbucoinsOrder(String orderID) throws IOException {
    AbucoinsOrder order =
        abucoinsAuthenticated.getOrder(
            orderID,
            exchange.getExchangeSpecification().getApiKey(),
            signatureCreator,
            exchange.getExchangeSpecification().getPassword(),
            timestamp());
    if (order.getMessage() != null) throw new ExchangeException(order.getMessage());

    return order;
  }

  /**
   * Corresponds to <code>POST orders</delete>
   *
   * @return
   * @throws IOException
   */
  public AbucoinsCreateOrderResponse createAbucoinsOrder(AbucoinsBaseCreateOrderRequest req)
      throws IOException {
    AbucoinsCreateOrderResponse resp =
        abucoinsAuthenticated.createOrder(
            exchange.getExchangeSpecification().getApiKey(),
            signatureCreator,
            exchange.getExchangeSpecification().getPassword(),
            timestamp(),
            req);
    if (resp.getMessage() != null) throw new ExchangeException(resp.getMessage());

    return resp;
  }

  /**
   * Corresponds to <code>DELETE orders/{order-id}</delete>
   *
   * @return
   * @throws IOException
   */
  public String deleteAbucoinsOrder(String orderID) throws IOException {
    String resp =
        abucoinsAuthenticated.deleteOrder(
            orderID,
            exchange.getExchangeSpecification().getApiKey(),
            signatureCreator,
            exchange.getExchangeSpecification().getPassword(),
            timestamp());
    String[] ids = AbucoinsAdapters.adaptToSetOfIDs(resp);
    return ids[0];
  }

  /**
   * Corresponds to <code>DELETE /orders</code> or <code>DELETE orders?product_id={product-id}</delete>
   *
   * @return
   * @throws IOException
   */
  public String[] deleteAllAbucoinsOrders(String... productIDs) throws IOException {
    String res;
    if (productIDs.length == 0)
      return AbucoinsAdapters.adaptToSetOfIDs(
          abucoinsAuthenticated.deleteAllOrders(
              exchange.getExchangeSpecification().getApiKey(),
              signatureCreator,
              exchange.getExchangeSpecification().getPassword(),
              timestamp()));
    else {
      List<String> ids = new ArrayList<>();
      for (String productID : productIDs) {
        res =
            abucoinsAuthenticated.deleteAllOrdersForProduct(
                productID,
                exchange.getExchangeSpecification().getApiKey(),
                signatureCreator,
                exchange.getExchangeSpecification().getPassword(),
                timestamp());
        String[] deletedIds = AbucoinsAdapters.adaptToSetOfIDs(res);
        for (String id : deletedIds) ids.add(id);
      }
      return ids.toArray(new String[ids.size()]);
    }
  }

  /**
   * Corresponds to <code>GET /fills</code>
   *
   * @return
   * @throws IOException
   */
  public AbucoinsFills getAbucoinsFills(String afterCursor, Integer limit) throws IOException {
    try {
      AbucoinsFills fills =
          abucoinsAuthenticated.getFills(
              exchange.getExchangeSpecification().getApiKey(),
              signatureCreator,
              exchange.getExchangeSpecification().getPassword(),
              timestamp(),
              afterCursor,
              limit);
      return fills;
    } catch (HttpStatusIOException initialException) {
      // in case of error Abucoins returns HTTP status 200 with a single property JSON
      try {
        AbucoinsError error =
            ObjectMapperHelper.readValue(initialException.getHttpBody(), AbucoinsError.class);
        throw new ExchangeException(error.getMessage());
      } catch (IOException finalException) {
        throw new ExportException(
            "Response neither expected DTO nor error message", finalException);
      }
    }
  }
}
