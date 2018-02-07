package org.knowm.xchange.bibox;

import java.io.IOException;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.MediaType;

import org.knowm.xchange.bibox.dto.BiboxResponse;
import org.knowm.xchange.bibox.dto.marketdata.BiboxTicker;

/**
 * @author odrotleff
 */
@Path("v1")
@Produces(MediaType.APPLICATION_JSON)
public interface Bibox {

  /**
   * Retrieves a ticker.
   * 
   * @param symbol the currency pair
   * @return
   * @throws IOException
   */
  @GET
  @Path("mdata")
  BiboxResponse<BiboxTicker> mdata(@QueryParam("cmd") String cmd, @QueryParam("pair") String pair) throws IOException, BiboxException;
}
