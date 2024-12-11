package org.knowm.xchange.coinex;

import jakarta.ws.rs.GET;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.Produces;
import jakarta.ws.rs.QueryParam;
import jakarta.ws.rs.core.MediaType;
import java.io.IOException;
import java.util.List;
import java.util.Map;
import org.knowm.xchange.coinex.dto.CoinexException;
import org.knowm.xchange.coinex.dto.CoinexResponse;
import org.knowm.xchange.coinex.dto.marketdata.CoinexAllMarketStatisticsV1;
import org.knowm.xchange.coinex.dto.marketdata.CoinexChainInfo;
import org.knowm.xchange.coinex.dto.marketdata.CoinexCurrencyPairInfo;
import org.knowm.xchange.coinex.dto.marketdata.CoinexMaintainInfo;
import org.knowm.xchange.coinex.dto.marketdata.CoinexMarketDepth;
import org.knowm.xchange.coinex.dto.marketdata.CoinexSingleMarketStatisticsV1;

@Path("")
@Produces(MediaType.APPLICATION_JSON)
public interface Coinex {

  @GET
  @Path("v1/common/asset/config")
  CoinexResponse<Map<String, CoinexChainInfo>> allChainInfos() throws IOException, CoinexException;

  @GET
  @Path("v1/market/ticker/all")
  CoinexResponse<CoinexAllMarketStatisticsV1> allMarketStatistics()
      throws IOException, CoinexException;

  @GET
  @Path("v1/market/ticker")
  CoinexResponse<CoinexSingleMarketStatisticsV1> singleMarketStatistics(
      @QueryParam("market") String market) throws IOException, CoinexException;

  @GET
  @Path("v2/maintain/info")
  CoinexResponse<List<CoinexMaintainInfo>> maintainInfo()
      throws IOException, CoinexException;


  @GET
  @Path("v2/spot/market")
  CoinexResponse<List<CoinexCurrencyPairInfo>> marketStatus(@QueryParam("market") String markets)
      throws IOException, CoinexException;

  @GET
  @Path("v2/spot/depth")
  CoinexResponse<CoinexMarketDepth> marketDepth(
      @QueryParam("market") String market,
      @QueryParam("limit") Integer limit,
      @QueryParam("interval") Integer interval)
      throws IOException, CoinexException;
}
