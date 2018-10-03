package org.knowm.xchange.bitcointoyou;

import java.io.IOException;
import java.util.Map;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.MediaType;
import org.knowm.xchange.bitcointoyou.dto.marketdata.BitcointoyouMarketData;
import org.knowm.xchange.bitcointoyou.dto.marketdata.BitcointoyouOrderBook;
import org.knowm.xchange.bitcointoyou.dto.marketdata.BitcointoyouPublicTrade;

/**
 * Bitcointoyou Exchange public end-points.
 *
 * @author Jonathas Carrijo
 * @author Danilo Guimaraes
 */
@Path("API/")
@Produces(MediaType.APPLICATION_JSON)
public interface Bitcointoyou {

  /**
   * Get the Bitcointoyou Exchange ticker
   *
   * @return a {@link Map} containing an instance of {@link BitcointoyouMarketData}
   * @throws BitcointoyouException
   * @throws IOException
   */
  @GET
  @Path("ticker.aspx")
  Map<String, BitcointoyouMarketData> getTicker() throws BitcointoyouException, IOException;

  /**
   * Get the public order book at Bitcointoyou Exchange
   *
   * @return an instance of {@link BitcointoyouOrderBook}
   * @throws BitcointoyouException
   * @throws IOException
   */
  @GET
  @Path("orderbook.aspx")
  BitcointoyouOrderBook getOrderBook() throws BitcointoyouException, IOException;

  /**
   * List all public trades made at Bitcointoyou Exchange.
   *
   * @param currency the currency. BTC or LTC. Optional
   * @param tradeTimestamp trade timestamp, in UNIX Time format. Filter trades made after the
   *     provided timestamp. Optional
   * @param minTradeId minimum trade ID. Filter trades made which tradeID is greater or equal to
   *     {@code minTradeId}. Optional
   * @return an array of {@link BitcointoyouPublicTrade}
   * @throws BitcointoyouException
   * @throws IOException
   */
  @GET
  @Path("trades.aspx")
  BitcointoyouPublicTrade[] getTrades(
      @QueryParam("currency") String currency,
      @QueryParam("timestamp") Long tradeTimestamp,
      @QueryParam("tid") Long minTradeId)
      throws BitcointoyouException, IOException;
}
