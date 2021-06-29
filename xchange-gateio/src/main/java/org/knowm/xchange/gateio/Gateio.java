package org.knowm.xchange.gateio;

import java.io.IOException;
import java.util.Map;
import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import org.knowm.xchange.gateio.dto.marketdata.*;

@Path("api2/1")
@Produces(MediaType.APPLICATION_JSON)
public interface Gateio {

  @GET
  @Path("marketinfo")
  GateioMarketInfoWrapper getMarketInfo() throws IOException;

  @GET
  @Path("pairs")
  GateioCurrencyPairs getPairs() throws IOException;

  @GET
  @Path("orderBooks")
  Map<String, GateioDepth> getDepths() throws IOException;

  @GET
  @Path("tickers")
  Map<String, GateioTicker> getTickers() throws IOException;

  @GET
  @Path("coininfo")
  GateioCoinInfoWrapper getCoinInfo() throws IOException;

  @GET
  @Path("ticker/{ident}_{currency}")
  GateioTicker getTicker(
      @PathParam("ident") String tradeableIdentifier, @PathParam("currency") String currency)
      throws IOException;

  @GET
  @Path("orderBook/{ident}_{currency}")
  GateioDepth getFullDepth(
      @PathParam("ident") String tradeableIdentifier, @PathParam("currency") String currency)
      throws IOException;

  @GET
  @Path("tradeHistory/{ident}_{currency}")
  GateioTradeHistory getTradeHistory(
      @PathParam("ident") String tradeableIdentifier, @PathParam("currency") String currency)
      throws IOException;

  @GET
  @Path("tradeHistory/{ident}_{currency}/{tradeId}")
  GateioTradeHistory getTradeHistorySince(
      @PathParam("ident") String tradeableIdentifier,
      @PathParam("currency") String currency,
      @PathParam("tradeId") String tradeId)
      throws IOException;

  @GET
  @Path("candlestick2/{currency_pair}")
  GateioCandlestickHistory getKlinesGate(
      @PathParam("currency_pair") String tradePair,
      @QueryParam("range_hour") Integer hours,
      @QueryParam("group_sec") Long interval)
      throws IOException;
}
