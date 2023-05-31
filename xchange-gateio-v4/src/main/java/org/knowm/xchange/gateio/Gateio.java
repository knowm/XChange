package org.knowm.xchange.gateio;

import java.io.IOException;
import java.util.List;
import java.util.Map;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.MediaType;
import org.knowm.xchange.gateio.dto.GateioException;
import org.knowm.xchange.gateio.dto.marketdata.GateioCandlestickHistory;
import org.knowm.xchange.gateio.dto.marketdata.GateioCoinInfoWrapper;
import org.knowm.xchange.gateio.dto.marketdata.GateioCurrencyChain;
import org.knowm.xchange.gateio.dto.marketdata.GateioCurrencyInfo;
import org.knowm.xchange.gateio.dto.marketdata.GateioCurrencyPairDetails;
import org.knowm.xchange.gateio.dto.marketdata.GateioCurrencyPairs;
import org.knowm.xchange.gateio.dto.marketdata.GateioDepth;
import org.knowm.xchange.gateio.dto.marketdata.GateioMarketInfoWrapper;
import org.knowm.xchange.gateio.dto.marketdata.GateioOrderBook;
import org.knowm.xchange.gateio.dto.marketdata.GateioTicker;
import org.knowm.xchange.gateio.dto.marketdata.GateioTradeHistory;

@Path("")
@Produces(MediaType.APPLICATION_JSON)
public interface Gateio {

  @GET
  @Path("api/v2/marketinfo")
  GateioMarketInfoWrapper getMarketInfo() throws IOException;

  @GET
  @Path("api2/1/pairs")
  GateioCurrencyPairs getPairs() throws IOException;

  @GET
  @Path("api2/1/orderBooks")
  Map<String, GateioDepth> getDepths() throws IOException;

  @GET
  @Path("/api2/1/tickers")
  Map<String, GateioTicker> getTickers() throws IOException;

  @GET
  @Path("/api2/1/coininfo")
  GateioCoinInfoWrapper getCoinInfo() throws IOException;

  @GET
  @Path("api2/1/ticker/{ident}_{currency}")
  GateioTicker getTicker(
      @PathParam("ident") String tradeableIdentifier, @PathParam("currency") String currency)
      throws IOException;

  @GET
  @Path("api2/1/orderBook/{ident}_{currency}")
  GateioDepth getFullDepth(
      @PathParam("ident") String tradeableIdentifier, @PathParam("currency") String currency)
      throws IOException;

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
  @Path("api2/1/candlestick2/{currency_pair}")
  GateioCandlestickHistory getKlinesGate(
      @PathParam("currency_pair") String tradePair,
      @QueryParam("range_hour") Integer hours,
      @QueryParam("group_sec") Long interval)
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


}
