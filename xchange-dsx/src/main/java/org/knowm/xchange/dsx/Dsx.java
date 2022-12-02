package org.knowm.xchange.dsx;

import java.io.IOException;
import java.util.Date;
import java.util.List;
import java.util.Map;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.QueryParam;
import org.knowm.xchange.dsx.dto.DsxCandle;
import org.knowm.xchange.dsx.dto.DsxCurrency;
import org.knowm.xchange.dsx.dto.DsxOrderBook;
import org.knowm.xchange.dsx.dto.DsxSort;
import org.knowm.xchange.dsx.dto.DsxSymbol;
import org.knowm.xchange.dsx.dto.DsxTicker;
import org.knowm.xchange.dsx.dto.DsxTrade;
import org.knowm.xchange.dsx.dto.DsxTradesSortBy;

/** Version 2 of Dsx API. See https://api.dsx.com/api/2/explore/ */
@Path("/api/2/")
public interface Dsx {

  @GET
  @Path("public/symbol")
  List<DsxSymbol> getSymbols() throws IOException;

  @GET
  @Path("public/currency")
  List<DsxCurrency> getCurrencies() throws IOException;

  @GET
  @Path("public/currency/{currency}")
  DsxCurrency getCurrency(@PathParam("currency") String currency) throws IOException;

  @GET
  @Path("public/ticker")
  List<DsxTicker> getTickers() throws IOException;

  @GET
  @Path("public/ticker/{symbol}")
  DsxTicker getTicker(@PathParam("symbol") String symbol) throws IOException;

  @GET
  @Path("public/orderbook/{symbol}")
  DsxOrderBook getOrderBook(@PathParam("symbol") String symbol, @QueryParam("limit") Integer limit)
      throws IOException;

  @GET
  @Path("public/trades/{symbol}")
  List<DsxTrade> getTrades(
      @PathParam("symbol") String symbol,
      @QueryParam("sort") DsxSort sortDirection,
      @QueryParam("by") DsxTradesSortBy sortBy,
      @QueryParam("from") Long from,
      @QueryParam("till") Long till,
      @QueryParam("limit") Integer limit,
      @QueryParam("offset") Integer offset)
      throws IOException;

  @GET
  @Path("public/trades")
  Map<String, List<DsxTrade>> getTrades(
      @QueryParam("sort") DsxSort sortDirection,
      @QueryParam("by") DsxTradesSortBy sortBy,
      @QueryParam("from") Long from,
      @QueryParam("till") Long till,
      @QueryParam("limit") Integer limit,
      @QueryParam("offset") Integer offset)
      throws IOException;

  @GET
  @Path("public/ticker")
  List<DsxTicker> getDsxTickers() throws IOException;

  @GET
  @Path("public/candles/{symbol}")
  List<DsxCandle> getDsxOHLC(
      @PathParam("symbol") String symbol,
      @QueryParam("limit") int limit,
      @QueryParam("period") String period)
      throws IOException;

  @GET
  @Path("public/candles/{symbol}")
  List<DsxCandle> getDsxOHLC(
      @PathParam("symbol") String symbol,
      @QueryParam("limit") int limit,
      @QueryParam("period") String period,
      @QueryParam("sort") String sort)
      throws IOException;

  @GET
  @Path("public/candles/{symbol}")
  List<DsxCandle> getDsxOHLC(
      @PathParam("symbol") String symbol,
      @QueryParam("limit") int limit,
      @QueryParam("period") String period,
      @QueryParam("from") Date from,
      @QueryParam("till") Date till,
      @QueryParam("sort") String sort)
      throws IOException;

  @GET
  @Path("public/candles/{symbol}")
  List<DsxCandle> getDsxOHLC(
      @PathParam("symbol") String symbol,
      @QueryParam("limit") int limit,
      @QueryParam("period") String period,
      @QueryParam("offset") int offset,
      @QueryParam("sort") String sort)
      throws IOException;
}
