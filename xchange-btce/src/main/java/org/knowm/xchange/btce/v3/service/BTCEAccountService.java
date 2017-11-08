package org.knowm.xchange.btce.v3.service;

import org.knowm.xchange.Exchange;
import org.knowm.xchange.btce.v3.BTCEAdapters;
import org.knowm.xchange.btce.v3.dto.account.BTCEAccountInfo;
import org.knowm.xchange.btce.v3.dto.trade.BTCETransHistoryResult;
import org.knowm.xchange.currency.Currency;
import org.knowm.xchange.dto.account.AccountInfo;
import org.knowm.xchange.dto.account.FundingRecord;
import org.knowm.xchange.exceptions.ExchangeException;
import org.knowm.xchange.exceptions.NotAvailableFromExchangeException;
import org.knowm.xchange.exceptions.NotYetImplementedForExchangeException;
import org.knowm.xchange.service.account.AccountService;
import org.knowm.xchange.service.trade.params.DefaultWithdrawFundsParams;
import org.knowm.xchange.service.trade.params.TradeHistoryParams;
import org.knowm.xchange.service.trade.params.WithdrawFundsParams;
import org.knowm.xchange.utils.DateUtils;

import java.io.IOException;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * @author Matija Mazi
 */
public class BTCEAccountService extends BTCEAccountServiceRaw implements AccountService {

  /**
   * Constructor
   *
   * @param exchange
   */
  public BTCEAccountService(Exchange exchange) {

    super(exchange);
  }

  @Override
  public AccountInfo getAccountInfo() throws IOException {

    BTCEAccountInfo info = getBTCEAccountInfo();
    return new AccountInfo(BTCEAdapters.adaptWallet(info));
  }

  @Override
  public String withdrawFunds(Currency currency, BigDecimal amount, String address) throws IOException {
    return withdraw(currency.toString(), amount, address);
  }

  @Override
  public String withdrawFunds(WithdrawFundsParams params) throws ExchangeException, NotAvailableFromExchangeException, NotYetImplementedForExchangeException, IOException {
    if (params instanceof DefaultWithdrawFundsParams) {
      DefaultWithdrawFundsParams defaultParams = (DefaultWithdrawFundsParams) params;
      return withdrawFunds(defaultParams.currency, defaultParams.amount, defaultParams.address);
    }
    throw new IllegalStateException("Don't know how to withdraw: " + params);
  }

  @Override
  public String requestDepositAddress(Currency currency, String... args) throws IOException {

    throw new NotAvailableFromExchangeException();
  }

  @Override
  public TradeHistoryParams createFundingHistoryParams() {
    throw new NotAvailableFromExchangeException();
  }

  @Override
  public List<FundingRecord> getFundingHistory(TradeHistoryParams params) throws ExchangeException, NotAvailableFromExchangeException, NotYetImplementedForExchangeException, IOException {
    Map<Long, BTCETransHistoryResult> map = transferHistory();

    List<FundingRecord> fundingRecords = new ArrayList<>();

    if(map == null)
      return fundingRecords;

    for (Long key : map.keySet()) {
      BTCETransHistoryResult result = map.get(key);

      FundingRecord.Status status = FundingRecord.Status.COMPLETE;//todo

      FundingRecord.Type type;//todo
      if(result.getType().equals(BTCETransHistoryResult.Type.BTC_deposit))
        type = FundingRecord.Type.DEPOSIT;
      else if(result.getType().equals(BTCETransHistoryResult.Type.BTC_withdrawal))
        type = FundingRecord.Type.WITHDRAWAL;
      else
        continue;

      fundingRecords.add(new FundingRecord(
              null,
              DateUtils.fromMillisUtc(result.getTimestamp()),
              Currency.getInstance(result.getCurrency()),
              result.getAmount(),
              String.valueOf(key),
              null,
              type,
              status,
              null,
              null,
              result.getDescription()
      ));
    }

    return fundingRecords;
  }
}
