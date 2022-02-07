package org.knowm.xchange.coinbase.v2.service;

import java.io.IOException;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import org.knowm.xchange.Exchange;
import org.knowm.xchange.coinbase.CoinbaseAdapters;
import org.knowm.xchange.coinbase.v2.Coinbase;
import org.knowm.xchange.coinbase.v2.dto.CoinbaseAmount;
import org.knowm.xchange.coinbase.v2.dto.account.CoinbaseAccountData;
import org.knowm.xchange.coinbase.v2.dto.account.transactions.CoinbaseBuySellResponse;
import org.knowm.xchange.currency.Currency;
import org.knowm.xchange.dto.Order;
import org.knowm.xchange.dto.account.AccountInfo;
import org.knowm.xchange.dto.account.Balance;
import org.knowm.xchange.dto.account.FundingRecord;
import org.knowm.xchange.dto.account.Wallet;
import org.knowm.xchange.dto.trade.UserTrades;
import org.knowm.xchange.exceptions.ExchangeException;
import org.knowm.xchange.exceptions.NotAvailableFromExchangeException;
import org.knowm.xchange.exceptions.NotYetImplementedForExchangeException;
import org.knowm.xchange.service.account.AccountService;
import org.knowm.xchange.service.trade.params.DefaultTradeHistoryParamPaging;
import org.knowm.xchange.service.trade.params.DefaultWithdrawFundsParams;
import org.knowm.xchange.service.trade.params.TradeHistoryParams;
import org.knowm.xchange.service.trade.params.WithdrawFundsParams;

public final class CoinbaseAccountService extends CoinbaseAccountServiceRaw
    implements AccountService {

  public CoinbaseAccountService(Exchange exchange) {

    super(exchange);
  }

  @Override
  public AccountInfo getAccountInfo() throws IOException {
    List<Wallet> wallets = new ArrayList<>();

    List<CoinbaseAccountData.CoinbaseAccount> coinbaseAccounts = getCoinbaseAccounts();
    for (CoinbaseAccountData.CoinbaseAccount coinbaseAccount : coinbaseAccounts) {
      CoinbaseAmount balance = coinbaseAccount.getBalance();
      Wallet wallet =
          Wallet.Builder.from(
                  Arrays.asList(
                      new Balance(
                          Currency.getInstance(balance.getCurrency()), balance.getAmount())))
              .id(coinbaseAccount.getId())
              .build();
      wallets.add(wallet);
    }

    return new AccountInfo(wallets);
  }

  @Override
  public String withdrawFunds(WithdrawFundsParams params)
      throws ExchangeException, NotAvailableFromExchangeException,
          NotYetImplementedForExchangeException, IOException {
    if (params instanceof DefaultWithdrawFundsParams) {
      DefaultWithdrawFundsParams defaultParams = (DefaultWithdrawFundsParams) params;
      return withdrawFunds(
          defaultParams.getCurrency(), defaultParams.getAmount(), defaultParams.getAddress());
    }
    throw new IllegalStateException("Don't know how to withdraw: " + params);
  }

  @Override
  public TradeHistoryParams createFundingHistoryParams() {
    return new CoinbaseTradeHistoryParams();
  }


  /**
   * The Coinbase is not typical exchange. It has splitted buys and sells into wallets (accounts).
   * To get it is necessary to know the accountId (wallet ID) see {@link AccountInfo#getWallets()}
   */
//  public List<FundingRecord>  getWithdrwalHistory(CoinbaseTradeHistoryParams params, String accountId)
//          throws IOException {
//    final String apiKey = exchange.getExchangeSpecification().getApiKey();
//    final BigDecimal timestamp = coinbase.getTime(Coinbase.CB_VERSION_VALUE).getData().getEpoch();
//    final CoinbaseBuySellResponse withdrawals =
//      (CoinbaseBuySellResponse) coinbase.getAllWithdrawals(
//              Coinbase.CB_VERSION_VALUE,
//              apiKey,
//              signatureCreator2,
//              timestamp,
//              accountId,
//              params.getLimit(),
//              params.getStartId()
//      );
//    return CoinbaseAdapters.adaptTrades(withdrawals.getData());
//  }

  /**
   * The Coinbase is not typical exchange. It has splitted buys and sells into wallets (accounts).
   * To get it is necessary to know the accountId (wallet ID) from {@link AccountInfo#getWallets()}
   */
  public List<FundingRecord> getDepositHistory(CoinbaseTradeHistoryParams params, String accountId)
          throws IOException {
    final String apiKey = exchange.getExchangeSpecification().getApiKey();
    final BigDecimal timestamp = coinbase.getTime(Coinbase.CB_VERSION_VALUE).getData().getEpoch();
    final CoinbaseBuySellResponse deposits =
            coinbase.getAllDeposits(
                    Coinbase.CB_VERSION_VALUE,
                    apiKey,
                    signatureCreator2,
                    timestamp,
                    accountId,
                    params.getLimit(),
                    params.getStartId()
            );
    return CoinbaseAdapters.adaptFundings(deposits.getData());
  }
}
