package com.xeiam.xchange.bitvc;

import java.io.IOException;

import javax.ws.rs.FormParam;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

import si.mazi.rescu.ParamsDigest;

import com.xeiam.xchange.bitvc.dto.account.BitVcAccountInfo;
import com.xeiam.xchange.bitvc.dto.marketdata.BitVcDepth;
import com.xeiam.xchange.bitvc.dto.marketdata.BitVcOrderBookTAS;
import com.xeiam.xchange.bitvc.dto.marketdata.BitVcTicker;
import com.xeiam.xchange.bitvc.dto.trade.BitVcCancelOrderResult;
import com.xeiam.xchange.bitvc.dto.trade.BitVcOrder;
import com.xeiam.xchange.bitvc.dto.trade.BitVcOrderResult;
import com.xeiam.xchange.bitvc.dto.trade.BitVcPlaceOrderResult;

@Path("/")
@Produces(MediaType.APPLICATION_JSON)
public interface BitVc {

  @GET
  @Path("ticker_{symbol}_json.js")
  public BitVcTicker getTicker(@PathParam("symbol") String symbol) throws IOException;

  @GET
  @Path("depth_{symbol}_json.js")
  public BitVcDepth getDepth(@PathParam("symbol") String symbol) throws IOException;

  @GET
  @Path("{symbol}_kline_{period}_json.js")
  public String[][] getKline(@PathParam("symbol") String symbol, @PathParam("period") String period) throws IOException;

  @GET
  @Path("detail_{symbol}_json.js")
  public BitVcOrderBookTAS getDetail(@PathParam("symbol") String symbol) throws IOException;

  /** Private **/
  @POST
  @Path("api/accountInfo/get")
  public BitVcAccountInfo getAccountInfo(@FormParam("access_key") String accessKey, @FormParam("created") long created,
      @FormParam("sign") ParamsDigest sign) throws IOException;

  @POST
  @Path("api/order/list")
  public BitVcOrderResult getOrders(@FormParam("access_key") String accessKey, @FormParam("coin_type") int coinType,
      @FormParam("created") long created, @FormParam("sign") ParamsDigest sign) throws IOException;

  @POST
  @Path("api/order/{id}")
  public BitVcOrder getOrder(@FormParam("access_key") String accessKey, @FormParam("coin_type") int coinType, @FormParam("created") long created,
      @FormParam("sign") ParamsDigest sign, @PathParam("id") long id) throws IOException;

  @POST
  @Path("api/order/{side}")
  public BitVcPlaceOrderResult placeLimitOrder(@FormParam("access_key") String accessKey, @FormParam("amount") String amount,
      @FormParam("coin_type") int coinType, @FormParam("created") long created, @FormParam("price") String price,
      @FormParam("sign") ParamsDigest sign, @PathParam("side") String side) throws IOException;

  @POST
  @Path("api/order/{side}")
  public BitVcPlaceOrderResult placeMarketOrder(@FormParam("access_key") String accessKey, @FormParam("amount") String amount,
      @FormParam("coin_type") int coinType, @FormParam("created") long created, @FormParam("sign") ParamsDigest sign, @PathParam("side") String side)
      throws IOException;

  @POST
  @Path("api/order/cancel/{id2}")
  public BitVcCancelOrderResult cancelOrder(@FormParam("access_key") String accessKey, @FormParam("coin_type") int coinType,
      @FormParam("created") long created, @FormParam("id") long id, @FormParam("sign") ParamsDigest sign, @PathParam("id2") long id2)
      throws IOException;
}
