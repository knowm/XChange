package org.knowm.xchange.quoine;

import java.io.IOException;
import jakarta.ws.rs.Consumes;
import jakarta.ws.rs.GET;
import jakarta.ws.rs.HeaderParam;
import jakarta.ws.rs.POST;
import jakarta.ws.rs.PUT;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.PathParam;
import jakarta.ws.rs.Produces;
import jakarta.ws.rs.QueryParam;
import jakarta.ws.rs.core.MediaType;
import org.knowm.xchange.quoine.dto.account.BitcoinAccount;
import org.knowm.xchange.quoine.dto.account.FiatAccount;
import org.knowm.xchange.quoine.dto.account.QuoineAccountBalance;
import org.knowm.xchange.quoine.dto.account.QuoineTradingAccountInfo;
import org.knowm.xchange.quoine.dto.trade.QuoineExecutionsResponse;
import org.knowm.xchange.quoine.dto.trade.QuoineNewOrderRequestWrapper;
import org.knowm.xchange.quoine.dto.trade.QuoineOrderDetailsResponse;
import org.knowm.xchange.quoine.dto.trade.QuoineOrderResponse;
import org.knowm.xchange.quoine.dto.trade.QuoineOrdersList;
import org.knowm.xchange.quoine.dto.trade.QuoineTradesResponse;
import org.knowm.xchange.quoine.dto.trade.QuoineTransactionsResponse;
import si.mazi.rescu.ParamsDigest;

@Path("/")
@Produces(MediaType.APPLICATION_JSON)
public interface QuoineAuthenticated extends Quoine {

  @GET
  @Path("fiat_accounts")
  FiatAccount[] getFiatAccountInfo(
      @HeaderParam("X-Quoine-API-Version") int apiVersion,
      @HeaderParam("X-Quoine-Auth") ParamsDigest signer,
      @HeaderParam("Content-Type") String contentType)
      throws IOException;

  @GET
  @Path("crypto_accounts")
  BitcoinAccount[] getCryptoAccountInfo(
      @HeaderParam("X-Quoine-API-Version") int apiVersion,
      @HeaderParam("X-Quoine-Auth") ParamsDigest signer,
      @HeaderParam("Content-Type") String contentType)
      throws IOException;

  @GET
  @Path("accounts/balance")
  QuoineAccountBalance[] getAllBalance(
      @HeaderParam("X-Quoine-API-Version") int apiVersion,
      @HeaderParam("X-Quoine-Auth") ParamsDigest signer,
      @HeaderParam("Content-Type") String contentType)
      throws IOException;

  @GET
  @Path("trading_accounts")
  QuoineTradingAccountInfo[] getTradingAccountInfo(
      @HeaderParam("X-Quoine-API-Version") int apiVersion,
      @HeaderParam("X-Quoine-Auth") ParamsDigest signer,
      @HeaderParam("Content-Type") String contentType)
      throws IOException;

  @POST
  @Path("orders/")
  @Consumes(MediaType.APPLICATION_JSON)
  QuoineOrderResponse placeOrder(
      @HeaderParam("X-Quoine-API-Version") int apiVersion,
      @HeaderParam("X-Quoine-Auth") ParamsDigest signer,
      @HeaderParam("Content-Type") String contentTyp,
      QuoineNewOrderRequestWrapper quoineNewOrderRequestWrapper)
      throws IOException;

  @PUT
  @Path("orders/{order_id}/cancel")
  QuoineOrderResponse cancelOrder(
      @HeaderParam("X-Quoine-API-Version") int apiVersion,
      @HeaderParam("X-Quoine-Auth") ParamsDigest signer,
      @HeaderParam("Content-Type") String contentTyp,
      @PathParam("order_id") String orderID)
      throws IOException;

  @GET
  @Path("orders/{order_id}")
  QuoineOrderDetailsResponse orderDetails(
      @HeaderParam("X-Quoine-API-Version") int apiVersion,
      @HeaderParam("X-Quoine-Auth") ParamsDigest signer,
      @HeaderParam("Content-Type") String contentTyp,
      @PathParam("order_id") String orderID)
      throws IOException;

  @GET
  @Path("orders")
  QuoineOrdersList listOrders(
      @HeaderParam("X-Quoine-API-Version") int apiVersion,
      @HeaderParam("X-Quoine-Auth") ParamsDigest signer,
      @HeaderParam("Content-Type") String contentType,
      @QueryParam("status") String status)
      throws IOException;

  @GET
  @Path("executions/me")
  QuoineExecutionsResponse executions(
      @HeaderParam("X-Quoine-API-Version") int apiVersion,
      @HeaderParam("X-Quoine-Auth") ParamsDigest signer,
      @HeaderParam("Content-Type") String contentType,
      @QueryParam("product_id") int productId,
      @QueryParam("limit") Integer limit,
      @QueryParam("page") Integer page,
      @QueryParam("with_details") int withDetails)
      throws IOException;

  @GET
  @Path("trades")
  QuoineTradesResponse trades(
      @HeaderParam("X-Quoine-API-Version") int apiVersion,
      @HeaderParam("X-Quoine-Auth") ParamsDigest signer,
      @HeaderParam("Content-Type") String contentType,
      @QueryParam("funding_currency") String fundingCurrency,
      @QueryParam("status") String status,
      @QueryParam("limit") Integer limit,
      @QueryParam("page") Integer page)
      throws IOException;

  @GET
  @Path("transactions")
  QuoineTransactionsResponse transactions(
      @HeaderParam("X-Quoine-API-Version") int apiVersion,
      @HeaderParam("X-Quoine-Auth") ParamsDigest signer,
      @HeaderParam("Content-Type") String contentType,
      @QueryParam("currency") String currency,
      @QueryParam("transaction_type") String transactionType,
      @QueryParam("limit") Integer limit,
      @QueryParam("page") Integer page)
      throws IOException;
}
