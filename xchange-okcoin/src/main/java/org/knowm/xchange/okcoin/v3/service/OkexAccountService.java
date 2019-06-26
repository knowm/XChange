package org.knowm.xchange.okcoin.v3.service;

import java.io.IOException;
import java.util.Collection;
import java.util.List;
import java.util.stream.Collectors;
import org.knowm.xchange.dto.account.AccountInfo;
import org.knowm.xchange.dto.account.Balance;
import org.knowm.xchange.dto.account.Wallet;
import org.knowm.xchange.okcoin.OkexAdaptersV3;
import org.knowm.xchange.okcoin.OkexExchangeV3;
import org.knowm.xchange.okcoin.v3.dto.account.OkexFundingAccountRecord;
import org.knowm.xchange.okcoin.v3.dto.account.OkexSpotAccountRecord;
import org.knowm.xchange.service.account.AccountService;

public class OkexAccountService extends OkexAccountServiceRaw implements AccountService {

  public OkexAccountService(OkexExchangeV3 exchange) {
    super(exchange);
  }

  @Override
  public AccountInfo getAccountInfo() throws IOException {
    List<OkexFundingAccountRecord> funding = super.fundingAccountInformation();
    Collection<Balance> fundingBalances =
        funding.stream().map(OkexAdaptersV3::convert).collect(Collectors.toList());
    List<OkexSpotAccountRecord> spotTradingAccount = super.spotTradingAccount();
    Collection<Balance> tradingBalances =
        spotTradingAccount.stream().map(OkexAdaptersV3::convert).collect(Collectors.toList());
    return new AccountInfo(
        new Wallet("Funding Account", "Funding Account", fundingBalances),
        new Wallet("Trading Account", "Trading Account", tradingBalances));
  }
}
