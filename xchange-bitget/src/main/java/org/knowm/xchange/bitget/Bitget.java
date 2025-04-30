package org.knowm.xchange.bitget;

import jakarta.ws.rs.GET;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.Produces;
import jakarta.ws.rs.QueryParam;
import jakarta.ws.rs.core.MediaType;
import java.io.IOException;
import java.util.List;
import org.knowm.xchange.bitget.dto.BitgetException;
import org.knowm.xchange.bitget.dto.BitgetResponse;
import org.knowm.xchange.bitget.dto.marketdata.BitgetCoinDto;
import org.knowm.xchange.bitget.dto.marketdata.BitgetMarketDepthDto;
import org.knowm.xchange.bitget.dto.marketdata.BitgetServerTime;
import org.knowm.xchange.bitget.dto.marketdata.BitgetSymbolDto;
import org.knowm.xchange.bitget.dto.marketdata.BitgetTickerDto;

@Path("")
@Produces(MediaType.APPLICATION_JSON)
public interface Bitget {

  @GET
  @Path("api/v2/public/time")
  BitgetResponse<BitgetServerTime> serverTime() throws IOException, BitgetException;

  @GET
  @Path("api/v2/spot/public/coins")
  BitgetResponse<List<BitgetCoinDto>> coins(@QueryParam("coin") String coin)
      throws IOException, BitgetException;

  @GET
  @Path("api/v2/spot/public/symbols")
  BitgetResponse<List<BitgetSymbolDto>> symbols(@QueryParam("symbol") String symbol)
      throws IOException, BitgetException;

  @GET
  @Path("api/v2/spot/market/tickers")
  BitgetResponse<List<BitgetTickerDto>> tickers(@QueryParam("symbol") String symbol)
      throws IOException, BitgetException;

  @GET
  @Path("api/v2/spot/market/orderbook")
  BitgetResponse<BitgetMarketDepthDto> orderbook(@QueryParam("symbol") String symbol)
      throws IOException, BitgetException;
}
