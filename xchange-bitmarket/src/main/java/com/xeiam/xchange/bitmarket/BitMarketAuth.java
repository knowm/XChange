package com.xeiam.xchange.bitmarket;

import com.xeiam.xchange.bitmarket.dto.BitMarketBaseResponse;
import com.xeiam.xchange.bitmarket.dto.account.BitMarketAccount;
import com.xeiam.xchange.bitmarket.dto.trade.BitMarketOrderResponse;
import com.xeiam.xchange.bitmarket.dto.trade.BitMarketOrdersResponse;
import si.mazi.rescu.ParamsDigest;

import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import java.io.IOException;
import java.math.BigDecimal;
import java.util.Map;

@Path("/api2/")
@Produces(MediaType.APPLICATION_JSON)
public interface BitMarketAuth {

  @POST
  @FormParam("method")
  public BitMarketBaseResponse<BitMarketAccount> info(@FormParam("tonce") Long tonce,
                                                      @HeaderParam("API-Key") String apiKey,
                                                      @HeaderParam("API-Hash") ParamsDigest sign) throws IOException;

  @POST
  @FormParam("method")
  public BitMarketBaseResponse<BitMarketOrderResponse> trade(@FormParam("market") String market,
                                                       @FormParam("type") String type,
                                                       @FormParam("amount") BigDecimal amount,
                                                       @FormParam("rate") BigDecimal rate,
                                                       @FormParam("tonce") Long tonce,
                                                       @HeaderParam("API-Key") String apiKey,
                                                       @HeaderParam("API-Hash") ParamsDigest sign) throws IOException;
  @POST
  @FormParam("method")
  public BitMarketBaseResponse<BitMarketOrderResponse> cancel(@FormParam("id") String id,
                                                       @FormParam("tonce") Long tonce,
                                                       @HeaderParam("API-Key") String apiKey,
                                                       @HeaderParam("API-Hash") ParamsDigest sign) throws IOException;


  @POST
  @FormParam("method")
  public BitMarketBaseResponse<Map<String, BitMarketOrdersResponse>> orders(@FormParam("market") String market,
                                                               @FormParam("tonce") Long tonce,
                                                               @HeaderParam("API-Key") String apiKey,
                                                               @HeaderParam("API-Hash") ParamsDigest sign) throws IOException;
}
