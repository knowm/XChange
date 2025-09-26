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
import org.knowm.xchange.bitget.dto.marketdata.*;

@Path("")
@Produces(MediaType.APPLICATION_JSON)
public interface BitgetFutures {

  @GET
  @Path("api/v2/public/time")
  BitgetResponse<BitgetServerTime> serverTime() throws IOException, BitgetException;

  @GET
  @Path("api/v2/mix/market/contracts")
  BitgetResponse<List<BitgetContractDto>> contracts(
      @QueryParam("symbol") String symbol, @QueryParam("productType") String productType)
      throws IOException, BitgetException;

  @GET
  @Path("api/v2/mix/market/tickers")
  BitgetResponse<List<BitgetFuturesTickerDto>> allTickers(
      @QueryParam("productType") String productType) throws IOException, BitgetException;

  @GET
  @Path("api/v2/mix/market/ticker")
  BitgetResponse<List<BitgetFuturesTickerDto>> ticker(
      @QueryParam("symbol") String symbol, @QueryParam("productType") String productType)
      throws IOException, BitgetException;
}
