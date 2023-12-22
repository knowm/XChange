package org.knowm.xchange.independentreserve;

import jakarta.ws.rs.Consumes;
import jakarta.ws.rs.POST;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.Produces;
import jakarta.ws.rs.core.MediaType;
import java.io.IOException;
import org.knowm.xchange.independentreserve.dto.IndependentReserveHttpStatusException;
import org.knowm.xchange.independentreserve.dto.account.IndependentReserveBalance;
import org.knowm.xchange.independentreserve.dto.account.IndependentReserveBrokerageFeeRequest;
import org.knowm.xchange.independentreserve.dto.account.IndependentReserveBrokerageFeeResponse;
import org.knowm.xchange.independentreserve.dto.account.IndependentReserveDepositAddressRequest;
import org.knowm.xchange.independentreserve.dto.account.IndependentReserveDepositAddressResponse;
import org.knowm.xchange.independentreserve.dto.account.IndependentReserveWithdrawDigitalCurrencyRequest;
import org.knowm.xchange.independentreserve.dto.auth.AuthAggregate;
import org.knowm.xchange.independentreserve.dto.trade.IndependentReserveCancelOrderRequest;
import org.knowm.xchange.independentreserve.dto.trade.IndependentReserveCancelOrderResponse;
import org.knowm.xchange.independentreserve.dto.trade.IndependentReserveOpenOrderRequest;
import org.knowm.xchange.independentreserve.dto.trade.IndependentReserveOpenOrdersResponse;
import org.knowm.xchange.independentreserve.dto.trade.IndependentReserveOrderDetailsRequest;
import org.knowm.xchange.independentreserve.dto.trade.IndependentReserveOrderDetailsResponse;
import org.knowm.xchange.independentreserve.dto.trade.IndependentReservePlaceLimitOrderRequest;
import org.knowm.xchange.independentreserve.dto.trade.IndependentReservePlaceLimitOrderResponse;
import org.knowm.xchange.independentreserve.dto.trade.IndependentReservePlaceMarketOrderRequest;
import org.knowm.xchange.independentreserve.dto.trade.IndependentReservePlaceMarketOrderResponse;
import org.knowm.xchange.independentreserve.dto.trade.IndependentReserveSynchDigitalCurrencyDepositAddressWithBlockchainRequest;
import org.knowm.xchange.independentreserve.dto.trade.IndependentReserveSynchDigitalCurrencyDepositAddressWithBlockchainResponse;
import org.knowm.xchange.independentreserve.dto.trade.IndependentReserveTradeHistoryRequest;
import org.knowm.xchange.independentreserve.dto.trade.IndependentReserveTradeHistoryResponse;
import org.knowm.xchange.independentreserve.dto.trade.IndependentReserveTransactionsRequest;
import org.knowm.xchange.independentreserve.dto.trade.IndependentReserveTransactionsResponse;

/** Author: Kamil Zbikowski Date: 4/10/15 */
@Path("Private")
@Produces(MediaType.APPLICATION_JSON)
public interface IndependentReserveAuthenticated {

  String SynchDigitalCurrencyDepositAddressWithBlockchain =
      "SynchDigitalCurrencyDepositAddressWithBlockchain";
  String WithdrawDigitalCurrency = "WithdrawDigitalCurrency";
  String GetDigitalCurrencyDepositAddress = "GetDigitalCurrencyDepositAddress";
  String GetBrokerageFees = "GetBrokerageFees";

  @POST
  @Path("GetAccounts")
  @Consumes(MediaType.APPLICATION_JSON)
  IndependentReserveBalance getBalance(AuthAggregate authAggregate)
      throws IndependentReserveHttpStatusException, IOException;

  @POST
  @Path("GetOpenOrders")
  @Consumes(MediaType.APPLICATION_JSON)
  IndependentReserveOpenOrdersResponse getOpenOrders(
      IndependentReserveOpenOrderRequest independentReserveOpenOrderRequest)
      throws IndependentReserveHttpStatusException, IOException;

  @POST
  @Path("GetTrades")
  @Consumes(MediaType.APPLICATION_JSON)
  IndependentReserveTradeHistoryResponse getTradeHistory(
      IndependentReserveTradeHistoryRequest independentReserveTradeHistoryRequest)
      throws IndependentReserveHttpStatusException, IOException;

  @POST
  @Path(GetDigitalCurrencyDepositAddress)
  @Consumes(MediaType.APPLICATION_JSON)
  IndependentReserveDepositAddressResponse getDigitalCurrencyDepositAddress(
      IndependentReserveDepositAddressRequest independentReserveDepositAddressRequest)
      throws IndependentReserveHttpStatusException, IOException;

  @POST
  @Path("PlaceLimitOrder")
  @Consumes(MediaType.APPLICATION_JSON)
  IndependentReservePlaceLimitOrderResponse placeLimitOrder(
      IndependentReservePlaceLimitOrderRequest independentReservePlaceLimitOrderRequest)
      throws IndependentReserveHttpStatusException, IOException;

  @POST
  @Path("PlaceMarketOrder")
  @Consumes(MediaType.APPLICATION_JSON)
  IndependentReservePlaceMarketOrderResponse placeMarketOrder(
      IndependentReservePlaceMarketOrderRequest independentReservePlaceMarketOrderRequest)
      throws IndependentReserveHttpStatusException, IOException;

  @POST
  @Path("CancelOrder")
  @Consumes(MediaType.APPLICATION_JSON)
  IndependentReserveCancelOrderResponse cancelOrder(
      IndependentReserveCancelOrderRequest independentReserveCancelOrderRequest)
      throws IndependentReserveHttpStatusException, IOException;

  @POST
  @Path("GetOrderDetails")
  @Consumes(MediaType.APPLICATION_JSON)
  IndependentReserveOrderDetailsResponse orderDetails(
      IndependentReserveOrderDetailsRequest independentReserveOrderDetailsRequest)
      throws IndependentReserveHttpStatusException, IOException;

  @POST
  @Path("GetTransactions")
  @Consumes(MediaType.APPLICATION_JSON)
  IndependentReserveTransactionsResponse getTransactions(
      IndependentReserveTransactionsRequest independentReserveTransactionsRequest)
      throws IndependentReserveHttpStatusException, IOException;

  /** Forces the deposit address to be checked for new Bitcoin or Ether deposits. */
  @POST
  @Path(SynchDigitalCurrencyDepositAddressWithBlockchain)
  @Consumes(MediaType.APPLICATION_JSON)
  IndependentReserveSynchDigitalCurrencyDepositAddressWithBlockchainResponse
      synchDigitalCurrencyDepositAddressWithBlockchain(
          IndependentReserveSynchDigitalCurrencyDepositAddressWithBlockchainRequest
              independentReserveSynchDigitalCurrencyDepositAddressWithBlockchainRequest)
          throws IndependentReserveHttpStatusException, IOException;

  @POST
  @Path(WithdrawDigitalCurrency)
  @Consumes(MediaType.APPLICATION_JSON)
  Object withdrawDigitalCurrency(IndependentReserveWithdrawDigitalCurrencyRequest req)
      throws IndependentReserveHttpStatusException, IOException;

  @POST
  @Path(GetBrokerageFees)
  @Consumes(MediaType.APPLICATION_JSON)
  IndependentReserveBrokerageFeeResponse getBrokerageFees(
      IndependentReserveBrokerageFeeRequest independentReserveBrokerageFeeRequest)
      throws IndependentReserveHttpStatusException, IOException;
}
