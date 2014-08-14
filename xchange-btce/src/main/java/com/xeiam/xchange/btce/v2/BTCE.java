package com.xeiam.xchange.btce.v2;

import java.io.IOException;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

import com.xeiam.xchange.btce.v2.dto.marketdata.BTCEDepth;
import com.xeiam.xchange.btce.v2.dto.marketdata.BTCETickerWrapper;
import com.xeiam.xchange.btce.v2.dto.marketdata.BTCETrade;

/**
 * @author Matija Mazi
 */
@Path("api")
@Deprecated
@Produces(MediaType.APPLICATION_JSON)
public interface BTCE {

  @GET
  @Path("2/{ident}_{currency}/ticker")
  BTCETickerWrapper getTicker(@PathParam("ident") String tradeableIdentifier, @PathParam("currency") String currency) throws IOException;

  @GET
  @Path("2/{ident}_{currency}/depth")
  BTCEDepth getDepth(@PathParam("ident") String tradeableIdentifier, @PathParam("currency") String currency) throws IOException;

  @GET
  @Path("2/{ident}_{currency}/trades")
  BTCETrade[] getTrades(@PathParam("ident") String tradeableIdentifier, @PathParam("currency") String currency) throws IOException;

}
