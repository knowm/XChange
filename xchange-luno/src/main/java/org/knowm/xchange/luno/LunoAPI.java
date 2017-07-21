package org.knowm.xchange.luno;

import java.io.IOException;
import java.math.BigDecimal;

import org.knowm.xchange.luno.dto.LunoBoolean;
import org.knowm.xchange.luno.dto.LunoException;
import org.knowm.xchange.luno.dto.account.LunoAccount;
import org.knowm.xchange.luno.dto.account.LunoAccountTransactions;
import org.knowm.xchange.luno.dto.account.LunoBalance;
import org.knowm.xchange.luno.dto.account.LunoFundingAddress;
import org.knowm.xchange.luno.dto.account.LunoPendingTransactions;
import org.knowm.xchange.luno.dto.account.LunoQuote;
import org.knowm.xchange.luno.dto.account.LunoWithdrawals;
import org.knowm.xchange.luno.dto.account.LunoWithdrawals.Withdrawal;
import org.knowm.xchange.luno.dto.marketdata.LunoOrderBook;
import org.knowm.xchange.luno.dto.marketdata.LunoTicker;
import org.knowm.xchange.luno.dto.marketdata.LunoTickers;
import org.knowm.xchange.luno.dto.marketdata.LunoTrades;
import org.knowm.xchange.luno.dto.trade.LunoFeeInfo;
import org.knowm.xchange.luno.dto.trade.LunoOrders;
import org.knowm.xchange.luno.dto.trade.LunoPostOrder;
import org.knowm.xchange.luno.dto.trade.LunoUserTrades;
import org.knowm.xchange.luno.dto.trade.OrderType;
import org.knowm.xchange.luno.dto.trade.State;
import org.knowm.xchange.luno.dto.trade.LunoOrders.Order;

public interface LunoAPI {

    /**
     * Market data API calls can be accessed by anyone without authentication.
     * @param pair required - Currency pair e.g. XBTZAR
     * @return
     * @throws IOException
     * @throws LunoException
     */
    LunoTicker ticker(String pair) throws IOException, LunoException;

    /**
     * Returns the latest ticker indicators from all active Luno exchanges.
     * @return
     * @throws IOException
     * @throws LunoException
     */
    LunoTickers tickers() throws IOException, LunoException;

    /**
     * Returns a list of bids and asks in the order book. Ask orders are sorted by price ascending. Bid orders are sorted by 
     * price descending. Note that multiple orders at the same price are not necessarily conflated.
     * @param pair required - Currency pair e.g. XBTZAR
     * @return
     * @throws IOException
     * @throws LunoException
     */
    LunoOrderBook orderbook(String pair) throws IOException, LunoException;

    /**
     * Returns a list of the most recent trades. At most 100 results are returned per call.
     * @param pair required - Currency pair e.g. XBTZAR
     * @param since optional - Fetch trades executed after this time, specified as a Unix timestamp in milliseconds.
     * @return
     * @throws IOException
     * @throws LunoException
     */
    LunoTrades trades(String pair, Long since) throws IOException, LunoException;

    /**
     * Create an additional account for the specified currency.
     * You must be verified to trade currency in order to be able to create an account. A user has a limit of 4 accounts per currency.
     * @param currency required - The currency code for the account you want to create e.g. XBT, IDR, MYR, ZAR
     * @param name required - The label to use for this account e.g. "Trading ACC". 
     * @return
     * @throws IOException
     * @throws LunoException
     */
    LunoAccount createAccount(String currency, String name) throws IOException, LunoException;

    /**
     * Return the list of all accounts and their respective balances.
     * @return
     * @throws IOException
     * @throws LunoException
     */
    LunoBalance balance() throws IOException, LunoException;

    /**
     * Return a list of transaction entries from an account.<br>
     * Transaction entry rows are numbered sequentially starting from 1, where 1 is the oldest entry. The range of rows to return
     * are specified with the min_row (inclusive) and max_row (exclusive) parameters. At most 1000 rows can be requested per call.<br>
     * If min_row or max_row is non-positive, the range wraps around the most recent row. For example, to fetch the 100 most recent
     * rows, use min_row=-100 and max_row=0.
     * @param id required - Account ID
     * @param minRow required - Minimum of the row range to return (inclusive)
     * @param maxRow required - Maximum of the row range to return (exclusive)
     * @return
     * @throws IOException
     * @throws LunoException
     */
    LunoAccountTransactions transactions(String id, int minRow, int maxRow) throws IOException, LunoException;

    /**
     * Return a list of all pending transactions related to the account.<br>
     * Unlike account entries, pending transactions are not numbered, and may be reordered, deleted or updated at any time.
     * @param id
     * @return
     * @throws IOException
     * @throws LunoException
     */
    LunoPendingTransactions pendingTransactions(String id) throws IOException, LunoException;

