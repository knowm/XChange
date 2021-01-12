package org.knowm.xchange.ftx.service;

import java.io.IOException;
import java.math.BigDecimal;
import java.util.List;
import java.util.Map;
import org.knowm.xchange.Exchange;
import org.knowm.xchange.currency.Currency;
import org.knowm.xchange.currency.CurrencyPair;
import org.knowm.xchange.dto.account.AccountInfo;
import org.knowm.xchange.dto.account.AddressWithTag;
import org.knowm.xchange.dto.account.Fee;
import org.knowm.xchange.dto.account.FundingRecord;
import org.knowm.xchange.ftx.FtxAdapters;
import org.knowm.xchange.instrument.Instrument;
import org.knowm.xchange.service.account.AccountService;
import org.knowm.xchange.service.trade.params.TradeHistoryParams;
import org.knowm.xchange.service.trade.params.WithdrawFundsParams;

public class FtxAccountService extends FtxAccountServiceRaw implements AccountService {

  public FtxAccountService(Exchange exchange) {
    super(exchange);
  }

  @Override
  public AccountInfo getAccountInfo() throws IOException {
    return FtxAdapters.adaptAccountInfo(getFtxAccountInformation(), getFtxWalletBalances());
  }

  @Override
  public String withdrawFunds(Currency currency, BigDecimal amount, String address)
      throws IOException {
    return null;
  }

  @Override
  public String withdrawFunds(Currency currency, BigDecimal amount, AddressWithTag address)
      throws IOException {
    return null;
  }

  @Override
  public String withdrawFunds(WithdrawFundsParams params) throws IOException {
    return null;
  }

  @Override
  public String requestDepositAddress(Currency currency, String... args) throws IOException {
    return null;
  }

  @Override
  public AddressWithTag requestDepositAddressData(Currency currency, String... args)
      throws IOException {
    return null;
  }

  @Override
  public TradeHistoryParams createFundingHistoryParams() {
    return null;
  }

  @Override
  public List<FundingRecord> getFundingHistory(TradeHistoryParams params) throws IOException {
    return null;
  }

  @Override
  public Map<Instrument, Fee> getDynamicTradingFeesByInstrument() throws IOException {
    return null;
  }

  @Override
  public Map<CurrencyPair, Fee> getDynamicTradingFees() throws IOException {
    return null;
  }
}
