package org.knowm.xchange.bybit;

import jakarta.ws.rs.GET;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.Produces;
import jakarta.ws.rs.QueryParam;
import jakarta.ws.rs.core.MediaType;
import java.io.IOException;
import org.knowm.xchange.bybit.dto.BybitCategorizedPayload;
import org.knowm.xchange.bybit.dto.BybitResult;
import org.knowm.xchange.bybit.dto.marketdata.BybitInstrumentInfo;
import org.knowm.xchange.bybit.dto.marketdata.BybitOrderBook;
import org.knowm.xchange.bybit.dto.marketdata.BybitServerTime;
import org.knowm.xchange.bybit.dto.marketdata.BybitTicker;
import org.knowm.xchange.bybit.service.BybitException;

@Path("")
@Produces(MediaType.APPLICATION_JSON)
public interface Bybit {


  @GET
  @Path("/v5/market/time")
  BybitResult<BybitServerTime> getServerTime() throws IOException, BybitException;


  @GET
  @Path("/v5/market/instruments-info")
  BybitResult<BybitCategorizedPayload<BybitInstrumentInfo>> getInstrumentsInfo(
      @QueryParam("category") String category
  )
      throws IOException, BybitException;


  @GET
  @Path("/v5/market/tickers")
  BybitResult<BybitCategorizedPayload<BybitTicker>> getTickers(
      @QueryParam("category") String category,
      @QueryParam("symbol") String symbol
  ) throws IOException, BybitException;


  @GET
  @Path("/v5/market/orderbook")
  BybitResult<BybitOrderBook> getOrderBook(
      @QueryParam("category") String category,
      @QueryParam("symbol") String symbol,
      @QueryParam("limit") Integer limit
  ) throws IOException, BybitException;


}
