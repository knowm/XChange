package org.knowm.xchange.ftx.service;

import java.io.IOException;
import org.knowm.xchange.Exchange;
import org.knowm.xchange.dto.account.AccountInfo;
import org.knowm.xchange.dto.account.AccountLeverageSetting;
import org.knowm.xchange.ftx.FtxAdapters;
import org.knowm.xchange.ftx.FtxException;
import org.knowm.xchange.ftx.dto.account.FtxLeverageDto;
import org.knowm.xchange.service.account.AccountService;

public class FtxAccountService extends FtxAccountServiceRaw implements AccountService {

  public FtxAccountService(Exchange exchange) {
    super(exchange);
  }

  @Override
  public AccountInfo getAccountInfo() throws IOException {
    return getSubaccountInfo(exchange.getExchangeSpecification().getUserName());
  }

  public AccountInfo getSubaccountInfo(String subaccount) throws IOException {
    return FtxAdapters.adaptAccountInfo(
        getFtxAccountInformation(subaccount), getFtxWalletBalances(subaccount));
  }

  @Override
  public AccountLeverageSetting setInitialLeverage(AccountLeverageSetting setting) throws IOException {
      try {
        ftx.changeLeverage(
                exchange.getExchangeSpecification().getApiKey(),
                exchange.getNonceFactory().createValue(),
                signatureCreator,
                null,
                new FtxLeverageDto(setting.getLeverage()));
        return new AccountLeverageSetting.Builder().leverage(setting.getLeverage()).build();
      } catch (FtxException e) {
        throw new FtxException(e.getMessage());
      }
    }
}
