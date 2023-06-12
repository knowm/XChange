package org.knowm.xchange.gateio;

import org.knowm.xchange.gateio.dto.GateioException;
import org.knowm.xchange.gateio.dto.marketdata.*;

import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import java.io.IOException;
import java.util.List;

@Path("")
@Produces(MediaType.APPLICATION_JSON)
public interface Gateio {

  @GET
  @Path("api2/1/tradeHistory/{ident}_{currency}")
  GateioTradeHistory getTradeHistory(
      @PathParam("ident") String tradeableIdentifier, @PathParam("currency") String currency)
      throws IOException;

  @GET
  @Path("api2/1/tradeHistory/{ident}_{currency}/{tradeId}")
  GateioTradeHistory getTradeHistorySince(
      @PathParam("ident") String tradeableIdentifier,
      @PathParam("currency") String currency,
      @PathParam("tradeId") String tradeId)
      throws IOException;


  @GET
  @Path("api/v4/spot/currencies")
  List<GateioCurrencyInfo> getCurrencies() throws IOException, GateioException;


  @GET
  @Path("api/v4/spot/order_book")
  GateioOrderBook getOrderBook(
      @QueryParam("currency_pair") String currencyPair,
      @QueryParam("with_id") Boolean withId
  ) throws IOException, GateioException;


  @GET
  @Path("api/v4/wallet/currency_chains")
  List<GateioCurrencyChain> getCurrencyChains(@QueryParam("currency") String currency) throws IOException, GateioException;


  @GET
  @Path("api/v4/spot/currency_pairs")
  List<GateioCurrencyPairDetails> getCurrencyPairDetails() throws IOException, GateioException;


  @GET
  @Path("api/v4/spot/currency_pairs/{currency_pair}")
  GateioCurrencyPairDetails getCurrencyPairDetails(@PathParam("currency_pair") String currencyPair) throws IOException, GateioException;


  @GET
  @Path("api/v4/spot/tickers")
  List<GateioTicker> getTickers(@QueryParam("currency_pair") String currencyPair) throws IOException, GateioException;


}
