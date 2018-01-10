package org.knowm.xchange.abucoins;

import java.io.IOException;

import javax.ws.rs.Consumes;
import javax.ws.rs.DELETE;
import javax.ws.rs.GET;
import javax.ws.rs.HeaderParam;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

import org.knowm.xchange.abucoins.dto.account.AbucoinsAccount;
import org.knowm.xchange.abucoins.dto.account.AbucoinsPaymentMethod;
import org.knowm.xchange.abucoins.dto.trade.AbucoinsOrder;

import si.mazi.rescu.ParamsDigest;

@Path("/")
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
public interface AbucoinsAuthenticated extends Abucoins {
  @GET
  @Path("accounts")
  AbucoinsAccount[] getAccounts(@HeaderParam("AC-ACCESS-KEY") String accessKey, @HeaderParam("AC-ACCESS-SIGN") ParamsDigest sign, @HeaderParam("AC-ACCESS-PASSPHRASE") String passphrase, @HeaderParam("AC-ACCESS-TIMESTAMP") String timestamp) throws IOException;
  
  @GET
  @Path("accounts/{account-id}")
  AbucoinsAccount getAccount(@HeaderParam("AC-ACCESS-KEY") String accessKey, @HeaderParam("AC-ACCESS-SIGN") ParamsDigest sign, @HeaderParam("AC-ACCESS-PASSPHRASE") String passphrase, @HeaderParam("AC-ACCESS-TIMESTAMP") String timestamp, @PathParam("account-id") String accountID) throws IOException;
  
  @GET
  @Path("payment-methods")
  AbucoinsPaymentMethod[] getPaymentMethods(@HeaderParam("AC-ACCESS-KEY") String accessKey, @HeaderParam("AC-ACCESS-SIGN") ParamsDigest sign, @HeaderParam("AC-ACCESS-PASSPHRASE") String passphrase, @HeaderParam("AC-ACCESS-TIMESTAMP") String timestamp) throws IOException;
  
  @GET
  @Path("orders")
  AbucoinsOrder[] getOrders(@HeaderParam("AC-ACCESS-KEY") String accessKey, @HeaderParam("AC-ACCESS-SIGN") ParamsDigest sign, @HeaderParam("AC-ACCESS-PASSPHRASE") String passphrase, @HeaderParam("AC-ACCESS-TIMESTAMP") String timestamp) throws IOException;
  
  @GET
  @Path("orders?status={status}")
  AbucoinsOrder[] getOrdersByStatus(@HeaderParam("AC-ACCESS-KEY") String accessKey, @HeaderParam("AC-ACCESS-SIGN") ParamsDigest sign, @HeaderParam("AC-ACCESS-PASSPHRASE") String passphrase, @HeaderParam("AC-ACCESS-TIMESTAMP") String timestamp, @PathParam("status") String status) throws IOException;

  @GET
  @Path("orders?product_id={product_id}")
  AbucoinsOrder[] getOrdersByProductID(@HeaderParam("AC-ACCESS-KEY") String accessKey, @HeaderParam("AC-ACCESS-SIGN") ParamsDigest sign, @HeaderParam("AC-ACCESS-PASSPHRASE") String passphrase, @HeaderParam("AC-ACCESS-TIMESTAMP") String timestamp, @PathParam("product_id") String productID) throws IOException;
  
  @GET
  @Path("orders?status={status}&product_id={product_id}")
  AbucoinsOrder[] getOrdersByStatusAndProductID(@HeaderParam("AC-ACCESS-KEY") String accessKey, @HeaderParam("AC-ACCESS-SIGN") ParamsDigest sign, @HeaderParam("AC-ACCESS-PASSPHRASE") String passphrase, @HeaderParam("AC-ACCESS-TIMESTAMP") String timestamp, @PathParam("status") String status, @PathParam("product_id") String productID) throws IOException;
  
  @GET
  @Path("orders/{order-id}")
  AbucoinsOrder getOrder(@HeaderParam("AC-ACCESS-KEY") String accessKey, @HeaderParam("AC-ACCESS-SIGN") ParamsDigest sign, @HeaderParam("AC-ACCESS-PASSPHRASE") String passphrase, @HeaderParam("AC-ACCESS-TIMESTAMP") String timestamp, @PathParam("order_id") String orderID) throws IOException;
  
  @DELETE
  @Path("orders/{order-id}")
  void deleteOrder(@HeaderParam("AC-ACCESS-KEY") String accessKey, @HeaderParam("AC-ACCESS-SIGN") ParamsDigest sign, @HeaderParam("AC-ACCESS-PASSPHRASE") String passphrase, @HeaderParam("AC-ACCESS-TIMESTAMP") String timestamp, @PathParam("order_id") String orderID) throws IOException;
  
  @DELETE
  @Path("orders")
  void deleteAllOrders(@HeaderParam("AC-ACCESS-KEY") String accessKey, @HeaderParam("AC-ACCESS-SIGN") ParamsDigest sign, @HeaderParam("AC-ACCESS-PASSPHRASE") String passphrase, @HeaderParam("AC-ACCESS-TIMESTAMP") String timestamp) throws IOException;
  
  @DELETE
  @Path("orders?product_id={product_id}")
  void deleteAllOrdersForProduct(@HeaderParam("AC-ACCESS-KEY") String accessKey, @HeaderParam("AC-ACCESS-SIGN") ParamsDigest sign, @HeaderParam("AC-ACCESS-PASSPHRASE") String passphrase, @HeaderParam("AC-ACCESS-TIMESTAMP") String timestamp, @PathParam("product_id") String productID) throws IOException;
}
