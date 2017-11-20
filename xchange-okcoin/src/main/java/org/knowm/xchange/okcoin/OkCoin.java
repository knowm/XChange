package org.knowm.xchange.okcoin;

import java.io.IOException;

import javax.ws.rs.Consumes;
import javax.ws.rs.FormParam;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.MediaType;

import org.knowm.xchange.okcoin.dto.account.OKCoinWithdraw;
import org.knowm.xchange.okcoin.dto.account.OkCoinAccountRecords;
import org.knowm.xchange.okcoin.dto.account.OkCoinFuturesUserInfoCross;
import org.knowm.xchange.okcoin.dto.account.OkCoinUserInfo;
import org.knowm.xchange.okcoin.dto.marketdata.OkCoinDepth;
import org.knowm.xchange.okcoin.dto.marketdata.OkCoinTickerResponse;
import org.knowm.xchange.okcoin.dto.marketdata.OkCoinTrade;
import org.knowm.xchange.okcoin.dto.trade.OkCoinFuturesOrderResult;
import org.knowm.xchange.okcoin.dto.trade.OkCoinFuturesTradeHistoryResult;
import org.knowm.xchange.okcoin.dto.trade.OkCoinOrderResult;
import org.knowm.xchange.okcoin.dto.trade.OkCoinPositionResult;
import org.knowm.xchange.okcoin.dto.trade.OkCoinTradeResult;

import si.mazi.rescu.ParamsDigest;

@Path("/v1")
@Produces(MediaType.APPLICATION_JSON)
public interface OkCoin {

  @GET
  @Path("ticker.do")
  OkCoinTickerResponse getTicker(@QueryParam("ok") String ok, @QueryParam("symbol") String symbol) throws IOException;

  @GET
  @Path("future_ticker.do")
  OkCoinTickerResponse getFuturesTicker(@QueryParam("symbol") String symbol, @QueryParam("contract_type") String contract) throws IOException;

  @GET
  @Path("depth.do")
  OkCoinDepth getDepth(@QueryParam("ok") String ok, @QueryParam("symbol") String symbol) throws IOException;

  @GET
  @Path("future_depth.do")
  OkCoinDepth getFuturesDepth(@QueryParam("ok") String ok, @QueryParam("symbol") String symbol,
      @QueryParam("contract_type") String contract) throws IOException;

  @GET
  @Path("trades.do")
  OkCoinTrade[] getTrades(@QueryParam("ok") String ok, @QueryParam("symbol") String symbol) throws IOException;

  @GET
  @Path("future_trades.do")
  OkCoinTrade[] getFuturesTrades(@QueryParam("ok") String ok, @QueryParam("symbol") String symbol,
      @QueryParam("contract_type") String contract) throws IOException;

  @GET
  @Path("trades.do")
  OkCoinTrade[] getTrades(@QueryParam("ok") String ok, @QueryParam("symbol") String symbol, @QueryParam("since") long since) throws IOException;

  @GET
  @Path("future_trades.do")
  OkCoinTrade[] getFuturesTrades(@QueryParam("ok") String ok, @QueryParam("symbol") String symbol, @QueryParam("contract_type") String contract,
      @QueryParam("since") long since) throws IOException;

  @POST
  @Path("userinfo.do")
  OkCoinUserInfo getUserInfo(@FormParam("api_key") String apikey, @FormParam("sign") ParamsDigest sign) throws IOException;

  @POST
  @Path("future_userinfo_4fix.do")
  OkCoinUserInfo getFuturesUserInfoFixed(@FormParam("api_key") String api_key, @FormParam("sign") ParamsDigest sign) throws IOException;

  @POST
  @Path("future_userinfo.do")
  OkCoinFuturesUserInfoCross getFuturesUserInfoCross(@FormParam("api_key") String api_key, @FormParam("sign") ParamsDigest sign) throws IOException;

  @POST
  @Path("trade.do")
  OkCoinTradeResult trade(@FormParam("api_key") String apikey, @FormParam("symbol") String symbol, @FormParam("type") String type,
      @FormParam("price") String price, @FormParam("amount") String amount, @FormParam("sign") ParamsDigest sign) throws IOException;

