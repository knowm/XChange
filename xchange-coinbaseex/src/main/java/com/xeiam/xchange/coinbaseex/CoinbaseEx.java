package com.xeiam.xchange.coinbaseex;

import java.io.IOException;
import java.util.List;

import javax.ws.rs.Consumes;
import javax.ws.rs.DELETE;
import javax.ws.rs.GET;
import javax.ws.rs.HeaderParam;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

import si.mazi.rescu.ParamsDigest;

import com.xeiam.xchange.coinbaseex.dto.account.CoinbaseExAccount;
import com.xeiam.xchange.coinbaseex.dto.marketdata.CoinbaseExProduct;
import com.xeiam.xchange.coinbaseex.dto.marketdata.CoinbaseExProductBook;
import com.xeiam.xchange.coinbaseex.dto.marketdata.CoinbaseExProductStats;
import com.xeiam.xchange.coinbaseex.dto.marketdata.CoinbaseExProductTicker;
import com.xeiam.xchange.coinbaseex.dto.marketdata.CoinbaseExTrade;
import com.xeiam.xchange.coinbaseex.dto.trade.CoinbaseExIdResponse;
import com.xeiam.xchange.coinbaseex.dto.trade.CoinbaseExOrder;
import com.xeiam.xchange.coinbaseex.dto.trade.CoinbaseExPlaceOrder;


@Path("/")
@Produces(MediaType.APPLICATION_JSON)
public interface CoinbaseEx {

  @GET
  @Path("products")
  List<CoinbaseExProduct> getProducts() throws IOException;

  @GET
  @Path("products/{baseCurrency}-{targetCurrency}/ticker")
  CoinbaseExProductTicker getProductTicker(@PathParam("baseCurrency") String baseCurrency, @PathParam("targetCurrency") String targetCurrency) throws IOException;

  @GET
  @Path("products/{baseCurrency}-{targetCurrency}/stats")
  CoinbaseExProductStats getProductStats(@PathParam("baseCurrency")String baseCurrency, @PathParam("targetCurrency") String targetCurrency) throws IOException;

  @GET
  @Path("products/{baseCurrency}-{targetCurrency}/book?level={level}")
  CoinbaseExProductBook getProductOrderBook(@PathParam("baseCurrency")String baseCurrency, @PathParam("targetCurrency") String targetCurrency, @PathParam("level") String level) throws IOException;
  
  @GET
  @Path("products/{baseCurrency}-{targetCurrency}/trades?limit={limit}")
  CoinbaseExTrade[] getTrades(@PathParam("baseCurrency")String baseCurrency, @PathParam("targetCurrency") String targetCurrency, @PathParam("limit") String limit) throws IOException;

  
  /** Authenticated calls */
  
  @GET
  @Path("accounts")
  CoinbaseExAccount[] getAccounts(@HeaderParam("CB-ACCESS-KEY") String apiKey, @HeaderParam("CB-ACCESS-SIGN") ParamsDigest signer, 
		  @HeaderParam("CB-ACCESS-TIMESTAMP") String timestamp, @HeaderParam("CB-ACCESS-PASSPHRASE") String passphrase);
  
  @GET
  @Path("orders?status={status}")
  CoinbaseExOrder[] getListOrders(@HeaderParam("CB-ACCESS-KEY") String apiKey, @HeaderParam("CB-ACCESS-SIGN") ParamsDigest signer, 
		  @HeaderParam("CB-ACCESS-TIMESTAMP") String timestamp, @HeaderParam("CB-ACCESS-PASSPHRASE") String passphrase, @PathParam("status") String status);
  
  @POST
  @Path("orders")
  @Consumes(MediaType.APPLICATION_JSON)
  CoinbaseExIdResponse placeLimitOrder(CoinbaseExPlaceOrder placeOrder, @HeaderParam("CB-ACCESS-KEY") String apiKey, @HeaderParam("CB-ACCESS-SIGN") ParamsDigest signer, 
		  @HeaderParam("CB-ACCESS-TIMESTAMP") String timestamp, @HeaderParam("CB-ACCESS-PASSPHRASE") String passphrase);
  
  @DELETE
  @Path("orders/{id}")
  @Produces(MediaType.TEXT_PLAIN)
  @Consumes(MediaType.TEXT_PLAIN)
  void cancelOrder(@PathParam("id") String id, @HeaderParam("CB-ACCESS-KEY") String apiKey, @HeaderParam("CB-ACCESS-SIGN") ParamsDigest signer, 
		  @HeaderParam("CB-ACCESS-TIMESTAMP") String timestamp, @HeaderParam("CB-ACCESS-PASSPHRASE") String passphrase);
  
  
}