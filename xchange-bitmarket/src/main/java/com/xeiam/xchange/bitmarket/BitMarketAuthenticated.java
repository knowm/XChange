package com.xeiam.xchange.bitmarket;

import com.xeiam.xchange.bitmarket.dto.account.BitMarketAccountInfoResponse;
import com.xeiam.xchange.bitmarket.dto.account.BitMarketDepositResponse;
import com.xeiam.xchange.bitmarket.dto.account.BitMarketWithdrawResponse;
import si.mazi.rescu.ParamsDigest;
import si.mazi.rescu.SynchronizedValueFactory;

import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import java.io.IOException;
import java.math.BigDecimal;

/**
 * @author kfonal
 */
@Path("api2/")
@Consumes(MediaType.APPLICATION_FORM_URLENCODED)
@Produces(MediaType.APPLICATION_JSON)
public interface BitMarketAuthenticated {

  @POST
  @FormParam("method")
  public BitMarketAccountInfoResponse info(@HeaderParam("API-Key") String apiKey,
      @HeaderParam("API-Hash") ParamsDigest sign,
      @FormParam("tonce") SynchronizedValueFactory<Long> timestamp) throws IOException;

  @POST
  @FormParam("method")
  public BitMarketWithdrawResponse withdraw(@HeaderParam("API-Key") String apiKey,
      @HeaderParam("API-Hash") ParamsDigest sign,
      @FormParam("tonce") SynchronizedValueFactory<Long> timestamp,
      @FormParam("currency") String currency,
      @FormParam("amount") BigDecimal amount,
      @FormParam("address") String address) throws IOException;

  @POST
  @FormParam("method")
  public BitMarketDepositResponse deposit(@HeaderParam("API-Key") String apiKey,
      @HeaderParam("API-Hash") ParamsDigest sign,
      @FormParam("tonce") SynchronizedValueFactory<Long> timestamp,
      @FormParam("currency") String currency) throws IOException;
}
