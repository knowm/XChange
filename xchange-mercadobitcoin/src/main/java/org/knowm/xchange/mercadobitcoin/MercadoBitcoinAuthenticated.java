package org.knowm.xchange.mercadobitcoin;

import java.io.IOException;
import java.math.BigDecimal;
import javax.annotation.Nonnull;
import javax.annotation.Nullable;
import jakarta.ws.rs.Consumes;
import jakarta.ws.rs.FormParam;
import jakarta.ws.rs.HeaderParam;
import jakarta.ws.rs.POST;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.Produces;
import jakarta.ws.rs.core.MediaType;
import org.knowm.xchange.mercadobitcoin.dto.MercadoBitcoinBaseTradeApiResult;
import org.knowm.xchange.mercadobitcoin.dto.account.MercadoBitcoinAccountInfo;
import org.knowm.xchange.mercadobitcoin.dto.trade.MercadoBitcoinCancelOrderResult;
import org.knowm.xchange.mercadobitcoin.dto.trade.MercadoBitcoinPlaceLimitOrderResult;
import org.knowm.xchange.mercadobitcoin.dto.trade.MercadoBitcoinUserOrders;
import si.mazi.rescu.ParamsDigest;

/**
 * @author Felipe Micaroni Lalli
 * @see org.knowm.xchange.mercadobitcoin.MercadoBitcoin
 */
@Path("tapi")
@Consumes(MediaType.APPLICATION_FORM_URLENCODED)
@Produces(MediaType.APPLICATION_JSON)
public interface MercadoBitcoinAuthenticated {

  @POST
  @Path("/")
  MercadoBitcoinBaseTradeApiResult<MercadoBitcoinAccountInfo> getInfo(
      @HeaderParam("Key") String key,
      @HeaderParam("Sign") ParamsDigest sign,
      @FormParam("method") String method,
      @FormParam("tonce") long tonce)
      throws IOException;

  @POST
  @Path("/")
  MercadoBitcoinBaseTradeApiResult<MercadoBitcoinUserOrders> getOrderList(
      @HeaderParam("Key") String key,
      @HeaderParam("Sign") ParamsDigest sign,
      @FormParam("method") String method,
      @FormParam("tonce") long tonce,
      @Nonnull @FormParam("pair") String pair,
      @Nullable @FormParam("type") String type,
      @FormParam("status") @Nullable String status,
      @FormParam("fromId") @Nullable String fromId,
      @FormParam("endId") @Nullable String endId,
      @FormParam("since") @Nullable Long since,
      @FormParam("end") @Nullable Long end)
      throws IOException;

  @POST
  @Path("/")
  MercadoBitcoinBaseTradeApiResult<MercadoBitcoinPlaceLimitOrderResult> placeLimitOrder(
      @HeaderParam("Key") String key,
      @HeaderParam("Sign") ParamsDigest sign,
      @FormParam("method") String method,
      @FormParam("tonce") long tonce,
      @Nonnull @FormParam("pair") String pair,
      @Nonnull @FormParam("type") String type,
      @Nonnull @FormParam("volume") BigDecimal volume,
      @Nonnull @FormParam("price") BigDecimal price)
      throws IOException;

  @POST
  @Path("/")
  MercadoBitcoinBaseTradeApiResult<MercadoBitcoinCancelOrderResult> cancelOrder(
      @HeaderParam("Key") String key,
      @HeaderParam("Sign") ParamsDigest sign,
      @FormParam("method") String method,
      @FormParam("tonce") long tonce,
      @Nonnull @FormParam("pair") String pair,
      @Nonnull @FormParam("order_id") String id)
      throws IOException;
}
