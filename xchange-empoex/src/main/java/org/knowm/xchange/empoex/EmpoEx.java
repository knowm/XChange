package org.knowm.xchange.empoex;

import java.io.IOException;
import java.util.List;
import java.util.Map;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

import org.knowm.xchange.empoex.dto.marketdata.EmpoExLevel;
import org.knowm.xchange.empoex.dto.marketdata.EmpoExTicker;
import org.knowm.xchange.empoex.dto.marketdata.EmpoExTrade;

@Path("/")
@Produces(MediaType.APPLICATION_JSON)
public interface EmpoEx {

  @GET
  @Path("marketinfo")
  List<EmpoExTicker> getEmpoExTickers() throws IOException;

  @GET
  @Path("marketinfo/{pairString}/")
  List<EmpoExTicker> getEmpoExTicker(@PathParam("pairString") String pairstring) throws IOException;

  @GET
  @Path("markethistory/{pairString}/")
  Map<String, List<EmpoExTrade>> getEmpoExTrades(@PathParam("pairString") String pairString) throws IOException;

  @GET
  @Path("orderbook/{pairString}/")
  Map<String, Map<String, List<EmpoExLevel>>> getEmpoExDepth(@PathParam("pairString") String pairString) throws IOException;

}
