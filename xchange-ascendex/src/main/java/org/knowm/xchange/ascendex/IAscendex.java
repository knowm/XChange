package org.knowm.xchange.ascendex;

import java.io.IOException;
import java.util.List;
import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import org.knowm.xchange.ascendex.dto.AscendexResponse;
import org.knowm.xchange.ascendex.dto.marketdata.*;
import org.knowm.xchange.ascendex.dto.trade.AscendexOrderResponse;
import si.mazi.rescu.ParamsDigest;

@Path("api/pro")
@Produces(MediaType.APPLICATION_JSON)
public interface IAscendex {
  /**=========================Market Data (Public)======================================**/
  @GET
  @Path("/v1/assets")
  @Deprecated
  AscendexResponse<List<AscendexAssetDto>> getAllAssets() throws IOException;

  @GET
  @Path("/v2/assets")
  AscendexResponse<List<AscendexAssetDto>> getAllAssetsV2() throws IOException;

  @GET
  @Path("/v1/products")
  AscendexResponse<List<AscendexProductDto>> getAllProducts() throws IOException;

  @GET
  @Path("/v1/{accountCategory}/products")
  AscendexResponse<List<AscendexProductKindDto>> getAllProducts(@PathParam("accountCategory") AccountCategory accountCategory) throws IOException;

  @GET
  @Path("/v1/spot/ticker")
  AscendexResponse<AscendexTickerDto> getTicker( @QueryParam("symbol") String symbol) throws IOException;

  @GET
  @Path("/v1/barhist")
  AscendexResponse<List<AscendexBarHistDto>> getHistoricalBarData(
          @QueryParam("symbol") String symbol,
          @QueryParam("interval") String internal,
          @QueryParam("to") Long to,
          @QueryParam("from") Long from,
          @QueryParam("n") Integer noOfBars)
          throws IOException;

  @GET
  @Path("/v1/depth")
  AscendexResponse<AscendexOrderbookDto> getOrderbookDepth(@QueryParam("symbol") String symbol)
      throws IOException;

  @GET
  @Path("/v1/trades")
  AscendexResponse<AscendexMarketTradesDto> getTrades(@QueryParam("symbol") String symbol,@QueryParam("n") Integer n)
      throws IOException;


}