  @POST
  @Path("future_trade.do")
  OkCoinTradeResult futuresTrade(@FormParam("api_key") String api_key, @FormParam("symbol") String symbol,
      @FormParam("contract_type") String contract, @FormParam("type") String type, @FormParam("price") String price,
      @FormParam("amount") String amount, @FormParam("match_price") int matchPrice, @FormParam("lever_rate") int leverRate,
      @FormParam("sign") ParamsDigest sign) throws IOException;

  @POST
  @Path("cancel_order.do")
  OkCoinTradeResult cancelOrder(@FormParam("api_key") String api_key, @FormParam("order_id") long orderId, @FormParam("symbol") String symbol,
      @FormParam("sign") ParamsDigest sign) throws IOException;

  @POST
  @Path("future_cancel.do")
  OkCoinTradeResult futuresCancelOrder(@FormParam("api_key") String api_key, @FormParam("order_id") long orderId, @FormParam("symbol") String symbol,
      @FormParam("contract_type") String contract, @FormParam("sign") ParamsDigest sign) throws IOException;

  @POST
  @Path("order_info.do")
  OkCoinOrderResult getOrder(@FormParam("api_key") String api_key, @FormParam("order_id") long orderId, @FormParam("symbol") String symbol,
      @FormParam("sign") ParamsDigest sign) throws IOException;

  @POST
  @Path("future_order_info.do")
  OkCoinFuturesOrderResult getFuturesOrder(@FormParam("api_key") String api_key, @FormParam("order_id") long orderId,
      @FormParam("symbol") String symbol, @FormParam("status") String status, @FormParam("current_page") String currentPage,
      @FormParam("page_length") String pageLength, @FormParam("contract_type") String contract,
      @FormParam("sign") ParamsDigest sign) throws IOException;

  @POST
  @Path(value = "future_orders_info.do")
  @Consumes(MediaType.APPLICATION_FORM_URLENCODED)
  OkCoinFuturesOrderResult getFuturesOrders(@FormParam("api_key") String api_key, @FormParam("order_id") String orderId,
      @FormParam("symbol") String symbol, @FormParam("contract_type") String contract, @FormParam("sign") ParamsDigest sign) throws IOException;

  @POST
  @Path("future_trades_history.do")
  OkCoinFuturesTradeHistoryResult[] getFuturesTradeHistory(@FormParam("api_key") String api_key, @FormParam("since") long since,
      @FormParam("symbol") String symbol, @FormParam("date") String date, @FormParam("sign") ParamsDigest sign) throws IOException;

  @POST
  @Path("future_position_4fix.do")
  OkCoinPositionResult getFuturesPositionsFixed(@FormParam("api_key") String api_key, @FormParam("symbol") String symbol,
      @FormParam("contract_type") String contract, @FormParam("sign") ParamsDigest sign) throws IOException;

  @POST
  @Path("future_position.do")
  OkCoinPositionResult getFuturesPositionsCross(@FormParam("api_key") String api_key, @FormParam("symbol") String symbol,
      @FormParam("contract_type") String contract, @FormParam("sign") ParamsDigest sign) throws IOException;

  @POST
  @Path("order_history.do")
  OkCoinOrderResult getOrderHistory(@FormParam("api_key") String apikey, @FormParam("symbol") String symbol, @FormParam("status") String status,
      @FormParam("current_page") String currentPage, @FormParam("page_length") String pageLength,
      @FormParam("sign") ParamsDigest sign) throws IOException;

  @POST
  @Path("withdraw.do")
  OKCoinWithdraw withdraw(@FormParam("api_key") String api_key, @FormParam("symbol") String symbol, @FormParam("sign") ParamsDigest sign,
      @FormParam("chargefee") String chargefee, @FormParam("trade_pwd") String trade_pwd, @FormParam("withdraw_address") String withdraw_address,
      @FormParam("withdraw_amount") String withdraw_amount, @FormParam("target") String target) throws IOException;

  @POST
  @Path("account_records.do")
  OkCoinAccountRecords getAccountRecords(@FormParam("api_key") String apikey, @FormParam("symbol") String symbol, @FormParam("type") String type,
      @FormParam("current_page") String currentPage, @FormParam("page_length") String pageLength,
      @FormParam("sign") ParamsDigest sign) throws IOException;
}
