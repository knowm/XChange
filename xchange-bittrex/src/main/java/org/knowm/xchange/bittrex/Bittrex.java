package org.knowm.xchange.bittrex;

import java.io.IOException;
import java.util.List;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.MediaType;
import org.knowm.xchange.bittrex.dto.BittrexBaseReponse;
import org.knowm.xchange.bittrex.dto.BittrexException;
import org.knowm.xchange.bittrex.dto.marketdata.BittrexCurrency;
import org.knowm.xchange.bittrex.dto.marketdata.BittrexDepth;
import org.knowm.xchange.bittrex.dto.marketdata.BittrexMarketSummary;
import org.knowm.xchange.bittrex.dto.marketdata.BittrexSymbol;
import org.knowm.xchange.bittrex.dto.marketdata.BittrexTicker;
import org.knowm.xchange.bittrex.dto.marketdata.BittrexTrade;

@Path("v1.1")
@Produces(MediaType.APPLICATION_JSON)
public interface Bittrex {

  @GET
  @Path("/public/getticker")
  BittrexBaseReponse<BittrexTicker> getTicker(@QueryParam("market") String market)
      throws BittrexException, IOException;

  @GET
  @Path("public/getmarketsummary/")
  BittrexBaseReponse<List<BittrexMarketSummary>> getMarketSummary(
      @QueryParam("market") String market) throws BittrexException, IOException;

  @GET
  @Path("public/getmarketsummaries/")
  BittrexBaseReponse<List<BittrexMarketSummary>> getMarketSummaries()
      throws BittrexException, IOException;

  @GET
  @Path("public/getorderbook/")
  BittrexBaseReponse<BittrexDepth> getBook(
      @QueryParam("market") String market,
      @QueryParam("type") String type,
      @QueryParam("depth") int depth)
      throws BittrexException, IOException;

  @GET
  @Path("public/getmarkethistory/")
  BittrexBaseReponse<List<BittrexTrade>> getTrades(@QueryParam("market") String market)
      throws BittrexException, IOException;

  @GET
  @Path("public/getmarkets")
  BittrexBaseReponse<List<BittrexSymbol>> getSymbols() throws BittrexException, IOException;

  @GET
  @Path("public/getcurrencies")
  BittrexBaseReponse<List<BittrexCurrency>> getCurrencies() throws BittrexException, IOException;
}
