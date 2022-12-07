package org.knowm.xchange.cexio.service;

import java.io.IOException;
import java.math.BigDecimal;
import java.util.Map;
import org.knowm.xchange.Exchange;
import org.knowm.xchange.cexio.CexIOAdapters;
import org.knowm.xchange.cexio.dto.account.CexIOBalanceInfo;
import org.knowm.xchange.cexio.dto.account.CexIOCryptoAddress;
import org.knowm.xchange.cexio.dto.account.CexIOFeeInfo.FeeDetails;
import org.knowm.xchange.currency.Currency;
import org.knowm.xchange.currency.CurrencyPair;
import org.knowm.xchange.dto.account.AccountInfo;
import org.knowm.xchange.dto.account.Fee;
import org.knowm.xchange.exceptions.NotAvailableFromExchangeException;
import org.knowm.xchange.instrument.Instrument;
import org.knowm.xchange.service.account.AccountService;
import org.knowm.xchange.service.trade.params.TradeHistoryParams;
import org.knowm.xchange.service.trade.params.WithdrawFundsParams;

/** Author: brox Since: 2/6/14 */
public class CexIOAccountService extends CexIOAccountServiceRaw implements AccountService {

  /**
   * Constructor
   *
   * @param exchange
   */
  public CexIOAccountService(Exchange exchange) {

    super(exchange);
  }

  @Override
  public AccountInfo getAccountInfo() throws IOException {
    CexIOBalanceInfo cexIOAccountInfo = getCexIOAccountInfo();
    return new AccountInfo(
        exchange.getExchangeSpecification().getUserName(),
        CexIOAdapters.adaptWallet(cexIOAccountInfo));
  }

  @Override
  public String requestDepositAddress(Currency currency, String... arguments) throws IOException {
    CexIOCryptoAddress cryptoAddress = getCexIOCryptoAddress(currency.getCurrencyCode());
    return cryptoAddress.getData();
  }

  @Override
  public Map<Instrument, Fee> getDynamicTradingFeesByInstrument() throws IOException {
    Map<CurrencyPair, FeeDetails> dynamicTradingFees = getMyFee();
    return CexIOAdapters.adaptDynamicTradingFees(dynamicTradingFees);
  }

  @Override
  public String withdrawFunds(WithdrawFundsParams params) {
    throw new NotAvailableFromExchangeException();
  }

  @Override
  public String withdrawFunds(Currency currency, BigDecimal amount, String address) {

    throw new NotAvailableFromExchangeException();
  }

  @Override
  public TradeHistoryParams createFundingHistoryParams() {
    throw new NotAvailableFromExchangeException();
  }
}
