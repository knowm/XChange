package org.knowm.xchange.bibox;

import java.io.IOException;
import java.util.List;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.MediaType;

import org.knowm.xchange.bibox.dto.BiboxResponse;
import org.knowm.xchange.bibox.dto.marketdata.BiboxMarket;
import org.knowm.xchange.bibox.dto.marketdata.BiboxTicker;
import org.knowm.xchange.bibox.dto.trade.BiboxOrderBook;

/**
 * @author odrotleff
 */
@Path("v1")
@Produces(MediaType.APPLICATION_JSON)
public interface Bibox {

  @GET
  @Path("mdata")
  BiboxResponse<BiboxTicker> mdata(
      @QueryParam("cmd") String cmd,
      @QueryParam("pair") String pair) throws IOException, BiboxException;

  /**
   * Retrieves the order book for a currency pair.
   * 
   * @param cmd always "depth"
   * @param pair the currency pair
   * @param size the max size of the order book (1-200)
   * @return
   * @throws IOException
   * @throws BiboxException
   */
  @GET
  @Path("mdata")
  BiboxResponse<BiboxOrderBook> orderBook(
      @QueryParam("cmd") String cmd,
      @QueryParam("pair") String pair,
      @QueryParam("size") Integer size) throws IOException, BiboxException;

  /**
   * Retrieves all tickers.
   * 
   * @param cmd always "marketAll"
   * @return
   * @throws IOException
   * @throws BiboxException
   */
  @GET
  @Path("mdata")
  BiboxResponse<List<BiboxMarket>> marketAll(@QueryParam("cmd") String cmd)
      throws IOException, BiboxException;
}
