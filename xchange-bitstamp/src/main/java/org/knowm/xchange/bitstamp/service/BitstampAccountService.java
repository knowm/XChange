package org.knowm.xchange.bitstamp.service;

import java.io.IOException;
import java.math.BigDecimal;
import java.util.List;

import org.knowm.xchange.Exchange;
import org.knowm.xchange.bitstamp.BitstampAdapters;
import org.knowm.xchange.bitstamp.dto.account.BitstampDepositAddress;
import org.knowm.xchange.bitstamp.dto.account.BitstampWithdrawal;
import org.knowm.xchange.currency.Currency;
import org.knowm.xchange.dto.account.AccountInfo;
import org.knowm.xchange.dto.account.FundingRecord;
import org.knowm.xchange.exceptions.ExchangeException;
import org.knowm.xchange.exceptions.NotAvailableFromExchangeException;
import org.knowm.xchange.exceptions.NotYetImplementedForExchangeException;
import org.knowm.xchange.service.account.AccountService;
import org.knowm.xchange.service.trade.params.DefaultWithdrawFundsParams;
import org.knowm.xchange.service.trade.params.RippleWithdrawFundsParams;
import org.knowm.xchange.service.trade.params.TradeHistoryParams;
import org.knowm.xchange.service.trade.params.WithdrawFundsParams;

/**
 * @author Matija Mazi
 */
public class BitstampAccountService extends BitstampAccountServiceRaw implements AccountService {

  /**
   * Constructor
   *
   * @param exchange
   */
  public BitstampAccountService(Exchange exchange) {

    super(exchange);
  }

  @Override
  public AccountInfo getAccountInfo() throws IOException {

    return BitstampAdapters.adaptAccountInfo(getBitstampBalance(), exchange.getExchangeSpecification().getUserName());
  }

  @Override
  public String withdrawFunds(Currency currency, BigDecimal amount, String address) throws IOException {
    return withdrawFunds(new DefaultWithdrawFundsParams(address, currency, amount));
  }

  @Override
  public String withdrawFunds(WithdrawFundsParams params) throws ExchangeException, NotAvailableFromExchangeException, NotYetImplementedForExchangeException, IOException {
    if (params instanceof RippleWithdrawFundsParams) {
      RippleWithdrawFundsParams rippleWithdrawFundsParams = (RippleWithdrawFundsParams) params;

      BitstampWithdrawal response = withdrawRippleFunds(rippleWithdrawFundsParams.amount, rippleWithdrawFundsParams.address, rippleWithdrawFundsParams.tag);

      if (response.error != null) {
        throw new ExchangeException("Failed to withdraw: " + response.error);
      }

      if (response.getId() == null) {
        return null;
      }

      return Integer.toString(response.getId());
    } else if (params instanceof DefaultWithdrawFundsParams) {
      DefaultWithdrawFundsParams defaultParams = (DefaultWithdrawFundsParams) params;

      BitstampWithdrawal response;
      if (defaultParams.currency.equals(Currency.LTC)) {
        response = withdrawLtcFunds(defaultParams.amount, defaultParams.address);
      } else if (defaultParams.currency.equals(Currency.ETH)) {
        response = withdrawEthFunds(defaultParams.amount, defaultParams.address);
      } else if (defaultParams.currency.equals(Currency.BTC)) {
        response = withdrawBtcFunds(defaultParams.amount, defaultParams.address);
      } else {
        throw new IllegalStateException("Cannot withdraw " + defaultParams.currency);
      }

      if (response.error != null) {
        throw new ExchangeException("Failed to withdraw: " + response.error);
      }

      if (response.getId() == null) {
        return null;
      }

      return Integer.toString(response.getId());
    }

    throw new IllegalStateException("Don't know how to withdraw: " + params);
  }

  /**
   * This returns the currently set deposit address. It will not generate a new address (ie. repeated calls will return the same address).
   */
  @Override
  public String requestDepositAddress(Currency currency, String... arguments) throws IOException {

    final BitstampDepositAddress response = getBitstampBitcoinDepositAddress();
    return response.getDepositAddress();

  }

  @Override
  public TradeHistoryParams createFundingHistoryParams() {
    throw new NotAvailableFromExchangeException();
  }

  @Override
  public List<FundingRecord> getFundingHistory(TradeHistoryParams params) throws ExchangeException, NotAvailableFromExchangeException, NotYetImplementedForExchangeException, IOException {
    throw new NotYetImplementedForExchangeException();
  }
}
