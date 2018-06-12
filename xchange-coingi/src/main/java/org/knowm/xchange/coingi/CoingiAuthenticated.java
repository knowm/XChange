package org.knowm.xchange.coingi;

import org.knowm.xchange.coingi.dto.CoingiException;
import org.knowm.xchange.coingi.dto.account.CoingiBalances;
import org.knowm.xchange.coingi.dto.account.TransactionList;
import org.knowm.xchange.coingi.dto.account.WithdrawalResponse;
import org.knowm.xchange.coingi.dto.request.*;
import org.knowm.xchange.coingi.dto.trade.CoingiOrder;
import org.knowm.xchange.coingi.dto.trade.CoingiOrdersList;
import org.knowm.xchange.coingi.dto.trade.CoingiPlaceOrderResponse;
import org.knowm.xchange.currency.CurrencyPair;
import org.knowm.xchange.utils.jackson.CurrencyPairDeserializer;

import javax.ws.rs.Consumes;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import java.io.IOException;
import java.util.Objects;

@Path("user")
@Consumes(MediaType.APPLICATION_JSON)
@Produces(MediaType.APPLICATION_JSON)
public interface CoingiAuthenticated {
  @POST
  @Path("add-order")
  CoingiPlaceOrderResponse placeLimitOrder(PlaceLimitOrderRequest request)
      throws CoingiException, IOException;

  /** @return true if order has been canceled. */
  @POST
  @Path("cancel-order")
  CoingiOrder cancelOrder(CancelOrderRequest request) throws CoingiException, IOException;

  @POST
  @Path("get-order")
  CoingiOrder getOrderStatus(GetOrderRequest request) throws CoingiException, IOException;

  @POST
  @Path("orders")
  CoingiOrdersList getOrderHistory(GetOrderHistoryRequest request)
      throws CoingiException, IOException;

  @POST
  @Path("balance")
  CoingiBalances getUserBalance(BalanceRequest balanceRequest) throws CoingiException, IOException;

  @POST
  @Path("transactions")
  TransactionList getTransactionHistory(TransactionHistoryRequest request)
      throws CoingiException, IOException;

  @POST
  @Path("create-crypto-withdrawal")
  WithdrawalResponse createWithdrawal(WithdrawalRequest request)
      throws CoingiException, IOException;

  class Pair {
    public final CurrencyPair pair;

    public Pair(CurrencyPair pair) {
      this.pair = pair;
    }

    public Pair(String pair) {
      this(CurrencyPairDeserializer.getCurrencyPairFromString(pair));
    }

    @Override
    public boolean equals(Object o) {
      return this == o
          || !(o == null || getClass() != o.getClass()) && Objects.equals(pair, ((Pair) o).pair);
    }

    @Override
    public int hashCode() {
      return Objects.hash(pair);
    }

    @Override
    public String toString() {
      return pair == null
          ? ""
          : String.format("%s-%s", pair.base.getCurrencyCode(), pair.counter.getCurrencyCode())
              .toLowerCase();
    }
  }
}
