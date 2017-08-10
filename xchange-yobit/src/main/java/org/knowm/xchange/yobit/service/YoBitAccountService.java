package org.knowm.xchange.yobit.service;

import org.knowm.xchange.currency.Currency;
import org.knowm.xchange.dto.account.AccountInfo;
import org.knowm.xchange.dto.account.Balance;
import org.knowm.xchange.dto.account.FundingRecord;
import org.knowm.xchange.dto.account.Wallet;
import org.knowm.xchange.exceptions.ExchangeException;
import org.knowm.xchange.exceptions.NotAvailableFromExchangeException;
import org.knowm.xchange.exceptions.NotYetImplementedForExchangeException;
import org.knowm.xchange.service.account.AccountService;
import org.knowm.xchange.service.trade.params.DefaultWithdrawFundsParams;
import org.knowm.xchange.service.trade.params.TradeHistoryParams;
import org.knowm.xchange.service.trade.params.WithdrawFundsParams;
import org.knowm.xchange.yobit.YoBit;
import org.knowm.xchange.yobit.YoBitAdapters;
import org.knowm.xchange.yobit.YoBitExchange;
import org.knowm.xchange.yobit.dto.BaseYoBitResponse;

import java.io.IOException;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class YoBitAccountService extends YoBitBaseService<YoBit> implements AccountService {

  public YoBitAccountService(YoBitExchange exchange) {
    super(YoBit.class, exchange);
  }

  @Override
  public AccountInfo getAccountInfo() throws ExchangeException, NotAvailableFromExchangeException, NotYetImplementedForExchangeException, IOException {
    BaseYoBitResponse response = service.getInfo(exchange.getExchangeSpecification().getApiKey(), signatureCreator, "getInfo", exchange.getNonceFactory());

    if (!response.success)
      throw new ExchangeException("failed to get account info");

    Map walletData = response.returnData;

    Map funds = (Map) walletData.get("funds");
    if (funds == null)
      funds = new HashMap();

    Map fundsIncludingOrders = (Map) walletData.get("funds_incl_orders");
    if (fundsIncludingOrders == null)
      fundsIncludingOrders = new HashMap();

    Collection<Wallet> wallets = new ArrayList<>();

    for (Object key : funds.keySet()) {
      Currency currency = YoBitAdapters.adaptCurrency(key.toString());

      BigDecimal amountAvailable = new BigDecimal(funds.get(key).toString());
      BigDecimal amountIncludingOrders = new BigDecimal(fundsIncludingOrders.get(key).toString());

      Balance balance = new Balance(
          currency,
          amountIncludingOrders,
          amountAvailable,
          BigDecimal.ZERO,
          BigDecimal.ZERO,
          BigDecimal.ZERO,
          BigDecimal.ZERO,
          BigDecimal.ZERO
      );

      wallets.add(new Wallet(currency.getCurrencyCode(), balance));
    }

    return new AccountInfo(wallets);
  }

  @Override
  public String withdrawFunds(Currency currency, BigDecimal amount, String address) throws ExchangeException, NotAvailableFromExchangeException, NotYetImplementedForExchangeException, IOException {
    return withdrawFunds(new DefaultWithdrawFundsParams(address, currency, amount));
  }

  @Override
  public String withdrawFunds(WithdrawFundsParams params) throws ExchangeException, NotAvailableFromExchangeException, NotYetImplementedForExchangeException, IOException {
    if (params instanceof DefaultWithdrawFundsParams) {
      DefaultWithdrawFundsParams defaultWithdrawFundsParams = (DefaultWithdrawFundsParams) params;

      BaseYoBitResponse response = service.withdrawCoinsToAddress(
          exchange.getExchangeSpecification().getApiKey(),
          signatureCreator,
          "WithdrawCoinsToAddress",
          exchange.getNonceFactory(),
          defaultWithdrawFundsParams.currency.getCurrencyCode(),
          defaultWithdrawFundsParams.amount,
          defaultWithdrawFundsParams.address
      );

      if (!response.success)
        throw new ExchangeException("failed to withdraw funds");

      return response.returnData.toString();
    }

    throw new IllegalStateException("Don't understand " + params);
  }

  @Override
  public String requestDepositAddress(Currency currency, String... args) throws ExchangeException, NotAvailableFromExchangeException, NotYetImplementedForExchangeException, IOException {
    boolean needNew = false;//todo: implement this option

    BaseYoBitResponse response = service.getDepositAddress(
        exchange.getExchangeSpecification().getApiKey(),
        signatureCreator,
        "GetDepositAddress",
        exchange.getNonceFactory(),
        currency.getCurrencyCode(),
        needNew
    );

    if (!response.success)
      throw new ExchangeException("failed to withdraw funds");

    return response.returnData.get("address").toString();
  }

  @Override
  public TradeHistoryParams createFundingHistoryParams() {
    throw new NotYetImplementedForExchangeException();
  }

  @Override
  public List<FundingRecord> getFundingHistory(TradeHistoryParams params) throws ExchangeException, NotAvailableFromExchangeException, NotYetImplementedForExchangeException, IOException {
    throw new NotAvailableFromExchangeException();
  }
}
