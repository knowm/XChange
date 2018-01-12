package org.knowm.xchange.abucoins;

import java.io.IOException;

import javax.ws.rs.Consumes;
import javax.ws.rs.DELETE;
import javax.ws.rs.GET;
import javax.ws.rs.HeaderParam;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

import org.knowm.xchange.abucoins.dto.AbucoinsBaseCreateOrderRequest;
import org.knowm.xchange.abucoins.dto.account.AbucoinsAccount;
import org.knowm.xchange.abucoins.dto.account.AbucoinsPaymentMethod;
import org.knowm.xchange.abucoins.dto.marketdata.AbucoinsCreateOrderResponse;
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
  @Path("accounts/{account_id}")
  AbucoinsAccount getAccount(@HeaderParam("AC-ACCESS-KEY") String accessKey, @HeaderParam("AC-ACCESS-SIGN") ParamsDigest sign, @HeaderParam("AC-ACCESS-PASSPHRASE") String passphrase, @HeaderParam("AC-ACCESS-TIMESTAMP") String timestamp, @PathParam("account_id") String accountID) throws IOException;
  
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
  @Path("orders/{order_id}")
  AbucoinsOrder getOrder(@HeaderParam("AC-ACCESS-KEY") String accessKey, @HeaderParam("AC-ACCESS-SIGN") ParamsDigest sign, @HeaderParam("AC-ACCESS-PASSPHRASE") String passphrase, @HeaderParam("AC-ACCESS-TIMESTAMP") String timestamp, @PathParam("order_id") String orderID) throws IOException;
  
  @POST
  @Path("orders")
  AbucoinsCreateOrderResponse createOrder(@HeaderParam("AC-ACCESS-KEY") String accessKey, @HeaderParam("AC-ACCESS-SIGN") ParamsDigest sign, @HeaderParam("AC-ACCESS-PASSPHRASE") String passphrase, @HeaderParam("AC-ACCESS-TIMESTAMP") String timestamp, AbucoinsBaseCreateOrderRequest req) throws IOException;
  
  @DELETE
  @Path("orders/{order_id}")
  @Produces(MediaType.TEXT_PLAIN)
  String deleteOrder(@HeaderParam("AC-ACCESS-KEY") String accessKey, @HeaderParam("AC-ACCESS-SIGN") ParamsDigest sign, @HeaderParam("AC-ACCESS-PASSPHRASE") String passphrase, @HeaderParam("AC-ACCESS-TIMESTAMP") String timestamp, @PathParam("order_id") String orderID) throws IOException;
  
  @DELETE
  @Path("orders")
  @Produces(MediaType.TEXT_PLAIN)
  String deleteAllOrders(@HeaderParam("AC-ACCESS-KEY") String accessKey, @HeaderParam("AC-ACCESS-SIGN") ParamsDigest sign, @HeaderParam("AC-ACCESS-PASSPHRASE") String passphrase, @HeaderParam("AC-ACCESS-TIMESTAMP") String timestamp) throws IOException;
  
  @DELETE
  @Path("orders?product_id={product_id}")
  @Produces(MediaType.TEXT_PLAIN)
  String deleteAllOrdersForProduct(@HeaderParam("AC-ACCESS-KEY") String accessKey, @HeaderParam("AC-ACCESS-SIGN") ParamsDigest sign, @HeaderParam("AC-ACCESS-PASSPHRASE") String passphrase, @HeaderParam("AC-ACCESS-TIMESTAMP") String timestamp, @PathParam("product_id") String productID) throws IOException;
}
