package org.knowm.xchange.huobi;

import java.io.IOException;

import javax.ws.rs.FormParam;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

import org.knowm.xchange.huobi.dto.account.BitVcAccountInfo;
import org.knowm.xchange.huobi.dto.marketdata.HuobiDepth;
import org.knowm.xchange.huobi.dto.marketdata.HuobiOrderBookTAS;
import org.knowm.xchange.huobi.dto.marketdata.HuobiTicker;
import org.knowm.xchange.huobi.dto.trade.HuobiCancelOrderResult;
import org.knowm.xchange.huobi.dto.trade.HuobiOrder;
import org.knowm.xchange.huobi.dto.trade.HuobiOrderResult;
import org.knowm.xchange.huobi.dto.trade.HuobiPlaceOrderResult;

import si.mazi.rescu.ParamsDigest;

@Path("/")
@Produces(MediaType.APPLICATION_JSON)
public interface BitVc {

  @GET
  @Path("ticker_{symbol}_json.js")
  public HuobiTicker getTicker(@PathParam("symbol") String symbol) throws IOException;

  @GET
  @Path("depth_{symbol}_json.js")
  public HuobiDepth getDepth(@PathParam("symbol") String symbol) throws IOException;

  @GET
  @Path("{symbol}_kline_{period}_json.js")
  public String[][] getKline(@PathParam("symbol") String symbol, @PathParam("period") String period) throws IOException;

  @GET
  @Path("detail_{symbol}_json.js")
  public HuobiOrderBookTAS getDetail(@PathParam("symbol") String symbol) throws IOException;

  /** Private **/
  @POST
  @Path("api/accountInfo/get")
  public BitVcAccountInfo getAccountInfo(@FormParam("access_key") String accessKey, @FormParam("created") long created,
      @FormParam("sign") ParamsDigest sign) throws IOException;

  @POST
  @Path("api/order/list")
  public HuobiOrderResult getOrders(@FormParam("access_key") String accessKey, @FormParam("coin_type") int coinType,
      @FormParam("created") long created, @FormParam("sign") ParamsDigest sign) throws IOException;

  @POST
  @Path("api/order/{id}")
  public HuobiOrder getOrder(@FormParam("access_key") String accessKey, @FormParam("coin_type") int coinType, @FormParam("created") long created,
      @FormParam("sign") ParamsDigest sign, @PathParam("id") long id) throws IOException;

  @POST
  @Path("api/order/{side}")
  public HuobiPlaceOrderResult placeLimitOrder(@FormParam("access_key") String accessKey, @FormParam("amount") String amount,
      @FormParam("coin_type") int coinType, @FormParam("created") long created, @FormParam("price") String price,
      @FormParam("sign") ParamsDigest sign, @PathParam("side") String side) throws IOException;

  @POST
  @Path("api/order/{side}")
  public HuobiPlaceOrderResult placeMarketOrder(@FormParam("access_key") String accessKey, @FormParam("amount") String amount,
      @FormParam("coin_type") int coinType, @FormParam("created") long created, @FormParam("sign") ParamsDigest sign, @PathParam("side") String side)
      throws IOException;

  @POST
  @Path("api/order/cancel/{id2}")
  public HuobiCancelOrderResult cancelOrder(@FormParam("access_key") String accessKey, @FormParam("coin_type") int coinType,
      @FormParam("created") long created, @FormParam("id") long id, @FormParam("sign") ParamsDigest sign, @PathParam("id2") long id2)
      throws IOException;
}