    /**
     * Returns a list of the most recently placed orders. You can specify an optional state=PENDING parameter to restrict the
     * results to only open orders. You can also specify the market by using the optional pair parameter. The list is truncated
     * after 100 items.
     * @param state optional - Filter to only orders of this state e.g. PENDING
     * @param pair optional - Filter to only orders of this currency pair e.g. XBTZAR
     * @return
     * @throws IOException
     * @throws LunoException
     */
    LunoOrders listOrders(State state, String pair) throws IOException, LunoException;

    /**
     * Create a new trade order.<br>
     * Warning! Orders cannot be reversed once they have executed. Please ensure your program has been thoroughly tested before
     * submitting orders.<br>
     * If no base_account_id or counter_account_id are specified, your default base currency or counter currency account will be
     * used. You can find your account IDs by calling the Balances API https://www.luno.com/en/api#accounts-balances.
     * @param pair required - The currency pair to trade e.g. XBTZAR
     * @param type required - "BID" for a bid (buy) limit order or "ASK" for an ask (sell) limit order.
     * @param volume required - Amount of Bitcoin to buy or sell as a decimal string in units of BTC e.g. "1.423".
     * @param price required - Limit price as a decimal string in units of ZAR/BTC e.g. "1200".
     * @param baseAccountId optional - The base currency account to use in the trade.
     * @param counterAccountId optional - The counter currency account to use in the trade.
     * @return
     * @throws IOException
     * @throws LunoException
     */
    LunoPostOrder postLimitOrder(String pair, OrderType type, BigDecimal volume, BigDecimal price, String baseAccountId,
            String counterAccountId) throws IOException, LunoException;

    /**
     * Create a new market order.<br>
     *  Warning! Orders cannot be reversed once they have executed. Please ensure your program has been thoroughly tested before
     *  submitting orders.<br>
     *  If no base_account_id or counter_account_id are specified, your default base currency or counter currency account will be
     *  used. You can find your account IDs by calling the Balances API https://www.luno.com/en/api#accounts-balances.<br>
     *  A market order executes immediately, and either buys as much bitcoin that can be bought for a set amount of fiat currency,
     *  or sells a set amount of bitcoin for as much fiat as possible.
     * @param pair required - The currency pair to trade e.g. XBTZAR
     * @param type required - "BUY" to buy bitcoin, or "SELL" to sell bitcoin.
     * @param counterVolume required, if type is "BUY"  - For a "BUY" order: amount of local currency (e.g. ZAR, MYR) to spend as a decimal string in units of the local currency e.g. "100.50".
     * @param baseVolume required, if type is "SELL" - For a "SELL" order: amount of Bitcoin to sell as a decimal string in units of BTC e.g. "1.423".
     * @param baseAccountId optional - The base currency account to use in the trade.
     * @param counterAccountId optional - The counter currency account to use in the trade.
     * @return
     * @throws IOException
     * @throws LunoException
     */
    LunoPostOrder postMarketOrder(String pair, OrderType type, BigDecimal counterVolume, BigDecimal baseVolume,
            String baseAccountId, String counterAccountId) throws IOException, LunoException;

    /**
     * Request to stop an order.
     * @param orderId required - The order reference as a string e.g. BXMC2CJ7HNB88U4
     * @return
     * @throws IOException
     * @throws LunoException
     */
    LunoBoolean stopOrder(String orderId) throws IOException, LunoException;

    /**
     * Get an order by its id.
     * @param orderId required - The order ID
     * @return
     * @throws IOException
     * @throws LunoException
     */
    Order getOrder(String orderId) throws IOException, LunoException;

    /**
     * Returns a list of your recent trades for a given pair, sorted by oldest first.<br>
     *  type in the response indicates the type of order that you placed in order to participate in the trade.<br>
     *  If is_buy in the response is true, then the order which completed the trade (market taker) was a bid order.
     * @param pair required - Filter to trades of this currency pair e.g. XBTZAR
     * @param since optional - Filter to trades on or after this timestamp, e.g. 1470810728478
     * @param limit optional - Limit to this number of trades (min 1, max 100, default 100)
     * @return
     * @throws IOException
     * @throws LunoException
     */
    LunoUserTrades listTrades(String pair, Long since, Integer limit) throws IOException, LunoException;

    /**
     * Returns your fees and 30 day trading volume (as of midnight) for a given pair.
     * @param pair required - Filter to trades of this currency pair e.g. XBTZAR
     * @return
     * @throws IOException
     * @throws LunoException
     */
    LunoFeeInfo feeInfo(String pair) throws IOException, LunoException;

    /**
     * Returns the default receive address associated with your account and the amount received via the address. You can specify an
     * optional address parameter to return information for a non-default receive address. In the response, total_received is the
     * total confirmed Bitcoin amount received excluding unconfirmed transactions. total_unconfirmed is the total sum of unconfirmed
     * receive transactions.
     * @param asset required - Currency code of the asset e.g. XBT
     * @param address optional - Specific Bitcoin address to retrieve. If not provided, the default address will be used.
     * @return
     * @throws IOException
     * @throws LunoException
     */
    LunoFundingAddress getFundingAddress(String asset, String address) throws IOException, LunoException;

