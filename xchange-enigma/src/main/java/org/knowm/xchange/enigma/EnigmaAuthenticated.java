package org.knowm.xchange.enigma;

import java.io.IOException;
import java.math.BigDecimal;
import java.util.List;
import java.util.Map;
import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.HeaderParam;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import org.knowm.xchange.enigma.dto.marketdata.EnigmaProduct;
import org.knowm.xchange.enigma.dto.marketdata.EnigmaProductMarketData;
import org.knowm.xchange.enigma.dto.trade.EnigmaExecuteQuoteRequest;
import org.knowm.xchange.enigma.dto.trade.EnigmaExecutedQuote;
import org.knowm.xchange.enigma.dto.trade.EnigmaNewOrder;
import org.knowm.xchange.enigma.dto.trade.EnigmaNewOrderRequest;
import org.knowm.xchange.enigma.dto.trade.EnigmaWithdrawlRequest;
import org.knowm.xchange.enigma.dto.trade.EnigmaWithdrawlResponse;

@Path("")
@Produces(MediaType.APPLICATION_JSON)
public interface EnigmaAuthenticated extends Enigma {

  @GET
  @Path("product")
  List<EnigmaProduct> getProducts(@HeaderParam("Authorization") String accessToken)
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
  EnigmaNewOrder submitOrder(
      @HeaderParam("Authorization") String accessToken, EnigmaNewOrderRequest orderRequest)
      throws IOException;

  @POST
  @Path("rfq/new")
  @Consumes(MediaType.APPLICATION_JSON)
  EnigmaExecutedQuote askForQuote(
      @HeaderParam("Authorization") String accessToken, EnigmaExecuteQuoteRequest quoteRequest)
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
  @Path("withdrawal/list")
  List<EnigmaWithdrawlResponse> getAllWithdrawals(@HeaderParam("Authorization") String accessToken);

  @POST
  @Path("withdrawal/new")
  EnigmaWithdrawlResponse withdrawl(
      @HeaderParam("Authorization") String accessToken, EnigmaWithdrawlRequest withdrawlRequest);
}
