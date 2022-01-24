package org.knowm.xchange.ascendex;

import java.io.IOException;
import java.util.List;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.MediaType;
import org.knowm.xchange.ascendex.dto.AscendexResponse;
import org.knowm.xchange.ascendex.dto.marketdata.AscendexAssetDto;
import org.knowm.xchange.ascendex.dto.marketdata.AscendexBarHistDto;
import org.knowm.xchange.ascendex.dto.marketdata.AscendexMarketTradesDto;
import org.knowm.xchange.ascendex.dto.marketdata.AscendexOrderbookDto;
import org.knowm.xchange.ascendex.dto.marketdata.AscendexProductDto;

@Path("api/pro/v1")
@Produces(MediaType.APPLICATION_JSON)
public interface IAscendex {

  @GET
  @Path("/assets")
  AscendexResponse<List<AscendexAssetDto>> getAllAssets() throws IOException;

  @GET
  @Path("/products")
  AscendexResponse<List<AscendexProductDto>> getAllProducts() throws IOException;

  @GET
  @Path("/depth")
  AscendexResponse<AscendexOrderbookDto> getOrderbookDepth(@QueryParam("symbol") String symbol)
      throws IOException;

  @GET
  @Path("/trades")
  AscendexResponse<AscendexMarketTradesDto> getTrades(@QueryParam("symbol") String symbol)
      throws IOException;

  @GET
  @Path("/barhist")
  AscendexResponse<List<AscendexBarHistDto>> getHistoricalBarData(
      @QueryParam("symbol") String symbol,
      @QueryParam("interval") String internal,
      @QueryParam("to") Long to,
      @QueryParam("from") Long from,
      @QueryParam("n") Integer noOfBars)
      throws IOException;
}
