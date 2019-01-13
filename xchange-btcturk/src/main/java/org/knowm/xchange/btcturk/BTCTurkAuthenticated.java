package org.knowm.xchange.btcturk;

import java.io.IOException;
import java.util.List;

import javax.ws.rs.DELETE;
import javax.ws.rs.FormParam;
import javax.ws.rs.GET;
import javax.ws.rs.HeaderParam;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.MediaType;

import org.knowm.xchange.btcturk.dto.BTCTurkSort;
import org.knowm.xchange.btcturk.dto.account.BTCTurkAccountBalance;
import org.knowm.xchange.btcturk.dto.account.BTCTurkDepositRequestResult;
import org.knowm.xchange.btcturk.dto.account.BTCTurkUserTransactions;
import org.knowm.xchange.btcturk.dto.account.BTCTurkWithdrawalRequestInfo;
import org.knowm.xchange.btcturk.dto.trade.BTCTurkCancelOrderResult;
import org.knowm.xchange.btcturk.dto.trade.BTCTurkOpenOrders;
import org.knowm.xchange.btcturk.dto.trade.BTCTurkExchangeResult;

import si.mazi.rescu.ParamsDigest;
import si.mazi.rescu.SynchronizedValueFactory;

/** @author mertguner */
@Path("api/")
@Produces(MediaType.APPLICATION_JSON)
public interface BTCTurkAuthenticated extends BTCTurk{

	/** Get the authenticated account's balance. 
	  * @author mertguner
	  * @exception IOException
	  * @return An object of type AccountBalance. Null if account balance cannot be retreived.
	  * @see BTCTurkAccountBalance
	  */
	@GET
	@Path("balance/")
		BTCTurkAccountBalance getBalance(
			  @HeaderParam("X-PCK") String apiKey,
			  @HeaderParam("X-Stamp")  SynchronizedValueFactory<Long> stamp,
			  @HeaderParam("X-Signature") ParamsDigest signature)
	      throws IOException;
	
	/** Get the authenticated account's latest transactions. Includes all balance changes. Buys, sells, deposits, withdrawals and fees.
	  * @author mertguner
	  * @param Offset
	  * @param Limit
	  * @param Sort
	  * @exception IOException
	  * @return A list of object type UserTransOutput. Null if user tranasctions cannot be retreived.
	  * @see List<BTCTurkUserTransactions>
	  */
	@GET
	@Path("userTransactions/")
	List<BTCTurkUserTransactions> getUserTransactions(
			@QueryParam("offset") int offset, 
			@QueryParam("limit") int limit,
			@QueryParam("sort") BTCTurkSort sort,
			@HeaderParam("X-PCK") String apiKey,
			@HeaderParam("X-Stamp")  SynchronizedValueFactory<Long> stamp,
			@HeaderParam("X-Signature") ParamsDigest signature)
					  throws IOException;

	/** Get all open orders of the user.
	  * @author mertguner
	  * @param PairSymbol
	  * @exception IOException
	  * @return Users open orders listed. Null if there was an error.
	  * @see List<BTCTurkOpenOrders>
	  */
	@GET
	@Path("openOrders/")
	List<BTCTurkOpenOrders> getOpenOrders(
			@QueryParam("pairSymbol") String pairSymbol,
			  @HeaderParam("X-PCK") String apiKey,
			  @HeaderParam("X-Stamp")  SynchronizedValueFactory<Long> stamp,
			  @HeaderParam("X-Signature") ParamsDigest signature)
					  throws IOException;
	
	/** Submits given Order. Requires authentication. 
	  * @author mertguner
	  * @param BTCTurkOrder to be submitted
	  * @exception IOException
	  * @return True if Order is submitted successfully, false if it was not.
	  * @see BTCTurkExchangeResult
	  */
	@POST
	@Path("exchange/")
	BTCTurkExchangeResult setOrder(
			@FormParam("Price") String Price,
			@FormParam("PricePrecision") String PricePrecision,
			@FormParam("Amount") String Amount,
			@FormParam("AmountPrecision") String AmountPrecision,
			@FormParam("OrderType") int OrderType,
			@FormParam("OrderMethod") int OrderMethod,
			@FormParam("PairSymbol") String PairSymbol,
			@FormParam("DenominatorPrecision") int DenominatorPrecision,
			@FormParam("Total") String Total,
			@FormParam("TotalPrecision") String TotalPrecision,
			@FormParam("TriggerPrice") String TriggerPrice,
			@FormParam("TriggerPricePrecision") String TriggerPricePrecision,
			@HeaderParam("X-PCK") String apiKey,
			@HeaderParam("X-Stamp")  SynchronizedValueFactory<Long> stamp,
			@HeaderParam("X-Signature") ParamsDigest signature)
	      throws IOException;
	
