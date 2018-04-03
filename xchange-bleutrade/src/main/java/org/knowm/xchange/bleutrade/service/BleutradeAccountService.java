package org.knowm.xchange.bleutrade.service;

import java.io.IOException;
import java.math.BigDecimal;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.TimeZone;
import org.knowm.xchange.Exchange;
import org.knowm.xchange.bleutrade.BleutradeAdapters;
import org.knowm.xchange.bleutrade.dto.account.BleutradeBalance;
import org.knowm.xchange.currency.Currency;
import org.knowm.xchange.dto.account.AccountInfo;
import org.knowm.xchange.dto.account.FundingRecord;
import org.knowm.xchange.exceptions.NotAvailableFromExchangeException;
import org.knowm.xchange.service.account.AccountService;
import org.knowm.xchange.service.trade.params.DefaultWithdrawFundsParams;
import org.knowm.xchange.service.trade.params.TradeHistoryParams;
import org.knowm.xchange.service.trade.params.WithdrawFundsParams;
import si.mazi.rescu.IRestProxyFactory;

public class BleutradeAccountService extends BleutradeAccountServiceRaw implements AccountService {

  /**
   * Constructor
   *
   * @param exchange
   */
  public BleutradeAccountService(Exchange exchange, IRestProxyFactory restProxyFactory) {

    super(exchange, restProxyFactory);
  }

  @Override
  public AccountInfo getAccountInfo() throws IOException {

    List<BleutradeBalance> bleutradeBalances = getBleutradeBalances();
    return new AccountInfo(BleutradeAdapters.adaptBleutradeBalances(bleutradeBalances));
  }

  @Override
  public String withdrawFunds(Currency currency, BigDecimal amount, String address)
      throws IOException {
    return withdraw(currency, amount, address);
  }

  @Override
  public String withdrawFunds(WithdrawFundsParams params) throws IOException {
    if (params instanceof DefaultWithdrawFundsParams) {
      DefaultWithdrawFundsParams defaultParams = (DefaultWithdrawFundsParams) params;
      return withdrawFunds(
          defaultParams.getCurrency(), defaultParams.getAmount(), defaultParams.getAddress());
    }
    throw new IllegalStateException("Don't know how to withdraw: " + params);
  }

  @Override
  public String requestDepositAddress(Currency currency, String... args) throws IOException {

    return getBleutradeDepositAddress(currency.toString()).getAddress();
  }

  @Override
  public TradeHistoryParams createFundingHistoryParams() {
    throw new NotAvailableFromExchangeException();
  }

  @Override
  public List<FundingRecord> getFundingHistory(TradeHistoryParams params) throws IOException {
    List<FundingRecord> fundingRecords = new ArrayList<>();
    SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
    dateFormat.setTimeZone(TimeZone.getTimeZone("UTC"));

    try {
      for (WithdrawRecord record : withdrawalHistory()) {
        String label = record.label;

        BigDecimal amount = record.amount;
        BigDecimal fee = null;

        String[] parts = label.split(";");
        String address = null;
        if (parts.length == 3) {
          amount = new BigDecimal(parts[0]);
          address = parts[1];
          fee = new BigDecimal(parts[2]);
        }

        fundingRecords.add(
            new FundingRecord(
                address,
                dateFormat.parse(record.timestamp),
                Currency.getInstance(record.coin),
                amount,
                record.id,
                record.transactionId,
                FundingRecord.Type.WITHDRAWAL,
                FundingRecord.Status.COMPLETE,
                null,
                fee,
                label));
      }

      for (DepositRecord record : depositHistory()) {
        fundingRecords.add(
            new FundingRecord(
                null,
                dateFormat.parse(record.timestamp),
                Currency.getInstance(record.coin),
                record.amount,
                record.id,
                null,
                FundingRecord.Type.DEPOSIT,
                FundingRecord.Status.COMPLETE,
                null,
                null,
                record.label));
      }
    } catch (ParseException e) {
      throw new IllegalStateException("Should not happen", e);
    }

    return fundingRecords;
  }
}
