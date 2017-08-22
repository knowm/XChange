package org.knowm.xchange.bittrex;

import java.io.IOException;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.MediaType;

import org.knowm.xchange.bittrex.dto.marketdata.BittrexCurrenciesResponse;
import org.knowm.xchange.bittrex.dto.marketdata.BittrexDepthResponse;
import org.knowm.xchange.bittrex.dto.marketdata.BittrexSymbolsResponse;
import org.knowm.xchange.bittrex.dto.marketdata.BittrexTickerResponse;
import org.knowm.xchange.bittrex.dto.marketdata.BittrexTickersResponse;
import org.knowm.xchange.bittrex.dto.marketdata.BittrexTradesResponse;

@Path("v1.1")
@Produces(MediaType.APPLICATION_JSON)
public interface Bittrex {

  @GET
  @Path("public/getmarketsummary/")
  BittrexTickerResponse getTicker(@QueryParam("market") String market) throws IOException;

  @GET
  @Path("public/getmarketsummaries/")
  BittrexTickersResponse getTickers() throws IOException;

  @GET
  @Path("public/getorderbook/")
  BittrexDepthResponse getBook(@QueryParam("market") String market, @QueryParam("type") String type,
      @QueryParam("depth") int depth) throws IOException;

  @GET
  @Path("public/getmarkethistory/")
  BittrexTradesResponse getTrades(@QueryParam("market") String market, @QueryParam("count") int count) throws IOException;

  @GET
  @Path("public/getmarkets")
  BittrexSymbolsResponse getSymbols() throws IOException;

  @GET
  @Path("public/getcurrencies")
  BittrexCurrenciesResponse getCurrencies() throws IOException;

}