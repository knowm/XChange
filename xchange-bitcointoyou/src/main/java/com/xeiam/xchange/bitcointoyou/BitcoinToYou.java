package com.xeiam.xchange.bitcointoyou;

import java.io.IOException;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.MediaType;

import com.xeiam.xchange.bitcointoyou.dto.marketdata.BitcoinToYouOrderBook;
import com.xeiam.xchange.bitcointoyou.dto.marketdata.BitcoinToYouTicker;
import com.xeiam.xchange.bitcointoyou.dto.marketdata.BitcoinToYouTransaction;

/**
 * @author Matija Mazi
 * @author Felipe Micaroni Lalli - See https://www.bitcointoyou.com/DocumentacaoAPI.aspx for up-to-date docs.
 * @see BitcoinToYouAuthenticated
 */
@Path("API")
@Produces(MediaType.APPLICATION_JSON)
public interface BitcoinToYou {

  /**
   * Returns "bids" and "asks". Each is a list of open orders and each order is represented as a list of price and amount.
   */
  @GET
  @Path("/orderbook.aspx")
  public BitcoinToYouOrderBook getOrderBookBTC() throws IOException;

  /**
   * Returns "bids" and "asks". Each is a list of open orders and each order is represented as a list of price and amount.
   */
  @GET
  @Path("/orderbook_litecoin.aspx")
  public BitcoinToYouOrderBook getOrderBookLTC() throws IOException;

  @GET
  @Path("/ticker.aspx")
  public BitcoinToYouTicker getTickerBTC() throws IOException;

  @GET
  @Path("/ticker_litecoin.aspx")
  public BitcoinToYouTicker getTickerLTC() throws IOException;

  @GET
  @Path("/trades.aspx")
  public BitcoinToYouTransaction[] getTransactions(@Nonnull @QueryParam("currency") String currency,
      @Nullable @QueryParam("timestamp") Long timestamp, @Nullable @QueryParam("tid") String tid) throws IOException;
}
