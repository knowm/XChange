package com.xeiam.xchange.bittrex.v1;

import java.io.IOException;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.MediaType;

import com.xeiam.xchange.bittrex.v1.dto.marketdata.BittrexDepthResponse;
import com.xeiam.xchange.bittrex.v1.dto.marketdata.BittrexSymbolsResponse;
import com.xeiam.xchange.bittrex.v1.dto.marketdata.BittrexTickerResponse;
import com.xeiam.xchange.bittrex.v1.dto.marketdata.BittrexTradesResponse;

@Path("v1.1")
@Produces(MediaType.APPLICATION_JSON)
public interface Bittrex {

  @GET
  @Path("public/getmarketsummary/")
  BittrexTickerResponse getTicker(@QueryParam("market") String market) throws IOException;

  @GET
  @Path("public/getorderbook/")
  BittrexDepthResponse getBook(@QueryParam("market") String market, @QueryParam("type") String type, @QueryParam("count") long count) throws IOException;

  @GET
  @Path("public/getmarkethistory/")
  BittrexTradesResponse getTrades(@QueryParam("market") String market, @QueryParam("count") long count) throws IOException;

  @GET
  @Path("public/getmarkets")
  BittrexSymbolsResponse getSymbols() throws IOException;

}