package org.knowm.xchange.cryptopia;

import java.io.IOException;
import java.util.List;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

import org.knowm.xchange.cryptopia.dto.CryptopiaBaseResponse;
import org.knowm.xchange.cryptopia.dto.marketdata.CryptopiaCurrency;
import org.knowm.xchange.cryptopia.dto.marketdata.CryptopiaMarketHistory;
import org.knowm.xchange.cryptopia.dto.marketdata.CryptopiaOrderBook;
import org.knowm.xchange.cryptopia.dto.marketdata.CryptopiaTicker;
import org.knowm.xchange.cryptopia.dto.marketdata.CryptopiaTradePair;

@Path("api")
@Produces(MediaType.APPLICATION_JSON)
public interface Cryptopia {

  @GET
  @Path("GetCurrencies")
  CryptopiaBaseResponse<List<CryptopiaCurrency>> getCurrencies() throws IOException;

  @GET
  @Path("GetTradePairs")
  CryptopiaBaseResponse<List<CryptopiaTradePair>> getTradePairs() throws IOException;

  @GET
  @Path("GetMarkets")
  CryptopiaBaseResponse<List<CryptopiaTicker>> getMarkets() throws IOException;

  @GET
  @Path("GetMarkets/{baseMarket}")
  CryptopiaBaseResponse<List<CryptopiaTicker>> getMarkets(@PathParam("baseMarket") String baseMarket) throws IOException;

  @GET
  @Path("GetMarkets/{baseMarket}/{hours}")
  CryptopiaBaseResponse<List<CryptopiaTicker>> getMarkets(@PathParam("baseMarket") String baseMarket, @PathParam("hours") long hours) throws IOException;

  @GET
  @Path("GetMarket/{market}")
  CryptopiaBaseResponse<CryptopiaTicker> getMarket(@PathParam("market") String market) throws IOException;

  @GET
  @Path("GetMarket/{market}/{hours}")
  CryptopiaBaseResponse<CryptopiaTicker> getMarket(@PathParam("market") String market, @PathParam("hours") long hours) throws IOException;

  @GET
  @Path("GetMarketHistory/{market}")
  CryptopiaBaseResponse<List<CryptopiaMarketHistory>> getMarketHistory(@PathParam("market") String market) throws IOException;

  @GET
  @Path("GetMarketHistory/{market}/{hours}")
  CryptopiaBaseResponse<List<CryptopiaMarketHistory>> getMarketHistory(@PathParam("market") String market, @PathParam("hours") long hours) throws IOException;

  @GET
  @Path("GetMarketOrders/{market}")
  CryptopiaBaseResponse<CryptopiaOrderBook> getMarketOrders(@PathParam("market") String market) throws IOException;

  @GET
  @Path("GetMarketOrders/{market}/{orderCount}")
  CryptopiaBaseResponse<CryptopiaOrderBook> getMarketOrders(@PathParam("market") String pair, @PathParam("orderCount") long orderCount) throws IOException;

  // TODO GetMarketOrderGroups

}
