package org.knowm.xchange.huobi;

import java.io.IOException;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

import org.knowm.xchange.huobi.dto.marketdata.futures.BitVcExchangeRate;
import org.knowm.xchange.huobi.dto.marketdata.futures.BitVcFuturesDepth;
import org.knowm.xchange.huobi.dto.marketdata.futures.BitVcFuturesTicker;
import org.knowm.xchange.huobi.dto.marketdata.futures.BitVcFuturesTrade;

@Path("/")
@Produces(MediaType.APPLICATION_JSON)
public interface BitVcFutures {

  @GET
  @Path("ticker_{symbol}_{contract}.js")
  public BitVcFuturesTicker getTicker(@PathParam("symbol") String symbol, @PathParam("contract") String contract) throws IOException;

  @GET
  @Path("depths_{symbol}_{contract}.js")
  public BitVcFuturesDepth getDepths(@PathParam("symbol") String symbol, @PathParam("contract") String contract) throws IOException;

  @GET
  @Path("trades_{symbol}_{contract}.js")
  public BitVcFuturesTrade[] getTrades(@PathParam("symbol") String symbol, @PathParam("contract") String contract) throws IOException;

  /** Non-XChange compatible methods */

  /*
   * @GET
   * @Path("index_price_{symbol}") public BitVcIndex getIndex(@PathParam("symbol") String symbol) throws IOException;
   */

  @GET
  @Path("exchange_rate.js")
  public BitVcExchangeRate getExchangeRate() throws IOException;
}