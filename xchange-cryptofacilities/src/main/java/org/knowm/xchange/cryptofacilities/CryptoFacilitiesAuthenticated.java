package org.knowm.xchange.cryptofacilities;

import java.io.IOException;
import java.math.BigDecimal;

import javax.ws.rs.Consumes;
import javax.ws.rs.HeaderParam;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.MediaType;

import org.knowm.xchange.cryptofacilities.dto.account.CryptoFacilitiesAccount;
import org.knowm.xchange.cryptofacilities.dto.marketdata.CryptoFacilitiesCancel;
import org.knowm.xchange.cryptofacilities.dto.marketdata.CryptoFacilitiesFills;
import org.knowm.xchange.cryptofacilities.dto.marketdata.CryptoFacilitiesOpenOrders;
import org.knowm.xchange.cryptofacilities.dto.marketdata.CryptoFacilitiesOpenPositions;
import org.knowm.xchange.cryptofacilities.dto.marketdata.CryptoFacilitiesOrder;

import si.mazi.rescu.ParamsDigest;
import si.mazi.rescu.SynchronizedValueFactory;

/**
 * @author Jean-Christophe Laruelle
 */

@Path("/api/v2")
@Produces(MediaType.APPLICATION_JSON)
public interface CryptoFacilitiesAuthenticated extends CryptoFacilities {

  @POST
  @Path("/account")
  @Consumes(MediaType.APPLICATION_FORM_URLENCODED)
  CryptoFacilitiesAccount account(@HeaderParam("APIKey") String apiKey, @HeaderParam("Authent") ParamsDigest signer,
      @HeaderParam("Nonce") SynchronizedValueFactory<Long> nonce) throws IOException;

  @POST
  @Path("/sendorder")
  @Consumes(MediaType.APPLICATION_FORM_URLENCODED)
  CryptoFacilitiesOrder sendOrder(@HeaderParam("APIKey") String apiKey, @HeaderParam("Authent") ParamsDigest signer,
      @HeaderParam("Nonce") SynchronizedValueFactory<Long> nonce, @QueryParam("orderType") String orderType, @QueryParam("symbol") String symbol,
      @QueryParam("side") String side, @QueryParam("size") BigDecimal size, @QueryParam("limitPrice") BigDecimal limitPrice) throws IOException;

  @POST
  @Path("/cancelorder")
  @Consumes(MediaType.APPLICATION_FORM_URLENCODED)
  CryptoFacilitiesCancel cancelOrder(@HeaderParam("APIKey") String apiKey, @HeaderParam("Authent") ParamsDigest signer,
      @HeaderParam("Nonce") SynchronizedValueFactory<Long> nonce, @QueryParam("order_id") String order_id) throws IOException;

  @POST
  @Path("/openorders")
  @Consumes(MediaType.APPLICATION_FORM_URLENCODED)
  CryptoFacilitiesOpenOrders openOrders(@HeaderParam("APIKey") String apiKey, @HeaderParam("Authent") ParamsDigest signer,
      @HeaderParam("Nonce") SynchronizedValueFactory<Long> nonce) throws IOException;

  @POST
  @Path("/fills")
  @Consumes(MediaType.APPLICATION_FORM_URLENCODED)
  CryptoFacilitiesFills fills(@HeaderParam("APIKey") String apiKey, @HeaderParam("Authent") ParamsDigest signer,
      @HeaderParam("Nonce") SynchronizedValueFactory<Long> nonce) throws IOException;

  @POST
  @Path("/openpositions")
  @Consumes(MediaType.APPLICATION_FORM_URLENCODED)
  CryptoFacilitiesOpenPositions openPositions(@HeaderParam("APIKey") String apiKey, @HeaderParam("Authent") ParamsDigest signer,
      @HeaderParam("Nonce") SynchronizedValueFactory<Long> nonce) throws IOException;

}
