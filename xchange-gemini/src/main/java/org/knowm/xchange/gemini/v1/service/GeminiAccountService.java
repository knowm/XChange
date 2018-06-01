package org.knowm.xchange.gemini.v1.service;

import java.io.IOException;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import org.knowm.xchange.Exchange;
import org.knowm.xchange.currency.Currency;
import org.knowm.xchange.dto.account.AccountInfo;
import org.knowm.xchange.dto.account.FundingRecord;
import org.knowm.xchange.exceptions.NotAvailableFromExchangeException;
import org.knowm.xchange.gemini.v1.GeminiAdapters;
import org.knowm.xchange.gemini.v1.dto.account.GeminiDepositAddressResponse;
import org.knowm.xchange.gemini.v1.dto.account.GeminiTransfersResponse;
import org.knowm.xchange.service.account.AccountService;
import org.knowm.xchange.service.trade.params.DefaultWithdrawFundsParams;
import org.knowm.xchange.service.trade.params.TradeHistoryParamLimit;
import org.knowm.xchange.service.trade.params.TradeHistoryParams;
import org.knowm.xchange.service.trade.params.TradeHistoryParamsTimeSpan;
import org.knowm.xchange.service.trade.params.WithdrawFundsParams;

public class GeminiAccountService extends GeminiAccountServiceRaw implements AccountService {

  /**
   * Constructor
   *
   * @param exchange
   */
  public GeminiAccountService(Exchange exchange) {

    super(exchange);
  }

  @Override
  public AccountInfo getAccountInfo() throws IOException {

    return new AccountInfo(GeminiAdapters.adaptWallet(getGeminiAccountInfo()));
  }

  /**
   * Withdrawal support
   *
   * @param currency
   * @param amount
   * @param address
   * @return
   * @throws IOException
   */
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

  /** This will result in a new address being created each time, and is severely rate-limited */
  @Override
  public String requestDepositAddress(Currency currency, String... arguments) throws IOException {
    GeminiDepositAddressResponse response = super.requestDepositAddressRaw(currency);
    return response.getAddress();
  }

  @Override
  public List<FundingRecord> getFundingHistory(TradeHistoryParams params) throws IOException {
    Date since = null;
    Integer limit = 50;

    if (params instanceof TradeHistoryParamLimit) {
      limit = ((TradeHistoryParamLimit) params).getLimit();
    }

    if (params instanceof TradeHistoryParamsTimeSpan) {
      since = ((TradeHistoryParamsTimeSpan) params).getStartTime();
    }

    List<FundingRecord> results = new ArrayList<>();

    for (GeminiTransfersResponse.GeminiTransfer transfer : transfers(since, limit)) {
      results.add(GeminiAdapters.adapt(transfer));
    }

    return results;
  }

  @Override
  public TradeHistoryParams createFundingHistoryParams() {
    throw new NotAvailableFromExchangeException();
  }
}
