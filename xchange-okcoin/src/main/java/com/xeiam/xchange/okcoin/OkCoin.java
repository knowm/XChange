package com.xeiam.xchange.okcoin;

import java.io.IOException;

import javax.ws.rs.FormParam;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.MediaType;

import si.mazi.rescu.ParamsDigest;

import com.xeiam.xchange.okcoin.dto.account.OkCoinUserInfo;
import com.xeiam.xchange.okcoin.dto.marketdata.OkCoinDepth;
import com.xeiam.xchange.okcoin.dto.marketdata.OkCoinTickerResponse;
import com.xeiam.xchange.okcoin.dto.marketdata.OkCoinTrade;
import com.xeiam.xchange.okcoin.dto.trade.OkCoinOrderResult;
import com.xeiam.xchange.okcoin.dto.trade.OkCoinTradeResult;

@Path("/")
@Produces(MediaType.APPLICATION_JSON)
public interface OkCoin {

  @GET
  @Path("ticker.do")
  OkCoinTickerResponse getTicker(@QueryParam("ok") String ok, @QueryParam("symbol") String symbol) throws IOException;

  @GET
  @Path("depth.do")
  OkCoinDepth getDepth(@QueryParam("ok") String ok, @QueryParam("symbol") String symbol) throws IOException;

  @GET
  @Path("trades.do")
  OkCoinTrade[] getTrades(@QueryParam("ok") String ok, @QueryParam("symbol") String symbol) throws IOException;

  @GET
  @Path("trades.do")
  OkCoinTrade[] getTrades(@QueryParam("ok") String ok, @QueryParam("symbol") String symbol, @QueryParam("since") long since) throws IOException;

  @POST
  @Path("userinfo.do")
  OkCoinUserInfo getUserInfo(@FormParam("partner") long partner, @FormParam("sign") ParamsDigest sign) throws IOException;

  @POST
  @Path("trade.do")
  OkCoinTradeResult trade(@FormParam("partner") long partner, @FormParam("symbol") String symbol, @FormParam("type") String type, @FormParam("rate") String rate, @FormParam("amount") String amount,
      @FormParam("sign") ParamsDigest sign) throws IOException;

  @POST
  @Path("cancelorder.do")
  OkCoinTradeResult cancelOrder(@FormParam("partner") long partner, @FormParam("order_id") long orderId, @FormParam("symbol") String symbol, @FormParam("sign") ParamsDigest sign) throws IOException;

  @POST
  @Path("getorder.do")
  OkCoinOrderResult getOrder(@FormParam("partner") long partner, @FormParam("order_id") long orderId, @FormParam("symbol") String symbol, @FormParam("sign") ParamsDigest sign) throws IOException;


  @POST
  @Path("getOrderHistory.do")
  OkCoinOrderResult getOrderHistory(@FormParam("partner") long partner, @FormParam("symbol") String symbol, 
      @FormParam("status") String status,
      @FormParam("currentPage") String currentPage,
      @FormParam("pageLength") String pageLength,
      @FormParam("sign") ParamsDigest sign) throws IOException;

}
