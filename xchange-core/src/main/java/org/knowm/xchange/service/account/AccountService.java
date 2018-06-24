package org.knowm.xchange.service.account;

import java.io.IOException;
import java.math.BigDecimal;
import java.util.List;
import java.util.Map;
import org.knowm.xchange.Exchange;
import org.knowm.xchange.currency.Currency;
import org.knowm.xchange.currency.CurrencyPair;
import org.knowm.xchange.dto.account.AccountInfo;
import org.knowm.xchange.dto.account.Fee;
import org.knowm.xchange.dto.account.FundingRecord;
import org.knowm.xchange.exceptions.ExchangeException;
import org.knowm.xchange.exceptions.NotAvailableFromExchangeException;
import org.knowm.xchange.exceptions.NotYetImplementedForExchangeException;
import org.knowm.xchange.service.BaseService;
import org.knowm.xchange.service.trade.params.DefaultWithdrawFundsParams;
import org.knowm.xchange.service.trade.params.TradeHistoryParams;
import org.knowm.xchange.service.trade.params.WithdrawFundsParams;

/**
 * Interface to provide the following to {@link Exchange}:
 *
 * <ul>
 *   <li>Standard methods available to explore send/receive account-related data
 * </ul>
 *
 * <p>The implementation of this service is expected to be based on a client polling mechanism of
 * some kind
 */
public interface AccountService extends BaseService {

  /**
   * Get account info
   *
   * @return the AccountInfo object, null if some sort of error occurred. Implementers should log
   *     the error.
   * @throws ExchangeException - Indication that the exchange reported some kind of error with the
   *     request or response
   * @throws NotAvailableFromExchangeException - Indication that the exchange does not support the
   *     requested function or data
   * @throws NotYetImplementedForExchangeException - Indication that the exchange supports the
   *     requested function or data, but it has not yet been implemented
   * @throws IOException - Indication that a networking error occurred while fetching JSON data
   */
  default AccountInfo getAccountInfo() throws IOException {
    throw new NotYetImplementedForExchangeException();
  }

  /**
   * Convenience method, typically just delegates to withdrawFunds(WithdrawFundsParams params)
   *
   * @param currency The currency to withdraw
   * @param amount The amount to withdraw
   * @param address The destination address
   * @return The result of the withdrawal (usually a transaction ID)
   * @throws ExchangeException - Indication that the exchange reported some kind of error with the
   *     request or response
   * @throws NotAvailableFromExchangeException - Indication that the exchange does not support the
   *     requested function or data
   * @throws NotYetImplementedForExchangeException - Indication that the exchange supports the
   *     requested function or data, but it has not yet been implemented
   * @throws IOException - Indication that a networking error occurred while fetching JSON data
   */
  default String withdrawFunds(Currency currency, BigDecimal amount, String address)
      throws IOException {
    return withdrawFunds(new DefaultWithdrawFundsParams(address, currency, amount));
  }

  /**
   * Withdraw funds from this account. Allows to withdraw digital currency funds from the exchange
   * account to an external address
   *
   * @param params The withdrawl details
   * @return The result of the withdrawal (usually a transaction ID)
   * @throws ExchangeException - Indication that the exchange reported some kind of error with the
   *     request or response
   * @throws NotAvailableFromExchangeException - Indication that the exchange does not support the
   *     requested function or data
   * @throws NotYetImplementedForExchangeException - Indication that the exchange supports the
   *     requested function or data, but it has not yet been implemented
   * @throws IOException - Indication that a networking error occurred while fetching JSON data
   */
  default String withdrawFunds(WithdrawFundsParams params) throws IOException {
    throw new NotYetImplementedForExchangeException();
  }

  /**
   * Request a digital currency address to fund this account. Allows to fund the exchange account
   * with digital currency from an external address
   *
   * @param currency The digital currency that corresponds to the desired deposit address.
   * @param args Necessary argument(s) as a {@code String}
   * @return the internal deposit address to send funds to
   * @throws ExchangeException - Indication that the exchange reported some kind of error with the
   *     request or response
   * @throws NotAvailableFromExchangeException - Indication that the exchange does not support the
   *     requested function or data
   * @throws NotYetImplementedForExchangeException - Indication that the exchange supports the
   *     requested function or data, but it has not yet been implemented
   * @throws IOException - Indication that a networking error occurred while fetching JSON data
   */
  default String requestDepositAddress(Currency currency, String... args) throws IOException {
    throw new NotYetImplementedForExchangeException();
  }

  /**
   * Create {@link TradeHistoryParams} object specific to this exchange. Object created by this
   * method may be used to discover supported and required {@link
   * #getFundingHistory(TradeHistoryParams)} parameters and should be passed only to the method in
   * the same class as the createFundingHistoryParams that created the object.
   */
  default TradeHistoryParams createFundingHistoryParams() {
    throw new NotYetImplementedForExchangeException();
  }

  /**
   * @return list of funding history if available or an empty list otherwise. This should never
   *     return null.
   * @throws ExchangeException - Indication that the exchange reported some kind of error with the
   *     request or response
   * @throws NotAvailableFromExchangeException - Indication that the exchange does not support the
   *     requested function or data
   * @throws NotYetImplementedForExchangeException - Indication that the exchange supports the
   *     requested function or data, but it has not yet been implemented
   * @throws IOException - Indication that a networking error occurred while fetching JSON data
   */
  default List<FundingRecord> getFundingHistory(TradeHistoryParams params) throws IOException {
    throw new NotYetImplementedForExchangeException();
  }
  /**
   * Get the trading fees per currency pair as determined by the given exchange's rules for
   * adjusting fees by recent volume traded. Some exchanges will provide the current fees per
   * currency via a single API request, while others require more logic to compute by hand.
   *
   * @return map between currency pairs and their fees at the time of invocation.
   * @throws ExchangeException - Indication that the exchange reported some kind of error with the
   *     request or response
   * @throws NotAvailableFromExchangeException - Indication that the exchange does not support the
   *     requested function or data
   * @throws NotYetImplementedForExchangeException - Indication that the exchange supports the
   *     requested function or data, but it has not yet been implemented
   * @throws IOException - Indication that a networking error occurred while fetching JSON data
   */
  public default Map<CurrencyPair, Fee> getDynamicTradingFees() throws IOException {
    throw new NotYetImplementedForExchangeException();
  }
}
