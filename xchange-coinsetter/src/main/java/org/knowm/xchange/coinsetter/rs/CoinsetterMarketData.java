package org.knowm.xchange.coinsetter.rs;

import java.io.IOException;
import java.math.BigDecimal;

import javax.ws.rs.DefaultValue;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.MediaType;

import org.knowm.xchange.coinsetter.CoinsetterException;
import org.knowm.xchange.coinsetter.dto.marketdata.CoinsetterLast;
import org.knowm.xchange.coinsetter.dto.marketdata.CoinsetterListDepth;
import org.knowm.xchange.coinsetter.dto.marketdata.CoinsetterPairedDepth;
import org.knowm.xchange.coinsetter.dto.marketdata.CoinsetterQuote;
import org.knowm.xchange.coinsetter.dto.marketdata.CoinsetterTicker;

/**
 * RESTful/JSON API: Market Data.
 */
@Path("/marketdata")
@Produces(MediaType.APPLICATION_JSON)
public interface CoinsetterMarketData {

  @GET
  @Path("last")
  public CoinsetterLast getLast() throws CoinsetterException, IOException;

  @GET
  @Path("last")
  public CoinsetterLast getLast(@QueryParam("since") int lookback) throws CoinsetterException, IOException;

  @GET
  @Path("ticker")
  public CoinsetterTicker getTicker() throws CoinsetterException, IOException;

  @GET
  @Path("depth")
  public CoinsetterPairedDepth getPairedDepth(@QueryParam("depth") @DefaultValue("10") int depth,
      @QueryParam("exchange") @DefaultValue("SMART") String exchange) throws CoinsetterException, IOException;

  @GET
  @Path("depth")
  public CoinsetterListDepth getListDepth(@QueryParam("depth") @DefaultValue("10") int depth, @QueryParam("format") String format,
      @QueryParam("exchange") @DefaultValue("SMART") String exchange) throws CoinsetterException, IOException;

  @GET
  @Path("full_depth")
  public CoinsetterListDepth getFullDepth() throws CoinsetterException, IOException;

  @GET
  @Path("full_depth")
  public CoinsetterListDepth getFullDepth(@QueryParam("exchange") String exchange) throws CoinsetterException, IOException;

  @GET
  @Path("quote")
  public CoinsetterQuote getQuote(@QueryParam("quantity") BigDecimal quantity, @QueryParam("symbol") String symbol)
      throws CoinsetterException, IOException;

}
