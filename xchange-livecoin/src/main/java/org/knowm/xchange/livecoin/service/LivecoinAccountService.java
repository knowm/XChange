package org.knowm.xchange.livecoin.service;

import java.io.IOException;
import java.math.BigDecimal;
import java.util.Date;
import java.util.List;
import java.util.Map;

import org.knowm.xchange.currency.Currency;
import org.knowm.xchange.dto.account.AccountInfo;
import org.knowm.xchange.dto.account.FundingRecord;
import org.knowm.xchange.exceptions.NotAvailableFromExchangeException;
import org.knowm.xchange.livecoin.Livecoin;
import org.knowm.xchange.livecoin.LivecoinDigest;
import org.knowm.xchange.livecoin.LivecoinExchange;
import org.knowm.xchange.service.account.AccountService;
import org.knowm.xchange.service.trade.params.DefaultWithdrawFundsParams;
import org.knowm.xchange.service.trade.params.TradeHistoryParamLimit;
import org.knowm.xchange.service.trade.params.TradeHistoryParamOffset;
import org.knowm.xchange.service.trade.params.TradeHistoryParams;
import org.knowm.xchange.service.trade.params.TradeHistoryParamsTimeSpan;
import org.knowm.xchange.service.trade.params.WithdrawFundsParams;

public class LivecoinAccountService extends LivecoinAccountServiceRaw implements AccountService {
  public LivecoinAccountService(LivecoinExchange exchange) {
    super(exchange);
  }

  @Override
  public AccountInfo getAccountInfo() throws IOException {
    return new AccountInfo(balances(null));
  }

  @Override
  public String withdrawFunds(Currency currency, BigDecimal amount, String address) throws IOException {
    return withdrawFunds(new DefaultWithdrawFundsParams(address, currency, amount));
  }

  @Override
  public String withdrawFunds(WithdrawFundsParams params) throws IOException {
    LivecoinWithdrawParams livecoinWithdrawParams;

    if (params instanceof LivecoinWithdrawParams) {
      livecoinWithdrawParams = (LivecoinWithdrawParams) params;
    } else if (params instanceof DefaultWithdrawFundsParams) {
      DefaultWithdrawFundsParams defaultWithdrawFundsParams = (DefaultWithdrawFundsParams) params;
      livecoinWithdrawParams = new CryptoWithdrawParams(defaultWithdrawFundsParams.getAmount(), defaultWithdrawFundsParams.getCurrency(),
          defaultWithdrawFundsParams.getAddress());
    } else {
      throw new IllegalStateException("Don't understand " + params);
    }

    return withdraw(livecoinWithdrawParams);
  }

  @Override
  public String requestDepositAddress(Currency currency, String... args) throws IOException {
    return walletAddress(currency);
  }

  @Override
  public List<FundingRecord> getFundingHistory(TradeHistoryParams params) throws IOException {
    Date start = new Date(0);
    Date end = new Date();
    if (params instanceof TradeHistoryParamsTimeSpan) {
      TradeHistoryParamsTimeSpan tradeHistoryParamsTimeSpan = (TradeHistoryParamsTimeSpan) params;
      start = tradeHistoryParamsTimeSpan.getStartTime();
      end = tradeHistoryParamsTimeSpan.getEndTime();
    }

    Long offset = 0L;
    if (params instanceof TradeHistoryParamOffset) {
      offset = ((TradeHistoryParamOffset) params).getOffset();
    }

    Integer limit = 100;
    if (params instanceof TradeHistoryParamLimit) {
      limit = ((TradeHistoryParamLimit) params).getLimit();
    }

    return funding(start, end, limit, offset);
  }

  @Override
  public TradeHistoryParams createFundingHistoryParams() {
    throw new NotAvailableFromExchangeException();
  }

  public class CryptoWithdrawParams implements LivecoinWithdrawParams, WithdrawFundsParams {
    public final BigDecimal amount;
    public final Currency currency;
    public final String wallet;

    public CryptoWithdrawParams(BigDecimal amount, Currency currency, String wallet) {
      this.amount = amount;
      this.currency = currency;
      this.wallet = wallet;
    }

    @Override
    public LivecoinResponse<Map> withdraw(Livecoin service, String apiKey, LivecoinDigest signatureCreator) throws IOException {
      return service.paymentOutCoin(apiKey, signatureCreator, currency.getCurrencyCode(), amount, wallet);
    }
  }

}
