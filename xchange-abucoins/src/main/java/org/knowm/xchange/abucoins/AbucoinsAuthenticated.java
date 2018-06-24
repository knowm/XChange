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
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.MediaType;
import org.knowm.xchange.abucoins.dto.AbucoinsBaseCreateOrderRequest;
import org.knowm.xchange.abucoins.dto.AbucoinsCryptoDepositRequest;
import org.knowm.xchange.abucoins.dto.AbucoinsCryptoWithdrawalRequest;
import org.knowm.xchange.abucoins.dto.account.AbucoinsAccount;
import org.knowm.xchange.abucoins.dto.account.AbucoinsAccounts;
import org.knowm.xchange.abucoins.dto.account.AbucoinsCryptoDeposit;
import org.knowm.xchange.abucoins.dto.account.AbucoinsCryptoWithdrawal;
import org.knowm.xchange.abucoins.dto.account.AbucoinsDepositsHistory;
import org.knowm.xchange.abucoins.dto.account.AbucoinsFills;
import org.knowm.xchange.abucoins.dto.account.AbucoinsPaymentMethods;
import org.knowm.xchange.abucoins.dto.account.AbucoinsWithdrawalsHistory;
import org.knowm.xchange.abucoins.dto.marketdata.AbucoinsCreateOrderResponse;
import org.knowm.xchange.abucoins.dto.trade.AbucoinsOrder;
import org.knowm.xchange.abucoins.dto.trade.AbucoinsOrders;
import si.mazi.rescu.ParamsDigest;

@Path("/")
@Produces(MediaType.APPLICATION_JSON)
public interface AbucoinsAuthenticated extends Abucoins {
  @GET
  @Path("accounts")
  AbucoinsAccounts getAccounts(
      @HeaderParam("AC-ACCESS-KEY") String accessKey,
      @HeaderParam("AC-ACCESS-SIGN") ParamsDigest sign,
      @HeaderParam("AC-ACCESS-PASSPHRASE") String passphrase,
      @HeaderParam("AC-ACCESS-TIMESTAMP") String timestamp)
      throws IOException;

  @GET
  @Path("accounts/{account-id}")
  AbucoinsAccount getAccount(
      @PathParam("account-id") String accountID,
      @HeaderParam("AC-ACCESS-KEY") String accessKey,
      @HeaderParam("AC-ACCESS-SIGN") ParamsDigest sign,
      @HeaderParam("AC-ACCESS-PASSPHRASE") String passphrase,
      @HeaderParam("AC-ACCESS-TIMESTAMP") String timestamp)
      throws IOException;

  @GET
  @Path("payment-methods")
  AbucoinsPaymentMethods getPaymentMethods(
      @HeaderParam("AC-ACCESS-KEY") String accessKey,
      @HeaderParam("AC-ACCESS-SIGN") ParamsDigest sign,
      @HeaderParam("AC-ACCESS-PASSPHRASE") String passphrase,
      @HeaderParam("AC-ACCESS-TIMESTAMP") String timestamp)
      throws IOException;

  @GET
  @Path("orders")
  AbucoinsOrders getOrders(
      @HeaderParam("AC-ACCESS-KEY") String accessKey,
      @HeaderParam("AC-ACCESS-SIGN") ParamsDigest sign,
      @HeaderParam("AC-ACCESS-PASSPHRASE") String passphrase,
      @HeaderParam("AC-ACCESS-TIMESTAMP") String timestamp)
      throws IOException;

  @GET
  @Path("orders?status={status}")
  AbucoinsOrders getOrdersByStatus(
      @PathParam("status") String status,
      @HeaderParam("AC-ACCESS-KEY") String accessKey,
      @HeaderParam("AC-ACCESS-SIGN") ParamsDigest sign,
      @HeaderParam("AC-ACCESS-PASSPHRASE") String passphrase,
      @HeaderParam("AC-ACCESS-TIMESTAMP") String timestamp)
      throws IOException;

  @GET
  @Path("orders?product_id={product-id}")
  AbucoinsOrders getOrdersByProductID(
      @PathParam("product-id") String productID,
      @HeaderParam("AC-ACCESS-KEY") String accessKey,
      @HeaderParam("AC-ACCESS-SIGN") ParamsDigest sign,
      @HeaderParam("AC-ACCESS-PASSPHRASE") String passphrase,
      @HeaderParam("AC-ACCESS-TIMESTAMP") String timestamp)
      throws IOException;

  @GET
  @Path("orders?status={status}&product_id={product-id}")
  AbucoinsOrders getOrdersByStatusAndProductID(
      @PathParam("status") String status,
      @PathParam("product-id") String productID,
      @HeaderParam("AC-ACCESS-KEY") String accessKey,
      @HeaderParam("AC-ACCESS-SIGN") ParamsDigest sign,
      @HeaderParam("AC-ACCESS-PASSPHRASE") String passphrase,
      @HeaderParam("AC-ACCESS-TIMESTAMP") String timestamp)
      throws IOException;

