package org.knowm.xchange.bittrex;

import java.io.IOException;
import java.util.Date;
import java.util.List;
import jakarta.ws.rs.Consumes;
import jakarta.ws.rs.DELETE;
import jakarta.ws.rs.GET;
import jakarta.ws.rs.HeaderParam;
import jakarta.ws.rs.POST;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.PathParam;
import jakarta.ws.rs.Produces;
import jakarta.ws.rs.QueryParam;
import jakarta.ws.rs.core.MediaType;
import org.knowm.xchange.bittrex.dto.BittrexException;
import org.knowm.xchange.bittrex.dto.account.BittrexAccountVolume;
import org.knowm.xchange.bittrex.dto.account.BittrexAddress;
import org.knowm.xchange.bittrex.dto.account.BittrexBalance;
import org.knowm.xchange.bittrex.dto.account.BittrexBalances;
import org.knowm.xchange.bittrex.dto.account.BittrexComissionRatesWithMarket;
import org.knowm.xchange.bittrex.dto.account.BittrexDepositHistory;
import org.knowm.xchange.bittrex.dto.account.BittrexNewAddress;
import org.knowm.xchange.bittrex.dto.account.BittrexWithdrawalHistory;
import org.knowm.xchange.bittrex.dto.batch.BatchResponse;
import org.knowm.xchange.bittrex.dto.batch.order.BatchOrder;
import org.knowm.xchange.bittrex.dto.trade.BittrexNewOrder;
import org.knowm.xchange.bittrex.dto.trade.BittrexOrder;
import org.knowm.xchange.bittrex.dto.trade.BittrexOrders;
import org.knowm.xchange.bittrex.dto.withdrawal.BittrexNewWithdrawal;
import org.knowm.xchange.bittrex.dto.withdrawal.BittrexWithdrawal;
import si.mazi.rescu.ParamsDigest;

@Path("v3")
@Produces(MediaType.APPLICATION_JSON)
public interface BittrexAuthenticated extends Bittrex {

  @GET
  @Path("account/fees/trading")
  List<BittrexComissionRatesWithMarket> getTradingFees(
      @HeaderParam("Api-Key") String apiKey,
      @HeaderParam("Api-Timestamp") Long timestamp,
      @HeaderParam("Api-Content-Hash") ParamsDigest hash,
      @HeaderParam("Api-Signature") ParamsDigest signature)
      throws IOException, BittrexException;

  @GET
  @Path("account/volume")
  BittrexAccountVolume getAccountVolume(
      @HeaderParam("Api-Key") String apiKey,
      @HeaderParam("Api-Timestamp") Long timestamp,
      @HeaderParam("Api-Content-Hash") ParamsDigest hash,
      @HeaderParam("Api-Signature") ParamsDigest signature)
      throws IOException, BittrexException;

  @POST
  @Path("batch")
  @Consumes(MediaType.APPLICATION_JSON)
  BatchResponse[] executeOrdersBatch(
      @HeaderParam("Api-Key") String apiKey,
      @HeaderParam("Api-Timestamp") Long timestamp,
      @HeaderParam("Api-Content-Hash") ParamsDigest hash,
      @HeaderParam("Api-Signature") ParamsDigest signature,
      BatchOrder[] batchOrders)
      throws IOException, BittrexException;

  @DELETE
  @Path("orders/{order_id}")
  BittrexOrder cancelOrder(
      @HeaderParam("Api-Key") String apiKey,
      @HeaderParam("Api-Timestamp") Long timestamp,
      @HeaderParam("Api-Content-Hash") ParamsDigest hash,
      @HeaderParam("Api-Signature") ParamsDigest signature,
      @PathParam("order_id") String accountId)
      throws IOException, BittrexException;

  @GET
  @Path("balances")
  BittrexBalances getBalances(
      @HeaderParam("Api-Key") String apiKey,
      @HeaderParam("Api-Timestamp") Long timestamp,
      @HeaderParam("Api-Content-Hash") ParamsDigest hash,
      @HeaderParam("Api-Signature") ParamsDigest signature)
      throws IOException, BittrexException;

  @GET
  @Path("balances/{currencySymbol}")
  BittrexBalance getBalance(
      @HeaderParam("Api-Key") String apiKey,
      @HeaderParam("Api-Timestamp") Long timestamp,
      @HeaderParam("Api-Content-Hash") ParamsDigest hash,
      @HeaderParam("Api-Signature") ParamsDigest signature,
      @PathParam("currencySymbol") String currencySymbol)
      throws IOException, BittrexException;

  @GET
  @Path("addresses")
  List<BittrexAddress> getAddresses(
      @HeaderParam("Api-Key") String apiKey,
      @HeaderParam("Api-Timestamp") Long timestamp,
      @HeaderParam("Api-Content-Hash") ParamsDigest hash,
      @HeaderParam("Api-Signature") ParamsDigest signature)
      throws IOException, BittrexException;

