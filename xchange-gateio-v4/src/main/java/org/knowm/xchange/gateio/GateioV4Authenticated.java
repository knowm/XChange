package org.knowm.xchange.gateio;

import java.io.IOException;
import java.util.List;
import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.HeaderParam;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.MediaType;
import org.knowm.xchange.gateio.dto.GateioException;
import org.knowm.xchange.gateio.dto.account.GateioCurrencyBalance;
import org.knowm.xchange.gateio.dto.account.GateioDepositAddress;
import org.knowm.xchange.gateio.dto.account.GateioOrder;
import org.knowm.xchange.gateio.dto.account.GateioWithdrawStatus;
import org.knowm.xchange.gateio.dto.account.GateioWithdrawalRecord;
import org.knowm.xchange.gateio.dto.account.GateioWithdrawalRequest;
import si.mazi.rescu.ParamsDigest;
import si.mazi.rescu.SynchronizedValueFactory;

@Path("api/v4")
@Produces(MediaType.APPLICATION_JSON)
public interface GateioV4Authenticated {

  @GET
  @Path("wallet/deposit_address")
  GateioDepositAddress getDepositAddress(
      @HeaderParam("KEY") String apiKey,
      @HeaderParam("Timestamp") SynchronizedValueFactory<Long> timestamp,
      @HeaderParam("SIGN") ParamsDigest signer,
      @QueryParam("currency") String currency
  ) throws IOException, GateioException;


  @GET
  @Path("wallet/withdraw_status")
  List<GateioWithdrawStatus> getWithdrawStatus(
      @HeaderParam("KEY") String apiKey,
      @HeaderParam("Timestamp") SynchronizedValueFactory<Long> timestamp,
      @HeaderParam("SIGN") ParamsDigest signer,
      @QueryParam("currency") String currency
  ) throws IOException, GateioException;


  @GET
  @Path("spot/accounts")
  List<GateioCurrencyBalance> getSpotAccounts(
      @HeaderParam("KEY") String apiKey,
      @HeaderParam("Timestamp") SynchronizedValueFactory<Long> timestamp,
      @HeaderParam("SIGN") ParamsDigest signer,
      @QueryParam("currency") String currency
  ) throws IOException, GateioException;


  @GET
  @Path("spot/orders")
  List<GateioOrder> listOrders(
      @HeaderParam("KEY") String apiKey,
      @HeaderParam("Timestamp") SynchronizedValueFactory<Long> timestamp,
      @HeaderParam("SIGN") ParamsDigest signer,
      @QueryParam("currency_pair") String currencyPair,
      @QueryParam("status") String status
  ) throws IOException, GateioException;


  @GET
  @Path("spot/orders/{order_id}")
  GateioOrder getOrder(
      @HeaderParam("KEY") String apiKey,
      @HeaderParam("Timestamp") SynchronizedValueFactory<Long> timestamp,
      @HeaderParam("SIGN") ParamsDigest signer,
      @PathParam("order_id") String orderId,
      @QueryParam("currency_pair") String currencyPair
  ) throws IOException, GateioException;


  @POST
  @Path("spot/orders")
  @Consumes(MediaType.APPLICATION_JSON)
  GateioOrder createOrder(
      @HeaderParam("KEY") String apiKey,
      @HeaderParam("Timestamp") SynchronizedValueFactory<Long> timestamp,
      @HeaderParam("SIGN") ParamsDigest signer,
      GateioOrder gateioOrder
  ) throws IOException, GateioException;


  @GET
  @Path("wallet/withdrawals")
  List<GateioWithdrawalRecord> getWithdrawals(
      @HeaderParam("KEY") String apiKey,
      @HeaderParam("Timestamp") SynchronizedValueFactory<Long> timestamp,
      @HeaderParam("SIGN") ParamsDigest signer,
      @QueryParam("currency") String currency
  ) throws IOException, GateioException;


  @POST
  @Path("withdrawals")
  @Consumes(MediaType.APPLICATION_JSON)
  GateioWithdrawalRecord withdraw(
      @HeaderParam("KEY") String apiKey,
      @HeaderParam("Timestamp") SynchronizedValueFactory<Long> timestamp,
      @HeaderParam("SIGN") ParamsDigest signer,
      GateioWithdrawalRequest gateioWithdrawalRequest
  ) throws IOException, GateioException;


}
