package org.knowm.xchange.okcoin.service;

import org.knowm.xchange.Exchange;
import org.knowm.xchange.currency.Currency;
import org.knowm.xchange.dto.account.AccountInfo;
import org.knowm.xchange.dto.account.FundingRecord;
import org.knowm.xchange.exceptions.ExchangeException;
import org.knowm.xchange.exceptions.NotAvailableFromExchangeException;
import org.knowm.xchange.exceptions.NotYetImplementedForExchangeException;
import org.knowm.xchange.okcoin.OkCoinAdapters;
import org.knowm.xchange.okcoin.dto.account.OKCoinWithdraw;
import org.knowm.xchange.okcoin.dto.account.OkCoinAccountRecords;
import org.knowm.xchange.service.account.AccountService;
import org.knowm.xchange.service.trade.params.DefaultTradeHistoryParamPaging;
import org.knowm.xchange.service.trade.params.TradeHistoryParams;

import java.io.IOException;
import java.math.BigDecimal;
import java.util.List;

public class OkCoinAccountService extends OkCoinAccountServiceRaw implements AccountService {

  /**
   * Constructor
   *
   * @param exchange
   */
  public OkCoinAccountService(Exchange exchange) {

    super(exchange);

  }

  @Override
  public AccountInfo getAccountInfo() throws IOException {

    return OkCoinAdapters.adaptAccountInfo(getUserInfo());
  }

  @Override
  public String withdrawFunds(Currency currency, BigDecimal amount, String address) throws IOException {
    String okcoinCurrency = currency == Currency.BTC ? "btc_usd" : "btc_ltc";

    OKCoinWithdraw result = withdraw(null, okcoinCurrency, address, amount);

    if (result != null)
      return result.getWithdrawId();

    return "";
  }

  @Override
  public String requestDepositAddress(Currency currency, String... args) throws IOException {
    throw new NotAvailableFromExchangeException();
  }

  @Override
  public List<FundingRecord> getFundingHistory(TradeHistoryParams params) throws ExchangeException, NotAvailableFromExchangeException, NotYetImplementedForExchangeException, IOException{
    final OkCoinFundingHistoryParams histParams = (OkCoinFundingHistoryParams) params;
    final OkCoinAccountRecords depositRecord = getAccountRecords(histParams.getSymbol(), "0",
            String.valueOf(histParams.getPageNumber() != null ? histParams.getPageNumber() : 1),
            String.valueOf(histParams.getPageLength() != null ? histParams.getPageLength() : 1));
    final OkCoinAccountRecords withdrawalRecord = getAccountRecords(histParams.getSymbol(), "1",
            String.valueOf(histParams.getPageNumber() != null ? histParams.getPageNumber() : 1),
            String.valueOf(histParams.getPageLength() != null ? histParams.getPageLength() : 1));
    final OkCoinAccountRecords[] okCoinAccountRecordsList = new OkCoinAccountRecords[] {depositRecord, withdrawalRecord};
    return OkCoinAdapters.adaptFundingHistory(okCoinAccountRecordsList);
  }

  public static class OkCoinFundingHistoryParams extends DefaultTradeHistoryParamPaging {

    private final String symbol;

    public OkCoinFundingHistoryParams(final Integer pageNumber, final Integer pageLength, final String symbol) {
      super(pageLength, pageNumber);
      this.symbol = symbol;
    }

    public String getSymbol() {
      return symbol;
    }
  }

}