	/** Cancels order with given OrderId 
	  * @author mertguner
	  * @param BTCTurkCancelOrderRequest
	  * @exception IOException
	  * @return True if order was cancelled, false otherwise
	  * @see BTCTurkCancelOrderResult
	  */
	@POST
	@Path("cancelOrder/")
	BTCTurkCancelOrderResult setCancelOrder(
	      @FormParam("id") String id,
		  @HeaderParam("X-PCK") String apiKey,
		  @HeaderParam("X-Stamp")  SynchronizedValueFactory<Long> stamp,
		  @HeaderParam("X-Signature") ParamsDigest signature)
	      throws IOException;
	
	/** Get the deposit money info 
	  * @author mertguner
	  * @deprecated by BtcTurk
	  * @exception IOException
	  * @return The deposit money. Null if there was an error
	  * @see BTCTurkDepositRequestResult
	  */
	@GET
	@Path("DepositMoney/")
	BTCTurkDepositRequestResult getDepositRequest(
			@HeaderParam("X-PCK") String apiKey,
			@HeaderParam("X-Stamp")  SynchronizedValueFactory<Long> stamp,
			@HeaderParam("X-Signature") ParamsDigest signature)
			throws IOException;
	
	/** Send the deposit money request, and return the deposit money request info. 
	  * @author mertguner
	  * @deprecated by BtcTurk
	  * @param BTCTurkDepositRequest
	  * @exception IOException
	  * @return If a request is already, return the deposit money info. Null if there was an error
	  * @see BTCTurkDepositRequestResult
	  */
	@POST
	@Path("DepositMoney/")
	BTCTurkDepositRequestResult setDepositRequest(
			@FormParam("amount") String amount,
			@FormParam("amount_precision") String amountPrecision,
			@HeaderParam("X-PCK") String apiKey,
			@HeaderParam("X-Stamp")  SynchronizedValueFactory<Long> stamp,
			@HeaderParam("X-Signature") ParamsDigest signature)
			throws IOException;
	
	/** Cancel money requests Deposit with given RequestId. 
	  * @author mertguner
	  * @deprecated by BtcTurk
	  * @param balanceRequestId
	  * @exception IOException
	  * @return True if request was cancelled, false otherwise
	  * @see Boolean
	  */
	@DELETE
	@Path("DepositMoney/CancelOperation/")
	Boolean cancelDepositRequest(
			@QueryParam("balanceRequestId") String balanceRequestId,
			@HeaderParam("X-PCK") String apiKey,
			@HeaderParam("X-Stamp")  SynchronizedValueFactory<Long> stamp,
			@HeaderParam("X-Signature") ParamsDigest signature)
			throws IOException;
	
	/** Get the withdrawal money info 
	  * @author mertguner
	  * @deprecated by BtcTurk
	  * @exception IOException
	  * @return The withdrawal money. Null if there was an error
	  * @see BTCTurkWithdrawalRequestInfo
	  */
	@GET
	@Path("WithdrawalMoney/")
	BTCTurkWithdrawalRequestInfo getWithdrawalRequest(
			@HeaderParam("X-PCK") String apiKey,
			@HeaderParam("X-Stamp")  SynchronizedValueFactory<Long> stamp,
			@HeaderParam("X-Signature") ParamsDigest signature)
			throws IOException;
	
	/** Send the withdrawal money request, and return the withdrawal money request info. 
	  * @author mertguner
	  * @deprecated by BtcTurk
	  * @param BTCTurkWithdrawalRequest
	  * @exception IOException
	  * @return If a request is already, return the withdrawal money info. Null if there was an error
	  * @see BTCTurkWithdrawalRequestInfo
	  */
	@POST
	@Path("WithdrawalMoney/")
	BTCTurkWithdrawalRequestInfo setWithdrawalRequest(
			@FormParam("iban") String Iban,
			@FormParam("friendly_name") String FriendlyName,
			@FormParam("friendly_name_save") Boolean FriendlyNameSave,
			@FormParam("amount") String Amount,
			@FormParam("amount_precision") String AmountPrecision,
			@FormParam("has_balance_request") Boolean HasBalanceRequest,
			@FormParam("balance_request_id") String BalanceRequestId,
			@FormParam("bank_id") String BankId,
			@FormParam("bank_name") String BankName,
			@FormParam("first_name") String FirstName,
			@FormParam("last_name") String LastName,
			@HeaderParam("X-PCK") String apiKey,
			@HeaderParam("X-Stamp")  SynchronizedValueFactory<Long> stamp,
			@HeaderParam("X-Signature") ParamsDigest signature)
			throws IOException;
	
	/** Cancel money requests Withdrawal with given RequestId
	  * @author mertguner
	  * @deprecated by BtcTurk
	  * @param balanceRequestId
	  * @exception IOException
	  * @return True if request was cancelled, false otherwise
	  * @see Boolean
	  */
	@DELETE
	@Path("WithdrawalMoney/CancelOperation/")
	Boolean cancelWithdrawalRequest(
			@QueryParam("balanceRequestId") String balanceRequestId,
			@HeaderParam("X-PCK") String apiKey,
			@HeaderParam("X-Stamp")  SynchronizedValueFactory<Long> stamp,
			@HeaderParam("X-Signature") ParamsDigest signature)
			throws IOException;
	
}
