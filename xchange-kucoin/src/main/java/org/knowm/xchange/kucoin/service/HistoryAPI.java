/** Copyright 2019 Mek Global Limited. */
package org.knowm.xchange.kucoin.service;

import java.io.IOException;
import java.util.List;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.MediaType;
import org.knowm.xchange.kucoin.dto.response.KucoinResponse;
import org.knowm.xchange.kucoin.dto.response.TradeHistoryResponse;

/** Based on code by chenshiwei on 2019/1/22. */
@Path("/api/v1/market")
@Produces(MediaType.APPLICATION_JSON)
public interface HistoryAPI {

  /**
   * List the latest trades for a symbol.
   *
   * @param symbol The symbol whose trades should be fetched.
   * @return The trades for the symbol.
   */
  @GET
  @Path("/histories")
  KucoinResponse<List<TradeHistoryResponse>> getTradeHistories(@QueryParam("symbol") String symbol)
      throws IOException;

  /**
   * Gets the kline of the specified symbol. Data are returned in grouped buckets based on requested
   * type.
   *
   * @param symbol The symbol whose trades should be fetched.
   * @param startAt The start time (in seconds) - defaults to 0, optional
   * @param endAt The end time (in seconds) - defaults to 0, optional
   * @param type The type of kline
   * @return The klines for the symbol and params.
   */
  @GET
  @Path("/candles")
  KucoinResponse<List<Object[]>> getKlines(
      @QueryParam("symbol") String symbol,
      @QueryParam("startAt") Long startAt,
      @QueryParam("endAt") Long endAt,
      @QueryParam("type") String type)
      throws IOException;
}
