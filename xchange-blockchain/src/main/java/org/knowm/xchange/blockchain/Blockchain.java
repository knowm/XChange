package org.knowm.xchange.blockchain;

import java.io.IOException;
import java.util.Map;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import org.knowm.xchange.blockchain.dto.BlockchainException;
import org.knowm.xchange.blockchain.dto.account.BlockchainSymbol;
import org.knowm.xchange.blockchain.dto.marketdata.BlockchainOrderBook;

/**
 * @author scuevas
 * @see <a href="https://api.blockchain.info/v3/#/">Swagger</a>
 */
@Path("v3/exchange")
@Produces(MediaType.APPLICATION_JSON)
public interface Blockchain {

  /**
   * Get a list of symbols
   *
   * @return This returns a map where the key {@link String} represents the currency symbol and the
   *     value which is an instance of type {@link BlockchainSymbol}
   */
  @Path("/symbols")
  @GET
  Map<String, BlockchainSymbol> getSymbols();

  /**
   * Level 3 Order Book data is available through the l3 channel. Each entry in bids and asks arrays
   * is an order, along with its id (id), price (px) and quantity (qty) attributes.
   *
   * @param symbol
   * @return All individual orders without aggregation of the L3 order book.
   */
  @Path("/l3/{symbol}")
  @GET
  BlockchainOrderBook getOrderBookL3(@PathParam("symbol") String symbol)
      throws IOException, BlockchainException;
}
