package org.knowm.xchange.okcoin.v3.service;

import java.io.IOException;
import java.math.BigDecimal;
import java.util.List;
import org.knowm.xchange.okcoin.OkexExchangeV3;
import org.knowm.xchange.okcoin.v3.dto.account.OkexDepositRecord;
import org.knowm.xchange.okcoin.v3.dto.account.OkexFundingAccountRecord;
import org.knowm.xchange.okcoin.v3.dto.account.OkexSpotAccountRecord;
import org.knowm.xchange.okcoin.v3.dto.account.OkexWithdrawalRecord;
import org.knowm.xchange.okcoin.v3.dto.account.OkexWithdrawalRequest;
import org.knowm.xchange.okcoin.v3.dto.account.OkexWithdrawalResponse;
import org.knowm.xchange.okcoin.v3.dto.trade.FundsTransferRequest;
import org.knowm.xchange.okcoin.v3.dto.trade.FundsTransferResponse;

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
}
