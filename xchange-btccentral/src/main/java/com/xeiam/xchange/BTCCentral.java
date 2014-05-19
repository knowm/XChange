package com.xeiam.xchange;

import com.xeiam.xchange.dto.marketdata.BTCCentralTicker;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import java.io.IOException;

/**
 * @author
 */
@Path("/")
@Produces(MediaType.APPLICATION_JSON)
public interface BTCCentral {


  /**
   *
   * @return BTCCentral ticker
   * @throws IOException
   */
  @GET
  @Path("ticker/")
  public BTCCentralTicker getBTCCentralTicker() throws IOException;
}
