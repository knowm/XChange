package org.knowm.xchange.okcoin.v3.service;

import java.io.IOException;
import java.math.BigDecimal;
import java.util.List;
import org.knowm.xchange.okcoin.OkexExchangeV3;
import org.knowm.xchange.okcoin.v3.dto.account.*;
import org.knowm.xchange.okcoin.v3.dto.trade.*;
import org.knowm.xchange.okcoin.v3.dto.trade.SwapAccountsResponse.SwapAccountInfo;

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
    return res.getHolding().get(0);
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
