package org.knowm.xchange.bleutrade;

import org.knowm.xchange.bleutrade.dto.account.BleutradeBalanceReturn;
import org.knowm.xchange.bleutrade.dto.account.BleutradeBalancesReturn;
import org.knowm.xchange.bleutrade.dto.account.BleutradeDepositAddressReturn;
import org.knowm.xchange.bleutrade.dto.account.BleutradeWithdrawReturn;
import org.knowm.xchange.bleutrade.dto.trade.BleutradeCancelOrderReturn;
import org.knowm.xchange.bleutrade.dto.trade.BleutradeOpenOrdersReturn;
import org.knowm.xchange.bleutrade.dto.trade.BleutradePlaceOrderReturn;
import org.knowm.xchange.bleutrade.dto.trade.BluetradeExecutedTradesWrapper;
import si.mazi.rescu.ParamsDigest;
import si.mazi.rescu.SynchronizedValueFactory;

import javax.ws.rs.GET;
import javax.ws.rs.HeaderParam;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.MediaType;
import java.io.IOException;
import java.math.BigDecimal;

@Path("v2")
@Produces(MediaType.APPLICATION_JSON)
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

  @GET
  @Path("account/getorders")
  BluetradeExecutedTradesWrapper getTrades(@QueryParam("apikey") String apiKey, @HeaderParam("apisign") ParamsDigest signature,
      @QueryParam("nonce") SynchronizedValueFactory<Long> nonce,
      @QueryParam("market") String market, @QueryParam("orderstatus") String orderStatus, @QueryParam("ordertype") String orderType) throws IOException, BleutradeException;

  @GET
  @Path("/account/withdraw")
  BleutradeWithdrawReturn withdraw(@QueryParam("apikey") String apiKey, @HeaderParam("apisign") ParamsDigest signature, @QueryParam("nonce") SynchronizedValueFactory<Long> nonce,
                                   @QueryParam("currency") String currency, @QueryParam("quantity") BigDecimal quantity, @QueryParam("address") String address) throws IOException, BleutradeException;
}
