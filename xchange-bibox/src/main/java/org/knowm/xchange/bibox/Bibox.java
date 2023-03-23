package org.knowm.xchange.bibox;

import java.io.IOException;
import java.util.List;
import jakarta.ws.rs.FormParam;
import jakarta.ws.rs.GET;
import jakarta.ws.rs.POST;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.Produces;
import jakarta.ws.rs.QueryParam;
import jakarta.ws.rs.core.MediaType;
import org.knowm.xchange.bibox.dto.BiboxMultipleResponses;
import org.knowm.xchange.bibox.dto.BiboxResponse;
import org.knowm.xchange.bibox.dto.marketdata.BiboxMarket;
import org.knowm.xchange.bibox.dto.marketdata.BiboxTicker;
import org.knowm.xchange.bibox.dto.trade.BiboxDeals;
import org.knowm.xchange.bibox.dto.trade.BiboxOrderBook;

/** @author odrotleff */
@Path("v1")
@Produces(MediaType.APPLICATION_JSON)
public interface Bibox {

  static final String FORM_CMDS = "cmds";
  static final String FORM_APIKEY = "apikey";
  static final String FORM_SIGNATURE = "sign";

  @GET
  @Path("mdata")
  BiboxResponse<BiboxTicker> mdata(@QueryParam("cmd") String cmd, @QueryParam("pair") String pair)
      throws IOException, BiboxException;

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
      @QueryParam("size") Integer size)
      throws IOException, BiboxException;

  /**
   * Retrieves the trade history.
   *
   * @param cmd always "deals"
   * @param pair the currency pair
   * @param size how many，1-200，if not passed will return 200
   * @return
   * @throws IOException
   * @throws BiboxException
   */
  @GET
  @Path("mdata")
  BiboxResponse<List<BiboxDeals>> deals(
      @QueryParam("cmd") String cmd,
      @QueryParam("pair") String pair,
      @QueryParam("size") Integer size)
      throws IOException, BiboxException;

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

  /**
   * Retrieve order books.
   *
   * @param cmds contains information about the list of order books to fetch.
   * @return list of order books
   */
  @POST
  @Path("mdata")
  BiboxMultipleResponses<BiboxOrderBook> orderBooks(@FormParam(FORM_CMDS) String cmds);
}
