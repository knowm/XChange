package com.xeiam.xchange.mercadobitcoin;

import java.io.IOException;
import java.math.BigDecimal;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;
import javax.ws.rs.Consumes;
import javax.ws.rs.FormParam;
import javax.ws.rs.HeaderParam;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

import si.mazi.rescu.ParamsDigest;

import com.xeiam.xchange.mercadobitcoin.dto.MercadoBitcoinBaseTradeApiResult;
import com.xeiam.xchange.mercadobitcoin.dto.account.MercadoBitcoinAccountInfo;
import com.xeiam.xchange.mercadobitcoin.dto.trade.MercadoBitcoinCancelOrderResult;
import com.xeiam.xchange.mercadobitcoin.dto.trade.MercadoBitcoinPlaceLimitOrderResult;
import com.xeiam.xchange.mercadobitcoin.dto.trade.MercadoBitcoinUserOrders;

/**
 * @author Felipe Micaroni Lalli
 * @see com.xeiam.xchange.mercadobitcoin.MercadoBitcoin
 */
@Path("tapi")
@Consumes(MediaType.APPLICATION_FORM_URLENCODED)
@Produces(MediaType.APPLICATION_JSON)
public interface MercadoBitcoinAuthenticated {

  @POST
  @Path("/")
  public MercadoBitcoinBaseTradeApiResult<MercadoBitcoinAccountInfo> getInfo(@HeaderParam("Key") String key, @HeaderParam("Sign") ParamsDigest sign,
      @FormParam("method") String method, @FormParam("tonce") long tonce) throws IOException;

  @POST
  @Path("/")
  public MercadoBitcoinBaseTradeApiResult<MercadoBitcoinUserOrders> getOrderList(@HeaderParam("Key") String key,
      @HeaderParam("Sign") ParamsDigest sign, @FormParam("method") String method, @FormParam("tonce") long tonce,
      @Nonnull @FormParam("pair") String pair, @Nullable @FormParam("type") String type, @FormParam("status") @Nullable String status,
      @FormParam("fromId") @Nullable String fromId, @FormParam("endId") @Nullable String endId, @FormParam("since") @Nullable Long since,
      @FormParam("end") @Nullable Long end) throws IOException;

  @POST
  @Path("/")
  public MercadoBitcoinBaseTradeApiResult<MercadoBitcoinPlaceLimitOrderResult> placeLimitOrder(@HeaderParam("Key") String key,
      @HeaderParam("Sign") ParamsDigest sign, @FormParam("method") String method, @FormParam("tonce") long tonce,
      @Nonnull @FormParam("pair") String pair, @Nonnull @FormParam("type") String type, @Nonnull @FormParam("volume") BigDecimal volume,
      @Nonnull @FormParam("price") BigDecimal price) throws IOException;

  @POST
  @Path("/")
  public MercadoBitcoinBaseTradeApiResult<MercadoBitcoinCancelOrderResult> cancelOrder(@HeaderParam("Key") String key,
      @HeaderParam("Sign") ParamsDigest sign, @FormParam("method") String method, @FormParam("tonce") long tonce,
      @Nonnull @FormParam("pair") String pair, @Nonnull @FormParam("order_id") String id) throws IOException;

}
