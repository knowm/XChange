package org.knowm.xchange.ftx;

import java.io.IOException;
import java.util.List;
import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.MediaType;
import org.knowm.xchange.ftx.dto.FtxResponse;
import org.knowm.xchange.ftx.dto.marketdata.FtxCandleDto;
import org.knowm.xchange.ftx.dto.marketdata.FtxMarketDto;
import org.knowm.xchange.ftx.dto.marketdata.FtxMarketsDto;
import org.knowm.xchange.ftx.dto.marketdata.FtxOrderbookDto;
import org.knowm.xchange.ftx.dto.marketdata.FtxTradeDto;

@Path("/api")
@Produces(MediaType.APPLICATION_JSON)
public interface Ftx {

  @GET
  @Path("/markets")
  @Consumes(MediaType.APPLICATION_JSON)
  FtxResponse<FtxMarketsDto> getMarkets() throws IOException, FtxException;

  @GET
  @Path("/markets/{market_name}")
  @Consumes(MediaType.APPLICATION_JSON)
  FtxResponse<FtxMarketDto> getMarket(@PathParam("market_name") String market)
      throws IOException, FtxException;

  @GET
  @Path("/markets/{market_name}/trades")
  @Consumes(MediaType.APPLICATION_JSON)
  FtxResponse<List<FtxTradeDto>> getTrades(
      @PathParam("market_name") String market, @QueryParam("limit") int limit)
      throws IOException, FtxException;

  @GET
  @Path("/markets/{market_name}/candles")
  @Consumes(MediaType.APPLICATION_JSON)
  FtxResponse<List<FtxCandleDto>> getCandles(
      @PathParam("market_name") String market,
      @QueryParam("resolution") String resolution,
      @QueryParam("start_time") String startTime,
      @QueryParam("end_time") String endTime,
      @QueryParam("limit") Integer limit)
      throws IOException, FtxException;

  @GET
  @Path("/markets/{market_name}/orderbook")
  FtxResponse<FtxOrderbookDto> getOrderbook(
      @PathParam("market_name") String market, @QueryParam("depth") int depth)
      throws IOException, FtxException;
}