  @GET
  @Path("addresses/{currencySymbol}")
  BittrexAddress getAddress(
      @HeaderParam("Api-Key") String apiKey,
      @HeaderParam("Api-Timestamp") Long timestamp,
      @HeaderParam("Api-Content-Hash") ParamsDigest hash,
      @HeaderParam("Api-Signature") ParamsDigest signature,
      @PathParam("currencySymbol") String currencySymbol)
      throws IOException, BittrexException;

  @POST
  @Path("addresses")
  BittrexAddress generateAddress(
      @HeaderParam("Api-Key") String apiKey,
      @HeaderParam("Api-Timestamp") Long timestamp,
      @HeaderParam("Api-Content-Hash") ParamsDigest hash,
      @HeaderParam("Api-Signature") ParamsDigest signature,
      BittrexNewAddress newAddress)
      throws IOException, BittrexException;

  @GET
  @Path("orders/{orderId}")
  BittrexOrder getOrder(
      @HeaderParam("Api-Key") String apiKey,
      @HeaderParam("Api-Timestamp") Long timestamp,
      @HeaderParam("Api-Content-Hash") ParamsDigest hash,
      @HeaderParam("Api-Signature") ParamsDigest signature,
      @PathParam("orderId") String orderId)
      throws IOException, BittrexException;

  @POST
  @Path("orders")
  @Consumes(MediaType.APPLICATION_JSON)
  BittrexOrder placeOrder(
      @HeaderParam("Api-Key") String apiKey,
      @HeaderParam("Api-Timestamp") Long timestamp,
      @HeaderParam("Api-Content-Hash") ParamsDigest hash,
      @HeaderParam("Api-Signature") ParamsDigest signature,
      BittrexNewOrder newOrderPayload)
      throws IOException, BittrexException;

  @GET
  @Path("orders/open")
  BittrexOrders getOpenOrders(
      @HeaderParam("Api-Key") String apiKey,
      @HeaderParam("Api-Timestamp") Long timestamp,
      @HeaderParam("Api-Content-Hash") ParamsDigest hash,
      @HeaderParam("Api-Signature") ParamsDigest signature)
      throws IOException, BittrexException;

  // V3 replacement for get order history
  @GET
  @Path("orders/closed")
  List<BittrexOrder> getClosedOrders(
      @HeaderParam("Api-Key") String apiKey,
      @HeaderParam("Api-Timestamp") Long timestamp,
      @HeaderParam("Api-Content-Hash") ParamsDigest hash,
      @HeaderParam("Api-Signature") ParamsDigest signature,
      @QueryParam("marketSymbol") String marketSymbol,
      @QueryParam("pageSize") Integer pageSize,
      @QueryParam("startDate") Date startDate,
      @QueryParam("endDate") Date endDate)
      throws IOException, BittrexException;

  @GET
  @Path("deposits/closed")
  List<BittrexDepositHistory> getDepositsClosed(
      @HeaderParam("Api-Key") String apiKey,
      @HeaderParam("Api-Timestamp") Long timestamp,
      @HeaderParam("Api-Content-Hash") ParamsDigest hash,
      @HeaderParam("Api-Signature") ParamsDigest signature,
      @QueryParam("currencySymbol") String currencySymbol,
      @QueryParam("nextPageToken") String nextPageToken,
      @QueryParam("previousPageToken") String previousPageToken,
      @QueryParam("pageSize") Integer pageSize)
      throws IOException, BittrexException;

  @GET
  @Path("withdrawals/closed")
  List<BittrexWithdrawalHistory> getWithdrawalsClosed(
      @HeaderParam("Api-Key") String apiKey,
      @HeaderParam("Api-Timestamp") Long timestamp,
      @HeaderParam("Api-Content-Hash") ParamsDigest hash,
      @HeaderParam("Api-Signature") ParamsDigest signature,
      @QueryParam("currencySymbol") String currencySymbol,
      @QueryParam("nextPageToken") String nextPageToken,
      @QueryParam("previousPageToken") String previousPageToken,
      @QueryParam("pageSize") Integer pageSize)
      throws IOException, BittrexException;

  @POST
  @Path("withdrawals")
  @Consumes(MediaType.APPLICATION_JSON)
  BittrexWithdrawal createNewWithdrawal(
      @HeaderParam("Api-Key") String apiKey,
      @HeaderParam("Api-Timestamp") Long timestamp,
      @HeaderParam("Api-Content-Hash") ParamsDigest hash,
      @HeaderParam("Api-Signature") ParamsDigest signature,
      BittrexNewWithdrawal newWithdrawal)
      throws IOException, BittrexException;
}