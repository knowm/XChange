package org.knowm.xchange.bithumb;

import java.io.IOException;
import java.util.List;
import jakarta.ws.rs.GET;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.PathParam;
import jakarta.ws.rs.Produces;
import jakarta.ws.rs.core.MediaType;
import org.knowm.xchange.bithumb.dto.BithumbResponse;
import org.knowm.xchange.bithumb.dto.marketdata.BithumbOrderbook;
import org.knowm.xchange.bithumb.dto.marketdata.BithumbOrderbookAll;
import org.knowm.xchange.bithumb.dto.marketdata.BithumbTicker;
import org.knowm.xchange.bithumb.dto.marketdata.BithumbTickersReturn;
import org.knowm.xchange.bithumb.dto.marketdata.BithumbTransactionHistory;

@Path("public")
@Produces(MediaType.APPLICATION_JSON)
public interface Bithumb {

  @GET
  @Path("ticker/ALL")
  BithumbResponse<BithumbTickersReturn> tickerAll() throws IOException, BithumbException;

  @GET
  @Path("ticker/{currency}")
  BithumbResponse<BithumbTicker> ticker(@PathParam("currency") String currency)
      throws IOException, BithumbException;

  @GET
  @Path("orderbook/ALL")
  BithumbResponse<BithumbOrderbookAll> orderbookAll() throws IOException, BithumbException;

  @GET
  @Path("orderbook/{currency}")
  BithumbResponse<BithumbOrderbook> orderbook(@PathParam("currency") String currency)
      throws IOException, BithumbException;

  @GET
  @Path("transaction_history/{currency}")
  BithumbResponse<List<BithumbTransactionHistory>> transactionHistory(
      @PathParam("currency") String currency) throws IOException, BithumbException;
}