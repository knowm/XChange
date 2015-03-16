package com.xeiam.xchange.bitkonan;

import java.io.IOException;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

import com.xeiam.xchange.bitkonan.dto.marketdata.BitKonanOrderBook;
import com.xeiam.xchange.bitkonan.dto.marketdata.BitKonanTicker;

/**
 * @author Piotr Ładyżyński
 */
@Path("api")
@Produces(MediaType.APPLICATION_JSON)
public interface BitKonan {

  /**
   * @return BTCCentral ticker
   * @throws java.io.IOException
   */
  @GET
  @Path("ticker")
  public BitKonanTicker getBitKonanTickerBTC() throws IOException;

  @GET
  @Path("btc_orderbook")
  public BitKonanOrderBook getBitKonanOrderBookBTC() throws IOException;

}
