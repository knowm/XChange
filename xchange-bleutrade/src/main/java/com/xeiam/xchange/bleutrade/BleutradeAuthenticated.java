package com.xeiam.xchange.bleutrade;

import java.io.IOException;

import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.HeaderParam;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.MediaType;

import si.mazi.rescu.ParamsDigest;
import si.mazi.rescu.SynchronizedValueFactory;

import com.xeiam.xchange.bleutrade.dto.account.BleutradeBalanceReturn;
import com.xeiam.xchange.bleutrade.dto.account.BleutradeBalancesReturn;
import com.xeiam.xchange.bleutrade.dto.account.BleutradeDepositAddressReturn;
import com.xeiam.xchange.bleutrade.dto.trade.BleutradeCancelOrderReturn;
import com.xeiam.xchange.bleutrade.dto.trade.BleutradeOpenOrdersReturn;
import com.xeiam.xchange.bleutrade.dto.trade.BleutradePlaceOrderReturn;

@Path("v2")
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
public interface BleutradeAuthenticated extends Bleutrade {

  @GET
  @Path("account/getdepositaddress")
  BleutradeDepositAddressReturn getDepositAddress(@QueryParam("apikey") String apiKey, @HeaderParam("apisign") ParamsDigest signature,
      @QueryParam("nonce") SynchronizedValueFactory<Long> nonce, @QueryParam("currency") String currency) throws IOException, BleutradeException;

  @GET
  @Path("account/getbalance")
  BleutradeBalanceReturn getBalance(@QueryParam("apikey") String apiKey, @HeaderParam("apisign") ParamsDigest signature,
      @QueryParam("nonce") SynchronizedValueFactory<Long> nonce, @QueryParam("currency") String currency) throws IOException, BleutradeException;

  @GET
  @Path("account/getbalances")
  BleutradeBalancesReturn getBalances(@QueryParam("apikey") String apiKey, @HeaderParam("apisign") ParamsDigest signature,
      @QueryParam("nonce") SynchronizedValueFactory<Long> nonce) throws IOException, BleutradeException;

  @GET
  @Path("market/buylimit")
  BleutradePlaceOrderReturn buyLimit(@QueryParam("apikey") String apikey, @HeaderParam("apisign") ParamsDigest signature,
      @QueryParam("nonce") SynchronizedValueFactory<Long> nonce, @QueryParam("market") String market, @QueryParam("quantity") String quantity,
      @QueryParam("rate") String rate) throws IOException, BleutradeException;

  @GET
  @Path("market/selllimit")
  BleutradePlaceOrderReturn sellLimit(@QueryParam("apikey") String apikey, @HeaderParam("apisign") ParamsDigest signature,
      @QueryParam("nonce") SynchronizedValueFactory<Long> nonce, @QueryParam("market") String market, @QueryParam("quantity") String quantity,
      @QueryParam("rate") String rate) throws IOException, BleutradeException;

  @GET
  @Path("market/cancel")
  BleutradeCancelOrderReturn cancel(@QueryParam("apikey") String apiKey, @HeaderParam("apisign") ParamsDigest signature,
      @QueryParam("nonce") SynchronizedValueFactory<Long> nonce, @QueryParam("orderid") String orderid) throws IOException, BleutradeException;

  @GET
  @Path("market/getopenorders")
  BleutradeOpenOrdersReturn getOrders(@QueryParam("apikey") String apiKey, @HeaderParam("apisign") ParamsDigest signature,
      @QueryParam("nonce") SynchronizedValueFactory<Long> nonce) throws IOException, BleutradeException;

}
