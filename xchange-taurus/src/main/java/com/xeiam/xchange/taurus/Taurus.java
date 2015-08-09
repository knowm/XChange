package com.xeiam.xchange.taurus;

import java.io.IOException;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.MediaType;

import com.xeiam.xchange.taurus.dto.marketdata.TaurusOrderBook;
import com.xeiam.xchange.taurus.dto.marketdata.TaurusTicker;
import com.xeiam.xchange.taurus.dto.marketdata.TaurusTransaction;

@Path("/")
@Produces(MediaType.APPLICATION_JSON)
public interface Taurus {

  @GET
  @Path("order_book/")
  TaurusOrderBook getOrderBook() throws IOException;

  @GET
  @Path("ticker/")
  TaurusTicker getTicker() throws IOException;

  @GET
  @Path("transactions/")
  TaurusTransaction[] getTransactions() throws IOException;

  @GET
  @Path("transactions/")
  TaurusTransaction[] getTransactions(@QueryParam("time") Time time) throws IOException;

  enum Time {
    hour, minute
  }
}
