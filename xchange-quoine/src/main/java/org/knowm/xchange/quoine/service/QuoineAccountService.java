package org.knowm.xchange.quoine.service;

import java.io.IOException;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

import org.knowm.xchange.BaseExchange;
import org.knowm.xchange.currency.Currency;
import org.knowm.xchange.dto.account.AccountInfo;
import org.knowm.xchange.dto.account.FundingRecord;
import org.knowm.xchange.dto.account.Wallet;
import org.knowm.xchange.exceptions.ExchangeException;
import org.knowm.xchange.exceptions.NotAvailableFromExchangeException;
import org.knowm.xchange.exceptions.NotYetImplementedForExchangeException;
import org.knowm.xchange.quoine.QuoineAdapters;
import org.knowm.xchange.quoine.dto.account.BitcoinAccount;
import org.knowm.xchange.quoine.dto.account.FiatAccount;
import org.knowm.xchange.service.account.AccountService;
import org.knowm.xchange.service.trade.params.TradeHistoryParams;
import org.knowm.xchange.service.trade.params.WithdrawFundsParams;

/**
 * <p>
 * XChange service to provide the following to {@link org.knowm.xchange.Exchange}:
 * </p>
 * <ul>
 * <li>ANX specific methods to handle account-related operations</li>
 * </ul>
 */
public class QuoineAccountService extends QuoineAccountServiceRaw implements AccountService {

  private final boolean useMargin;

  /**
   * Constructor
   */
  public QuoineAccountService(BaseExchange baseExchange, boolean useMargin) {

    super(baseExchange);

    this.useMargin = useMargin;
  }

  @Override
  public AccountInfo getAccountInfo() throws IOException {
    //need to make 2 calls

    FiatAccount[] quoineFiatAccountInfo = getQuoineFiatAccountInfo();
    List<Wallet> fiatBalances = QuoineAdapters.adapt(quoineFiatAccountInfo);

    BitcoinAccount[] cyptoBalances = getQuoineCryptoAccountInfo();
    List<Wallet> cryptoWallets = QuoineAdapters.adapt(cyptoBalances);

    List<Wallet> all = new ArrayList<>();
    all.addAll(fiatBalances);
    all.addAll(cryptoWallets);

    return new AccountInfo(all);
  }

  @Override
  public String withdrawFunds(Currency currency, BigDecimal amount, String address) throws IOException {

    throw new NotAvailableFromExchangeException();
  }

  @Override
  public String withdrawFunds(WithdrawFundsParams params) throws ExchangeException, NotAvailableFromExchangeException, NotYetImplementedForExchangeException, IOException {
    throw new NotYetImplementedForExchangeException();
  }

  @Override
  public String requestDepositAddress(Currency currency, String... args) throws IOException {
    BitcoinAccount[] quoineCryptoAccountInfo = getQuoineCryptoAccountInfo();
    for (BitcoinAccount bitcoinAccount : quoineCryptoAccountInfo) {
      Currency ccy = Currency.getInstance(bitcoinAccount.getCurrency());
      if (ccy.equals(currency))
        return bitcoinAccount.getAddress();
    }
    return null;
  }

  @Override
  public TradeHistoryParams createFundingHistoryParams() {
    throw new NotAvailableFromExchangeException();
  }

  @Override
  public List<FundingRecord> getFundingHistory(TradeHistoryParams params) throws ExchangeException, NotAvailableFromExchangeException, NotYetImplementedForExchangeException, IOException {
    throw new NotAvailableFromExchangeException();
  }
}
