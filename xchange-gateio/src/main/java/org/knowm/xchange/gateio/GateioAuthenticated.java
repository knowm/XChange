package org.knowm.xchange.gateio;

import java.io.IOException;
import java.math.BigDecimal;
import java.util.Map;
import javax.ws.rs.Consumes;
import javax.ws.rs.FormParam;
import javax.ws.rs.HeaderParam;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import org.knowm.xchange.gateio.dto.GateioBaseResponse;
import org.knowm.xchange.gateio.dto.account.GateioDepositAddress;
import org.knowm.xchange.gateio.dto.account.GateioDepositsWithdrawals;
import org.knowm.xchange.gateio.dto.account.GateioFunds;
import org.knowm.xchange.gateio.dto.marketdata.GateioFeeInfo;
import org.knowm.xchange.gateio.dto.trade.GateioOpenOrders;
import org.knowm.xchange.gateio.dto.trade.GateioOrderStatus;
import org.knowm.xchange.gateio.dto.trade.GateioPlaceOrderReturn;
import org.knowm.xchange.gateio.dto.trade.GateioTradeHistoryReturn;
import si.mazi.rescu.ParamsDigest;

@Path("api2/1")
@Consumes(MediaType.APPLICATION_FORM_URLENCODED)
@Produces(MediaType.APPLICATION_JSON)
public interface GateioAuthenticated extends Gateio {

  @POST
  @Path("private/balances")
  GateioFunds getFunds(@HeaderParam("KEY") String apiKey, @HeaderParam("SIGN") ParamsDigest signer)
      throws IOException;

  @POST
  @Path("private/depositAddress")
  GateioDepositAddress getDepositAddress(
      @HeaderParam("KEY") String apiKey,
      @HeaderParam("SIGN") ParamsDigest signer,
      @FormParam("currency") String currency)
      throws IOException;

  @POST
  @Path("private/withdraw")
  GateioBaseResponse withdraw(
      @HeaderParam("KEY") String apiKey,
      @HeaderParam("SIGN") ParamsDigest signer,
      @FormParam("currency") String currency,
      @FormParam("amount") BigDecimal amount,
      @FormParam("address") String address)
      throws IOException;

  @POST
  @Path("private/cancelorder")
  GateioBaseResponse cancelOrder(
      @FormParam("orderNumber") String orderNumber,
      @FormParam("currencyPair") String currencyPair,
      @HeaderParam("KEY") String apiKey,
      @HeaderParam("SIGN") ParamsDigest signer)
      throws IOException;

  @POST
  @Path("private/cancelAllOrders")
  GateioBaseResponse cancelAllOrders(
      @FormParam("type") String type,
      @FormParam("currencyPair") String currencyPair,
      @HeaderParam("KEY") String apiKey,
      @HeaderParam("SIGN") ParamsDigest signer)
      throws IOException;

  @POST
  @Path("private/buy")
  GateioPlaceOrderReturn buy(
      @FormParam("currencyPair") String currencyPair,
      @FormParam("rate") BigDecimal rate,
      @FormParam("amount") BigDecimal amount,
      @HeaderParam("KEY") String apiKey,
      @HeaderParam("SIGN") ParamsDigest signer)
      throws IOException;

  @POST
  @Path("private/sell")
  GateioPlaceOrderReturn sell(
      @FormParam("currencyPair") String currencyPair,
      @FormParam("rate") BigDecimal rate,
      @FormParam("amount") BigDecimal amount,
      @HeaderParam("KEY") String apiKey,
      @HeaderParam("SIGN") ParamsDigest signer)
      throws IOException;

  @POST
  @Path("private/openOrders")
  GateioOpenOrders getOpenOrders(
      @HeaderParam("KEY") String apiKey, @HeaderParam("SIGN") ParamsDigest signer)
      throws IOException;

  @POST
  @Path("private/tradeHistory")
  GateioTradeHistoryReturn getUserTradeHistory(
      @HeaderParam("KEY") String apiKey,
      @HeaderParam("SIGN") ParamsDigest signer,
      @FormParam("currencyPair") String currencyPair)
      throws IOException;

  @POST
  @Path("private/depositsWithdrawals")
  GateioDepositsWithdrawals getDepositsWithdrawals(
      @HeaderParam("KEY") String apiKey,
      @HeaderParam("SIGN") ParamsDigest signer,
      @FormParam("start") Long startUnixTime,
      @FormParam("end") Long endUnixTime)
      throws IOException;

  @POST
  @Path("private/getorder")
  GateioOrderStatus getOrderStatus(
      @FormParam("orderNumber") String orderNumber,
      @FormParam("currencyPair") String currencyPair,
      @HeaderParam("KEY") String apiKey,
      @HeaderParam("SIGN") ParamsDigest signer)
      throws IOException;

  @POST
  @Path("private/feelist")
  Map<String, GateioFeeInfo> getFeeList(
      @HeaderParam("KEY") String apiKey, @HeaderParam("SIGN") ParamsDigest signer)
      throws IOException;
}
