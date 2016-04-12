package org.knowm.xchange.cryptsy;

import java.io.IOException;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;

import org.knowm.xchange.cryptsy.dto.marketdata.CryptsyCurrencyPairsReturn;
import org.knowm.xchange.cryptsy.dto.marketdata.CryptsyPublicMarketDataReturn;
import org.knowm.xchange.cryptsy.dto.marketdata.CryptsyPublicOrderbookReturn;

/**
 * @author ObsessiveOrange
 */
@Path("/")
public interface Cryptsy {

  @GET
  @Path("api.php?method=marketdatav2")
  CryptsyPublicMarketDataReturn getAllMarketData() throws IOException;

  @GET
  @Path("api.php?method=singlemarketdata&marketid={marketid}")
  CryptsyPublicMarketDataReturn getMarketData(@PathParam("marketid") int marketId) throws IOException;

  @GET
  @Path("api.php?method=orderdatav2")
  CryptsyPublicOrderbookReturn getAllOrderbookData() throws IOException;

  @GET
  @Path("api.php?method=singleorderdata&marketid={marketid}")
  CryptsyPublicOrderbookReturn getOrderbookData(@PathParam("marketid") int marketId) throws IOException;

  @GET
  @Path("api.php?method=orderdatav2")
  CryptsyCurrencyPairsReturn getCryptsyCurrencyPairs() throws IOException;
}
