package org.knowm.xchange.lykke;

import java.io.IOException;
import java.util.List;
import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import org.knowm.xchange.lykke.dto.marketdata.LykkeAsset;
import org.knowm.xchange.lykke.dto.marketdata.LykkeAssetPair;
import org.knowm.xchange.lykke.dto.marketdata.LykkeOrderBook;

@Path("api/")
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
public interface Lykke {

  @GET
  @Path("assetpairs")
  List<LykkeAssetPair> getAssetPairs() throws IOException;

  @GET
  @Path("assetpairs/{id}")
  LykkeAssetPair getAssetPairById(@PathParam("id") String assetPair) throws IOException;

  @GET
  @Path("orderbooks")
  List<LykkeOrderBook> getAllOrderBooks() throws IOException;

  @GET
  @Path("orderbooks/{assetPairId}")
  List<LykkeOrderBook> getOrderBookByAssetPair(@PathParam("assetPairId") String assetPair)
      throws IOException;

  @GET
  @Path("assets/dictionary")
  List<LykkeAsset> getLykkeAsset() throws IOException;
}