    /**
     * Allocates a new receive address to your account. There is a rate limit of 1 address per hour, but bursts of up to 10
     * addresses are allowed.
     * @return
     * @throws IOException
     * @throws LunoException
     */
    LunoFundingAddress createFundingAddress(String asset) throws IOException, LunoException;

    /**
     * Returns a list of withdrawal requests.
     * @return
     * @throws IOException
     * @throws LunoException
     */
    LunoWithdrawals withdrawals() throws IOException, LunoException;

    /**
     * Creates a new withdrawal request.
     * @param type required - Withdrawal types e.g. ZAR_EFT, NAD_EFT, KES_MPESA, MYR_IBG, IDR_LLG
     * @param amount required - Amount to withdraw. The currency depends on the type.
     * @param beneficiaryId optional - The beneficiary ID of the bank account the withdrawal will be paid out to. This parameter is
     * required if you have multiple bank accounts. Your bank account beneficiary ID can be found by clicking on the beneficiary
     * name on the Beneficiaries page [https://www.luno.com/wallet/beneficiaries].
     * @return
     * @throws IOException
     * @throws LunoException
     */
    Withdrawal requestWithdrawal(String type, BigDecimal amount, String beneficiaryId) throws IOException, LunoException;

    /**
     * Returns the status of a particular withdrawal request.
     * @param withdrawalId required - Withdrawal ID to retrieve.
     * @return
     * @throws IOException
     * @throws LunoException
     */
    Withdrawal getWithdrawal(String withdrawalId) throws IOException, LunoException;

    /**
     * Cancel a withdrawal request. This can only be done if the request is still in state PENDING.
     * @param withdrawalId required - ID of the withdrawal to cancel.

     * @return
     * @throws IOException
     * @throws LunoException
     */
    Withdrawal cancelWithdrawal(String withdrawalId) throws IOException, LunoException;

    /**
     * Send Bitcoin from your account to a Bitcoin address or email address.<br>
     * If the email address is not associated with an existing Luno account, an invitation to create an account and claim the funds
     * will be sent.<br>
     * Warning! Bitcoin transactions are irreversible. Please ensure your program has been thoroughly tested before using this call.
     * @param amount required - Amount to send as a decimal string.
     * @param currency required - Currency to send e.g. XBT
     * @param address required - Destination Bitcoin address or email address to send to.
     * @param description optional - Description for the transaction to record on the account statement.
     * @param message optional - Message to send to the recipient. This is only relevant when sending to an email address.
     * @return
     * @throws IOException
     * @throws LunoException
     */
    LunoBoolean send(BigDecimal amount, String currency, String address, String description, String message) throws IOException,
            LunoException;

    /**
     * Creates a new quote to buy or sell a particular amount.<br>
     * You can specify either the exact amount that you want to pay or the exact amount that you want too receive.<br>
     * For example, to buy exactly 0.1 Bitcoin using ZAR, you would create a quote to BUY 0.1 XBTZAR. The returned quote includes
     * the appropriate ZAR amount. To buy Bitcoin using exactly ZAR 100, you would create a quote to SELL 100 ZARXBT. The returned
     * quote specifies the Bitcoin as the counter amount that will be returned.<br>
     * An error is returned if your account is not verified for the currency pair, or if your account would have insufficient
     * balance to ever exercise the quote. 
     * @param type required - Possible types: BUY, SELL
     * @param baseAmount required - Amount to buy or sell in the pair base currency.
     * @param pair required - Currency pair to trade e.g. XBTZAR, XBTMYR. The pair can also be flipped if you want to buy or sell
     * the counter currency (e.g. ZARXBT).
     * @return
     * @throws IOException
     * @throws LunoException
     */
    LunoQuote createQuote(OrderType type, BigDecimal baseAmount, String pair) throws IOException, LunoException;

    /**
     * Get the latest status of a quote.
     * @param quoteId required - ID of the quote to retrieve.
     * @return
     * @throws IOException
     * @throws LunoException
     */
    LunoQuote getQuote(String quoteId) throws IOException, LunoException;

    /**
     * Exercise a quote to perform the trade. If there is sufficient balance available in your account, it will be debited and the
     * counter amount credited.<br>
     * An error is returned if the quote has expired or if you have insufficient available balance.
     * @param quoteId required - ID of the quote to exercise.
     * @return
     * @throws IOException
     * @throws LunoException
     */
    LunoQuote exerciseQuote(String quoteId) throws IOException, LunoException;

    /**
     * Discard a quote. Once a quote has been discarded, it cannot be exercised even if it has not expired yet.
     * @param quoteId required - ID of the quote to discard.
     * @return
     * @throws IOException
     * @throws LunoException
     */
    LunoQuote discardQuote(String quoteId) throws IOException, LunoException;

}