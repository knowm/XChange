package org.knowm.xchange.ftx.service;

import org.knowm.xchange.Exchange;
import org.knowm.xchange.dto.account.AccountInfo;
import org.knowm.xchange.ftx.FtxAdapters;
import org.knowm.xchange.ftx.dto.FtxResponse;
import org.knowm.xchange.ftx.dto.account.FtxAccountDto;
import org.knowm.xchange.service.account.AccountService;
import org.knowm.xchange.service.account.params.AccountLeverageParams;

import java.io.IOException;
import java.math.BigDecimal;

public class FtxAccountService extends FtxAccountServiceRaw implements AccountService {

  public FtxAccountService(Exchange exchange) {
    super(exchange);
  }

  @Override
  public AccountInfo getAccountInfo() throws IOException {
    return getSubaccountInfo(exchange.getExchangeSpecification().getUserName());
  }

  public AccountInfo getSubaccountInfo(String subaccount) throws IOException {
    FtxResponse<FtxAccountDto> ftxAccountInformation = getFtxAccountInformation(subaccount);
    BigDecimal leverage = ftxAccountInformation.getResult().getLeverage();
    return FtxAdapters.adaptAccountInfo(
        ftxAccountInformation,
        getFtxWalletBalances(subaccount),
        ((FtxTradeService) exchange.getTradeService())
            .getOpenPositionsForSubaccount(subaccount, leverage)
            .getOpenPositions());
  }

  @Override
  public void setLeverage(AccountLeverageParams params) throws IOException {
    setLeverage(exchange.getExchangeSpecification().getUserName(), params.getLeverage());
  }
}