  @GET
  @Path("orders/{order-id}")
  AbucoinsOrder getOrder(
      @PathParam("order-id") String orderID,
      @HeaderParam("AC-ACCESS-KEY") String accessKey,
      @HeaderParam("AC-ACCESS-SIGN") ParamsDigest sign,
      @HeaderParam("AC-ACCESS-PASSPHRASE") String passphrase,
      @HeaderParam("AC-ACCESS-TIMESTAMP") String timestamp)
      throws IOException;

  @POST
  @Path("orders")
  @Consumes(MediaType.APPLICATION_JSON)
  AbucoinsCreateOrderResponse createOrder(
      @HeaderParam("AC-ACCESS-KEY") String accessKey,
      @HeaderParam("AC-ACCESS-SIGN") ParamsDigest sign,
      @HeaderParam("AC-ACCESS-PASSPHRASE") String passphrase,
      @HeaderParam("AC-ACCESS-TIMESTAMP") String timestamp,
      AbucoinsBaseCreateOrderRequest req)
      throws IOException;

  @DELETE
  @Path("orders/{order-id}")
  @Produces(MediaType.TEXT_PLAIN)
  String deleteOrder(
      @PathParam("order-id") String orderID,
      @HeaderParam("AC-ACCESS-KEY") String accessKey,
      @HeaderParam("AC-ACCESS-SIGN") ParamsDigest sign,
      @HeaderParam("AC-ACCESS-PASSPHRASE") String passphrase,
      @HeaderParam("AC-ACCESS-TIMESTAMP") String timestamp)
      throws IOException;

  @DELETE
  @Path("orders")
  @Produces(MediaType.TEXT_PLAIN)
  String deleteAllOrders(
      @HeaderParam("AC-ACCESS-KEY") String accessKey,
      @HeaderParam("AC-ACCESS-SIGN") ParamsDigest sign,
      @HeaderParam("AC-ACCESS-PASSPHRASE") String passphrase,
      @HeaderParam("AC-ACCESS-TIMESTAMP") String timestamp)
      throws IOException;

  @DELETE
  @Path("orders?product_id={product-id}")
  @Produces(MediaType.TEXT_PLAIN)
  String deleteAllOrdersForProduct(
      @PathParam("product-id") String productID,
      @HeaderParam("AC-ACCESS-KEY") String accessKey,
      @HeaderParam("AC-ACCESS-SIGN") ParamsDigest sign,
      @HeaderParam("AC-ACCESS-PASSPHRASE") String passphrase,
      @HeaderParam("AC-ACCESS-TIMESTAMP") String timestamp)
      throws IOException;

  @GET
  @Path("fills")
  AbucoinsFills getFills(
      @HeaderParam("AC-ACCESS-KEY") String accessKey,
      @HeaderParam("AC-ACCESS-SIGN") ParamsDigest sign,
      @HeaderParam("AC-ACCESS-PASSPHRASE") String passphrase,
      @HeaderParam("AC-ACCESS-TIMESTAMP") String timestamp,
      @QueryParam("after") String after,
      @QueryParam("limit") Integer limit)
      throws IOException;

  @POST
  @Path("withdrawals/make")
  @Consumes(MediaType.APPLICATION_JSON)
  AbucoinsCryptoWithdrawal withdrawalsMake(
      AbucoinsCryptoWithdrawalRequest cryptoWithdrawalRequest,
      @HeaderParam("AC-ACCESS-KEY") String accessKey,
      @HeaderParam("AC-ACCESS-SIGN") ParamsDigest sign,
      @HeaderParam("AC-ACCESS-PASSPHRASE") String passphrase,
      @HeaderParam("AC-ACCESS-TIMESTAMP") String timestamp)
      throws IOException;

  @GET
  @Path("withdrawals/history")
  AbucoinsWithdrawalsHistory withdrawalsHistory(
      @HeaderParam("AC-ACCESS-KEY") String accessKey,
      @HeaderParam("AC-ACCESS-SIGN") ParamsDigest sign,
      @HeaderParam("AC-ACCESS-PASSPHRASE") String passphrase,
      @HeaderParam("AC-ACCESS-TIMESTAMP") String timestamp)
      throws IOException;

  @POST
  @Path("deposits/make")
  @Consumes(MediaType.APPLICATION_JSON)
  AbucoinsCryptoDeposit depositsMake(
      AbucoinsCryptoDepositRequest cryptoRequest,
      @HeaderParam("AC-ACCESS-KEY") String accessKey,
      @HeaderParam("AC-ACCESS-SIGN") ParamsDigest sign,
      @HeaderParam("AC-ACCESS-PASSPHRASE") String passphrase,
      @HeaderParam("AC-ACCESS-TIMESTAMP") String timestamp)
      throws IOException;

  @GET
  @Path("deposits/history")
  AbucoinsDepositsHistory depositsHistory(
      @HeaderParam("AC-ACCESS-KEY") String accessKey,
      @HeaderParam("AC-ACCESS-SIGN") ParamsDigest sign,
      @HeaderParam("AC-ACCESS-PASSPHRASE") String passphrase,
      @HeaderParam("AC-ACCESS-TIMESTAMP") String timestamp)
      throws IOException;
}
