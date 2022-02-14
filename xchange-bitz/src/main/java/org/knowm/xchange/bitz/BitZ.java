package org.knowm.xchange.bitz;

import java.io.IOException;
import java.math.BigDecimal;
import javax.ws.rs.FormParam;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.MediaType;
import org.knowm.xchange.bitz.dto.account.result.BitZUserAssetsResult;
import org.knowm.xchange.bitz.dto.marketdata.result.BitZCurrencyCoinRateResult;
import org.knowm.xchange.bitz.dto.marketdata.result.BitZCurrencyRateResult;
import org.knowm.xchange.bitz.dto.marketdata.result.BitZKlineResult;
import org.knowm.xchange.bitz.dto.marketdata.result.BitZOrdersResult;
import org.knowm.xchange.bitz.dto.marketdata.result.BitZSymbolListResult;
import org.knowm.xchange.bitz.dto.marketdata.result.BitZTickerAllResult;
import org.knowm.xchange.bitz.dto.marketdata.result.BitZTickerResult;
import org.knowm.xchange.bitz.dto.marketdata.result.BitZTradesResult;
import org.knowm.xchange.bitz.dto.trade.result.BitZEntrustSheetInfoResult;
import org.knowm.xchange.bitz.dto.trade.result.BitZTradeAddResult;
import org.knowm.xchange.bitz.dto.trade.result.BitZTradeCancelListResult;
import org.knowm.xchange.bitz.dto.trade.result.BitZTradeCancelResult;
import org.knowm.xchange.bitz.dto.trade.result.BitZUserHistoryResult;
import si.mazi.rescu.ParamsDigest;

@Path("api_v1")
@Produces(MediaType.APPLICATION_JSON)
public interface BitZ {

  @GET
  @Path("tickerall")
  BitZTickerAllResult getTickerAllResult() throws IOException;

  @GET
  @Path("ticker?coin={symbol}")
  BitZTickerResult getTickerResult(@PathParam("symbol") String symbol) throws IOException;

  @GET
  @Path("depth?coin={symbol}")
  BitZOrdersResult getOrdersResult(@PathParam("symbol") String symbol) throws IOException;

  @GET
  @Path("orders?coin={symbol}")
  BitZTradesResult getTradesResult(@PathParam("symbol") String symbol) throws IOException;

  @GET
  @Path("Market/ticker")
  BitZTickerResult getTicker(@QueryParam("symbol") String symbol) throws IOException;

  @GET
  @Path("Market/tickerall")
  BitZTickerAllResult getTickerAll(@QueryParam("symbol") String symbols) throws IOException;

  @GET
  @Path("Market/depth")
  BitZOrdersResult getDepth(@QueryParam("symbol") String symbol) throws IOException;

  @GET
  @Path("Market/order")
  BitZTradesResult getOrder(@QueryParam("symbol") String symbol) throws IOException;

  @GET
  @Path("Market/kline")
  BitZKlineResult getKline(
      @QueryParam("symbol") String symbol,
      @QueryParam("resolution") String resolution,
      @QueryParam("size") Integer size,
      @QueryParam("to") String microsecond)
      throws IOException;

  @GET
  @Path("Market/symbolList")
  BitZSymbolListResult getSymbolList(@QueryParam("symbol") String symbols) throws IOException;

  @GET
  @Path("Market/currencyRate")
  BitZCurrencyRateResult getCurrencyRate(@QueryParam("symbol") String symbols) throws IOException;

  @GET
  @Path("Market/currencyCoinRate")
  BitZCurrencyCoinRateResult getCurrencyCoinRate(@QueryParam("coins") String coins)
      throws IOException;

  @GET
  @Path("Market/coinRate")
  BitZCurrencyCoinRateResult getCoinRate(@QueryParam("coins") String coins) throws IOException;

  @POST
  @Path("Trade/addEntrustSheet")
  BitZTradeAddResult addEntrustSheet(
      @FormParam("apiKey") String apiKey,
      @FormParam("symbol") String symbol,
      @FormParam("timeStamp") String timeStamp,
      @FormParam("nonce") String nonce,
      @FormParam("sign") ParamsDigest sign,
      @FormParam("type") String type,
      @FormParam("price") BigDecimal price,
      @FormParam("number") BigDecimal number,
      @FormParam("tradePwd") String tradePwd)
      throws IOException;

  @POST
  @Path("Trade/cancelEntrustSheet")
  BitZTradeCancelResult cancelEntrustSheet(
      @FormParam("apiKey") String apiKey,
      @FormParam("timeStamp") String timeStamp,
      @FormParam("nonce") String nonce,
      @FormParam("sign") ParamsDigest sign,
      @FormParam("entrustSheetId") String entrustSheetId)
      throws IOException;

  @POST
  @Path("Trade/cancelEntrustSheet")
  BitZTradeCancelListResult cancelAllEntrustSheet(
      @FormParam("apiKey") String apiKey,
      @FormParam("timeStamp") String timeStamp,
      @FormParam("nonce") String nonce,
      @FormParam("sign") ParamsDigest sign,
      @FormParam("ids") String ids)
      throws IOException;

  @POST
  @Path("Trade/getUserHistoryEntrustSheet")
  BitZUserHistoryResult getUserHistoryEntrustSheet(
      @FormParam("apiKey") String apiKey,
      @FormParam("timeStamp") String timeStamp,
      @FormParam("nonce") String nonce,
      @FormParam("sign") ParamsDigest sign,
      @FormParam("coinFrom") String coinFrom,
      @FormParam("coinTo") String coinTo,
      @FormParam("type") Integer type,
      @FormParam("page") Integer page,
      @FormParam("pageSize") Integer pageSize,
      @FormParam("startTime") String startTime,
      @FormParam("endTime") String endTime)
      throws IOException;

  @POST
  @Path("Trade/getUserHistoryEntrustSheet")
  BitZUserHistoryResult getUserHistoryEntrustSheet(
      @FormParam("apiKey") String apiKey,
      @FormParam("timeStamp") String timeStamp,
      @FormParam("nonce") String nonce,
      @FormParam("sign") ParamsDigest sign,
      @FormParam("coinFrom") String coinFrom,
      @FormParam("coinTo") String coinTo,
      @FormParam("page") Integer page,
      @FormParam("pageSize") Integer pageSize,
      @FormParam("startTime") String startTime,
      @FormParam("endTime") String endTime)
      throws IOException;

  @POST
  @Path("Trade/getEntrustSheetInfo")
  BitZEntrustSheetInfoResult getEntrustSheetInfo(
      @FormParam("apiKey") String apiKey,
      @FormParam("timeStamp") String timeStamp,
      @FormParam("nonce") String nonce,
      @FormParam("sign") ParamsDigest sign,
      @FormParam("entrustSheetId") String entrustSheetId)
      throws IOException;

  @POST
  @Path("Assets/getUserAssets")
  BitZUserAssetsResult getUserAssets(
      @FormParam("apiKey") String apiKey,
      @FormParam("timeStamp") String timeStamp,
      @FormParam("nonce") String nonce,
      @FormParam("sign") ParamsDigest sign)
      throws IOException;

  @GET
  @Path("kline?coin={symbol}&type={type}")
  BitZKlineResult getKlineResult(@PathParam("symbol") String symbol, @PathParam("type") String type)
      throws IOException;
}
