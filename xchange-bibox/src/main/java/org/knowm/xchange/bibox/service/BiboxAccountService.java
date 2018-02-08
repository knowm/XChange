package org.knowm.xchange.bibox.service;

import java.io.IOException;
import java.math.BigDecimal;
import java.util.List;

import org.knowm.xchange.Exchange;
import org.knowm.xchange.bibox.dto.BiboxAdapters;
import org.knowm.xchange.currency.Currency;
import org.knowm.xchange.dto.account.AccountInfo;
import org.knowm.xchange.dto.account.FundingRecord;
import org.knowm.xchange.service.account.AccountService;
import org.knowm.xchange.service.trade.params.TradeHistoryParams;
import org.knowm.xchange.service.trade.params.WithdrawFundsParams;

/**
 * @author odrotleff
 */
public class BiboxAccountService extends BiboxAccountServiceRaw implements AccountService {

  public BiboxAccountService(Exchange exchange) {
    super(exchange);
  }

  @Override
  public AccountInfo getAccountInfo() throws IOException {
    // TODO could be improved with a batched call to get other infos
    return BiboxAdapters.adaptAccountInfo(getBiboxAccountInfo());
  }

  @Override
  public String withdrawFunds(Currency currency, BigDecimal amount, String address) throws IOException {
    // TODO Auto-generated method stub
    return null;
  }

  @Override
  public String withdrawFunds(WithdrawFundsParams params) throws IOException {
    // TODO Auto-generated method stub
    return null;
  }

  @Override
  public String requestDepositAddress(Currency currency, String... args) throws IOException {
    // TODO Auto-generated method stub
    return null;
  }

  @Override
  public TradeHistoryParams createFundingHistoryParams() {
    // TODO Auto-generated method stub
    return null;
  }

  @Override
  public List<FundingRecord> getFundingHistory(TradeHistoryParams params) throws IOException {
    // TODO Auto-generated method stub
    return null;
  }
}
