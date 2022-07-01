package org.knowm.xchange.coingi;

import java.io.IOException;
import javax.ws.rs.Consumes;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import org.knowm.xchange.coingi.dto.CoingiException;
import org.knowm.xchange.coingi.dto.account.CoingiBalanceRequest;
import org.knowm.xchange.coingi.dto.account.CoingiBalances;
import org.knowm.xchange.coingi.dto.account.CoingiDepositWalletRequest;
import org.knowm.xchange.coingi.dto.account.CoingiDepositWalletResponse;
import org.knowm.xchange.coingi.dto.account.CoingiUserTransactionList;
import org.knowm.xchange.coingi.dto.account.CoingiWithdrawalRequest;
import org.knowm.xchange.coingi.dto.account.CoingiWithdrawalResponse;
import org.knowm.xchange.coingi.dto.trade.CoingiCancelOrderRequest;
import org.knowm.xchange.coingi.dto.trade.CoingiGetOrderHistoryRequest;
import org.knowm.xchange.coingi.dto.trade.CoingiGetOrderRequest;
import org.knowm.xchange.coingi.dto.trade.CoingiOrder;
import org.knowm.xchange.coingi.dto.trade.CoingiOrdersList;
import org.knowm.xchange.coingi.dto.trade.CoingiPlaceLimitOrderRequest;
import org.knowm.xchange.coingi.dto.trade.CoingiPlaceOrderResponse;
import org.knowm.xchange.coingi.dto.trade.CoingiTransactionHistoryRequest;

@Path("user")
@Consumes(MediaType.APPLICATION_JSON)
@Produces(MediaType.APPLICATION_JSON)
public interface CoingiAuthenticated {
  @POST
  @Path("add-order")
  CoingiPlaceOrderResponse placeLimitOrder(CoingiPlaceLimitOrderRequest request)
      throws CoingiException, IOException;

  /** @return true if order has been canceled. */
  @POST
  @Path("cancel-order")
  CoingiOrder cancelOrder(CoingiCancelOrderRequest request) throws CoingiException, IOException;

  @POST
  @Path("get-order")
  CoingiOrder getOrderStatus(CoingiGetOrderRequest request) throws CoingiException, IOException;

  @POST
  @Path("orders")
  CoingiOrdersList getOrderHistory(CoingiGetOrderHistoryRequest request)
      throws CoingiException, IOException;

  @POST
  @Path("balance")
  CoingiBalances getUserBalance(CoingiBalanceRequest balanceRequest)
      throws CoingiException, IOException;

  @POST
  @Path("transactions")
  CoingiUserTransactionList getTransactionHistory(CoingiTransactionHistoryRequest request)
      throws CoingiException, IOException;

  @POST
  @Path("create-crypto-withdrawal")
  CoingiWithdrawalResponse createWithdrawal(CoingiWithdrawalRequest request)
      throws CoingiException, IOException;

  @POST
  @Path("deposit-wallet")
  CoingiDepositWalletResponse depositWallet(CoingiDepositWalletRequest request)
      throws CoingiException, IOException;
}
