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
import com.xeiam.xchange.okcoin.dto.trade.OkCoinPositionResult;
import com.xeiam.xchange.okcoin.dto.trade.OkCoinTradeResult;

@Path("/v1")
@Produces(MediaType.APPLICATION_JSON)
public interface OkCoin {

  @GET
  @Path("ticker.do")
  OkCoinTickerResponse getTicker(@QueryParam("ok") String ok, @QueryParam("symbol") String symbol) throws IOException;

  @GET
  @Path("future_ticker.do")
  OkCoinTickerResponse getFuturesTicker(@QueryParam("symbol") String symbol, @QueryParam("contract_type") String prompt) throws IOException;

  @GET
  @Path("depth.do")
  OkCoinDepth getDepth(@QueryParam("ok") String ok, @QueryParam("symbol") String symbol) throws IOException;

  @GET
  @Path("future_depth.do")
  OkCoinDepth getFuturesDepth(@QueryParam("ok") String ok, @QueryParam("symbol") String symbol, @QueryParam("contract_type") String prompt)
      throws IOException;

  @GET
  @Path("trades.do")
  OkCoinTrade[] getTrades(@QueryParam("ok") String ok, @QueryParam("symbol") String symbol) throws IOException;

  @GET
  @Path("future_trades.do")
  OkCoinTrade[] getFuturesTrades(@QueryParam("ok") String ok, @QueryParam("symbol") String symbol, @QueryParam("contract_type") String prompt)
      throws IOException;

  @GET
  @Path("trades.do")
  OkCoinTrade[] getTrades(@QueryParam("ok") String ok, @QueryParam("symbol") String symbol, @QueryParam("since") long since) throws IOException;

  @GET
  @Path("future_trades.do")
  OkCoinTrade[] getFuturesTrades(@QueryParam("ok") String ok, @QueryParam("symbol") String symbol, @QueryParam("contract_type") String prompt,
      @QueryParam("since") long since) throws IOException;

  @POST
  @Path("userinfo.do")
  OkCoinUserInfo getUserInfo(@FormParam("api_key") String apikey, @FormParam("sign") ParamsDigest sign) throws IOException;

  @POST
  @Path("future_userinfo_4fix.do")
  OkCoinUserInfo getFuturesUserInfo(@FormParam("api_key") String api_key, @FormParam("sign") ParamsDigest sign) throws IOException;

  @POST
  @Path("trade.do")
  OkCoinTradeResult trade(@FormParam("api_key") String apikey, @FormParam("symbol") String symbol, @FormParam("type") String type,
      @FormParam("price") String price, @FormParam("amount") String amount, @FormParam("sign") ParamsDigest sign) throws IOException;

  @POST
  @Path("future_trade.do")
  OkCoinTradeResult futuresTrade(@FormParam("api_key") String api_key, @FormParam("symbol") String symbol, @FormParam("contract_type") String prompt,
      @FormParam("type") String type, @FormParam("price") String price, @FormParam("amount") String amount, @FormParam("match_price") int matchPrice,
      @FormParam("sign") ParamsDigest sign) throws IOException;

  @POST
  @Path("cancel_order.do")
  OkCoinTradeResult cancelOrder(@FormParam("api_key") String api_key, @FormParam("order_id") long orderId, @FormParam("symbol") String symbol,
      @FormParam("sign") ParamsDigest sign) throws IOException;

  @POST
  @Path("future_cancel.do")
  OkCoinTradeResult futuresCancelOrder(@FormParam("api_key") String api_key, @FormParam("order_id") long orderId, @FormParam("symbol") String symbol,
      @FormParam("contract_type") String prompt, @FormParam("sign") ParamsDigest sign) throws IOException;

  @POST
  @Path("order_info.do")
  OkCoinOrderResult getOrder(@FormParam("api_key") String api_key, @FormParam("order_id") long orderId, @FormParam("symbol") String symbol,
      @FormParam("sign") ParamsDigest sign) throws IOException;

  @POST
  @Path("future_order_info.do")
  /** TODO 2006 error ... */
  OkCoinOrderResult getFuturesOrder(@FormParam("api_key") String api_key, @FormParam("order_id") long orderId, @FormParam("symbol") String symbol,
      @FormParam("status") String status, @FormParam("current_page") String currentPage, @FormParam("page_length") String pageLength,
      @FormParam("contract_type") String prompt, @FormParam("sign") ParamsDigest sign) throws IOException;

  @POST
  @Path("future_position_4fix.do")
  OkCoinPositionResult getFuturesPositions(@FormParam("api_key") String api_key, @FormParam("symbol") String symbol,
      @FormParam("contract_type") String prompt, @FormParam("sign") ParamsDigest sign) throws IOException;

  @POST
  @Path("order_history.do")
  OkCoinOrderResult getOrderHistory(@FormParam("api_key") String apikey, @FormParam("symbol") String symbol, @FormParam("status") String status,
      @FormParam("current_page") String currentPage, @FormParam("page_length") String pageLength, @FormParam("sign") ParamsDigest sign)
      throws IOException;

}
