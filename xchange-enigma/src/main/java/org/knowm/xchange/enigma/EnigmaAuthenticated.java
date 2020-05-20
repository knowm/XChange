package org.knowm.xchange.enigma;

import java.io.IOException;
import java.math.BigDecimal;
import java.util.List;
import java.util.Map;
import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import org.knowm.xchange.enigma.dto.BaseResponse;
import org.knowm.xchange.enigma.dto.marketdata.*;
import org.knowm.xchange.enigma.dto.trade.*;

@Path("")
@Produces(MediaType.APPLICATION_JSON)
public interface EnigmaAuthenticated extends Enigma {

  @GET
  @Path("product")
  List<EnigmaProduct> getProducts(@HeaderParam("Authorization") String accessToken)
      throws IOException;

  @GET
  @Path("xchange/indicative/market/data/{product-id}")
  EnigmaTicker getTicker(
      @HeaderParam("Authorization") String accessToken, @PathParam("product-id") int productId)
      throws IOException;

  @GET
  @Path("xchange/orderbook/{pair}")
  EnigmaOrderBook getOrderBook(
      @HeaderParam("Authorization") String accessToken, @PathParam("pair") String pair)
      throws IOException;

  @GET
  @Path("order/client/list/false/{infra}")
  EnigmaTransaction[] getTransactions(
      @HeaderParam("Authorization") String accessToken, @PathParam("infra") String infrastructure)
      throws IOException;

  @GET
  @Path("spot/{product-id}")
  EnigmaProductMarketData getProductMarketData(
      @HeaderParam("Authorization") String accessToken, @PathParam("product-id") int productId)
      throws IOException;

  @GET
  @Path("risk/limit")
  Map<String, BigDecimal> getAccountRiskLimits(@HeaderParam("Authorization") String accessToken)
      throws IOException;

  @POST
  @Path("order/new")
  @Consumes(MediaType.APPLICATION_JSON)
  EnigmaOrderSubmission submitOrder(
      @HeaderParam("Authorization") String accessToken, EnigmaNewOrderRequest orderRequest)
      throws IOException;

  @POST
  @Path("order/new")
  @Consumes(MediaType.APPLICATION_JSON)
  EnigmaOrderSubmission submitLimitOrder(
      @HeaderParam("Authorization") String accessToken, EnigmaLimitOrderRequest orderRequest)
      throws IOException;

  @GET
  @Path("xchange/cancel/order/")
  @Consumes(MediaType.APPLICATION_JSON)
  BaseResponse cancelOrder(@HeaderParam("Authorization") String accessToken) throws IOException;

  @POST
  @Path("rfq/new")
  @Consumes(MediaType.APPLICATION_JSON)
  EnigmaQuote askForQuote(
      @HeaderParam("Authorization") String accessToken, EnigmaQuoteRequest quoteRequest)
      throws IOException;

  @POST
  @Path("rfq/execute")
  @Consumes(MediaType.APPLICATION_JSON)
  EnigmaExecutedQuote executeQuoteRequest(
      @HeaderParam("Authorization") String accessToken, EnigmaExecuteQuoteRequest quoteRequest)
      throws IOException;

  @GET
  @Path("balance/{infra}")
  Map<String, BigDecimal> getBalance(
      @HeaderParam("Authorization") String accessToken, @PathParam("infra") String infrastructure)
      throws IOException;

  @GET
  @Path("settlement/list")
  List<EnigmaWithdrawal> getAllWithdrawals(@HeaderParam("Authorization") String accessToken);

  @POST
  @Path("withdrawal/new")
  EnigmaWithdrawal withdrawal(
      @HeaderParam("Authorization") String accessToken, EnigmaWithdrawalRequest withdrawalRequest);

  @POST
  @Path("settlement/new")
  EnigmaWithdrawal withdrawal(
      @HeaderParam("Authorization") String accessToken,
      EnigmaWithdrawFundsRequest withdrawalRequest);

  @GET
  @Path("account/show/currency/{currency}")
  List<Object> depositAddress(
      @HeaderParam("Authorization") String accessToken, @PathParam("currency") String currency);

  @GET
  @Path("xchange/order/open")
  EnigmaOpenOrders openOrders(@HeaderParam("Authorization") String accessToken);
}
