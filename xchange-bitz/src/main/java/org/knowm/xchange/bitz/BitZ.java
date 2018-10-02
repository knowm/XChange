package org.knowm.xchange.bitz;

import java.io.IOException;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import org.knowm.xchange.bitz.dto.marketdata.result.BitZKlineResult;
import org.knowm.xchange.bitz.dto.marketdata.result.BitZOrdersResult;
import org.knowm.xchange.bitz.dto.marketdata.result.BitZTickerAllResult;
import org.knowm.xchange.bitz.dto.marketdata.result.BitZTickerResult;
import org.knowm.xchange.bitz.dto.marketdata.result.BitZTradesResult;

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
  @Path("kline?coin={symbol}&type={type}")
  BitZKlineResult getKlineResult(@PathParam("symbol") String symbol, @PathParam("type") String type)
      throws IOException;
}
