package org.knowm.xchange.okcoin.v3.service;

import java.io.IOException;
import java.math.BigDecimal;
import java.util.Collections;
import java.util.List;
import org.knowm.xchange.okcoin.OkexExchangeV3;
import org.knowm.xchange.okcoin.v3.dto.account.BillType;
import org.knowm.xchange.okcoin.v3.dto.account.FuturesBillsResponse;
import org.knowm.xchange.okcoin.v3.dto.account.FuturesPosition;
import org.knowm.xchange.okcoin.v3.dto.account.MarginAccountResponse;
import org.knowm.xchange.okcoin.v3.dto.account.MarginAccountSettingsRecord;
import org.knowm.xchange.okcoin.v3.dto.account.OkexDepositRecord;
import org.knowm.xchange.okcoin.v3.dto.account.OkexFundingAccountRecord;
import org.knowm.xchange.okcoin.v3.dto.account.OkexSpotAccountRecord;
import org.knowm.xchange.okcoin.v3.dto.account.OkexWithdrawalRecord;
import org.knowm.xchange.okcoin.v3.dto.account.OkexWithdrawalRequest;
import org.knowm.xchange.okcoin.v3.dto.account.OkexWithdrawalResponse;
import org.knowm.xchange.okcoin.v3.dto.trade.FundsTransferRequest;
import org.knowm.xchange.okcoin.v3.dto.trade.FundsTransferResponse;
import org.knowm.xchange.okcoin.v3.dto.trade.FuturesAccountsByCurrencyResponse;
import org.knowm.xchange.okcoin.v3.dto.trade.FuturesAccountsResponse;
import org.knowm.xchange.okcoin.v3.dto.trade.FuturesPositionsResponse;
import org.knowm.xchange.okcoin.v3.dto.trade.MarginBorrowRequest;
import org.knowm.xchange.okcoin.v3.dto.trade.MarginBorrowResponse;
import org.knowm.xchange.okcoin.v3.dto.trade.MarginRepaymentRequest;
import org.knowm.xchange.okcoin.v3.dto.trade.MarginRepaymentResponse;
import org.knowm.xchange.okcoin.v3.dto.trade.SwapAccountsResponse;
import org.knowm.xchange.okcoin.v3.dto.trade.SwapAccountsResponse.SwapAccountInfo;
import org.knowm.xchange.okcoin.v3.dto.trade.SwapPositionsEntry;

public class OkexAccountServiceRaw extends OkexBaseService {

  protected OkexAccountServiceRaw(OkexExchangeV3 exchange) {
    super(exchange);
  }

  public List<OkexFundingAccountRecord> fundingAccountInformation() throws IOException {
    return okex.fundingAccountInformation(apikey, digest, timestamp(), passphrase);
  }

  public List<OkexSpotAccountRecord> spotTradingAccount() throws IOException {
    return okex.spotTradingAccount(apikey, digest, timestamp(), passphrase);
  }

  public FundsTransferResponse fundsTransfer(
      String currency,
      BigDecimal amount,
      String from,
      String to,
      String subAccount,
      String instrumentId)
      throws IOException {
    FundsTransferRequest req =
        FundsTransferRequest.builder()
            .currency(currency)
            .amount(amount)
            .from(from)
            .to(to)
            .subAccount(subAccount)
            .instrumentId(instrumentId)
            .build();
    FundsTransferResponse res = okex.fundsTransfer(apikey, digest, timestamp(), passphrase, req);
    res.checkResult();
    return res;
  }

  public OkexWithdrawalResponse withdrawal(OkexWithdrawalRequest req) throws IOException {
    OkexWithdrawalResponse res = okex.withdrawal(apikey, digest, timestamp(), passphrase, req);
    res.checkResult();
    return res;
  }

  public List<OkexWithdrawalRecord> recentWithdrawalHistory() throws IOException {
    return okex.recentWithdrawalHistory(apikey, digest, timestamp(), passphrase);
  }

  public List<OkexDepositRecord> recentDepositHistory() throws IOException {
    return okex.recentDepositHistory(apikey, digest, timestamp(), passphrase);
  }

  /** ******************************** Futures Account API ********************************* */
  public List<FuturesPosition> getFuturesPositions() throws IOException {
    FuturesPositionsResponse res =
        okex.getFuturesPositions(apikey, digest, timestamp(), passphrase);
    res.checkResult();
    List<List<FuturesPosition>> holding = res.getHolding();
    return holding == null || holding.isEmpty() ? Collections.emptyList() : holding.get(0);
  }

  public FuturesAccountsResponse getFuturesAccounts() throws IOException {
    FuturesAccountsResponse res = okex.getFuturesAccounts(apikey, digest, timestamp(), passphrase);
    res.checkResult();
    return res;
  }

  public FuturesAccountsByCurrencyResponse getFuturesAccounts(String currency) throws IOException {
    FuturesAccountsByCurrencyResponse res =
        okex.getFuturesAccounts(apikey, digest, timestamp(), passphrase, currency);
    res.checkResult();
    return res;
  }

  public List<FuturesBillsResponse> getFuturesBills(
      String underlying, String after, String before, Integer limit, BillType type)
      throws IOException {
    List<FuturesBillsResponse> res =
        okex.getFuturesBills(
            apikey, digest, timestamp(), passphrase, underlying, after, before, limit, type);
    return res;
  }

  /** ******************************** SWAP Account API ********************************* */
  public List<SwapPositionsEntry> getSwapPositions() throws IOException {
    return okex.getSwapPositions(apikey, digest, timestamp(), passphrase);
  }

  public List<SwapAccountInfo> getSwapAccounts() throws IOException {
    SwapAccountsResponse res = okex.getSwapAccounts(apikey, digest, timestamp(), passphrase);
    res.checkResult();
    return res.getInfo();
  }

  /**
   * ******************************** Margin Account API *********************************
   *
   * @return
   */
  public MarginAccountResponse[] marginAccounts() throws IOException {
    return okex.marginAccounts(apikey, digest, timestamp(), passphrase);
  }

  public List<MarginAccountSettingsRecord> marginAccountsSettings() throws IOException {
    return okex.marginAccountsSettings(apikey, digest, timestamp(), passphrase);
  }

  public MarginBorrowResponse marginBorrow(MarginBorrowRequest req) throws IOException {
    return okex.marginBorrow(apikey, digest, timestamp(), passphrase, req);
  }

  public MarginRepaymentResponse marginRepayment(MarginRepaymentRequest req) throws IOException {
    return okex.marginRepayment(apikey, digest, timestamp(), passphrase, req);
  }
}
