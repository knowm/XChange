package org.knowm.xchange.cryptofacilities;

import java.io.IOException;
import java.math.BigDecimal;
import javax.ws.rs.FormParam;
import javax.ws.rs.GET;
import javax.ws.rs.HeaderParam;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.MediaType;
import org.knowm.xchange.cryptofacilities.dto.account.CryptoFacilitiesAccounts;
import org.knowm.xchange.cryptofacilities.dto.marketdata.CryptoFacilitiesCancel;
import org.knowm.xchange.cryptofacilities.dto.marketdata.CryptoFacilitiesCancelAllOrdersAfter;
import org.knowm.xchange.cryptofacilities.dto.marketdata.CryptoFacilitiesFills;
import org.knowm.xchange.cryptofacilities.dto.marketdata.CryptoFacilitiesOpenOrders;
import org.knowm.xchange.cryptofacilities.dto.marketdata.CryptoFacilitiesOpenPositions;
import org.knowm.xchange.cryptofacilities.dto.marketdata.CryptoFacilitiesOrder;
import org.knowm.xchange.cryptofacilities.dto.trade.BatchOrder;
import org.knowm.xchange.cryptofacilities.dto.trade.BatchOrderResult;
import si.mazi.rescu.ParamsDigest;
import si.mazi.rescu.SynchronizedValueFactory;

/** @author Jean-Christophe Laruelle */
@Path("/api/v3")
@Produces(MediaType.APPLICATION_JSON)
public interface CryptoFacilitiesAuthenticated extends CryptoFacilities {

  @GET
  @Path("accounts")
  CryptoFacilitiesAccounts accounts(
      @HeaderParam("APIKey") String apiKey,
      @HeaderParam("Authent") ParamsDigest signer,
      @HeaderParam("Nonce") SynchronizedValueFactory<Long> nonce)
      throws IOException;

  @POST
  @Path("sendorder")
  CryptoFacilitiesOrder sendOrder(
      @HeaderParam("APIKey") String apiKey,
      @HeaderParam("Authent") ParamsDigest signer,
      @HeaderParam("Nonce") SynchronizedValueFactory<Long> nonce,
      @QueryParam("orderType") String orderType,
      @QueryParam("symbol") String symbol,
      @QueryParam("side") String side,
      @QueryParam("size") BigDecimal size,
      @QueryParam("limitPrice") BigDecimal limitPrice)
      throws IOException;

  @POST
  @Path("batchorder")
  BatchOrderResult batchOrder(
      @HeaderParam("APIKey") String apiKey,
      @HeaderParam("Authent") ParamsDigest signer,
      @HeaderParam("Nonce") SynchronizedValueFactory<Long> nonce,
      @FormParam("json") BatchOrder orderCommands)
      throws IOException;

  @POST
  @Path("cancelorder")
  CryptoFacilitiesCancel cancelOrder(
      @HeaderParam("APIKey") String apiKey,
      @HeaderParam("Authent") ParamsDigest signer,
      @HeaderParam("Nonce") SynchronizedValueFactory<Long> nonce,
      @QueryParam("order_id") String order_id)
      throws IOException;

  @POST
  @Path("cancelallordersafter")
  CryptoFacilitiesCancelAllOrdersAfter cancelAllOrdersAfter(
      @HeaderParam("APIKey") String apiKey,
      @HeaderParam("Authent") ParamsDigest signer,
      @HeaderParam("Nonce") SynchronizedValueFactory<Long> nonce,
      @QueryParam("timeout") long timeoutSeconds)
      throws IOException;

  @GET
  @Path("openorders")
  CryptoFacilitiesOpenOrders openOrders(
      @HeaderParam("APIKey") String apiKey,
      @HeaderParam("Authent") ParamsDigest signer,
      @HeaderParam("Nonce") SynchronizedValueFactory<Long> nonce)
      throws IOException;

  @GET
  @Path("fills")
  CryptoFacilitiesFills fills(
      @HeaderParam("APIKey") String apiKey,
      @HeaderParam("Authent") ParamsDigest signer,
      @HeaderParam("Nonce") SynchronizedValueFactory<Long> nonce,
      @QueryParam("lastFillTime") String lastFillTime)
      throws IOException;

  @GET
  @Path("openpositions")
  CryptoFacilitiesOpenPositions openPositions(
      @HeaderParam("APIKey") String apiKey,
      @HeaderParam("Authent") ParamsDigest signer,
      @HeaderParam("Nonce") SynchronizedValueFactory<Long> nonce)
      throws IOException;
}
