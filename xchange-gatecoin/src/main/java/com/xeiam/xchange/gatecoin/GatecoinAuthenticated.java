package com.xeiam.xchange.gatecoin;

import java.io.IOException;
import java.math.BigDecimal;
import javax.ws.rs.FormParam;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import si.mazi.rescu.ParamsDigest;
import com.xeiam.xchange.gatecoin.dto.account.Results.GatecoinBalanceResult;
import com.xeiam.xchange.gatecoin.dto.account.Results.GatecoinDepositAddressResult;
import com.xeiam.xchange.gatecoin.dto.account.Results.GatecoinWithdrawResult;
import com.xeiam.xchange.gatecoin.dto.trade.Results.GatecoinCancelOrderResult;
import com.xeiam.xchange.gatecoin.dto.trade.Results.GatecoinOrderResult;
import com.xeiam.xchange.gatecoin.dto.trade.Results.GatecoinPlaceOrderResult;
import com.xeiam.xchange.gatecoin.dto.trade.Results.GatecoinTradeHistoryResult;
import javax.ws.rs.DELETE;
import javax.ws.rs.GET;
import javax.ws.rs.HeaderParam;
import javax.ws.rs.PathParam;
import javax.ws.rs.QueryParam;

@Path("api")
//@Consumes(MediaType.APPLICATION_JSON)
@Produces(MediaType.APPLICATION_JSON)
public interface GatecoinAuthenticated extends Gatecoin {

  @GET
  @Path("Trade/Orders")
  public GatecoinOrderResult getOpenOrders(@HeaderParam("API_PUBLIC_KEY") String publicKey,
          @HeaderParam("API_REQUEST_SIGNATURE") ParamsDigest signature,
          @HeaderParam("API_REQUEST_DATE") String date) throws IOException;

  @POST
  @Path("Trade/Orders")
  public GatecoinPlaceOrderResult placeOrder(@HeaderParam("API_PUBLIC_KEY") String publicKey, 
            @HeaderParam("API_REQUEST_SIGNATURE") ParamsDigest signature,
            @HeaderParam("API_REQUEST_DATE") String date,
            @FormParam("Amount") BigDecimal Amount,
            @FormParam("Price") BigDecimal Price,
            @FormParam("Way") String Way,
            @FormParam("Code") String Code)
      throws IOException;

  @DELETE
  @Path("Trade/Orders")
  public GatecoinCancelOrderResult cancelAllOrders(@HeaderParam("API_PUBLIC_KEY") String publicKey,
          @HeaderParam("API_REQUEST_SIGNATURE") ParamsDigest signature,
          @HeaderParam("API_REQUEST_DATE") String date) throws  IOException;
  
  @DELETE
  @Path("Trade/Orders/{OrderId}")
  public GatecoinCancelOrderResult cancelOrder(@HeaderParam("API_PUBLIC_KEY") String publicKey,
          @HeaderParam("API_REQUEST_SIGNATURE") ParamsDigest signature,
          @HeaderParam("API_REQUEST_DATE") String date,
          @PathParam("OrderId") String OrderId) throws  IOException;

  @GET
  @Path("Trade/Trades")
  public GatecoinTradeHistoryResult getUserTrades(@HeaderParam("API_PUBLIC_KEY") String publicKey,
          @HeaderParam("API_REQUEST_SIGNATURE") ParamsDigest signature,
          @HeaderParam("API_REQUEST_DATE") String date) throws  IOException;
  
  @GET
  @Path("Trade/Trades")
  public GatecoinTradeHistoryResult getUserTrades(@HeaderParam("API_PUBLIC_KEY") String publicKey,
          @HeaderParam("API_REQUEST_SIGNATURE") ParamsDigest signature,
          @HeaderParam("API_REQUEST_DATE") String date,
          @QueryParam("Count") int Count) throws  IOException;
  
  @GET
  @Path("Trade/Trades")
  public GatecoinTradeHistoryResult getUserTrades(@HeaderParam("API_PUBLIC_KEY") String publicKey,
          @HeaderParam("API_REQUEST_SIGNATURE") ParamsDigest signature,
          @HeaderParam("API_REQUEST_DATE") String date,
          @QueryParam("Count") int Count,
          @QueryParam("TransactionID") long TransactionID) throws  IOException;

  @GET
  @Path("Balance/Balances")
  public GatecoinBalanceResult getUserBalance(@HeaderParam("API_PUBLIC_KEY") String publicKey,
          @HeaderParam("API_REQUEST_SIGNATURE") ParamsDigest signature,
          @HeaderParam("API_REQUEST_DATE") String date);
  @GET
  @Path("ElectronicWallet/DepositWallets")
  public GatecoinDepositAddressResult getDepositAddress(@HeaderParam("API_PUBLIC_KEY") String publicKey,
          @HeaderParam("API_REQUEST_SIGNATURE") ParamsDigest signature,
          @HeaderParam("API_REQUEST_DATE") String date) throws  IOException;

  @POST
  @Path("ElectronicWallet/Withdrawals/{DigiCurrency}")
  public GatecoinWithdrawResult withdrawBitcoin(@HeaderParam("API_PUBLIC_KEY") String publicKey,
          @HeaderParam("API_REQUEST_SIGNATURE") ParamsDigest signature,
          @HeaderParam("API_REQUEST_DATE") String date,
          @PathParam("DigiCurrency") String DigiCurrency,
          @FormParam("AddressName") String AddressName,
          @FormParam("Amount") BigDecimal Amount)
      throws  IOException;

}
